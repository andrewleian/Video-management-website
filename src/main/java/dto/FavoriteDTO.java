package dto;


import lombok.Data;

import java.sql.Date;

@Data
public class FavoriteDTO {
    private int id;
    private UserDTO user;
    private VideoDTO video;
    private Date likeDate;


}
