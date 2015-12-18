package vn.zara.domain.question;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;
import vn.zara.domain.lesson.Question;
import vn.zara.domain.lesson.QuestionService;
import vn.zara.web.dto.QuestionResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vo on 17-Dec-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestQuestionService {

    @Autowired
    QuestionService questionService;

    @Test
    public void test(){
        List<Question> listQuestion = questionService.getRandomQuestionForExercise("56726a2127320dcb8fb0d67b");
    }

}
