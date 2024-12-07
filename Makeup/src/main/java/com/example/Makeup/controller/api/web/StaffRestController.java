package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.AccountDTO;
import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.StaffDTO;
import com.example.Makeup.dto.StaffDetailDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.mapper.AccountMapper;
import com.example.Makeup.mapper.StaffMapper;
import com.example.Makeup.service.AccountService;
import com.example.Makeup.service.RoleService;
import com.example.Makeup.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @GetMapping
    public List<StaffDTO> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/staffDetail")
    public List<StaffDetailDTO> getAllStaffDetail() {
        return staffService.getAllStaffDetail();
    }

    @GetMapping("/staffDetail/{staffId}")
    public StaffDetailDTO getStaffDetailById(@PathVariable int staffId) {
        return staffService.getStaffDetailById(staffId);
    }


    // Lấy thông tin nhân viên theo ID, trả về StaffDTO
    @GetMapping("/{id}")

    public ResponseEntity<StaffDTO> getStaffById(@PathVariable int id) {
        return staffService.getStaffById(id)
                .map(staff -> ResponseEntity.ok(staffMapper.toStaffDTO(staff)))
                .orElse(ResponseEntity.notFound().build());
    }

    //     Thêm nhân viên, trả về StaffDTO
    @PostMapping
    public StaffDTO addStaff(@RequestBody StaffDTO staffDTO) {
        Staff staff = staffMapper.toStaffEntity(staffDTO); // Chuyển StaffDTO thành Staff
        Staff savedStaff = staffService.addStaff(staff);
        return staffMapper.toStaffDTO(savedStaff); // Chuyển Staff thành StaffDTO và trả về
    }

    @PostMapping("/staffDetail")
    public ResponseEntity<ApiResponse<StaffDTO>> addStaff(@RequestBody StaffDetailDTO staffDetailDTO) {
        // Kiểm tra xem username đã tồn tại chưa
        if (accountService.isUsernameExists(staffDetailDTO.getUsername())) {
            // Trả về ApiResponse với mã lỗi và thông báo lỗi
            return ResponseEntity.badRequest().body(
                    ApiResponse.<StaffDTO>builder()
                            .code(400)  // Mã lỗi, bạn có thể thay đổi tùy theo mã lỗi của hệ thống
                            .message("Tên người dùng đã tồn tại")
                            .result(null)  // Trả về null cho result
                            .build()
            );
        }

        // Chuyển StaffDetailDTO thành StaffDTO
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(staffDetailDTO.getId());
        staffDTO.setNameStaff(staffDetailDTO.getNameStaff());
        staffDTO.setPhone(staffDetailDTO.getPhone());

        // Chuyển StaffDTO thành Staff entity
        Staff staff = staffMapper.toStaffEntity(staffDTO);

        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(staffDetailDTO.getPassword());

        // Tạo AccountDTO từ thông tin trong StaffDetailDTO
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserName(staffDetailDTO.getUsername());
        accountDTO.setPassWord(encodedPassword);
        accountDTO.setRoleId(staffDetailDTO.getRole());

        // Lưu tài khoản vào database
        Account savedAccount = accountService.save(accountDTO);

        // Gắn tài khoản vào nhân viên
        staff.setAccount(savedAccount);

        // Lưu nhân viên vào database
        Staff savedStaff = staffService.addStaff(staff);

        // Chuyển Staff entity thành StaffDTO và trả về
        return ResponseEntity.ok(
                ApiResponse.<StaffDTO>builder()
                        .code(200)  // Mã thành công
                        .message("Thêm nhân viên thành công")
                        .result(staffMapper.toStaffDTO(savedStaff))  // Trả về kết quả
                        .build()
        );
    }




    @PutMapping("/staffDetail/{id}")
    public ResponseEntity<ApiResponse<StaffDTO>> updateStaff(@PathVariable int id, @RequestBody StaffDetailDTO staffDetailDTO) {
        // Kiểm tra xem nhân viên có tồn tại không
        Optional<Staff> optionalStaff = staffService.getStaffById(id);
        if (optionalStaff.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.<StaffDTO>builder()
                            .code(404)  // Mã lỗi 404 cho không tìm thấy
                            .message("Không tìm thấy nhân viên với ID: " + id)
                            .result(null)  // Kết quả null vì không tìm thấy nhân viên
                            .build()
            );
        }

        Staff existingStaff = optionalStaff.get();

        // Kiểm tra xem username đã tồn tại chưa
        if (accountService.isUsernameExists(staffDetailDTO.getUsername()) &&
                !existingStaff.getAccount().getUserName().equals(staffDetailDTO.getUsername())) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<StaffDTO>builder()
                            .code(400)  // Mã lỗi 400 cho yêu cầu sai
                            .message("Tên người dùng đã tồn tại")
                            .result(null)  // Kết quả null vì lỗi này chỉ là thông báo
                            .build()
            );
        }

        // Cập nhật thông tin nhân viên
        existingStaff.setNameStaff(staffDetailDTO.getNameStaff());
        existingStaff.setPhone(staffDetailDTO.getPhone());

        // Cập nhật vai trò nếu có thay đổi
        Account account = existingStaff.getAccount();
        if (account != null) {
            if (!account.getUserName().equals(staffDetailDTO.getUsername())) {
                account.setUserName(staffDetailDTO.getUsername());
            }

            Role role = roleService.getRoleById(staffDetailDTO.getRole());
            if (role != null) {
                account.setRole(role);
            }

            accountService.save(accountMapper.toDTO(account));
        }

        // Lưu nhân viên đã được cập nhật
        Staff updatedStaff = staffService.updateStaff(existingStaff);

        // Trả về kết quả sau khi cập nhật thành công
        return ResponseEntity.ok(
                ApiResponse.<StaffDTO>builder()
                        .code(200)  // Mã thành công 200
                        .message("Cập nhật nhân viên thành công")
                        .result(staffMapper.toStaffDTO(updatedStaff))  // Kết quả là thông tin nhân viên đã cập nhật
                        .build()
        );
    }


    // Cập nhật nhân viên, trả về StaffDTO
    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable int id, @RequestBody StaffDTO staffDTO) {
        Staff staff = staffMapper.toStaffEntity(staffDTO); // Chuyển StaffDTO thành Staff
        try {
            Staff updatedStaff = staffService.updateStaff(id, staff);
            return ResponseEntity.ok(staffMapper.toStaffDTO(updatedStaff)); // Chuyển Staff thành StaffDTO và trả về
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable int id) {
        // Cập nhật các lịch hẹn có liên quan
        staffService.updateAppointmentsStaffId(id);
//        Staff st=staffService.getStaffById(id);
        // Xóa nhân viên
        staffService.deleteStaff(id);


//        accountService.delete(id);

        // Trả về mã trạng thái 204 (No Content)
        return ResponseEntity.noContent().build();

        //Chưa làm xóa account
    }

}