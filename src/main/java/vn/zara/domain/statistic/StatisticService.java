package vn.zara.domain.statistic;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.zara.domain.learn.DoExercise;
import vn.zara.domain.learn.DoExerciseRepository;
import vn.zara.domain.user.User;
import vn.zara.domain.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vo on 18-Dec-15.
 */

@Service
public class StatisticService {
    protected static Logger Logger = LoggerFactory.getLogger(StatisticService.class);

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    DoExerciseRepository doExerciseRepository;

    @Autowired
    UserRepository userRepository;

    public void createStatisticForUsername(String username) {
        Stream<DoExercise> doExerciseStream = doExerciseRepository.findByUsername(username);
        DateTime currentDate = new DateTime();

        List<DoExercise> doExercises = doExerciseStream.filter(doExercise ->
                currentDate.getDayOfMonth() == doExercise.getCreatedDate().getDayOfMonth() &&
                        currentDate.getMonthOfYear() == doExercise.getCreatedDate().getMonthOfYear() &&
                        currentDate.getYear() == doExercise.getCreatedDate().getYear())
                .collect(Collectors.toList());

        Logger.debug(String.format("doExercise in current date: %s", doExercises.size()));

        Statistic statistic = new Statistic();

        statistic.setUsername(username);

        Map<String, DoExercise> map = new HashMap<>();
        doExercises.forEach(doExercise -> map.put(doExercise.getLesson(), doExercise));
        statistic.setLesson(map.size());

        statistic.setExercise(doExercises.size());

        statistic.setScore(doExercises.stream().mapToInt(doExercise -> doExercise.getScore()).sum());

        statisticRepository.save(statistic);
    }

    public void createStatistic() {
        List<User> userStream = userRepository.findAll();
        userStream.forEach(user -> createStatisticForUsername(user.getUsername()));
    }
}
