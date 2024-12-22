package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response DTO that provides scores of all employees. */
@Schema(description = "Response DTO that provides scores of all employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEvaluationScoreDTO {
    @Schema(description = "The total score for all employees")
    private float totalScore;

    @Schema(description = "List of employee evaluations")
    private List<EmployeeEvaluationDTO> employeeEvaluations;
}
