package com.ontime.office.repository;

import com.ontime.office.model.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    List<ShiftAssignment> findByEmployeeId(Long employeeId);

}
