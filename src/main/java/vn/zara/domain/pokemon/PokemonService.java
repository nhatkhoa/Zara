/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 9:21 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PokemonService {
    protected static Logger Logger = LoggerFactory.getLogger(PokemonService.class);

    private PokemonRepository      pokemonRepository;
    private PokemonGroupRepository pokemonGroups;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository, PokemonGroupRepository pokemonGroups) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonGroups = pokemonGroups;
    }

    public PokemonGroup createGroupPokemon(Map<Long, String> pokemons) {
        PokemonGroup group = new PokemonGroup();

        Logger.debug(String.format("Create new group pokemon size %s with first item %s",
                                   pokemons.size(),
                                   pokemons.values().iterator().next()));

        Map<Long, Pokemon> pokemonMap = pokemons
                .entrySet()
                .stream()
                .collect(Collectors.toConcurrentMap(pokemonEntry -> pokemonEntry.getKey().longValue(),
                                                    pokemonEntry -> pokemonRepository
                                                            .findOneByName(pokemonEntry.getValue()).get()));
        group.setPokemons(pokemonMap);
        pokemonGroups.save(group);

        return group;
    }
}
