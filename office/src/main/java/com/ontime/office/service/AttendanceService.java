package com.ontime.office.service;

import com.ontime.office.dto.AttendanceReportDTO;
import com.ontime.office.model.Attendance;
import com.ontime.office.repository.AttendanceRepository;
import com.ontime.office.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // --- NEW: Inject JavaMailSender for sending emails ---
    @Autowired
    private JavaMailSender mailSender;

    public Attendance checkIn(Long employeeId) {
        // 🔐 Check if employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee with ID " + employeeId + " does not exist.");
        }

        LocalDate today = LocalDate.now();

        // ✅ Check if already checked in today
        if (attendanceRepository.existsByEmployeeIdAndDate(employeeId, today)) {
            throw new RuntimeException("Employee has already checked in today.");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalTime.now());

        Attendance saved = attendanceRepository.save(attendance);

        // --- NEW: Send check-in email notification ---
        sendCheckInEmail(employeeId, saved);

        return saved;
    }

    public boolean hasCheckedInToday(Long employeeId, LocalDate date) {
        return attendanceRepository.existsByEmployeeIdAndDate(employeeId, date);
    }

    public List<Attendance> getYesterdayAttendance() {
        return attendanceRepository.findByDate(LocalDate.now().minusDays(1));
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public Attendance checkOut(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow();
        attendance.setCheckOutTime(LocalTime.now());
        Attendance saved = attendanceRepository.save(attendance);

        // --- NEW: Send check-out email notification ---
        sendCheckOutEmail(attendance.getEmployeeId(), saved);

        return saved;
    }

    public List<AttendanceReportDTO> getAttendanceReport() {
        List<Attendance> attendanceList = attendanceRepository.findAll();

        return attendanceList.stream().map(a -> {
            String employeeName = employeeRepository.findById(a.getEmployeeId())
                    .map(e -> e.getName())
                    .orElse("Unknown");

            return new AttendanceReportDTO(
                    a.getEmployeeId(),
                    employeeName,
                    a.getDate(),
                    a.getCheckInTime(),
                    a.getCheckOutTime()
            );
        }).toList();
    }

    public List<Attendance> getTodayAttendance() {
        return attendanceRepository.findByDate(LocalDate.now());
    }

    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    // --- NEW PRIVATE METHODS FOR SENDING EMAILS ---

    private void sendCheckInEmail(Long employeeId, Attendance attendance) {
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(employee.getEmail());
            message.setSubject("Check-In Confirmation");
            message.setText(String.format(
                    "Hi %s,\n\nYou have successfully checked in on %s at %s.\n\nBest regards,\nOffice Attendance System",
                    employee.getName(),
                    attendance.getDate(),
                    attendance.getCheckInTime()
            ));
            mailSender.send(message);
        });
    }

    private void sendCheckOutEmail(Long employeeId, Attendance attendance) {
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(employee.getEmail());
            message.setSubject("Check-Out Confirmation");
            message.setText(String.format(
                    "Hi %s,\n\nYou have successfully checked out on %s at %s.\n\nBest regards,\nOffice Attendance System",
                    employee.getName(),
                    attendance.getDate(),
                    attendance.getCheckOutTime()
            ));
            mailSender.send(message);
        });
    }
}
