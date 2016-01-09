package vn.zara.domain.learn;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;
import vn.zara.domain.common.exception.ExerciseNotExisted;
import vn.zara.domain.lesson.Question;
import vn.zara.domain.statistic.StatisticService;
import vn.zara.domain.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestDoExerciseService {
    protected static org.slf4j.Logger Logger = LoggerFactory.getLogger(TestDoExerciseService.class);

    @Autowired
    private DoExerciseService doExerciseService;

    @Test(expected = ExerciseNotExisted.class)
    public void testGetRandomQuestions(){

        List<Question> questions = doExerciseService.getRandomQuestions("5674a9f2890cbff560201e29");
        Assert.assertThat(questions.size(), CoreMatchers.not(0));

        // Test exception
        doExerciseService.getRandomQuestions("12j98ufj34918je1231289dS");

    }
}
