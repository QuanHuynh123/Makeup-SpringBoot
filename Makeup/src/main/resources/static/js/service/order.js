export const OrderModule = {
    ordersList: [],
    currentPage: 0,
    pageSize: 10,
    totalPages: 0,
    sortDirection: {},
    currentStatus: 0,

    showAlert(type, message) {
        Swal.fire({
            icon: type === 'success' ? 'success' : 'error',
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    },

    formatDate(dateStr) {
        if (!dateStr) return '';
        return new Date(dateStr).toLocaleDateString('vi-VN');
    },

    renderOrders(list) {
        const tbody = document.querySelector('#table-content-orders');
        tbody.innerHTML = '';
        if (!list || list.length === 0) {
            tbody.innerHTML = `<tr><td colspan="10" class="text-center">Chưa có đơn đặt hàng nào!</td></tr>`;
            return;
        }

        list.forEach((o, i) => {
            tbody.innerHTML += `
                <tr>
                    <td>${this.currentPage * this.pageSize + i + 1}</td>
                    <td>${o.nameUser || 'N/A'}</td>
                    <td>${o.phone || 'N/A'}</td>
                    <td>${o.totalPrice || 0}</td>
                    <td>${o.totalQuantity || 0}</td>
                    <td>${this.formatDate(o.orderDate)}</td>
                    <td>${this.formatDate(o.pickupDate)}</td>
                    <td>${o.paymentMethod || 'N/A'}</td>
                    <td>${o.status || 'N/A'}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-approve" data-id="${o.id}">
                            <i class="fa-solid fa-check"></i>
                        </button>
                        <button class="btn btn-danger btn-sm btn-delete" data-id="${o.id}">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>`;
        });

        this.updatePagination();
    },

    updatePagination() {
        const pagination = document.querySelector('#pagination');
        pagination.innerHTML = '';

        const prev = `<li class="page-item ${this.currentPage === 0 ? 'disabled' : ''}">
                        <a class="page-link" href="#">Trước</a></li>`;
        pagination.innerHTML += prev;

        for (let i = 0; i < this.totalPages; i++) {
            pagination.innerHTML += `<li class="page-item ${i === this.currentPage ? 'active' : ''}">
                <a class="page-link" href="#">${i + 1}</a></li>`;
        }

        const next = `<li class="page-item ${this.currentPage === this.totalPages - 1 ? 'disabled' : ''}">
                        <a class="page-link" href="#">Sau</a></li>`;
        pagination.innerHTML += next;

        document.querySelectorAll('#pagination .page-link').forEach((el, idx) => {
            if (idx === 0 || idx === this.totalPages + 1) {
                el.addEventListener('click', e => {
                    e.preventDefault();
                    if (idx === 0 && this.currentPage > 0) this.currentPage--;
                    else if (idx === this.totalPages + 1 && this.currentPage < this.totalPages - 1) this.currentPage++;
                    this.loadPagedOrders();
                });
            } else {
                el.addEventListener('click', e => {
                    e.preventDefault();
                    this.currentPage = idx - 1;
                    this.loadPagedOrders();
                });
            }
        });
    },

    loadPagedOrders() {
        const sortStr = Object.keys(this.sortDirection)
            .map(k => `${k},${this.sortDirection[k]}`).join('|') || 'orderDate,desc';

        fetch(`/api/admin/orders?page=${this.currentPage}&size=${this.pageSize}&sort=${sortStr}&status=${this.currentStatus}`)
            .then(res => {
                if (!res.ok) throw new Error('Lỗi kết nối API');
                return res.json();
            })
            .then(data => {
                if (data && data.result && data.result.content) {
                    this.ordersList = data.result.content;
                    this.totalPages = data.result.totalPages || 1; // Đặt mặc định totalPages = 1 nếu null
                    this.renderOrders(this.ordersList);
                } else {
                    this.showAlert('danger', 'Không có dữ liệu!');
                    this.ordersList = [];
                    this.totalPages = 1;
                    this.renderOrders([]);
                }
            })
            .catch(err => {
                console.error('Lỗi:', err);
                this.showAlert('danger', 'Tải dữ liệu thất bại!');
                this.ordersList = [];
                this.totalPages = 1;
                this.renderOrders([]);
            });
    },

    sortTable(column) {
        const direction = this.sortDirection[column] === 'asc' ? 'desc' : 'asc';
        this.sortDirection[column] = direction;
        this.currentPage = 0; // Reset về trang đầu khi sắp xếp
        this.loadPagedOrders(); // Tải lại từ API với sort mới
    },

    searchOrder() {
        const text = document.getElementById('searchOrder').value.toLowerCase();
        if (!this.ordersList.length) {
            this.loadPagedOrders().then(() => this.searchOrder());
            return;
        }
        const filtered = this.ordersList.filter(o =>
            o.nameUser?.toLowerCase().includes(text) ||
            o.phone?.toLowerCase().includes(text) ||
            o.id?.toString().includes(text)
        );
        this.renderOrders(filtered);
    },

    filterOrders() {
        this.currentStatus = document.getElementById('orderFilter').value;
        this.currentPage = 0;
        this.loadPagedOrders();
    },

    approveOrder(orderId) {
        fetch(`/api/admin/orders/approve/${orderId}`, { method: 'POST' })
            .then(res => res.ok ? res.json() : Promise.reject(res))
            .then(data => {
                this.showAlert('success', data.message || 'Duyệt thành công!');
                this.loadPagedOrders();
            })
            .catch(err => {
                console.error('Lỗi duyệt:', err);
                this.showAlert('danger', 'Duyệt thất bại!');
            });
    },

    deleteOrder(orderId) {
        fetch(`/api/admin/orders/delete/${orderId}`, { method: 'DELETE' })
            .then(res => res.ok ? res.json() : Promise.reject(res))
            .then(data => {
                this.showAlert('success', data.message || 'Xóa thành công!');
                this.loadPagedOrders();
            })
            .catch(err => {
                console.error('Lỗi xóa:', err);
                this.showAlert('danger', 'Xóa thất bại!');
            });
    },

    init() {
        this.loadPagedOrders();

        document.getElementById('searchOrder')
            .addEventListener('input', () => this.searchOrder());

        document.querySelectorAll('th.sortable').forEach(th => {
            th.addEventListener('click', () => {
                const column = th.getAttribute('data-sort');
                this.sortTable(column);
            });
        });

        document.getElementById('orderFilter')
            .addEventListener('change', () => this.filterOrders());

        document.getElementById('table-content-orders')
            .addEventListener('click', e => {
                const approveBtn = e.target.closest('.btn-approve');
                const deleteBtn = e.target.closest('.btn-delete');
                const id = (approveBtn || deleteBtn)?.getAttribute('data-id');
                if (approveBtn) this.approveOrder(id);
                else if (deleteBtn) this.deleteOrder(id);
            });
    }
};