/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:44 PM at ZaraApi.
 */

package vn.zara.domain.learn;

import lombok.Data;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.lesson.Lesson;
import vn.zara.domain.lesson.Question;

import java.util.Map;

@Data
@Document(collection = "do_exercises")
public class DoExercise {
    protected static Logger Logger = LoggerFactory.getLogger(DoExercise.class);

    @Id
    private String username;

    @Indexed
    private String lesson;

    private String exercise;

    private int score;

    @CreatedDate
    private DateTime createdDate;

}
