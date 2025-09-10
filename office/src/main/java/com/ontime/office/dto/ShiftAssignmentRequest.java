package com.ontime.office.dto;

import java.time.LocalDate;

public class ShiftAssignmentRequest {

    private Long employeeId;
    private LocalDate date;
    private String shift;

    // ✅ No-argument constructor
    public ShiftAssignmentRequest() {
    }

    // ✅ All-argument constructor
    public ShiftAssignmentRequest(Long employeeId, LocalDate date, String shift) {
        this.employeeId = employeeId;
        this.date = date;
        this.shift = shift;
    }

    // ✅ Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
