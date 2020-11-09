package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.service.OwnerService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@Slf4j
@RequestMapping({"/owners", "/owners.html"})
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listOwners(@ModelAttribute OwnerDTO owner, BindingResult bindingResult, Model model) {		// @ModelAttribute is used to bind the data from the owner object in the form to this method, BindingResult parameter needs to be defined directly after this
        if (StringUtils.isNotBlank(owner.getLastName())) {
            Set<OwnerDTO> owners = ownerService.findAllByLastNameLike(owner.getLastName());

            if (CollectionUtils.isEmpty(owners)) {
                // No owners found
                bindingResult.rejectValue("lastName", "notfound", "not found");                     // In case no results were found, reject the entered lastName value via bindingResult and trigger the error message
                return "owners/search-form";
            } else if (owners.size() == 1) {
                return "redirect:/owners/" + owners.iterator().next().getId();                              // Redirect to owner detail page in case only 1 result is found
            }

            model.addAttribute("owners", owners);
        } else {
            model.addAttribute("owners", ownerService.findAll());                                       // If a no value was entered, just return all owners
        }

        return "owners/list";
    }

    @GetMapping("/find")
    public ModelAndView showFindOwnersForm() {
        ModelAndView mav = new ModelAndView("owners/search-form");
        mav.addObject("owner", new OwnerDTO());

        return mav;
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {             // Instead of returning a String with the view path in it and injecting the Model via a method argument, you can use the ModelAndView composite object to handle this a different way
        ModelAndView mav = new ModelAndView("owners/detail");
        mav.addObject("owner", ownerService.findById(ownerId));

        return mav;
    }
}
