package fr.univlorrainem1archi.friendsfiestas_v1.salon.models;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SalonMapper {
    @InheritInverseConfiguration
    SalonDTO to(Salon salon);
    Salon to(SalonDTO salonDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    SalonDTO to(RequestBodySalon salon);
}
