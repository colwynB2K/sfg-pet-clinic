package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.VisitDTO;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class VisitController {
    public static final String VIEWS_VISITS_FORM = "visits/form";

    private final VisitService visitService;
    private final PetService petService;

    @Autowired
    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");                   // Don't accept id values from the web layer

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {      // Incoming String values for visit date are parsed to LocalDate
            @Override
            public void setAsText(String text) {
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public VisitDTO loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        PetDTO pet = petService.findById(petId);
        model.put("pet", pet);
        VisitDTO visit = new VisitDTO();
        pet.getVisits().add(visit);
        visit.setPet(pet);

        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before this is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String showAddVisitForm(@PathVariable("petId") Long petId, Map<String, Object> model) {

        return VIEWS_VISITS_FORM;
    }

    // Spring MVC calls method loadPetWithVisit(...) before this is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String createVisit(@Valid VisitDTO visit, BindingResult result, @PathVariable Long ownerId, @PathVariable Long petId) {
        if (result.hasErrors()) {
            return VIEWS_VISITS_FORM;
        } else {
            PetDTO pet = petService.findById(petId);
            pet.getVisits().add(visit);
            visit.setPet(pet);
            VisitDTO savedVisit = visitService.save(visit);

            return OwnerController.VIEWS_OWNER_OWNERS_REDIRECT + savedVisit.getPet().getOwner().getId();
        }
    }
}
