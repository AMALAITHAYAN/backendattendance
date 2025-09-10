package com.ontime.office.service;

import com.ontime.office.dto.LoginDTO;
import com.ontime.office.dto.LoginResponseDTO;
import com.ontime.office.model.Employee;
import com.ontime.office.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // DTO-based login (returns detailed response)
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(loginDTO.getEmail());

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            if (employee.getPassword().equals(loginDTO.getPassword())) {
                // Update lastLogin
                employee.setLastLogin(LocalDateTime.now());
                employeeRepository.save(employee);

                // Format last login date
                String formattedLastLogin = (employee.getLastLogin() != null)
                        ? employee.getLastLogin().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))
                        : "N/A";

                return new LoginResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getRole(),
                        employee.getUsername(),
                        employee.getDob() != null ? employee.getDob().toString() : "N/A",
                        employee.getJoiningDate() != null ? employee.getJoiningDate().toString() : "N/A",
                        employee.getShiftDetails(),
                        employee.getSalary(),
                        employee.getStatus(),
                        formattedLastLogin
                );
            } else {
                throw new RuntimeException("Invalid password.");
            }
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    // Simple email/password login (returns Employee object)
    public Employee login(String email, String password) {
        Employee employee = employeeRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        employee.setLastLogin(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {

        employeeRepository.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new RuntimeException("An employee with this email already exists");
        });

        employeeRepository.findByNameAndEmail(employee.getName(), employee.getEmail()).ifPresent(e -> {
            throw new RuntimeException("An employee with this name and email already exists");
        });

        // Generate unique employeeCode
        long count = employeeRepository.count() + 1;
        String code = String.format("EMP%03d", count);
        while (employeeRepository.findByEmployeeCode(code).isPresent()) {
            count++;
            code = String.format("EMP%03d", count);
        }
        employee.setEmployeeCode(code);

        // Default values
        if (!StringUtils.hasText(employee.getShiftDetails())) {
            employee.setShiftDetails("");
        }
        if (employee.getStatus() == null) {
            employee.setStatus("Active");
        }

        employee.setMobileNumber(employee.getMobileNumber());
        employee.setEmergencyContactNumber(employee.getEmergencyContactNumber());
        employee.setEmergencyRelation(employee.getEmergencyRelation());

        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        return employeeRepository.save(employee);
    }
    public List<Employee> getTodayBirthdayEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();

        LocalDate today = LocalDate.now();

        return allEmployees.stream()
                .filter(emp -> emp.getDob() != null &&
                        emp.getDob().getDayOfMonth() == today.getDayOfMonth() &&
                        emp.getDob().getMonthValue() == today.getMonthValue())
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setRole(employeeDetails.getRole());

        if (StringUtils.hasText(employeeDetails.getEmployeeCode())) {
            employee.setEmployeeCode(employeeDetails.getEmployeeCode());
        }
        if (StringUtils.hasText(employeeDetails.getPassword())) {
            employee.setPassword(employeeDetails.getPassword());
        }
        if (StringUtils.hasText(employeeDetails.getShiftDetails())) {
            employee.setShiftDetails(employeeDetails.getShiftDetails());
        }

        if (employeeDetails.getDob() != null) employee.setDob(employeeDetails.getDob());
        if (employeeDetails.getJoiningDate() != null) employee.setJoiningDate(employeeDetails.getJoiningDate());
        if (employeeDetails.getSalary() != null) employee.setSalary(employeeDetails.getSalary());
        if (employeeDetails.getStatus() != null) employee.setStatus(employeeDetails.getStatus());
        if (employeeDetails.getUsername() != null) employee.setUsername(employeeDetails.getUsername());
        if (StringUtils.hasText(employeeDetails.getMobileNumber())) {
            employee.setMobileNumber(employeeDetails.getMobileNumber());
        }
        if (StringUtils.hasText(employeeDetails.getEmergencyContactNumber())) {
            employee.setEmergencyContactNumber(employeeDetails.getEmergencyContactNumber());
        }
        if (StringUtils.hasText(employeeDetails.getEmergencyRelation())) {
            employee.setEmergencyRelation(employeeDetails.getEmergencyRelation());
        }

        employee.setUpdatedAt(LocalDateTime.now());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
    public List<Employee> searchEmployees(String company, String status, String department, String designation) {
        // Default to empty string if null
        if (company == null) company = "";
        if (status == null) status = "";
        if (department == null) department = "";
        if (designation == null) designation = "";

        return employeeRepository.findByCompanyContainingIgnoreCaseAndStatusContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(
                company, status, department, designation
        );
    }

}
