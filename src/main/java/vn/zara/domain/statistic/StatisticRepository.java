package vn.zara.domain.statistic;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Vo on 18-Dec-15.
 */
public interface StatisticRepository extends MongoRepository<Statistic, String> {
}
