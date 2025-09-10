package com.ontime.office.scheduler;

import com.ontime.office.model.Employee;
import com.ontime.office.service.EmployeeService;
import com.ontime.office.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BirthdayScheduler {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    // ✅ Runs every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void sendBirthdayEmails() {
        List<Employee> birthdayEmployees = employeeService.getTodayBirthdayEmployees();

        if (birthdayEmployees.isEmpty()) {
            System.out.println("✅ No birthdays today.");
            return;
        }

        for (Employee employee : birthdayEmployees) {
            String subject = "🎉 Happy Birthday, " + employee.getName() + "!";
            String message = buildBirthdayMessage(employee.getName());

            // ✅ Send email with image (from resources/static/)
            emailService.sendEmailWithImage(
                    employee.getEmail(),
                    subject,
                    message,
                    "static/birthday.webp"
            );

            System.out.println("🎁 Birthday email with image sent to: " +
                    employee.getName() + " (" + employee.getEmail() + ")");
        }

        System.out.println("✅ Total birthday emails sent: " + birthdayEmployees.size());
    }

    // ✅ Message template builder
    private String buildBirthdayMessage(String name) {
        return "Dear " + name + ",<br><br>" +
                "🎉 Wishing you a day filled with love, laughter, and joy!<br>" +
                "May this year bring you closer to your dreams and goals.<br><br>" +
                "Enjoy your special day to the fullest! 🎂🎈<br><br>" +
                "<b>Warm wishes,</b><br>Team OnTime Office";
    }
}
