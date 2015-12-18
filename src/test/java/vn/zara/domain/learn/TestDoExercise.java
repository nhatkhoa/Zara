package vn.zara.domain.learn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.zara.ZaraApiApplication;
import vn.zara.domain.statistic.StatisticService;
import vn.zara.domain.util.SecurityUtil;

import java.util.*;

/**
 * Created by Vo on 18-Dec-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZaraApiApplication.class)
@WebAppConfiguration
public class TestDoExercise {
    protected static org.slf4j.Logger Logger = LoggerFactory.getLogger(TestDoExercise.class);

    @Autowired
    DoExerciseRepository doExerciseRepository;

    @Autowired
    StatisticService statisticService;

    @Test
    public void testInsert() {
        List<DoExercise> doExercises = new ArrayList<DoExercise>();
        for (int i = 0; i < 5; i++) {
            DoExercise doExercise = new DoExercise();
            doExercise.setUsername(SecurityUtil.getCurrentLogin());
            doExercise.setLesson("56726a2227320dcb8fb0da6d");
            doExercise.setExercise("56726a2127320dcb8fb0d67b");
            doExercise.setScore(new Random().nextInt(40)*10);
            doExercises.add(doExercise);
        }
        for (int i = 0; i < 5; i++) {
            DoExercise doExercise = new DoExercise();
            doExercise.setUsername(SecurityUtil.getCurrentLogin());
            doExercise.setLesson("56726a2227320dcb8fb0da6d");
            doExercise.setExercise("56726a2127320dcb8fb0d67c");
            doExercise.setScore(new Random().nextInt(40)*10);
            doExercises.add(doExercise);
        }

        doExerciseRepository.save(doExercises);
    }

    @Test
    public void test(){
        statisticService.createStatistic();
    }
}
