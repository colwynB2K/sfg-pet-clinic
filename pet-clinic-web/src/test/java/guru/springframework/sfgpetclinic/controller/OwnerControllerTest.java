package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)                                                 // Setup Mockito JUnit 5 environment, without this @Mock and @InjectMocks will not work
class OwnerControllerTest {

    @Mock
    private OwnerService mockOwnerService;

    @InjectMocks
    private OwnerController ownerController;

    MockMvc mockMvc;

    Set<OwnerDTO> owners;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();         // Initialize the Mock MVC environment using a standalone setup (it will only load the mentioned controller and its dependencies and not a full web application context which is awfully slow)

        owners = new HashSet<>();
        owners.add(OwnerDTO.builder().build());
    }

    @Test
    void listOwners() throws Exception {
        // when
        when(mockOwnerService.findAll()).thenReturn(owners);

        // then
        mockMvc.perform(get("/owners"))                                     // get the owners list page
                .andExpect(model().attribute("owners", equalTo(owners)))        // check that the model has a 'owners' attribute with a value equals to our owners HashSet
                .andExpect(status().isOk())                                           // check for HTTP status 200
                .andExpect(view().name("owners/list"));                // check the returned view name

        verify(mockOwnerService).findAll();                                            // As usual verify that method findAll() on the mocked OwnerService was called 1 time
    }

    @Test
    void showFindOwnersForm() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/search-form"));

        verifyNoInteractions(mockOwnerService);                                         // As this method is not implemented yet, this should interact with the ownerService at all!
    }

    @Test
    void findOwnersReturnsNone() throws Exception {
        Long id = 1L;

        // given
        when(mockOwnerService.findAllByLastNameLike(anyString())).thenReturn(Collections.EMPTY_SET);

        // when
        mockMvc.perform(get("/owners?lastName=BLAAT"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/search-form"));

        verify(mockOwnerService).findAllByLastNameLike(anyString());
    }

    @Test
    void findOwnersReturnsOne() throws Exception {
        Long id = 1L;

        // given
        when(mockOwnerService.findAllByLastNameLike(anyString())).thenReturn(Collections.singleton(OwnerDTO.builder().id(id).build()));

        // when
        mockMvc.perform(get("/owners?lastName=BLAAT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + id));

        verify(mockOwnerService).findAllByLastNameLike(anyString());
    }

    @Test
    void findOwnersReturnsMany() throws Exception {
        // given
        owners.add(OwnerDTO.builder().city("Genk").build());                                     // Add second owner to set with city Genk (otherwise it won't get added as Set doesn't allow duplicates)
        when(mockOwnerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        // when
        mockMvc.perform(get("/owners?lastName=BLAAT"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owners", equalTo(owners)))
                .andExpect(view().name("owners/list"));

        verify(mockOwnerService).findAllByLastNameLike(anyString());
    }

    @Test
    void showOwner() throws Exception {
        // given
        OwnerDTO expectedOwner = OwnerDTO.builder().id(1L).build();
        when(mockOwnerService.findById(anyLong())).thenReturn(expectedOwner);

        // when
        mockMvc.perform(get("/owners/1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("owner", equalTo(expectedOwner)))
        .andExpect(view().name("owners/detail"));

        // then
        verify(mockOwnerService).findById(anyLong());
    }

   /* @Test
    void showAddOwnerForm() throws Exception {
        // when
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/form"))
                .andExpect(model().attributeExists("owner"));

        // then
        verifyNoInteractions(mockOwnerService);
    }

    @Test
    void createOwner() throws Exception {
        // given
        OwnerDTO expectedOwner = OwnerDTO.builder().id(1L).build();
        when(mockOwnerService.save(any(OwnerDTO.class))).thenReturn(expectedOwner);

        // when
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attribute("owner", equalTo(expectedOwner)));

        // then
        verify(mockOwnerService).save(any(OwnerDTO.class));
    }

    @Test
    void showUpdateOwnerForm() throws Exception {
        // given
        OwnerDTO expectedOwner = OwnerDTO.builder().id(1L).build();
        when(mockOwnerService.findById(anyLong())).thenReturn(expectedOwner);

        // when
        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/form"))
                .andExpect(model().attribute("owner", equalTo(expectedOwner)));

        // then
        verify(mockOwnerService).findById(anyLong());
    }

    @Test
    void updateOwner() throws Exception {
        // given
        OwnerDTO expectedOwner = OwnerDTO.builder().id(1L).build();
        when(mockOwnerService.save(any(OwnerDTO.class))).thenReturn(expectedOwner);

        // when
        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attribute("owner", equalTo(expectedOwner)));

        // then
        verify(mockOwnerService).save(any(OwnerDTO.class));
    }*/
}