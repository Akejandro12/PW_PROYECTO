package org.usco.pw.pw_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.usco.pw.pw_proyecto.service.UsuarioServicio;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioServicio servicio;

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios")
    public String verPaginaDeInicio(Model modelo) {
        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        return "usuarios";
    }
}

