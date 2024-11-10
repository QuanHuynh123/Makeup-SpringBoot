$(document).ready(function() {
    $('#btn_book_service').on('click', function(event) {
        event.preventDefault(); // Ngăn chặn submit mặc định của form

        // Hàm kiểm tra email hợp lệ
        function validateEmail(email) {
            var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(email);
        }

        // Hàm kiểm tra số điện thoại hợp lệ
        function validatePhoneNumber(phoneNumber) {
            var re = /^(0|\+84)[0-9]{9,10}$/;
            return re.test(phoneNumber);
        }


        // Lấy giá trị từ các trường input
        var name = $('input[name="Name"]').val();
        var email = $('input[name="Email"]').val();
        var phoneNumber = $('input[name="PhoneNumber"]').val();
        var message = $('textarea[name="Message"]').val();
        var option = $('#options').val();
        var date = $('#date').val();
        var time = $('#time').val();

        // Kiểm tra hợp lệ
        if (!validateEmail(email)) {
            alert('Please enter a valid email address.');
            return;
        }

        if (!validatePhoneNumber(phoneNumber)) {
            alert('Please enter a valid phone number.');
            return;
        }

        if (!name || !phoneNumber || !message || !option || !date || !time) {
            alert('Please fill out all required fields.');
            return;
        }

        // Gửi yêu cầu POST đến API
        $.ajax({
            url: '/api/book-service', // Đường dẫn API
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                email: email,
                phoneNumber: phoneNumber,
                message: message,
                serviceOption: option,
                appointmentDate: date,
                appointmentTime: time
            }),
            success: function(response) {
                alert('Booking successful!');
            },
            error: function(xhr, status, error) {
                alert('Booking failed: ' + xhr.responseText); // Thông báo lỗi
            }
        });
    });
});
