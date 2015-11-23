/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:23 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "exercises")
public class Exercise {
    protected static Logger Logger = LoggerFactory.getLogger(Exercise.class);

    @Id
    private String id;

    private String title;
    private String description;

    @JsonIgnore
    @DBRef
    private List<Question> questions = new ArrayList();

}
