package com.example.Makeup.dto.response;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeekAppointmentsDTO {
  private int weekNumber; // Volume of the week in the year
  private Date startDate;
  private Date endDate;
  private List<AppointmentsAdminResponse> appointments;
}
