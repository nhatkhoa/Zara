/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:23 PM at ZaraApi.
 */

package vn.zara.domain.lesson;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "lessons")
public class Lesson extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(Lesson.class);

    private String name;
    private String description;
    private String theory;

    @DBRef
    private List<Exercise> exercises = new ArrayList();
}
