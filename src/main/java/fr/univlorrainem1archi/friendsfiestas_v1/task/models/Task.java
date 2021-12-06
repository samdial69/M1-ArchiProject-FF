package fr.univlorrainem1archi.friendsfiestas_v1.task.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String description;

    @ManyToOne
    @JsonBackReference
    private Salon salon;

    @OneToOne(cascade = CascadeType.ALL)
    private Member affectedMember;

    private boolean done;

    public String toString(){
        return this.getDescription();
    }
}
