package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeFreeDayConstraint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeFreeDayConstraintRepository
        extends JpaRepository<EmployeeFreeDayConstraint, UUID> {
    Optional<EmployeeFreeDayConstraint> findByEmployeeAbbreviation(String employeeAbbreviation);
}
