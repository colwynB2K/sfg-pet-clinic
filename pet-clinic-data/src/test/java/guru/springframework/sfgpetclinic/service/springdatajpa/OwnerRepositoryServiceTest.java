package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repository.OwnerRepository;
import guru.springframework.sfgpetclinic.repository.PetRepository;
import guru.springframework.sfgpetclinic.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)                             // Setup Mockito JUnit 5 environment, without this @Mock and @InjectMocks will not work
class OwnerRepositoryServiceTest {

    private static final Long ID = 1L;
    private static final String LAST_NAME = "Doe";


    @Mock                                                       // Annotate all dependencies you wish to mock out with @Mock
    private OwnerRepository mockOwnerRepository;

    @Mock
    private PetRepository mockPetRepository;

    @Mock
    private PetTypeRepository mockPetTypeRepository;

    @InjectMocks                                                // Inject Mock dependencies into this object
    private OwnerRepositoryService ownerRepositoryService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        // when
        when(mockOwnerRepository.findByLastName(LAST_NAME)).thenReturn(owner);
        Owner actualOwner = ownerRepositoryService.findByLastName(LAST_NAME);

        // then
        verify(mockOwnerRepository).findByLastName(LAST_NAME);
        assertNotNull(actualOwner);
        assertEquals(owner, actualOwner);
    }

    @Test
    void findAll() {
        Set<Owner> expectedOwners = new HashSet<>();
        expectedOwners.add(owner);

        // when
        when(mockOwnerRepository.findAll()).thenReturn(expectedOwners);
        Set<Owner> actualOwners = ownerRepositoryService.findAll();

        // then
        verify(mockOwnerRepository).findAll();
        assertNotNull(actualOwners);
        assertEquals(expectedOwners.iterator().next(), actualOwners.iterator().next());
    }

    @Test
    void findById() {
        // when
        when(mockOwnerRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(owner));
        Owner actualOwner = ownerRepositoryService.findById(ID);

        // then
        verify(mockOwnerRepository).findById(ID);
        assertNotNull(actualOwner);
        assertEquals(owner, actualOwner);
    }

    @Test
    void findById_Should_Throw_ObjectNotFoundException_For_Non_Existing_Id() {
        // when
        Long nonExistingId = 666L;
        when(mockOwnerRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // then
        ObjectNotFoundException e = assertThrows(ObjectNotFoundException.class, () -> ownerRepositoryService.findById(nonExistingId));
        verify(mockOwnerRepository).findById(nonExistingId);
        assertEquals("No owner found for id'" + nonExistingId + "'", e.getMessage());
    }

    @Test
    void save() {
        // when
        when(mockOwnerRepository.save(owner)).thenReturn(owner);
        Owner actualOwner = ownerRepositoryService.save(owner);

        // then
        verify(mockOwnerRepository).save(owner);
        assertNotNull(actualOwner);
        assertEquals(owner, actualOwner);
    }

    @Test
    void delete() {
        // when
        ownerRepositoryService.delete(owner);

        // then
        verify(mockOwnerRepository).delete(owner);
    }

    @Test
    void deleteById() {
        // when
        ownerRepositoryService.deleteById(ID);

        // then
        verify(mockOwnerRepository).deleteById(ID);
    }
}