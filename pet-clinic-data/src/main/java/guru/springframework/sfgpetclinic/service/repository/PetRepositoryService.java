package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepositoryService extends CrudRepository<Pet, Long> {
}
