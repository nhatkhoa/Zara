/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.val;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
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
public class TestQuestionRepository {
    protected static Logger Logger = LoggerFactory.getLogger(TestQuestionRepository.class);

    @Autowired
    private LessonRepository lessons;
    @Autowired
    private ExerciseRepository exercises;
    @Autowired
    private QuestionRepository questions;

    @Test
    public void testFindAll() {

    }


    @Ignore
    @After
    public void cleanDatabase() {
        lessons.deleteAll();
    }

}
