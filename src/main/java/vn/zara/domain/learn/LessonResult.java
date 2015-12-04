/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 8:38 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;
import vn.zara.domain.pokemon.Pokemon;
import vn.zara.domain.pokemon.PokemonGroup;

@Data
@Document(collection = "lesson_results")
public class LessonResult extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(LessonResult.class);

    private String       lessonId;
    private String       username;
    private Long         score;
    @DBRef
    private PokemonGroup pokemon;

}
