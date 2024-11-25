// Espera a que el DOM se cargue completamente antes de ejecutar cualquier código.
$(document).ready(function () {

    // Asocia un evento de 'submit' al formulario con el id "registerForm".
    $("#registerForm").on("submit", function (event) {
        let valid = true; // Variable para verificar si el formulario es válido

        // Obtiene el valor del campo 'nombre' y lo limpia de espacios innecesarios
        const nombre = $("#nombre").val().trim();
        // Verifica si el campo 'nombre' está vacío
        if (nombre === "") {
            // Si está vacío, agrega la clase 'is-invalid' para resaltar el campo como inválido
            $("#nombre").addClass("is-invalid");
            valid = false; // Establece 'valid' a false, indicando que hay un error
        } else {
            // Si no está vacío, elimina la clase 'is-invalid' (si existiera)
            $("#nombre").removeClass("is-invalid");
        }

        // Obtiene el valor del campo 'apellido' y lo limpia de espacios innecesarios
        const apellido = $("#apellido").val().trim();
        // Verifica si el campo 'apellido' está vacío
        if (apellido === "") {
            // Si está vacío, agrega la clase 'is-invalid' para resaltar el campo como inválido
            $("#apellido").addClass("is-invalid");
            valid = false; // Establece 'valid' a false, indicando que hay un error
        } else {
            // Si no está vacío, elimina la clase 'is-invalid' (si existiera)
            $("#apellido").removeClass("is-invalid");
        }

        // Obtiene el valor del campo 'email'
        const email = $("#email").val();
        // Expresión regular para validar que el correo tiene el formato adecuado
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        // Verifica si el correo no cumple con el formato adecuado
        if (!emailRegex.test(email)) {
            // Si el correo no es válido, agrega la clase 'is-invalid'
            $("#email").addClass("is-invalid");
            valid = false; // Establece 'valid' a false, indicando que hay un error
        } else {
            // Si el correo es válido, elimina la clase 'is-invalid'
            $("#email").removeClass("is-invalid");
        }

        // Obtiene el valor del campo 'password'
        const password = $("#password").val();
        // Verifica si la longitud de la contraseña es menor a 4 caracteres
        if (password.length < 4) {
            // Si la contraseña es demasiado corta, agrega la clase 'is-invalid'
            $("#password").addClass("is-invalid");
            valid = false; // Establece 'valid' a false, indicando que hay un error
        } else {
            // Si la contraseña es suficientemente larga, elimina la clase 'is-invalid'
            $("#password").removeClass("is-invalid");
        }

        // Si algún campo es inválido, se evita el envío del formulario
        if (!valid) {
            event.preventDefault();
        }
    });

    // Asocia un evento 'input' a los campos 'nombre', 'apellido', 'email' y 'password'
    // Este evento se activa cada vez que el usuario escribe en el campo
    $("#nombre, #apellido, #email, #password").on("input", function () {
        // Elimina la clase 'is-invalid' cuando el usuario empieza a corregir el campo
        $(this).removeClass("is-invalid");
    });
});
