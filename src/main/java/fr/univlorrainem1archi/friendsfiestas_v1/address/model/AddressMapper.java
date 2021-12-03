package fr.univlorrainem1archi.friendsfiestas_v1.address.model;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @InheritInverseConfiguration
    AddressDTO to(Address address);
    Address to(AddressDTO addressDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    AddressDTO to(RequestBodyAddress address);

}
