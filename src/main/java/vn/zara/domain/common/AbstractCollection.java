/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 03/12/2015 - 9:09 AM at ZaraApi.
 */

package vn.zara.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

public class AbstractCollection {

    @Id
    @Getter
    @Setter
    protected String id;

    @CreatedDate
    @Getter
    @Setter
    @JsonIgnore
    protected DateTime createdDate;

    @LastModifiedDate
    @Getter
    @Setter
    @JsonIgnore
    protected DateTime lastModifiedDate;

}
