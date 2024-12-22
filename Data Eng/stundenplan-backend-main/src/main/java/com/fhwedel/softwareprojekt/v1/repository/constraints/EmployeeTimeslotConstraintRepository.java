package com.fhwedel.softwareprojekt.v1.repository.constraints;

import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintType;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeTimeslotConstraintRepository
        extends JpaRepository<EmployeeTimeslotConstraint, UUID> {
    List<EmployeeTimeslotConstraint> findByConstraintType(ConstraintType constraintType);

    List<EmployeeTimeslotConstraint> findByEmployeeAbbreviation(String employeeAbbreviation);

    List<EmployeeTimeslotConstraint> findByEmployeeAbbreviationAndConstraintType(
            String employeeAbbreviation, ConstraintType constraintType);

    List<EmployeeTimeslotConstraint> findByEmployeeAbbreviationAndConstraintTypeAndIsAccepted(
            String employeeAbbreviation, ConstraintType constraintType, Boolean isAccepted);

    List<EmployeeTimeslotConstraint> findByEmployeeAbbreviationAndConstraintTypeAndConstraintValueAndIsAccepted(
            String employeeAbbreviation, ConstraintType constraintType, ConstraintValue constraintValue,
            Boolean isAccepted);

    default List<EmployeeTimeslotConstraint> findHardConstraintsNotWantedForEmployee(String employeeAbbreviation) {
        return findByEmployeeAbbreviationAndConstraintTypeAndConstraintValueAndIsAccepted(employeeAbbreviation,
                ConstraintType.HARD, ConstraintValue.NOT_WANTED, Boolean.TRUE);
    }

    Optional<EmployeeTimeslotConstraint>
    findByEmployeeAbbreviationAndTimeslotIndexAndStartTimeAndEndTimeAndWeekday(
            String employeeAbbreviation,
            Integer timeslotIndex,
            LocalTime startTime,
            LocalTime endTime,
            DayOfWeek weekday);
}
