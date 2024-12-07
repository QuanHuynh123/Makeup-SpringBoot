package com.example.Makeup.repository;

import com.example.Makeup.dto.StaffDetailDTO;
import com.example.Makeup.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer> {
    @Query("SELECT new com.example.Makeup.dto.StaffDetailDTO(" +
            "s.id, s.nameStaff, s.phone, " +
            "a.userName, r.id, a.passWord) " +  // Thêm a.password để lấy mật khẩu từ bảng Account
            "FROM Staff s " +
            "LEFT JOIN s.account a " +         // Liên kết với bảng Account
            "LEFT JOIN a.role r " +            // Liên kết với bảng Role thông qua Account
            "WHERE s.id > 0")                  // Loại bỏ Staff có id <= 0
    List<StaffDetailDTO> findAllStaffWithDetails();

    @Query("SELECT new com.example.Makeup.dto.StaffDetailDTO(" +
            "s.id, s.nameStaff, s.phone, " +
            "a.userName, r.id, a.passWord) " +  // Thêm trường mật khẩu nếu cần
            "FROM Staff s " +
            "LEFT JOIN s.account a " +
            "LEFT JOIN a.role r " +
            "WHERE s.id = :staffId")
    StaffDetailDTO findStaffDetailById(@Param("staffId") int staffId);


}