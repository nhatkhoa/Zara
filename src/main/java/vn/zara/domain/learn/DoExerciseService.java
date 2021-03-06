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
import vn.zara.domain.common.exception.ExerciseNotExisted;
import vn.zara.domain.common.exception.LessonNotExisted;
import vn.zara.domain.common.exception.ZaraException;
import vn.zara.domain.lesson.*;
import vn.zara.domain.util.SecurityUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class DoExerciseService {
    protected static Logger Logger = LoggerFactory.getLogger(DoExerciseService.class);

    private final DoExerciseRepository doExerciseRepository;
    private final LessonResultService lessonResultService;
    private final LessonRepository lessonRepository;
    private final ExerciseRepository exerciseRepository;
    @Autowired
    public DoExerciseService(DoExerciseRepository doExerciseRepository,
                             LessonResultService lessonResultService,
                             LessonRepository lessonRepository,
                             ExerciseRepository exerciseRepository){
        this.doExerciseRepository = doExerciseRepository;
        this.lessonResultService = lessonResultService;
        this.lessonRepository = lessonRepository;
        this.exerciseRepository = exerciseRepository;
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

    public List<Question> getRandomQuestions(String exerciseId){
        Exercise exercise = exerciseRepository.findOne(exerciseId);
        if(exercise == null)
            throw new ExerciseNotExisted("Can not get random question for not existed exercise!");

        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Number do exercise %s - %s",
                exercise.getTitle(), doExerciseRepository.findByUsernameAndExercise(username, exerciseId).count()));

        long sumScore = doExerciseRepository
                .findByUsernameAndExercise(username, exerciseId)
                .mapToLong(doExercise -> doExercise.getScore())
                .sum();

        Logger.debug(String.format("Sum score of exercise %s: %s", exercise.getTitle(), sumScore));

        List<Question.LEVEL> levels = Question.LEVEL.getList((int)sumScore);
        Logger.debug("- List of range level for random question:");
        levels.forEach(level -> Logger.debug(String.format("Level %s : score is %s", level.name(), level.getScore())));

        List<Question> questions = exercise.getQuestions()
                .stream().filter(question -> levels.contains(question.getLevel()))
                .collect(Collectors.toList());

        Collections.shuffle(questions);

        List<Question> random10Questions = questions.stream().limit(10).collect(Collectors.toList());

        return random10Questions;

    }

}
