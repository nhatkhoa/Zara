// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.joda.time.DateTime;

@Data
@ToString
@AllArgsConstructor
public class ExerciseForListing {

    private String   id;
    private String   title;
    private String   description;
    private int      lastScore;
    private DateTime lastLearn;
}
