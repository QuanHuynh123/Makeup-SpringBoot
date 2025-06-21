$('#loginForm').on('submit', function (event) {
          event.preventDefault();
          const username = $('#username').val().trim();
          const password = $('#password').val().trim();

          if (!username || !password) {
              Swal.fire({
                  icon: 'error',
                  title: 'Lỗi...',
                  text: 'Tên người dùng và mật khẩu không được để trống.'
              });
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
                  Swal.fire({
                      icon: 'error',
                      title: 'Lỗi...',
                      text: msg
                  });
              }
          });
      });

  function showError(message) {
      Swal.fire({
          icon: 'error',
          title: 'Lỗi...',
          text: message
      });
  }