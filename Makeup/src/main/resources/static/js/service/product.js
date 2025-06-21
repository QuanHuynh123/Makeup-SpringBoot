$(document).ready(function () {
    // Lọc sản phẩm theo subCategory
    $('#subCategory').on('change', function () {
        const subCategoryId = $(this).val();
        if (!subCategoryId) return;

        $.ajax({
            url: '/api/products/by-sub-category/' + subCategoryId,
            method: 'GET',
            success: function (response) {
                if (response && response.length > 0) {
                    $('#container_product .product-list').html(generateProductHtml(response));
                    $('.pagination').hide(); // Ẩn phân trang khi lọc
                } else {
                    $('#container_product .product-list').html('<p>Không có sản phẩm nào trong mục này.</p>');
                    $('.pagination').hide();
                }
            },
            error: function () {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Không thể tải sản phẩm từ danh mục con đã chọn.'
                });
            }
        });
    });

    // Reset form sau khi tạo sản phẩm thành công
    function resetProductForm() {
        $('#name').val('');
        $('#price').val('');
        $('#description').val('');
        $('input[name="size"]').prop('checked', false);
        $('#subCategory').val('');
        fileList = [];
        $('.upload-img').empty();
        $('.upload-info-value').text('0');
        $('.upload-img').css('padding', '0');
    }

    // Hover hình ảnh hiển thị nút xóa (hiệu ứng nhỏ)
    $(document).on('mouseenter', '.uploaded-img', function () {
        $(this).find('.remove-btn').fadeIn(150);
    });

    $(document).on('mouseleave', '.uploaded-img', function () {
        $(this).find('.remove-btn').fadeOut(150);
    });

    // Preview hình ảnh đầu tiên lớn hơn (nếu cần UI đẹp hơn)
    $(document).on('click', '.uploaded-img img', function () {
        const src = $(this).attr('src');
        Swal.fire({
            imageUrl: src,
            imageAlt: 'Preview',
            showCloseButton: true,
            showConfirmButton: false,
            width: 'auto',
            padding: '1em',
        });
    });
});
