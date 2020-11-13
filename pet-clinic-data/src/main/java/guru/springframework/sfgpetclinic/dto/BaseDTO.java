package guru.springframework.sfgpetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BaseDTO implements Serializable {

    protected Long id;

    // Flag as new when id is null
    public boolean isNew() {
        return this.id == null;
    }
}
