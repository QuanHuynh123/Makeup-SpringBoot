import { ProductModule } from '/js/service/admin/product.js';

export const CreateProductModule = {
    fileList: [],
    modalInstance: null,
    isBoundEvents: false,

    init() {
        if (this.isBoundEvents) {
            return;
        }
        this.isBoundEvents = true;

        const openBtn = document.getElementById('tab-create-product');
        if (openBtn) {
            openBtn.onclick = () => this.openModal();
        }

        const uploadArea = document.getElementById('create-upload-area');
        const uploadInput = document.getElementById('create-upload-input');
        if (uploadArea && uploadInput) {
            uploadArea.onclick = () => uploadInput.click();
            uploadInput.onchange = (event) => this.handleInputFiles(event.target.files);
        }

        const previewContainer = document.getElementById('create-upload-preview');
        if (previewContainer) {
            previewContainer.onclick = (event) => {
                const removeBtn = event.target.closest('.remove-btn');
                if (!removeBtn) return;

                event.preventDefault();
                const uploadedItem = removeBtn.closest('.uploaded-img');
                const indexToRemove = Number(uploadedItem?.dataset?.index);
                if (Number.isNaN(indexToRemove)) return;

                this.fileList = this.fileList.filter((_, index) => index !== indexToRemove);
                this.renderPreview();
            };
        }

        const saveBtn = document.getElementById('btn-save-create-product');
        if (saveBtn) {
            saveBtn.onclick = (event) => {
                event.preventDefault();
                this.submitCreate();
            };
        }

        const modalElement = document.getElementById('createProductModal');
        if (modalElement) {
            this.modalInstance = bootstrap.Modal.getOrCreateInstance(modalElement);
            modalElement.addEventListener('hidden.bs.modal', () => this.resetForm());
        }
    },

    openModal() {
        if (!this.modalInstance) {
            const modalElement = document.getElementById('createProductModal');
            if (modalElement) {
                this.modalInstance = bootstrap.Modal.getOrCreateInstance(modalElement);
            }
        }
        this.modalInstance?.show();
        setTimeout(() => {
            document.getElementById('create-name')?.focus();
        }, 100);
    },

    handleInputFiles(files) {
        if (!files || files.length === 0) return;

        this.fileList = this.fileList.concat(Array.from(files));
        this.renderPreview();
    },

    renderPreview() {
        const preview = document.getElementById('create-upload-preview');
        const count = document.getElementById('create-upload-count');
        if (!preview || !count) return;

        preview.innerHTML = '';
        this.fileList.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = (event) => {
                preview.innerHTML += `
                    <div class="uploaded-img" data-index="${index}">
                        <img src="${event.target?.result}">
                        <button type="button" class="remove-btn">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                `;
            };
            reader.readAsDataURL(file);
        });

        count.textContent = this.fileList.length.toString();
        preview.style.padding = this.fileList.length > 0 ? '20px' : '0';
    },

    getCheckedSizes() {
        return Array.from(document.querySelectorAll('input[name="createSize"]:checked'))
            .map(input => input.value)
            .join(',');
    },

    submitCreate() {
        const name = document.getElementById('create-name')?.value?.trim();
        const subCategoryId = document.getElementById('create-subCategory')?.value;
        const price = Number(document.getElementById('create-price')?.value);
        const quantity = Number(document.getElementById('create-quantity')?.value);
        const description = document.getElementById('create-description')?.value?.trim();
        const size = this.getCheckedSizes();

        if (!name || !subCategoryId || !price || !quantity || !description || !size) {
            Swal.fire({
                icon: 'warning',
                title: 'Thông tin chưa đầy đủ',
                text: 'Vui lòng nhập đầy đủ thông tin sản phẩm.'
            });
            return;
        }

        if (this.fileList.length === 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Thiếu hình ảnh',
                text: 'Vui lòng tải lên ít nhất một hình ảnh.'
            });
            return;
        }

        const formData = new FormData();
        formData.append('name', name);
        formData.append('description', description);
        formData.append('size', size);
        formData.append('price', price.toString());
        formData.append('subCategoryId', subCategoryId);
        formData.append('quantity', quantity.toString());
        formData.append('currentQuantity', quantity.toString());
        formData.append('rentalCount', '0');
        formData.append('status', 'true');

        this.fileList.forEach(file => formData.append('files', file));

        $.ajax({
            url: '/api/admin/products',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: () => {
                Swal.fire({
                    icon: 'success',
                    title: 'Tạo sản phẩm thành công'
                }).then(() => {
                    this.modalInstance?.hide();
                    ProductModule.currentPage = 0;
                    ProductModule.loadPagedProducts();
                });
            },
            error: (response) => {
                Swal.fire({
                    icon: 'error',
                    title: response.responseJSON?.message || 'Tạo sản phẩm thất bại',
                    text: 'Không thể tạo sản phẩm mới.'
                });
            }
        });
    },

    resetForm() {
        document.getElementById('create-name') && (document.getElementById('create-name').value = '');
        document.getElementById('create-subCategory') && (document.getElementById('create-subCategory').value = '');
        document.getElementById('create-price') && (document.getElementById('create-price').value = '');
        document.getElementById('create-quantity') && (document.getElementById('create-quantity').value = '');
        document.getElementById('create-description') && (document.getElementById('create-description').value = '');
        document.querySelectorAll('input[name="createSize"]').forEach(input => {
            input.checked = false;
        });

        const uploadInput = document.getElementById('create-upload-input');
        if (uploadInput) {
            uploadInput.value = '';
        }

        this.fileList = [];
        this.renderPreview();
    }
};