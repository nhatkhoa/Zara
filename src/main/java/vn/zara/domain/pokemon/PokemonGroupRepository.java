package vn.zara.domain.pokemon;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:07 AM at ZaraApi.
 */
public interface PokemonGroupRepository extends MongoRepository<PokemonGroup, String> {
}
