package guru.springframework.sfgpetclinic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class PersonDTO extends BaseDTO {

    public PersonDTO(Long id, String firstName, String lastName) { // We explicitly coded the all args constructor as @AllArgsConstructor would only include the firstName and lastName properties with the properties from the super class
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Column(name = "first_name")            // If you don't specify this, Hibernate will just convert firstName to separate it with underscores, so it basically is the same with or without this annotation
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;
}
