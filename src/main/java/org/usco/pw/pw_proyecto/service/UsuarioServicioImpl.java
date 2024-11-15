package org.usco.pw.pw_proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.usco.pw.pw_proyecto.controller.dto.UsuarioRegistroDTO;
import org.usco.pw.pw_proyecto.entity.Rol;
import org.usco.pw.pw_proyecto.entity.Usuario;
import org.usco.pw.pw_proyecto.repository.RolRepositorio;
import org.usco.pw.pw_proyecto.repository.UsuarioRepositorio;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    // Inyectamos BCryptPasswordEncoder de manera estándar en lugar de a través del constructor
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RolRepositorio rolRepositorio;

    // Inyectamos el repositorio en el constructor
    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        // Buscar el rol 'ROLE_USER' existente en la base de datos
        Rol rolUsuario = rolRepositorio.findByNombre("ROLE_USER");
        if (rolUsuario == null) {
            throw new RuntimeException("Rol 'ROLE_USER' no encontrado en la base de datos.");
        }

        Usuario usuario = new Usuario(registroDTO.getNombre(),
                registroDTO.getApellido(),registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(rolUsuario));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
}
