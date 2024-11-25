package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Rol en el sistema.
 * Utiliza anotaciones de JPA para mapear la clase a una tabla en la base de datos.
 * También se utiliza Lombok para la generación automática de métodos como getters, setters y constructores.
 */
@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {

    /**
     * ID único del rol en la base de datos, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol. Este campo describe el nombre del rol (por ejemplo, "ADMIN", "USUARIO", etc.).
     */
    private String nombre;

    /**
     * Constructor para crear un rol con solo el nombre.
     *
     * @param nombre El nombre del rol.
     */
    public Rol(String nombre) {
        this.nombre = nombre;
    }
}







