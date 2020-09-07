package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepositoryService extends CrudRepository<Vet, Long> {
}
