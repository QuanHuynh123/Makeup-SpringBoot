export const UIManager = {
    sidebar: document.querySelector(".Sidebar"),
    main: document.querySelector(".admin-main"),
    submenuTitles: document.querySelectorAll(".Submenu-title"),
    popupMenus: {
        main: document.getElementById("popup-menu"),
        order: document.getElementById("popup-menu-order"),
    },
    menuButtons: {
        main: document.querySelector(".btnMore"),
        order: document.querySelector(".btnMoreOrder"),
    },
    customRangePopup: document.getElementById("customRangePopup"),
    toggleButton: document.querySelector(".btnToggleSidebar"),
    isSmallScreen: window.matchMedia("(max-width: 992px)").matches,
    activePopup: null,

    // Hàm toggle sidebar
    toggleSidebar() {
        this.sidebar?.classList.toggle("showSidebar");
        this.main?.classList.toggle("showSidebar");
    },

    // Hàm xử lý thay đổi kích thước
    handleResize() {
        const mediaQuery = window.matchMedia("(max-width: 992px)");
        const currentlySmallScreen = mediaQuery.matches;

        if (currentlySmallScreen !== this.isSmallScreen) {
            this.isSmallScreen = currentlySmallScreen;
            if (this.isSmallScreen) {
                this.sidebar?.classList.remove("showSidebar");
                this.main?.classList.remove("showSidebar");
            } else {
                this.sidebar?.classList.add("showSidebar");
                this.main?.classList.add("showSidebar");
            }
        }
    },

    // Hàm mở/đóng popup
    togglePopup(popupKey) {
        const popup = this.popupMenus[popupKey];
        if (!popup) return;

        if (this.activePopup === popupKey) {
            this.closePopup();
        } else {
            this.closePopup();
            popup.classList.remove("d-none");
            popup.classList.add("d-block");
            this.activePopup = popupKey;
            document.addEventListener("click", this.handleClickOutside.bind(this));
        }
    },

    closePopup() {
        if (this.activePopup && this.popupMenus[this.activePopup]) {
            const popup = this.popupMenus[this.activePopup];
            popup.classList.remove("d-block");
            popup.classList.add("d-none");
        }
        this.activePopup = null;
        document.removeEventListener("click", this.handleClickOutside.bind(this));
    },

    // Hàm xử lý click ngoài popup
    handleClickOutside(event) {
        if (
            this.activePopup &&
            this.popupMenus[this.activePopup] &&
            !this.popupMenus[this.activePopup].contains(event.target) &&
            !this.menuButtons[this.activePopup]?.contains(event.target)
        ) {
            this.closePopup();
        }
    },

    // Hàm hiển thị biểu đồ
    displayChart(response, containerId) {
        const chartContainer = document.getElementById(containerId);
        if (!chartContainer) return;

        chartContainer.innerHTML = "";
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
    },

    // Hàm xử lý custom range
    handleCustomRange(type, callback) {
        this.customRangePopup.classList.remove("d-none");

        const submitRangeButton = this.customRangePopup.querySelector("#submitRange");
        const closePopupButton = this.customRangePopup.querySelector("#closePopup");

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
                    callback(data, type === "orders" ? "chartContainerOrder" : "chartContainer");
                    this.customRangePopup.classList.add("d-none");
                })
                .catch((error) => {
                    console.error("Error fetching data:", error);
                    alert("Failed to fetch data");
                    this.customRangePopup.classList.add("d-none");
                });
        };

        const closeHandler = () => this.customRangePopup.classList.add("d-none");

        submitRangeButton.addEventListener("click", submitHandler, { once: true });
        closePopupButton.addEventListener("click", closeHandler, { once: true });
    },

    // Hàm chuyển đổi tab
    toggleTabContent(tabId) {
        const tabs = [
            'dashboard', 'schedule', 'staff', 'appointment', 'product',
            'order', 'approveOrder', 'create-product', 'edit-product'
        ];
        tabs.forEach(tab => {
            const element = document.getElementById(tab);
            if (element) {
                element.classList.add('d-none');
                if (tab === tabId) {
                    element.classList.remove('d-none');
                }
            }
        });
    },

    // Khởi tạo UI
    init() {
        if (!this.sidebar) console.warn("Sidebar element not found");

        // Gắn sự kiện resize và toggle sidebar
        window.addEventListener("resize", this.handleResize.bind(this));
        this.handleResize();
        this.toggleButton?.addEventListener("click", this.toggleSidebar.bind(this));

        // Gắn sự kiện submenu
        this.submenuTitles.forEach((title) => {
            title.addEventListener("click", () => {
                this.submenuTitles.forEach((t) => t.classList.remove("active"));
                title.classList.add("active");
            });
        });

        // Gắn sự kiện cho các nút popup
        if (this.menuButtons.main) {
            this.menuButtons.main.addEventListener("click", (event) => {
                event.stopPropagation();
                this.togglePopup("main");
            });
        }
        if (this.menuButtons.order) {
            this.menuButtons.order.addEventListener("click", (event) => {
                event.stopPropagation();
                this.togglePopup("order");
            });
        }

        // Gắn sự kiện cho popup menu items
        document.querySelectorAll(".popup-menu-item").forEach((item) => {
            item.addEventListener("click", async (event) => {
                const apiEndpoint = item.getAttribute("data-api");
                const isCustomRange = item.getAttribute("data-custom-range");

                if (isCustomRange) {
                    this.handleCustomRange("appointments", this.displayChart.bind(this));
                } else if (apiEndpoint) {
                    try {
                        const response = await fetch(apiEndpoint);
                        if (!response.ok) throw new Error("API call failed");
                        const data = await response.json();
                        this.displayChart(data, "chartContainer");
                    } catch (error) {
                        console.error("Error fetching API:", error);
                        alert("Failed to fetch data");
                    }
                }
                this.closePopup();
            });
        });

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
    }
};