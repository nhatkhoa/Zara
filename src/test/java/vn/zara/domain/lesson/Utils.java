/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 6:28 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    protected static Logger Logger = LoggerFactory.getLogger(Utils.class);
    public static Lesson createLesson(String title) {
        val lesson = new Lesson();
        lesson.setName(String.format("Bài học %s", title));
        lesson.setDescription("Có khùng mới học bài này :v");
        lesson.setTheory("Bảng nhân 3 thì lấy bảng cửu chương ra mà xem, vào đây làm gì?");
        return lesson;
    }

    public static Exercise createExercise(String title) {
        val exercise = new Exercise();
        exercise.setTitle(title);
        exercise.setDescription("Bài tập này chán lắm vào làm gì?");
        return exercise;
    }

    public static List<Exercise> addExerciseToLesson() {
        val list = new ArrayList<Exercise>();
        for (int i = 0; i < 10; i++) {
            val exercise = createExercise(String.format("Bài tập dành cho gà con cấp %s", i));
            list.add(exercise);
        }
        return list;
    }

    public static List<Question> createRandomQuestions() {
        List<Question> quests = new ArrayList();

        for (int i = 0; i < 10; i++) {
            val question = new Question();
            question.setQuestion("Bạn có biết bảng nhân 3 là gì không?");
            question.setOptions(new String[]{"Là bảng nhân", "Là bảng cửu chương", "Không biết nữa"});
            question.setAnswers(new Integer[]{0});
            quests.add(question);
        }
        return quests;
    }
}
