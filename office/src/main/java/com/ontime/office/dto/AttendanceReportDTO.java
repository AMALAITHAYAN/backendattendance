package com.ontime.office.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceReportDTO {
    private Long employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    // Constructor
    public AttendanceReportDTO(Long employeeId, String employeeName, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    // Getters and setters

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
