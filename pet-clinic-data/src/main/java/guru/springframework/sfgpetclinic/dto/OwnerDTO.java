package guru.springframework.sfgpetclinic.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OwnerDTO extends PersonDTO {

    @Builder
    public OwnerDTO(Long id, String firstName, String lastName, String address, String city, String telephone, Set<PetDTO> pets) {		// We explicitly coded the all args constructor as @AllArgsConstructor would only include the firstName and lastName properties with the properties from the super class
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets != null) {
            this.pets = pets;
        }
    }

    private String address;

    private String city;

    private String telephone;

    private Set<PetDTO> pets = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OwnerDTO))
            return false;

        OwnerDTO other = (OwnerDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
