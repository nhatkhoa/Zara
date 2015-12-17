/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 7:50 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.zara.domain.common.exception.LessonResultNotExisted;
import vn.zara.domain.common.exception.NotEnoughPokemon;
import vn.zara.domain.common.exception.PokemonNotExisted;
import vn.zara.domain.common.exception.ZaraException;
import vn.zara.domain.pokemon.Pokemon;
import vn.zara.domain.pokemon.PokemonGroup;
import vn.zara.domain.pokemon.PokemonGroupRepository;
import vn.zara.domain.pokemon.PokemonInitializeDatabase;
import vn.zara.domain.util.SecurityUtil;

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
        availablePokemon.removeAll(existedPokemon);
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

    public Pokemon getPokemonByLesson(String lessonId) {
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Get pokemon for lesson %s at user %s", lessonId, username));
        Optional<LessonResult> lessonResult = lessonResultRepository.findOneByLessonIdAndUsername(lessonId, username);
        if(!lessonResult.isPresent()){
            Logger.debug(String.format("User %s still not learning lesson %s", username, lessonId));
            throw new LessonResultNotExisted(String.format("User %s still not learning lesson %s", username, lessonId));
        }

        Pokemon resultPokemon = null;
        resultPokemon = lessonResult
                .get()
                .getPokemon()
                .getPokemons()
                .entrySet()
                .stream()
                .filter(pokemonEntry -> (lessonResult.get().getScore() - pokemonEntry.getKey().longValue()) >= 0)
                .max(new Comparator<Map.Entry<Long, Pokemon>>() {
                    @Override
                    public int compare(Map.Entry<Long, Pokemon> o1, Map.Entry<Long, Pokemon> o2) {
                        return o1.getKey().intValue() - o2.getKey().intValue();
                    }
                })
                .get()
                .getValue();

        Logger.debug(String.format("User %s get pokemon for lesson %s with level %s | Find out %s",
                                   username, lessonId, lessonResult.get().getScore(), resultPokemon));

        return resultPokemon;
    }

}
