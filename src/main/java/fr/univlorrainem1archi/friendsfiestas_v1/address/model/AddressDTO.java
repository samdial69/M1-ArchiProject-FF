package fr.univlorrainem1archi.friendsfiestas_v1.address.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    private Long id;
    private String street;
    private String postalCode;
    private String city;

}
