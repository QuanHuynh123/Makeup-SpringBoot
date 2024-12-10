package com.example.Makeup.dto;

import java.util.Date;
import java.util.List;

public class WeekAppointmentsDTO {
    private int weekNumber; // Tuần trong tháng
    private Date startDate; // Ngày bắt đầu của tuần
    private Date endDate;   // Ngày kết thúc của tuần
    private List<AppointmentDetailDTO> appointments; // Các lịch hẹn trong tuần

    public WeekAppointmentsDTO(int weekNumber, Date startDate, Date endDate, List<AppointmentDetailDTO> appointments) {
        this.weekNumber = weekNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appointments = appointments;
    }

    // Getters và Setters
    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<AppointmentDetailDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDetailDTO> appointments) {
        this.appointments = appointments;
    }
}
