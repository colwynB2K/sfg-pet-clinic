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

import javax.validation.Valid;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping({"/owners", "/owners.html"})
public class OwnerController {

    public static final String VIEWS_OWNER_OWNERS_DETAIL = "owners/detail";
    public static final String VIEWS_OWNER_OWNERS_FORM = "owners/form";
    public static final String VIEWS_OWNER_OWNERS_LIST = "owners/list";
    public static final String VIEWS_OWNER_OWNERS_REDIRECT = "redirect:/owners/";
    public static final String VIEWS_OWNER_OWNERS_SEARCH_FORM = "owners/search-form";

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
                return VIEWS_OWNER_OWNERS_SEARCH_FORM;
            } else if (owners.size() == 1) {
                return VIEWS_OWNER_OWNERS_REDIRECT + owners.iterator().next().getId();                              // Redirect to owner detail page in case only 1 result is found
            }

            model.addAttribute("owners", owners);
        } else {
            model.addAttribute("owners", ownerService.findAll());                                       // If a no value was entered, just return all owners
        }

        return VIEWS_OWNER_OWNERS_LIST;
    }

    @GetMapping("/find")
    public ModelAndView showFindOwnersForm() {
        ModelAndView mav = new ModelAndView(VIEWS_OWNER_OWNERS_SEARCH_FORM);
        mav.addObject("owner", new OwnerDTO());

        return mav;
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {             // Instead of returning a String with the view path in it and injecting the Model via a method argument, you can use the ModelAndView composite object to handle this a different way
        ModelAndView mav = new ModelAndView(VIEWS_OWNER_OWNERS_DETAIL);
        mav.addObject("owner", ownerService.findById(ownerId));

        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showAddOwnerForm() {
        ModelAndView mav = new ModelAndView(VIEWS_OWNER_OWNERS_FORM);
        mav.addObject("owner", new OwnerDTO());

        return mav;
    }

    @PostMapping("/new")
    public String createOwner(@Valid @ModelAttribute OwnerDTO owner, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_OWNERS_FORM;
        } else {
            OwnerDTO savedOwner = ownerService.save(owner);
            model.addAttribute("owner", savedOwner);
            return VIEWS_OWNER_OWNERS_REDIRECT + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public ModelAndView showUpdateOwnerForm(@PathVariable Long ownerId) {
        ModelAndView mav = new ModelAndView(VIEWS_OWNER_OWNERS_FORM);
        mav.addObject("owner", ownerService.findById(ownerId));

        return mav;
    }

    @PostMapping("/{ownerId}/edit")
    public String updateOwner(@ModelAttribute OwnerDTO owner, BindingResult result, Model model, @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_OWNERS_FORM;
        } else {
            // Due to the hacking protection provided by the @InitBinder directive which doesn't allow the web layer to set the id property, this will always be null in the incoming model, so we need to set it from the url
            owner.setId(ownerId);

            OwnerDTO savedOwner = ownerService.save(owner);
            model.addAttribute("owner", savedOwner);

            return VIEWS_OWNER_OWNERS_REDIRECT + savedOwner.getId();
        }
    }
}
