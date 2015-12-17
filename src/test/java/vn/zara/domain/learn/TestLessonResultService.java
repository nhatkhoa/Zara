/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;
import vn.zara.domain.common.exception.NotEnoughPokemon;
import vn.zara.domain.common.exception.ZaraException;
import vn.zara.domain.pokemon.Pokemon;
import vn.zara.domain.pokemon.PokemonGroupRepository;
import vn.zara.domain.pokemon.PokemonInitializeDatabase;
import vn.zara.domain.pokemon.PokemonService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestLessonResultService {
    protected static Logger Logger = LoggerFactory.getLogger(TestLessonResultService.class);

    @Autowired
    private LessonResultService lessonResultService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private DoExerciseService doExerciseService;

    @Autowired
    private DoExerciseRepository doExerciseRepository;

    @Autowired
    private LessonResultRepository lessonResultRepository;

    @Autowired
    private PokemonGroupRepository pokemonGroupRepository;

    @Autowired
    private PokemonInitializeDatabase pokemonInitializeDatabase;


    @Test(expected = NotEnoughPokemon.class)
    public void testUpdateLessonScore(){
        pokemonInitializeDatabase.seedData();
        // --- Test for case normal
        doExerciseService.addNewResultOfExercise("testLessonId", "TestExerciseId", 10);
        Pokemon pokemon = lessonResultService.getPokemonByLesson("testLessonId");
        Assert.assertThat(pokemon, CoreMatchers.notNullValue());
        Assert.assertThat(lessonResultService.getLessonScore("testLessonId"), CoreMatchers.is(new Long(10)));

        // --- Test random pokemon
        doExerciseService.addNewResultOfExercise("testLessonId1", "TestExerciseId", 10);
        Pokemon pokemon1 = lessonResultService.getPokemonByLesson("testLessonId1");
        // --- Pokemon in this case is different to above cas
        Assert.assertThat(pokemon1, CoreMatchers.not(pokemon));

        doExerciseService.addNewResultOfExercise("testLessonId2", "TestExerciseId", 10);
        Pokemon pokemon2 = lessonResultService.getPokemonByLesson("testLessonId2");
        Assert.assertThat(pokemon2,CoreMatchers.not(pokemon));
        Assert.assertThat(pokemon2,CoreMatchers.not(pokemon1));

        // --- Test for not enough pokemon
        doExerciseService.addNewResultOfExercise("testLessonId3", "TestExerciseId", 10);
        Assert.assertThat(lessonResultService.getPokemonByLesson("testLessonId3"),
                          CoreMatchers.nullValue());


    }
    @After
    public void cleanTest(){
        lessonResultRepository.deleteAll();
        doExerciseRepository.deleteAll();
        pokemonGroupRepository.deleteAll();
    }

}
