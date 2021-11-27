package fr.univlorrainem1archi.friendsfiestas_v1.task.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
}
