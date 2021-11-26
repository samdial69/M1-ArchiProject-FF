package fr.univlorrainem1archi.friendsfiestas_v1.salon.models;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    @OneToOne
    private Address addressEvent;
}
