package com.example.Makeup.mapper;

import com.example.Makeup.dto.StaffDTO;
import com.example.Makeup.entity.Staff;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class StaffMapperImpl implements StaffMapper {

    @Override
    public StaffDTO toStaffDTO(Staff staff) {
        if ( staff == null ) {
            return null;
        }

        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setId( staff.getId() );
        staffDTO.setNameStaff( staff.getNameStaff() );
        staffDTO.setPhone( staff.getPhone() );

        return staffDTO;
    }

    @Override
    public Staff toStaffEntity(StaffDTO staffDTO) {
        if ( staffDTO == null ) {
            return null;
        }

        Staff staff = new Staff();

        staff.setId( staffDTO.getId() );
        staff.setNameStaff( staffDTO.getNameStaff() );
        staff.setPhone( staffDTO.getPhone() );

        return staff;
    }
}
