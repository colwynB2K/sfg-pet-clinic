package guru.springframework.sfgpetclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private Long id; // Hibernate recommends to use 'boxes types' (like Integer, Long, etc...) instead of primitives (int, long,... ) for ids as these can be null, while primitives cannot

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
