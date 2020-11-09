package guru.springframework.sfgpetclinic.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitDTO extends BaseDTO {

    private LocalDate date;

    private String description;

    private PetDTO pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof VisitDTO))
            return false;

        VisitDTO other = (VisitDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
