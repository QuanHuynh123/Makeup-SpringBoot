export const ProductModule = {
    productsList: [],
    currentPage: 0,
    pageSize: 10,
    totalPages: 0,
    sortDirection: null,
    currentStatus: null,

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

    loadPagedProducts() {
        const sortStr = this.sortDirection || 'createdAt,desc';
        const statusParam = this.currentStatus !== null ? `&status=${this.currentStatus}` : '';

        fetch(`/api/admin/products?page=${this.currentPage}&size=${this.pageSize}&sort=${sortStr}${statusParam}`)
            .then(res => {
                if (!res.ok) throw new Error('Lỗi kết nối API');
                return res.json();
            })
            .then(data => {
                if (data && data.result) {
                    this.productsList = data.result.content;
                    this.totalPages = data.result.totalPages || 1;
                    this.renderProduct(this.productsList);
                } else {
                    this.showAlert('danger', 'Không có dữ liệu!');
                    this.productsList = [];
                    this.totalPages = 1;
                    this.renderProduct([]);
                }
            })
            .catch(err => {
                console.error('Lỗi:', err);
                this.productsList = [];
                this.totalPages = 1;
                this.renderProduct([]);
            });
    },

    renderProduct(list) {
        const tbody = document.querySelector('#table-content-products');
        tbody.innerHTML = '';
        if (!list || list.length === 0) {
            tbody.innerHTML = `<tr><td colspan="9" class="text-center">Chưa có sản phẩm nào!</td></tr>`;
            return;
        }

        list.forEach((product, i) => {
            tbody.innerHTML += `
                <tr>
                    <td>${this.currentPage * this.pageSize + i + 1}</td>
                    <td>${product.nameProduct || 'N/A'}</td>
                    <td>${product.subCategoryName || 'N/A'}</td>
                    <td>${product.price || 0}</td>
                    <td>${product.size || 'N/A'}</td>
                    <td>${product.status ? "Còn đồ" : "Hết đồ"}</td>
                    <td>${product.rentalCount || 0}</td>
                    <td>${this.formatDate(product.createdAt)}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-edit" data-id="${product.id}">
                            <i class="fa-regular fa-pen-to-square"></i>
                        </button>
                        <button class="btn btn-danger btn-sm btn-delete" data-id="${product.id}">
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
                    this.loadPagedProducts();
                });
            } else {
                el.addEventListener('click', e => {
                    e.preventDefault();
                    this.currentPage = idx - 1;
                    this.loadPagedProducts();
                });
            }
        });
    },

    sortTable(column) {
        const direction = this.sortDirection && this.sortDirection.startsWith(column + ',')
            ? (this.sortDirection.endsWith('asc') ? 'desc' : 'asc')
            : 'asc';
        this.sortDirection = `${column},${direction}`;
        this.currentPage = 0;
        this.loadPagedProducts();
    },

    searchProduct() {
        const text = document.getElementById('searchProduct').value.toLowerCase();
        if (!this.productsList.length) {
            this.loadPagedProducts().then(() => this.searchProduct());
            return;
        }
        const filtered = this.productsList.filter(p =>
            (p.nameProduct?.toLowerCase().includes(text) || '') ||
            (p.subCategoryName?.toLowerCase().includes(text) || '') ||
            (p.size?.toLowerCase().includes(text) || '') ||
            (p.price?.toString().includes(text) || '') ||
            (p.rentalCount?.toString().includes(text) || '')
        );
        this.renderProduct(filtered);
    },

    filterProducts() {
        this.currentStatus = document.getElementById('productFilter').value;
        this.currentPage = 0;
        this.loadPagedProducts();
    },

    editProduct(productId) {
        $('.btn-create').data('product-id', productId);
        $.ajax({
            url: `/api/admin/products/${productId}`,
            type: 'GET',
            success: function(product) {
                console.log('Product data:', product); // Debugging
                // Populate form fields
                $('#nameProduct').val(product.result.nameProduct);
                $('#price').val(product.result.price);
                $('#description').val(product.result.describe);
                $('#size').val(product.result.size);
                $('#subCategory').val(product.result.subCategoryId);
                $('#status').val(product.result.status.toString());

                // Populate existing images
                $('#image-gallery').html('');
                if (product.result.image) {
                    const images = product.result.image.split(',');
                    images.forEach(image => {
                        if (image) {
                            $('#image-gallery').append(`
                                <div>
                                    <img src="/images/product/${image.trim()}" alt="Product Image" style="width: 100px; height: auto;">
                                </div>
                            `);
                        }
                    });
                }

                // Show modal
                $('#editProductModal').modal('show');
            },
            error: function(xhr) {
                console.error('Error fetching product:', xhr); // Debugging
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Không thể tải thông tin sản phẩm',
                });
            }
        });
    },

    deleteProduct(productId) {
        Swal.fire({
            title: 'Bạn có chắc chắn?',
            text: "Hành động này không thể hoàn tác!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`/api/products/${productId}/delete`, {
                    method: 'DELETE'
                })
                .then(res => res.ok ? res.json() : Promise.reject(res))
                .then(data => {
                    this.showAlert('success', data.message || 'Đã xóa sản phẩm thành công!');
                    this.loadPagedProducts();
                })
                .catch(err => {
                    console.error('Lỗi xóa:', err);
                    this.showAlert('error', 'Đã xảy ra lỗi khi xóa sản phẩm!');
                });
            }
        });
    },

    init() {
        this.loadPagedProducts();

        document.getElementById('searchProduct')
            ?.addEventListener('input', () => this.searchProduct());

        document.getElementById('productFilter')
            ?.addEventListener('change', () => this.filterProducts());

        document.querySelectorAll('th.sortable').forEach(th => {
            th.addEventListener('click', () => {
                const column = th.getAttribute('data-sort');
                this.sortTable(column);
            });
        });

        document.getElementById('table-content-products')
            ?.addEventListener('click', e => {
                const editBtn = e.target.closest('.btn-edit');
                const deleteBtn = e.target.closest('.btn-delete');
                const id = (editBtn || deleteBtn)?.getAttribute('data-id');
                if (editBtn) this.editProduct(id);
                else if (deleteBtn) this.deleteProduct(id);
            });

        document.getElementById('tab-create-product')
            ?.addEventListener('click', () => {
                window.location.href = '/admin/products/create';
            });
    }
};
document.addEventListener("DOMContentLoaded", () => {
    ProductModule.init();
});