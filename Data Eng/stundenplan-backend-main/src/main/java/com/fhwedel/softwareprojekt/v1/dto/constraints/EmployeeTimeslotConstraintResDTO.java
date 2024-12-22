package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintType;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO for representing employee timeslot constraints response.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTimeslotConstraintResDTO {
    @Schema(description = "The abbreviation of the employee for whom the constraint is created")
    private String employeeAbbreviation;

    @Schema(description = "The type of constraint (e.g., 'Morning Shift', 'Afternoon Shift')")
    private ConstraintType constraintType;

    @Schema(description = "The value of the constraint (e.g., '1', '2')")
    private ConstraintValue constraintValue;

    @Schema(description = "The day of the week for the timeslot constraint")
    private DayOfWeek weekday;

    @Schema(description = "The index of the timeslot")
    private Integer timeslotIndex;

    @Schema(description = "The start time of the timeslot")
    private LocalTime startTime;

    @Schema(description = "The end time of the timeslot")
    private LocalTime endTime;

    @Schema(description = "Additional information or reason for the constraint")
    private String reason;

    private Boolean isAccepted;
}
