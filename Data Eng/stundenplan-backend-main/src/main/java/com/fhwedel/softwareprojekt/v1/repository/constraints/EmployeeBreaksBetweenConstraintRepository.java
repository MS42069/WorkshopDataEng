package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeBreaksBetweenConstraint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeBreaksBetweenConstraintRepository
        extends JpaRepository<EmployeeBreaksBetweenConstraint, UUID> {

    Optional<EmployeeBreaksBetweenConstraint> findByEmployeeAbbreviation(
            String employeeAbbreviation);

    boolean existsByEmployeeAbbreviation(String employeeAbbreviation);

    void deleteByEmployeeAbbreviation(String employeeAbbreviation);
}
