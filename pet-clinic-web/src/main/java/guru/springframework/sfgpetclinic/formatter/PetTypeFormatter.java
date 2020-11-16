package guru.springframework.sfgpetclinic.formatter;

import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
// This is needed to convert the 'dog' and 'cat' petType values in the pet form dropdown to PetTypeDTO objects for the Controller
// This will act like some kind of Interceptor
public class PetTypeFormatter implements Formatter<PetTypeDTO> {

    private final PetTypeService petTypeService;

    @Autowired
    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetTypeDTO parse(String name, Locale locale) throws ParseException {
        PetTypeDTO petTypeDTO =  petTypeService.findByName(name);

        if (petTypeDTO != null) {
            return petTypeDTO;
        }

        throw new ParseException("PetType '" + name + "'not found", 0);
    }

    @Override
    public String print(PetTypeDTO petTypeDTO, Locale locale) {
        return petTypeDTO.getName();
    }
}
