package dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Date;

@Data
public class FavoriteCustomDTO {
    private String videoTitle;
    private int favoriteCount;
    private Date latestDate;
    private Date oldestDate;
}
