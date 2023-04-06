package dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ShareDTO {
    private int id;
    private UserDTO user;
    private VideoDTO video;
    private String emails;
    private Date shareDate;

}
