/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 05/12/2015 - 8:10 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PokemonInitializeDatabase {
    protected static Logger Logger = LoggerFactory.getLogger(PokemonInitializeDatabase.class);
    private final PokemonService pokemonService;

    @Autowired
    public PokemonInitializeDatabase(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public void seedData(){
        Map<Long, String> testMap = new HashMap<>();
        testMap.put(new Long(0),"Venusaur");
        testMap.put(new Long(1000),"Bulbasaur");
        testMap.put(new Long(2000),"Ivysaur");
        pokemonService.createGroupPokemon(testMap);

        Map<Long, String> testMap1 = new HashMap<>();
        testMap1.put(new Long(0),"Charmander");
        testMap1.put(new Long(1500),"Charmeleon");
        testMap1.put(new Long(3000),"Charizard");
        pokemonService.createGroupPokemon(testMap);

        Map<Long, String> testMap2 = new HashMap<>();
        testMap2.put(new Long(0),"Oddish");
        testMap2.put(new Long(1000),"Gloom");
        testMap2.put(new Long(2000),"Vileplume");
        pokemonService.createGroupPokemon(testMap);
    }
}
