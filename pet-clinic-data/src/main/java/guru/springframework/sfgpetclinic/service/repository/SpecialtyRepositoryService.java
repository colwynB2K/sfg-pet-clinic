package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.Specialty;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepositoryService extends CrudRepository<Specialty, Long> {
}
