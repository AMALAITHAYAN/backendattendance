package com.ontime.office.service;

import com.ontime.office.model.Attendance;
import com.ontime.office.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Attendance> getReport(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        if (employeeId != null) {
            return reportRepository.findByEmployeeIdAndDateBetween(employeeId, fromDate, toDate);
        } else {
            return reportRepository.findByDateBetween(fromDate, toDate);
        }
    }
}
