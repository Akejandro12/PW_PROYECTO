package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Representa una venta realizada en el sistema.
 * Esta entidad está mapeada a la tabla 'venta' en la base de datos.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venta {

    /**
     * Identificador único de la venta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que realizó la venta.
     */
    private String usuario;

    /**
     * ID del producto vendido.
     */
    private Long productoId;

    /**
     * Cantidad de productos vendidos.
     */
    private int cantidad;

    /**
     * Fecha y hora en la que se realizó la venta.
     */
    private Timestamp fechaHora;

}

