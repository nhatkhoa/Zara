package vn.zara.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by khoahoang on 1/7/2016.
 */
@Data
@AllArgsConstructor
@ToString
public class PokemonDetail {
    private String name;
    private String height;
    private String ThumbnailAltText;
    private String ThumbnailImage;
    private String[] type;
    private String[] abilities;
    private String[] weakness;
    private long level;
    private long nextScore;
    private long currentScore;
    private long previousScore;
}
