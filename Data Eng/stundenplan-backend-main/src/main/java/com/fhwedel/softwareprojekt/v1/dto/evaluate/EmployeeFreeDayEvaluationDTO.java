package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.FreeDayConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO for evaluating free day constraints of an employee.
 */
@Getter
@Builder
@Schema(description = "DTO for evaluating free day constraints of an employee")
public class EmployeeFreeDayEvaluationDTO {
    @Schema(description = "The week of the evaluation")
    private int week;

    @Schema(description = "The score for the evaluation")
    private float score;

    @Schema(description = "The constraint being evaluated")
    private FreeDayConstraintResDTO constraint;
}
