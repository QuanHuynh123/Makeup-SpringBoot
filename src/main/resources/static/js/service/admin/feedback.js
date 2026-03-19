export const FeedbackModule = {
    feedbackList: [],
    isProcessing: false,

    showAlert(type, message) {
        Swal.fire({
            icon: type,
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    },

    formatDateTime(dateTime) {
        if (!dateTime) return '';
        return new Date(dateTime).toLocaleString('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },

    renderFeedbacks(list) {
        const tbody = document.getElementById('table-content-feedbacks');
        if (!tbody) return;

        tbody.innerHTML = '';
        if (!list || list.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" class="text-center">Không có phản hồi nào</td></tr>';
            return;
        }

        const rows = list.map((feedback) => `
            <tr>
                <td>${feedback.id}</td>
                <td>${feedback.rating}</td>
                <td>${feedback.comment}</td>
                <td>${feedback.userId}</td>
                <td>${feedback.typeMakeupId}</td>
                <td>${this.formatDateTime(feedback.createdAt)}</td>
                <td>${this.formatDateTime(feedback.updatedAt)}</td>
                <td>
                    <button class="btn btn-warning btn-sm btn-edit" data-id="${feedback.id}">
                        <i class="fa-solid fa-pen-to-square"></i>
                    </button>
                    <button class="btn btn-danger btn-sm btn-delete" data-id="${feedback.id}">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');

        tbody.innerHTML = rows;
    },

    loadFeedbacks() {
        return fetch('/api/admin/feedbacks')
            .then((response) => response.ok ? response.json() : Promise.reject(response))
            .then((data) => {
                if (data && Array.isArray(data.result)) {
                    this.feedbackList = data.result;
                    this.renderFeedbacks(this.feedbackList);
                } else {
                    this.feedbackList = [];
                    this.renderFeedbacks([]);
                    this.showAlert('error', 'Không thể tải danh sách phản hồi!');
                }
            })
            .catch((error) => {
                console.error('Lỗi:', error);
                this.feedbackList = [];
                this.renderFeedbacks([]);
                this.showAlert('error', 'Lỗi khi tải danh sách phản hồi!');
            });
    },

    searchFeedback() {
        const searchInput = document.getElementById('searchFeedback');
        const text = (searchInput?.value || '').toLowerCase();

        const filtered = this.feedbackList.filter((feedback) =>
            feedback.comment?.toLowerCase().includes(text) ||
            String(feedback.userId || '').toLowerCase().includes(text) ||
            String(feedback.typeMakeupId || '').toLowerCase().includes(text)
        );

        this.renderFeedbacks(filtered);
    },

    deleteFeedback(id) {
        if (this.isProcessing) return;
        this.isProcessing = true;

        const deleteButton = document.getElementById('confirmDeleteButton');
        if (deleteButton) {
            deleteButton.disabled = true;
            deleteButton.textContent = 'Đang xóa...';
        }

        fetch(`/api/feedbacks/${id}`, { method: 'DELETE' })
            .then((response) => {
                if (!response.ok) throw new Error('Delete failed');
                this.showAlert('success', 'Xóa phản hồi thành công!');
                bootstrap.Modal.getInstance(document.getElementById('confirmDeleteModal'))?.hide();
                return this.loadFeedbacks();
            })
            .catch((error) => {
                console.error('Lỗi:', error);
                this.showAlert('error', 'Lỗi khi xóa phản hồi!');
            })
            .finally(() => {
                this.isProcessing = false;
                if (deleteButton) {
                    deleteButton.disabled = false;
                    deleteButton.textContent = 'Xóa';
                }
            });
    },

    bindEvents() {
        const searchInput = document.getElementById('searchFeedback');
        if (searchInput) {
            searchInput.oninput = () => this.searchFeedback();
        }

        const tableBody = document.getElementById('table-content-feedbacks');
        if (tableBody) {
            tableBody.onclick = (event) => {
                const editBtn = event.target.closest('.btn-edit');
                const deleteBtn = event.target.closest('.btn-delete');

                if (editBtn) {
                    this.showAlert('info', 'Tính năng chỉnh sửa phản hồi chưa được bật.');
                    return;
                }

                if (deleteBtn) {
                    const id = deleteBtn.getAttribute('data-id');
                    const confirmDeleteButton = document.getElementById('confirmDeleteButton');
                    if (confirmDeleteButton) {
                        confirmDeleteButton.setAttribute('data-id', id);
                    }
                    new bootstrap.Modal(document.getElementById('confirmDeleteModal')).show();
                }
            };
        }

        const confirmDeleteButton = document.getElementById('confirmDeleteButton');
        if (confirmDeleteButton) {
            confirmDeleteButton.onclick = () => {
                const id = confirmDeleteButton.getAttribute('data-id');
                if (id) {
                    this.deleteFeedback(id);
                }
            };
        }
    },

    init() {
        this.bindEvents();
        this.loadFeedbacks();
    }
};

document.addEventListener('DOMContentLoaded', () => {
    FeedbackModule.init();
});
