package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.model.PetType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetTypeMapper {

    PetType toEntity(PetTypeDTO petTypeDTO);

    PetTypeDTO toDTO(PetType petType);
}
