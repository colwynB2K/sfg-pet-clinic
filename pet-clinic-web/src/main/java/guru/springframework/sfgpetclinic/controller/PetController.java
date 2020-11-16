package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@Slf4j
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    public static final String VIEWS_PETS_FORM = "pets/form";

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    @Autowired
    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @InitBinder
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    // You can annotate a method with @ModelAttribute if you want to set this model attribute for all request mappings and avoid duplicating the related code
    @ModelAttribute("petTypes")
    public Collection<PetTypeDTO> populatePetTypes() {
        return petTypeService.findAll();
    }

    // Each pet belongs to an owner, so the request mapping url /owners/{ownerId}/pets is constructed in such a way to enable us to always make the corresponding owner available to the view
    @ModelAttribute("owner")
    public OwnerDTO findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @GetMapping("/new")
    public String showAddPetForm(Model model) {
        PetDTO pet = new PetDTO();
        OwnerDTO owner = (OwnerDTO) model.getAttribute("owner");
        pet.setOwner(owner);                                        // Because of this you can display the owner name in the pet form
        model.addAttribute("pet", pet);

        return VIEWS_PETS_FORM;
    }

    @PostMapping("/new")
    public String createPet(@Valid @ModelAttribute PetDTO pet, BindingResult result, @PathVariable Long ownerId, Model model) {
        // Avoid adding duplicate pets
        OwnerDTO owner = (OwnerDTO) model.getAttribute("owner");
        if ( pet.isNew() && StringUtils.hasLength(pet.getName()) && owner.getPetByName(pet.getName(), true) != null ) {
            result.rejectValue("name", "duplicate", "Already exists!");
        }

        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);

            return VIEWS_PETS_FORM;
        } else {
            pet.setOwner(owner);
            PetDTO savedPet = petService.save(pet);

            return OwnerController.VIEWS_OWNER_OWNERS_REDIRECT + savedPet.getOwner().getId();
        }
    }

    @GetMapping("/{petId}/edit")
    public String showUpdatePetForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));

        return VIEWS_PETS_FORM;
    }

    @PostMapping("/{petId}/edit")
    public String updatePet(@Valid @ModelAttribute PetDTO pet, BindingResult result, Model model) {
        OwnerDTO owner = (OwnerDTO) model.getAttribute("owner");

        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);

            return VIEWS_PETS_FORM;
        } else {
            owner.getPets().add(pet);
            pet.setOwner(owner);
            PetDTO savedPet = petService.save(pet);

            return "redirect:/owners/" + savedPet.getOwner().getId();
        }
    }
}
