package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.VisitDTO;
import guru.springframework.sfgpetclinic.model.Visit;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface VisitMapper {

    Visit toEntity(VisitDTO visitDTO);

    @Named("VisitSetIgnorePetChildVisits")
    @IterableMapping(qualifiedByName = "VisitIgnorePetChildVisits")
    Set<Visit> toEntitySetIgnorePetChildVisits(Set<VisitDTO> visitDTOs);

    @Named("VisitIgnorePetChildVisits")
    @Mappings(
            @Mapping(target = "pet.visits", ignore = true)
    )
    Visit toEntityIgnorePetChildVisits(VisitDTO visitDTO);


    VisitDTO toDTO(Visit visit);

    @Named("VisitSetIgnorePetChildVisits")
    @IterableMapping(qualifiedByName = "VisitIgnorePetChildVisits")
    Set<VisitDTO> toDTOSetIgnorePetChildVisits(Set<Visit> visits);

    @Named("VisitIgnorePetChildVisits")
    @Mappings(
            @Mapping(target = "pet.visits", ignore = true)
    )
    VisitDTO toDTOIgnorePetChildVisits(Visit visit);
}
