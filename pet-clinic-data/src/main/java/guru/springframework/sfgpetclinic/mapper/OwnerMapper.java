package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.model.Owner;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface OwnerMapper {

    @IterableMapping(qualifiedByName = "toEntity")
    Set<Owner> toEntitySet(Set<OwnerDTO> ownerDTOs);

    @Named("toEntity")
    @Mappings(
            @Mapping(target = "pets", qualifiedByName = "PetSetIgnoreOwnerChildPets")
    )
    Owner toEntity(OwnerDTO ownerDTO);

    @Named("OwnerIgnorePetChildOwner")
    @Mappings(
            @Mapping(target = "pets", qualifiedByName = "PetSetIgnoreOwner")
    )
    Owner toEntityIgnorePetChildOwner(OwnerDTO ownerDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    Set<OwnerDTO> toDTOSet(Set<Owner> owners);

    @Named("toDTO")
    @Mappings(
            @Mapping(target = "pets", qualifiedByName = "PetSetIgnoreOwnerChildPets")
    )
    OwnerDTO toDTO(Owner owner);

    @Named("OwnerIgnorePetChildOwner")
    @Mappings(
            @Mapping(target = "pets", qualifiedByName = "PetSetIgnoreOwner")
    )
    OwnerDTO toDTOIgnorePetChildOwner(Owner owner);
}
