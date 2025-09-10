package com.ontime.office.dto;

public class LoginResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String username;
    private String dob;
    private String joiningDate;
    private String shiftDetails;
    private Double salary;
    private String status;
    private String lastLogin;

    // ✅ Full constructor
    public LoginResponseDTO(Long id, String name, String email, String role,
                            String username, String dob, String joiningDate,
                            String shiftDetails, Double salary, String status, String lastLogin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.username = username;
        this.dob = dob;
        this.joiningDate = joiningDate;
        this.shiftDetails = shiftDetails;
        this.salary = salary;
        this.status = status;
        this.lastLogin = lastLogin;
    }

    // ✅ Partial constructor (4 fields)
    public LoginResponseDTO(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getShiftDetails() {
        return shiftDetails;
    }

    public void setShiftDetails(String shiftDetails) {
        this.shiftDetails = shiftDetails;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
