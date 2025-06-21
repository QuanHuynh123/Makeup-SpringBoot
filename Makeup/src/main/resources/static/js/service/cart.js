/***********************************************
 * GIỎ HÀNG - XỬ LÝ SỰ KIỆN VÀ API
 ***********************************************/

// Hiện/ẩn mini cart
function toggleCart() {
    const miniCart = document.getElementById('mini-cart');
    miniCart.classList.toggle('hidden');
}

// Chuyển hướng đến trang giỏ hàng
function goToCartPage() {
    window.location.href = '/cart';
}

// Chuyển hướng đến trang thanh toán
function checkout() {
    window.location.href = '/checkout';
}

// Giả lập thêm sản phẩm vào giỏ hàng
function addToCart(product) {
    const cartItems = document.getElementById('cart-items');
    const emptyMsg = document.getElementById('cart-empty-msg');
    const cartCount = document.querySelector('.cart-count');

    // Ẩn thông báo "Giỏ hàng trống"
    if (emptyMsg) emptyMsg.style.display = 'none';

    // Tăng số lượng giỏ hàng
    if (cartCount) {
        const count = parseInt(cartCount.textContent) || 0;
        cartCount.textContent = count + 1;
    }
}

// Chờ tài liệu tải xong
document.addEventListener("DOMContentLoaded", function () {
    /***********************************************
     * Tăng/giảm số lượng sản phẩm
     ***********************************************/
    document.querySelectorAll(".quantity-increase").forEach(button => {
        button.addEventListener("click", function () {
            const input = this.parentElement.querySelector(".quantity-input");
            let currentValue = parseInt(input.value) || 0;
            input.value = currentValue + 1;
        });
    });

    document.querySelectorAll(".quantity-decrease").forEach(button => {
        button.addEventListener("click", function () {
            const input = this.parentElement.querySelector(".quantity-input");
            let currentValue = parseInt(input.value) || 0;
            if (currentValue > 1) {
                input.value = currentValue - 1;
            }
        });
    });

    /***********************************************
     * XÓA sản phẩm khỏi giỏ hàng
     ***********************************************/
    document.querySelectorAll(".btn-delete-cart-item").forEach(button => {
        button.addEventListener("click", function () {
            const cartItem = this.closest(".cart-item-info");
            const itemId = cartItem?.getAttribute("cart-id");
            if (!itemId) return;

            $.ajax({
                url: `/api/cart/delete?cartItemId=${itemId}`,
                method: 'DELETE',
                success: function () {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Xóa thành công!",
                        showConfirmButton: false,
                        timer: 3000
                    }).then(() => location.reload());
                },
                error: function () {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Xóa thất bại!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            });
        });
    });

    /***********************************************
     * CẬP NHẬT sản phẩm trong giỏ hàng
     ***********************************************/
    document.querySelectorAll(".btn-update-cart-item").forEach(button => {
        button.addEventListener("click", function () {
            const cartItem = this.closest(".cart-item-info");
            if (!cartItem) return;

            const itemId = cartItem.getAttribute("cart-id");
            const productId = cartItem.querySelector(".product-id")?.value;
            const quantity = cartItem.querySelector(".quantity-input")?.value;
            const useDate = cartItem.querySelector(".use-date")?.value;

            const currentDate = new Date().toISOString().split('T')[0];
            if (useDate < currentDate) {
                Swal.fire("Ngày sử dụng không thể là ngày trong quá khứ. Vui lòng chọn lại.");
                return;
            }

            $.ajax({
                url: '/api/cart/update',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    cartItemId: itemId,
                    productId: productId,
                    quantity: quantity,
                    useDate: useDate
                }),
                success: function () {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Cập nhật thành công!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => location.reload());
                },
                error: function () {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Cập nhật thất bại!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            });
        });
    });
});

$(document).ready(function () {
    $('#add_cart_btn').on('click', function (e) {
        e.preventDefault();

        const productId = document.getElementById('productId').getAttribute('product-id');
        const gender = $('#gender').val();
        const size = $('#size').val();
        const useDate = $('#use-date').val();
        const quantity = $('#quantity').val();

        const currentDate = new Date().toISOString().split('T')[0]; // Lấy ngày hiện tại theo định dạng yyyy-mm-dd
        if (useDate < currentDate) {
            Swal.fire("Ngày sử dụng không thể là ngày trong quá khứ. Vui lòng chọn lại.");
            return;
        }

        if (!productId|| !gender || !size || !useDate || quantity <= 0) {
            Swal.fire("Vui lòng chọn đầy đủ thông tin trước khi thêm vào giỏ hàng.!");
            return;
        }

        $.ajax({
            url: '/api/cart/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                productId: productId,
                useDate: useDate,
                quantity: quantity
            }),
            success: function (response) {
                Swal.fire({
                  position: "top-end",
                  icon: "success",
                  title: "Thêm sản phẩm vào giỏ thành công!",
                  showConfirmButton: false,
                  timer: 1500
                }).then(() => {
                location.reload();  // Reload trang sau khi thông báo kết thúc
                });
            },
            error: function (error) {
                Swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Thêm hàng vào giỏ thất bại!",
                  footer: '<a href="#">Why do I have this issue?</a>'
                });
            }
        });
    });
});
