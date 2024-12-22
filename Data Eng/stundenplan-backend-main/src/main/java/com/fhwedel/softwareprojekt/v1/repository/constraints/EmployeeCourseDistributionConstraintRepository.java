package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeCourseDistributionConstraint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCourseDistributionConstraintRepository
        extends JpaRepository<EmployeeCourseDistributionConstraint, UUID> {

    Optional<EmployeeCourseDistributionConstraint> findByEmployeeAbbreviation(
            String employeeAbbreviation);
}
