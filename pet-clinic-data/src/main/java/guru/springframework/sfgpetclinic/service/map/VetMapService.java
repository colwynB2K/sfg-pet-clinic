package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.SpecialtyService;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    @Autowired
    public VetMapService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }

    @Override
    public Vet save(Vet vet) {
        if (vet != null) {
            Set<Specialty> specialties = vet.getSpecialties();
            specialties.forEach(specialty -> {
                if (specialty.getId() == null) {                                            // If the current Specialty wasn't persisted yet
                    Specialty savedSpecialtyWithId = specialtyService.save(specialty);      // save it
                    specialty.setId(savedSpecialtyWithId.getId());                          // Make sure to stored the saved specialty id on the current Specialty object
                }
            });

            return super.save(vet);
        } else {
            throw new RuntimeException("vet object can't be null!");
        }
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
