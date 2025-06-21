// ================== SỰ KIỆN ==================
$(document).ready(function () {
    $(".btn-delete").on("click", handleDeleteProduct);
});

function handleDeleteProduct() {
    const productId = $(this).data("id");

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
            deleteProduct(productId);
        }
    });
}

function approveOrder(orderId) {
    Swal.fire({
        title: 'Bạn có chắc chắn muốn duyệt đơn hàng này?',
        text: 'Bạn sẽ không thể hoàn tác hành động này!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Duyệt',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            callApproveOrder(orderId);
        }
    });
}

// ================== API ===========================================================================

function callApproveOrder(orderId) {
    $.ajax({
        url: `/admin/order/approve/${orderId}`,
        type: 'POST',
        contentType: 'application/json',
        success: function (data) {
            Swal.fire('Thành công!', data.message || 'Đơn hàng đã được duyệt.', 'success');
        },
        error: function (xhr) {
            const errorMessage = xhr.responseJSON?.message || 'Có lỗi xảy ra khi duyệt đơn hàng.';
            Swal.fire('Thất bại!', errorMessage, 'error');
        }
    });
}

function deleteProduct(productId) {
    $.ajax({
        url: `/api/products/delete/${productId}`,
        type: "DELETE",
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: response,
                text: 'Đã xóa sản phẩm ra khỏi danh sách'
            });
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: "Lỗi",
                text: 'Đã xảy ra lỗi khi xóa thông tin'
            });
        }
    });
}
