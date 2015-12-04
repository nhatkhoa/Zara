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
public class TestQuestionRepository {
    protected static Logger Logger = LoggerFactory.getLogger(TestQuestionRepository.class);

    @Autowired
    private LessonRepository lessons;
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
        questions.deleteAll();
    }
    private List<Question> createRandomQuestionToExercise(){
        List<Question> randomQuestions = Utils.createRandomQuestions();
        questions.save(randomQuestions);

        this.trackExercise.getQuestions().addAll(randomQuestions);
        exercises.save(this.trackExercise);
        return randomQuestions;
    }
    @Test
    public void testAddQuestionToExercise(){
        List<Question> randomQuestions = createRandomQuestionToExercise();

        Exercise exercise = exercises.findOne(trackExercise.getId());
        List<Question> questionResults = exercise.getQuestions();

        Assert.assertThat(questionResults.size(), CoreMatchers.is(randomQuestions.size()));
        Assert.assertThat(
                questionResults,
                CoreMatchers.hasItems(
                        randomQuestions.get(randomQuestions.size() - randomQuestions.size()/2),
                        randomQuestions.get(randomQuestions.size() - 1)));
    }

    @Test
    public void testFindAll() {

        List<Question> randomQuestions = createRandomQuestionToExercise();

        val ques = questions.findAll();

        Assert.assertThat(ques.size(), CoreMatchers.is(randomQuestions.size()));
        Assert.assertThat(ques, CoreMatchers.hasItem(randomQuestions.get(0)));
    }

    @After
    public void cleanExercise(){
        exercises.delete(this.trackExercise);
    }

}
