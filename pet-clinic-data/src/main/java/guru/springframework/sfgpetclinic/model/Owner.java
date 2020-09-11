package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "pets", callSuper = false)     // SonarLint complained about a missing callSuper:  By setting callSuper to true, you can include the equals and hashCode methods of your superclass in the generated methods. For hashCode, the result of super.hashCode() is included in the hash algorithm, and forequals, the generated method will return false if the super implementation thinks it is not equal to the passed in object. Be aware that not all equals implementations handle this situation properly. However, lombok-generated equals implementations do handle this situation properly, so you can safely call your superclass equals if it, too, has a lombok-generated equals method. If you have an explicit superclass you are forced to supply some value for callSuper to acknowledge that you've considered it; failure to do so results in a warning.
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
        this.pets = pets;
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
}
