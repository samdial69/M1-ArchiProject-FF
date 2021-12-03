package fr.univlorrainem1archi.friendsfiestas_v1.salon.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class SalonDTO {
    private Long id;

    private String name;
    private String description;
    private Date dateEvent;
}
