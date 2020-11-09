package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.dto.VisitDTO;
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
public class VisitMapService extends AbstractMapService<VisitDTO, Long> implements VisitService {

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
    public Set<VisitDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(VisitDTO visit) {
        super.delete(visit);
    }

    @Override
    public VisitDTO save(VisitDTO visit) {
        if (visit != null) {
            PetDTO pet = visit.getPet();
            if (pet != null) {
                PetTypeDTO petType = pet.getPetType();
                if (petType != null) {
                    if (petType.getId() == null) {                                   // If the PetType wasn't persisted yet
                        PetTypeDTO savedPetTypeWithId = petTypeService.save(petType);   // save it
                        pet.setPetType(savedPetTypeWithId);                          // set the saved Pet Type with id on the pet object
                    }
                } else {
                    throw new RuntimeException("Pet Type is required.");
                }

                OwnerDTO owner = pet.getOwner();
                if (owner != null) {
                    if (owner.getId() == null) {                                    // If the Owner wasn't persisted yet
                        OwnerDTO savedOwnerWithId = ownerService.save(owner);          // save it
                        pet.setOwner(savedOwnerWithId);                             // set the saved Owner with id on the pet object
                    }
                } else {
                    throw new RuntimeException("Owner is required.");
                }

                if (pet.getId() == null) {                                          // If the Pet wasn't persisted yet
                    PetDTO savedPetWithId = petService.save(pet);                      // save it
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
    public VisitDTO findById(Long id) {
        return super.findById(id);
    }
}
