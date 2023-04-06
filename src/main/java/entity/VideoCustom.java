package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class VideoCustom {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Poster")
    private String poster;
    @Column(name = "Views")
    private Integer views;
    @Column(name = "Description")
    private String description;
    @Column(name = "Active")
    private boolean active;
    @Column(name = "Like")
    private boolean like;

}
