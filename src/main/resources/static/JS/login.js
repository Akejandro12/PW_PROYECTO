$(document).ready(function () {
    $("#loginForm").on("submit", function (event) {
        let valid = true;

        const email = $("#username").val();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            $("#username").addClass("is-invalid");
            valid = false;
        } else {
            $("#username").removeClass("is-invalid");
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

    $("#username, #password").on("input", function () {
        $(this).removeClass("is-invalid");
    });
});