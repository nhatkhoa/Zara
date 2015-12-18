package vn.zara.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.zara.domain.statistic.StatisticService;

/**
 * Created by Vo on 19-Dec-15.
 */

@RestController
@RequestMapping("api/statistic")
public class StatisticRest {
    protected static Logger Logger = LoggerFactory.getLogger(StatisticRest.class);

    private final StatisticService statisticService;

    @Autowired
    public StatisticRest(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public void autoStatistic(@PathVariable("username") String username) {
        statisticService.createStatisticForUsername(username);
    }

}
