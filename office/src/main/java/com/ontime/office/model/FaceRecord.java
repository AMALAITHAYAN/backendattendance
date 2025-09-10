package com.ontime.office.model;

import jakarta.persistence.*;

@Entity
@Table(name = "face_records")
public class FaceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(length = 255)
    private String faceImagePath; // Optional: can store path or URL of face image

    // ✅ No-arg constructor
    public FaceRecord() {
    }

    // ✅ All-arg constructor (excluding ID since it’s auto-generated)
    public FaceRecord(Long employeeId, String faceImagePath) {
        this.employeeId = employeeId;
        this.faceImagePath = faceImagePath;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFaceImagePath() {
        return faceImagePath;
    }

    public void setFaceImagePath(String faceImagePath) {
        this.faceImagePath = faceImagePath;
    }

    // ✅ Optional: toString()
    @Override
    public String toString() {
        return "FaceRecord{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", faceImagePath='" + faceImagePath + '\'' +
                '}';
    }
}
