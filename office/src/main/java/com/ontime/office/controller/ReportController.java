package com.ontime.office.controller;

import com.ontime.office.model.Attendance;
import com.ontime.office.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://attendencefrontend.vercel.app"
})
public class ReportController {

    @Autowired
    private ReportService reportService;

    // ✅ Get attendance report for date range (optionally filtered by employee ID)
    @GetMapping
    public List<Attendance> getReport(
            @RequestParam(required = false) Long employeeId,
            @RequestParam String fromDate,
            @RequestParam String toDate
    ) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);
        return reportService.getReport(employeeId, from, to);
    }
}
