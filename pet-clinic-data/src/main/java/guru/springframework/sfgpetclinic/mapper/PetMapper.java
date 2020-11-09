package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.model.Pet;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class, PetTypeMapper.class, VisitMapper.class})
public interface PetMapper {

    @IterableMapping(qualifiedByName = "toEntity")
    Set<Pet> toEntitySet(Set<PetDTO> petDTOs);

    @Named("toEntity")
    @Mappings({
            @Mapping(target = "owner", qualifiedByName = "OwnerIgnorePetChildOwner"),
            @Mapping(target = "visits", qualifiedByName = "VisitSetIgnorePetChildVisits")
    })
    Pet toEntity(PetDTO petDTO);

    @Named("PetSetIgnoreOwnerChildPets")
    @IterableMapping(qualifiedByName = "PetIgnoreOwnerChildPets")
    Set<Pet> toEntitySetIgnoreOwnerChildPets(Set<PetDTO> petDTOs);

    @Named("PetIgnoreOwnerChildPets")
    @Mappings(
            @Mapping(target = "owner.pets", ignore = true)
    )
    Pet toEntityIgnoreOwnerChildPets(PetDTO petDTO);

    @Named("PetSetIgnoreOwner")
    @IterableMapping(qualifiedByName = "PetIgnoreOwner")
    Set<Pet> toEntitySetIgnoreOwner(Set<PetDTO> pets);

    @Named("PetIgnoreOwner")
    @Mappings(
            @Mapping(target = "owner", ignore = true)
    )
    Pet toEntityIgnoreOwner(PetDTO petDTO);


    @IterableMapping(qualifiedByName = "toDTO")
    Set<PetDTO> toDTOSet(Set<Pet> pets);

    @Named("toDTO")
    @Mappings({
            @Mapping(target = "owner", qualifiedByName = "OwnerIgnorePetChildOwner"),
            @Mapping(target = "visits", qualifiedByName = "VisitSetIgnorePetChildVisits")
    })
    PetDTO toDTO(Pet pet);

    @Named("PetSetIgnoreOwnerChildPets")
    @IterableMapping(qualifiedByName = "PetIgnoreOwnerChildPets")
    Set<PetDTO> toDTOSetIgnoreOwnerChildPets(Set<Pet> pets);

    @Named("PetIgnoreOwnerChildPets")
    @Mappings(
            @Mapping(target = "owner.pets", ignore = true)
    )
    PetDTO toDTOIgnoreOwnerChildPets(Pet pet);

    @Named("PetSetIgnoreOwner")
    @IterableMapping(qualifiedByName = "PetIgnoreOwner")
    Set<PetDTO> toDTOSetIgnoreOwner(Set<Pet> pets);

    @Named("PetIgnoreOwner")
    @Mappings(
            @Mapping(target = "owner", ignore = true)
    )
    PetDTO toDTOIgnoreOwner(Pet pet);

}
