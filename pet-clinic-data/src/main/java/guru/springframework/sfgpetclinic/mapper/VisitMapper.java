package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.VisitDTO;
import guru.springframework.sfgpetclinic.model.Visit;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface VisitMapper {

    @Named("SetVisitIgnorePetChildRelations")
    @IterableMapping(qualifiedByName = "VisitIgnorePetChildRelations")
    Set<Visit> toEntityIgnorePetChildRelations(Set<VisitDTO> visitDTOS);

    @Named("VisitIgnorePetChildRelations")
    @Mappings({
            @Mapping(target = "pet.owner", ignore = true),
            @Mapping(target = "pet.visits", ignore = true)
    })
    Visit toEntityIgnorePetChildRelations(VisitDTO visitDTO);

    @Named("SetVisitIgnorePet")
    @IterableMapping(qualifiedByName = "VisitIgnorePet")
    Set<Visit> toEntitySetIgnorePet(Set<VisitDTO> visitDTOS);

    @Named("VisitIgnorePet")
    @Mappings({
            @Mapping(target = "pet", ignore = true)
    })
    Visit toEntityIgnorePet(VisitDTO visitDTO);

    @Mappings({
            @Mapping(target = "pet", qualifiedByName = "PetIgnoreVisitChildPet")
    })
    Visit toEntity(VisitDTO visitDTO);

    @Named("SetVisitIgnorePetChildRelations")
    @IterableMapping(qualifiedByName = "VisitIgnorePetChildRelations")
    Set<VisitDTO> toDTOSetIgnorePetChildRelations(Set<Visit> visits);

    @Named("VisitIgnorePetChildRelations")
    @Mappings({
            @Mapping(target = "pet.owner", ignore = true),
            @Mapping(target = "pet.visits", ignore = true)
    })
    VisitDTO toDTOIgnorePetChildRelations(Visit visit);

    @Named("SetVisitIgnorePet")
    @IterableMapping(qualifiedByName = "VisitIgnorePet")
    Set<VisitDTO> toDTOSetIgnorePet(Set<Visit> visits);

    @Named("VisitIgnorePet")
    @Mappings({
            @Mapping(target = "pet", ignore = true)
    })
    VisitDTO toDTOIgnorePet(Visit visit);

    @Mappings({
            @Mapping(target = "pet", qualifiedByName = "PetIgnoreVisitChildPet")
    })
    VisitDTO toDTO(Visit visit);
}
