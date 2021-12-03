package fr.univlorrainem1archi.friendsfiestas_v1.salon.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Date dateEvent;

    @OneToOne(cascade = CascadeType.ALL)
    private Address addressEvent;

    @OneToOne
    private User host;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "salon")
    @JsonManagedReference
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "salon")
    @JsonManagedReference
    private List<Member> members;

    public String toString(){
        return this.getName();
    }

}
