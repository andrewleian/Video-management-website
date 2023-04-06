package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class ShareFriends {
    @Id
    @Column(name = "SenderName")
    private String senderName;
    @Column(name = "SenderEmail")
    private String senderEmail;
    @Column(name = "ReceiverEmail")
    private String receiverEmail;
    @Column(name = "SentDate")
    private Date sentDate;
}
