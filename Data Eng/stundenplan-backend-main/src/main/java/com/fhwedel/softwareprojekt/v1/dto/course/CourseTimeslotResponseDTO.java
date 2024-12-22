package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for providing the client with the data of an {@link
 * com.fhwedel.softwareprojekt.v1.model.CourseTimeslot} entity.
 */
@Schema(description = "Represents course-timeslots")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTimeslotResponseDTO implements Serializable {
    @Schema(description = "Day of the week for the course timeslot")
    private DayOfWeek weekday;

    @Schema(description = "Details of the timeslot")
    private TimeslotResDTO timeslot;
}
