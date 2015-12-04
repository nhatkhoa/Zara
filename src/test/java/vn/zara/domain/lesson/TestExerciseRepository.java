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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestExerciseRepository {
    protected static Logger Logger = LoggerFactory.getLogger(TestExerciseRepository.class);

    @Autowired
    private LessonRepository lessons;
    @Autowired
    private ExerciseRepository exercises;
    @Autowired
    private QuestionRepository questions;

    @Test
    public void testFindAll() {
        for (int i = 0; i < 3; i++) {
            val lesson = Utils.createLesson(String.format("Làm bảng nhân " + (i + 2)));
            lessons.save(lesson);
        }
        Assert.assertThat(lessons.findAll().size(), CoreMatchers.is(3));
    }

    @Test
    public void testFindOneById() {
        val lesson = Utils.createLesson("Bài Học Test");
        lessons.save(lesson);

        val lessonResult = lessons.findOne(lesson.getId());
        Assert.assertThat(lessonResult, CoreMatchers.notNullValue());
        Assert.assertThat(lessonResult.getName(), CoreMatchers.containsString("Bài Học Test"));
    }

    @Test
    public void testAddExerciseToLesson() {
        // --- create instance of LessonForListing
        val lesson = Utils.createLesson("Bài Học Test");
        lessons.save(lesson);

        // --- create random 10 exercises
        val exerciseRandom = Utils.addExerciseToLesson();
        exercises.save(exerciseRandom);

        // --- add 10 random exercises to lesson
        lesson.getExercises().addAll(exerciseRandom);
        lessons.save(lesson);

        val lessonResult = lessons.findOne(lesson.getId());
        Assert.assertThat(lessonResult, CoreMatchers.notNullValue());
        Assert.assertThat(lessonResult.getExercises().size(), CoreMatchers.is(10));
        Assert.assertThat(lessonResult.getExercises().get(0).getTitle(), CoreMatchers.containsString("Bài tập dành cho gà con cấp"));

    }


    @Before
    public void cleanDatabase() {
        lessons.deleteAll();
    }

}
