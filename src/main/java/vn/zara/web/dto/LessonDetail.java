// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.*;
import vn.zara.domain.pokemon.Pokemon;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetail{

    protected String id;
    protected String title;
    protected String description;
    protected String theory;
    protected PokemonDetail pokemon;
    protected List<ExerciseForListing> exercises;
    protected long sumOfScore;

}
