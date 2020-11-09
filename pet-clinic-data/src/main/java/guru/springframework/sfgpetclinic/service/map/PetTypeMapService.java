package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class PetTypeMapService extends AbstractMapService<PetTypeDTO, Long> implements PetTypeService {

    @Override
    public Set<PetTypeDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(PetTypeDTO petType) {
        super.delete(petType);
    }

    @Override
    public PetTypeDTO save(PetTypeDTO petType) {
        return super.save(petType);
    }

    @Override
    public PetTypeDTO findById(Long id) {
        return super.findById(id);
    }
}
