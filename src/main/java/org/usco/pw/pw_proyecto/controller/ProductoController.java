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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productos/editar/{id}")
    public String mostrarEditarProducto(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoServicio.obtenerProducto(id));
        return "editarProducto";

    }
    @PostMapping("/productos/{id}")
    public String actualizarProducto(@PathVariable Long id,@ModelAttribute("producto") Producto producto, Model model) {
        Producto productoActual = productoServicio.obtenerProducto(id);
        productoActual.setId(id);
        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setCantidad(producto.getCantidad());

        productoServicio.actualizarProducto(productoActual);
        return "redirect:/productos";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return "redirect:/productos";
    }



}