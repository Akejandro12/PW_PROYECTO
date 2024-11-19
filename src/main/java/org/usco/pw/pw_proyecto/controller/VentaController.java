package org.usco.pw.pw_proyecto.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.usco.pw.pw_proyecto.entity.Venta;
import org.usco.pw.pw_proyecto.service.ProductoServicioImpl;
import org.usco.pw.pw_proyecto.service.VentaServicioImpl;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VentaController {

    @Autowired
    private ProductoServicioImpl productoServicio;
    @Autowired
    private VentaServicioImpl ventaServicioImpl;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/historial")
    public String mostrarHistorial(Model model) {
        List<Venta> ventas = ventaServicioImpl.listarVentas(); // Método para obtener el historial
        model.addAttribute("ventas", ventas);
        return "historial";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generarReporteVentas")
    public ResponseEntity<byte[]> generarReporteVentas() throws IOException {
        // Obtener la lista de ventas
        List<Venta> ventas = ventaServicioImpl.listarVentas();

        // Crear un libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Historial de Ventas");

        // Crear una fila para los encabezados
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Usuario");
        headerRow.createCell(2).setCellValue("Producto ID");
        headerRow.createCell(3).setCellValue("Cantidad");
        headerRow.createCell(4).setCellValue("Fecha y Hora");

        // Rellenar las filas con los datos de ventas
        int rowNum = 1;
        for (Venta venta : ventas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(venta.getId());
            row.createCell(1).setCellValue(venta.getUsuario());
            row.createCell(2).setCellValue(venta.getProductoId());
            row.createCell(3).setCellValue(venta.getCantidad());
            row.createCell(4).setCellValue(venta.getFechaHora().toString());
        }

        // Convertir el libro de trabajo a un array de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        // Establecer las cabeceras HTTP para la descarga
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ventas_report.xlsx");

        // Devolver el archivo Excel como una respuesta
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    @GetMapping("/generarReporte")
    public void generarReporte(HttpServletResponse response) throws Exception {
        // Configurar el tipo de contenido y el encabezado de la respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=reporte_historial.pdf");

        // Compilar el reporte
        JasperReport reporte = JasperCompileManager
                .compileReport(resourceLoader.getResource("classpath:historialVentas.jrxml").getInputStream());

        // Usar la conexión de base de datos
        try (Connection conexion = dataSource.getConnection()) {
            // Generar el reporte sin parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);

            // Exportar el reporte como PDF y enviarlo en la respuesta
            try (OutputStream salida = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, salida);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }


}
