package org.usco.pw.pw_proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.usco.pw.pw_proyecto.controller.dto.UsuarioRegistroDTO;
import org.usco.pw.pw_proyecto.service.UsuarioServicio;

/**
 * Controlador encargado de gestionar el registro de nuevos usuarios.
 * Permite la visualización del formulario de registro y el procesamiento de la creación de una cuenta de usuario.
 */
@Controller
@RequestMapping("/registro")  // Mapea las peticiones a la ruta "/registro".
public class RegistroUsuarioController {

    private UsuarioServicio usuarioServicio;  // Servicio que maneja la lógica de registro de usuarios.

    /**
     * Constructor que inicializa el servicio de usuarios.
     *
     * @param usuarioServicio El servicio encargado de la gestión de usuarios.
     */
    public RegistroUsuarioController(UsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Método que crea una nueva instancia de UsuarioRegistroDTO antes de cada solicitud.
     * Se utiliza para enlazar los datos del formulario con el objeto de tipo DTO.
     *
     * @return Una nueva instancia de UsuarioRegistroDTO.
     */
    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();  // Devuelve un nuevo objeto DTO vacío para el formulario.
    }

    /**
     * Método que muestra el formulario de registro de usuario.
     *
     * @return La vista del formulario de registro.
     */
    @GetMapping  // Mapeo para las solicitudes GET a "/registro".
    public String mostrarFormularioDeRegistro() {
        return "registro";  // Devuelve la vista que contiene el formulario de registro.
    }

    /**
     * Método que procesa el registro de un nuevo usuario.
     * Recibe los datos del formulario, los convierte a un DTO y los pasa al servicio para su almacenamiento.
     *
     * @param registroDTO El objeto que contiene los datos del nuevo usuario.
     * @return Redirige a la página de registro con un mensaje de éxito.
     */
    @PostMapping  // Mapeo para las solicitudes POST a "/registro".
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
        usuarioServicio.guardar(registroDTO);  // Guarda el nuevo usuario utilizando el servicio.
        return "redirect:/registro?exito";  // Redirige a la página de registro con un parámetro de éxito en la URL.
    }
}
