package com.ontime.office.repository;

import com.ontime.office.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ✅ Login support
    Optional<Employee> findByEmailAndPassword(String email, String password);

    // ✅ For duplicate/email checks
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByEmployeeCode(String employeeCode);
    Optional<Employee> findByNameAndEmail(String name, String email);
    Optional<Employee> findByUsername(String username);

    // ✅ For filtering in searchEmployees()
    List<Employee> findByCompanyContainingIgnoreCaseAndStatusContainingIgnoreCaseAndDepartmentContainingIgnoreCaseAndDesignationContainingIgnoreCase(
            String company, String status, String department, String designation
    );

    // ✅ For birthday filters, etc.
    List<Employee> findByDob(LocalDate dob);
}
