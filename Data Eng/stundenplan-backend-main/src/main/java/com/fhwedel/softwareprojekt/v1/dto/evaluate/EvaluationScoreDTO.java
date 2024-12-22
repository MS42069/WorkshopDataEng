package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response DTO that provides the data of the score of a timetable. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response DTO that provides the data of the score of a timetable")
public class EvaluationScoreDTO {
    @Schema(description = "The evaluation score for employee schedules")
    private EmployeeEvaluationScoreDTO employeeEvaluationScoreDTO;

    @Schema(description = "The evaluation score for student schedules")
    private StudentEvaluationScoreDTO studentEvaluationScoreDTO;

    @Schema(description = "The overall score for the timetable")
    private Float score;
}
