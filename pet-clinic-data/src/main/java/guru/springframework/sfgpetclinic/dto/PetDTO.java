package guru.springframework.sfgpetclinic.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class PetDTO extends BaseDTO {

    private String name;

    private LocalDate birthDate;

    private PetTypeDTO petType;

    private OwnerDTO owner;

    private Set<VisitDTO> visits = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PetDTO))
            return false;

        PetDTO other = (PetDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
