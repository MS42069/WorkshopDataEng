package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeSubsequentTimeslotsConstraint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSubsequentTimeslotsConstraintRepository
        extends JpaRepository<EmployeeSubsequentTimeslotsConstraint, UUID> {

    Optional<EmployeeSubsequentTimeslotsConstraint> findByEmployeeAbbreviation(
            String employeeAbbreviation);
}
