package guru.springframework.sfgpetclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
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

    // Convenience methods
    public PetDTO getPetByName(String name) {
        return getPetByName(name, false);
    }

    public PetDTO getPetByName(String name, boolean ignoreNew) {

        List<PetDTO> petsWithSameName = pets.stream().filter(pet -> pet.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        return !petsWithSameName.isEmpty() ? petsWithSameName.get(0) : null;
    }

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

    // Debugger triggered a StackOverflowError as it tries to print the objects in the IDE which caused circular references
    @Override
    public String toString() {
        return "OwnerDTO{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
