$(document).ready(function(){
    let fileList = [];

    console.log('edit-product.js loaded');

    $(document).on('click', '#modal-cancel-btn', function(e) {
        e.preventDefault();
        $('#editProductModal').modal('hide');
    });

    // Manage overlay and aria-hidden when modal opens
    $('#editProductModal').on('show.bs.modal', function() {
        console.log('Modal show event triggered'); // Debugging
        $('.admin-content-overlay').addClass('show-overlay');
        $(this).removeAttr('aria-hidden');
        setTimeout(() => {
            $('#nameProduct').focus();
        }, 100);
    });

    // Reset modal and overlay when closed
    $('#editProductModal').on('hidden.bs.modal', function () {
        console.log('Modal hidden event triggered'); // Debugging
        $('.admin-content-overlay').removeClass('show-overlay');
        $(this).attr('aria-hidden', 'true');

        // Clear fields
        $('#nameProduct, #price, #description, #size, #subCategory').val('');
        $('#status').val('false');
        $('#upload-input').val('');
        fileList = [];
        $('.upload-img').html('').css('padding', '0');
        $('.upload-info-value').text('0');
        $('#image-gallery').html('');
        $('.btn-create').removeData('product-id');
    });

    // File upload handling
    $('.upload-area').click(function () {
        $('#upload-input').trigger('click');
    });

    $('#upload-input').change(event => {
        if (event.target.files) {
            const files = Array.from(event.target.files);
            fileList = fileList.concat(files);

            $('.upload-img').html('');

            fileList.forEach((file, index) => {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const html = `
                        <div class="uploaded-img" data-index="${index}">
                            <img src="${e.target.result}">
                            <button type="button" class="remove-btn">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    `;
                    $('.upload-img').append(html);
                };
                reader.readAsDataURL(file);
            });

            $('.upload-info-value').text(fileList.length);
            $('.upload-img').css('padding', '20px');
        }
    });

    // Remove uploaded image
    $(document).on('click', '.remove-btn', function(event) {
        event.preventDefault();
        event.stopPropagation();
        const uploadedImgDiv = $(this).closest('.uploaded-img');
        const indexToRemove = parseInt(uploadedImgDiv.data('index'), 10);

        fileList = fileList.filter((_, index) => index !== indexToRemove);

        uploadedImgDiv.remove();
        $('.upload-info-value').text(fileList.length);

        $('.uploaded-img').each((newIndex, element) => {
            $(element).attr('data-index', newIndex);
        });
    });

    // Form submission for editing product
    $('.btn-create').on('click', function(e) {
        e.preventDefault();

        let id = $(this).data('product-id');
        let name = $('#nameProduct').val();
        let price = $('#price').val();
        let description = $('#description').val();
        let size = $('#size').val();
        let subCategoryId = $('#subCategory').val();
        let status = $('#status').val() === 'true';

        if (!name || !price || !description || !size || !subCategoryId) {
            Swal.fire({
                icon: 'warning',
                title: 'Thông tin chưa đầy đủ',
                text: 'Hãy nhập đầy đủ thông tin sản phẩm',
                timer: 2300
            });
            return;
        }

        const formData = new FormData();
        formData.append('nameProduct', name);
        formData.append('price', parseFloat(price));
        formData.append('description', description);
        formData.append('size', size);
        formData.append('subCategoryId', parseInt(subCategoryId));
        formData.append('status', status);

        if (fileList.length > 0) {
            fileList.forEach(file => {
                formData.append('files', file);
            });
        }

        $.ajax({
            url: `/api/admin/products/${id}/update`,
            type: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                Swal.fire({
                    icon: 'success',
                    title: response,
                    text: 'Thông tin sản phẩm đã được sửa',
                }).then(() => {
                    $('#editProductModal').modal('hide');
                    ProductModule.loadPagedProducts();
                });
            },
            error: function(response) {
                Swal.fire({
                    icon: 'error',
                    title: response.responseJSON?.message || 'Lỗi',
                    text: 'Không thể sửa sản phẩm',
                });
            }
        });
    });
});