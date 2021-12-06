package fr.univlorrainem1archi.friendsfiestas_v1.task.models;


import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private Salon salon;

    private String description;
    private Member affectedMember;

    private boolean done;

}
