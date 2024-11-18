package org.usco.pw.pw_proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usco.pw.pw_proyecto.entity.Venta;
import org.usco.pw.pw_proyecto.repository.VentaRepositorio;

import java.util.List;

@Service
public class VentaServicioImpl implements VentaServicio {

    @Autowired
    VentaRepositorio ventaRepositorio;


    public List<Venta> listarVentas() {
        return ventaRepositorio.findAll();
    }
}