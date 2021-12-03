package fr.univlorrainem1archi.friendsfiestas_v1.task.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBodyTask {
    private String description;

}
