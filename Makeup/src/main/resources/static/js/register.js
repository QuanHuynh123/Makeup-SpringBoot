	alert("ccccccccccccccccccccccccccc");

//$(function(){
//	$('.form-holder').delegate("input", "focus", function(){
//		$('.form-holder').removeClass("active");
//		$(this).parent().addClass("active");
//	})
//})
//
//
//$(document).ready(function() {
//     $('#btn_register').on('click', function(event) {
//        event.preventDefault(); // Ngăn chặn việc submit mặc định của form
//        alert("cc");
//    });
//
//    // Hàm kiểm tra email hợp lệ
//    function validateEmail(email) {
//        var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//        return re.test(email);
//    }
//});

/* // Lấy giá trị từ các trường input
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

          // Gửi yêu cầu AJAX
          $.ajax({
              url: '/api/register', // Đường dẫn API
              type: 'POST',
              contentType: 'application/json',
           data: JSON.stringify({
  				userName: email, // Sử dụng "userName" thay vì "username" theo DTO trên server
  				passWord: password, // Đảm bảo tên trường khớp với DTO trên server
  				role: { // Thêm thông tin role vào đây
  					id: 1, // ID của role, phải là giá trị tồn tại trong cơ sở dữ liệu
  					nameRole: "USER" // Tên role
  				}
  			}),
              success: function(response) {
                  alert('Registration successful!'); // Thông báo thành công
                  // Có thể chuyển hướng hoặc reset form nếu cần
                  $('#registrationForm')[0].reset(); // Reset form
              },
              error: function(xhr, status, error) {
                  alert('Registration failed: ' + xhr.responseText); // Thông báo lỗi
              }
          });