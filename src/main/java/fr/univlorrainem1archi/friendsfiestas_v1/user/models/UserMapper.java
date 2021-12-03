package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @InheritInverseConfiguration
    UserDTO to(User user);
    User to(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    UserDTO to(RequestBodyUser user);

}
