let selectedTime = null;

document.querySelector(".time-grid").addEventListener("click", function (event) {
    let slot = event.target.closest(".time-slot"); // Chỉ bắt sự kiện khi click vào `.time-slot`
    if (!slot) return; // Nếu click chỗ khác thì bỏ qua

    document.querySelectorAll(".time-slot").forEach(s => s.classList.remove("selected"));

    slot.classList.add("selected");
    selectedTime = slot.dataset.value;
    timeScheduler.style.display = 'none';
});

$(document).ready(function () {
    $('#btn_book_service').on('click', function (event) {
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

        if (isNaN(selectedTime) ||  selectedTime == null ) {
            alert("Invalid start time");
            return;
        }

        // Convert startTime về dạng HH:mm:ss
        let startTime = selectedTime.toString().padStart(2, '0') + ":00:00";

        // Tính toán endTime (start + 2 giờ)
        var startDate = new Date(`${date}T${startTime}`);
        startDate.setHours(startDate.getHours() + 2); // Cộng 2 giờ

        // Chuyển endTime về định dạng HH:mm:ss
        var endTime = startDate.toISOString().split('T')[1].split('.')[0];

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

        // Gửi yêu cầu POST để thêm người dùng
        $.ajax({
            url: '/api/users/create', // Đường dẫn API thêm user
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                fullName: name,
                email: email,
                phone: phoneNumber,
                address: message
            }),
            success: function (userResponse) {
                const userId = userResponse.id; // Lấy userId từ response

                // Gửi yêu cầu POST để đặt lịch
                $.ajax({
                    url: '/api/appointments/create', // Đường dẫn API đặt lịch
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        startTime: startTime,
                        endTime: endTime ,
                        makeupDate: date,
                        status: false,
                        userId: userId, // Sử dụng userId đã tạo
                        serviceMakeupId: serviceOption,
                        staffId: serviceStaff
                    }),
                    success: function (appointmentResponse) {
                        Swal.fire({
                            position: "mid",
                            icon: "success",
                            title: "Booking success",
                            showConfirmButton: false,
                            timer: 1500
                        });
                    },
                    error: function (xhr, status, error) {
                        let errorMessage = "Nhân viên này đã có lịch trong khoảng thời gian này";
                        try {
                            const jsonResponse = JSON.parse(xhr.responseText);
                            errorMessage = jsonResponse.message || errorMessage;
                        } catch (e) {
                            console.error("Error parsing response: ", e);
                        }

                        Swal.fire({
                            position: "mid",
                            icon: "error",
                            title: errorMessage,
                            showConfirmButton: false,
                            timer: 1500
                        });
                    }
                });
            },
            error: function (xhr, status, error) {
                let errorMessage = "Something went wrong while creating the user!";
                try {
                    const jsonResponse = JSON.parse(xhr.responseText);
                    errorMessage = jsonResponse.message || errorMessage;
                } catch (e) {
                    console.error("Error parsing response: ", e);
                }

                Swal.fire({
                    position: "mid",
                    icon: "error",
                    title: errorMessage,
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
    });

});


    /* ** Time picker
    ...................
    Time picker ** */

    $(document).ready(function () {
    $("#date, #optionsStaff").on("change", function () {
        let selectedDate = $("#date").val();
        let selectedStaff= $("#optionsStaff").val();

    if (selectedDate !== "" && selectedStaff !== "") {
        $.ajax({
           url: "/api/appointments/by-date",
           type: "GET",
           data: {
               staffId: selectedStaff,
               makeupDate : selectedDate
           },
           success: function (response) {
                if (response != "No appointment")
                    updateTimeSlots(response,selectedDate);
                else resetTimeSlots();
           },
           error: function (xhr, status, error) {
           }
       });
    }
 });

    function updateTimeSlots(appointments,selectedDate) {
          selectedTime =  null ;
          let timeGrid = $(".time-grid"); // Chọn div chứa time-slot
          timeGrid.empty();

          convertTime(selectedDate);

          let workingHours = [];
            for (let i = 7; i <= 18; i++) {
                if (i === 11 || i === 12) continue; // Skip 11,12h de nghi trua

                workingHours.push(`${i}`);

                let timeSlot = document.createElement("div");
                timeSlot.classList.add("time-slot", `${i}`); // Thêm class theo i
                timeSlot.value = i;
                timeSlot.dataset.value = i;
                timeSlot.textContent = (i < 12) ? `${i}:00 AM` : `${i - 12}:00 PM`;

                timeGrid.append(timeSlot);
            }

            appointments.forEach(function (appointment) {
            let startHour = parseInt(appointment.startTime.split(":")[0]);
            let endHour = parseInt(appointment.endTime.split(":")[0]);

            workingHours.forEach(function (hour, index) {
                let hourInt = parseInt(hour);

                // Nếu giờ nằm trong khoảng đặt lịch -> Thêm class 'selected' và cấm nhấn
                if (hourInt >= startHour && hourInt < endHour) {
                    $(`.${hourInt}`).addClass("isPicked").css("pointer-events", "none");

                } else // Nếu giờ nhỏ hơn startHour nhưng đặt vào sẽ trùng (tức là giờ đó + 2 >= startHour) => Khóa luôn
                if (hourInt + 1  == startHour) {
                    $(`.${hourInt}`).addClass("isPicked").css("pointer-events", "none");
                }
            });
        });
      }

      function convertTime(selectedDate){
        let dateObj = new Date(selectedDate); // Chuyển chuỗi thành đối tượng Date

        let options = { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' };
        let formattedDate = dateObj.toLocaleDateString('en-GB', options); // Định dạng lại

        document.getElementById("title_date_picker").textContent = formattedDate;
      }

      function resetTimeSlots() {
        let timeGrid = $(".time-grid"); // Chọn div chứa time-slot
        timeGrid.empty();
        for (let i = 7; i <= 18; i++) {
            if (i === 11 || i ===12 ) continue; // Skip 11,12h de nghi trua
            let timeSlot = document.createElement("div");
            timeSlot.classList.add("time-slot", `${i}`); // Thêm class theo i
            timeSlot.dataset.value = i;
            timeSlot.textContent = (i < 12) ? `${i}:00 AM` : `${i - 12}:00 PM`;
            selectedTime = null;
            timeGrid.append(timeSlot);
        }
      }
 });


