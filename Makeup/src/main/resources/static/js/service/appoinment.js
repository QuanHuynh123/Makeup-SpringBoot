
/*********************************************API **********************************************/
// Hàm tải danh sách nhân viên
function loadAppointmentsDetail() {
    fetch('/api/appointments')
        .then(response => response.json())
        .then(data => {
            // Gọi renderAppointmentTable để hiển thị dữ liệu vào bảng
            appointmentList = data;
            renderAppointmentTable(data);
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

// update Appointment
function saveChangesAppointment() {
    const id = document.getElementById('addAppointmentId').value; // Lấy ID cuộc hẹn
    const userId = tmpAppointment.userID; // Lấy ID khách hàng
    const staffId = document.getElementById('addNhanVien').value; // Lấy ID nhân viên
    const serviceMakeupId = document.getElementById('addServiceMakeupName').value; // Lấy ID dịch vụ makeup
    const makeupDate = document.getElementById('addMakeupDate').value; // Lấy ngày makeup
    let startTime = document.getElementById('addStartTime').value; // Lấy thời gian bắt đầu
    let endTime = document.getElementById('addEndTime').value; // Lấy thời gian kết thúc
    const status = document.getElementById('addStatus').value; // Lấy tình trạng

    if (!userId || !staffId || !serviceMakeupId || !makeupDate || !startTime || !endTime || !status) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Đảm bảo thời gian có định dạng HH:mm:ss
    if (!startTime.match(/^\d{2}:\d{2}:\d{2}$/)) {
        startTime = `${startTime}:00`; // Thêm :00 nếu chỉ có HH:mm
    }
    if (!endTime.match(/^\d{2}:\d{2}:\d{2}$/)) {
        endTime = `${endTime}:00`; // Thêm :00 nếu chỉ có HH:mm
    }

    // Kiểm tra xem là hành động thêm mới hay chỉnh sửa
    const url = id ? `/api/appointments/${id}` : '/api/appointments'; // URL cho hành động sửa hoặc thêm mới
    const method = id ? 'PUT' : 'POST'; // Nếu có ID thì là sửa, nếu không thì là thêm mới

    // Chuẩn bị dữ liệu gửi lên
    const requestData = {
        userId, // ID khách hàng
        staffId, // ID nhân viên
        serviceMakeupId, // ID dịch vụ makeup
        makeupDate, // Ngày makeup
        startTime, // Thời gian bắt đầu (định dạng HH:mm:ss)
        endTime, // Thời gian kết thúc (định dạng HH:mm:ss)
        status: parseInt(status), // Tình trạng
    };

    // Gửi yêu cầu API
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
    })
        .then(async (response) => {
            const action = id ? 'Cập nhật' : 'Thêm mới';

            // Lấy dữ liệu JSON từ phản hồi
            const responseData = await response.json();

            // Kiểm tra trạng thái phản hồi từ API
            if (response.ok && responseData.code === 200) {
                showAlert('success', `${action} cuộc hẹn thành công!`);
                document.getElementById('addAppointmentId').value = '';
                loadAppointmentsDetail(); // Tải lại danh sách cuộc hẹn
                const editModal = bootstrap.Modal.getInstance(document.getElementById('addModalAppointment'));
                editModal.hide(); // Đóng modal sau khi lưu thành công
                clearFormSearchAppointment(); // Xóa form tìm kiếm nếu có
            } else {
                // Hiển thị thông báo lỗi từ server nếu có
                const errorMessage = responseData.message || 'Dữ liệu không hợp lệ hoặc lỗi từ phía máy chủ.';
                showAlert('danger', errorMessage);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi kết nối tới máy chủ!');
        });
}


// add Appointment
function addCustomer() {
    const customerName = document.getElementById('addCustomerName').value;
    const sex = document.getElementById('addSex').value;
    const phoneNumber = document.getElementById('addPhoneNumber').value;
    const birthday = document.getElementById('addBirthday').value;
    const address = document.getElementById('addAddress').value;
    const status = document.getElementById('addStatus').value;

    if (!customerName || !phoneNumber || !birthday || !phoneNumber || !address) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const customerData = {
        customerName,
        sex,
        phoneNumber,
        birthday,
        address,
        status
    };

    fetch('/admin/customers/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(customerData)
    })
        .then(response => {
            if (response.ok) {
                showAlert('success', 'Thêm khách hàng thành công!');
                loadAppointmentsDetail();
                const addModalAppointment = bootstrap.Modal.getInstance(document.getElementById('addModalAppointment'));
                addModalAppointment.hide();
            } else {
                showAlert('danger', 'Thêm khách hàng thất bại!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi thêm khách hàng:', error);
        });
}

// edit Appointment
function editAppointment(id) {
    // Lấy ID của cuộc hẹn từ tham số
    const appointmentId = id;
    document.getElementById("addModalAppointmentLabel").innerHTML = "Chỉnh sửa lịch hẹn";
    document.getElementById('appointmentId').classList.remove("d-none");
    // if (parseInt(document.getElementById('addStatus').value) == 1) {
    //     document.getElementById('saveButtonAppointment').disabled = true;
    // } else {
    //     document.getElementById('saveButtonAppointment').disabled = true;
    // }

    // Gửi yêu cầu GET để lấy thông tin cuộc hẹn từ API
    fetch(`/api/appointments/appointmentDetail/${appointmentId}`)
        .then(response => response.json())
        .then(appointment => {
            // Điền dữ liệu vào form
            tmpAppointment = appointment;
            document.getElementById('addAppointmentId').value = appointment.id;  // ID của cuộc hẹn
            document.getElementById('addKhachHang').value = appointment.userName;  // Khách hàng
            document.getElementById('addNhanVien').value = appointment.staffName || '';  // Nhân viên
            document.getElementById('addMakeupDate').value = appointment.makeupDate;  // Ngày makeup
            document.getElementById('addStartTime').value = appointment.startTime;  // Thời gian bắt đầu
            document.getElementById('addEndTime').value = appointment.endTime;  // Thời gian kết thúc

            // Điền tình trạng vào select
            const statusSelect = document.getElementById('addStatus');
            statusSelect.value = appointment.status == 1 ? "1" : "0";  // Duyệt hoặc Chưa duyệt
            if (appointment.status == 1) {
                document.getElementById('addNhanVien').disabled = true;
                document.getElementById('addMakeupDate').disabled = true;
                document.getElementById('addStartTime').disabled = true;
                document.getElementById('addStatus').disabled = true;
                document.getElementById('addServiceMakeupName').disabled = true;
            } else {
                document.getElementById('addNhanVien').disabled = false;
                document.getElementById('addMakeupDate').disabled = false;
                document.getElementById('addStartTime').disabled = false;
                document.getElementById('addStatus').disabled = false;
                document.getElementById('addServiceMakeupName').disabled = false;
            }

            // Điền dịch vụ makeup vào selectbox
            const serviceSelect = document.getElementById('addServiceMakeupName');
            serviceSelect.value = appointment.serviceMakeupID; // Chọn dịch vụ hiện tại

            const staffSelect = document.getElementById('addNhanVien');
            staffSelect.value = appointment.staffID; // Chọn dịch vụ hiện tại

            // Đặt thuộc tính "data-action" của nút lưu là "edit"
            document.getElementById('saveButtonAppointment').setAttribute('data-action', 'edit');
            findAvailableStaff();
            // Hiển thị modal
            const editModal = new bootstrap.Modal(document.getElementById('addModalAppointment'));
            editModal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            // Hiển thị thông báo lỗi nếu có
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin cuộc hẹn!');
        });
}


function searchAppointment() {
    const searchInput = document.getElementById("searchAppointment").value.toLowerCase();

    if (appointmentList.length == 0) {
        fetch('/api/staff')
            .then(response => response.json())
            .then(data => {
                appointmentList = data;
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu:', error);
            });
    }

    // Lọc danh sách nhân viên dựa trên các thuộc tính
    const filteredList = appointmentList.filter((staff) =>
        staff.nameStaff.toLowerCase().includes(searchInput) ||
        staff.phone.includes(searchInput) ||
        staff.id.toString().includes(searchInput)
    );

    // Hiển thị kết quả lọc lên bảng
    renderAppointmentTable(filteredList);
}

function clearFormSearchAppointment() {
    document.getElementById('searchAppointment').value = '';
    //    document.getElementById('statusFilter').selectedIndex = 0;
}