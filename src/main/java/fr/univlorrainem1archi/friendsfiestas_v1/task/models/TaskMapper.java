package fr.univlorrainem1archi.friendsfiestas_v1.task.models;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @InheritInverseConfiguration
    TaskDTO to(Task task);
    Task to(TaskDTO taskDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true)
            /*@Mapping(target = "affectedMember.id", source = "affectedMember")*/
    })
    TaskDTO to(RequestBodyTask task);

}
