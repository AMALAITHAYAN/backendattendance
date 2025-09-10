package com.ontime.office.dto;

import java.time.LocalDateTime;

public class ShiftDTO {
    private String shiftType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // ✅ No-arg constructor
    public ShiftDTO() {
    }

    // ✅ All-arg constructor
    public ShiftDTO(String shiftType, LocalDateTime startTime, LocalDateTime endTime) {
        this.shiftType = shiftType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ✅ Getters and Setters
    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
