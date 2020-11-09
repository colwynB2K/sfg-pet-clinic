package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Profile({"default", "Map"})
@Slf4j
public class OwnerMapService extends AbstractMapService<OwnerDTO, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    @Autowired
    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<OwnerDTO> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(OwnerDTO owner) {
        super.delete(owner);
    }

    @Override
    public OwnerDTO save(OwnerDTO owner) {
        if (owner != null) {
            Set<PetDTO> pets = owner.getPets();
            if (pets != null) {
                pets.forEach(pet -> {
                    PetTypeDTO petType = pet.getPetType();
                    if (petType != null) {
                        if (petType.getId() == null) {                                   // If the PetType wasn't persisted yet
                            PetTypeDTO savedPetTypeWithId = petTypeService.save(petType);   // save it
                            pet.setPetType(savedPetTypeWithId);                          // set the saved Pet Type with id on the pet object
                        }
                    } else {
                        throw new RuntimeException("Pet Type is required.");
                    }

                    if (pet.getId() == null) {                                          // If the Pet wasn't persisted yet
                        PetDTO savedPetWithId = petService.save(pet);                      // save it
                        pet.setId(savedPetWithId.getId());                              // Make sure to stored the saved pet id on the current Pet object
                    }
                });
            }
            owner.setPets(pets);

            return super.save(owner);
        } else {
            throw new RuntimeException("Owner can't be null");
        }
    }

    @Override
    public OwnerDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public OwnerDTO findByLastName(String lastName) {
        for (Map.Entry<Long, OwnerDTO> entry : map.entrySet()) {
            if (lastName.equals(entry.getValue().getLastName())) {
                return entry.getValue();
            }
        }

        throw new RuntimeException("Couldn't find Owner with lastName '" + lastName + "'");
    }

    @Override
    public Set<OwnerDTO> findAllByLastNameLike(String lastName) {
        Set<OwnerDTO> owners = new HashSet<>();
        for (Map.Entry<Long, OwnerDTO> entry : map.entrySet()) {
            if (entry.getValue().getLastName().contains(lastName)) {
                owners.add(entry.getValue());
            }
        }

        return owners;
    }
}
