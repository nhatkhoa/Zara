/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 6:53 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    protected static Logger Logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    //TODO: Get random question against score of student in respective exercise
    public List<Question> getRandomQuestionForExercise(String exerciseId){

        Exercise exercise = exerciseRepository.findOne(exerciseId);
        Assert.notNull(exercise);

        Logger.debug(String.format("Get random question for exercise %s: got %s item",
                                   exerciseId, exercise.getQuestions().size()));
        return exercise
                .getQuestions()
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
