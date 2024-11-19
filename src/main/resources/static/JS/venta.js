function validarFormulario() {
    var productos = document.querySelectorAll(".producto");
    var cantidades = document.querySelectorAll(".cantidad");

    for (let i = 0; i < productos.length; i++) {
        var productoId = productos[i].value;
        var cantidad = cantidades[i].value;

        var productoSeleccionado = productos[i].querySelector(`option[value="${productoId}"]`);
        var inventarioDisponible = productoSeleccionado ? parseInt(productoSeleccionado.getAttribute("data-inventario")) : 0;

        console.log(`Producto ID: ${productoId}, Cantidad solicitada: ${cantidad}, Inventario disponible: ${inventarioDisponible}`);

        if (cantidad > inventarioDisponible) {
            alert("No hay suficiente inventario para el producto seleccionado en la fila " + (i + 1));
            return false;
        }
        if (!productoId || cantidad <= 0) {
            alert("Por favor, seleccione un producto y una cantidad válida en la fila " + (i + 1));
            return false;
        }
    }
    return true;
}

function agregarProducto() {
    var formulario = document.getElementById("formulario-productos");
    var nuevoProducto = formulario.children[0].cloneNode(true);

    nuevoProducto.querySelector(".producto").value = "";
    nuevoProducto.querySelector(".cantidad").value = 1;

    formulario.appendChild(nuevoProducto);
}