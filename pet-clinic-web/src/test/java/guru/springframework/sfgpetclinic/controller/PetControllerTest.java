package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetService mockPetService;

    @Mock
    PetTypeService mockPetTypeService;

    @Mock
    OwnerService mockOwnerService;

    @InjectMocks
    PetController petController;

    private MockMvc mockMvc;
    private OwnerDTO owner = OwnerDTO.builder().id(1L).build();

    @BeforeEach
    void setUp() {
        Set<PetTypeDTO> petTypes = new HashSet<>();
        petTypes.add(PetTypeDTO.builder().id(1L).name("Cat").build());
        petTypes.add(PetTypeDTO.builder().id(2L).name("Dog").build());

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        // given
        when(mockOwnerService.findById(anyLong())).thenReturn(owner);
        when(mockPetTypeService.findAll()).thenReturn(petTypes);
    }

    @Test
    void showAddPetForm() throws Exception {
        // when
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(view().name(PetController.VIEWS_PETS_FORM));

        // then
        verifyNoInteractions(mockPetService);
    }

    @Test
    void createPet() throws Exception {
        // given
        when(mockPetService.save(any(PetDTO.class))).thenReturn(PetDTO.builder().id(1L).owner(owner).build());

        // when
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(OwnerController.VIEWS_OWNER_OWNERS_REDIRECT + "1"));

        // then
        verify(mockPetService).save(any(PetDTO.class));
    }

    @Test
    void showUpdatePetForm() throws Exception {
        // given
        when(mockPetService.findById(anyLong())).thenReturn(PetDTO.builder().id(2L).build());

        // when
        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name(PetController.VIEWS_PETS_FORM));
    }

    @Test
    void updatePet() throws Exception {
        // given
        when(mockPetService.save(any(PetDTO.class))).thenReturn(PetDTO.builder().id(1L).owner(owner).build());

        // when
        mockMvc.perform(post("/owners/1/pets/2/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(OwnerController.VIEWS_OWNER_OWNERS_REDIRECT + "1"));

        // then
        verify(mockPetService).save(any(PetDTO.class));
    }
}