package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.OwnerMapper;
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

    @Mock
    private OwnerMapper mockOwnerMapper;

    @InjectMocks                                                // Inject Mock dependencies into this object
    private OwnerRepositoryService ownerRepositoryService;

    private Owner owner;
    private OwnerDTO ownerDTO;

    @BeforeEach
    void setUp() {

        owner = Owner.builder().id(ID).lastName(LAST_NAME).build();
        ownerDTO = OwnerDTO.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        // when
        when(mockOwnerRepository.findByLastName(LAST_NAME)).thenReturn(owner);
        when(mockOwnerMapper.toDTO(owner)).thenReturn(ownerDTO);
        OwnerDTO actualOwner = ownerRepositoryService.findByLastName(LAST_NAME);

        // then
        verify(mockOwnerRepository).findByLastName(LAST_NAME);
        verify(mockOwnerMapper).toDTO(owner);
        assertNotNull(actualOwner);
        assertEquals(ownerDTO, actualOwner);
    }

    @Test
    void findAll() {
        Set<Owner> expectedOwners = new HashSet<>();
        expectedOwners.add(owner);

        // when
        when(mockOwnerRepository.findAll()).thenReturn(expectedOwners);
        when(mockOwnerMapper.toDTO(owner)).thenReturn(ownerDTO);
        Set<OwnerDTO> actualOwners = ownerRepositoryService.findAll();

        // then
        verify(mockOwnerRepository).findAll();
        verify(mockOwnerMapper).toDTO(owner);
        assertNotNull(actualOwners);
        assertEquals(ownerDTO, actualOwners.iterator().next());
    }

    @Test
    void findById() {
        // when
        when(mockOwnerRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(owner));
        when(mockOwnerMapper.toDTO(owner)).thenReturn(ownerDTO);
        OwnerDTO actualOwner = ownerRepositoryService.findById(ID);

        // then
        verify(mockOwnerRepository).findById(ID);
        verify(mockOwnerMapper).toDTO(owner);
        assertNotNull(actualOwner);
        assertEquals(ownerDTO, actualOwner);
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
        when(mockOwnerMapper.toDTO(owner)).thenReturn(ownerDTO);
        when(mockOwnerMapper.toEntity(ownerDTO)).thenReturn(owner);
        OwnerDTO actualOwner = ownerRepositoryService.save(ownerDTO);

        // then
        verify(mockOwnerRepository).save(owner);
        verify(mockOwnerMapper).toDTO(owner);
        verify(mockOwnerMapper).toEntity(ownerDTO);
        assertNotNull(actualOwner);
        assertEquals(ownerDTO, actualOwner);
    }

    @Test
    void delete() {
        // when
        when(mockOwnerMapper.toEntity(ownerDTO)).thenReturn(owner);
        ownerRepositoryService.delete(ownerDTO);

        // then
        verify(mockOwnerRepository).delete(owner);
        verify(mockOwnerMapper).toEntity(ownerDTO);
    }

    @Test
    void deleteById() {
        // when
        ownerRepositoryService.deleteById(ID);

        // then
        verify(mockOwnerRepository).deleteById(ID);
    }
}