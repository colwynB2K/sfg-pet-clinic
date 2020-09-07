package guru.springframework.sfgpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Hibernate recommends to use 'boxes types' (like Integer, Long, etc...) instead of primitives (int, long,... ) for ids as these can be null, while primitives cannot

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
