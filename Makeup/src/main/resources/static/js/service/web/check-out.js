$(document).ready(function() {
    let cartData = [];
    let isShipping = false; // Biến theo dõi trạng thái shipping

    // Hàm cập nhật một dòng trong bảng
    function updateRow(itemId, newQuantity) {
        const item = cartData.find(i => i.id === itemId);
        if (item) {
            item.quantity = newQuantity;
            const $row = $(`tr[data-item-id="${itemId}"]`);
            const subtotal = item.price * newQuantity;
            $row.find('.quantity-input').val(newQuantity);
            $row.find('.subtotal').text(subtotal.toFixed(2));
            updateSummary();
        }
    }

    // Hàm cập nhật summary
    function updateSummary() {
        const totalQuantity = cartData.reduce((sum, item) => sum + item.quantity, 0);
        const basePrice = cartData.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        const shippingCost = isShipping ? 30.00 : 0.00;
        const totalPrice = basePrice + shippingCost;

        $('#cartQuantity').text(totalQuantity);
        $('#shippingCost').text(`$${shippingCost.toFixed(2)}`);
        $('#orderTotal').text(`$${totalPrice.toFixed(2)}`);
    }

    // Hàm render toàn bộ bảng sản phẩm
    function renderProductTable() {
        const tbody = $('#productTableBody');
        const fragment = document.createDocumentFragment();
        tbody.addClass('hidden'); // Ẩn trước khi render

        if (!cartData || cartData.length === 0) {
            const tr = document.createElement('tr');
            tr.innerHTML = '<td colspan="3" style="text-align: center;">Bạn chưa chọn sản phẩm nào</td>';
            fragment.appendChild(tr);
            updateSummary(0, 0);
        } else {
            cartData.forEach(item => {
                const subtotal = item.price * item.quantity;
                const tr = document.createElement('tr');
                tr.setAttribute('data-item-id', item.id);
                tr.innerHTML = `
                    <td><img src="/images/product/${item.firstImage}" alt="Product Image" style="width: 50px;"><span>${item.productName}</span></td>
                    <td><input type="number" class="quantity-input" value="${item.quantity}" min="1" style="width: 60px;" data-item-id="${item.id}"></td>
                    <td><span class="subtotal">${subtotal.toFixed(2)}</span></td>
                `;
                fragment.appendChild(tr);
            });
            updateSummary();
        }

        tbody.empty().append(fragment); // Thêm fragment một lần
        tbody.removeClass('hidden'); // Hiện lại với hiệu ứng
    }

    // Hàm tải dữ liệu giỏ hàng từ API
    function loadCartData() {
        $.ajax({
            url: '/api/cart',
            method: 'GET',
            success: function(response) {
                if (response && response.result) {
                    cartData = response.result;
                    renderProductTable();
                } else {
                    $('#productTableBody').html('<tr><td colspan="3" style="text-align: center;">Không thể tải giỏ hàng</td></tr>');
                    updateSummary(0, 0);
                }
            },
            error: function(xhr) {
                console.log('Error:', xhr.responseJSON);
                $('#productTableBody').html('<tr><td colspan="3" style="text-align: center;">Lỗi khi tải giỏ hàng</td></tr>');
                updateSummary(0, 0);
            }
        });
    }

    // Gọi API khi trang tải
    loadCartData();

    // Hàm debounce để giảm tần suất render
    function debounce(func, wait) {
        let timeout;
        return function(...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    }

    // Xử lý thay đổi số lượng với event delegation và debounce
    $('#productTableBody').on('change', '.quantity-input', debounce(function() {
        const itemId = $(this).data('item-id');
        const newQuantity = parseInt($(this).val()) || 1;
        updateRow(itemId, newQuantity);
    }, 300));

    // Xử lý checkbox shipping
    $('#shipping').on('change', function() {
        isShipping = $(this).is(':checked');
        updateSummary(); // Cập nhật summary khi thay đổi shipping
    });

    // Xử lý nút Place Order
    $('.btn-place').on('click', function(event) {
        event.preventDefault();

        var email = $('#email').val();
        var firstName = $('#firstName').val();
        var phoneNumber = $('#phoneNumber').val();
        var message = $('#message').val();
        var address = $('#address').val();
        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
        const quantity = $('#cartQuantity').text();
        const totalPrice = parseFloat($('#orderTotal').text().replace('$', ''));
        const shippingType = isShipping ? '1' : '2'; // Giả định enum ShippingType

        if (quantity == 0) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Chưa có sản phẩm nào trong giỏ hàng.'
            });
            return;
        }

        if (!address && isShipping) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Vui lòng nhập địa chỉ khi chọn giao hàng!'
            });
            return;
        }

        const amount = totalPrice;
        const orderInfo = "Don hang " + amount;

        let requestUrl = '/api/order/place';
        if (paymentMethod == 2) {
            requestUrl = '/submitOrder';
        }

        // Chuẩn bị orderItems từ cartData
        const orderItems = cartData.map(item => ({
            productId: item.productId, // Giả định cartData có productId
            quantity: item.quantity,
            price: item.price,
            rentalDate: item.rentalDate,
        }));

        // Gửi Ajax request
        $.ajax({
            url: requestUrl,
            type: 'POST',
            data: JSON.stringify({
                orderInfo: orderInfo,
                email: email,
                firstName: firstName,
                phoneNumber: phoneNumber,
                message: message,
                address: address,
                typeShipping: shippingType,
                paymentMethod: parseInt(paymentMethod),
                quantity: parseInt(quantity),
                totalPrice: totalPrice,
                orderItems: orderItems
            }),
            contentType: 'application/json',
            success: function(response) {
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Đặt hàng thành công!",
                    showConfirmButton: false,
                    timer: 2000
                }).then(() => {
                    window.location.href = "/cosplay/home";
                });
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Something went wrong. Please try again.'
                });
            }
        });
    });
});

// JavaScript to ensure only one radio button can be selected at a time
document.addEventListener('DOMContentLoaded', function () {
    const paymentInStore = document.getElementById('paymentInStore');
    const vnpay = document.getElementById('vnpay');

    paymentInStore.addEventListener('change', function () {
        if (paymentInStore.checked) {
            vnpay.checked = false;
        }
    });

    vnpay.addEventListener('change', function () {
        if (vnpay.checked) {
            paymentInStore.checked = false;
        }
    });
});