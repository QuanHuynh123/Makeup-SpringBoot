package com.example.Makeup.repository;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff,UUID> {

    @Query("SELECT new com.example.Makeup.dto.model.StaffDTO(" +
            "s.id, s.nameStaff, s.phone, " +
            "a.userName, r.id, a.passWord) " +
            "FROM Staff s " +
            "JOIN s.account a " +
            "JOIN a.role r")
    List<StaffDTO> findAllStaff();

    @Query("SELECT new com.example.Makeup.dto.model.StaffDTO(" +
            "s.id, s.nameStaff, s.phone, " +
            "a.userName, r.id, a.passWord) " +  // Thêm trường mật khẩu nếu cần
            "FROM Staff s " +
            "LEFT JOIN s.account a " +
            "LEFT JOIN a.role r " +
            "WHERE s.id = :staffId")
    StaffDTO findStaffDetailById(@Param("staffId") UUID staffId);

}