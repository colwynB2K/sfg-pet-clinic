package guru.springframework.sfgpetclinic.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class VetDTO extends PersonDTO {

    private Set<SpecialtyDTO> specialties = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof VetDTO))
            return false;

        VetDTO other = (VetDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
