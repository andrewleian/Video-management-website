package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class FavoriteUsers {
    @Id
    @Column(name = "UserName")
    private String userName;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "Email")
    private String email;
    @Column(name = "FavoriteDate")
    private Date favoriteDate;
}
