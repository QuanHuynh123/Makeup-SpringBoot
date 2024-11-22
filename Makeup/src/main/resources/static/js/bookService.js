$(document).ready(function() {
    // Lấy danh sách dịch vụ và load vào select
    $.ajax({
        url: "/api/serviceMakeups/services",
        type: "GET",
        success: function(data) {
            const serviceSelect = $("#optionsMakeup");
            $.each(data, function(index, service) {
                const option = $("<option>")
                    .val(service.id)
                    .text(service.nameService)
                    .data('price', service.timeNeed); // Thêm data-price vào từng option
                serviceSelect.append(option);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error fetching services:", error);
        }
    });

    // Xử lý sự kiện khi nhấn nút "Send"
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
        var serviceOption = $('#optionsMakeup').val(); // Lấy từ #optionsMakeup
         var serviceStaff = $('#optionsStaff').val();
        var date = $('#date').val();
        var startTime = $('#time').val();
        var endTime = null;
        var timeNeed = parseInt($('#optionsMakeup').find(":selected").data('time'), 10); // Chuyển thành int
        console.log(timeNeed)

        if (startTime && timeNeed && !isNaN(timeNeed)) { // Kiểm tra timeNeed là số hợp lệ
            var startDate = new Date(`${date}T${startTime}`);
            startDate.setMinutes(startDate.getMinutes() + timeNeed*60);
            endTime = startDate.toTimeString().split(' ')[0].slice(0, 5);
        } else {
            console.log("Start time or duration is missing or invalid");
        }


        // Kiểm tra hợp lệ
        if (!validateEmail(email)) {
            alert('Please enter a valid email address.');
            return;
        }

        if (!validatePhoneNumber(phoneNumber)) {
            alert('Please enter a valid phone number.');
            return;
        }

        if (!name || !phoneNumber || !message || !serviceOption || !date || !startTime) {
            alert('Please fill out all required fields.');
            return;
        }

        // Gửi yêu cầu POST đến API
        $.ajax({
            url: '/api/appointments/create', // Đường dẫn API
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                startTime: startTime+":00",
                endTime: endTime+":00",
                makeupDate: date,
                status: true,
                 "user": {
                    "id": 1
                  },
                  "serviceMakeup": {
                    "id": serviceOption
                  },
                  "staff": {
                    "id": serviceStaff
                  }

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
