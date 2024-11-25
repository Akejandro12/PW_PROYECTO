package org.usco.pw.pw_proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usco.pw.pw_proyecto.entity.Venta;
import org.usco.pw.pw_proyecto.repository.VentaRepositorio;

import java.util.List;

/**
 * Implementación del servicio de ventas.
 * Proporciona la lógica de negocio para interactuar con las ventas registradas en el sistema.
 *
 * <p>Esta clase implementa la interfaz {@link VentaServicio} y utiliza el repositorio {@link VentaRepositorio}
 * para recuperar los datos relacionados con las ventas de la base de datos.</p>
 */
@Service
public class VentaServicioImpl implements VentaServicio {

    @Autowired
    VentaRepositorio ventaRepositorio;

    /**
     * Recupera una lista de todas las ventas registradas en el sistema.
     *
     * @return Una lista de objetos {@link Venta} que representan todas las ventas en el sistema.
     */
    public List<Venta> listarVentas() {
        return ventaRepositorio.findAll();
    }
}
