package org.usco.pw.pw_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.usco.pw.pw_proyecto.service.UsuarioServicio;

/**
 * Controlador encargado de gestionar el registro y la administración de usuarios.
 * Permite la visualización, eliminación y gestión de los usuarios dentro de la aplicación.
 */
@Controller
public class RegistroController {

    @Autowired
    private UsuarioServicio servicio;  // Servicio que maneja la lógica de negocio de los usuarios.

    /**
     * Método para mostrar la página de inicio de sesión.
     *
     * @return La vista de inicio de sesión.
     */
    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";  // Devuelve la vista de inicio de sesión.
    }

    /**
     * Método para mostrar la lista de usuarios registrados en el sistema.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param modelo El modelo que contiene los atributos a pasar a la vista.
     * @return La vista que muestra la lista de usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Asegura que solo los usuarios con rol ADMIN puedan acceder.
    @GetMapping("/usuarios")
    public String verPaginaDeInicio(Model modelo) {
        modelo.addAttribute("usuarios", servicio.listarUsuarios());  // Carga la lista de usuarios.
        return "usuarios";  // Devuelve la vista que muestra los usuarios.
    }

    /**
     * Método para eliminar un usuario del sistema.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param id El identificador del usuario a eliminar.
     * @return Redirige a la página de la lista de usuarios después de la eliminación.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Asegura que solo los usuarios con rol ADMIN puedan acceder.
    @PostMapping("/usuarios/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        servicio.eliminarUsuario(id);  // Elimina el usuario especificado por su ID.
        return "redirect:/usuarios";  // Redirige a la lista de usuarios después de la eliminación.
    }

}


