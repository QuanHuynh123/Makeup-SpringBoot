    $(document).ready(function () {
        let appointmentsData = [];

        // Hàm tạo HTML cho lịch hẹn
        function generateAppointmentHtml(appointments) {

            console.log(appointments);
            let html = '';
            if (!appointments || appointments.length === 0) {
                html = '<p class="text-muted">Bạn chưa có lịch hẹn nào.</p>';
            } else {
                appointments.forEach(item => {
                    html += `
                        <div class="alert alert-light col-12" role="alert">
                            <h5>Ngày: ${item.makeupDate || ''}</h5>
                            <h5>Giờ bắt đầu: ${item.startTime || ''}</h5>
                            <p><span>Nhân viên: ${item.nameStaff || 'Chưa có'}</span></p>
                            <hr>
                            <p class="mb-0">Thành tiền: ${item.price || 'N/A'}</p>
                            <p class="font-weight-light mb-2">
                                Tình trạng lịch hẹn: <mark>${item.status ? 'Đã duyệt' : 'Đang chờ duyệt'}</mark>
                            </p>
                            <div class="alert alert-primary" role="alert">
                                <a href="#" class="alert-link">Dịch vụ: ${item.typeMakeupName || 'Chưa có'}</a>
                            </div>
                        </div>
                    `;
                });
            }
            return html;
        }

        // Hàm tìm kiếm linh động trên client-side
        function filterAppointments(searchKey) {
            if (!searchKey) {
                $('#appointmentList').html(generateAppointmentHtml(appointmentsData));
                return;
            }
            const filtered = appointmentsData.filter(item =>
                (item.makeupDate && item.makeupDate.toLowerCase().includes(searchKey.toLowerCase())) ||
                (item.startTime && item.startTime.toLowerCase().includes(searchKey.toLowerCase())) ||
                (item.nameStaff && item.nameStaff.toLowerCase().includes(searchKey.toLowerCase())) ||
                (item.typeMakeupName && item.typeMakeupName.toLowerCase().includes(searchKey.toLowerCase())) ||
                (item.price && item.price.toString().includes(searchKey)) ||
                (item.status && (item.status ? 'Đã duyệt' : 'Đang chờ duyệt').includes(searchKey))
            );
            $('#appointmentList').html(generateAppointmentHtml(filtered));
        }

        // Tải dữ liệu từ API
        $.ajax({
            url: '/api/appointments/my-appointments',
            method: 'GET',
            success: function (response) {
                if (response && response.result) {
                    appointmentsData = response.result;
                    $('#appointmentList').html(generateAppointmentHtml(appointmentsData));
                } else {
                    $('#appointmentList').html('<p class="text-muted">Không thể tải lịch hẹn.</p>');
                }
            },
            error: function (xhr) {
                console.log('Error:', xhr.responseJSON);
                $('#appointmentList').html('<p class="text-muted">Lỗi khi tải lịch hẹn.</p>');
            }
        });

        // Xử lý tìm kiếm
        $('#searchButton').on('click', function () {
            const searchKey = $('#searchInput').val().trim().toLowerCase();
            filterAppointments(searchKey);
        });

        $('#searchInput').on('keypress', function (e) {
            if (e.key === 'Enter') {
                const searchKey = $(this).val().trim().toLowerCase();
                filterAppointments(searchKey);
            }
        });
    });