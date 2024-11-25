package org.usco.pw.pw_proyecto.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "inicio";
    }
    @GetMapping("/403")
    public String mostrar403() {
        return "403";
    }

}
