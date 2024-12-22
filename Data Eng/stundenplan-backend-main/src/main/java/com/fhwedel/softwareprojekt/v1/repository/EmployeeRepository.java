package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByTimetable(Timetable timetable);

    Optional<Employee> findFirstByAbbreviation(String abbreviation);

    List<Employee> findByAbbreviation(String abbreviation);

    void deleteByTimetableId(UUID timetableId);
}
