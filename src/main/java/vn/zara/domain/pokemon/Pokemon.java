/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 5:57 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

@Document(collection = "pokemons")
@Data
@ToString
public class Pokemon extends AbstractCollection{
    protected static Logger Logger = LoggerFactory.getLogger(Pokemon.class);

    @Indexed
    private String name;
    private String height;
    private String ThumbnailAltText;
    private String ThumbnailImage;
    private String[] type;
    private String[] abilities;
    private String[] weakness;
}
