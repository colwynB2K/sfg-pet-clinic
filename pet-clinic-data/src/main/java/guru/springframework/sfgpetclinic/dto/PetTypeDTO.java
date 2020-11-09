package guru.springframework.sfgpetclinic.dto;

import lombok.Data;

@Data
public class PetTypeDTO extends BaseDTO {

    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PetTypeDTO))
            return false;

        PetTypeDTO other = (PetTypeDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
