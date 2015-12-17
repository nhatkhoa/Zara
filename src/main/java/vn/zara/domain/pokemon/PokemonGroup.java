/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 5:52 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "pokemon_groups")
@Data
public class PokemonGroup extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(PokemonGroup.class);

    @DBRef
    private Map<Long, Pokemon> pokemons = new HashMap();
}
