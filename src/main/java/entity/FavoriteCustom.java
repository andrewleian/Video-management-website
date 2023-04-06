package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Entity
public class FavoriteCustom {
    @Id
    @Column(name = "VideoTitle")
    private String videoTitle;
    @Column(name = "FavoriteCount")
    private int favoriteCount;
    @Column(name = "LatestDate")
    private Date latestDate;
    @Column(name = "OldestDate")
    private Date oldestDate;


}
