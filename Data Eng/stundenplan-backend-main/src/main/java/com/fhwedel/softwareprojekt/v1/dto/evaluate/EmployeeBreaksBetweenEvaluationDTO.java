package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.BreaksBetweenConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO for evaluating employee breaks between constraints.
 */
@Getter
@Builder
@Schema(description = "Represents the evaluation of employee breaks between constraints")
public class EmployeeBreaksBetweenEvaluationDTO {
    @Schema(description = "The week of the evaluation")
    private int week;
    @Schema(description = "The score of the evaluation")
    private float score;

    @Schema(description = "Details of the breaks between constraint")
    private BreaksBetweenConstraintResDTO constraint;
}
