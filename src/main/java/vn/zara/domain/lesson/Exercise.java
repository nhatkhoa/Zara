/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:23 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "exercises")
public class Exercise extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(Exercise.class);

    private String title;
    private String description;

    @JsonIgnore
    @DBRef
    private List<Question> questions = new ArrayList();

}
