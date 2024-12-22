package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for providing the client with the data of an {@link WorkTime} entity. */
@Schema(
        description =
                "Represents an employee's working time: a specific time slot on a specific weekday")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkTimeResponseDTO implements Serializable {
    @Schema(description = "The day of the week when the employee works")
    private DayOfWeek weekday;

    @Schema(description = "The time slot during which the employee works")
    private TimeslotResDTO timeslot;
}
