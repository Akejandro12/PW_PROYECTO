package org.usco.pw.pw_proyecto.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    // Inyección de dependencias
    @Autowired
    private ProductoServicioImpl productoServicio;
    @Autowired
    private VentaServicioImpl ventaServicioImpl;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Obtiene el nombre del usuario autenticado.
     *
     * @return El nombre del usuario autenticado, o "Desconocido" si no está autenticado.
     */
    private String obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "Desconocido";
    }

    /**
     * Muestra la página de venta donde se pueden ver los productos disponibles para la venta.
     * Solo accesible por usuarios con los roles ADMIN o VENDEDOR.
     *
     * @param model El modelo para pasar los productos a la vista.
     * @return El nombre de la vista de venta.
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @GetMapping("/vender")
    public String mostrarPaginaVenta(Model model) {
        List<Producto> productos = productoServicio.listarProductos();  // Obtiene la lista de productos disponibles
        model.addAttribute("productos", productos);  // Agrega los productos al modelo
        return "venta";  // Devuelve la vista de la venta
    }

    /**
     * Procesa la venta de los productos seleccionados y actualiza los inventarios.
     *
     * @param productoIds Lista de IDs de productos seleccionados.
     * @param cantidades Lista de cantidades para cada producto seleccionado.
     * @param redirectAttributes Atributos para redirigir con mensajes.
     * @return Redirección a la página de productos.
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    @PostMapping("/procesarVender")
    public String procesarVenta(@RequestParam("productoId") List<Long> productoIds,
                                @RequestParam("cantidad") List<Integer> cantidades,
                                RedirectAttributes redirectAttributes) {
        boolean ventaExitosa = true;
        double totalVenta = 0;

        String usuarioAutenticado = obtenerUsuarioAutenticado();  // Obtiene el usuario autenticado

        // Procesa cada producto de la venta
        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            int cantidad = cantidades.get(i);

            try {
                // Registra la venta de cada producto
                productoServicio.registrarVenta(usuarioAutenticado, productoId, cantidad);

                Producto producto = productoServicio.obtenerProducto(productoId);
                totalVenta += producto.getPrecio() * cantidad;  // Suma el total de la venta

            } catch (Exception e) {
                ventaExitosa = false;
                redirectAttributes.addFlashAttribute("error", "Error al procesar la venta del producto con ID " + productoId + ": " + e.getMessage());
                break;
            }
        }

        // Si la venta fue exitosa, muestra el mensaje con el total de la venta
        if (ventaExitosa) {
            redirectAttributes.addFlashAttribute("mensaje", "Venta realizada exitosamente. Total: " + totalVenta);
        }

        return "redirect:/productos";  // Redirige a la lista de productos
    }

    /**
     * Muestra el historial de ventas. Solo accesible por el rol ADMIN.
     *
     * @param model El modelo para pasar el historial de ventas a la vista.
     * @return El nombre de la vista del historial de ventas.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/historial")
    public String mostrarHistorial(Model model) {
        List<Venta> ventas = ventaServicioImpl.listarVentas();  // Obtiene el historial de ventas
        model.addAttribute("ventas", ventas);  // Agrega el historial al modelo
        return "historial";  // Devuelve la vista del historial
    }

    /**
     * Genera un reporte en formato Excel con el historial de ventas.
     *
     * @return El archivo Excel generado como respuesta.
     * @throws IOException Si ocurre un error al generar el archivo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generarReporteVentas")
    public ResponseEntity<byte[]> generarReporteVentas() throws IOException {

        List<Venta> ventas = ventaServicioImpl.listarVentas();  // Obtiene el historial de ventas

        // Crea un archivo Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Historial de Ventas");

        // Crea la fila de encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Usuario");
        headerRow.createCell(2).setCellValue("Producto ID");
        headerRow.createCell(3).setCellValue("Cantidad");
        headerRow.createCell(4).setCellValue("Fecha y Hora");

        // Llena las filas con los datos de las ventas
        int rowNum = 1;
        for (Venta venta : ventas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(venta.getId());
            row.createCell(1).setCellValue(venta.getUsuario());
            row.createCell(2).setCellValue(venta.getProductoId());
            row.createCell(3).setCellValue(venta.getCantidad());
            row.createCell(4).setCellValue(venta.getFechaHora().toString());
        }

        // Convierte el archivo Excel en un byte array para enviarlo como respuesta
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        // Configura las cabeceras HTTP para la descarga
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ventas_reporte.xlsx");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);  // Devuelve el archivo Excel
    }

    /**
     * Genera un reporte en formato PDF con el historial de ventas utilizando JasperReports.
     *
     * @param response El objeto HttpServletResponse para escribir la respuesta.
     * @throws Exception Si ocurre un error al generar el reporte.
     */
    @GetMapping("/generarReporte")
    public void generarReporte(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");  // Establece el tipo de contenido a PDF
        response.setHeader("Content-Disposition", "inline; filename=reporte_historial.pdf");  // Define el nombre del archivo

        // Compila el reporte Jasper
        JasperReport reporte = JasperCompileManager
                .compileReport(resourceLoader.getResource("classpath:historialVentas.jrxml").getInputStream());

        // Conecta a la base de datos y llena el reporte
        try (Connection conexion = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);

            // Escribe el reporte PDF en la respuesta
            try (OutputStream salida = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, salida);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }

}
