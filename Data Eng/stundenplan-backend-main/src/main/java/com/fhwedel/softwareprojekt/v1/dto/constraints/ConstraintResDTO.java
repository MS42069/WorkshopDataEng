package com.fhwedel.softwareprojekt.v1.dto.constraints;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response DTO that provides the data of the Constraints for an employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstraintResDTO {
    @Schema(description = "Response DTO for Lunch Break Constraints")
    private LunchBreakConstraintResDTO lunchBreak;

    @Schema(description = "Response DTO for Free Day Constraints")
    private FreeDayConstraintResDTO freeDay;

    @Schema(description = "Response DTO for Breaks Between Constraints")
    private BreaksBetweenConstraintResDTO breaksBetween;

    @Schema(description = "Response DTO for Course Distribution Constraints")
    private CourseDistributionConstraintResDTO courseDistribution;

    @Schema(description = "Response DTO for Subsequent Timeslots Constraints")
    private SubsequentTimeslotsConstraintResDTO subsequentTimeslots;
}
