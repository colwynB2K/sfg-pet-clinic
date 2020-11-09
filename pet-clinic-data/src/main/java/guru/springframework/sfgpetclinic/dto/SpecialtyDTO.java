package guru.springframework.sfgpetclinic.dto;

import lombok.Data;

@Data
public class SpecialtyDTO extends BaseDTO {

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SpecialtyDTO))
            return false;

        SpecialtyDTO other = (SpecialtyDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
