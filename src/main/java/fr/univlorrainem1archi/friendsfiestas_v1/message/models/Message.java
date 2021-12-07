package fr.univlorrainem1archi.friendsfiestas_v1.message.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private LocalDateTime sendAt;

    @ManyToOne
    @JsonBackReference
    private Member member;

    public Message() {
        this.sendAt = LocalDateTime.now();
    }

//    public List<?> getFormatMessage(){
//        return List.of(this.getMyUser().getId(),this.getMyUser(),this.getContent(),this.getSendAt());
//    }
//
//    private User getMyUser() {
//        return this.getMember().getUser();
//    }
}
