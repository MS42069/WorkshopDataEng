package com.fhwedel.softwareprojekt.v1.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for receiving {@link com.fhwedel.softwareprojekt.v1.model.CourseTimeslot} data from the
 * client.
 */
@Schema(description = "Represents a timeslot when a course may take place.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTimeslotDTO implements Serializable {
    @Schema(
            description = "Weekday the Course is allowed to take place on",
            example = "e. g. MONDAY,TUESDAY,...")
    @NotNull
    private DayOfWeek weekday;

    @Schema(description = "Timeslot Identifier (UUID from the database)")
    @NotNull
    private UUID timeslotID;
}
