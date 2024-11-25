package org.usco.pw.pw_proyecto.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.usco.pw.pw_proyecto.entity.Producto;
import org.usco.pw.pw_proyecto.service.ProductoServicio;
import org.usco.pw.pw_proyecto.service.ProductoServicioImpl;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con los productos.
 * Permite listar, crear, editar y eliminar productos en el sistema.
 */
@Controller
public class ProductoController {

    @Autowired
    private ProductoServicioImpl productoServicio;  // Servicio que maneja la lógica de negocio de los productos.

    /**
     * Método para listar todos los productos disponibles en el sistema.
     * Se cargan los productos desde el servicio y se añaden al modelo para la vista.
     *
     * @param model El modelo que contiene los atributos a pasar a la vista.
     * @return La vista de productos con la lista de productos cargada.
     */
    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoServicio.listarProductos());  // Carga los productos.

        // Se verifican si hay mensajes para mostrar (errores o éxitos).
        if (model.containsAttribute("mensaje")) {
            model.addAttribute("mensaje", model.getAttribute("mensaje"));
        }
        if (model.containsAttribute("error")) {
            model.addAttribute("error", model.getAttribute("error"));
        }

        return "productos";  // Devuelve la vista "productos".
    }

    /**
     * Método para mostrar el formulario de creación de un nuevo producto.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param model El modelo que contiene los atributos a pasar a la vista.
     * @return La vista de formulario de creación de producto.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Asegura que solo los usuarios con rol ADMIN puedan acceder.
    @GetMapping("/productos/crearProducto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());  // Inicializa un nuevo objeto Producto.
        return "crearProducto";  // Devuelve la vista para crear el producto.
    }

    /**
     * Método para guardar un nuevo producto en la base de datos.
     * Recibe los datos del producto desde el formulario de creación.
     *
     * @param producto El producto a guardar.
     * @param result Resultado de la validación de los datos del producto.
     * @param model El modelo que contiene los atributos a pasar a la vista.
     * @return Redirige a la vista de productos.
     */
    @PostMapping("/productos")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "crearProducto";  // Si hay errores de validación, muestra el formulario nuevamente.
        }
        productoServicio.guardarProducto(producto);  // Guarda el producto en la base de datos.
        return "redirect:/productos";  // Redirige a la lista de productos.
    }

    /**
     * Método para mostrar el formulario de edición de un producto existente.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param id El identificador del producto a editar.
     * @param modelo El modelo que contiene los atributos a pasar a la vista.
     * @return La vista para editar el producto.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Asegura que solo los usuarios con rol ADMIN puedan acceder.
    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo) {
        Producto producto = productoServicio.obtenerProducto(id);  // Obtiene el producto por su ID.

        if (producto != null) {
            modelo.addAttribute("producto", producto);  // Añade el producto al modelo.
            return "editarProducto";  // Devuelve la vista de edición de producto.
        } else {
            return "redirect:/productos";  // Si no se encuentra el producto, redirige a la lista de productos.
        }
    }

    /**
     * Método para actualizar un producto existente en la base de datos.
     * Recibe los datos modificados del producto desde el formulario de edición.
     *
     * @param id El identificador del producto a actualizar.
     * @param producto El producto con los nuevos datos.
     * @param redirectAttributes Los atributos a agregar a la redirección.
     * @return Redirige a la lista de productos.
     */
    @PostMapping("/productos/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        Producto productoExistente = productoServicio.obtenerProducto(id);  // Busca el producto existente.

        if (productoExistente != null) {
            // Actualiza los atributos del producto existente con los nuevos valores.
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setPrecio(producto.getPrecio());
            productoServicio.actualizarProducto(productoExistente);  // Guarda el producto actualizado.

            redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado con éxito.");  // Mensaje de éxito.
            return "redirect:/productos";  // Redirige a la lista de productos.
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el producto.");  // Mensaje de error si no se encuentra el producto.
            return "redirect:/productos";  // Redirige a la lista de productos.
        }
    }

    /**
     * Método para eliminar un producto existente de la base de datos.
     * Solo accesible por usuarios con rol de ADMIN.
     *
     * @param id El identificador del producto a eliminar.
     * @return Redirige a la lista de productos.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Asegura que solo los usuarios con rol ADMIN puedan acceder.
    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);  // Elimina el producto de la base de datos.
        return "redirect:/productos";  // Redirige a la lista de productos.
    }
}
