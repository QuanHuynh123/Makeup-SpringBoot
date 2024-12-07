package com.example.Makeup.service;
import com.example.Makeup.dto.StaffDTO;
import com.example.Makeup.dto.StaffDetailDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.mapper.StaffMapper;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StaffMapper staffMapper;

    public List<StaffDTO> getAllStaff() {
        // Truy vấn tất cả các nhân viên từ repository
        List<Staff> staffList = staffRepository.findAll();

        // Lọc bỏ các nhân viên có id <= 0
        staffList = staffList.stream()
                .filter(staff -> staff.getId() > 0)
                .collect(Collectors.toList());

        // Chuyển đổi danh sách Staff sang StaffDTO
        return staffMapper.toStaffDTOList(staffList);
    }

    public List<StaffDetailDTO> getAllStaffDetail() {
        // Truy vấn danh sách từ repository
        return staffRepository.findAllStaffWithDetails();
    }

    public Optional<Staff> getStaffById(int id) {
        return staffRepository.findById(id);
    }
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    public Staff updateStaff(int id, Staff staffDetails) {
        return staffRepository.findById(id).map(staff -> {
            staff.setNameStaff(staffDetails.getNameStaff());
            staff.setPhone(staffDetails.getPhone());
            return staffRepository.save(staff);
        }).orElseThrow(() -> new RuntimeException("Staff not found with id " + id));
    }

    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }

    public void updateAppointmentsStaffId(int staffId) {
        // Lấy Staff có id = 0 từ cơ sở dữ liệu
        Staff defaultStaff = staffRepository.findById(0).orElseThrow(() -> new RuntimeException("Staff with ID 0 not found"));

        // Cập nhật tất cả các lịch hẹn có staffId là nhân viên bị xóa
        List<Appointment> appointments = appointmentRepository.findByStaffId(staffId);

        // Duyệt qua các lịch hẹn và cập nhật staff thành staff có id = 0
        for (Appointment appointment : appointments) {
            appointment.setStaff(defaultStaff);  // Thay staff hiện tại bằng staff có id = 0
            appointmentRepository.save(appointment);  // Lưu lại các thay đổi
        }
    }

    public StaffDetailDTO getStaffDetailById(int staffId) {
        // Tìm nhân viên theo ID và trả về thông tin chi tiết
        return staffRepository.findStaffDetailById(staffId);
    }

    public Staff updateStaff(Staff staff) {
        return staffRepository.save(staff); // Sử dụng save() để cập nhật nếu đối tượng đã tồn tại
    }

}