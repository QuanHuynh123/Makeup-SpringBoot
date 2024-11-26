// document.addEventListener("DOMContentLoaded", () => {
//     const sidebar = document.querySelector(".Sidebar");
//     const main = document.querySelector(".admin-main");
//     const submenuTitles = document.querySelectorAll(".Submenu-title");
//     const popupMenuOrder = document.getElementById("popup-menu-order");
//     const popupMenuAppointment = document.getElementById("popup-menu-appointment");
//     const menuButtonOrder = document.querySelector(".btnMore-order");
//     const menuButtonAppointment = document.querySelector(".btnMore-appointment");

//     let isPopupOrderOpen = false;
//     let isPopupAppointmentOpen = false;

//     if (!sidebar) {
//         console.warn("Sidebar element not found");
//     }

//     // Hàm toggle sidebar
//     function toggleSidebar() {
//         sidebar?.classList.toggle("showSidebar");
//         main?.classList.toggle("showSidebar");
//     }

//     // Hàm toggle popup menu (popup order)
//     function togglePopupOrder() {
//         if (!popupMenuOrder) return;

//         if (!isPopupOrderOpen) {
//             popupMenuOrder.classList.remove("d-none");
//             popupMenuOrder.classList.add("d-block");
//             isPopupOrderOpen = true;
//             document.addEventListener("click", handleClickOutsideOrder);
//         } else {
//             closePopupOrder();
//         }
//     }

//     // Hàm toggle popup menu (popup appointment)
//     function togglePopupAppointment() {
//         if (!popupMenuAppointment) return;

//         if (!isPopupAppointmentOpen) {
//             popupMenuAppointment.classList.remove("d-none");
//             popupMenuAppointment.classList.add("d-block");
//             isPopupAppointmentOpen = true;
//             document.addEventListener("click", handleClickOutsideAppointment);
//         } else {
//             closePopupAppointment();
//         }
//     }

//     // Hàm đóng popup order
//     function closePopupOrder() {
//         if (!popupMenuOrder) return;

//         popupMenuOrder.classList.remove("d-block");
//         popupMenuOrder.classList.add("d-none");
//         isPopupOrderOpen = false;

//         document.removeEventListener("click", handleClickOutsideOrder);
//     }

//     // Hàm đóng popup appointment
//     function closePopupAppointment() {
//         if (!popupMenuAppointment) return;

//         popupMenuAppointment.classList.remove("d-block");
//         popupMenuAppointment.classList.add("d-none");
//         isPopupAppointmentOpen = false;

//         document.removeEventListener("click", handleClickOutsideAppointment);
//     }

//     // Xử lý click ngoài popup order
//     function handleClickOutsideOrder(event) {
//         if (
//             popupMenuOrder &&
//             !popupMenuOrder.contains(event.target) &&
//             !menuButtonOrder?.contains(event.target)
//         ) {
//             closePopupOrder();
//         }
//     }

//     // Xử lý click ngoài popup appointment
//     function handleClickOutsideAppointment(event) {
//         if (
//             popupMenuAppointment &&
//             !popupMenuAppointment.contains(event.target) &&
//             !menuButtonAppointment?.contains(event.target)
//         ) {
//             closePopupAppointment();
//         }
//     }

//     // Gán sự kiện click cho các nút
//     menuButtonOrder?.addEventListener("click", togglePopupOrder);
//     menuButtonAppointment?.addEventListener("click", togglePopupAppointment);

//     // Xử lý click submenu
//     submenuTitles.forEach((title) => {
//         title.addEventListener("click", () => {
//             submenuTitles.forEach((t) => t.classList.remove("active"));
//             title.classList.add("active");
//         });
//     });

//     // Xử lý các mục trong popup menu
//     const popupMenuItems = document.querySelectorAll(".popupMenuItem");
//     popupMenuItems.forEach(item => {
//         item.addEventListener("click", async (event) => {
//             const apiEndpoint = item.getAttribute("data-api");
//             const isCustomRange = item.getAttribute("data-custom-range");

//             if (isCustomRange) {
//                 handleCustomRange();
//             } else if (apiEndpoint) {
//                 try {
//                     const response = await fetch(apiEndpoint);
//                     if (!response.ok) throw new Error("API call failed");
//                     const data = await response.json();

//                     // Gọi hàm vẽ biểu đồ với dữ liệu từ API
//                     displayChart(data);
//                 } catch (error) {
//                     console.error("Error fetching API:", error);
//                     alert("Failed to fetch data");
//                 }
//             }
//             closePopup()
//         });
//     });

//     // Hàm xử lý Custom Range
//     function handleCustomRange() {
//         const customRangePopup = document.getElementById("customRangePopup");
//         customRangePopup.classList.remove("d-none"); // Hiển thị popup

//         // Lắng nghe sự kiện đóng và gửi dữ liệu khi chọn phạm vi ngày
//         const submitRangeButton = customRangePopup.querySelector("#submitRange");
//         const closePopupButton = customRangePopup.querySelector("#closePopup");

//         submitRangeButton.addEventListener("click", () => {
//             const startDate = document.getElementById("startDate").value;
//             const endDate = document.getElementById("endDate").value;

//             if (!startDate || !endDate) {
//                 alert("Please select both start and end dates.");
//                 return;
//             }

//             if (startDate > endDate) {
//                 alert("Please select start date before end date.");
//                 return;
//             }

//             // Gửi yêu cầu API với startDate và endDate
//             fetch(`/appointments/stats/customRange?startDate=${startDate}&endDate=${endDate}`)
//                 .then(response => response.json())
//                 .then(data => {
//                     displayChart(data, "appointment"); // Gọi với loại chart appointment
//                     customRangePopup.classList.add("d-none"); // Đóng popup sau khi nhận dữ liệu
//                 })
//                 .catch(error => {
//                     console.error("Error fetching data:", error);
//                     alert("Failed to fetch data");
//                     customRangePopup.classList.add("d-none"); // Đóng popup nếu có lỗi
//                 });
//         });

//         closePopupButton.addEventListener("click", () => {
//             customRangePopup.classList.add("d-none"); // Đóng popup khi nhấn "Close"
//         });
//     }

//     // Hàm hiển thị biểu đồ với dữ liệu API
//     function displayChart(response, chartType) {
//         // Lấy phần tử container của biểu đồ
//         const chartContainer = document.getElementById("chartContainer");

//         // Xóa phần tử biểu đồ cũ nếu tồn tại
//         if (chartContainer) {
//             chartContainer.innerHTML = ''; // Xóa nội dung của phần tử container
//         }

//         // Khởi tạo mảng dataPoints để chứa dữ liệu cho biểu đồ
//         let dataPoints = [];

//         // Kiểm tra dữ liệu và tạo các dataPoints cho biểu đồ
//         if (response && typeof response === "object") {
//             Object.keys(response).forEach(key => {
//                 let count = response[key] || 0; // Nếu không có dữ liệu thì set là 0

//                 // Đảm bảo số lượng là số nguyên
//                 count = Math.round(count);

//                 // Dữ liệu theo ngày hoặc theo tháng
//                 if (key.includes("-")) {
//                     dataPoints.push({
//                         label: key, // Ngày hoặc tuần
//                         y: count // Số lượng
//                     });
//                 } else {
//                     dataPoints.push({
//                         label: "Ngày " + key,
//                         y: count
//                     });
//                 }
//             });

//             // Tạo biểu đồ mới trong phần tử chartContainer
//             const chart = new CanvasJS.Chart("chartContainer", {
//                 animationEnabled: true,
//                 theme: "light2",
//                 axisY: {
//                     title: "Số lượng",
//                     includeZero: true,
//                     minimum: 0,
//                     interval: 1,
//                     stripLines: [{
//                         value: 0,
//                         color: "#888",
//                         lineDashType: "solid",
//                         width: 1
//                     }],
//                     labelFormatter: function (e) {
//                         return Math.round(e.value);
//                     }
//                 },
//                 data: [{
//                     type: "column", // Loại biểu đồ: cột (column)
//                     dataPoints: dataPoints
//                 }]
//             });
//             chart.render();
//         } else {
//             console.error("Dữ liệu không hợp lệ", response);
//         }
//     }
// });



document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector(".Sidebar");
    const main = document.querySelector(".admin-main");
    const submenuTitles = document.querySelectorAll(".Submenu-title");
    const popupMenu = document.getElementById("popup-menu");
    const menuButton = document.querySelector(".btnMore");
    const popupMenuItems = document.querySelectorAll(".popup-menu-item");

    let isPopupOpen = false; // Trạng thái popup menu
    let chartContainer = document.getElementById("chartContainer");

    if (!sidebar) {
        console.warn("Sidebar element not found");
    }

    // Hàm toggle sidebar
    function toggleSidebar() {
        sidebar?.classList.toggle("showSidebar");
        main?.classList.toggle("showSidebar");
    }

    // Hàm toggle popup menu
    function togglePopup() {
        if (!popupMenu) return;

        if (!isPopupOpen) {
            popupMenu.classList.remove("d-none");
            popupMenu.classList.add("d-block");
            isPopupOpen = true;

            document.addEventListener("click", handleClickOutside);
        } else {
            closePopup();
        }
    }

    // Hàm đóng popup menu
    function closePopup() {
        if (!popupMenu) return;

        popupMenu.classList.remove("d-block");
        popupMenu.classList.add("d-none");
        isPopupOpen = false;

        document.removeEventListener("click", handleClickOutside);
    }

    // Xử lý click ngoài popup
    function handleClickOutside(event) {
        if (
            popupMenu &&
            !popupMenu.contains(event.target) &&
            !menuButton?.contains(event.target)
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

    // Gắn sự kiện toggle popup vào nút menu
    if (menuButton) {
        menuButton.addEventListener("click", (event) => {
            event.stopPropagation();
            togglePopup();
        });
    }

    // Xử lý các mục trong popup menu
    popupMenuItems.forEach(item => {
        item.addEventListener("click", async (event) => {
            const apiEndpoint = item.getAttribute("data-api");
            const isCustomRange = item.getAttribute("data-custom-range");

            if (isCustomRange) {
                handleCustomRange();
            } else if (apiEndpoint) {
                try {
                    const response = await fetch(apiEndpoint);
                    if (!response.ok) throw new Error("API call failed");
                    const data = await response.json();

                    // Gọi hàm vẽ biểu đồ với dữ liệu từ API
                    displayChart(data);
                } catch (error) {
                    console.error("Error fetching API:", error);
                    alert("Failed to fetch data");
                }
            }
            closePopup()
        });
    });

    // Hàm xử lý Custom Range
    function handleCustomRange() {
        const customRangePopup = document.getElementById("customRangePopup");
        customRangePopup.classList.remove("d-none"); // Hiển thị popup

        // Lắng nghe sự kiện đóng và gửi dữ liệu khi chọn phạm vi ngày
        const submitRangeButton = customRangePopup.querySelector("#submitRange");
        const closePopupButton = customRangePopup.querySelector("#closePopup");

        submitRangeButton.addEventListener("click", () => {
            const startDate = document.getElementById("startDate").value;
            const endDate = document.getElementById("endDate").value;

            if (!startDate || !endDate) {
                alert("Please select both start and end dates.");
                return;
            }

            if (startDate > endDate) {
                alert("Please select start date before end date.");
                return;
            }

            // Gửi yêu cầu API với startDate và endDate
            fetch(`/appointments/stats/customRange?startDate=${startDate}&endDate=${endDate}`)
                .then(response => response.json())
                .then(data => {
                    displayChart(data);
                    customRangePopup.classList.add("d-none"); // Đóng popup sau khi nhận dữ liệu
                })
                .catch(error => {
                    console.error("Error fetching data:", error);
                    alert("Failed to fetch data");
                    customRangePopup.classList.add("d-none"); // Đóng popup nếu có lỗi
                });
        });

        closePopupButton.addEventListener("click", () => {
            customRangePopup.classList.add("d-none"); // Đóng popup khi nhấn "Close"
        });
    }

    // Hàm hiển thị biểu đồ với dữ liệu API
    function displayChart(response) {
        // Lấy phần tử container của biểu đồ
        const chartContainer = document.getElementById("chartContainer");

        // Xóa phần tử biểu đồ cũ nếu tồn tại
        if (chartContainer) {
            chartContainer.innerHTML = ''; // Xóa nội dung của phần tử container
        }

        // Khởi tạo mảng dataPoints để chứa dữ liệu cho biểu đồ
        let dataPoints = [];

        // Kiểm tra xem API trả về dữ liệu theo ngày hay không
        if (response && typeof response === "object") {
            // Lặp qua các phần tử trong response và tạo dataPoints cho biểu đồ
            Object.keys(response).forEach(key => {
                let count = response[key] || 0; // Nếu không có dữ liệu thì set là 0

                // Đảm bảo số lượng appointments là số nguyên
                count = Math.round(count); // Làm tròn số lượng appointments thành số nguyên

                // Nếu dữ liệu là ngày tháng
                if (key.includes("-")) {
                    dataPoints.push({
                        label: key, // Ngày hoặc tuần
                        y: count // Số lượng
                    });
                } else {
                    // Nếu dữ liệu là theo ngày trong tháng
                    dataPoints.push({
                        label: "Ngày " + key,
                        y: count
                    });
                }
            });

            // Tạo biểu đồ mới trong phần tử chartContainer
            const chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "light2",
                axisY: {
                    title: "Số lượng",
                    includeZero: true, // Đảm bảo bắt đầu từ 0 trên trục Y
                    minimum: 0, // Thiết lập giá trị tối thiểu cho trục Y
                    interval: 1, // Thiết lập khoảng cách giữa các nhãn trên trục Y (bước nhảy là 1)
                    stripLines: [{ // Tùy chọn để hiển thị các đường kẻ ngang nếu cần
                        value: 0,
                        color: "#888",
                        lineDashType: "solid",
                        width: 1
                    }],
                    labelFormatter: function (e) {
                        return Math.round(e.value); // Đảm bảo các giá trị trên trục Y luôn là số nguyên
                    }
                },
                data: [{
                    type: "column", // Loại biểu đồ: cột (column)
                    dataPoints: dataPoints
                }]
            });
            chart.render();
        } else {
            console.error("Dữ liệu không hợp lệ", response);
        }
    }
});
