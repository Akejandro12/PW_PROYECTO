package org.usco.pw.pw_proyecto.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usco.pw.pw_proyecto.entity.Producto;
import org.usco.pw.pw_proyecto.entity.Venta;
import org.usco.pw.pw_proyecto.entity.VentaControl;
import org.usco.pw.pw_proyecto.repository.ProductoRepositorio;
import org.usco.pw.pw_proyecto.repository.VentaControlRepositorio;
import org.usco.pw.pw_proyecto.repository.VentaRepositorio;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private VentaRepositorio ventaRepository;// Inyección del repositorio de ventas

    @Autowired
    private VentaControlRepositorio ventaControlRepository;

    public List<Producto> getAllProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public Producto obtenerProducto(Long id) {
        return productoRepositorio.findById(id).get();
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }


    @Transactional
    public void registrarVenta(String usuario, Long productoId, int cantidad) {

        Producto producto = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


        if (producto.getCantidad() < cantidad) {
            throw new RuntimeException("No hay suficiente inventario para el producto " + producto.getNombre());
        }


        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepositorio.save(producto);


        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setProductoId(productoId);
        venta.setCantidad(cantidad);
        venta.setFechaHora(new Timestamp(System.currentTimeMillis()));
        ventaRepository.save(venta);


        VentaControl ventaControl = new VentaControl();
        ventaControl.setProductoId(productoId);
        ventaControl.setUsuarioNombre(usuario);
        ventaControl.setFechaHora(new Timestamp(System.currentTimeMillis()));
        ventaControlRepository.save(ventaControl);
    }
}






