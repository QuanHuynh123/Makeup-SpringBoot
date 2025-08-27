$(document).ready(function() {
    let feedbackList = [];
    let isProcessing = false; // Ngăn double-click

    // Hàm hiển thị thông báo
    function showAlert(type, message) {
        Swal.fire({
            icon: type,
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    }

    // Hàm định dạng ngày giờ
    function formatDateTime(dateTime) {
        if (!dateTime) return '';
        return new Date(dateTime).toLocaleString('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    // Hàm render danh sách phản hồi
    function renderFeedbacks(list) {
        const tbody = $('#table-content-feedbacks');
        tbody.empty();
        if (!list || list.length === 0) {
            tbody.append('<tr><td colspan="8" class="text-center">Không có phản hồi nào</td></tr>');
            return;
        }

        list.forEach((feedback, index) => {
            tbody.append(`
                <tr>
                    <td>${feedback.id}</td>
                    <td>${feedback.rating}</td>
                    <td>${feedback.comment}</td>
                    <td>${feedback.userId}</td>
                    <td>${feedback.typeMakeupId}</td>
                    <td>${formatDateTime(feedback.createdAt)}</td>
                    <td>${formatDateTime(feedback.updatedAt)}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-edit" data-id="${feedback.id}">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </button>
                        <button class="btn btn-danger btn-sm btn-delete" data-id="${feedback.id}">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `);
        });
    }

    // Hàm tải danh sách phản hồi
    function loadFeedbacks() {
        $.ajax({
            url: '/api/admin/feedbacks',
            method: 'GET',
            success: function(response) {
                if (response && response.result) {
                    feedbackList = response.result;
                    renderFeedbacks(feedbackList);
                } else {
                    showAlert('error', 'Không thể tải danh sách phản hồi!');
                    renderFeedbacks([]);
                }
            },
            error: function(xhr) {
                console.error('Lỗi:', xhr);
                showAlert('error', 'Lỗi khi tải danh sách phản hồi!');
                renderFeedbacks([]);
            }
        });
    }

    // Hàm tải danh sách khách hàng
    function loadUsers() {
        $.ajax({
            url: '/api/users',
            method: 'GET',
            success: function(response) {
                const select = $('#addUserId');
                select.find('option:not(:first)').remove();
                if (response && response.result) {
                    response.result.forEach(user => {
                        select.append(`<option value="${user.id}">${user.fullName}</option>`);
                    });
                }
            }
        });
    }

    // Hàm tải danh sách dịch vụ makeup
    function loadTypeMakeups() {
        $.ajax({
            url: '/api/type-makeups',
            method: 'GET',
            success: function(response) {
                const select = $('#addTypeMakeupId');
                select.find('option:not(:first)').remove();
                if (response && response.result) {
                    response.result.forEach(type => {
                        select.append(`<option value="${type.id}">${type.name}</option>`);
                    });
                }
            }
        });
    }

    // Hàm tìm kiếm phản hồi
    function searchFeedback() {
        const text = $('#searchFeedback').val().toLowerCase();
        const filtered = feedbackList.filter(f =>
            f.comment?.toLowerCase().includes(text) ||
            f.userId?.toLowerCase().includes(text) ||
            f.typeMakeupId?.toLowerCase().includes(text)
        );
        renderFeedbacks(filtered);
    }


    // Hàm xóa phản hồi
    function deleteFeedback(id) {
        if (isProcessing) return;
        isProcessing = true;
        const deleteButton = $('#confirmDeleteButton');
        deleteButton.prop('disabled', true).text('Đang xóa...');

        $.ajax({
            url: `/api/feedbacks/${id}`,
            method: 'DELETE',
            success: function() {
                showAlert('success', 'Xóa phản hồi thành công!');
                $('#confirmDeleteModal').modal('hide');
                loadFeedbacks();
            },
            error: function(xhr) {
                showAlert('error', 'Lỗi khi xóa phản hồi!');
            },
            complete: function() {
                isProcessing = false;
                deleteButton.prop('disabled', false).text('Xóa');
            }
        });
    }

    // Sự kiện
    $('#searchFeedback').on('input', searchFeedback);

    $('#table-content-feedbacks').on('click', '.btn-edit', function() {
        const id = $(this).data('id');
        const feedback = feedbackList.find(f => f.id === id);
        showAddEditModal(feedback);
    });

    $('#table-content-feedbacks').on('click', '.btn-delete', function() {
        const id = $(this).data('id');
        $('#confirmDeleteButton').data('id', id);
        $('#confirmDeleteModal').modal('show');
    });

    $('#confirmDeleteButton').on('click', function() {
        const id = $(this).data('id');
        deleteFeedback(id);
    });


    // Khởi tạo
    loadFeedbacks();
    loadUsers();
    loadTypeMakeups();
});