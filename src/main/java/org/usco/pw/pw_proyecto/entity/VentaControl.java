package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Clase que representa el control de ventas.
 * Se usa para almacenar información relacionada con la venta en una tabla separada 'venta_control'.
 */
@Entity
@Table(name = "venta_control")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaControl {

    /**
     * Identificador único de la venta en el control.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID del producto involucrado en la venta.
     */
    private Long productoId;

    /**
     * Nombre del usuario que realizó la venta.
     */
    private String usuarioNombre;

    /**
     * Fecha y hora exacta en que se registró la venta en el control.
     */
    private Timestamp fechaHora;

}
