package org.usco.pw.pw_proyecto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO (Data Transfer Object) utilizada para representar los datos de un usuario
 * al registrarse en el sistema.
 * Esta clase se utiliza para transferir los datos de registro entre capas de la aplicación.
 */
@Data  // Genera los métodos getter, setter, toString, equals y hashCode automáticamente.
@AllArgsConstructor  // Genera un constructor con todos los parámetros.
@NoArgsConstructor   // Genera un constructor sin parámetros.
public class UsuarioRegistroDTO {

    private Long id;  // Identificador único del usuario.
    private String nombre;  // Nombre del usuario.
    private String apellido;  // Apellido del usuario.
    private String email;  // Correo electrónico del usuario.
    private String password;  // Contraseña del usuario.

    /**
     * Constructor personalizado para inicializar los datos del usuario sin incluir el id.
     * Este constructor es útil al crear un objeto para registrar un usuario, sin necesidad de un id previo.
     *
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public UsuarioRegistroDTO(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }
}
