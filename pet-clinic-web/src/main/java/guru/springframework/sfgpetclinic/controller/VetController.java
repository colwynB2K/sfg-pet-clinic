package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.dto.VetDTO;
import guru.springframework.sfgpetclinic.service.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@Slf4j
public class VetController {

    private static final String VIEWS_VETS_LIST = "vets/list";

    private final VetService vetService;

    @Autowired
    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets", "/vets.html", "vets/index", "vets/index.html"})
    public String listVets(Model model) {

        model.addAttribute("vets", vetService.findAll());

        return VIEWS_VETS_LIST;
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<VetDTO> getVetsJson() {        // @ResponseBody will by default format the response as JSON
        return vetService.findAll();
    }
}
