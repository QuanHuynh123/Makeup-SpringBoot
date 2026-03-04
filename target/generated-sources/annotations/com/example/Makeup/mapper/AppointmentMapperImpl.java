package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.entity.TypeMakeup;
import com.example.Makeup.entity.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-20T22:10:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentDTO toAppointmentDTO(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setUserId( appointmentUserId( appointment ) );
        appointmentDTO.setTypeMakeupId( appointmentTypeMakeupId( appointment ) );
        appointmentDTO.setStaffId( appointmentStaffId( appointment ) );
        appointmentDTO.setId( appointment.getId() );
        appointmentDTO.setStartTime( appointment.getStartTime() );
        appointmentDTO.setEndTime( appointment.getEndTime() );
        appointmentDTO.setPrice( appointment.getPrice() );
        appointmentDTO.setMakeupDate( appointment.getMakeupDate() );
        appointmentDTO.setStatus( appointment.isStatus() );
        appointmentDTO.setCreatedAt( appointment.getCreatedAt() );
        appointmentDTO.setUpdatedAt( appointment.getUpdatedAt() );

        return appointmentDTO;
    }

    @Override
    public Appointment toAppointmentEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setUser( appointmentDTOToUser( appointmentDTO ) );
        appointment.setTypeMakeup( appointmentDTOToTypeMakeup( appointmentDTO ) );
        appointment.setStaff( appointmentDTOToStaff( appointmentDTO ) );
        appointment.setCreatedAt( appointmentDTO.getCreatedAt() );
        appointment.setUpdatedAt( appointmentDTO.getUpdatedAt() );
        appointment.setId( appointmentDTO.getId() );
        appointment.setStartTime( appointmentDTO.getStartTime() );
        appointment.setEndTime( appointmentDTO.getEndTime() );
        appointment.setMakeupDate( appointmentDTO.getMakeupDate() );
        appointment.setPrice( appointmentDTO.getPrice() );
        appointment.setStatus( appointmentDTO.isStatus() );

        return appointment;
    }

    private UUID appointmentUserId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        User user = appointment.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int appointmentTypeMakeupId(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        TypeMakeup typeMakeup = appointment.getTypeMakeup();
        if ( typeMakeup == null ) {
            return 0;
        }
        int id = typeMakeup.getId();
        return id;
    }

    private UUID appointmentStaffId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Staff staff = appointment.getStaff();
        if ( staff == null ) {
            return null;
        }
        UUID id = staff.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User appointmentDTOToUser(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( appointmentDTO.getUserId() );

        return user;
    }

    protected TypeMakeup appointmentDTOToTypeMakeup(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        TypeMakeup typeMakeup = new TypeMakeup();

        typeMakeup.setId( appointmentDTO.getTypeMakeupId() );

        return typeMakeup;
    }

    protected Staff appointmentDTOToStaff(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Staff staff = new Staff();

        staff.setId( appointmentDTO.getStaffId() );

        return staff;
    }
}
