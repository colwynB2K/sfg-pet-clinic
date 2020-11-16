package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.dto.VisitDTO;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String YET_ANOTHER_VISIT_DESCRIPTION = "yet another visit";

    @Mock
    PetService mockPetService;

    @Mock
    VisitService mockVisitService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    private static String visitsUrl = null;
    private final Long petId = 1L;
    private final Long ownerId = 1L;

    @BeforeEach
    void setUp() {


        when(mockPetService.findById(anyLong()))
                .thenReturn(
                        PetDTO.builder()
                                .id(petId)
                                .birthDate(LocalDate.of(2018,11,13))
                                .name("Cutie")
                                .visits(new HashSet<>())
                                .owner(OwnerDTO.builder()
                                        .id(ownerId)
                                        .lastName("Doe")
                                        .firstName("Joe")
                                        .build())
                                .petType(PetTypeDTO.builder()
                                        .name("Dog").build())
                                .build()
                );

        visitsUrl = "/owners/" + ownerId + "/pets/" + petId + "/visits/new";

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void showAddVisitForm() throws Exception {
        // when
        mockMvc.perform(get(visitsUrl))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name(VisitController.VIEWS_VISITS_FORM));

        // then
        verifyNoInteractions(mockVisitService);
    }

    @Test
    void createVisit() throws Exception {
        // given
        when(mockVisitService.save(any(VisitDTO.class))).thenReturn(
                VisitDTO.builder()
                        .pet(PetDTO.builder()
                                .id(petId)
                                .owner(OwnerDTO.builder()
                                        .id(ownerId)
                                        .build())
                                .build())
                        .build()
        );

        // when
        mockMvc.perform(post(visitsUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date","2018-11-11")
                .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(OwnerController.VIEWS_OWNER_OWNERS_REDIRECT + ownerId))
                .andExpect(model().attributeExists("visit"));

        // then
        verify(mockVisitService).save(any(VisitDTO.class));
    }
}