// Espera a que el DOM esté completamente cargado antes de ejecutar el código
$(document).ready(function () {

    // Se ejecuta cuando el formulario con id 'loginForm' es enviado
    $("#loginForm").on("submit", function (event) {
        // Variable para controlar si el formulario es válido
        let valid = true;

        // Obtener el valor del campo de correo electrónico
        const email = $("#username").val();
        // Expresión regular para validar el formato del correo electrónico
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // Comprobar si el correo electrónico no cumple con el formato
        if (!emailRegex.test(email)) {
            // Agregar la clase 'is-invalid' al campo para marcarlo como inválido
            $("#username").addClass("is-invalid");
            valid = false; // Marcar el formulario como no válido
        } else {
            // Si el correo es válido, quitar la clase 'is-invalid'
            $("#username").removeClass("is-invalid");
        }

        // Obtener el valor del campo de la contraseña
        const password = $("#password").val();

        // Verificar si la contraseña tiene menos de 4 caracteres
        if (password.length < 4) {
            // Agregar la clase 'is-invalid' al campo de la contraseña
            $("#password").addClass("is-invalid");
            valid = false; // Marcar el formulario como no válido
        } else {
            // Si la contraseña es válida, quitar la clase 'is-invalid'
            $("#password").removeClass("is-invalid");
        }

        // Si el formulario no es válido, evitar que se envíe
        if (!valid) {
            event.preventDefault();
        }
    });

    // Al ingresar texto en los campos de correo electrónico o contraseña
    $("#username, #password").on("input", function () {
        // Eliminar la clase 'is-invalid' cuando el usuario empieza a escribir
        $(this).removeClass("is-invalid");
    });
});
