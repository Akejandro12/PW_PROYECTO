package org.usco.pw.pw_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // Método para obtener el usuario autenticado
    private String obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "Desconocido";
    }

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
        double totalVenta = 0;  // Almacenamos el total de la venta

        // Obtener el nombre del usuario autenticado
        String usuarioAutenticado = obtenerUsuarioAutenticado();

        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            int cantidad = cantidades.get(i);

            // Registrar la venta utilizando el servicio
            try {
                productoServicio.registrarVenta(usuarioAutenticado, productoId, cantidad);

                // Si todo salió bien, calculamos el total
                Producto producto = productoServicio.obtenerProducto(productoId);  // Asumimos que tienes un método para obtener el producto
                totalVenta += producto.getPrecio() * cantidad;

            } catch (Exception e) {
                ventaExitosa = false;
                redirectAttributes.addFlashAttribute("error", "Error al procesar la venta del producto con ID " + productoId + ": " + e.getMessage());
                break;
            }
        }

        if (ventaExitosa) {
            redirectAttributes.addFlashAttribute("mensaje", "Venta realizada exitosamente. Total: " + totalVenta);
        }

        return "redirect:/productos";  // Redirigir a la lista de productos
    }

}
