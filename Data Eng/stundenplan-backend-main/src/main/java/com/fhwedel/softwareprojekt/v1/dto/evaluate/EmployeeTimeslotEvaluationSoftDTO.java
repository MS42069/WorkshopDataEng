package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for evaluating employee timeslot soft constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for evaluating employee timeslot soft constraints")
public class EmployeeTimeslotEvaluationSoftDTO {
    @Schema(description = "The week of the evaluation")
    private int week;
    @Schema(description = "The overall score for the evaluation")
    private float score;

    @Schema(description = "The list of employee timeslot constraints that harm the schedule")
    private List<EmployeeTimeslotConstraintResDTO> harmedConstraints;
}
