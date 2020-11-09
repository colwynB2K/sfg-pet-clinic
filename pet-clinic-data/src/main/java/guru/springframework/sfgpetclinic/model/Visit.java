package guru.springframework.sfgpetclinic.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "visit")
public class Visit extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne                                          // One pet can have many visits to the vet
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Visit))
            return false;

        Visit other = (Visit) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
