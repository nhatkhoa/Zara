/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 10:07 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonDataInitialize {
    protected static Logger Logger = LoggerFactory.getLogger(LessonDataInitialize.class);

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Lesson createLesson(String title) {
        val lesson = new Lesson();
        lesson.setName(String.format("Bài học: %s", title));
        lesson.setDescription("Bài học mẫu");
        lesson.setTheory("Đây được nhấn mạnh chỉ là mang tính chất minh họa chứ không có làm được gì cả.");
        return lesson;
    }

    public Exercise createExercise(String title) {
        val exercise = new Exercise();
        exercise.setTitle(title);
        exercise.setDescription("Bài tập này chán lắm vào làm gì?");
        return exercise;
    }

    public List<Exercise> addExerciseToLesson() {
        val list = new ArrayList<Exercise>();
        for (int i = 0; i < 10; i++) {
            val exercise = createExercise(String.format("Bài tập cấp %s", i));
            list.add(exercise);
        }
        return list;
    }

    public  List<Question> createRandomQuestions() {
        List<Question> quests = new ArrayList();

        for (int i = 0; i < 20; i++) {
            val question = new Question();
            question.setQuestion(String.format("Cau %s: Bạn có biết bảng nhân 3 là gì không (Level BASIC) ?",i));
            question.setOptions(new String[]{"Là bảng nhân", "Là bảng cửu chương", "Không biết nữa"});
            question.setAnswers(new Integer[]{0});
            question.setLevel(Question.LEVEL.BASIC);
            quests.add(question);
        }

        for (int i = 0; i < 20; i++) {
            val question = new Question();
            question.setQuestion(String.format("Cau %s: Bạn có biết bảng nhân 3 là gì không (Level MEDIUM) ?",i));
            question.setOptions(new String[]{"Là bảng nhân", "Là bảng cửu chương", "Không biết nữa"});
            question.setAnswers(new Integer[]{0});
            question.setLevel(Question.LEVEL.MEDIUM);
            quests.add(question);
        }
        for (int i = 0; i < 20; i++) {
            val question = new Question();
            question.setQuestion(String.format("Cau %s: Bạn có biết bảng nhân 3 là gì không (Level HIGH) ?",i));
            question.setOptions(new String[]{"Là bảng nhân", "Là bảng cửu chương", "Không biết nữa"});
            question.setAnswers(new Integer[]{0});
            question.setLevel(Question.LEVEL.HIGH);
            quests.add(question);
        }

        questionRepository.save(quests);
        return quests;
    }

    public void seedData(){
        // --- Empty data first
        exerciseRepository.deleteAll();
        lessonRepository.deleteAll();

        // --- create instance of LessonForListing
        Lesson lesson = createLesson("Bảng Nhân 3");
        lessonRepository.save(lesson);

        // --- create random 10 exercises
        List<Exercise> exerciseRandom = addExerciseToLesson();
        exerciseRepository.save(exerciseRandom);
        exerciseRandom.forEach(exercise -> {
            exercise.getQuestions().addAll(createRandomQuestions());
        });
        exerciseRepository.save(exerciseRandom);

        // ---- add exercises for lesson
        lesson.getExercises().addAll(exerciseRandom);
        lessonRepository.save(lesson);

        lessonRepository.save(createLesson("Bảng Nhân 4"));
        lessonRepository.save(createLesson("Đọc thời gian"));
        lessonRepository.save(createLesson("Phép nhân 3 chữ số"));
        lessonRepository.save(createLesson("Phép chia 2 chữ số"));
        lessonRepository.save(createLesson("Phép công 3 chữ số"));
    }
}
