// Biến toàn cục
let selectedTime = null;

// Hàm xử lý chọn time slot
function handleTimeSlotSelection(event) {
    const slot = event.target.closest(".time-slot");
    if (!slot) return;

    document.querySelectorAll(".time-slot").forEach(s => s.classList.remove("selected"));
    slot.classList.add("selected");
    selectedTime = slot.dataset.value;
    document.getElementById("scheduler").style.display = "none";
}

// Hàm cập nhật time slots dựa trên lịch hẹn
function updateTimeSlots(appointments, selectedDate) {
    selectedTime = null;
    const timeGrid = document.querySelector(".time-grid");
    timeGrid.innerHTML = "";

    convertTime(selectedDate);

    const workingHours = [];
    for (let i = 7; i <= 18; i++) {
        if (i === 11 || i === 12) continue; // Bỏ qua giờ nghỉ trưa
        workingHours.push(i);
        const timeSlot = document.createElement("div");
        timeSlot.classList.add("time-slot", `${i}`);
        timeSlot.dataset.value = i;
        timeSlot.textContent = i < 12 ? `${i}:00 AM` : `${i - 12}:00 PM`;
        timeGrid.appendChild(timeSlot);
    }

    appointments.forEach(appointment => {
        const startHour = parseInt(appointment.startTime.split(":")[0]);
        const endHour = parseInt(appointment.endTime.split(":")[0]);
        workingHours.forEach(hour => {
            const hourInt = parseInt(hour);
            if (hourInt >= startHour && hourInt < endHour) {
                document.querySelector(`.${hourInt}`).classList.add("isPicked");
                document.querySelector(`.${hourInt}`).style.pointerEvents = "none";
            } else if (hourInt + 1 === startHour) {
                document.querySelector(`.${hourInt}`).classList.add("isPicked");
                document.querySelector(`.${hourInt}`).style.pointerEvents = "none";
            }
        });
    });
}

// Hàm reset time slots
function resetTimeSlots() {
    selectedTime = null;
    const timeGrid = document.querySelector(".time-grid");
    timeGrid.innerHTML = "";
    for (let i = 7; i <= 18; i++) {
        if (i === 11 || i === 12) continue;
        const timeSlot = document.createElement("div");
        timeSlot.classList.add("time-slot", `${i}`);
        timeSlot.dataset.value = i;
        timeSlot.textContent = i < 12 ? `${i}:00 AM` : `${i - 12}:00 PM`;
        timeGrid.appendChild(timeSlot);
    }
}

// Hàm định dạng ngày
function convertTime(selectedDate) {
    const dateObj = new Date(selectedDate);
    const options = { weekday: "long", day: "numeric", month: "long", year: "numeric" };
    document.getElementById("title_date_picker").textContent = dateObj.toLocaleDateString("en-GB", options);
}

// Hàm kiểm tra email
function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// Hàm kiểm tra số điện thoại
function validatePhoneNumber(phoneNumber) {
    return /^(0|\+84)[0-9]{9,10}$/.test(phoneNumber);
}

// Hàm xử lý đặt lịch
function handleBooking(event) {
    event.preventDefault();

    const name = $('input[name="Name"]').val();
    const email = $('input[name="Email"]').val();
    const phoneNumber = $('input[name="PhoneNumber"]').val();
    const message = $('textarea[name="Message"]').val();
    const serviceOption = $('#optionsMakeup').val();
    const serviceStaff = $('#optionsStaff').val();
    const date = $('#date').val();

    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    const selectedDate = new Date(date);

    if (selectedDate < currentDate) {
        alert("Please select a valid date. The date cannot be in the past.");
        return;
    }

    if (!selectedTime || isNaN(selectedTime)) {
        alert("Invalid start time");
        return;
    }

    const startTime = `${selectedTime.toString().padStart(2, "0")}:00:00`;
    const startDate = new Date(`${date}T${startTime}`);
    startDate.setHours(startDate.getHours() + 2);
    const endTime = startDate.toISOString().split("T")[1].split(".")[0];

    if (!validateEmail(email) || !validatePhoneNumber(phoneNumber) || !name || !phoneNumber || !message || !serviceOption || !date || !startTime) {
        alert("Please fill out all required fields or enter valid data.");
        return;
    }

    $.ajax({
            url: "/api/appointments/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                startTime,
                endTime,
                makeupDate: selectedDate,
                serviceMakeupId: serviceOption,
                staffId: serviceStaff,
                guestInfo: {
                    fullName: name,
                    email,
                    phone: phoneNumber,
                    message
                } // Gửi thông tin khách nếu cần
            }),
            success: function (response) {
                Swal.fire({
                    position: "mid",
                    icon: "success",
                    title: "Booking success",
                    showConfirmButton: false,
                    timer: 1500
                });
            },
            error: function (xhr) {
                const errorMessage = xhr.responseJSON?.message || "An error occurred during booking";
                Swal.fire({
                    position: "mid",
                    icon: "error",
                    title: errorMessage,
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });
}

// Hàm xử lý nút time picker
function handleTimePickerToggle(event) {
    event.preventDefault();
    const timeScheduler = document.getElementById("scheduler");
    timeScheduler.style.display = timeScheduler.style.display === "none" || !timeScheduler.style.display ? "block" : "none";
}

// Khởi tạo sự kiện
document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".time-grid").addEventListener("click", handleTimeSlotSelection);
    document.querySelector("#btn_book_service").addEventListener("click", handleBooking);
    document.getElementById("timePickerBtn").addEventListener("click", handleTimePickerToggle);

    $("#date, #optionsStaff").on("change", function () {
        const selectedDate = $("#date").val();
        const selectedStaff = $("#optionsStaff").val();
        if (selectedDate && selectedStaff) {
            $.ajax({
                url: "/api/appointments/by-date",
                type: "GET",
                data: { staffId: selectedStaff, makeupDate: selectedDate },
                success: function (response) {
                    response !== "No appointment" ? updateTimeSlots(response, selectedDate) : resetTimeSlots();
                },
                error: function () {
                    resetTimeSlots(); // Reset nếu có lỗi
                }
            });
        }
    });
});