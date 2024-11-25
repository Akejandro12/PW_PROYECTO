package org.usco.pw.pw_proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar las rutas principales de la aplicación,
 * incluyendo la redirección a la página de inicio y el manejo de errores como
 * el acceso denegado (403).
 */
@Controller
public class RootController {

    /**
     * Método que redirige la solicitud a la página de inicio.
     *
     * @return Redirección a "/inicio".
     */
    @GetMapping("/")  // Mapeo para la solicitud GET a la ruta raíz "/".
    public String redirectToInicio() {
        return "redirect:/inicio";  // Redirige a la página de inicio ("/inicio").
    }

    /**
     * Método que muestra la vista de la página de inicio.
     *
     * @return La vista "inicio".
     */
    @GetMapping("/inicio")  // Mapeo para la solicitud GET a "/inicio".
    public String mostrarInicio() {
        return "inicio";  // Devuelve la vista "inicio".
    }

    /**
     * Método que muestra la vista de error 403 (Acceso denegado).
     *
     * @return La vista de error 403.
     */
    @GetMapping("/403")  // Mapeo para la solicitud GET a "/403".
    public String mostrar403() {
        return "403";  // Devuelve la vista de error 403.
    }

}
