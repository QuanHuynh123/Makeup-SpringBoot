/***********************************************
 * GIỎ HÀNG - XỬ LÝ SỰ KIỆN VÀ API
 ***********************************************/

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

// Hàm làm mới mini-cart
function refreshMiniCart() {
    $.ajax({
        url: '/api/cart',
        method: 'GET',
        success: function (response) {
            const miniCartItems = document.getElementById('cart-mini-items');
            const cartCount = document.querySelector('.cart-count');
            const emptyMsg = document.getElementById('cart-empty-msg');

            if (response && response.result && response.result.length > 0) {
                let html = '';
                response.result.forEach(item => {
                    html += `
                        <li>
                            <div class="content_item_cart">
                                <input class="cart-id" style="display:none;" value="${item.id}">
                                <div class="content_item_cart_left">
                                    <h3 class="text_name_product">${item.productName}</h3>
                                    <p class="p_use_date small-text">RentalDate: ${new Date(item.rentalDate).toISOString().split('T')[0]}</p>
                                    <p class="p_quantity small-text">Quantity: ${item.quantity}</p>
                                    <p class="p_price small-text">Price: ${item.price}</p>
                                    <button class="btn-delete-cart-item"><i class="fa-solid fa-x"></i></button>
                                </div>
                                <div class="content_item_cart_right">
                                    <img class="img_item_cart" src="/images/product/${item.firstImage}" alt="Product Image">
                                </div>
                            </div>
                        </li>
                    `;
                });
                miniCartItems.innerHTML = html;
                if (cartCount) cartCount.textContent = response.result.length;
                if (emptyMsg) emptyMsg.style.display = 'none';

                // Gắn lại sự kiện cho các nút mới trong mini-cart
                attachDeleteEvent();
                refreshCartPage(response);
            } else {
                miniCartItems.innerHTML = '';
                if (cartCount) cartCount.textContent = '0';
                if (emptyMsg) emptyMsg.style.display = 'block';
            }
        },
        error: function (xhr) {
        }
    });
}

// Hàm làm mới cart page với tìm kiếm
function refreshCartPage(response) {
    const cartTable = document.querySelector('.cart-table');
    if (!cartTable) return;

    // Lưu dữ liệu gốc
    window.cartData = response.result || [];

    let html = `
        <tr style="border-bottom: 1px solid #ccc; border-top: 1px solid #ccc;">
            <th style="width: 40%;">Products</th>
            <th class="align-th" style="width: 13%;">Price</th>
            <th class="align-th" style="width: 15%;">Quantity</th>
            <th class="align-th" style="width: 13%;">Total</th>
            <th class="align-th" style="width: 4%;"></th>
            <th class="align-th" style="width: 4%;"></th>
        </tr>
    `;

    // Hiển thị toàn bộ nếu không có từ khóa
    if (response && response.result && response.result.length > 0) {
        response.result.forEach(item => {
            html += `
                <tr style="vertical-align: middle;" cart-id="${item.id}">
                    <td class="align-th cart-item-infor">
                        <div style="display: flex;">
                            <img src="/images/product/${item.firstImage}" alt="Product Image" style="width: 100px; height: 100px; object-fit: cover; object-position: center; display: block; border-radius: 2px;">
                            <input class="product-id" style="display:none;" value="${item.productId}">
                            <div class="infor">
                                <p style="margin: 0; font-weight: 600;">${item.productName}</p>
                                <p style="margin: 2px 0; font-size: 14px;">Size : S</p>
                                <input type="date" class="use-date" value="${new Date(item.rentalDate).toISOString().split('T')[0]}">
                            </div>
                        </div>
                    </td>
                    <td class="align-th unit-price">${item.price}</td>
                    <td class="align-th">
                        <div style="display: flex; justify-content: center;">
                            <button class="button-update-quantity quantity-decrease">-</button>
                            <input class="quantity-input" value="${item.quantity}" style="width: 40px; text-align: center; border: 1px solid black;">
                            <button class="button-update-quantity quantity-increase">+</button>
                        </div>
                    </td>
                    <td class="align-th">${(item.price * item.quantity).toFixed(2)}</td>
                    <td class="align-th">
                        <button class="btn-update-cart-item"><i class="fa fa-rotate-right"></i></button>
                    </td>
                    <td class="align-th">
                        <button class="btn-delete-cart-item"><i class="fa fa-times"></i></button>
                    </td>
                </tr>
            `;
        });
    } else {
        html += '<tr><td colspan="6"><p>Giỏ hàng của bạn đang trống.</p></td></tr>';
    }
    cartTable.innerHTML = html;

    // Cập nhật tổng giá
    const grandTotal = document.querySelector('.grand-total span:last-child');
    if (response && response.result && grandTotal) {
        const total = response.result.reduce((sum, item) => sum + (item.price * item.quantity), 0).toFixed(2);
        grandTotal.textContent = '$' + total;
    }

    // Gắn lại sự kiện cho các nút mới trong cart page
    attachDeleteEvent();
    attachUpdateEvent();
    attachQuantityEvents();
}

// Hàm tìm kiếm trong giỏ hàng với tham số từ khóa
function searchCartItems(keyword) {
    const cartTable = document.querySelector('.cart-table');
    if (!cartTable || !window.cartData) return;

    // Sử dụng DocumentFragment để tối ưu hóa render
    const fragment = document.createDocumentFragment();
    const newTable = document.createElement('table');
    newTable.className = 'cart-table';

    let html = `
        <tr style="border-bottom: 1px solid #ccc; border-top: 1px solid #ccc;">
            <th style="width: 40%;">Products</th>
            <th class="align-th" style="width: 13%;">Price</th>
            <th class="align-th" style="width: 15%;">Quantity</th>
            <th class="align-th" style="width: 13%;">Total</th>
            <th class="align-th" style="width: 4%;"></th>
            <th class="align-th" style="width: 4%;"></th>
        </tr>
    `;

    const filteredData = !keyword || keyword === '' ? window.cartData : window.cartData.filter(item =>
        item.productName.toLowerCase().includes(keyword.toLowerCase())
    );

    if (filteredData.length > 0) {
        filteredData.forEach(item => {
            html += `
                <tr style="vertical-align: middle;" cart-id="${item.id}">
                    <td class="align-th cart-item-infor">
                        <div style="display: flex;">
                            <img src="/images/product/${item.firstImage}" alt="Product Image" style="width: 100px; height: 100px; object-fit: cover; object-position: center; display: block; border-radius: 2px;">
                            <input class="product-id" style="display:none;" value="${item.productId}">
                            <div class="infor">
                                <p style="margin: 0; font-weight: 600;">${item.productName}</p>
                                <p style="margin: 2px 0; font-size: 14px;">Size : S</p>
                                <input type="date" class="use-date" value="${new Date(item.rentalDate).toISOString().split('T')[0]}">
                            </div>
                        </div>
                    </td>
                    <td class="align-th unit-price">${item.price}</td>
                    <td class="align-th">
                        <div style="display: flex; justify-content: center;">
                            <button class="button-update-quantity quantity-decrease">-</button>
                            <input class="quantity-input" value="${item.quantity}" style="width: 40px; text-align: center; border: 1px solid black;">
                            <button class="button-update-quantity quantity-increase">+</button>
                        </div>
                    </td>
                    <td class="align-th">${(item.price * item.quantity).toFixed(2)}</td>
                    <td class="align-th">
                        <button class="btn-update-cart-item"><i class="fa fa-rotate-right"></i></button>
                    </td>
                    <td class="align-th">
                        <button class="btn-delete-cart-item"><i class="fa fa-times"></i></button>
                    </td>
                </tr>
            `;
        });
    } else {
        html += '<tr><td colspan="6"><p>Không tìm thấy sản phẩm.</p></td></tr>';
    }

    newTable.innerHTML = html;
    fragment.appendChild(newTable);
    cartTable.parentNode.replaceChild(fragment, cartTable);

    // Cập nhật tổng giá dựa trên dữ liệu lọc
    const grandTotal = document.querySelector('.grand-total span:last-child');
    if (grandTotal) {
        const total = filteredData.reduce((sum, item) => sum + (item.price * item.quantity), 0).toFixed(2);
        grandTotal.textContent = '$' + total;
    }

    // Gắn lại sự kiện cho các nút mới trong cart page
    attachDeleteEvent();
    attachUpdateEvent();
    attachQuantityEvents();
}

// Hàm gắn sự kiện xóa
function attachDeleteEvent() {
    document.querySelectorAll(".btn-delete-cart-item").forEach(button => {
        button.removeEventListener("click", deleteCartItem);
        button.addEventListener("click", deleteCartItem);
    });
}

// Hàm gắn sự kiện cập nhật
function attachUpdateEvent() {
    document.querySelectorAll(".btn-update-cart-item").forEach(button => {
        button.removeEventListener("click", updateCartItem);
        button.addEventListener("click", updateCartItem);
    });
}

// Hàm gắn sự kiện tăng/giảm số lượng
function attachQuantityEvents() {
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
}

// Hàm xử lý xóa sản phẩm
function deleteCartItem(event) {
    const button = event.currentTarget;
    let cartItem, itemId;

    // Kiểm tra nếu đang ở mini-cart (có input.cart-id)
    const miniCartItem = button.closest("li");
    if (miniCartItem && miniCartItem.querySelector(".cart-id")) {
        cartItem = miniCartItem;
        itemId = cartItem.querySelector(".cart-id").value;
    } else {
        // Kiểm tra nếu đang ở trang giỏ hàng riêng (có tr[cart-id])
        cartItem = button.closest("tr");
        if (cartItem) {
            itemId = cartItem.getAttribute("cart-id");
        }
    }

    if (!itemId) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Không tìm thấy ID mục giỏ hàng!",
        });
        return;
    }

    Swal.fire({
        title: "Bạn có chắc chắn muốn xóa không?",
        text: "Hành động này không thể hoàn tác!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Xóa",
        cancelButtonText: "Hủy"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `/api/cart/delete?cartItemId=${itemId}`,
                method: 'DELETE',
                success: function (response) {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Xóa thành công!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        // Xóa mục khỏi giao diện
                        cartItem.remove();

                        // Cập nhật số lượng giỏ hàng
                        const cartCount = document.querySelector('.cart-count');
                        if (cartCount) {
                            const count = parseInt(cartCount.textContent) || 0;
                            cartCount.textContent = count > 0 ? count - 1 : 0;
                        }

                        // Kiểm tra giỏ hàng rỗng trong mini-cart
                        const miniCartItems = document.querySelectorAll("#cart-mini-items li").length;
                        if (miniCartItems === 0) {
                            const emptyMsg = document.getElementById('cart-empty-msg');
                            if (emptyMsg) emptyMsg.style.display = 'block';
                        }

                        // Làm mới cả mini-cart và cart page
                        refreshMiniCart();
                        if (document.querySelector('.cart-table')) {
                            refreshCartPage(response); // Sử dụng response từ API nếu có
                        }
                    });
                },
                error: function (xhr) {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: xhr.responseJSON?.message || "Xóa thất bại!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            });
        }
    });
}

// Hàm xử lý cập nhật sản phẩm
function updateCartItem(event) {
    const button = event.currentTarget;
    const cartItem = button.closest("tr");
    if (!cartItem) return;

    const itemId = cartItem.getAttribute("cart-id");
    const productId = cartItem.querySelector(".product-id")?.value;
    const quantity = cartItem.querySelector(".quantity-input")?.value;
    const useDate = cartItem.querySelector(".use-date")?.value;
    let rentalDate = useDate + "T00:00:00";

    const currentDate = new Date().toISOString();
    if (rentalDate < currentDate) {
        Swal.fire("Ngày sử dụng không thể là ngày trong quá khứ. Vui lòng chọn lại.");
        return;
    }

    $.ajax({
        url: '/api/cart/update',
        method: 'POST',
        contentType: 'application/json',
        headers: {
            'Accept': 'application/json'
        },
        data: JSON.stringify({
            cartItemId: itemId,
            productId: productId,
            quantity: quantity,
            rentalDate: rentalDate
        }),
        success: function (response) {
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Cập nhật thành công!",
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                // Làm mới mini-cart
                refreshMiniCart();

                // Cập nhật tổng giá trong trang giỏ hàng (nếu có)
                if (document.querySelector('.cart-table')) {
                    refreshCartPage(response);
                }
            });
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
}

// Hàm debounce để tối ưu hóa tìm kiếm
function debounce(func, wait) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

$(document).ready(function () {
    $('#add_cart_btn').off('click').on('click', function (e) {
        e.preventDefault();

        const productId = document.getElementById('productId').getAttribute('product-id');
        const gender = $('#gender').val();
        const size = $('#size').val();
        const useDate = $('#use-date').val();
        let rentalDate = useDate + "T00:00:00";
        const quantity = $('#quantity').val();

        const currentDate = new Date().toISOString();
        if (rentalDate < currentDate) {
            Swal.fire("Ngày sử dụng không thể là ngày trong quá khứ. Vui lòng chọn lại.");
            return;
        }

        if (!productId || !gender || !size || !rentalDate || quantity <= 0) {
            Swal.fire("Vui lòng chọn đầy đủ thông tin trước khi thêm vào giỏ hàng.!");
            return;
        }

        $.ajax({
            url: '/api/cart/add',
            method: 'POST',
            contentType: 'application/json',
            headers: {
                'Accept': 'application/json'
            },
            data: JSON.stringify({
                productId: productId,
                rentalDate: rentalDate,
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
                    // Làm mới mini-cart
                    refreshMiniCart();
                    if (document.querySelector('.cart-table')) {
                        refreshCartPage(response);
                    }
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

// Hàm xóa toàn bộ giỏ hàng
function clearCart() {
    Swal.fire({
        title: "Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng không?",
        text: "Hành động này sẽ xóa tất cả các sản phẩm trong giỏ hàng và không thể hoàn tác!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Xóa tất cả",
        cancelButtonText: "Hủy"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/api/cart/delete-all',
                method: 'DELETE',
                success: function (response) {
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Xóa toàn bộ giỏ hàng thành công!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        // Làm mới cả mini-cart và cart page
                        refreshMiniCart();
                        if (document.querySelector('.cart-table')) {
                            refreshCartPage(response);
                        }
                    });
                },
                error: function (xhr) {
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: xhr.responseJSON?.message || "Xóa toàn bộ giỏ hàng thất bại!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            });
        }
    });
}

// Chuyển hướng đến trang thanh toán với kiểm tra rentalDate
function checkout() {
    // Tải dữ liệu giỏ hàng để kiểm tra
    $.ajax({
        url: '/api/cart',
        method: 'GET',
        success: function(response) {
            const cartData = response.result || [];
            if (cartData.length === 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Giỏ hàng trống!'
                });
                return;
            }

            // Kiểm tra tính đồng nhất của rentalDate
            const rentalDates = new Set(cartData.map(item => item.rentalDate));
            if (rentalDates.size > 1) {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Tất cả sản phẩm phải có cùng ngày thuê. Vui lòng điều chỉnh giỏ hàng!'
                });
                return;
            }

            // Nếu hợp lệ, chuyển đến trang checkout
            window.location.href = '/user/my-cart/check-out';
        },
        error: function(xhr) {
            console.log('Error:', xhr.responseJSON);
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Không thể tải giỏ hàng để kiểm tra. Vui lòng thử lại!'
            });
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    refreshMiniCart();
    attachDeleteEvent();
    attachUpdateEvent();
    attachQuantityEvents();

    if (document.getElementById('searchInput') && document.getElementById('searchContext')?.value === 'cart') {
        const initialKeyword = document.getElementById('searchInput').value.trim();
        if (initialKeyword) {
            searchCartItems(initialKeyword); // Gọi hàm tìm kiếm khi tải trang nếu có từ khóa
        } else {
            searchCartItems(''); // Hiển thị full data nếu không có từ khóa
        }
    }
});