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
import vn.zara.domain.pokemon.PokemonGroup;
import vn.zara.domain.pokemon.PokemonService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestLessonResultRepository {
    protected static Logger Logger = LoggerFactory.getLogger(TestLessonResultRepository.class);

    @Autowired
    private LessonResultRepository lessonResults;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private DoExerciseService doExerciseService;

    @Test
    public void testFindAll() throws IOException {
        LessonResult result = new LessonResult();
        result.setUsername("Nhatkhoa");
        result.setLessonId("afsfwrwq342r2rfavdsfadf");
        result.setScore(new Long(21213));
        lessonResults.save(result);

        Assert.assertThat(lessonResults.findAll(), CoreMatchers.anything());
        Assert.assertThat(lessonResults.findAll().size(), CoreMatchers.not(0));
    }

    @Test
    public void testFindOneLessonResultByLessonIdAndUsername() {
        // --- Insert new lesson result
        LessonResult result = new LessonResult();
        result.setUsername("Nhatkhoa");
        result.setLessonId("1312312dsadasxasdascq3e");
        result.setScore(new Long(435343));
        lessonResults.save(result);

        Optional<LessonResult> lessonResult = lessonResults.findOneByLessonIdAndUsername("1312312dsadasxasdascq3e", "Nhatkhoa");
        Assert.assertThat(lessonResult.get(), CoreMatchers.notNullValue());
        Assert.assertThat(lessonResult.get().getScore(), CoreMatchers.is(new Long(435343)));
        Assert.assertThat(lessonResult.get().getCreatedDate(), CoreMatchers.notNullValue());
    }

    @After
    public void removeAllData() {
        // --- Empty all data in collection before run next test
        lessonResults.deleteAll();
    }

}
