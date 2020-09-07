package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepositoryService extends CrudRepository<PetType, Long> {
}
