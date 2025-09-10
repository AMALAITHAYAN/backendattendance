package com.ontime.office.controller;

import com.ontime.office.dto.LoginDTO;
import com.ontime.office.dto.LoginResponseDTO;
import com.ontime.office.dto.ShiftAssignmentRequest;
import com.ontime.office.dto.ShiftDTO;
import com.ontime.office.model.Employee;
import com.ontime.office.model.ShiftAssignment;
import com.ontime.office.repository.EmployeeRepository;
import com.ontime.office.repository.ShiftAssignmentRepository;
import com.ontime.office.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://attendencefrontend.vercel.app"
})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftAssignmentRepository shiftAssignmentRepository;

    // --- Employee CRUD ---

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        try {
            Employee saved = employeeService.addEmployee(employee);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Employee employee = employeeService.login(loginDTO.getEmail(), loginDTO.getPassword());
            LoginResponseDTO response = new LoginResponseDTO(
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getRole(),
                    employee.getUsername(),
                    employee.getDob() != null ? employee.getDob().toString() : null,
                    employee.getJoiningDate() != null ? employee.getJoiningDate().toString() : null,
                    employee.getShiftDetails(),
                    employee.getSalary(),
                    employee.getStatus(),
                    employee.getLastLogin() != null ? employee.getLastLogin().toString() : null
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // --- Search ---

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation
    ) {
        List<Employee> filteredEmployees = employeeService.searchEmployees(company, status, department, designation);
        return ResponseEntity.ok(filteredEmployees);
    }

    // --- Shifts ---

    @PostMapping("/shift")
    public ResponseEntity<?> assignMultipleShifts(@RequestBody List<ShiftAssignmentRequest> requests) {
        for (ShiftAssignmentRequest request : requests) {
            Optional<Employee> employeeOpt = employeeRepository.findById(request.getEmployeeId());
            if (employeeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found: " + request.getEmployeeId());
            }

            ShiftAssignment assignment = new ShiftAssignment();
            assignment.setEmployee(employeeOpt.get());
            assignment.setDate(request.getDate());
            assignment.setShift(request.getShift());

            shiftAssignmentRepository.save(assignment);
        }
        return ResponseEntity.ok("Shifts assigned successfully");
    }

    @PostMapping("/shifts/bulk")
    public ResponseEntity<?> assignShiftsBulk(@RequestBody List<ShiftAssignmentRequest> shiftRequests) {
        for (ShiftAssignmentRequest request : shiftRequests) {
            Optional<Employee> employeeOpt = employeeRepository.findById(request.getEmployeeId());
            if (employeeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found: " + request.getEmployeeId());
            }

            ShiftAssignment assignment = new ShiftAssignment();
            assignment.setEmployee(employeeOpt.get());
            assignment.setDate(request.getDate());
            assignment.setShift(request.getShift());

            shiftAssignmentRepository.save(assignment);
        }
        return ResponseEntity.ok("Shifts assigned successfully");
    }

    @GetMapping("/shift/{employeeId}")
    public ResponseEntity<?> getShiftDetailsForEmployee(@PathVariable Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        List<ShiftAssignment> assignments = shiftAssignmentRepository.findByEmployeeId(employeeId);

        List<ShiftDTO> shiftDTOs = new ArrayList<>();
        for (ShiftAssignment assignment : assignments) {
            LocalDateTime start = assignment.getDate().atTime(9, 0);
            LocalDateTime end = assignment.getDate().atTime(17, 0);
            shiftDTOs.add(new ShiftDTO(assignment.getShift(), start, end));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", employeeOpt.get().getId());
        response.put("name", employeeOpt.get().getName());
        response.put("shifts", shiftDTOs);

        return ResponseEntity.ok(response);
    }
}
