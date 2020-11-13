package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface OwnerMapper {


    Set<Owner> toEntitySet(Set<OwnerDTO> ownerDTOs);

    @Mappings({
            @Mapping(target = "pets", qualifiedByName = "SetPetIgnoreOwnerChildPetsAndVisitChildPet")
    })
    Owner toEntity(OwnerDTO ownerDTO);

    @Named("OwnerIgnorePetsChildRelations")
    @Mappings({
        @Mapping(target = "pets", qualifiedByName = "SetPetIgnoreChildRelations")
    })
    Owner toEntityIgnorePetsChildRelations(OwnerDTO ownerDTO);

    Set<OwnerDTO> toDTOSet(Set<Owner> owners);

    @Mappings({
            @Mapping(target = "pets", qualifiedByName = "SetPetIgnoreOwnerChildPetsAndVisitChildPet")
    })
    OwnerDTO toDTO(Owner owner);

    @Named("OwnerIgnorePetsChildRelations")
    @Mappings({
            @Mapping(target = "pets", qualifiedByName = "SetPetIgnoreChildRelations")
    })
    OwnerDTO toDTOIgnorePetsChildRelations(Owner owner);
}
