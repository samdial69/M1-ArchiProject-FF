package fr.univlorrainem1archi.friendsfiestas_v1.task.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String description;
    //private MemberDTO affectedMember;

}
