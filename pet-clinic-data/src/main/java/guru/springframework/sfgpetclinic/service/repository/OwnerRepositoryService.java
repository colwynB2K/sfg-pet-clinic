package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepositoryService extends CrudRepository<Owner, Long> {
}
