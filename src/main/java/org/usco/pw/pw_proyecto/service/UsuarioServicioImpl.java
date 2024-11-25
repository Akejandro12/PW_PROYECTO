package org.usco.pw.pw_proyecto.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

/**
 * Implementación del servicio para gestionar operaciones relacionadas con los usuarios en el sistema.
 * Proporciona métodos para registrar, obtener, eliminar y cargar detalles del usuario.
 */
@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RolRepositorio rolRepositorio;

    /**
     * Constructor que inicializa el repositorio de usuarios.
     *
     * @param usuarioRepositorio El repositorio de usuarios.
     */
    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     *
     * @param registroDTO El DTO con la información del usuario a registrar.
     * @return El usuario guardado con su ID asignado.
     * @throws RuntimeException Si no se encuentra el rol "ROLE_USER" en la base de datos.
     */
    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {

        // Buscar el rol "ROLE_USER" en la base de datos
        Rol rolUsuario = rolRepositorio.findByNombre("ROLE_USER");
        if (rolUsuario == null) {
            throw new RuntimeException("Rol 'ROLE_USER' no encontrado en la base de datos.");
        }

        // Crear un nuevo objeto Usuario con los datos del DTO y el rol asignado
        Usuario usuario = new Usuario(registroDTO.getNombre(),
                registroDTO.getApellido(), registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(rolUsuario));

        // Guardar el usuario en el repositorio
        return usuarioRepositorio.save(usuario);
    }

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param usuarioId El ID del usuario a eliminar.
     * @throws RuntimeException Si el usuario no se encuentra en la base de datos.
     */
    @Transactional
    public void eliminarUsuario(Long usuarioId) {

        // Buscar el usuario por ID
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminar el usuario
        usuarioRepositorio.delete(usuario);
    }

    /**
     * Carga los detalles del usuario mediante su nombre de usuario (email).
     *
     * @param username El nombre de usuario (email) del usuario.
     * @return Los detalles del usuario.
     * @throws UsernameNotFoundException Si el usuario no es encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario por su email
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        // Mapear los roles a autoridades y devolver un objeto User
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    /**
     * Mapea una colección de roles a una colección de autoridades de Spring Security.
     *
     * @param roles Los roles a mapear.
     * @return Una colección de autoridades (SimpleGrantedAuthority).
     */
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de todos los usuarios registrados en el sistema.
     *
     * @return Una lista de usuarios.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    /**
     * Obtiene un usuario mediante su nombre de usuario (email).
     *
     * @param username El nombre de usuario (email) del usuario.
     * @return El usuario con el email especificado.
     */
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepositorio.findByEmail(username);
    }

    /**
     * Obtiene el usuario autenticado en el sistema.
     *
     * @return El usuario autenticado.
     */
    public Usuario obtenerUsuarioAutenticado() {
        // Obtener el nombre de usuario (email) del contexto de seguridad
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return obtenerUsuarioPorUsername(username);
    }
}
