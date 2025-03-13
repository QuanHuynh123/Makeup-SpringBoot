function toggleCart() {
    const miniCart = document.getElementById('mini-cart');
    miniCart.classList.toggle('hidden'); // Đóng hoặc mở mini cart bằng cách thêm/xóa class 'hidden'
}

// Chuyển hướng đến trang giỏ hàng
function goToCartPage() {
    window.location.href = '/cart'; // Đường dẫn đến trang giỏ hàng
}

$(document).ready(function () {
    $("#btn-checkout").on("click", function (event) {
        event.preventDefault(); // Chặn chuyển hướng ngay lập tức

        let dates = new Set();

        $(".p_use_date").each(function () {
            let dateText = $(this).text().replace("Use date : ", "").trim(); // Lấy ngày
            dates.add(dateText);
        });

        // Kiểm tra xem có nhiều hơn một ngày không
        if (dates.size > 1) {
            Swal.fire({
                icon: "error",
                title: "Chờ đã",
                text: "Các sản phẩm phải có chung một ngày đặt!",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
            return;
        }

        // Lấy ngày đầu tiên từ Set
        let selectedDate = Array.from(dates)[0]; // Lấy phần tử đầu tiên
        let useDate = new Date(selectedDate);
        let today = new Date();
        today.setHours(0, 0, 0, 0); // Đưa về 00:00 để so sánh

        // Kiểm tra ngày có hợp lệ không
        if (useDate < today) {
            Swal.fire({
                icon: "error",
                title: "Lỗi ngày đặt",
                text: "Không thể đặt sản phẩm với ngày trong quá khứ!",
            });
            return;
        }

        // Nếu hợp lệ, chuyển hướng đến trang checkout
        window.location.href = $(this).attr("href");
    });
});



// Thêm sản phẩm vào giỏ hàng (giả lập)
function addToCart(product) {
    const cartItems = document.getElementById('cart-items');
    const emptyMsg = document.getElementById('cart-empty-msg');
    const cartCount = document.querySelector('.cart-count');

    // Ẩn thông báo "Giỏ hàng trống"
    emptyMsg.style.display = 'none';

    // Cập nhật số lượng giỏ hàng
    cartCount.textContent = parseInt(cartCount.textContent) + 1;
}