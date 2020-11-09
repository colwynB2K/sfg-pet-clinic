package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.SpecialtyDTO;
import guru.springframework.sfgpetclinic.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class SpecialtyMapService extends AbstractMapService<SpecialtyDTO, Long> implements SpecialtyService {
    @Override
    public Set<SpecialtyDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(SpecialtyDTO specialty) {
        super.delete(specialty);
    }

    @Override
    public SpecialtyDTO save(SpecialtyDTO specialty) {
        return super.save(specialty);
    }

    @Override
    public SpecialtyDTO findById(Long id) {
        return super.findById(id);
    }
}
