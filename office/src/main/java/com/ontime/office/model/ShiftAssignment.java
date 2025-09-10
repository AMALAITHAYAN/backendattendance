package com.ontime.office.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "shift_assignments")
public class ShiftAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 50, nullable = false)
    private String shift;

    // ✅ Default constructor
    public ShiftAssignment() {
    }

    // ✅ Parameterized constructor
    public ShiftAssignment(Employee employee, LocalDate date, String shift) {
        this.employee = employee;
        this.date = date;
        this.shift = shift;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    // ✅ Optional: toString()
    @Override
    public String toString() {
        return "ShiftAssignment{" +
                "id=" + id +
                ", employeeId=" + (employee != null ? employee.getId() : null) +
                ", date=" + date +
                ", shift='" + shift + '\'' +
                '}';
    }
}
