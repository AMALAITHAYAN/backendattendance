package com.ontime.office.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String mobileNumber;

    @Column(length = 20)
    private String emergencyContactNumber;

    @Column(length = 50)
    private String emergencyRelation;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String designation;

    @Column(length = 50)
    private String role;

    @Column(unique = true, length = 50)
    private String username;

    private LocalDate dob;

    private LocalDate joiningDate;

    @Column(length = 255)
    private String shiftDetails;

    private Double salary;

    @Column(length = 50)
    private String status;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(unique = true, length = 50)
    private String employeeCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShiftAssignment> shiftAssignments = new ArrayList<>();

    // Constructors
    public Employee() {
    }

    public Employee(Long id, String name, String email, String password, String mobileNumber,
                    String emergencyContactNumber, String emergencyRelation, String company,
                    String department, String designation, String role, String username,
                    LocalDate dob, LocalDate joiningDate, String shiftDetails, Double salary,
                    String status, LocalDateTime lastLogin, String employeeCode,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.emergencyContactNumber = emergencyContactNumber;
        this.emergencyRelation = emergencyRelation;
        this.company = company;
        this.department = department;
        this.designation = designation;
        this.role = role;
        this.username = username;
        this.dob = dob;
        this.joiningDate = joiningDate;
        this.shiftDetails = shiftDetails;
        this.salary = salary;
        this.status = status;
        this.lastLogin = lastLogin;
        this.employeeCode = employeeCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }

    public String getEmergencyRelation() { return emergencyRelation; }
    public void setEmergencyRelation(String emergencyRelation) { this.emergencyRelation = emergencyRelation; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public String getShiftDetails() { return shiftDetails; }
    public void setShiftDetails(String shiftDetails) { this.shiftDetails = shiftDetails; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<ShiftAssignment> getShiftAssignments() { return shiftAssignments; }
    public void setShiftAssignments(List<ShiftAssignment> shiftAssignments) { this.shiftAssignments = shiftAssignments; }

    // Lifecycle Hooks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(username, employee.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username);
    }

    // toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }
}
