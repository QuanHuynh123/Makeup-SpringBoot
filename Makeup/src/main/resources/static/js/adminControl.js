document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector(".Sidebar");
    const main = document.querySelector(".admin-main");
    const submenuTitles = document.querySelectorAll(".Submenu-title");
    const popupMenus = {
        main: document.getElementById("popup-menu"),
        order: document.getElementById("popup-menu-order"),
    };
    const menuButtons = {
        main: document.querySelector(".btnMore"),
        order: document.querySelector(".btnMoreOrder"),
    };
    const customRangePopup = document.getElementById("customRangePopup");
    const toggleButton = document.querySelector(".btnToggleSidebar");

    // Hàm toggle sidebar
    function toggleSidebar() {
        sidebar?.classList.toggle("showSidebar");
        main?.classList.toggle("showSidebar");
    }

    // Hàm toggle sidebar
    function toggleSidebar() {
        sidebar?.classList.toggle("showSidebar");
        main?.classList.toggle("showSidebar");
    }

    // Biến trạng thái lưu kích thước màn hình hiện tại
    let isSmallScreen = window.matchMedia("(max-width: 992px)").matches;

    // Hàm xử lý thay đổi kích thước
    function handleResize() {
        const mediaQuery = window.matchMedia("(max-width: 992px)");
        const currentlySmallScreen = mediaQuery.matches;

        // Chỉ thực hiện khi trạng thái thay đổi
        if (currentlySmallScreen !== isSmallScreen) {
            isSmallScreen = currentlySmallScreen; // Cập nhật trạng thái

            // Xóa hoặc thêm class dựa trên kích thước màn hình
            if (isSmallScreen) {
                sidebar?.classList.remove("showSidebar");
                main?.classList.remove("showSidebar");
            } else {
                sidebar?.classList.add("showSidebar");
                main?.classList.add("showSidebar");
            }
        }
    }

    // Lắng nghe sự kiện resize
    window.addEventListener("resize", handleResize);

    // Kiểm tra kích thước ngay khi tải trang
    handleResize();

    // Gắn sự kiện click vào nút toggle
    toggleButton.addEventListener("click", toggleSidebar);

    let activePopup = null;

    if (!sidebar) console.warn("Sidebar element not found");

    // Hàm mở/đóng popup
    function togglePopup(popupKey) {
        const popup = popupMenus[popupKey];
        if (!popup) return;

        if (activePopup === popupKey) {
            closePopup();
        } else {
            closePopup(); // Đóng popup đang mở trước đó
            popup.classList.remove("d-none");
            popup.classList.add("d-block");
            activePopup = popupKey;
            document.addEventListener("click", handleClickOutside);
        }
    }

    // Hàm đóng popup
    function closePopup() {
        if (activePopup && popupMenus[activePopup]) {
            const popup = popupMenus[activePopup];
            popup.classList.remove("d-block");
            popup.classList.add("d-none");
        }
        activePopup = null;
        document.removeEventListener("click", handleClickOutside);
    }

    // Hàm xử lý click ngoài popup
    function handleClickOutside(event) {
        if (
            activePopup &&
            popupMenus[activePopup] &&
            !popupMenus[activePopup].contains(event.target) &&
            !menuButtons[activePopup]?.contains(event.target)
        ) {
            closePopup();
        }
    }

    // Xử lý click submenu
    submenuTitles.forEach((title) => {
        title.addEventListener("click", () => {
            submenuTitles.forEach((t) => t.classList.remove("active"));
            title.classList.add("active");
        });
    });

    // Gắn sự kiện toggle popup vào các nút menu
    if (menuButtons.main) {
        menuButtons.main.addEventListener("click", (event) => {
            event.stopPropagation();
            togglePopup("main");
        });
    }

    if (menuButtons.order) {
        menuButtons.order.addEventListener("click", (event) => {
            event.stopPropagation();
            togglePopup("order");
        });
    }

    // Xử lý các mục trong popup menu
    document.querySelectorAll(".popup-menu-item").forEach((item) => {
        item.addEventListener("click", async (event) => {
            const apiEndpoint = item.getAttribute("data-api");
            const isCustomRange = item.getAttribute("data-custom-range");

            if (isCustomRange) {
                handleCustomRange("appointments");
            } else if (apiEndpoint) {
                try {
                    const response = await fetch(apiEndpoint);
                    if (!response.ok) throw new Error("API call failed");
                    const data = await response.json();
                    displayChart(data, "chartContainer");
                } catch (error) {
                    console.error("Error fetching API:", error);
                    alert("Failed to fetch data");
                }
            }
            closePopup();
        });
    });

    // Xử lý các mục trong popup menu order
    document.querySelectorAll(".popup-menu-item-order").forEach((item) => {
        item.addEventListener("click", async (event) => {
            const apiEndpoint = item.getAttribute("data-api");
            const isCustomRange = item.getAttribute("data-custom-range");

            if (isCustomRange) {
                handleCustomRange("orders");
            } else if (apiEndpoint) {
                try {
                    const response = await fetch(apiEndpoint);
                    if (!response.ok) throw new Error("API call failed");
                    const data = await response.json();
                    displayChart(data, "chartContainerOrder");
                } catch (error) {
                    console.error("Error fetching API:", error);
                    alert("Failed to fetch data");
                }
            }
            closePopup();
        });
    });

    // Hàm xử lý Custom Range
    function handleCustomRange(type) {
        customRangePopup.classList.remove("d-none");

        const submitRangeButton = customRangePopup.querySelector("#submitRange");
        const closePopupButton = customRangePopup.querySelector("#closePopup");

        const submitHandler = () => {
            const startDate = document.getElementById("startDate").value;
            const endDate = document.getElementById("endDate").value;

            if (!startDate || !endDate) {
                alert("Please select both start and end dates.");
                return;
            }

            if (startDate > endDate) {
                alert("Start date must be before end date.");
                return;
            }

            fetch(`/${type}/stats/customRange?startDate=${startDate}&endDate=${endDate}`)
                .then((response) => response.json())
                .then((data) => {
                    type == "orders" ? displayChart(data, "chartContainerOrder") : displayChart(data, "chartContainer");
                    customRangePopup.classList.add("d-none");
                })
                .catch((error) => {
                    console.error("Error fetching data:", error);
                    alert("Failed to fetch data");
                    customRangePopup.classList.add("d-none");
                });
        };

        const closeHandler = () => customRangePopup.classList.add("d-none");

        submitRangeButton.addEventListener("click", submitHandler, { once: true });
        closePopupButton.addEventListener("click", closeHandler, { once: true });
    }

    // Hàm hiển thị biểu đồ với dữ liệu API
    function displayChart(response, containerId) {
        const chartContainer = document.getElementById(containerId);
        if (!chartContainer) return;

        chartContainer.innerHTML = ""; // Xóa nội dung cũ
        if (!response || typeof response !== "object") {
            console.error("Invalid data", response);
            return;
        }

        const dataPoints = Object.entries(response).map(([key, count]) => ({
            label: key.includes("-") ? key : `Ngày ${key}`,
            y: Math.round(count || 0),
        }));

        const chart = new CanvasJS.Chart(containerId, {
            animationEnabled: true,
            theme: "light2",
            axisY: {
                title: "Số lượng",
                includeZero: true,
                interval: 1,
            },
            data: [{ type: "column", dataPoints }],
        });
        chart.render();
    }

    // Hàm chuyển đổi các tab
    function toggleTabContent(tabId) {
        const dashboard = document.getElementById('dashboard');
        const schedule = document.getElementById('schedule');
        const staff = document.getElementById('staff');
        const appointment = document.getElementById('appointment');
        const product = document.getElementById('product');
        const createProduct = document.getElementById('create-product');
        const editProduct = document.getElementById('edit-product');

        // Ẩn tất cả nội dung và sau đó hiển thị tab tương ứng
        dashboard.classList.add('d-none');
        schedule.classList.add('d-none');
        staff.classList.add('d-none');
        appointment.classList.add('d-none');
        product.classList.add('d-none');
        createProduct.classList.add('d-none');
        editProduct.classList.add('d-none');

        if (tabId === 'dashboard') {
            dashboard.classList.remove('d-none');
        } else if (tabId === 'schedule') {
            schedule.classList.remove('d-none');
        } else if (tabId === 'staff') {
            staff.classList.remove('d-none');
        } else if (tabId === 'product') {
            product.classList.remove('d-none');
        } else if (tabId === 'appointment') {
            appointment.classList.remove('d-none');
        }
    }

    // Sử dụng hàm dùng chung cho các tab
    document.getElementById('tab-schedule').addEventListener('click', function () {
        // Chuyển tab và tải nội dung
        toggleTabContent('schedule');
        loadStaffSelectAppointment();
        loadAppointment(12, 2024, document.getElementById("staffSelect").value);
    });

    document.getElementById('tab-dashboard').addEventListener('click', function () {
        toggleTabContent('dashboard');
    });

    document.getElementById('tab-staff').addEventListener('click', function () {
        toggleTabContent('staff');
        fetch('/admin/staff')
            .then(response => response.text())
            .then(data => {
                document.getElementById('staff').innerHTML = data;
                loadStaffs();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    document.getElementById('tab-appointment').addEventListener('click', function () {
        toggleTabContent('appointment');
        fetch('/admin/appointment')
            .then(response => response.text())
            .then(data => {
                document.getElementById('appointment').innerHTML = data;
                loadAppointmentsDetail();
                loadMakeUpServices();
                loadStaffSelect();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    document.getElementById('tab-product').addEventListener('click', function () {
        toggleTabContent('product');
        fetch('/admin/products')
            .then(response => response.text())
            .then(data => {
                document.getElementById('product').innerHTML = data;
            })
            .catch(error => console.error('Error loading HTML:', error));
    });
    
    document.getElementById('btn-back').addEventListener('click', function () {
        toggleTabContent('product');
        fetch('/admin/products')
            .then(response => response.text())
            .then(data => {
                document.getElementById('product').innerHTML = data;
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    ///////////////////////Appointment tab///////////////////////////

});