package entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "Fullname")
    private String fullname;
    @Column(name = "Admin")
    private boolean admin;
    @OneToMany(mappedBy = "user")
    private List<Favorite> favoriteList;
    @OneToMany(mappedBy = "user")
    private List<Share> shareList;


}
