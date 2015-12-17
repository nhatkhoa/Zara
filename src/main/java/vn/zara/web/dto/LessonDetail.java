// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.*;

import java.util.List;

@Data
@ToString
public class LessonDetail extends LessonForListing{

    public LessonDetail(String id, String title, String description, String pokemon, long pokemonLevel, String pokemonImg) {
        super(id, title, description, pokemon, pokemonLevel, pokemonImg);
        this.setExercises(exercises);
        this.setTheory(theory);
    }

    @Getter @Setter
    private String theory;

    @Getter @Setter
    private List<ExerciseForListing> exercises;
}
