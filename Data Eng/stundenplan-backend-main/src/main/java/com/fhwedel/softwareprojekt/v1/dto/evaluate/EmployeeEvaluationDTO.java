package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for representing employee evaluations.
 */
@Builder
@Getter
@Setter
@Schema(description = "Represents the evaluation of an employee")
public class EmployeeEvaluationDTO {
    @Schema(description = "The overall score of the evaluation")
    private int score;

    @Schema(description = "The abbreviation of the employee")
    private String abbreviation;

    @Schema(description = "Evaluation of timeslot constraints for the employee")
    private List<EmployeeTimeslotEvaluationSoftDTO> timeslotEvaluationSoft;

    @Schema(description = "Evaluation of timeslot constraints for the employee")
    private List<EmployeeTimeslotEvaluationHardDTO> timeslotEvaluationHard;

    @Schema(description = "Evaluation of free day constraints for the employee")
    private List<EmployeeFreeDayEvaluationDTO> freeDayEvaluation;

    @Schema(description = "Evaluation of breaks between constraints for the employee")
    private List<EmployeeBreaksBetweenEvaluationDTO> breaksBetweenEvaluation;

    @Schema(description = "Evaluation of lunch break constraints for the employee")
    private List<EmployeeLunchBreakEvaluationDTO> lunchBreakEvaluation;

    @Schema(description = "Evaluation of course distribution constraints for the employee")
    private List<EmployeeDistributionEvaluationDTO> distributionEvaluation;

    @Schema(description = "Evaluation of subsequent timeslots constraints for the employee")
    private List<EmployeeSubsequentTimeslotsEvaluationDTO> subsequentTimeslotsEvaluation;
}
