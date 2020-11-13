package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.model.Pet;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class, PetTypeMapper.class, VisitMapper.class})
public interface PetMapper {

    @Named("SetPetIgnoreOwnerChildPetsAndVisitChildPet")
    @IterableMapping(qualifiedByName = "PetIgnoreOwnerChildPetsAndVisitChildPet")
    Set<Pet> toEntitySetIgnoreOwnerChildPetsAndVisitChildPet(Set<PetDTO> petDTOs);

    Set<Pet> toEntitySet(Set<PetDTO> petDTOs);

    @Named("PetIgnoreOwnerChildPetsAndVisitChildPet")
    @Mappings({
            @Mapping(target = "owner.pets", ignore = true),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePet")
    })
    Pet toEntityIgnoreOwnerAndVisitChildPet(PetDTO petDTO);

    @Mappings({
            @Mapping(target = "owner", qualifiedByName = "OwnerIgnorePetsChildRelations"),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePetChildRelations")
    })
    Pet toEntity(PetDTO petDTO);

    @Named("SetPetIgnoreChildRelations")
    @IterableMapping(qualifiedByName = "PetIgnoreChildRelations")
    Set<Pet> toEntitySetIgnoreChildRelations(Set<PetDTO> petDTOs);

    @Named("PetIgnoreChildRelations")
    @Mappings({
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "visits", ignore = true)
    })
    Pet toEntityIgnoreChildRelations(PetDTO petDTO);

    @Named("PetIgnoreVisitChildPet")
    @Mappings({
            @Mapping(target = "owner.pets", ignore = true),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePet")
    })
    Pet toEntityIgnoreVisitChildPet(PetDTO petDTO);

    @Named("SetPetIgnoreOwnerChildPetsAndVisitChildPet")
    @IterableMapping(qualifiedByName = "PetIgnoreOwnerChildPetsAndVisitChildPet")
    Set<PetDTO> toDTOSetIgnoreOwnerAndVisitChildPet(Set<Pet> pets);

    Set<PetDTO> toDTOSet(Set<Pet> pets);

    @Named("PetIgnoreOwnerChildPetsAndVisitChildPet")
    @Mappings({
            @Mapping(target = "owner.pets", ignore = true),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePet")
    })
    PetDTO toDTOIgnoreOwnerChildPetsAndVisitChildPet(Pet pet);

    @Mappings({
            @Mapping(target = "owner", qualifiedByName = "OwnerIgnorePetsChildRelations"),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePetChildRelations")
    })
    PetDTO toDTO(Pet pet);

    @Named("SetPetIgnoreChildRelations")
    @IterableMapping(qualifiedByName = "PetIgnoreChildRelations")
    Set<PetDTO> toDTOSetIgnoreChildRelations(Set<Pet> pets);

    @Named("PetIgnoreChildRelations")
    @Mappings({
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "visits", ignore = true)
    })
    PetDTO toDTOIgnoreChildRelations(Pet pet);

    @Named("PetIgnoreVisitChildPet")
    @Mappings({
            @Mapping(target = "owner.pets", ignore = true),
            @Mapping(target = "visits", qualifiedByName = "SetVisitIgnorePet")
    })
    PetDTO toDTOIgnoreVisitChildPet(Pet pet);
}
