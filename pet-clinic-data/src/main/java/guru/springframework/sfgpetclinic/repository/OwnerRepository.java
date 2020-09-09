package guru.springframework.sfgpetclinic.repository;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);                              // Use Spring Data JPA (Dynamic) Query Methods which in the background will generate a corresponding DAO implementation with the actual database query dynamically
}
