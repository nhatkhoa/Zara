/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestPokemonService {
    protected static Logger Logger = LoggerFactory.getLogger(TestPokemonService.class);

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokemonGroupRepository pokemonGroupRepository;

    @Test
    public void testCreateGroupPokemon(){
        Map<Long, String> testMap = new HashMap<>();
        testMap.put(new Long(0),"Venusaur");
        testMap.put(new Long(1000),"Bulbasaur");
        testMap.put(new Long(2000),"Ivysaur");
        PokemonGroup group = pokemonService.createGroupPokemon(testMap);

        PokemonGroup groupTest = pokemonGroupRepository.findOne(group.getId());

        Assert.assertThat(groupTest, CoreMatchers.notNullValue());
        Assert.assertThat(groupTest.getPokemons().size(), CoreMatchers.is(3));
        Assert.assertThat(groupTest.getPokemons().get(new Long(0)).getName(), CoreMatchers.containsString("Venusaur"));
        Assert.assertThat(groupTest.getPokemons().get(new Long(1000)).getName(), CoreMatchers.containsString("Bulbasaur"));
        Assert.assertThat(groupTest.getPokemons().get(new Long(2000)).getName(), CoreMatchers.containsString("Ivysaur"));

    }

    @After
    public void emptyDataOfTest(){
        pokemonGroupRepository.deleteAll();
    }
}
