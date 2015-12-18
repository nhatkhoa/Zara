package vn.zara.domain.statistic;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;

/**
 * Created by Vo on 18-Dec-15.
 */

@Data
@Document(collection = "statistics")
public class Statistic extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(Statistic.class);

    @Indexed
    private String username;    // cua ai
    private int lesson;     //hoc duoc bao nhieu lesson
    private int exercise;   //lam duoc bao nhieu exercise
    private int score;      //tong diem la bao nhieuF
}
