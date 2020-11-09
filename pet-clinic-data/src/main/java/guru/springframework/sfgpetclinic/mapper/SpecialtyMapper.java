package guru.springframework.sfgpetclinic.mapper;

import guru.springframework.sfgpetclinic.dto.SpecialtyDTO;
import guru.springframework.sfgpetclinic.model.Specialty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    Specialty toEntity(SpecialtyDTO specialtyDTO);

    SpecialtyDTO toDTO(Specialty specialty);
}
