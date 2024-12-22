package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.ConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.ConstraintResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service class for managing various constraint-related operations. */
@Service
@RequiredArgsConstructor
public class ConstraintService {

    /** Service for managing LunchBreakConstraint operations. */
    private final LunchBreakConstraintService lunchBreakConstraintService;

    /** Service for managing FreeDayConstraint operations. */
    private final FreeDayConstraintService freeDayConstraintService;

    /** Service for managing BreaksBetweenConstraint operations. */
    private final BreaksBetweenConstraintService breaksBetweenConstraintService;

    /** Service for managing CourseDistributionConstraint operations. */
    private final CourseDistributionConstraintService courseDistributionConstraintService;

    /** Service for managing SubsequentTimeslotsConstraint operations. */
    private final SubsequentTimeslotsConstraintService subsequentTimeslotsConstraintService;

    /**
     * Retrieves constraint information for a given preference profile.
     *
     * @param employeeAbbreviation The abbreviation of the employee's preference profile.
     * @return A DTO containing constraint information.
     */
    public ConstraintResDTO getConstraintsForPreferenceProfile(String employeeAbbreviation) {
        return ConstraintResDTO.builder()
                .lunchBreak(lunchBreakConstraintService.getResDTO(employeeAbbreviation))
                .freeDay(freeDayConstraintService.getResDTO(employeeAbbreviation))
                .breaksBetween(breaksBetweenConstraintService.getResDTO(employeeAbbreviation))
                .courseDistribution(
                        courseDistributionConstraintService.getResDTO(employeeAbbreviation))
                .subsequentTimeslots(
                        subsequentTimeslotsConstraintService.getResDTO(employeeAbbreviation))
                .build();
    }

    /**
     * Handles the saving of various constraint types.
     *
     * @param constraintDTO The DTO containing constraint information to be saved.
     */
    public void handleSave(ConstraintReqDTO constraintDTO) {
        lunchBreakConstraintService.handleSave(constraintDTO.getLunchBreak());
        freeDayConstraintService.handleSave(constraintDTO.getFreeDay());
        breaksBetweenConstraintService.handleSave(constraintDTO.getBreaksBetween());
        subsequentTimeslotsConstraintService.handleSave(constraintDTO.getSubsequentTimeslots());
        courseDistributionConstraintService.handleSave(constraintDTO.getCourseDistribution());
    }
}
