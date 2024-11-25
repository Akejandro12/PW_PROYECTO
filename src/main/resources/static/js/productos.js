// Escucha el evento 'DOMContentLoaded' que se dispara cuando el DOM está completamente cargado
document.addEventListener("DOMContentLoaded", () => {

    // Selecciona todas las celdas con la clase 'stock' (asumido que representan la cantidad de stock de los productos)
    const stockCells = document.querySelectorAll(".stock");

    // Itera sobre cada celda de stock para procesarla individualmente
    stockCells.forEach(cell => {

        // Convierte el contenido de la celda de stock en un número entero
        const stock = parseInt(cell.textContent.trim(), 10);

        // Si el stock es menor o igual a 2 unidades
        if (stock <= 2) {

            // Obtiene el nombre del producto de la primera celda en la misma fila (asumido que es el nombre del producto)
            const productName = cell.parentElement.querySelector("td:first-child").textContent.trim();

            // Muestra una alerta con una advertencia sobre el stock bajo del producto
            alert(`Advertencia: Quedan pocas unidades del producto "${productName}". Stock actual: ${stock}`);
        }
    });
});
