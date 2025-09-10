package com.ontime.office.controller;

import com.ontime.office.dto.AttendanceReportDTO;
import com.ontime.office.model.Attendance;
import com.ontime.office.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://attendencefrontend.vercel.app"
})
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // ✅ Fetch report
    @GetMapping("/report")
    public ResponseEntity<List<AttendanceReportDTO>> getAttendanceReport() {
        return ResponseEntity.ok(attendanceService.getAttendanceReport());
    }

    // ✅ Check-in
    @PostMapping("/checkin/{employeeId}")
    public ResponseEntity<?> checkIn(@PathVariable Long employeeId) {
        try {
            Attendance saved = attendanceService.checkIn(employeeId);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ Check-out
    @PostMapping("/checkout/{id}")
    public Attendance checkOut(@PathVariable Long id) {
        return attendanceService.checkOut(id);
    }

    // ✅ Get today's attendance
    @GetMapping("/today")
    public List<Attendance> getTodayAttendance() {
        return attendanceService.getTodayAttendance();
    }

    // ✅ Get yesterday's attendance
    @GetMapping("/yesterday")
    public List<Attendance> getYesterdayAttendance() {
        return attendanceService.getYesterdayAttendance();
    }

    // ✅ Get by date
    @GetMapping("/date")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@RequestParam String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // format: yyyy-MM-dd
            List<Attendance> result = attendanceService.getAttendanceByDate(parsedDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ✅ Get by employee
    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployee(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceByEmployee(employeeId);
    }
}
