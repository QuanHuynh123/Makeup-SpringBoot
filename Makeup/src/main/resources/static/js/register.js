$(document).ready(function() {
     $('#btn_register').on('click', function(event) {
        event.preventDefault(); // Ngăn chặn việc submit mặc định của form


    // Hàm kiểm tra email hợp lệ
    function validateEmail(email) {
        var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

 // Lấy giá trị từ các trường input
      var email = $('#email').val();
      var password = $('#password').val();
      var confirmPassword = $('#confirmPassword').val();

      // Kiểm tra hợp lệ
      if (!validateEmail(email)) {
          alert('Please enter a valid email address.');
          return;
      }

      if (password !== confirmPassword) {
          alert('Passwords do not match.');
          return;
      }


      $.ajax({
          url: '/api/register', // Đường dẫn API
          type: 'POST',
          contentType: 'application/json',
       data: JSON.stringify({
            userName: email,
            passWord: password,
            role: {
                id: 1,
                nameRole: "USER"
            }
        }),
          success: function(response) {
              alert('Registration successful!');
          },
          error: function(xhr, status, error) {
              alert('Registration failed: ' + xhr.responseText); // Thông báo lỗi
          }
      });

    });
});