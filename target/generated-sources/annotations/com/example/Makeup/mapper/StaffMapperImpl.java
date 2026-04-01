package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Staff;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T21:04:50+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class StaffMapperImpl implements StaffMapper {

    @Override
    public StaffDTO toStaffDTO(Staff staff) {
        if ( staff == null ) {
            return null;
        }

        StaffDTO staffDTO = new StaffDTO();

        UUID id = staffAccountId( staff );
        if ( id != null ) {
            staffDTO.setAccountId( id.toString() );
        }
        staffDTO.setAppointments( appointmentListToAppointmentDTOList( staff.getAppointments() ) );
        staffDTO.setCreatedAt( staff.getCreatedAt() );
        staffDTO.setId( staff.getId() );
        staffDTO.setNameStaff( staff.getNameStaff() );
        staffDTO.setPhone( staff.getPhone() );
        staffDTO.setUpdatedAt( staff.getUpdatedAt() );

        return staffDTO;
    }

    @Override
    public Staff toStaffEntity(StaffDTO staffDTO) {
        if ( staffDTO == null ) {
            return null;
        }

        Staff staff = new Staff();

        staff.setAccount( staffDTOToAccount( staffDTO ) );
        staff.setCreatedAt( staffDTO.getCreatedAt() );
        staff.setUpdatedAt( staffDTO.getUpdatedAt() );
        staff.setAppointments( appointmentDTOListToAppointmentList( staffDTO.getAppointments() ) );
        staff.setId( staffDTO.getId() );
        staff.setNameStaff( staffDTO.getNameStaff() );
        staff.setPhone( staffDTO.getPhone() );

        return staff;
    }

    private UUID staffAccountId(Staff staff) {
        if ( staff == null ) {
            return null;
        }
        Account account = staff.getAccount();
        if ( account == null ) {
            return null;
        }
        UUID id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected AppointmentDTO appointmentToAppointmentDTO(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setCreatedAt( appointment.getCreatedAt() );
        appointmentDTO.setEndTime( appointment.getEndTime() );
        appointmentDTO.setId( appointment.getId() );
        appointmentDTO.setMakeupDate( appointment.getMakeupDate() );
        appointmentDTO.setPrice( appointment.getPrice() );
        appointmentDTO.setStartTime( appointment.getStartTime() );
        appointmentDTO.setStatus( appointment.isStatus() );
        appointmentDTO.setUpdatedAt( appointment.getUpdatedAt() );

        return appointmentDTO;
    }

    protected List<AppointmentDTO> appointmentListToAppointmentDTOList(List<Appointment> list) {
        if ( list == null ) {
            return null;
        }

        List<AppointmentDTO> list1 = new ArrayList<AppointmentDTO>( list.size() );
        for ( Appointment appointment : list ) {
            list1.add( appointmentToAppointmentDTO( appointment ) );
        }

        return list1;
    }

    protected Account staffDTOToAccount(StaffDTO staffDTO) {
        if ( staffDTO == null ) {
            return null;
        }

        Account account = new Account();

        if ( staffDTO.getAccountId() != null ) {
            account.setId( UUID.fromString( staffDTO.getAccountId() ) );
        }

        return account;
    }

    protected Appointment appointmentDTOToAppointment(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setCreatedAt( appointmentDTO.getCreatedAt() );
        appointment.setUpdatedAt( appointmentDTO.getUpdatedAt() );
        appointment.setEndTime( appointmentDTO.getEndTime() );
        appointment.setId( appointmentDTO.getId() );
        appointment.setMakeupDate( appointmentDTO.getMakeupDate() );
        appointment.setPrice( appointmentDTO.getPrice() );
        appointment.setStartTime( appointmentDTO.getStartTime() );
        appointment.setStatus( appointmentDTO.isStatus() );

        return appointment;
    }

    protected List<Appointment> appointmentDTOListToAppointmentList(List<AppointmentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Appointment> list1 = new ArrayList<Appointment>( list.size() );
        for ( AppointmentDTO appointmentDTO : list ) {
            list1.add( appointmentDTOToAppointment( appointmentDTO ) );
        }

        return list1;
    }
}
