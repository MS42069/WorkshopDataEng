package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeLunchBreakConstraint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeLunchBreakConstraintRepository
        extends JpaRepository<EmployeeLunchBreakConstraint, UUID> {

    Optional<EmployeeLunchBreakConstraint> findByEmployeeAbbreviation(String abbreviation);

    void deleteByEmployeeAbbreviation(String abbreviation);
}
