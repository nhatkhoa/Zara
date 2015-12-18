package vn.zara.web.dto;

import lombok.Getter;
import lombok.Setter;
import vn.zara.domain.lesson.Question;

/**
 * Created by Vo on 18-Dec-15.
 */

public class QuestionResponse extends Question {

    @Getter
    @Setter
    private  int Score;
}
