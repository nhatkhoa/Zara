/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:23 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "questions")
public class Question extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(Question.class);

    public enum LEVEL {
        BASIC(0),
        MEDIUM(150),
        HIGH(300);

        private int score;

        LEVEL(int score) {
            this.score = score;
        }

        public String getLevel() {
            return name();
        }

        public int getScore() {
            return this.score;
        }

        public List<LEVEL> getList(int score){
            List<LEVEL> temp = new ArrayList<>();
            for (LEVEL level : LEVEL.values()){
                if(level.getScore() <= score){
                    temp.add(level);
                }
            }
            return temp;
        }

    }

    private String question;
    private String[] options;
    private Object[] answers;
    private LEVEL level;
}
