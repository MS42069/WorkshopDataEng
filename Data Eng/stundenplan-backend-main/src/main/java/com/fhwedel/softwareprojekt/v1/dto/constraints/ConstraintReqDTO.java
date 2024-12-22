package com.fhwedel.softwareprojekt.v1.dto.constraints;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create new constraints or to modify the "
                        + "properties of existing constraints")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstraintReqDTO {
    @Schema(description = "Request DTO for Lunch Break Constraints")
    private LunchBreakConstraintReqDTO lunchBreak;

    @Schema(description = "Request DTO for Free Day Constraints")
    private FreeDayConstraintReqDTO freeDay;

    @Schema(description = "Request DTO for Breaks Between Constraints")
    private BreaksBetweenConstraintReqDTO breaksBetween;

    @Schema(description = "Request DTO for Course Distribution Constraints")
    private CourseDistributionConstraintReqDTO courseDistribution;

    @Schema(description = "Request DTO for Subsequent Timeslots Constraints")
    private SubsequentTimeslotsConstraintReqDTO subsequentTimeslots;
}
