package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
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

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();         // Initialize the Mock MVC environment using a standalone setup (it will only load the mentioned controller and its dependencies and not a full web application context which is awfully slow)

        owners = new HashSet<>();
        owners.add(Owner.builder().build());
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
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("notimplemented"));

        verifyNoInteractions(mockOwnerService);                             // As this method is not implemented yet, this should interact with the ownerService at all!
    }
}