package org.usco.pw.pw_proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usco.pw.pw_proyecto.entity.Producto;
import org.usco.pw.pw_proyecto.repository.ProductoRepositorio;

import java.util.List;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<Producto> getAllProductos() {return productoRepositorio.findAll();}
    public void saveProducto(Producto producto) {productoRepositorio.save(producto);}

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
}




