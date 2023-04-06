package dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
@Data
public class VideoCustomDTO {
    private String id;
    private String title;
    private String poster;
    private Integer views;
    private String description;
    private boolean active;
    private boolean like;
    private List<FavoriteDTO> favoriteList;
    private List<ShareDTO> shareList;
}
