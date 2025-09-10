package com.ontime.office.repository;

import com.ontime.office.model.FaceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRecordRepository extends JpaRepository<FaceRecord, Long> {
    // Optional: add findByEmployeeId or other queries if needed
    FaceRecord findByEmployeeId(Long employeeId);
}
