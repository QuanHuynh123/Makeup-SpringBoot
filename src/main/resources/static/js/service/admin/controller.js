import { UIManager } from '/js/service/admin/ui-handler.js';
import { AppointmentModule } from '/js/service/admin/appointment.js';
import { OrderModule } from '/js/service/admin/order.js';
import { ProductModule } from '/js/service/admin/product.js';
import { CreateProductModule } from '/js/service/admin/create-product.js';
import { StaffModule} from '/js/service/admin/staff.js';

function loadDefaultTab() {
    const defaultTab = document.getElementById('tab-dashboard');
    if (defaultTab) {
        defaultTab.click();
    }
}

function fetchHtml(url, errorLabel = 'nội dung') {
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Lỗi tải ${errorLabel}: ${response.status}`);
            }
            return response.text();
        });
}

function initBootstrapModals() {
    document.querySelectorAll('.modal').forEach(modal => {
        new bootstrap.Modal(modal);
    });
}

function bindTab(tabButtonId, contentId, handler) {
    const tabButton = document.getElementById(tabButtonId);
    if (!tabButton) return;

    tabButton.addEventListener('click', () => {
        UIManager.toggleTabContent(contentId);
        handler?.();
    });
}

document.addEventListener("DOMContentLoaded", () => {
    UIManager.init();

    bindTab('tab-schedule', 'schedule', () => {
        loadStaffSelectAppointment();

        const now = new Date();
        const currentMonth = now.getMonth() + 1;
        const currentYear = now.getFullYear();
        const staffId = document.getElementById('staffSelect').value;

        loadAppointment(currentMonth, currentYear, staffId);
    });

    bindTab('tab-dashboard', 'dashboard');

    bindTab('tab-staff', 'staff', () => {
        fetchHtml('/admin/staff', 'danh sách nhân viên')
            .then(data => {
                document.getElementById('staff').innerHTML = data;
                StaffModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    bindTab('tab-product', 'product', () => {
        fetchHtml('/admin/products', 'danh sách sản phẩm')
            .then(data => {
                document.getElementById('product').innerHTML = data;
                ProductModule.init();
                CreateProductModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    bindTab('tab-appointment', 'appointment', () => {
        fetchHtml('/admin/appointment', 'nội dung Appointment')
            .then(data => {
                document.getElementById('appointment').innerHTML = data;
                initBootstrapModals();
                AppointmentModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    bindTab('tab-order', 'order', () => {
        fetchHtml('/admin/order', 'nội dung Order')
            .then(data => {
                document.getElementById('order').innerHTML = data;
                initBootstrapModals();
                OrderModule.init();
            })
            .catch(error => console.error('Error loading HTML:', error));
    });

    loadDefaultTab();
});