let staffListSelect2 = [];
const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
const vietnamTime = getCurrentVietnamTime();
const currentMonth = vietnamTime.getMonth() + 1;
let currentWeekNumber = getWeekOfMonth(vietnamTime);
let data = null;

function selectCurrentMonth() {
    const monthSelect = document.getElementById("monthSelect");
    if (monthSelect) {
        monthSelect.value = currentMonth;
    } else {
        console.error("Không tìm thấy phần tử với ID 'monthSelect'");
    }
}

function getCurrentVietnamTime() {
    const now = new Date();
    const vietnamOffset = 7 * 60;
    const localOffset = now.getTimezoneOffset();
    return new Date(now.getTime() + (vietnamOffset + localOffset) * 60000);
}

function getWeekOfMonth(date) {
    const startOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
    const dayOfMonth = date.getDate();
    const startDay = (startOfMonth.getDay() + 6) % 7;
    return Math.ceil((dayOfMonth + startDay) / 7);
}

function loadStaffSelectAppointment() {
    return fetch('/api/admin/staffs')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const staffDetails = data.result;
            const staffSelect = document.getElementById('staffSelect');
            if (!staffSelect) {
                console.error('Không tìm thấy phần tử #staffSelect trong DOM');
                return;
            }
            staffSelect.innerHTML = '';
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.disabled = true;
            defaultOption.textContent = 'Chọn nhân viên';
            staffSelect.appendChild(defaultOption);
            staffListSelect2 = staffDetails;
            staffDetails.forEach((staff, index) => {
                const option = document.createElement('option');
                option.value = staff.id;
                option.textContent = `${staff.nameStaff}`;
                if (index === 0) option.selected = true;
                staffSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Lỗi khi tải danh sách nhân viên:', error);
        });
}

const loadAppointment = async (month, year, staffID) => {
    try {
        const response = await fetch(`/api/admin/appointments/by-month?month=${month}&year=${year}&staffID=${staffID}`);
        if (response.status === 204) {
            data = [];
            renderScheduleForWeek(data, currentWeekNumber);
            return data;
        }
        if (!response.ok) {
            throw new Error(`Error: ${response.status} - ${response.statusText}`);
        }
        const dataRes = await response.json();
        if (dataRes.code !== 200) {
            throw new Error(dataRes.message || "Lỗi khi lấy dữ liệu lịch hẹn");
        }
        data = dataRes.result;
        renderScheduleForWeek(data, currentWeekNumber);
        return data;
    } catch (error) {
        console.error("Lỗi khi tải dữ liệu cuộc hẹn:", error);
    }
};

$(document).ready(function () {
    selectCurrentMonth();
    loadStaffSelectAppointment().then(() => {
        const staffID = document.getElementById('staffSelect').value;
        if (staffID) {
            loadAppointment(currentMonth, 2024, staffID);
        }
    });
    $('#monthSelect').change(function () {
        const selectedMonth = $(this).val();
        if (selectedMonth) {
            currentWeekNumber = 1;
            loadAppointment(selectedMonth, 2024, document.getElementById('staffSelect').value);
        }
    });
});

function getRandomColorHSL() {
    const hue = Math.floor(Math.random() * 360);
    const saturation = Math.floor(Math.random() * 30) + 70;
    const lightness = Math.floor(Math.random() * 30) + 40;
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
    if (!selectedWeek) {
        console.warn(`Không có dữ liệu cho tuần số ${weekNumber}`);
        document.querySelectorAll('.cd-schedule__group ul').forEach(ul => ul.innerHTML = '');
        return;
    }
    document.getElementById('dateOfWeek').innerHTML =
        `Week ${weekNumber}: ${formatDateStringToDDMMYYYY(selectedWeek.startDate)} - ${formatDateStringToDDMMYYYY(selectedWeek.endDate)}`;
    document.querySelectorAll('.cd-schedule__group ul').forEach(ul => ul.innerHTML = '');
    if (selectedWeek.appointments.length === 0) {
        console.log(`Không có lịch hẹn nào trong tuần ${weekNumber}`);
        return;
    }
    selectedWeek.appointments.forEach(appointment => {
        const appointmentDate = new Date(appointment.makeupDate);
        const dayIndex = appointmentDate.getDay();
        const dayName = daysOfWeek[dayIndex];
        const randomColor = getRandomColorHSL();
        appointment.color = randomColor;
        const eventHTML = `
            <li class="cd-schedule__event">
                <a style="background-color: ${randomColor} !important;"
                   data-start="${appointment.startTime}"
                   data-end="${appointment.endTime}"
                   data-id="${appointment.id}"
                   href="#0">
                    <em class="cd-schedule__name">
//                        ID: ${appointment.id}<br>
                        Customer: ${appointment.nameUser}<br>
                        Phone: ${appointment.phoneUser}<br>
                    </em>
                </a>
            </li>
        `;
        document.querySelectorAll('.cd-schedule__group span').forEach(dayElement => {
            if (dayElement.textContent.trim() === dayName) {
                dayElement.closest('li').querySelector('ul').insertAdjacentHTML('beforeend', eventHTML);
            }
        });
    });
    const scheduleTemplate = new ScheduleTemplate(document.querySelector('.cd-schedule'));
    scheduleTemplate.placeEvents();
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
    const modal = document.querySelector('.cd-schedule-modal');
    modal.querySelector('.cd-schedule-modal__date').textContent =
        `${appointment.startTime} - ${appointment.endTime}`;
    modal.querySelector('.cd-schedule-modal__name').textContent = appointment.title || `ID: ${appointment.id}`;
    modal.querySelector('.cd-schedule-modal__event-info').innerHTML = `
        <p><strong>Customer:</strong> ${appointment.nameUser || "N/A"}</p>
        <p><strong>Phone:</strong> ${appointment.phoneUser || "N/A"}</p>
        <p><strong>Service:</strong> ${appointment.typeMakeupName || "N/A"}</p>
        <p><strong>Makeup Date:</strong> ${appointment.makeupDate || "N/A"}</p>
        <p><strong>Staff name:</strong> ${appointment.nameStaff || "N/A"}</p>
        <p><strong>Staff ID:</strong> ${appointment.staffId || "N/A"}</p>
        <p><strong>Start Time:</strong> ${appointment.startTime || "N/A"}</p>
        <p><strong>End Time:</strong> ${appointment.endTime || "N/A"}</p>
    `;
    const modalHeader = modal.querySelector('.cd-schedule-modal__header-bg');
    modalHeader.style.backgroundColor = appointment.color;
    modal.style.top = "50%";
    modal.style.left = "calc(50% + 130px)";
    modal.style.height = "500px";
    modal.style.width = "800px";
    modal.style.transform = "translate(-50%, -50%)";
    modal.classList.add('active');
    document.querySelector('.cd-schedule-modal__header').style.width = "130px";
    document.querySelector('.cd-schedule-modal__body').style.marginLeft = "130px";
    const closeModal = modal.querySelector('.cd-schedule-modal__close');
    closeModal.addEventListener('click', function () {
        modal.classList.remove('active');
    });
    document.querySelector('.cd-schedule__cover-layer').addEventListener('click', function () {
        modal.classList.remove('active');
    });
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            modal.classList.remove('active');
        }
    });
}

function changeWeek(offset) {
    currentWeekNumber += offset;
    if (currentWeekNumber < 1) {
        currentWeekNumber = 1;
        console.log("Đây là tuần đầu tiên của tháng!");
        return;
    }
    if (currentWeekNumber > 5) {
        currentWeekNumber = 5;
        console.log("Đây là tuần cuối cùng của tháng!");
        return;
    }
    const selectedWeek = data.find(week => week.weekNumber === currentWeekNumber);
    if (selectedWeek) {
        renderScheduleForWeek(data, currentWeekNumber);
        const scheduleTemplate = new ScheduleTemplate(document.querySelector('.cd-schedule'));
        scheduleTemplate.placeEvents();
    } else {
        console.warn(`Không có dữ liệu cho tuần số ${currentWeekNumber}`);
    }
}