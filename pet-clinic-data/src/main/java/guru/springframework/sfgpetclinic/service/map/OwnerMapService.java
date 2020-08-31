package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    @Autowired
    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public Owner save(Owner owner) {
        if (owner != null) {
            Set<Pet> pets = owner.getPets();
            if (pets != null) {
                pets.forEach(pet -> {
                    PetType petType = pet.getPetType();
                    if (petType != null) {
                        if (petType.getId() == null) {                                   // If the PetType wasn't persisted yet
                            PetType savedPetTypeWithId = petTypeService.save(petType);   // save it
                            pet.setPetType(savedPetTypeWithId);                          // set the saved Pet Type with id on the pet object
                        }
                    } else {
                        throw new RuntimeException("Pet Type is required.");
                    }

                    if (pet.getId() == null) {                                          // If the Pet wasn't persisted yet
                        Pet savedPetWithId = petService.save(pet);                      // save it
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
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
