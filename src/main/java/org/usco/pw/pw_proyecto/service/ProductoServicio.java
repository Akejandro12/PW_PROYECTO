package org.usco.pw.pw_proyecto.service;


import org.usco.pw.pw_proyecto.entity.Producto;

import java.util.List;

public interface ProductoServicio {
    public List<Producto> listarProductos();

    public Producto guardarProducto(Producto producto);

    public Producto obtenerProducto(Long id);

    public Producto actualizarProducto(Producto producto);

    public void eliminarProducto(Long id);


}
