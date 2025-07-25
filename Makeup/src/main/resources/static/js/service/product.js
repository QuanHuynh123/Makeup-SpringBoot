$(document).ready(function () {
    // Hàm tạo HTML cho sản phẩm
    function generateProductHtml(products) {
        let html = '';
        products.forEach(product => {
            html += `
                <li class="product-item">
                    <a href="/productDetail/${product.id}">
                        <img src="/images/product/${product.firstImage}" alt="Product Image">
                        <div class="product-info">
                            <h4>${product.nameProduct || 'No name available'}</h4>
                            <p class="product-price">${product.price || 'N/A'}</p>
                        </div>
                    </a>
                </li>
            `;
        });
        return html;
    }

    // Hàm tạo HTML cho phân trang
    function generatePaginationHtml(categoryId, currentPage, totalPages) {
        let html = '';
        if (currentPage > 0) {
            html += `<li><a href="#" class="page-link" data-action="prev">< Prev</a></li>`;
        }
        for (let i = 0; i < totalPages; i++) {
            html += `<li><a href="#" class="page-link ${i === currentPage ? 'active' : ''}" data-page="${i}">${i + 1}</a></li>`;
        }
        if (currentPage < totalPages - 1) {
            html += `<li><a href="#" class="page-link" data-action="next">Next ></a></li>`;
        }
        return html;
    }

    // Hàm tải sản phẩm bằng AJAX
    function loadProducts(categoryId, page , searchQuery) {

        $.ajax({
            url: `/api/category?id=${categoryId}&page=${page}&searchKey=${searchQuery}`,
            method: 'GET',
            success: function (response) {
                if (!response || !response.result) {
                    $('#productList').html('<p style="margin-top:10px;">Không có sản phẩm nào.</p>');
                    $('#paginationContainer').hide();
                    return;
                }
                if (response.result.products.length > 0) {
                    $('#productList').html(generateProductHtml(response.result.products));
                    $('#currentPage').data('current-page', response.result.currentPage);
                    $('#totalPages').data('total-pages', response.result.totalPages);
                    $('#paginationList').html(generatePaginationHtml(categoryId, response.result.currentPage, response.result.totalPages));
                    $('#paginationContainer').show();
                } else {
                    $('#productList').html('<p>Không có sản phẩm nào trong mục này.</p>');
                    $('#paginationContainer').hide();
                }
            },
            error: function (xhr) {
                $('#paginationContainer').hide();
            }
        });
    }

    // Xử lý phân trang
    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();
        const categoryId = $('#categoryId').data('category-id') || -1;
        let currentPage = $('#currentPage').data('current-page');
        const totalPages = $('#totalPages').data('total-pages');

        // Xóa class active khỏi tất cả các page-link
        $('.page-link').removeClass('active');

        // Xử lý logic phân trang
        if ($(this).data('action') === 'prev' && currentPage > 0) {
            currentPage--;
        } else if ($(this).data('action') === 'next' && currentPage < totalPages - 1) {
            currentPage++;
        } else if ($(this).data('page') !== undefined) {
            currentPage = parseInt($(this).data('page'));
        }

        // Thêm class active cho trang được chọn
        if ($(this).data('page') !== undefined) {
            $(this).addClass('active');
        }

        if (currentPage >= 0 && currentPage < totalPages) {
            loadProducts(categoryId, currentPage , $('#searchKey').data('search-key') || null);
        }
    });

    // Tải sản phẩm khi trang được tải
    const categoryId = $('#categoryId').data('category-id') || -1;
    const currentPage = $('#currentPage').data('current-page') || 0;
    const searchQuery = $('#searchKey').data('search-key') || null;
    loadProducts(categoryId, currentPage, searchQuery);
});