package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class PetMapService extends AbstractMapService<PetDTO, Long> implements PetService {
    @Override
    public Set<PetDTO> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(PetDTO pet) {
        super.delete(pet);
    }

    @Override
    public PetDTO save(PetDTO pet) {
        return super.save(pet);
    }

    @Override
    public PetDTO findById(Long id) {
        return super.findById(id);
    }
}
