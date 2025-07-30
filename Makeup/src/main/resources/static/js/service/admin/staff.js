export const StaffModule = {
    staffList: [],
    sortDirection: null,
    currentRole: null,

    showAlert(type, message) {
        Swal.fire({
            icon: type === 'success' ? 'success' : 'error',
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    },

    removeDiacritics(str) {
        const map = {
            'à': 'a', 'á': 'a', 'ả': 'a', 'ã': 'a', 'ạ': 'a', 'ă': 'a', 'ặ': 'a', 'ắ': 'a', 'ằ': 'a', 'ẳ': 'a', 'ẵ': 'a', 'â': 'a', 'ầ': 'a', 'ấ': 'a', 'ẩ': 'a', 'ẫ': 'a', 'ậ': 'a',
            'è': 'e', 'é': 'e', 'ẻ': 'e', 'ẽ': 'e', 'ẹ': 'e', 'ê': 'e', 'ề': 'e', 'ế': 'e', 'ể': 'e', 'ễ': 'e', 'ệ': 'e',
            'ì': 'i', 'í': 'i', 'ỉ': 'i', 'ĩ': 'i', 'ị': 'i',
            'ò': 'o', 'ó': 'o', 'ỏ': 'o', 'õ': 'o', 'ọ': 'o', 'ô': 'o', 'ồ': 'o', 'ố': 'o', 'ổ': 'o', 'ỗ': 'o', 'ộ': 'o',
            'ù': 'u', 'ú': 'u', 'ủ': 'u', 'ũ': 'u', 'ụ': 'u', 'ư': 'u', 'ừ': 'u', 'ứ': 'u', 'ử': 'u', 'ữ': 'u', 'ự': 'u',
            'ỳ': 'y', 'ý': 'y', 'ỷ': 'y', 'ỹ': 'y', 'ỵ': 'y',
            'đ': 'd', 'Đ': 'd'
        };
        return str.replace(/[àáảãạâầấẩẫăậèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộùúủũụưừứửữựỳýỷỹỵđĐ]/g, match => map[match]).toLowerCase();
    },

    loadStaff() {
        const sortStr = this.sortDirection || 'nameStaff,asc';
        const roleParam = this.currentRole !== null ? `&role=${this.currentRole}` : '';

        fetch(`/api/admin/staffs?sort=${sortStr}${roleParam}`)
            .then(res => {
                if (!res.ok) throw new Error('Lỗi kết nối API');
                return res.json();
            })
            .then(data => {
                if (data && data.result) {
                    this.staffList = data.result;
                    this.renderStaff(this.staffList);
                } else {
                    this.showAlert('danger', 'Không có dữ liệu!');
                    this.staffList = [];
                    this.renderStaff([]);
                }
            })
            .catch(err => {
                console.error('Lỗi:', err);
                this.staffList = [];
                this.renderStaff([]);
            });
    },

    renderStaff(list) {
        const tbody = document.querySelector('#table-content-staff');
        tbody.innerHTML = '';
        if (!list || list.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="text-center">Chưa có nhân viên nào!</td></tr>`;
            return;
        }

        list.forEach((staff, i) => {
            const roleName = staff.role === 1 ? 'Admin' : staff.role === 3 ? 'Staff' : 'Unknown';
            tbody.innerHTML += `
                <tr>
                    <td>${i + 1}</td>
                    <td>${staff.nameStaff || 'N/A'}</td>
                    <td>${staff.phone || 'N/A'}</td>
                    <td>${staff.username || 'N/A'}</td>
                    <td>${roleName}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-edit" data-id="${staff.id}">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </button>
                        <button class="btn btn-danger btn-sm btn-delete" data-id="${staff.id}">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });
    },

    sortTable(column) {
        const direction = this.sortDirection && this.sortDirection.startsWith(column + ',')
            ? (this.sortDirection.endsWith('asc') ? 'desc' : 'asc')
            : 'asc';
        this.sortDirection = `${column},${direction}`;

        document.querySelectorAll('th.sortable').forEach(th => {
            th.classList.remove('asc', 'desc');
        });
        const th = document.querySelector(`th[data-sort="${column}"]`);
        if (th) th.classList.add(direction);

        this.loadStaff();
    },

    searchStaff() {
        const text = this.removeDiacritics(document.getElementById('searchStaff').value.toLowerCase());
        if (!this.staffList.length) {
            this.loadStaff().then(() => this.searchStaff());
            return;
        }
        const filtered = this.staffList.filter(s =>
            this.removeDiacritics(s.nameStaff || '').includes(text) ||
            (s.phone || '').includes(text) ||
            this.removeDiacritics(s.username || '').includes(text) ||
            (s.id || '').toString().includes(text)
        );
        this.renderStaff(filtered);
    },

    filterStaff() {
        this.currentRole = document.getElementById('staffFilter').value;
        this.loadStaff();
    },

    showAddModal() {
        document.getElementById('addForm').reset();
        document.getElementById('addModalLabel').innerHTML = 'Thêm nhân viên';
        document.getElementById('staffId').classList.add('d-none');
        document.getElementById('passStaff').classList.remove('d-none');
        document.getElementById('saveButton').setAttribute('data-action', 'add');
        const addModal = new bootstrap.Modal(document.getElementById('addModal'));
        addModal.show();
    },

    saveStaff() {
        const id = document.getElementById('addStaffId').value;
        const nameStaff = document.getElementById('addStaffName').value.trim();
        const phone = document.getElementById('addPhoneNumber').value.trim();
        const username = document.getElementById('addUsername').value.trim();
        const role = document.getElementById('addRole').value;
        const password = document.getElementById('addPassword').value.trim();

        if (!nameStaff || !phone || !username || !role || (!id && !password)) {
            this.showAlert('error', 'Vui lòng điền đầy đủ thông tin!');
            return;
        }

        const staffData = {
            nameStaff,
            phone,
            username,
            role: parseInt(role)
        };
        if (!id) staffData.password = password;
        if (id) staffData.id = id;

        const url = id ? `/api/admin/staffs/${id}` : '/api/admin/staffs/create';
        const method = id ? 'PUT' : 'POST';

        fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(staffData)
        })
            .then(res => res.ok ? res.json() : Promise.reject(res))
            .then(data => {
                this.showAlert('success', id ? 'Cập nhật nhân viên thành công!' : 'Thêm nhân viên thành công!');
                this.loadStaff();
                bootstrap.Modal.getInstance(document.getElementById('addModal')).hide();
            })
            .catch(err => {
                console.error('Lỗi:', err);
                this.showAlert('error', id ? 'Cập nhật nhân viên thất bại!' : 'Thêm nhân viên thất bại!');
            });
    },

    editStaff(id) {
        fetch(`/api/admin/staffs/${id}`)
            .then(res => res.ok ? res.json() : Promise.reject(res))
            .then(data => {
                const staff = data.result;
                document.getElementById('addModalLabel').innerHTML = 'Chỉnh sửa nhân viên';
                document.getElementById('staffId').classList.remove('d-none');
                document.getElementById('passStaff').classList.add('d-none');
                document.getElementById('addStaffId').value = staff.id;
                document.getElementById('addStaffName').value = staff.nameStaff;
                document.getElementById('addPhoneNumber').value = staff.phone;
                document.getElementById('addUsername').value = staff.username;
                document.getElementById('addRole').value = staff.role;
                document.getElementById('saveButton').setAttribute('data-action', 'edit');
                const editModal = new bootstrap.Modal(document.getElementById('addModal'));
                editModal.show();
            })
            .catch(err => {
                console.error('Lỗi:', err);
                this.showAlert('error', 'Không thể tải thông tin nhân viên!');
            });
    },

    deleteStaff(id) {
        const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
        document.getElementById('confirmDeleteButton').onclick = () => {
            fetch(`/api/admin/staffs/${id}`, { method: 'DELETE' })
                .then(res => res.ok ? res.json() : Promise.reject(res))
                .then(data => {
                    this.showAlert('success', data.message || 'Xóa nhân viên thành công!');
                    this.loadStaff();
                    confirmDeleteModal.hide();
                })
                .catch(err => {
                    console.error('Lỗi:', err);
                    this.showAlert('error', 'Xóa nhân viên thất bại!');
                    confirmDeleteModal.hide();
                });
        };
        confirmDeleteModal.show();
    },

    init() {
        this.loadStaff();

        document.getElementById('searchStaff')
            ?.addEventListener('input', () => this.searchStaff());

        document.getElementById('staffFilter')
            ?.addEventListener('change', () => this.filterStaff());

        document.querySelectorAll('th.sortable').forEach(th => {
            th.addEventListener('click', () => {
                const column = th.getAttribute('data-sort');
                this.sortTable(column);
            });
        });

        document.getElementById('table-content-staff')
            ?.addEventListener('click', e => {
                const editBtn = e.target.closest('.btn-edit');
                const deleteBtn = e.target.closest('.btn-delete');
                const id = (editBtn || deleteBtn)?.getAttribute('data-id');
                if (editBtn) this.editStaff(id);
                else if (deleteBtn) this.deleteStaff(id);
            });

        document.getElementById('tab-create-staff')
            ?.addEventListener('click', () => this.showAddModal());
    }
};

$(document).ready(function () {
    StaffModule.init();
});