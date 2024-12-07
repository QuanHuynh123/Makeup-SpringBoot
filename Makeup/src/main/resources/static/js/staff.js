let appointmentList = [];
let staffList = [];
let staffListSelect = [];
let makeupList = [];
let tmpAppointment;

function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;

    alertBox.style.position = 'fixed';
    alertBox.style.top = '10%';
    alertBox.style.left = '50%';
    alertBox.style.transform = 'translateX(-50%)';
    alertBox.style.zIndex = '9999';

    document.body.appendChild(alertBox);

    setTimeout(() => {
        alertBox.style.opacity = '0';
        alertBox.style.transform = 'translateX(-50%) translateY(-50%)';
        setTimeout(() => {
            alertBox.remove();
        }, 600);
    }, 3000);
}

// function loadServices() {
//     fetch('/api/services')  // Thay '/api/services' bằng URL chính xác của API
//         .then(response => response.json())
//         .then(data => {
//             const serviceSelect = document.getElementById('addServiceMakeupName');
//             serviceSelect.innerHTML = '<option value="" disabled selected>Chọn dịch vụ</option>';  // Reset các option cũ
//             makeupList = data;
//             data.forEach(service => {
//                 const option = document.createElement('option');
//                 option.value = service.id;  // Sử dụng ID của dịch vụ làm value
//                 option.textContent = service.nameService;  // Hiển thị tên dịch vụ
//                 serviceSelect.appendChild(option);
//             });
//         })
//         .catch(error => console.error('Error loading services:', error));
// }

function findAvailableStaff() {
    // Lấy giá trị từ các trường nhập liệu trong modal
    const startTime = document.getElementById('addStartTime').value;  // Lấy thời gian bắt đầu
    const endTime = document.getElementById('addEndTime').value;      // Lấy thời gian kết thúc
    const day = document.getElementById('addMakeupDate').value;      // Lấy ngày makeup
    const id = document.getElementById('addAppointmentId').value;    // Lấy id của cuộc hẹn (nếu có)
    const staff = document.getElementById('addNhanVien').value;

    // Kiểm tra nếu các giá trị hợp lệ
    if (!startTime || !endTime || !day) {
        console.error("Cần nhập đủ thông tin ngày, thời gian bắt đầu và kết thúc!");
        return;
    }

    // Chuyển đổi startTime và endTime thành đối tượng Date
    const startDate = new Date(`${day}T${startTime}`);
    const endDate = new Date(`${day}T${endTime}`);

    // Tạo một mảng để lưu các nhân viên rảnh
    let availableStaff = [];

    // Lặp qua tất cả nhân viên
    staffListSelect.forEach(staffMember => {
        let isAvailable = true;

        // Kiểm tra xem nhân viên này có cuộc hẹn nào trong khoảng thời gian không
        appointmentList.forEach(appointment => {
            // Chỉ kiểm tra các cuộc hẹn đã được duyệt (status = 1)
            if (appointment.staffID === staffMember.id && appointment.status === 1) {
                const appointmentStart = new Date(`${appointment.makeupDate}T${appointment.startTime}`);
                const appointmentEnd = new Date(`${appointment.makeupDate}T${appointment.endTime}`);

                // Nếu là cuộc hẹn hiện tại (theo ID), cho phép thêm vào danh sách
                if (appointment.id == id) {
                    // Nếu thời gian chọn nằm trong khoảng thời gian cuộc hẹn hiện tại
                    if (startDate >= appointmentStart && endDate <= appointmentEnd) {
                        isAvailable = true; // Nhân viên rảnh trong khoảng thời gian đó
                    } else {
                        isAvailable = false; // Nhân viên không rảnh
                    }
                }
                // Kiểm tra xem lịch hẹn có trùng với thời gian tìm kiếm không
                else if (
                    (startDate >= appointmentStart && startDate < appointmentEnd) ||
                    (endDate > appointmentStart && endDate <= appointmentEnd) ||
                    (startDate <= appointmentStart && endDate >= appointmentEnd)
                ) {
                    isAvailable = false; // Nếu có, nhân viên này không rảnh
                }
            }
        });

        // Nếu không có cuộc hẹn nào trùng, thêm nhân viên này vào danh sách rảnh
        if (isAvailable) {
            availableStaff.push(staffMember);
        }
    });

    // Tải các nhân viên rảnh vào select
    const staffSelect = document.getElementById('addNhanVien');
    staffSelect.innerHTML = '<option value="" disabled selected>Chọn nhân viên</option>'; // Xóa tất cả các tùy chọn cũ

    // Nếu có nhân viên rảnh, thêm các tùy chọn vào dropdown
    if (availableStaff.length > 0) {
        availableStaff.forEach(staffMember => {
            const option = document.createElement('option');
            option.value = staffMember.id;  // Lưu trữ id của nhân viên
            option.textContent = staffMember.id + " - " + staffMember.nameStaff; // Hiển thị tên nhân viên
            staffSelect.appendChild(option);
        });
    } else {
        // Nếu không có nhân viên rảnh, thông báo cho người dùng
        const option = document.createElement('option');
        option.value = '';
        option.textContent = 'Không có nhân viên rảnh';
        staffSelect.appendChild(option);
    }

    staffSelect.value = tmpAppointment.staffID;
}






function loadEndtime() {
    const startTimeInput = document.getElementById('addStartTime');
    const endTimeInput = document.getElementById('addEndTime');
    const serviceMakeupSelect = document.getElementById('addServiceMakeupName'); // Lấy giá trị từ select dịch vụ

    const serviceMakeupID = parseInt(serviceMakeupSelect.value); // Lấy ID dịch vụ được chọn

    // Tìm dịch vụ tương ứng với serviceMakeupID
    const selectedService = makeupList.find(service => service.id === serviceMakeupID);
    if (selectedService) {
        const serviceTime = selectedService.time; // Thời gian dịch vụ (giờ)

        // Lấy giá trị thời gian bắt đầu
        const startTimeValue = startTimeInput.value;
        if (startTimeValue) {
            const [startHour, startMinute] = startTimeValue.split(':').map(Number); // Tách giờ và phút

            // Tính toán thời gian kết thúc bằng cách cộng thêm thời gian dịch vụ
            const endHour = startHour + serviceTime; // Thêm thời gian dịch vụ vào giờ bắt đầu
            const endMinute = startMinute; // Phút không thay đổi

            // Đảm bảo giờ không vượt quá 24 giờ
            const formattedEndHour = endHour % 24;
            const formattedEndTime = `${formattedEndHour.toString().padStart(2, '0')}:${endMinute.toString().padStart(2, '0')}`;

            // Gán thời gian kết thúc vào input
            console.log(formattedEndTime);
            endTimeInput.value = formattedEndTime;
        }
    }
}

// Hàm tải danh sách nhân viên từ API
function loadStaffSelect() {
    fetch('/api/staff')
        .then(response => response.json())
        .then(staffDetails => {
            const staffSelect = document.getElementById('addNhanVien');

            // Xóa các lựa chọn cũ trong select (nếu có)
            staffSelect.innerHTML = '<option value="" disabled selected>Chọn nhân viên</option>';
            staffListSelect = staffDetails;
            // Thêm các lựa chọn mới
            staffDetails.forEach(staff => {
                const option = document.createElement('option');
                option.value = staff.id;  // Giá trị là ID nhân viên
                option.textContent = staff.id + " - " + staff.nameStaff;
                staffSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi tải danh sách nhân viên!');
        });
}


// Hàm tải danh sách nhân viên
function loadStaffs() {
    fetch('/api/staff/staffDetail')
        .then(response => response.json())
        .then(data => {
            // Gọi renderStaffTable để hiển thị dữ liệu vào bảng
            staffList = data;
            renderStaffTable(data);
        })
        .catch(error => {
            console.error('Lỗi khi tải dữ liệu:', error);
        });
}

// Hàm xóa khách hàng
function deleteStaff(id) {
    const customerId = id;
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/api/staff/${customerId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa nhân viên thành công!');
                    loadStaffs();
                    clearFormSearchStaff();
                } else {
                    showAlert('danger', 'Xóa nhân viên thất bại!');
                }
                confirmDeleteModal.hide();
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert('danger', 'Có lỗi xảy ra!');
                confirmDeleteModal.hide();
            });
    };
    confirmDeleteModal.show();
}


// Hàm mở modal thêm nhân viên
function showAddModal() {
    // Reset form và ẩn ID nhân viên
    document.getElementById('addForm').reset();
    document.getElementById("addModalLabel").innerHTML = "Thêm nhân viên";
    document.getElementById('staffId').classList.add("d-none");
    document.getElementById('passStaff').classList.remove("d-none");
    // Đặt thuộc tính "data-action" của nút lưu là "add"s
    document.getElementById('saveButton').setAttribute('data-action', 'add');
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}



// Hàm chỉnh sửa thông tin nhân viên
function editStaff(id) {
    // Lấy ID của nhân viên từ thuộc tính data-id
    const staffId = id;
    document.getElementById("addModalLabel").innerHTML = "Chỉnh sửa nhân viên";
    document.getElementById('staffId').classList.remove("d-none");
    document.getElementById('passStaff').classList.add("d-none");

    // Gửi yêu cầu GET để lấy thông tin nhân viên từ API
    fetch(`/api/staff/staffDetail/${staffId}`)
        .then(response => response.json())
        .then(staff => {
            // Điền dữ liệu vào form
            document.getElementById('addStaffId').value = staff.id;
            document.getElementById('addStaffName').value = staff.nameStaff;
            document.getElementById('addPhoneNumber').value = staff.phone;
            document.getElementById('addUsername').value = staff.username;
            document.getElementById('addRole').value = staff.role;

            // Đặt thuộc tính "data-action" của nút lưu là "edit"
            document.getElementById('saveButton').setAttribute('data-action', 'edit');
            const editModal = new bootstrap.Modal(document.getElementById('addModal'));
            editModal.show();
        })
        .catch(error => {
            console.error('Error:', error);
            // Hiển thị thông báo lỗi nếu có
            showAlert('danger', 'Có lỗi xảy ra khi lấy thông tin nhân viên!');
        });
}

// Hàm thêm nhân viên
function addStaff() {
    const staffName = document.getElementById('addStaffName').value;
    const phoneNumber = document.getElementById('addPhoneNumber').value;
    const username = document.getElementById('addUsername').value;
    const role = document.getElementById('addRole').value;
    const password = document.getElementById('addPassword').value;
    document.getElementById('passStaff').classList.remove("d-none");

    // Kiểm tra tính hợp lệ của dữ liệu
    if (!staffName || !phoneNumber || !username || !role || !password) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const staffData = {
        nameStaff: staffName,
        phone: phoneNumber,
        username: username,
        role: parseInt(role),
        password: password
    };

    console.log(staffData);
}


// Hàm lưu thay đổi thông tin nhân viên
function saveChangesStaff() {
    const id = document.getElementById('addStaffId').value;
    const nameStaff = document.getElementById('addStaffName').value.trim();
    const phone = document.getElementById('addPhoneNumber').value.trim();
    const username = document.getElementById('addUsername').value.trim();
    const role = document.getElementById('addRole').value;
    const password = document.getElementById('addPassword').value; // Chỉnh sửa password nếu cần

    // Kiểm tra các trường dữ liệu bắt buộc (không kiểm tra mật khẩu cho trường hợp update)
    if (!nameStaff || !phone || !username || !role) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Kiểm tra mật khẩu chỉ khi là thêm mới
    if (!id && !password) {
        showAlert('danger', 'Vui lòng điền mật khẩu!');
        return;
    }

    // Kiểm tra xem là hành động thêm mới hay chỉnh sửa
    const url = id ? `/api/staff/staffDetail/${id}` : '/api/staff/staffDetail'; // URL cho hành động sửa hoặc thêm
    const method = id ? 'PUT' : 'POST'; // Nếu có id thì là sửa, nếu không thì là thêm mới

    // Chuẩn bị dữ liệu gửi lên
    const requestData = {
        nameStaff,
        phone,
        username,
        role: parseInt(role),  // Chuyển đổi role sang dạng số nếu cần
    };

    // Nếu là thêm mới thì thêm mật khẩu vào requestData
    if (password) {
        requestData.password = password;  // Chỉ thêm password nếu có
    }

    // Nếu là chỉnh sửa thì thêm `id` vào
    if (id) {
        requestData.id = id;
    }

    // Gửi yêu cầu API
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
    })
        .then(async (response) => {
            const action = id ? 'Sửa' : 'Thêm mới';

            // Kiểm tra trạng thái phản hồi
            if (response.ok) {
                showAlert('success', `${action} nhân viên thành công!`);
                document.getElementById('addStaffId').value = '';
                loadAppointmentsDetail(); // Tải lại danh sách nhân viên
                const editModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
                editModal.hide();
                clearFormSearchStaff(); // Xóa form tìm kiếm nếu có
            } else if (response.status === 400) {
                // Lấy nội dung lỗi từ phản hồi
                const errorData = await response.json();
                if (errorData.message) {
                    showAlert('danger', errorData.message); // Hiển thị thông báo lỗi chi tiết từ server
                } else {
                    showAlert('danger', 'Dữ liệu không hợp lệ hoặc lỗi từ phía máy chủ.');
                }
            } else {
                showAlert('danger', `Có lỗi xảy ra khi ${action.toLowerCase()} nhân viên!`);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi kết nối tới máy chủ!');
        });
}




function removeDiacritics(str) {
    // Đầu tiên, thay thế dấu các ký tự có dấu bằng ký tự không dấu tương ứng
    const map = {
        'à': 'a', 'á': 'a', 'ả': 'a', 'ã': 'a', 'ạ': 'a', 'ả': 'a', 'ă': 'a', 'ặ': 'a', 'ắ': 'a', 'ằ': 'a', 'ẳ': 'a', 'ẵ': 'a', 'â': 'a', 'ầ': 'a', 'ấ': 'a', 'ẩ': 'a', 'ẫ': 'a', 'ậ': 'a',
        'è': 'e', 'é': 'e', 'ẻ': 'e', 'ẽ': 'e', 'ẹ': 'e', 'ê': 'e', 'ề': 'e', 'ế': 'e', 'ể': 'e', 'ễ': 'e', 'ệ': 'e',
        'ì': 'i', 'í': 'i', 'ỉ': 'i', 'ĩ': 'i', 'ị': 'i',
        'ò': 'o', 'ó': 'o', 'ỏ': 'o', 'õ': 'o', 'ọ': 'o', 'ô': 'o', 'ồ': 'o', 'ố': 'o', 'ổ': 'o', 'ỗ': 'o', 'ộ': 'o',
        'ù': 'u', 'ú': 'u', 'ủ': 'u', 'ũ': 'u', 'ụ': 'u', 'ư': 'u', 'ừ': 'u', 'ứ': 'u', 'ử': 'u', 'ữ': 'u', 'ự': 'u',
        'ỳ': 'y', 'ý': 'y', 'ỷ': 'y', 'ỹ': 'y', 'ỵ': 'y',
        'đ': 'd', 'Đ': 'd'
    };

    // Thay thế tất cả các ký tự có dấu thành không dấu
    return str.replace(/[àáảãạâầấẩẫăậèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộùúủũụưừứửữựỳýỷỹỵđĐ]/g, function (match) {
        return map[match];
    }).toLowerCase();
}


function searchStaff() {
    const searchInput = removeDiacritics(document.getElementById("searchStaff").value);

    // Kiểm tra nếu danh sách nhân viên chưa được tải
    if (staffList.length === 0) {
        fetch('/api/staff/staffDetail')
            .then(response => response.json())
            .then(data => {
                staffList = data; // Lưu danh sách nhân viên
                filterAndRenderStaff(searchInput); // Gọi hàm lọc và hiển thị
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu:', error);
            });
    } else {
        filterAndRenderStaff(searchInput); // Nếu đã có dữ liệu, thực hiện lọc
    }
}

function filterAndRenderStaff(searchInput) {
    // Lọc danh sách nhân viên dựa trên các thuộc tính không dấu
    const filteredList = staffList.filter((staff) =>
        removeDiacritics(staff.nameStaff).includes(searchInput) ||
        staff.phone.includes(searchInput) ||
        staff.id.toString().includes(searchInput)
    );

    // Hiển thị kết quả lọc lên bảng
    renderStaffTable(filteredList);
}

// Hàm hiển thị danh sách nhân viên lên bảng
function renderStaffTable(data) {
    const tableContent = document.getElementById('table-content');
    tableContent.innerHTML = ''; // Xóa nội dung cũ

    // Duyệt qua danh sách nhân viên và thêm hàng vào bảng
    data.forEach(staff => {
        // Xác định vai trò dựa trên giá trị role
        let roleName = '';
        switch (staff.role) {
            case 1:
                roleName = 'ADMIN';
                break;
            case 3:
                roleName = 'STAFF';
                break;
            default:
                roleName = 'UNKNOWN'; // Vai trò không xác định
                break;
        }

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${staff.id}</td>
            <td>${staff.nameStaff}</td>
            <td>${staff.phone}</td>
            <td>${staff.username}</td>
            <td>${roleName}</td> <!-- Hiển thị vai trò đã chuyển đổi -->
            <td>
                <button type="button" class="btn btn-warning btn-sm" onclick="editStaff(${staff.id});"><i class="fa-solid fa-pen-to-square"></i></button>
                <button type="button" class="btn btn-danger btn-sm" onclick="deleteStaff(${staff.id})"><i class="fa-solid fa-trash"></i></button>
            </td>
        `;
        tableContent.appendChild(row);
    });
}



// Lắng nghe sự kiện nhập liệu vào ô tìm kiếm
document.getElementById('searchStaff').addEventListener('input', searchStaff);

// Hàm làm mới tìm kiếm
function clearFormSearchStaff() {
    document.getElementById('searchStaff').value = '';
    //    document.getElementById('statusFilter').selectedIndex = 0;
}



////////////////////////////////////////////////////////////////////////////


// Hàm mở modal thêm lịch
function showAddModalAppointment() {
    // Reset form và ẩn ID nhân viên
    document.getElementById('addForm').reset();
    document.getElementById("addModalAppointmentLabel").innerHTML = "Thêm nhân viên";
    document.getElementById('appointmentId').classList.add("d-none");
    // Đặt thuộc tính "data-action" của nút lưu là "add"
    document.getElementById('saveButtonAppointment').setAttribute('data-action', 'add');
    const addModalAppointment = new bootstrap.Modal(document.getElementById('addModalAppointment'));
    addModalAppointment.show();
}

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

function loadMakeUpServices() {
    fetch('/api/serviceMakeups/services')
        .then(response => response.json())
        .then(data => {
            const serviceSelect = document.getElementById('addServiceMakeupName');
            serviceSelect.innerHTML = '<option value="" disabled selected>Chọn dịch vụ</option>';
            makeupList = data;
            data.forEach(service => {
                const option = document.createElement('option');
                option.value = service.id;  // Sử dụng ID của dịch vụ làm value
                option.textContent = service.nameService + " - " + service.time + "h";  // Hiển thị tên dịch vụ
                serviceSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading services:', error));
}

// Hàm xóa khách hàng
function deleteStaff(id) {
    const customerId = id;
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/api/staff/${customerId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa nhân viên thành công!');
                    loadAppointmentsDetail();
                    clearFormSearch();
                } else {
                    showAlert('danger', 'Xóa nhân viên thất bại!');
                }
                confirmDeleteModal.hide();
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert('danger', 'Có lỗi xảy ra!');
                confirmDeleteModal.hide();
            });
    };
    confirmDeleteModal.show();
}





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



// Hàm thêm khách hàng
function addAppointment() {
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

// Hàm lưu thay đổi thông tin cuộc hẹn
function saveChangesAppointment() {
    const id = document.getElementById('addAppointmentId').value; // Lấy ID cuộc hẹn
    const userId = tmpAppointment.userID; // Lấy ID khách hàng
    const staffId = document.getElementById('addNhanVien').value; // Lấy ID nhân viên
    const serviceMakeupId = document.getElementById('addServiceMakeupName').value; // Lấy ID dịch vụ makeup
    const makeupDate = document.getElementById('addMakeupDate').value; // Lấy ngày makeup
    let startTime = document.getElementById('addStartTime').value; // Lấy thời gian bắt đầu
    let endTime = document.getElementById('addEndTime').value; // Lấy thời gian kết thúc
    const status = document.getElementById('addStatus').value; // Lấy tình trạng

    // Kiểm tra các trường dữ liệu bắt buộc
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

// Hàm hiển thị danh sách cuộc hẹn lên bảng
function renderAppointmentTable(data) {
    const tableContent = document.getElementById('table-content');
    tableContent.innerHTML = ''; // Xóa nội dung cũ

    // Duyệt qua danh sách cuộc hẹn và thêm hàng vào bảng
    data.forEach(appointment => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${appointment.id}</td>
            <td >${appointment.userName}</td>
            <td>${appointment.staffName != 'null' ? appointment.staffName : 'Chưa chọn'}</td>
            <td>${appointment.serviceMakeupName}</td>
            <td>${appointment.makeupDate}</td>
            <td>${appointment.startTime}</td>
            <td>${appointment.endTime}</td>
            <td>${appointment.status ? 'Duyệt' : 'Chưa duyệt'}</td>
            <td>
                <button type="button" class="btn btn-warning btn-sm" onclick="editAppointment(${appointment.id});"><i class="fa-solid fa-pen-to-square"></i></button>
                <button type="button" class="btn btn-danger btn-sm" onclick="deleteAppointment(${appointment.id})"><i class="fa-solid fa-trash"></i></button>
            </td>
        `;
        tableContent.appendChild(row);
    });
}


// Lắng nghe sự kiện nhập liệu vào ô tìm kiếm
document.getElementById('searchStaff').addEventListener('input', searchStaff);

// Hàm làm mới tìm kiếm
function clearFormSearchAppointment() {
    document.getElementById('searchAppointment').value = '';
    //    document.getElementById('statusFilter').selectedIndex = 0;
}