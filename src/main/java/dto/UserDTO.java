package dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    private String id;
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    private String password;
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    @Email (message = "Your email is incorrect")
    private String email;
    @NotNull(message = "Must be not null!")
    @NotEmpty(message = "Must be not empty")
    private String fullname;
    private boolean admin;
    private List<FavoriteDTO> favoriteList;
    private List<ShareDTO> shareList;


}
