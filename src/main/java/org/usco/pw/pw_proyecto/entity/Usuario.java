package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Representa a un Usuario en el sistema.
 * Esta entidad está mapeada a la tabla 'usuarios' en la base de datos, con una restricción única para el campo 'email'.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    /**
     * Identificador único del usuario en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Apellido del usuario.
     */
    @Column(name = "apellido")
    private String apellido;

    /**
     * Correo electrónico del usuario. Debe ser único en la base de datos.
     */
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Roles asignados al usuario. Relación de muchos a muchos con la entidad 'Rol'.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "usuarios_roles", // Nombre de la tabla intermedia para la relación muchos a muchos
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), // Columna que hace referencia al usuario
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id") // Columna que hace referencia al rol
    )
    private Collection<Rol> roles;

    /**
     * Constructor adicional para crear un usuario con nombre, apellido, correo, contraseña y roles.
     *
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param roles Colección de roles asignados al usuario.
     */
    public Usuario(String nombre, String apellido, String email, String password, Collection<Rol> roles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}

