package org.usco.pw.pw_proyecto.service;

import org.usco.pw.pw_proyecto.entity.Venta;

import java.util.List;

/**
 * Interfaz que define los servicios relacionados con las operaciones de venta.
 * Proporciona un método para listar todas las ventas registradas en el sistema.
 */
public interface VentaServicio {

    /**
     * Obtiene una lista de todas las ventas registradas en el sistema.
     *
     * @return Una lista de objetos {@link Venta} que representan las ventas en el sistema.
     */
    public List<Venta> listarVentas();
}
