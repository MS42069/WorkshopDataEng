package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.validation.ValidTimeslotTime;
import com.fhwedel.softwareprojekt.v1.validation.group.ExtendedConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A DTO for the {@link com.fhwedel.softwareprojekt.v1.model.Timeslot} entity */
@Schema(
        description =
                "Represents a time slot that defines a time interval between a start and an end time. "
                        + "The start time must be before the end time.",
        example =
                """
        {
            "startTime": "08:00",
            "endTime": "09:00",
            "index": 0
        }
        """)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@GroupSequence({TimeslotReqDTO.class, ExtendedConstraint.class})
@ValidTimeslotTime(groups = ExtendedConstraint.class)
public class TimeslotReqDTO implements Serializable {

    @Schema(description = "Start time of the time slot", required = true)
    @NotNull
    private LocalTime startTime;

    @Schema(description = "End time of the time slot", required = true)
    @NotNull
    private LocalTime endTime;

    @Schema(
            description = "Index that may be used to define an order; must be unique",
            minimum = "0")
    @NotNull
    @PositiveOrZero
    private Integer index;
}
