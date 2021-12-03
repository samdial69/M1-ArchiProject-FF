package fr.univlorrainem1archi.friendsfiestas_v1.address.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBodyAddress {
    private String street;
    private String postalCode;
    private String city;

    public String getCompleteAddress() {
        return this.getStreet() +", "+ this.getPostalCode() +" "+ this.getCity();
    }
}
