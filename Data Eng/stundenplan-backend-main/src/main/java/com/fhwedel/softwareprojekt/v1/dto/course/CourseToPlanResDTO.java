package com.fhwedel.softwareprojekt.v1.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Provides for a course planning data such as the amount of timeslots the course still "
                        + "has to be planned for and the degree of freedom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseToPlanResDTO extends CourseBasicResDTO {

    @Schema(description = "Amount of slots the course still has to be planned on")
    private int amountOfSlotsToPlan;

    @Schema(
            description =
                    "Degree of freedom with regard to the available options at "
                            + "which an event of the course can be scheduled")
    private int degreeOfFreedom;
}
