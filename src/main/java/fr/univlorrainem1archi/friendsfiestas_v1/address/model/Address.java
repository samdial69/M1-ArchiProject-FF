package fr.univlorrainem1archi.friendsfiestas_v1.address.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Size(min = 5, message = "Ce champ doit contenir minimum 10 caractères")
    private String street;

    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Size(min = 2, max = 35, message = "Ce champ doit contenir 2 et 35 caractères")
    private String city;

    @NotBlank(message = "Ce champ ne doit pas être vide")
    @Size(min = 5, max = 5, message = "Ce champ doit contenir 5 caractères")
    private String postalCode;

    public String getCompleteAddress() {
        return this.getStreet()+", "+this.postalCode+" "+this.getCity();
    }
}
