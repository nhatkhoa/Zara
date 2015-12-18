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

        Exercise exercise = exerciseRepository.findOne(exerciseId);
        Assert.notNull(exercise);

        Logger.debug(String.format("Get random question for exercise %s: got %s item",
                exerciseId, exercise.getQuestions().size()));

        //co id cua bai tap
        //lay diem cao nhat cua bt co da lam (1 bai co the lam nhieu lam)
        //diem<60 load 10 cau basic
        //60<= diem < 80 load 8 cau basic + 2 cau normal
        //...

        Stream<DoExercise> listResult = doExerciseRepository.findByUsernameAndExercise(SecurityUtil.getCurrentLogin(), exerciseId);
        Optional<DoExercise> obj = listResult.max(new Comparator<DoExercise>() {
            @Override
            public int compare(DoExercise o1, DoExercise o2) {
                return o1.getScore() - o2.getScore();
            }
        });
        int maxScore = obj.isPresent() ? obj.get().getScore() : 0;

        List<Question> listQuestion;
        switch (getLevelDetail(maxScore)) {
            case "Basic":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.BASIC, null);
                break;
            case "BasicAndNormal":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.BASIC, Question.LEVEL.NORMAL);
                break;
            case "Normal":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.NORMAL, null);
                break;
            case "NormalAndMedium":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.NORMAL, Question.LEVEL.MEDIUM);
                break;
            case "Medium":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.MEDIUM, null);
                break;
            case "MediumAndHigh":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.MEDIUM, Question.LEVEL.HIGH);
                break;
            case "High":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.HIGH, null);
                break;
            case "HighAndAdvanced":
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.HIGH, Question.LEVEL.ADVANCED);
                break;
            default:
                listQuestion = getRandomQuestionForExerciseWithLevel(exercise, Question.LEVEL.ADVANCED, null);
                break;
        }
        for (Question q : listQuestion){
            Logger.debug(q.getQuestion());
        }
        return listQuestion;
    }

    private String getLevelDetail(int maxScore) {
        if (maxScore < Question.LEVEL.BASIC.getScore() * 6)
            return "Basic";
        if (maxScore < Question.LEVEL.BASIC.getScore() * 8)
            return "BasicAndNormal";
        if (maxScore < Question.LEVEL.NORMAL.getScore() * 6)
            return "Normal";
        if (maxScore < Question.LEVEL.NORMAL.getScore() * 8)
            return "NormalAndMedium";
        if (maxScore < Question.LEVEL.MEDIUM.getScore() * 6)
            return "Medium";
        if (maxScore < Question.LEVEL.MEDIUM.getScore() * 8)
            return "MediumAndHigh";
        if (maxScore < Question.LEVEL.HIGH.getScore() * 6)
            return "High";
        if (maxScore < Question.LEVEL.HIGH.getScore() * 8)
            return "HighAndAdvanced";
        return "Advanced";
    }

    private List<Question> getRandomQuestionForExerciseWithLevel(Exercise exercise, Question.LEVEL level1, Question.LEVEL level2) {
        List<Question> listQuestion = new ArrayList<Question>();
        List<Question> qs = exercise.getQuestions().stream()
                .filter(q -> level2 == null ? q.getLevel().equals(level1) :
                        q.getLevel().equals(level1) || q.getLevel().equals(level2))
                .collect(Collectors.toList());
        int i = 1;
        int l1 = 0;
        int l2 = 0;
        while (i <= 10) {
            int flat = 0;
            Random ran = new Random();
            int a = ran.nextInt(qs.size());
            for (Question question : listQuestion) {
                if (qs.get(a).equals(question)) {
                    flat = 1;
                }
            }
            if (flat == 0) {
                if (level2 == null) {
                    listQuestion.add(qs.get(a));
                    i++;
                } else {
                    if (qs.get(a).getLevel().equals(level1) && l1 < 8) {
                        l1++;
                        listQuestion.add(qs.get(a));
                        i++;
                    }
                    if (qs.get(a).getLevel().equals(level2) && l2 < 2) {
                        l2++;
                        listQuestion.add(qs.get(a));
                        i++;
                    }
                }
            }
        }
        return listQuestion;
    }
}
