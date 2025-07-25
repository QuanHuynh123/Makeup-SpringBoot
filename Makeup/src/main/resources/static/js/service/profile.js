    document.addEventListener("DOMContentLoaded", function () {
      setTimeout(alertUserProfile, 1500);
    });
    function alertUserProfile() {
      var name = document.getElementById('name').value.trim();
      var email = document.getElementById('email').value.trim();
      var phone = document.getElementById('phone').value.trim();
      var address = document.getElementById('address').value.trim();

      // Kiểm tra nếu bất kỳ trường nào bị trống
      if (!name || !email || !phone || !address) {
          Swal.fire({
              icon: 'warning',
              title: 'Thông tin chưa đầy đủ',
              text: 'Bạn nên cập nhật thông tin để sử dụng một cách tiện lợi hơn!',
              timer: 2300
          });
          return;
      }
  }

function getData() {
    var email = document.getElementById('email').value;
    var phone = document.getElementById('phone').value;
    var address = document.getElementById('address').value;
    var name = document.getElementById('name').value;

    Swal.fire({
        title: "Bạn có chắc muốn cập nhật thông tin không?",
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: "Lưu",
        denyButtonText: `Không lưu`
    }).then((result) => {
        if (result.isConfirmed) {
            fetchData(name, email, phone, address);
        } else if (result.isDenied) {
            Swal.fire("Chưa có thay đổi nào được lưu", "", "info");
        }
    });
}


    function fetchData(name, email, phone, address) {
        if (!name || !email || !phone || !address) {
              Swal.fire({
                  icon: 'warning',
                  title: 'Thông tin chưa đầy đủ',
                  text: 'Nên điền đầy đủ thông tin!',
                  confirmButtonText: 'Đã hiểu'
              });
              return;
        }
        $.ajax({
            url: '/api/users/profile/update',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                email: email,
                phone: phone,
                address: address,
            }),
            success: function (response) {
                console.log(response);
                Swal.fire({
                    icon: 'success',
                    title: "Cập nhật thành công",
                    text: 'Thông tin của bạn đã được cập nhật.',
                });
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Đã xảy ra lỗi khi cập nhật thông tin.',
                });
            },
        });
    }