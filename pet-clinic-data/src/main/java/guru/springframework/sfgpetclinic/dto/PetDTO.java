package guru.springframework.sfgpetclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class PetDTO extends BaseDTO {

    @Builder
    public PetDTO(Long id, String name, LocalDate birthDate, PetTypeDTO petType, OwnerDTO owner, Set<VisitDTO> visits) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
        if (visits != null) {
            this.visits = visits;
        }
    }

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    // Debugger triggered a StackOverflowError as it tries to print the objects in the IDE which caused circular references
    @Override
    public String toString() {
        return "PetDTO{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", petType=" + petType +
                ", id=" + id +
                '}';
    }
}
