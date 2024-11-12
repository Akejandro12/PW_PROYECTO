package org.usco.pw.pw_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.usco.pw.pw_proyecto.entity.Producto;
import org.usco.pw.pw_proyecto.service.ProductoServicioImpl;

import java.util.List;

@Controller
public class VentaController {

    @Autowired
    private ProductoServicioImpl productoServicio;
    @GetMapping("/vender")
    public String mostrarPaginaVenta(Model model) {
        List<Producto> productos = productoServicio.listarProductos();
        model.addAttribute("productos", productos);
        return "venta";
    }
    @PostMapping("/procesarVender")
    public String procesarVenta(@RequestParam("productoId") Long productoId,
                                @RequestParam("cantidad") int cantidad,
                                Model model) {
        Producto producto = productoServicio.obtenerProducto(productoId);

        if (producto != null && producto.getCantidad() >= cantidad) {
            producto.setCantidad(producto.getCantidad() - cantidad); // Resta la cantidad vendida del inventario
            productoServicio.actualizarProducto(producto); // Guarda el producto con la cantidad actualizada
            model.addAttribute("mensaje", "Venta realizada exitosamente.");
        } else {
            model.addAttribute("error", "No hay suficiente inventario para realizar la venta.");
        }

        return "redirect:/productos"; // Redirige a una página de confirmación o error
    }
}