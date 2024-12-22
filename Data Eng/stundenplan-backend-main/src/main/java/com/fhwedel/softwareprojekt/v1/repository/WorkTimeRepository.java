package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID> {
    List<WorkTime> findByEmployeeId(UUID employeeId);

    List<WorkTime> findByEmployeeAbbreviation(String abbreviation);
}
