// ========== Event ==========
$(document).ready(function () {
    $('#btn_register').on('click', handleRegister);
});

function handleRegister(event) {
    event.preventDefault();

    const username = $('#username').val().trim();
    const password = $('#password').val().trim();
    const confirmPassword = $('#confirmPassword').val();

    if (!username) {
            showError('Username cannot be empty.');
            return;
    }

    if (!isValidUsername(username)) {
        showError('Username must be 3-20 characters long and contain only letters, numbers, or underscores.');
        return;
    }

    if (password !== confirmPassword) {
        showError('Passwords do not match.');
        return;
    }

    const userData = {
        userName: username,
        passWord: password,
    };

    callRegister( userData);
}

// ========== VALIDATION ==========
function isValidUsername(username) {
    const re = /^[a-zA-Z0-9_]{3,20}$/;
    return re.test(username);
}

// ============================================ API =========================================
function callRegister( data) {
    $.ajax({
        url: "/register",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title:'Registration successful!',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
              window.location.href = '/login';
            });
        },
        error: function (xhr) {
            const msg =  'Registration failed';
            showError(msg);
        }
    });
}

// ========== Common ==========
function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message,
        footer: '<a href="#">Why do I have this issue?</a>'
    });
}
