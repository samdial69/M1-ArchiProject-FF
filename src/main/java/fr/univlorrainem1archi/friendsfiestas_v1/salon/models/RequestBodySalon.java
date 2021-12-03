package fr.univlorrainem1archi.friendsfiestas_v1.salon.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBodySalon {
    private String name;
    private String description;
    private Date dateEvent;
}
