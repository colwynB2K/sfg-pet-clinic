package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.SpecialtyDTO;
import guru.springframework.sfgpetclinic.dto.VetDTO;
import guru.springframework.sfgpetclinic.service.SpecialtyService;
import guru.springframework.sfgpetclinic.service.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class VetMapService extends AbstractMapService<VetDTO, Long> implements VetService {

    private final SpecialtyService specialtyService;

    @Autowired
    public VetMapService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<VetDTO> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(VetDTO vet) {
        super.delete(vet);
    }

    @Override
    public VetDTO save(VetDTO vet) {
        if (vet != null) {
            Set<SpecialtyDTO> specialties = vet.getSpecialties();
            specialties.forEach(specialty -> {
                if (specialty.getId() == null) {                                            // If the current Specialty wasn't persisted yet
                    SpecialtyDTO savedSpecialtyWithId = specialtyService.save(specialty);      // save it
                    specialty.setId(savedSpecialtyWithId.getId());                          // Make sure to stored the saved specialty id on the current Specialty object
                }
            });

            return super.save(vet);
        } else {
            throw new RuntimeException("vet object can't be null!");
        }
    }

    @Override
    public VetDTO findById(Long id) {
        return super.findById(id);
    }
}
