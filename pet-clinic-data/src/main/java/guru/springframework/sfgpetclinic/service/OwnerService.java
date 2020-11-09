package guru.springframework.sfgpetclinic.service;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;

import java.util.Set;

public interface OwnerService extends CrudService<OwnerDTO, Long> {

    OwnerDTO findByLastName(String lastName);

    Set<OwnerDTO> findAllByLastNameLike(String lastName);
}
