package com.example.Makeup.mapper;

import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:00+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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
        appointmentDTO.setServiceMakeupId( appointmentServiceMakeupId( appointment ) );
        appointmentDTO.setStaffId( appointmentStaffId( appointment ) );
        appointmentDTO.setId( appointment.getId() );
        appointmentDTO.setStartTime( appointment.getStartTime() );
        appointmentDTO.setEndTime( appointment.getEndTime() );
        appointmentDTO.setMakeupDate( appointment.getMakeupDate() );
        appointmentDTO.setStatus( appointment.isStatus() );

        return appointmentDTO;
    }

    @Override
    public Appointment toAppointmentEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setUser( appointmentDTOToUser( appointmentDTO ) );
        appointment.setServiceMakeup( appointmentDTOToServiceMakeup( appointmentDTO ) );
        appointment.setStaff( appointmentDTOToStaff( appointmentDTO ) );
        appointment.setId( appointmentDTO.getId() );
        appointment.setStartTime( appointmentDTO.getStartTime() );
        appointment.setEndTime( appointmentDTO.getEndTime() );
        appointment.setMakeupDate( appointmentDTO.getMakeupDate() );
        appointment.setStatus( appointmentDTO.isStatus() );

        return appointment;
    }

    private int appointmentUserId(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        User user = appointment.getUser();
        if ( user == null ) {
            return 0;
        }
        int id = user.getId();
        return id;
    }

    private int appointmentServiceMakeupId(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        ServiceMakeup serviceMakeup = appointment.getServiceMakeup();
        if ( serviceMakeup == null ) {
            return 0;
        }
        int id = serviceMakeup.getId();
        return id;
    }

    private int appointmentStaffId(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        Staff staff = appointment.getStaff();
        if ( staff == null ) {
            return 0;
        }
        int id = staff.getId();
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

    protected ServiceMakeup appointmentDTOToServiceMakeup(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        ServiceMakeup serviceMakeup = new ServiceMakeup();

        serviceMakeup.setId( appointmentDTO.getServiceMakeupId() );

        return serviceMakeup;
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
