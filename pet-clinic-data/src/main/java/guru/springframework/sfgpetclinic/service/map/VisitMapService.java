package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    @Autowired
    public VisitMapService(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit visit) {
        super.delete(visit);
    }

    @Override
    public Visit save(Visit visit) {
        if (visit != null) {
            Pet pet = visit.getPet();
            if (pet != null) {
                PetType petType = pet.getPetType();
                if (petType != null) {
                    if (petType.getId() == null) {                                   // If the PetType wasn't persisted yet
                        PetType savedPetTypeWithId = petTypeService.save(petType);   // save it
                        pet.setPetType(savedPetTypeWithId);                          // set the saved Pet Type with id on the pet object
                    }
                } else {
                    throw new RuntimeException("Pet Type is required.");
                }

                Owner owner = pet.getOwner();
                if (owner != null) {
                    if (owner.getId() == null) {                                    // If the Owner wasn't persisted yet
                        Owner savedOwnerWithId = ownerService.save(owner);          // save it
                        pet.setOwner(savedOwnerWithId);                             // set the saved Owner with id on the pet object
                    }
                } else {
                    throw new RuntimeException("Owner is required.");
                }

                if (pet.getId() == null) {                                          // If the Pet wasn't persisted yet
                    Pet savedPetWithId = petService.save(pet);                      // save it
                    pet.setId(savedPetWithId.getId());                              // Make sure to stored the saved pet id on the current Pet object
                }
            }
            visit.setPet(pet);

            return super.save(visit);
        } else {
            throw new RuntimeException("Visit can't be null");
        }
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
