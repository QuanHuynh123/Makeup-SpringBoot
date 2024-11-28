/*function showAlert(type, message) {
        const alertBox = document.createElement('div');
        alertBox.className = `alert alert-${type}`;
        alertBox.innerText = message;

        // Đảm bảo vị trí hiển thị hợp lý
        alertBox.style.position = 'fixed';
        alertBox.style.top = '20px'; // Hiển thị ở gần đầu trang
        alertBox.style.left = '50%';
        alertBox.style.transform = 'translateX(-50%)';
        alertBox.style.zIndex = '9999'; // Đảm bảo luôn trên cùng

        document.body.appendChild(alertBox);

        // Hiệu ứng mờ dần
        setTimeout(() => {
            alertBox.style.opacity = '0';
            alertBox.style.transform = 'translateX(-50%) translateY(-10px)'; // Nhẹ nhàng di chuyển lên trên
            setTimeout(() => {
                alertBox.remove();
            }, 600); // Đảm bảo alert đã biến mất hoàn toàn trước khi xóa
        }, 3000);
    }
// Load danh sách khách hàng
function loadCustomers() {
    fetch('/admin/customers/listCustomer')
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = '';
            data.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
                    <td>${customer.sex === 1 ? 'Nam' : 'Nữ'}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.address}</td>
                    <td>${customer.birthday}</td>
                    <td>${customer.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>${customer.time}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCustomer(${customer.customerId})">Chỉnh sửa</button>
                        <button class="btn btn-danger btn-sm" ${customer.status === 0 ? 'disabled' : ''} onclick="deleteCustomer(${customer.customerId})">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}

// Hiển thị modal thêm khách hàng
function showAddModal() {
    document.getElementById('addForm').reset();
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}


// Thêm khách hàng
function addCustomer() {
    const customer = {
        customerName: document.getElementById('addCustomerName').value,
        phoneNumber: document.getElementById('addPhoneNumber').value,
        address: document.getElementById('addAddress').value,
        birthday: document.getElementById('addBirthday').value,
        status: document.getElementById('addStatus').value
    };

    fetch('/admin/customers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(customer)
    })
        .then(response => {
            if (response.ok) {
                loadCustomers();
                new bootstrap.Modal(document.getElementById('addModal')).hide();
            } else {
                alert('Lỗi khi thêm khách hàng!');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Hiển thị modal chỉnh sửa
function editCustomer(button) {
    // Lấy ID từ thuộc tính data-id của nút
    const id = button.getAttribute('data-id');

    // Kiểm tra ID có tồn tại hay không
    if (!id) {
        console.error('Không tìm thấy ID khách hàng.');
        return;
    }
    console.log('ID khách hàng:', id); // Debug ID

    // Gửi yêu cầu lấy thông tin khách hàng
    fetch(`/admin/customers/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Không tìm thấy khách hàng!');
            return response.json();
        })
        .then(customer => {
            // Điền thông tin khách hàng vào modal
            document.getElementById('customerId').value = customer.customerId;
            document.getElementById('customerName').value = customer.customerName;
            document.getElementById('phoneNumber').value = customer.phoneNumber;
            document.getElementById('address').value = customer.address;
            document.getElementById('birthday').value = customer.birthday;
            document.getElementById('status').value = customer.status;

            // Hiển thị modal chỉnh sửa
            new bootstrap.Modal(document.getElementById('editModal')).show();
        })
        .catch(error => console.error('Error:', error));
}



// Lưu thay đổi
function saveChanges() {
    const id = document.getElementById('customerId').value;
    const customer = {
        customerName: document.getElementById('customerName').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        address: document.getElementById('address').value,
        birthday: document.getElementById('birthday').value,
        status: document.getElementById('status').value
    };

    fetch(`/admin/customers/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(customer)
    })
        .then(response => {
            if (response.ok) {
                loadCustomers();
                new bootstrap.Modal(document.getElementById('editModal')).hide();
            } else {
                alert('Lỗi khi lưu thay đổi!');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Xóa khách hàng (chuyển trạng thái thành ngưng hoạt động)
function deleteCustomer(id) {
    const deleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/admin/customers/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    loadCustomers();
                    deleteModal.hide();
                } else {
                    showAlert('danger', 'Lỗi khi xóa khách hàng!');
                }
            })
            .catch(error => console.error('Error:', error));
    };
    deleteModal.show();
}


// Lọc khách hàng theo trạng thái
function filterCustomers() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;

    fetch(`/admin/customers?search=${searchInput}&status=${statusFilter}`)
        .then(response => response.json())
        .then(data => {
            const tableContent = document.getElementById('table-content');
            tableContent.innerHTML = '';
            data.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
                    <td>${customer.sex === 1 ? 'Nam' : 'Nữ'}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.address}</td>
                    <td>${customer.birthday}</td>
                    <td>${customer.status === 1 ? 'Hoạt động' : 'Ngưng hoạt động'}</td>
                    <td>${customer.time}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCustomer(${customer.customerId})">Chỉnh sửa</button>
                        <button class="btn btn-danger btn-sm" ${customer.status === 0 ? 'disabled' : ''} onclick="deleteCustomer(${customer.customerId})">Xóa</button>
                    </td>
                `;
                tableContent.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}

// Tải danh sách khách hàng khi trang được load
document.addEventListener('DOMContentLoaded', loadCustomers);
*/
// Hàm hiển thị thông báo

let staffList = [];

function showAlert(type, message) {
    const alertBox = document.createElement('div');
    alertBox.className = `alert alert-${type}`;
    alertBox.innerText = message;

    alertBox.style.position = 'fixed';
    alertBox.style.top = '20px';
    alertBox.style.left = '50%';
    alertBox.style.transform = 'translateX(-50%)';
    alertBox.style.zIndex = '9999';

    document.body.appendChild(alertBox);

    setTimeout(() => {
        alertBox.style.opacity = '0';
        alertBox.style.transform = 'translateX(-50%) translateY(-10px)';
        setTimeout(() => {
            alertBox.remove();
        }, 600);
    }, 3000);
}

// Hàm tải danh sách nhân viên
function loadCustomers() {
    fetch('/api/staff')
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
function deleteCustomer(button) {
    const customerId = button.getAttribute('data-id');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

    document.getElementById('confirmDeleteButton').onclick = function () {
        fetch(`/admin/customers/delete/${customerId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    showAlert('success', 'Xóa khách hàng thành công!');
                    loadCustomers();
                    clearFormSearch();
                } else {
                    showAlert('danger', 'Xóa khách hàng thất bại!');
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
    // Đặt thuộc tính "data-action" của nút lưu là "add"
    document.getElementById('saveButton').setAttribute('data-action', 'add');
    const addModal = new bootstrap.Modal(document.getElementById('addModal'));
    addModal.show();
}

// Hàm chỉnh sửa thông tin nhân viên
function editCustomer(button) {
    // Lấy ID của nhân viên từ thuộc tính data-id
    const staffId = button.getAttribute('data-id');
    document.getElementById("addModalLabel").innerHTML = "Chỉnh sửa nhân viên";
    document.getElementById('staffId').classList.remove("d-none");

    // Gửi yêu cầu GET để lấy thông tin nhân viên từ API
    fetch(`/api/staff/${staffId}`)
        .then(response => response.json())
        .then(staff => {
            // Điền dữ liệu vào form
            document.getElementById('addCustomerId').value = staff.id;
            document.getElementById('addCustomerName').value = staff.nameStaff;
            document.getElementById('addPhoneNumber').value = staff.phone;

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

// Hàm thêm khách hàng
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
                loadCustomers();
                const addModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
                addModal.hide();
            } else {
                showAlert('danger', 'Thêm khách hàng thất bại!');
            }
        })
        .catch(error => {
            console.error('Lỗi khi thêm khách hàng:', error);
        });
}

// Hàm lưu thay đổi thông tin nhân viên
function saveChanges() {
    const id = document.getElementById('addCustomerId').value;
    const nameStaff = document.getElementById('addCustomerName').value;
    const phone = document.getElementById('addPhoneNumber').value;

    // Kiểm tra các trường dữ liệu bắt buộc
    if (!nameStaff || !phone) {
        showAlert('danger', 'Vui lòng điền đầy đủ thông tin!');
        return;
    }

    // Kiểm tra xem là hành động thêm mới hay chỉnh sửa
    const url = id ? `/api/staff/${id}` : '/api/staff'; // URL cho hành động sửa hoặc thêm
    const method = id ? 'PUT' : 'POST'; // Nếu có customerId thì là sửa, nếu không thì là thêm mới

    // Chuẩn bị dữ liệu gửi lên
    const requestData = {
        nameStaff,
        phone,
    };

    // Nếu là chỉnh sửa thì thêm `customerId` vào
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
        .then(response => {
            if (response.status == 200) {
                const action = id ? 'Sửa' : 'Thêm mới';
                showAlert('success', `${action} nhân viên thành công!`);
                loadCustomers(); // Tải lại danh sách nhân viên
                const editModal = bootstrap.Modal.getInstance(document.getElementById('addModal'));
                editModal.hide();
                clearFormSearch(); // Xóa form tìm kiếm nếu có
            } else {
                showAlert('danger', 'Có lỗi khi lưu thay đổi!');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showAlert('danger', 'Có lỗi xảy ra khi lưu thay đổi!');
        });
}

function searchStaff() {
    const searchInput = document.getElementById("searchStaff").value.toLowerCase();


    if (staffList.length == 0) {
        fetch('/api/staff')
            .then(response => response.json())
            .then(data => {
                staffList = data;
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu:', error);
            });
    }

    // Lọc danh sách nhân viên dựa trên các thuộc tính
    const filteredList = staffList.filter((staff) =>
        staff.nameStaff.toLowerCase().includes(searchInput) ||
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
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${staff.id}</td>
            <td>${staff.nameStaff}</td>
            <td>${staff.phone}</td>
            <td>
                <button type="button" class="btn btn-warning btn-sm" onclick="editStaff(${staff.id})">Chỉnh sửa</button>
                <button type="button" class="btn btn-danger btn-sm" onclick="deleteStaff(${staff.id})">Xóa</button>
            </td>
        `;
        tableContent.appendChild(row);
    });
}

// Lắng nghe sự kiện nhập liệu vào ô tìm kiếm
document.getElementById('searchStaff').addEventListener('input', searchStaff);

// Hàm làm mới tìm kiếm
function clearFormSearch() {
    document.getElementById('searchStaff').value = '';
//    document.getElementById('statusFilter').selectedIndex = 0;
}