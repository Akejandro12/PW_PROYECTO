$(document).ready(function () {
    $("#registerForm").on("submit", function (event) {
        let valid = true;

        const nombre = $("#nombre").val().trim();
        if (nombre === "") {
            $("#nombre").addClass("is-invalid");
            valid = false;
        } else {
            $("#nombre").removeClass("is-invalid");
        }

        const apellido = $("#apellido").val().trim();
        if (apellido === "") {
            $("#apellido").addClass("is-invalid");
            valid = false;
        } else {
            $("#apellido").removeClass("is-invalid");
        }

        const email = $("#email").val();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            $("#email").addClass("is-invalid");
            valid = false;
        } else {
            $("#email").removeClass("is-invalid");
        }

        const password = $("#password").val();
        if (password.length < 4) {
            $("#password").addClass("is-invalid");
            valid = false;
        } else {
            $("#password").removeClass("is-invalid");
        }

        if (!valid) {
            event.preventDefault();
        }
    });
    $("#nombre, #apellido, #email, #password").on("input", function () {
        $(this).removeClass("is-invalid");
    });
});