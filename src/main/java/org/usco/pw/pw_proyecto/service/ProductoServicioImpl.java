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
import java.util.List;

/**
 * Implementación del servicio relacionado con la gestión de productos y ventas.
 * Proporciona métodos para realizar operaciones de CRUD sobre los productos y gestionar el registro de ventas.
 */
@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private VentaRepositorio ventaRepository; // Inyección del repositorio de ventas

    @Autowired
    private VentaControlRepositorio ventaControlRepository;

    /**
     * Obtiene todos los productos disponibles en el sistema.
     *
     * @return List<Producto> - Lista de todos los productos.
     */
    public List<Producto> getAllProductos() {
        return productoRepositorio.findAll();
    }

    /**
     * Lista todos los productos disponibles en el sistema.
     *
     * @return List<Producto> - Lista de productos.
     */
    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    /**
     * Guarda un nuevo producto en el sistema.
     *
     * @param producto Producto a guardar.
     * @return Producto - El producto guardado, posiblemente con cambios como un ID generado.
     */
    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto a obtener.
     * @return Producto - El producto con el ID proporcionado.
     * @throws RuntimeException Si no se encuentra el producto.
     */
    @Override
    public Producto obtenerProducto(Long id) {
        return productoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    /**
     * Actualiza un producto existente en el sistema.
     *
     * @param producto Producto con los datos actualizados.
     * @return Producto - El producto actualizado.
     */
    @Override
    public Producto actualizarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    /**
     * Elimina un producto del sistema por su ID.
     *
     * @param id ID del producto a eliminar.
     */
    @Override
    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }

    /**
     * Registra una venta de un producto. Este método también actualiza la cantidad de inventario del producto
     * y guarda el registro de la venta en las entidades correspondientes.
     *
     * @param usuario El nombre del usuario que realiza la venta.
     * @param productoId El ID del producto que se está vendiendo.
     * @param cantidad La cantidad del producto que se está vendiendo.
     * @throws RuntimeException Si no hay suficiente inventario o si el producto no se encuentra.
     */
    @Transactional
    public void registrarVenta(String usuario, Long productoId, int cantidad) {

        // Obtiene el producto por ID
        Producto producto = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verifica si hay suficiente inventario
        if (producto.getCantidad() < cantidad) {
            throw new RuntimeException("No hay suficiente inventario para el producto " + producto.getNombre());
        }

        // Actualiza el inventario del producto
        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepositorio.save(producto);

        // Registra la venta
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setProductoId(productoId);
        venta.setCantidad(cantidad);
        venta.setFechaHora(new Timestamp(System.currentTimeMillis()));
        ventaRepository.save(venta);

        // Registra el control de la venta
        VentaControl ventaControl = new VentaControl();
        ventaControl.setProductoId(productoId);
        ventaControl.setUsuarioNombre(usuario);
        ventaControl.setFechaHora(new Timestamp(System.currentTimeMillis()));
        ventaControlRepository.save(ventaControl);
    }
}







