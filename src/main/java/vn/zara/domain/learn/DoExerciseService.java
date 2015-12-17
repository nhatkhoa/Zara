/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 7:50 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.zara.domain.common.exception.LessonNotExisted;
import vn.zara.domain.common.exception.ZaraException;
import vn.zara.domain.lesson.LessonRepository;
import vn.zara.domain.util.SecurityUtil;

import java.util.EventObject;

@Service
@Transactional(readOnly = true)
public class DoExerciseService {
    protected static Logger Logger = LoggerFactory.getLogger(DoExerciseService.class);

    private final DoExerciseRepository doExerciseRepository;
    private final LessonResultService lessonResultService;
    private final LessonRepository lessonRepository;
    @Autowired
    public DoExerciseService(DoExerciseRepository doExerciseRepository,
                             LessonResultService lessonResultService,
                             LessonRepository lessonRepository){
        this.doExerciseRepository = doExerciseRepository;
        this.lessonResultService = lessonResultService;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public void addNewResultOfExercise(String lessonId, String exerciseId, int score){
        String username = SecurityUtil.getCurrentLogin();

        // -- Check if this lesson existed
        if(lessonRepository.findOne(lessonId) == null){
            String message = String.format("Lesson %s is not existed.", lessonId);
            Logger.debug(message);
            throw new LessonNotExisted(message);
        }

        Logger.debug(String.format("Add new exercise result for %s : %s %s %s",username,lessonId, exerciseId, score));

        DoExercise doExercise = new DoExercise();
        doExercise.setUsername(username);
        doExercise.setLesson(lessonId);
        doExercise.setExercise(exerciseId);
        doExercise.setScore(score);
        doExerciseRepository.save(doExercise);

        // --- Update sum score of this lesson
        lessonResultService.updateLessonScore(lessonId);
    }

}
