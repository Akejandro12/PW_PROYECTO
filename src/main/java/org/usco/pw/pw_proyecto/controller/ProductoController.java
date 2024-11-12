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

@Controller
public class ProductoController {

    @Autowired
    private ProductoServicioImpl productoServicio;

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoServicio.listarProductos());
        if (model.containsAttribute("mensaje")) {
            model.addAttribute("mensaje", model.getAttribute("mensaje"));
        }
        if (model.containsAttribute("error")) {
            model.addAttribute("error", model.getAttribute("error"));
        }
        return "productos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productos/crearProducto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearProducto";
    }

    @PostMapping("/productos")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        productoServicio.guardarProducto(producto);
        return "redirect:/productos";
    }

    // Cambiar la ruta aquí para que sea /productos/editar/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo) {
        Producto producto = productoServicio.obtenerProducto(id);
        if (producto != null) {
            modelo.addAttribute("producto", producto);
            return "editarProducto"; // Nombre de la plantilla Thymeleaf
        } else {
            // Redirigir o mostrar error si no se encuentra el producto
            return "redirect:/productos";
        }
    }

    // Cambiar la ruta aquí también para que sea /productos/editar/{id}
    @PostMapping("/productos/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        Producto productoExistente = productoServicio.obtenerProducto(id);
        if (productoExistente != null) {
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setPrecio(producto.getPrecio());  // Aquí actualizas el precio

            productoServicio.actualizarProducto(productoExistente);

            redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado con éxito.");
            return "redirect:/productos";
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el producto.");
            return "redirect:/productos";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return "redirect:/productos";
    }
}
