function performSearch(keyword) {
    var searchContext = document.getElementById('searchContext')?.value || 'default';

    // Cho phép gọi ngay cả khi keyword rỗng để hiển thị full data
    switch (searchContext) {
        case 'cosplay':
            window.location.href = '/cosplay/search?searchKey=' + encodeURIComponent(keyword || '');
            break;
        case 'cart':
            // Gọi hàm tìm kiếm trực tiếp với từ khóa, chỉ nếu trang hỗ trợ
            if (typeof searchCartItems === 'function' && document.querySelector('.cart-table')) {
                searchCartItems(keyword || ''); // Truyền keyword, nếu rỗng thì hiển thị all
            } else {
                alert('Trang hiện tại không hỗ trợ tìm kiếm giỏ hàng!');
            }
            break;
        case 'makeup':
            window.location.href = '/search?query=' + encodeURIComponent(keyword || '');
            break;
        default:
            alert('Không hỗ trợ tìm kiếm trên trang này hoặc chưa cấu hình ngữ cảnh!');
    }
}

// Gắn sự kiện input với debounce cho tìm kiếm linh hoạt
// Hàm debounce để tối ưu hóa tìm kiếm
function debounce(func, wait) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

// Gắn sự kiện input với debounce cho tìm kiếm linh hoạt (chỉ cho 'cart' và 'makeup')
const debouncedPerformSearch = debounce(function () {
    const keyword = document.getElementById('searchInput')?.value.trim();
    const searchContext = document.getElementById('searchContext')?.value || 'default';
    if (searchContext === 'cosplay') {
        return; // Không thực hiện tìm kiếm cho 'cosplay' khi nhập
    }
    performSearch(keyword);
}, 300);
document.getElementById('searchInput')?.addEventListener('input', debouncedPerformSearch);

// Gắn sự kiện Enter và nút tìm kiếm để kích hoạt ngay
document.getElementById('searchInput')?.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        const keyword = this.value.trim();
        performSearch(keyword);
    }
});
document.querySelector('#right_bottom_header .search-bar button')?.addEventListener('click', function () {
    const keyword = document.getElementById('searchInput')?.value.trim();
    performSearch(keyword);
});