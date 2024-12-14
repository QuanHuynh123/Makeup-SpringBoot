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
            $.ajax({
                url: `/admin/order/approve/${orderId}`,
                type: 'POST',
                contentType: 'application/json',
                success: function (data) {
                    Swal.fire('Thành công!', data.message || 'Đơn hàng đã được duyệt.', 'success');
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message
                        ? xhr.responseJSON.message
                        : 'Có lỗi xảy ra khi duyệt đơn hàng.';
                    Swal.fire('Thất bại!', errorMessage, 'error');
                }
            });
        }
    });
}
