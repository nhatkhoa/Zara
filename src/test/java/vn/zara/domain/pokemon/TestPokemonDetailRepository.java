/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.domain.pokemon;

import org.hamcrest.CoreMatchers;
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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestPokemonDetailRepository {
    protected static Logger Logger = LoggerFactory.getLogger(TestPokemonDetailRepository.class);

    @Autowired
    private PokemonDetailRepository pokemons;
    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    public void testFindAll() throws IOException {
        List<PokemonDetail> pokes = pokemons.findAll();
        Assert.assertThat(pokes, CoreMatchers.anything());
    }

    @Test
    public void testFindOnePokemonDetailByName() {
        Optional<PokemonDetail> pokemonDetail = pokemons.findOneByName("Ivysaur");
        Assert.assertThat(pokemonDetail.get(), CoreMatchers.notNullValue());
        Assert.assertThat(pokemonDetail.get().getId(), CoreMatchers.is("565f85255d7580838fd504e5"));
    }

}
