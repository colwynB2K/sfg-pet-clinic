package guru.springframework.sfgpetclinic.service.repository;

import guru.springframework.sfgpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepositoryService extends CrudRepository<Visit, Long> {
}
