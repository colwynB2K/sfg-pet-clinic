package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "owner")
public class Owner extends Person {

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {		// We explicitly coded the all args constructor as @AllArgsConstructor would only include the firstName and lastName properties with the properties from the super class
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

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Pet> pets = new HashSet<>();

    // Convenience methods
    public Pet getPetByName(String name) {
        return getPetByName(name, false);
    }

    public Pet getPetByName(String name, boolean ignoreNew) {

        return pets.stream().filter(pet -> pet.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Owner))
            return false;

        Owner other = (Owner) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
