package dto;

import entity.Favorite;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class VideoDTO {
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    private String id;
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    private String title;
    private String poster;
    @NotNull(message = "Must be not null!")
    private Integer views;
    @Length(min = 0 , max = 1000 , message = "Must be less than 1000 characters")
    private String description;
    @NotNull(message = "Must be not null!")
    private boolean active;
    private List<FavoriteDTO> favoriteList;
    private List<ShareDTO> shareList;


    public String showTitle(){
        String title = this.title;
        if(title.length() >45){
            title = title.substring(0,45) + "...";
        }
        return title;
    }

}
