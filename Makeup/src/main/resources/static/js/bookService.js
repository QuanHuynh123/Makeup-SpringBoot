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
        var serviceOption = $('#optionsMakeup').val();
        var serviceStaff = $('#optionsStaff').val();
        var date = $('#date').val();
        var startTime = $('#time').val();
        var endTime = null;
        var timeNeed = parseInt($('#optionsMakeup').find(":selected").data('time'), 10);

        // Tính toán endTime
        if (startTime && timeNeed && !isNaN(timeNeed)) {
            var startDate = new Date(`${date}T${startTime}`);
            startDate.setMinutes(startDate.getMinutes() + timeNeed * 60);
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

        // Kiểm tra thời gian hợp lệ trong khoảng 8:00 - 20:00
        var openingTime = new Date(`${date}T08:00`);
        var closingTime = new Date(`${date}T20:00`);
        var startTimeDate = new Date(`${date}T${startTime}`);
        var endTimeDate = new Date(`${date}T${endTime}`);

        if (startTimeDate < openingTime || endTimeDate > closingTime) {
            alert('The appointment time must be between 08:00 and 20:00.');
            return;
        }

        // Gửi yêu cầu POST đến API
        $.ajax({
            url: '/api/appointments/create', // Đường dẫn API
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                startTime: startTime + ":00",
                endTime: endTime + ":00",
                makeupDate: date,
                status: true,
                "userId": 1,
                "serviceMakeupId": serviceOption,
                "staffId": serviceStaff
            }),
            success: function(response) {
                Swal.fire({
                                    position: "mid",
                                    icon: "success",
                                    title: "Booking success",
                                    showConfirmButton: false,
                                    timer: 1500
                                })
            },
            error: function(xhr, status, error) {
               Swal.fire({
                                   position: "mid",
                                   icon: "error",
                                   title: response,
                                   showConfirmButton: false,
                                   timer: 1500
                               })
            }
        });
    });

});