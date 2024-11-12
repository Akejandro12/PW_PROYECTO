package org.usco.pw.pw_proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String verProductos() {
        return "productos";
    }
}