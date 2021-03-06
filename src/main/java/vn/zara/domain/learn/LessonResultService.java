/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 7:50 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.zara.domain.common.exception.LessonResultNotExisted;
import vn.zara.domain.common.exception.NotEnoughPokemon;
import vn.zara.domain.common.exception.PokemonNotExisted;
import vn.zara.domain.common.exception.ZaraException;
import vn.zara.domain.lesson.Exercise;
import vn.zara.domain.pokemon.Pokemon;
import vn.zara.domain.pokemon.PokemonGroup;
import vn.zara.domain.pokemon.PokemonGroupRepository;
import vn.zara.domain.pokemon.PokemonInitializeDatabase;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.web.dto.PokemonDetail;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class LessonResultService {
    protected static Logger Logger = LoggerFactory.getLogger(LessonResultService.class);

    @Autowired
    private LessonResultRepository lessonResultRepository;
    @Autowired
    private DoExerciseRepository   doExerciseRepository;
    @Autowired
    private PokemonGroupRepository pokemonGroupRepository;

    @Autowired
    private PokemonInitializeDatabase pokemonInitializeDatabase;


    @Transactional
    public long updateLessonScore(String lessonId) throws ZaraException{
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Request update sum score of lesson %s for user %s", lessonId, username));

        Stream<DoExercise> listResult = doExerciseRepository.findByUsernameAndLesson(username, lessonId);

        long sumScore = listResult.mapToLong(doExercise -> doExercise.getScore()).sum();

        // --- Delete current record if existed
        Optional<LessonResult> forUpdate = lessonResultRepository.findOneByLessonIdAndUsername(lessonId, username);
        if (forUpdate.isPresent()){
            LessonResult temp = forUpdate.get();
            temp.setScore(sumScore);
            lessonResultRepository.save(temp);

            Logger.debug(String.format("Create new LessonResult: lesson %s, user %s with new Score [%s]",
                                       lessonId, username, sumScore));
        }
        else{
            LessonResult newLessonResult = new LessonResult();
            newLessonResult.setLessonId(lessonId);
            newLessonResult.setUsername(username);
            newLessonResult.setScore(new Long(sumScore));
            newLessonResult.setPokemon(getRandomPokemon());

            Logger.debug(String.format("Create new LessonResult: lesson %s, user %s with new Score [%s] pokemon %s",
                                       lessonId, username, sumScore, newLessonResult.getPokemon().getId()));

            lessonResultRepository.save(newLessonResult);
        }

        return sumScore;
    }

    private PokemonGroup getRandomPokemon() throws ZaraException {
        String username = SecurityUtil.getCurrentLogin();

        // --- If there are no group pokemon --> init example databse for pokemon
        if(pokemonGroupRepository.findAll().size() == 0)
            pokemonInitializeDatabase.seedData();

        List<PokemonGroup> existedPokemon = lessonResultRepository.findAll()
                .stream()
                .map(lessonResult -> lessonResult.getPokemon())
                .collect(Collectors.toList());

        List<PokemonGroup> availablePokemon = pokemonGroupRepository.findAll();
        existedPokemon.forEach(pokemonGroup -> availablePokemon.remove(pokemonGroup));
        Logger.debug(String.format("Get pokemon random for %s, number of available pokemon is %s",
                                   username, availablePokemon.size()));
        int totalPokemon = availablePokemon.size();
        if(totalPokemon == 0) {
            Logger.debug(String.format("Not enough pokemon in the system. Pokemon was used: %s , pokemon in system: %s",
                                       existedPokemon.size(), pokemonGroupRepository.findAll().size()));
            throw new NotEnoughPokemon("Not enough pokemon in system.");
        }
        Random random = new Random();
        int randomNum = Math.abs(random.nextInt() % totalPokemon);
        Logger.debug(String.format("Get pokemon random for %s, random number for get pokemon is %s",
                                   username, randomNum));
        return availablePokemon.get(randomNum);
    }


    public Long getLessonScore(String lessonId) {
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Request score of lesson %s for user %s", lessonId, username));
        Optional<LessonResult> lessonResult = lessonResultRepository
                .findOneByLessonIdAndUsername(lessonId, username);

        return lessonResult.isPresent() ? lessonResult.get().getScore() : 0;
    }

    public Pokemon getPokemonByLesson(String lessonId) throws LessonResultNotExisted
    {
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Get pokemon for lesson %s at user %s", lessonId, username));
        Optional<LessonResult> lessonResult = lessonResultRepository.findOneByLessonIdAndUsername(lessonId, username);
        if(!lessonResult.isPresent()){
            Logger.debug(String.format("User %s still not learning lesson %s", username, lessonId));
            throw new LessonResultNotExisted(String.format("User %s still not learning lesson %s", username, lessonId));
        }
        long scoreOfLesson = lessonResult.get().getScore();

        Logger.debug(String.format("+ getPokemonByLesson: score lesson is %s", scoreOfLesson));

        Logger.debug(String.format("+ getPokemonByLesson: number level of pokemon %s",
                lessonResult.get().getPokemon().getPokemons().size()));

        Optional<Long> keyScore = lessonResult
                .get()
                .getPokemon()
                .getPokemons()
                .keySet()
                .stream()
                .filter(pokemonScore -> pokemonScore <= scoreOfLesson)
                .max(((o1, o2) -> o1.intValue() - o2.intValue()));

        if(!keyScore.isPresent()){
            Logger.debug(String.format("User %s get pokemon for lesson %s with level %s | Find out %s",
                    username, lessonId, lessonResult.get().getScore(),
                    lessonResult.get().getPokemon().getPokemons().get(new Long(0))));
            return lessonResult.get().getPokemon().getPokemons().get(new Long(0));
        }
        Logger.debug("getPokemonForLesson: key score is " + keyScore.get());
        Logger.debug(String.format("User %s get pokemon for lesson %s with level %s | Find out %s",
                username, lessonId, lessonResult.get().getScore(),
                lessonResult.get().getPokemon().getPokemons().get(new Long(keyScore.get()))));

        return lessonResult.get().getPokemon().getPokemons().get(new Long(keyScore.get()));
    }
    public long getSumScoreOfCurrentUser(){
        String username = SecurityUtil.getCurrentLogin();
        return lessonResultRepository.findByUsername(username)
                .mapToLong(lessonResult -> lessonResult.getScore())
                .sum();
    }

    public PokemonGroup getGroupPokemon(String lessonId){
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Get pokemon for lesson %s at user %s", lessonId, username));
        Optional<LessonResult> lessonResult = lessonResultRepository.findOneByLessonIdAndUsername(lessonId, username);
        if(!lessonResult.isPresent()){
            Logger.debug(String.format("User %s still not learning lesson %s", username, lessonId));
            throw new LessonResultNotExisted(String.format("User %s still not learning lesson %s", username, lessonId));
        }

        return lessonResult.get().getPokemon();

    }

    public PokemonDetail getPokemonForLesson(String lessonId){
       try{
           Pokemon pokemon = getPokemonByLesson(lessonId);
           Logger.debug(String.format("+ getPokemonForLesson: got pokemon %s", pokemon));
           long scoreOfLesson = getLessonScore(lessonId);
           PokemonGroup pokemonGroup = getGroupPokemon(lessonId);
           long[] scores = pokemonGroup.getPokemons()
                   .keySet()
                   .stream()
                   .mapToLong(score -> score.longValue())
                   .sorted()
                   .toArray();
           long currentScore = 0;
           long nextScore = 0;
           long previousScore = 0;
           int level = 0;
           for (int i = 0; i < scores.length; i++){
               Logger.debug(String.format("Item of list pokemon level: %s", scores[i]));
               if(scoreOfLesson >= scores[i] && i == scores.length - 1){
                   currentScore = scoreOfLesson - scores[i];
                   previousScore = scores[i];
                   nextScore = currentScore + previousScore;
                   level = i + 1;
                   continue;
               }

               if(scoreOfLesson >= scores[i] && scoreOfLesson < scores[i + 1]){
                   currentScore = scoreOfLesson - scores[i];
                   nextScore = scores[i+1];
                   previousScore = scores[i];
                   level = i + 1;
               }

           }
           PokemonDetail detail = new PokemonDetail(pokemon.getName(),pokemon.getHeight(),
                   pokemon.getThumbnailAltText(),pokemon.getThumbnailImage(),pokemon.getType(),
                   pokemon.getAbilities(),pokemon.getWeakness(), level, nextScore, currentScore, previousScore);
           Logger.debug(detail.toString());
           return detail;
       }catch(Exception e){
           Logger.error(e.getMessage());
           return null;
       }
    }


}
