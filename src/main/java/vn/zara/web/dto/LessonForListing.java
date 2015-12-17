// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import vn.zara.domain.common.validation.FieldMatch;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
public class LessonForListing {

    protected String id;
    protected String title;
    protected String description;
    protected String pokemon;
    protected long pokemonLevel;
    protected String pokemonImg;

}
