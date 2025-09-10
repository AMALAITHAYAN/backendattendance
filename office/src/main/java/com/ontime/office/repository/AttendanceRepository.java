package com.ontime.office.repository;

import com.ontime.office.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // ✅ Find all records for a specific employee
    List<Attendance> findByEmployeeId(Long employeeId);

    // ✅ Find all records for a specific date
    List<Attendance> findByDate(LocalDate date);

    // ✅ Find a record for an employee on a specific date
    List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    // ✅ Check if an attendance entry exists for an employee on a specific date
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
