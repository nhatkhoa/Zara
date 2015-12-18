package vn.zara.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.zara.domain.lesson.Question;

/**
 * Created by Vo on 18-Dec-15.
 */

@Data
public class QuestionResponse extends Question {

    private  int Score;

    public QuestionResponse castQuestionToQuestionResponse(Question q){
        QuestionResponse qr = new QuestionResponse();
        qr.setId(q.getId());
        qr.setQuestion(q.getQuestion());
        qr.setOptions(q.getOptions());
        qr.setAnswers(q.getAnswers());
        qr.setLevel(q.getLevel());
        qr.setScore(q.getLevel().getScore());
        qr.setCreatedDate(q.getCreatedDate());
        qr.setLastModifiedDate(q.getLastModifiedDate());
        return qr;
    }

}
