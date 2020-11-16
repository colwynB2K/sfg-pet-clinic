package guru.springframework.sfgpetclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class VisitDTO extends BaseDTO {

    private LocalDate date;

    private String description;

    private PetDTO pet;

    @Builder
    public VisitDTO(Long id, LocalDate date, String description, PetDTO pet) {
        super(id);
        this.date = date;
        this.description = description;
        this.pet = pet;
    }

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
