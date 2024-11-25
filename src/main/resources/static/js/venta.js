// Función que valida el formulario antes de enviarlo.
function validarFormulario() {
    // Selecciona todos los elementos con la clase "producto" (campos de selección de productos).
    var productos = document.querySelectorAll(".producto");
    // Selecciona todos los elementos con la clase "cantidad" (campos de entrada de cantidades).
    var cantidades = document.querySelectorAll(".cantidad");

    // Recorre cada producto y su cantidad correspondiente en el formulario.
    for (let i = 0; i < productos.length; i++) {
        // Obtiene el valor del producto seleccionado (ID del producto).
        var productoId = productos[i].value;
        // Obtiene el valor de la cantidad introducida para ese producto.
        var cantidad = cantidades[i].value;

        // Busca el elemento <option> correspondiente al producto seleccionado.
        var productoSeleccionado = productos[i].querySelector(`option[value="${productoId}"]`);
        // Obtiene la cantidad de inventario disponible del atributo "data-inventario" del <option>.
        var inventarioDisponible = productoSeleccionado ? parseInt(productoSeleccionado.getAttribute("data-inventario")) : 0;

        // Imprime en la consola información de la validación actual (para depuración).
        console.log(`Producto ID: ${productoId}, Cantidad solicitada: ${cantidad}, Inventario disponible: ${inventarioDisponible}`);

        // Verifica si la cantidad solicitada es mayor que la cantidad disponible en inventario.
        if (cantidad > inventarioDisponible) {
            // Si no hay suficiente inventario, muestra un mensaje de error y detiene el envío del formulario.
            alert("No hay suficiente inventario para el producto seleccionado en la fila " + (i + 1));
            return false; // Detiene el envío del formulario (retorna 'false').
        }

        // Verifica si no se ha seleccionado un producto o si la cantidad es inválida (menor o igual a 0).
        if (!productoId || cantidad <= 0) {
            // Si hay un error, muestra un mensaje de error y detiene el envío del formulario.
            alert("Por favor, seleccione un producto y una cantidad válida en la fila " + (i + 1));
            return false; // Detiene el envío del formulario (retorna 'false').
        }
    }

    // Si todos los productos y cantidades son válidos, permite el envío del formulario.
    return true; // Retorna 'true', indicando que la validación pasó.
}

// Función para agregar un nuevo campo de producto al formulario.
function agregarProducto() {
    // Obtiene el formulario que contiene los productos.
    var formulario = document.getElementById("formulario-productos");
    // Clona la primera fila del formulario (que contiene los campos de selección de producto y cantidad).
    var nuevoProducto = formulario.children[0].cloneNode(true);

    // Restaura los valores de los campos del nuevo producto para que estén vacíos o por defecto.
    nuevoProducto.querySelector(".producto").value = ""; // Resetea el campo de selección del producto.
    nuevoProducto.querySelector(".cantidad").value = 1; // Establece la cantidad predeterminada a 1.

    // Añade el nuevo producto clonado al final del formulario.
    formulario.appendChild(nuevoProducto);
}
