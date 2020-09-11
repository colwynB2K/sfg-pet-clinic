package guru.springframework.sfgpetclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {
        return "index";
    }

    @GetMapping("/oups")
    public String oupsHandler() {
        return "notimplemented";
    }
}
