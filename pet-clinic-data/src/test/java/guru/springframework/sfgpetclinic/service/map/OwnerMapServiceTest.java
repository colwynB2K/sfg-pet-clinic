package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;

    private final Long OWNER_ID = 1L;
    private final String OWNER_LAST_NAME = "Doe";
    private final PetDTO pet = new PetDTO();
    private final OwnerDTO owner = OwnerDTO.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build();


    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(owner); // Add 1 owner with only id configured to the HashMap
    }

    @Test
    void findAll() {
        // when
        Set<OwnerDTO> actualOwners = ownerMapService.findAll();

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
        OwnerDTO owner = ownerMapService.map.get(OWNER_ID);

        // when
        ownerMapService.delete(owner);

        // then
        assertEquals(0, ownerMapService.map.size());
    }

    @Test
    void save() {
        // given
        String petName = "Hedwig";
        PetTypeDTO petType = new PetTypeDTO();
        petType.setName("Owl");
        pet.setPetType(petType);
        pet.setName(petName);
        owner.getPets().add(pet);

        // when
        OwnerDTO savedOwner = ownerMapService.save(owner);

        // then
        assertNotNull(savedOwner);
        Set<PetDTO> savedOwnerPets = savedOwner.getPets();
        PetDTO savedPet = savedOwnerPets.iterator().next();
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
        OwnerDTO actualOwner = ownerMapService.findById(OWNER_ID);

        // then
        assertNotNull(actualOwner);
        assertEquals(OWNER_ID, actualOwner.getId());
    }

    @Test
    void findByLastName() {
        // when
        OwnerDTO actualOwner = ownerMapService.findByLastName(OWNER_LAST_NAME);

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