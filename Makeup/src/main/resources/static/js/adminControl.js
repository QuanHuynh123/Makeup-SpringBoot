const sidebar = document.querySelector('.Sidebar');
const submenuTitles = document.querySelectorAll('.Submenu-title');

function toggleSidebar() {
    sidebar.classList.toggle('showSidebar');
}

document.addEventListener('DOMContentLoaded', () => {
    if (!sidebar) {
        console.warn("Sidebar element not found");
    }
});


submenuTitles.forEach(title => {
    title.addEventListener('click', () => {
        // Tìm phần tử .Submenu-item bên trong .Submenu
        const submenuItem = title.closest('.Submenu').querySelector('.Submenu-item');
        const submenuContent = submenuItem.querySelector('.Submenu-item-content');
        const arrowIcon = title.querySelector('.arrow-icon');

        // Toggle the "show" class for the submenu content
        submenuContent.classList.toggle('show');

        // Toggle arrow direction
        if (submenuContent.classList.contains('show')) {
            arrowIcon.innerHTML = '<path d="M256 294.1L383 167c9.4-9.4 24.6-9.4 33.9 0s9.3 24.6 0 34L273 345c-9.1 9.1-23.7 9.3-33.1.7L95 201.1c-4.7-4.7-7-10.9-7-17s2.3-12.3 7-17c9.4-9.4 24.6-9.4 33.9 0l127.1 127z"></path>'; // Icon khi mở
        } else {
            arrowIcon.innerHTML = '<path d="M294.1 256L167 129c-9.4-9.4-9.4-24.6 0-33.9s24.6-9.3 34 0L345 239c9.1 9.1 9.3 23.7.7 33.1L201.1 417c-4.7 4.7-10.9 7-17 7s-12.3-2.3-17-7c-9.4-9.4-9.4-24.6 0-33.9l127-127.1z"></path>'; // Icon khi đóng
        }
    });
});

