package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for evaluating employee timeslot hard constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for evaluating employee timeslot hard constraints")
public class EmployeeTimeslotEvaluationHardDTO {
    @Schema(description = "The week of the evaluation")
    private int week;
    @Schema(description = "The list of employee timeslot constraints that harm the schedule")
    private List<EmployeeTimeslotConstraintResDTO> harmedConstraints;
}
