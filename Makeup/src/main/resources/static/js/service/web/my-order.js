$(document).ready(function() {
    const orderList = $('#orderList');
    const orderItemsModal = $('#orderItemsModal');
    const orderItemsList = $('#orderItemsList');
    const closeModal = $('.close');
    let ordersData = []; // Lưu toàn bộ dữ liệu để lọc và sắp xếp

    // Hàm gọi API lấy danh sách đơn hàng
    function loadOrders() {
        $.ajax({
            url: '/api/order/my-order',
            method: 'GET',
            success: function(response) {
                console.log('API Response:', response);
                if (response.code === 200 && response.result) {
                    ordersData = response.result; // Lưu dữ liệu
                    renderOrderList(ordersData);
                } else if (response.success && response.result) {
                    ordersData = response.result;
                    renderOrderList(ordersData);
                } else {
                    orderList.html('<p class="text-muted">Bạn chưa có đơn hàng nào.</p>');
                }
            },
            error: function(xhr) {
                console.log('Error:', xhr.responseJSON);
                orderList.html('<p class="text-muted">Không thể tải danh sách đơn hàng.</p>');
            }
        });
    }

    // Hàm render danh sách đơn hàng
    function renderOrderList(orders) {
        const fragment = document.createDocumentFragment();
        if (!orders || orders.length === 0) {
            orderList.html('<p class="text-muted">Bạn chưa có đơn hàng nào.</p>');
            return;
        }

        orders.forEach(order => {
            const div = document.createElement('div');
            div.className = 'alert alert-light col-12';
            div.setAttribute('data-order-id', order.id);
            div.innerHTML = `
                <h5 class="alert-heading">
                    Mã đơn hàng: ${order.id} | Ngày: ${new Date(order.orderDate).toLocaleDateString()}
                </h5>
                <hr>
                <p class="mb-0">
                    Tổng hóa đơn: <b>${order.totalPrice}$</b> | Thành tiền: <b>${order.totalQuantity} sản phẩm</b>
                </p>
                <p class="font-weight-light mb-2">Tình trạng đơn hàng: <mark>${order.status === 'PROCESSED' ? 'Đã xử lý' : 'Đang chờ xử lý'}</mark></p>
                <p class="font-weight-light mb-2">Ngày lấy: <mark>${order.pickupDate ? new Date(order.pickupDate).toLocaleDateString() : 'Đang chờ xử lý'}</mark></p>
            `;
            fragment.appendChild(div);
        });
        orderList.empty().append(fragment);
    }

    // Hàm gọi API lấy chi tiết items của đơn hàng
    function loadOrderItems(orderId) {
        $.ajax({
            url: `/api/order/${orderId}/items`,
            method: 'GET',
            success: function(response) {
                console.log('Items Response:', response);
                if (response.code === 200 && response.result) {
                    renderOrderItems(response.result);
                    orderItemsModal.css('display', 'block');
                } else if (response.success && response.result) {
                    renderOrderItems(response.result);
                    orderItemsModal.css('display', 'block');
                } else {
                    orderItemsList.html('<p>Không có chi tiết đơn hàng.</p>');
                    orderItemsModal.css('display', 'block');
                }
            },
            error: function(xhr) {
                console.log('Error:', xhr.responseJSON);
                orderItemsList.html('<p>Lỗi khi tải chi tiết đơn hàng.</p>');
                orderItemsModal.css('display', 'block');
            }
        });
    }

    // Hàm render danh sách items
    function renderOrderItems(items) {
        if (!items || items.length === 0) {
            orderItemsList.html('<p>Không có sản phẩm trong đơn hàng.</p>');
            return;
        }

        const fragment = document.createDocumentFragment();
        items.forEach(item => {
            const div = document.createElement('div');
            div.className = 'item';
            div.innerHTML = `
                <div class="item-content">
                    <img src="/images/product/${item.firstImageUrl}" alt="${item.productName}" class="item-image" style="width: 70px; height: 70px; object-fit: cover; margin-right: 10px;">
                    <div class="item-details">
                        <span class="item-name">${item.productName} (x${item.quantity})</span>
                        <span class="item-status">Trạng thái: ${item.status}</span>
                        <span class="item-rental">Ngày thuê: ${new Date(item.rentalDate).toLocaleDateString()}</span>
                    </div>
                </div>
                <span class="item-price">${(item.price * item.quantity).toFixed(2)}$</span>
            `;
            fragment.appendChild(div);
        });
        orderItemsList.empty().append(fragment);
    }

    // Hàm lọc và sắp xếp
    function filterAndSortOrders() {
        let filteredOrders = [...ordersData]; // Sao chép dữ liệu gốc
        const searchTerm = $('#orderSearch').val().toLowerCase();
        const sortBy = $('#sortByDate').val();

        // Lọc theo mã đơn hàng
        if (searchTerm) {
            filteredOrders = filteredOrders.filter(order =>
                order.id.toLowerCase().includes(searchTerm)
            );
        }

        // Sắp xếp theo ngày
        if (sortBy === 'newest') {
            filteredOrders.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
        } else if (sortBy === 'oldest') {
            filteredOrders.sort((a, b) => new Date(a.orderDate) - new Date(b.orderDate));
        }

        renderOrderList(filteredOrders);
    }

    // Gọi API khi trang tải
    loadOrders();

    // Gắn sự kiện tìm kiếm
    $('#orderSearch').on('input', function() {
        filterAndSortOrders();
    });

    // Gắn sự kiện sắp xếp
    $('#sortByDate').on('change', function() {
        filterAndSortOrders();
    });

    // Gắn sự kiện click với delegation
    $(document).on('click', '.alert-light', function() {
        const orderId = $(this).data('order-id');
        console.log('Clicked Order ID:', orderId);
        orderItemsList.empty(); // Xóa nội dung cũ
        loadOrderItems(orderId);
    });

    // Đóng modal khi click vào nút close
    closeModal.on('click', function() {
        orderItemsModal.css('display', 'none');
    });

    // Đóng modal khi click ra ngoài
    $(document).on('click', function(event) {
        if ($(event.target).is('.modal')) {
            orderItemsModal.css('display', 'none');
        }
    });
});