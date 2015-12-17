/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 6:09 AM at ZaraApi.
 */

package vn.zara.infras.dao;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;
import vn.zara.domain.lesson.LessonRepository;
import vn.zara.web.dto.LessonForListing;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestDataForDisplay {
    protected static Logger Logger = LoggerFactory.getLogger(TestDataForDisplay.class);

    @Autowired
    private DataForDisplayService dataForDisplayService;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void testGetAllLessonForIndex(){
        Set<LessonForListing> listLesson = dataForDisplayService.getAllLessonForIndex();
        Assert.assertThat(listLesson.size(), CoreMatchers.not(0));

        // --- There are only one kind pokemon that's "none"
        Assert.assertThat(listLesson
                                  .stream()
                                  .map(p-> p.getPokemon())
                                  .collect(Collectors.toSet()).size(),
                          CoreMatchers.is(1));

        Assert.assertThat(listLesson
                                  .stream()
                                  .map(p-> p.getPokemon())
                                  .collect(Collectors.toList()),
                          CoreMatchers.hasItem("none"));

        Assert.assertThat(listLesson.size(), CoreMatchers.is(lessonRepository.findAll().size()));

        Assert.assertThat(listLesson
                                  .stream()
                                  .map(p-> p.getTitle())
                                  .collect(Collectors.toList()),
                          CoreMatchers.is(lessonRepository
                                                  .findAll()
                                                  .stream()
                                                  .map(p -> p.getName())
                                                  .collect(Collectors.toList())));
    }

}
