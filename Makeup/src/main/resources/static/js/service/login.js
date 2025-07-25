$('#loginForm').on('submit', function (event) {
          event.preventDefault();
          const username = $('#username').val().trim();
          const password = $('#password').val().trim();

          if (!username || !password) {
                showError('Username and password cannot be empty.');
              return;
          }

          const loginData = {
              username: username,
              password: password
          };

          $.ajax({
              url: '/login',
              type: 'POST',
              contentType: 'application/json',
              data: JSON.stringify(loginData),
              success: function (response) {
              localStorage.setItem('token', response.result);
                  Swal.fire({
                      icon: 'success',
                      title: response.message || 'Đăng nhập thành công!',
                      showConfirmButton: false,
                      timer: 1500
                  }).then(() => {
                      window.location.href = '/home';
                  });
              },
              error: function (xhr) {
                  const msg = xhr.responseJSON?.message || 'Đăng nhập thất bại. Vui lòng thử lại.';
                  showError(msg);
              }
          });
      });

  function showError(message) {
      Swal.fire({
          icon: 'error',
          title: 'Error...',
          text: message
      });
  }