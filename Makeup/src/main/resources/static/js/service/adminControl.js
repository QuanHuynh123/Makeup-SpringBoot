import { UIManager } from '/js/service/admin-ui-handler.js';
import { AppointmentModule } from '/js/service/appointment.js';
import { OrderModule } from '/js/service/order.js';
import { ProductModule } from '/js/service/admin/product.js';
import { StaffModule} from '/js/service/admin/staff.js';
//import { ScheduleModule } from '/js/service/schedule.js';

// Hàm tải tab mặc định
function loadDefaultTab() {
    const defaultTab = document.getElementById('tab-dashboard');
    if (defaultTab) {
        defaultTab.click();
    } else {
        console.warn("Default tab element not found");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    UIManager.init();
    loadDefaultTab();

    // Hàm xử lý nút back
    function handleBackButton() {
        const btnBack = document.getElementById('btn-back');
        if (btnBack) {
            btnBack.addEventListener('click', function () {
                UIManager.toggleTabContent('product');
                fetch('/admin/products')
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById('product').innerHTML = data;
                        setupProductCreateEvent();
                        setupProductEditEvent();
                    })
                    .catch(error => console.error('Error loading HTML:', error));
            });
        }
    }

    function setupProductCreateEvent() {
        const createProductBtn = document.getElementById('tab-create-product');
        if (createProductBtn) {
            createProductBtn.addEventListener('click', function () {
                UIManager.toggleTabContent('create-product');
                fetch('/admin/products/create')
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById('create-product').innerHTML = data;
                        handleBackButton();
                    })
                    .catch(error => console.error('Error loading HTML:', error));
            });
        }
    }

    function setupProductEditEvent() {
        const productTable = document.getElementById('product');
        if (productTable) {
            productTable.addEventListener('click', function (event) {
                if (event.target && event.target.closest('div[id^="tab-edit-product-"]')) {
                    const clickedDiv = event.target.closest('div[id^="tab-edit-product-"]');
                    const productId = clickedDiv.id.split('-')[3];
                    console.log('Product ID for editing: ' + productId);
                    UIManager.toggleTabContent('edit-product');
                    fetch(`/api/admin/products/edit/${productId}`)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById('edit-product').innerHTML = data;
                            handleBackButton();
                        })
                        .catch(error => console.error('Error loading edit form:', error));
                }
            });
        }
    }

    // Tab schedule
    document.getElementById('tab-schedule').addEventListener('click', function () {
        UIManager.toggleTabContent('schedule');
        loadStaffSelectAppointment();

        const now = new Date();
        const currentMonth = now.getMonth() + 1;
        const currentYear = now.getFullYear();
        const staffId = document.getElementById('staffSelect').value;

        loadAppointment(currentMonth, currentYear, staffId);
    });

    // Tab dashboard
    document.getElementById('tab-dashboard').addEventListener('click', function () {
        UIManager.toggleTabContent('dashboard');
    });

    // Tab staff
    document.getElementById('tab-staff').addEventListener('click', function () {
        UIManager.toggleTabContent('staff');
        fetch('/admin/staff')
            .then(response => response.text())
            .then(data => {
                document.getElementById('staff').innerHTML = data;
                StaffModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    // Tab product
    document.getElementById('tab-product').addEventListener('click', function () {
        UIManager.toggleTabContent('product');
        fetch('/admin/products')
            .then(response => response.text())
            .then(data => {
                document.getElementById('product').innerHTML = data;
                setupProductCreateEvent();
                setupProductEditEvent();
                ProductModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    // Tab appointment
    document.getElementById('tab-appointment').addEventListener('click', function () {
        UIManager.toggleTabContent('appointment');
        fetch('/admin/appointment')
            .then(response => {
                if (!response.ok) throw new Error('Lỗi tải nội dung Appointment: ' + response.status);
                return response.text();
            })
            .then(data => {
                document.getElementById('appointment').innerHTML = data;
                document.querySelectorAll('.modal').forEach(modal => {
                    new bootstrap.Modal(modal);
                });
                AppointmentModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    // Tab order
    document.getElementById('tab-order').addEventListener('click', function () {
        UIManager.toggleTabContent('order');
        fetch('/admin/order')
            .then(response => {
                if (!response.ok) throw new Error('Lỗi tải nội dung Order: ' + response.status);
                return response.text();
            })
            .then(data => {
                document.getElementById('order').innerHTML = data;
                document.querySelectorAll('.modal').forEach(modal => {
                    new bootstrap.Modal(modal);
                });
                OrderModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });
});