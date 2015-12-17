/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 7:35 AM at ZaraApi.
 */

package vn.zara.infras.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.zara.domain.common.exception.ExerciseNotExisted;
import vn.zara.domain.learn.DoExerciseService;
import vn.zara.domain.lesson.Exercise;
import vn.zara.domain.lesson.ExerciseRepository;
import vn.zara.domain.lesson.Question;
import vn.zara.web.dto.LessonDetail;

import java.util.List;

@Service
public class ProcessDoExerciseService {
    protected static Logger Logger = LoggerFactory.getLogger(ProcessDoExerciseService.class);

    private final DoExerciseService     doExerciseService;
    private final DataForDisplayService dataForDisplayService;
    private final ExerciseRepository    exerciseRepository;

    @Autowired
    public ProcessDoExerciseService(DoExerciseService doExerciseService,
                                    DataForDisplayService dataForDisplayService,
                                    ExerciseRepository exerciseRepository) {
        this.doExerciseService = doExerciseService;
        this.dataForDisplayService = dataForDisplayService;
        this.exerciseRepository = exerciseRepository;
    }

    public LessonDetail updateExerciseScore(String lessonId, String exerciseId, int score) {
        Logger.debug(String.format("Request update score for exercise %s with score is %s",
                                   exerciseId, score));
        doExerciseService.addNewResultOfExercise(lessonId, exerciseId, score);
        return dataForDisplayService.getLessonDetail(lessonId);
    }

    public List<Question> getRandomQuestions(String exerciseId) {
        Exercise exercise = exerciseRepository.findOne(exerciseId);

        if(exercise == null){
            String message = String.format("Exercise %s is not existed.", exerciseId);
            Logger.debug(message);
            throw new ExerciseNotExisted(message);
        }

        return null;
    }
}
