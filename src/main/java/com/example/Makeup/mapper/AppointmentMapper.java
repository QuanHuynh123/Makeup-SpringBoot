package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "typeMakeup.id", target = "typeMakeupId")
  @Mapping(source = "staff.id", target = "staffId")
  AppointmentDTO toAppointmentDTO(Appointment appointment);

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "typeMakeupId", target = "typeMakeup.id")
  @Mapping(source = "staffId", target = "staff.id")
  Appointment toAppointmentEntity(AppointmentDTO appointmentDTO);
}
