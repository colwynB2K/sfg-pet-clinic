package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;

    private final Long OWNER_ID = 1L;
    private final String OWNER_LAST_NAME = "Doe";
    private final Pet pet = new Pet();
    private final Owner owner = Owner.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build();


    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(owner); // Add 1 owner with only id configured to the HashMap
    }

    @Test
    void findAll() {
        // when
        Set<Owner> actualOwners = ownerMapService.findAll();

        // then
        assertEquals(1, actualOwners.size());
    }

    @Test
    void deleteById() {
        // when
        ownerMapService.deleteById(OWNER_ID);

        // then
        assertEquals(0, ownerMapService.map.size());
    }

    @Test
    void delete() {
        // given
        Owner owner = ownerMapService.map.get(OWNER_ID);

        // when
        ownerMapService.delete(owner);

        // then
        assertEquals(0, ownerMapService.map.size());
    }

    @Test
    void save() {
        // given
        String petName = "Hedwig";
        PetType petType = new PetType();
        petType.setName("Owl");
        pet.setPetType(petType);
        pet.setName(petName);
        owner.getPets().add(pet);

        // when
        Owner savedOwner = ownerMapService.save(owner);

        // then
        assertNotNull(savedOwner);
        Set<Pet> savedOwnerPets = savedOwner.getPets();
        Pet savedPet = savedOwnerPets.iterator().next();
        assertNotNull(savedOwner.getId());
        assertNotNull(savedPet.getId());
        assertEquals(petName, savedPet.getName());
        assertNotNull(savedPet.getPetType().getId());
        assertEquals(petType.getName(), savedPet.getPetType().getName());
    }

    @Test
    void save_Should_Throw_RunTimeException_When_Owner_Is_Null() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> ownerMapService.save(null));
        assertEquals("Owner can't be null", e.getMessage());
    }

    @Test
    void save_Should_Throw_RunTimeException_When_PetType_Is_Null() {
        owner.getPets().add(pet);

        // then
        RuntimeException e = assertThrows(RuntimeException.class, () -> ownerMapService.save(owner));
        assertEquals("Pet Type is required.", e.getMessage());
    }

    @Test
    void findById() {
        // when
        Owner actualOwner = ownerMapService.findById(OWNER_ID);

        // then
        assertNotNull(actualOwner);
        assertEquals(OWNER_ID, actualOwner.getId());
    }

    @Test
    void findByLastName() {
        // when
        Owner actualOwner = ownerMapService.findByLastName(OWNER_LAST_NAME);

        // then
        assertNotNull(actualOwner);
        assertEquals(OWNER_ID, actualOwner.getId());
    }

    @Test
    void findByLastName_Should_Throw_RuntimeException_For_Non_Existing_LastName() {
        String lastName = "BLAAT";
        RuntimeException e = assertThrows(RuntimeException.class, () -> ownerMapService.findByLastName(lastName));
        assertEquals("Couldn't find Owner with lastName '" + lastName + "'", e.getMessage());
    }
}