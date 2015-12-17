/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.val;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestQuestionService {
    protected static Logger Logger = LoggerFactory.getLogger(TestQuestionService.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExerciseRepository exercises;
    @Autowired
    private QuestionRepository questions;

    private Exercise trackExercise = null;

    @Before
    public void initData(){
        val exercise = Utils.createExercise("Bài tập cho test questions");
        exercises.save(exercise);
        this.trackExercise = exercise;
    }

    private List<Question> createRandomQuestionToExercise(){
        List<Question> randomQuestions = Utils.createRandomQuestions();
        questions.save(randomQuestions);

        this.trackExercise.getQuestions().addAll(randomQuestions);
        exercises.save(this.trackExercise);
        return randomQuestions;
    }

    @Test
    public void testGetRandomQuestionForExercise(){
        List<Question> randomQuestions = createRandomQuestionToExercise();
        List<Question> questionRandom = questionService.getRandomQuestionForExercise(trackExercise.getId());

        Assert.assertThat(questionRandom, CoreMatchers.notNullValue());
        Assert.assertThat(questionRandom.size(), CoreMatchers.is(10));
    }


    @Ignore
    @After
    public void cleanDatabase() {

        questions.deleteAll();
        exercises.delete(this.trackExercise);
    }

}
