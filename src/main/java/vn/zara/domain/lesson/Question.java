/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:23 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "questions")
public class Question {
    protected static Logger Logger = LoggerFactory.getLogger(Question.class);

    public enum LEVEL{
        BASIC(10),
        NORMAL(20),
        MEDIUM(30),
        HIGH(40),
        ADVANCED(50);

        private int score;
        LEVEL(int score){
            this.score = score;
        }
        public String getLevel(){return name();}
        public int getScore(){return this.score;}

    }

    @Id
    private String id;
    private String question;
    private String[] options;
    private Object[] answers;
}
