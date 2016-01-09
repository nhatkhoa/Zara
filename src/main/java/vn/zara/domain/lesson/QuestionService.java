/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 6:53 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.zara.domain.learn.DoExercise;
import vn.zara.domain.learn.DoExerciseRepository;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.web.dto.QuestionResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionService {
    protected static Logger Logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private DoExerciseRepository doExerciseRepository;

    //TODO: Get random question against score of student in respective exercise
    public List<Question> getRandomQuestionForExercise(String exerciseId) {

        return null;
    }

}
