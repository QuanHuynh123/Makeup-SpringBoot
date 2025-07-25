export const AppointmentModule = {
    appointmentList: [],
    tmpAppointment: {},

    showAlert(type, message) {
        Swal.fire({
            position: "mid",
            icon: type === 'success' ? 'success' : 'error',
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    },

    renderAppointmentTable(appointments) {
        const tableBody = document.querySelector('#table-content-appointments');
        if (!tableBody) {
            console.error('Không tìm thấy tbody#table-content-appointments');
            this.showAlert('danger', 'Không tìm thấy bảng để hiển thị dữ liệu!');
            return;
        }

        tableBody.innerHTML = '';
        if (!appointments || !Array.isArray(appointments) || appointments.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="9" class="text-center">Không có lịch hẹn nào</td></tr>';
            return;
        }

        appointments.forEach((appointment,index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${appointment.nameUser || 'N/A'}</td>
                <td>${appointment.nameStaff || 'N/A'}</td>
                <td>${appointment.typeMakeupName || 'N/A'}</td>
                <td>${appointment.makeupDate}</td>
                <td>${appointment.startTime}</td>
                <td>${appointment.endTime}</td>
                <td>${appointment.status ? 'Hoàn thành' : 'Chưa hoàn thành'}</td>
                <td>
                    <button type="button" class="btn btn-warning btn-sm" data-id="${appointment.id}">
                        <i class="fa-solid fa-pen-to-square"></i>
                    </button>
                    <button type="button" class="btn btn-danger btn-sm" data-id="${appointment.id}">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    },

    loadAppointments() {
        fetch('/api/appointments')
            .then(response => response.ok ? response.json() : Promise.reject(response))
            .then(data => {
                if (!data || !Array.isArray(data.result)) {
                    this.showAlert('danger', 'Không có dữ liệu lịch hẹn hoặc dữ liệu không hợp lệ!');
                    this.appointmentList = [];
                    this.renderAppointmentTable([]);
                    return;
                }
                this.appointmentList = data.result;
                this.renderAppointmentTable(data.result);
            })
            .catch(error => {
                console.error('Lỗi khi tải lịch hẹn:', error);
                this.showAlert('danger', 'Không thể tải danh sách lịch hẹn!');
                this.appointmentList = [];
                this.renderAppointmentTable([]);
            });
    },

    saveChangesAppointment() {
        const id = document.getElementById('addAppointmentId').value;
        const userId = this.tmpAppointment.userID;
        const staffId = document.getElementById('addNhanVien').value;
        const serviceMakeupId = document.getElementById('addServiceMakeupName').value;
        const makeupDate = document.getElementById('addMakeupDate').value;
        let startTime = document.getElementById('addStartTime').value;
        let endTime = document.getElementById('addEndTime').value;
        const status = document.getElementById('addStatus').value;

        if (!userId || !staffId || !serviceMakeupId || !makeupDate || !startTime || !endTime || !status) {
            this.showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
            return;
        }

        startTime = startTime.match(/^\d{2}:\d{2}:\d{2}$/) ? startTime : `${startTime}:00`;
        endTime = endTime.match(/^\d{2}:\d{2}:\d{2}$/) ? endTime : `${endTime}:00`;

        const requestData = { userId, staffId, serviceMakeupId, makeupDate, startTime, endTime, status: parseInt(status) };
        const url = id ? `/api/appointments/${id}` : '/api/appointments';
        const method = id ? 'PUT' : 'POST';

        fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        })
            .then(async response => {
                const data = await response.json();
                if (response.ok && data.code === 200) {
                    this.showAlert('success', id ? 'Cập nhật cuộc hẹn thành công!' : 'Thêm cuộc hẹn thành công!');
                    document.getElementById('addAppointmentId').value = '';
                    this.loadAppointments();
                    bootstrap.Modal.getInstance(document.getElementById('addModalAppointment')).hide();
                    this.clearFormSearchAppointment();
                } else {
                    this.showAlert('danger', data.message || 'Lỗi từ máy chủ!');
                }
            })
            .catch(error => {
                console.error('Lỗi khi lưu lịch hẹn:', error);
                this.showAlert('danger', 'Không thể kết nối tới máy chủ!');
            });
    },

    editAppointment(element) {
        const id = element.getAttribute('data-id');
        document.getElementById('addModalAppointmentLabel').innerHTML = 'Chỉnh sửa lịch hẹn';
        document.getElementById('appointmentId').classList.remove('d-none');

        fetch(`/api/appointments/appointmentDetail/${id}`)
            .then(response => response.ok ? response.json() : Promise.reject(response))
            .then(data => {
                const appointment = data.result;
                if (!appointment) {
                    this.showAlert('danger', 'Không tìm thấy thông tin lịch hẹn!');
                    return;
                }

                this.tmpAppointment = appointment;
                document.getElementById('addAppointmentId').value = appointment.id;
                document.getElementById('addKhachHang').value = appointment.nameUser || '';
                document.getElementById('addNhanVien').value = appointment.staffId || '';
                document.getElementById('addServiceMakeupName').value = appointment.serviceMakeupId || '';
                document.getElementById('addMakeupDate').value = appointment.makeupDate;
                document.getElementById('addStartTime').value = appointment.startTime;
                document.getElementById('addEndTime').value = appointment.endTime;
                document.getElementById('addStatus').value = appointment.status ? '1' : '0';

                const fields = ['addNhanVien', 'addMakeupDate', 'addStartTime', 'addStatus', 'addServiceMakeupName'];
                fields.forEach(field => {
                    document.getElementById(field).disabled = appointment.status == 1;
                });

                document.getElementById('saveButtonAppointment').setAttribute('data-action', 'edit');
                this.findAvailableStaff();
                new bootstrap.Modal(document.getElementById('addModalAppointment')).show();
            })
            .catch(error => {
                console.error('Lỗi khi lấy lịch hẹn:', error);
                this.showAlert('danger', 'Không thể lấy thông tin lịch hẹn!');
            });
    },

    deleteAppointment(element) {
        const id = element.getAttribute('data-id');
        const confirmModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
        confirmModal.show();

        document.getElementById('confirmDeleteButton').onclick = () => {
            fetch(`/api/appointments/${id}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        this.showAlert('success', 'Xóa lịch hẹn thành công!');
                        this.loadAppointments();
                        confirmModal.hide();
                    } else {
                        this.showAlert('danger', 'Xóa lịch hẹn thất bại!');
                    }
                })
                .catch(error => {
                    console.error('Lỗi khi xóa lịch hẹn:', error);
                    this.showAlert('danger', 'Không thể kết nối tới máy chủ!');
                });
        };
    },

    searchAppointment() {
        const searchInput = document.getElementById('searchAppointment').value.toLowerCase();

        if (this.appointmentList.length === 0) {
            fetch('/api/appointments')
                .then(response => response.ok ? response.json() : Promise.reject(response))
                .then(data => {
                    if (!data || !Array.isArray(data.result)) {
                        this.showAlert('danger', 'Không có dữ liệu lịch hẹn!');
                        this.renderAppointmentTable([]);
                        return;
                    }
                    this.appointmentList = data.result;
                    this.filterAndRender(searchInput);
                })
                .catch(error => {
                    console.error('Lỗi khi tải lịch hẹn:', error);
                    this.showAlert('danger', 'Không thể tải danh sách lịch hẹn!');
                    this.renderAppointmentTable([]);
                });
        } else {
            this.filterAndRender(searchInput);
        }
    },

    filterAndRender(searchInput) {
        const filteredList = this.appointmentList.filter(appointment =>
            appointment.nameUser?.toLowerCase().includes(searchInput) ||
            appointment.nameStaff?.toLowerCase().includes(searchInput) ||
            appointment.id?.toString().includes(searchInput)
        );
        this.renderAppointmentTable(filteredList);
    },

    clearFormSearchAppointment() {
        document.getElementById('searchAppointment').value = '';
        this.renderAppointmentTable(this.appointmentList);
    },

    loadMakeUpServices() {
        fetch('/api/makeup-services')
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('addServiceMakeupName');
                select.innerHTML = '<option value="" disabled selected>Chọn dịch vụ</option>';
                data.result.forEach(service => {
                    const option = document.createElement('option');
                    option.value = service.id;
                    option.textContent = service.name;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading makeup services:', error));
    },

    loadStaffSelect() {
        fetch('/api/staff')
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('addNhanVien');
                select.innerHTML = '<option value="" disabled selected>Chọn nhân viên</option>';
                data.result.forEach(staff => {
                    const option = document.createElement('option');
                    option.value = staff.id;
                    option.textContent = staff.name;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading staff:', error));
    },

    loadEndtime() {
        const serviceId = document.getElementById('addServiceMakeupName').value;
        const startTime = document.getElementById('addStartTime').value;

        if (!serviceId || !startTime) {
            document.getElementById('addEndTime').value = '';
            return;
        }

        fetch(`/api/makeup-services/${serviceId}`)
            .then(response => response.json())
            .then(data => {
                const duration = data.result.duration;
                const [hours, minutes] = startTime.split(':').map(Number);
                const endTime = new Date();
                endTime.setHours(hours, minutes + duration);
                document.getElementById('addEndTime').value = endTime.toTimeString().slice(0, 5);
            })
            .catch(error => console.error('Error loading end time:', error));
    },

    findAvailableStaff() {
        const makeupDate = document.getElementById('addMakeupDate').value;
        const startTime = document.getElementById('addStartTime').value;
        const serviceId = document.getElementById('addServiceMakeupName').value;

        if (!makeupDate || !startTime || !serviceId) return;

        fetch(`/api/staff/available?makeupDate=${makeupDate}&startTime=${startTime}&serviceId=${serviceId}`)
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('addNhanVien');
                select.innerHTML = '<option value="" disabled selected>Chọn nhân viên</option>';
                data.result.forEach(staff => {
                    const option = document.createElement('option');
                    option.value = staff.id;
                    option.textContent = staff.name;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading available staff:', error));
    },

    init() {
        console.log('AppointmentModule initialized');
        this.loadAppointments();
        this.loadMakeUpServices();
        this.loadStaffSelect();

        document.getElementById('appointment').addEventListener('click', (e) => {
            const editButton = e.target.closest('.btn-warning.btn-sm');
            const deleteButton = e.target.closest('.btn-danger.btn-sm');
            if (editButton) {
                this.editAppointment(editButton);
                this.loadEndtime();
            } else if (deleteButton) {
                this.deleteAppointment(deleteButton);
            }
        });
        const searchInput = document.getElementById('searchAppointment');
        if (searchInput) {
            searchInput.addEventListener('input', () => this.searchAppointment());
        }
        const saveButton = document.getElementById('saveButtonAppointment');
        if (saveButton) {
            saveButton.addEventListener('click', () => this.saveChangesAppointment());
        }
        const serviceSelect = document.getElementById('addServiceMakeupName');
        const startTimeInput = document.getElementById('addStartTime');
        const makeupDateInput = document.getElementById('addMakeupDate');
        if (serviceSelect) {
            serviceSelect.addEventListener('change', () => {
                this.loadEndtime();
                this.findAvailableStaff();
            });
        }
        if (startTimeInput) {
            startTimeInput.addEventListener('change', () => {
                this.loadEndtime();
                this.findAvailableStaff();
            });
        }
        if (makeupDateInput) {
            makeupDateInput.addEventListener('change', () => this.findAvailableStaff());
        }
    }
};