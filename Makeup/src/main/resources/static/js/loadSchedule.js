

// Days of the week mapping
const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
// Lấy tháng và tuần hiện tại theo múi giờ Việt Nam
const vietnamTime = getCurrentVietnamTime();
const currentMonth = vietnamTime.getMonth(); // Tháng hiện tại (0 - 11)
let currentWeekNumber = getWeekOfMonth(vietnamTime) - 1;
var data = null;

// Đặt giá trị mặc định cho select
function selectCurrentMonth() {
	const monthSelect = document.getElementById("monthSelect");

	if (monthSelect) {
		// Đặt giá trị tương ứng với tháng hiện tại
		monthSelect.value = currentMonth + 1;
	} else {
		console.error("Không tìm thấy phần tử với ID 'monthSelect'");
	}
}

// Hàm lấy thời gian hiện tại theo múi giờ Việt Nam
function getCurrentVietnamTime() {
	const now = new Date();
	const vietnamOffset = 7 * 60; // UTC+7, in minutes
	const localOffset = now.getTimezoneOffset(); // Difference from UTC, in minutes
	const vietnamTime = new Date(now.getTime() + (vietnamOffset + localOffset) * 60000);
	return vietnamTime;
}

function getWeekOfMonth(date) {
	const startOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
	const dayOfMonth = date.getDate();
	const startDay = (startOfMonth.getDay() + 6) % 7; // Adjust to make Monday the first day of the week

	// Calculate the week number based on the adjusted start day
	return Math.ceil((dayOfMonth + startDay) / 7);
}


const loadAppointment = (month, year) => {
	$.ajax({
		url: `/api/appointments/by-month?month=${month}&year=${year}`,
		type: "GET",
		success: function (dataRes) {
			data = dataRes; // Lưu dữ liệu từ API vào biến data
			renderScheduleForWeek(data, currentWeekNumber);
		},
		error: function (xhr, status, error) {
			console.error("Error fetching appointments:", error);
		}
	});
}

$(document).ready(function () {
	// Gọi API khi giá trị tháng thay đổi
	$('#monthSelect').change(function () {
		const selectedMonth = $(this).val(); // Lấy giá trị tháng được chọn
		const year = 2024; // Có thể thay đổi hoặc lấy năm hiện tại nếu cần
		currentWeekNumber = 1;

		// Kiểm tra xem người dùng đã chọn tháng hay chưa
		if (selectedMonth != "Choose month") {
			loadAppointment(selectedMonth, year); // Gọi API với tháng và năm đã chọn
		}
	});
});






// Hàm tạo màu HSL ngẫu nhiên
function getRandomColorHSL() {
	const hue = Math.floor(Math.random() * 360); // Ngẫu nhiên giữa 0 và 360
	const saturation = Math.floor(Math.random() * 30) + 70; // Độ bão hòa từ 70% đến 100%
	const lightness = Math.floor(Math.random() * 30) + 40; // Độ sáng từ 40% đến 70%
	return `hsl(${hue}, ${saturation}%, ${lightness}%)`;
}

function formatDateStringToDDMMYYYY(dateString) {
	const date = new Date(dateString);
	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const year = date.getFullYear();
	return `${day}/${month}/${year}`;
}

function renderScheduleForWeek(data, weekNumber) {
	const selectedWeek = data.find(week => week.weekNumber === weekNumber);
	document.getElementById('dateOfWeek').innerHTML =
		"Week " + weekNumber + ": " +
		formatDateStringToDDMMYYYY(selectedWeek.startDate) + " - " +
		formatDateStringToDDMMYYYY(selectedWeek.endDate);

	// Xóa tất cả các sự kiện lịch cũ mà không xóa cấu trúc lịch
	document.querySelectorAll('.cd-schedule__event').forEach(event => event.remove());

	if (!selectedWeek || selectedWeek.appointments.length === 0) {
		console.log(`Tuần ${weekNumber} không có lịch hẹn nào.`);

		// Hiển thị lịch trống nếu không có sự kiện nào
		const dayElements = document.querySelectorAll('.cd-schedule__group span');
		dayElements.forEach(dayElement => {
			const ulElement = dayElement.closest('li').querySelector('ul');
			ulElement.innerHTML = ''; // Đảm bảo xóa nội dung cũ trong các ngày
			ulElement.insertAdjacentHTML('beforeend', '');
		});
		return;
	}

	// Nếu có lịch hẹn, thêm vào lịch như bình thường
	selectedWeek.appointments.forEach(appointment => {
		const appointmentDate = new Date(appointment.makeupDate);
		const dayIndex = appointmentDate.getDay();
		const dayName = daysOfWeek[dayIndex];

		const randomColor = getRandomColorHSL();
		appointment.color = randomColor;  // Lưu màu của appointment để sử dụng sau

		const eventHTML = `
          <li class="cd-schedule__event">
            <a style="background-color: ${randomColor} !important;" 
               data-start="${appointment.startTime}" 
               data-end="${appointment.endTime}" 
               data-id="${appointment.id}" 
               href="#0">
              <em class="cd-schedule__name">Appointment ID: ${appointment.id}</em>
            </a>
          </li>
        `;

		const dayElements = document.querySelectorAll('.cd-schedule__group span');
		dayElements.forEach(dayElement => {
			if (dayElement.textContent.trim() === dayName) {
				dayElement.closest('li').querySelector('ul').insertAdjacentHTML('beforeend', eventHTML);
			}
		});
	});

	// Gắn sự kiện nhấp chuột cho từng sự kiện trong lịch
	document.querySelectorAll('.cd-schedule__event a').forEach(eventElement => {
		eventElement.addEventListener('click', function (e) {
			e.preventDefault();
			const appointmentId = this.getAttribute('data-id');
			showAppointmentDetails(appointmentId, selectedWeek.appointments);
		});
	});
}

function showAppointmentDetails(appointmentId, appointments) {
	const appointment = appointments.find(app => app.id == appointmentId);

	if (!appointment) {
		console.error("Không tìm thấy thông tin chi tiết của lịch hẹn!");
		return;
	}

	// Cập nhật nội dung của popup
	const modal = document.querySelector('.cd-schedule-modal');
	modal.querySelector('.cd-schedule-modal__date').textContent =
		`${appointment.startTime} - ${appointment.endTime}`;
	modal.querySelector('.cd-schedule-modal__name').textContent = appointment.title || `Appointment ID: ${appointment.id}`;
	modal.querySelector('.cd-schedule-modal__event-info').innerHTML = `
        <p><strong>Location:</strong> ${appointment.location || "N/A"}</p>
        <p><strong>Description:</strong> ${appointment.description || "No description available"}</p>
    `;

	// Thay đổi màu header của modal theo màu của appointment
	const modalHeader = modal.querySelector('.cd-schedule-modal__header-bg');
	modalHeader.style.backgroundColor = appointment.color;

	// Hiển thị modal
	modal.style.top = "50%";
	modal.style.left = "calc(50% + 130px)";
	modal.style.height = "500px";
	modal.style.width = "800px";
	modal.style.transform = "translate(-50%, -50%)";
	modal.classList.add('active');

	document.querySelector('.cd-schedule-modal__header').style.width = "130px";
	document.querySelector('.cd-schedule-modal__body').style.marginLeft = "130px";
	// Đóng modal khi nhấn nút close
	const closeModal = modal.querySelector('.cd-schedule-modal__close');
	closeModal.addEventListener('click', function () {
		modal.classList.remove('active');
	});
}



// Hàm thay đổi tuần hiển thị
function changeWeek(offset) {
	currentWeekNumber += offset;
	if (currentWeekNumber <= 0 || currentWeekNumber >= 6) {
		currentWeekNumber -= offset;;
		return;
	}

	const selectedWeek = data.find(week => week.weekNumber === currentWeekNumber);

	if (selectedWeek) {
		renderScheduleForWeek(data, currentWeekNumber);
		var scheduleTemplate = new ScheduleTemplate(document.querySelector('.cd-schedule'));

		// Gọi hàm placeEvents từ đối tượng
		scheduleTemplate.placeEvents();
	} else {
		console.warn("Không có dữ liệu cho tuần số ", currentWeekNumber);
		renderScheduleForWeek(data, currentWeekNumber);
	}
}

// Gọi hàm renderScheduleForWeek sau khi tải dữ liệu từ API
//renderScheduleForWeek(data, currentWeekNumber);
