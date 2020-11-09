package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.VetDTO;
import guru.springframework.sfgpetclinic.model.Vet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class})
public interface VetMapper {

    Vet toEntity(VetDTO vetDTO);

    VetDTO toDTO(Vet vet);
}
