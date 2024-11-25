package org.usco.pw.pw_proyecto.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.usco.pw.pw_proyecto.controller.dto.UsuarioRegistroDTO;
import org.usco.pw.pw_proyecto.entity.Usuario;

import java.util.List;

/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios en el sistema.
 * Extiende {@link UserDetailsService} para la integración con la seguridad de Spring.
 * Proporciona métodos para registrar, listar y eliminar usuarios.
 */
public interface UsuarioServicio extends UserDetailsService {

    /**
     * Guarda un nuevo usuario en el sistema.
     *
     * @param registroDTO El DTO con la información del usuario a registrar.
     * @return Usuario - El usuario que se ha guardado, posiblemente con un ID generado.
     */
    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    /**
     * Obtiene la lista de todos los usuarios registrados en el sistema.
     *
     * @return List<Usuario> - Lista de todos los usuarios.
     */
    public List<Usuario> listarUsuarios();

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param id ID del usuario a eliminar.
     */
    void eliminarUsuario(Long id);
}

