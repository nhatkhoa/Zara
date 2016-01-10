/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 8:44 PM at ZaraApi.
 */

package vn.zara.domain.learn;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

@Data
@Document(collection = "do_exercises")
public class DoExercise extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(DoExercise.class);

    @Indexed
    private String username;

    @Indexed
    private String lesson;

    @Indexed
    private String exercise;

    private int score;

}
