package org.usco.pw.pw_proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

import java.util.Date;

@Entity
@Table(name = "venta_control")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productoId;
    private String usuarioNombre;
    private Timestamp fechaHora;

}