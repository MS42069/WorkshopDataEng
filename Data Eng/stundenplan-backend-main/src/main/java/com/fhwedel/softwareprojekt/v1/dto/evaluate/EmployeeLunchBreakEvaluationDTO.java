package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.LunchBreakConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * DTO for evaluating lunch break constraints of an employee.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for evaluating lunch break constraints of an employee")
public class EmployeeLunchBreakEvaluationDTO {
    @Schema(description = "The week of the evaluation")
    private int week;

    @Schema(description = "The score for the evaluation")
    private float score;

    @Schema(description = "The lunch break constraint being evaluated")
    private LunchBreakConstraintResDTO constraint;

    @Schema(description = "The days of the week where the constraint harms the schedule")
    private Set<DayOfWeek> harmedDays;
}
