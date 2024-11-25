package org.usco.pw.pw_proyecto.service;

import org.usco.pw.pw_proyecto.entity.Producto;

import java.util.List;

/**
 * Interfaz que define los servicios relacionados con la gestión de productos.
 * Proporciona métodos para listar, guardar, obtener, actualizar y eliminar productos.
 */
public interface ProductoServicio {

    /**
     * Lista todos los productos disponibles en el sistema.
     *
     * @return List<Producto> - Lista de todos los productos.
     */
    public List<Producto> listarProductos();

    /**
     * Guarda un nuevo producto en el sistema.
     *
     * @param producto Producto a guardar.
     * @return Producto - El producto que ha sido guardado, posiblemente con cambios como un ID generado.
     */
    public Producto guardarProducto(Producto producto);

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto que se desea obtener.
     * @return Producto - El producto con el ID proporcionado, o null si no se encuentra.
     */
    public Producto obtenerProducto(Long id);

    /**
     * Actualiza un producto existente en el sistema.
     *
     * @param producto Producto con los datos actualizados.
     * @return Producto - El producto actualizado.
     */
    public Producto actualizarProducto(Producto producto);

    /**
     * Elimina un producto del sistema por su ID.
     *
     * @param id ID del producto que se desea eliminar.
     */
    public void eliminarProducto(Long id);
}
