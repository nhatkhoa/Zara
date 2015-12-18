package vn.zara.domain.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.zara.domain.statistic.StatisticService;

/**
 * Created by Vo on 19-Dec-15.
 */

@Component
public class ScheduleStatistic {

    @Autowired
    StatisticService statisticService;

    @Scheduled(cron="0 0 23 * * ?")
    public void auto() {
        statisticService.createStatistic();
    }
}
