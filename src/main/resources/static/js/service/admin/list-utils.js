export function getNextSortDirection(currentSort, column) {
    if (currentSort && currentSort.startsWith(`${column},`)) {
        return currentSort.endsWith('asc') ? 'desc' : 'asc';
    }
    return 'asc';
}

export function renderPagination(containerSelector, currentPage, totalPages, onPageChange) {
    const pagination = document.querySelector(containerSelector);
    if (!pagination) return;

    pagination.innerHTML = '';

    const prev = `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                    <a class="page-link" href="#">Trước</a></li>`;
    pagination.innerHTML += prev;

    for (let i = 0; i < totalPages; i++) {
        pagination.innerHTML += `<li class="page-item ${i === currentPage ? 'active' : ''}">
            <a class="page-link" href="#">${i + 1}</a></li>`;
    }

    const next = `<li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link" href="#">Sau</a></li>`;
    pagination.innerHTML += next;

    pagination.onclick = (event) => {
        const link = event.target.closest('.page-link');
        if (!link) return;
        event.preventDefault();

        const links = Array.from(pagination.querySelectorAll('.page-link'));
        const idx = links.indexOf(link);
        if (idx === -1) return;

        let nextPage = currentPage;
        if (idx === 0 && currentPage > 0) {
            nextPage = currentPage - 1;
        } else if (idx === totalPages + 1 && currentPage < totalPages - 1) {
            nextPage = currentPage + 1;
        } else if (idx > 0 && idx <= totalPages) {
            nextPage = idx - 1;
        }

        if (nextPage !== currentPage) {
            onPageChange(nextPage);
        }
    };
}
