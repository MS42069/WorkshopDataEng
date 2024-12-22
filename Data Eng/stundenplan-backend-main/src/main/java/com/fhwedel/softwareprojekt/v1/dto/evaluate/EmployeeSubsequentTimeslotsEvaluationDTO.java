package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.SubsequentTimeslotsConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * DTO for evaluating subsequent timeslots constraints of an employee.
 */
@Builder
@Getter
@Schema(description = "DTO for evaluating subsequent timeslots constraints of an employee")
public class EmployeeSubsequentTimeslotsEvaluationDTO {

    @Schema(description = "The week of the evaluation")
    private int week;

    @Schema(description = "The score for the evaluation")
    private float score;

    @Schema(description = "The subsequent timeslots constraint being evaluated")
    private SubsequentTimeslotsConstraintResDTO constraint;

    @Schema(description = "The days of the week where the constraint harms the schedule")
    private Set<DayOfWeek> harmedDays;
}
