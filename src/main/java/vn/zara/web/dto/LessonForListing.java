// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import vn.zara.domain.common.validation.FieldMatch;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LessonForListing {

    protected String id;
    protected String title;
    protected String description;
    protected boolean canLearn;
    protected long scoreRequire;
    protected PokemonDetail pokemon;
}
