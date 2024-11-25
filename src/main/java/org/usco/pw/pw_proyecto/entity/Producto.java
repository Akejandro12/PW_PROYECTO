package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Producto en la base de datos.
 * Utiliza anotaciones de JPA para mapear la clase a una tabla en la base de datos.
 * También se usa Lombok para la generación automática de métodos como getters, setters y constructores.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    /**
     * ID único del producto en la base de datos, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto. No puede ser nulo y su longitud máxima es de 20 caracteres.
     */
    @NotNull
    @Size(max = 20)
    private String nombre;

    /**
     * Descripción del producto. No puede ser nula.
     */
    @NotNull
    private String descripcion;

    /**
     * Cantidad disponible del producto en inventario. No puede ser nula.
     */
    @NotNull
    private Integer cantidad;

    /**
     * Precio del producto. No puede ser nulo.
     */
    @NotNull
    private Double precio;

}

