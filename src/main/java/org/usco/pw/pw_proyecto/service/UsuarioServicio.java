package org.usco.pw.pw_proyecto.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.usco.pw.pw_proyecto.controller.dto.UsuarioRegistroDTO;
import org.usco.pw.pw_proyecto.entity.Usuario;

import java.util.List;

public interface UsuarioServicio extends UserDetailsService {

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();

}
