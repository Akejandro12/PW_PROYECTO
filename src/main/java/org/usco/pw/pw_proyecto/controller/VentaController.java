package org.usco.pw.pw_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.usco.pw.pw_proyecto.entity.Producto;
import org.usco.pw.pw_proyecto.service.ProductoServicioImpl;

import java.util.List;

@Controller
public class VentaController {

    @Autowired
    private ProductoServicioImpl productoServicio;

    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @GetMapping("/vender")
    public String mostrarPaginaVenta(Model model) {
        List<Producto> productos = productoServicio.listarProductos();
        model.addAttribute("productos", productos);
        return "venta";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @PostMapping("/procesarVender")
    public String procesarVenta(@RequestParam("productoId") List<Long> productoIds,
                                @RequestParam("cantidad") List<Integer> cantidades,
                                RedirectAttributes redirectAttributes) {
        boolean ventaExitosa = true;
        double totalVenta = 0;  // Nuevo campo para almacenar el total de la venta

        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            int cantidad = cantidades.get(i);

            Producto producto = productoServicio.obtenerProducto(productoId);

            if (producto != null && producto.getCantidad() >= cantidad) {
                producto.setCantidad(producto.getCantidad() - cantidad);
                productoServicio.actualizarProducto(producto);
                totalVenta += producto.getPrecio() * cantidad;  // Agregar al total de la venta
            } else {
                ventaExitosa = false;
                redirectAttributes.addFlashAttribute("error", "No hay suficiente inventario para el producto " + producto.getNombre() + ".");
                break;
            }
        }

        if (ventaExitosa) {
            redirectAttributes.addFlashAttribute("mensaje", "Venta realizada exitosamente. Total: " + totalVenta);
        }

        return "redirect:/productos";
    }

}
