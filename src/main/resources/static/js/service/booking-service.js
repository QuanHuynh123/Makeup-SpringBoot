// Biến toàn cục
let selectedTime = null;
const WORKING_HOURS = [7, 8, 9, 10, 13, 14, 15, 16, 17, 18];

function formatHourLabel(hour) {
    return hour < 12 ? `${hour}:00 AM` : `${hour - 12}:00 PM`;
}

function renderTimeSlots(disabledHours = new Set()) {
    const timeGrid = document.querySelector(".time-grid");
    if (!timeGrid) return;

    selectedTime = null;
    timeGrid.innerHTML = "";

    WORKING_HOURS.forEach(hour => {
        const timeSlot = document.createElement("div");
        timeSlot.classList.add("time-slot");
        timeSlot.dataset.value = String(hour);
        timeSlot.textContent = formatHourLabel(hour);
        if (disabledHours.has(hour)) {
            timeSlot.classList.add("isPicked");
            timeSlot.style.pointerEvents = "none";
        }
        timeGrid.appendChild(timeSlot);
    });
}

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
    convertTime(selectedDate);
    const disabledHours = new Set();

    appointments.forEach(appointment => {
        const startHour = parseInt(appointment.startTime.split(":")[0]);
        const endHour = parseInt(appointment.endTime.split(":")[0]);
        WORKING_HOURS.forEach(hour => {
            if ((hour >= startHour && hour < endHour) || hour + 1 === startHour) {
                disabledHours.add(hour);
            }
        });
    });

    renderTimeSlots(disabledHours);
}

// Hàm reset time slots
function resetTimeSlots() {
    renderTimeSlots();
}

// Hàm định dạng ngày
function convertTime(selectedDate) {
    const dateObj = new Date(selectedDate);
    const options = { weekday: "long", day: "numeric", month: "long", year: "numeric" };
    const titleEl = document.getElementById("title_date_picker");
    if (titleEl) {
        titleEl.textContent = dateObj.toLocaleDateString("en-GB", options);
    }
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
        showError("Please select a valid date. The date cannot be in the past.");
        return;
    }

    const selectedHour = Number(selectedTime);
    if (!Number.isInteger(selectedHour)) {
        showError("Invalid start time");
        return;
    }

    const pad = n => n.toString().padStart(2, "0");

    const startTime = `${pad(selectedHour)}:00:00`;
    const startDate = new Date(`${date}T${startTime}`);

    const endDate = new Date(startDate.getTime());
    endDate.setHours(endDate.getHours() + 2);

    const endHour = pad(endDate.getHours());
    const endTime = `${endHour}:00:00`;


    if (!validateEmail(email)
        || !validatePhoneNumber(phoneNumber)
        || !name
        || !phoneNumber
        || !message
        || !serviceOption
        || !serviceStaff
        || !date
        || !startTime) {
        showError("Please fill out all required fields or enter valid data.");
        return;
    }

    $.ajax({
            url: "/api/appointments/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                startTime,
                endTime,
                makeupDate: date,
                typeMakeupId: serviceOption,
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
    const timeGrid = document.querySelector(".time-grid");
    const bookBtn = document.querySelector("#btn_book_service");
    const timePickerBtn = document.getElementById("timePickerBtn");

    if (!timeGrid || !bookBtn || !timePickerBtn) {
        return;
    }

    resetTimeSlots();
    timeGrid.addEventListener("click", handleTimeSlotSelection);
    bookBtn.addEventListener("click", handleBooking);
    timePickerBtn.addEventListener("click", handleTimePickerToggle);

    $("#date, #optionsStaff").on("change", function () {
        const selectedDate = $("#date").val();
        const selectedStaff = $("#optionsStaff").val();
        if (!selectedDate || !selectedStaff) {
            resetTimeSlots();
            return;
        }

        $.ajax({
            url: "/api/appointments/by-date",
            type: "GET",
            data: { staffId: selectedStaff, makeupDate: selectedDate },
            success: function (response) {
                if (response.code === 200 && response.result && response.result.length > 0) {
                    updateTimeSlots(response.result, selectedDate);
                } else {
                    resetTimeSlots();
                }
            },
            error: function () {
                resetTimeSlots();
            }
        });
    });
});

function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message,
        confirmButtonColor: '#d33',
        confirmButtonText: 'Close'
    });
}