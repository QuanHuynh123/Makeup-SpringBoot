function toggleCart() {
    const miniCart = document.getElementById('mini-cart');
    miniCart.classList.toggle('hidden'); // Đóng hoặc mở mini cart bằng cách thêm/xóa class 'hidden'
}

// Chuyển hướng đến trang giỏ hàng
function goToCartPage() {
    window.location.href = '/cart'; // Đường dẫn đến trang giỏ hàng
}

// Chuyển hướng đến trang thanh toán
function checkout() {
    window.location.href = '/checkout'; // Đường dẫn đến trang thanh toán
}

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