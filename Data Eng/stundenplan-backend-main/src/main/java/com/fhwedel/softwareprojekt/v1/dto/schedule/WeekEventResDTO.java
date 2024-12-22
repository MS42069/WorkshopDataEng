package com.fhwedel.softwareprojekt.v1.dto.schedule;

import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseBasicResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Provides the data of a specific week event.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeekEventResDTO implements Serializable {

    @Schema(description = "Event identifier (UUID from the database)")
    private UUID id;

    @Schema(description = "The course that is scheduled for this day and time")
    private CourseBasicResDTO course;

    @Schema(description = "Week number for the event")
    private Integer week;

    @Schema(description = "Weekday on which the event takes place")
    private DayOfWeek weekday;

    @Schema(description = "Block of timeslots")
    @Builder.Default
    private List<IdWrapperDTO> timeslots = new ArrayList<>();

    @Schema(description = "Combination of rooms where the event will take place")
    @Builder.Default
    private List<IdWrapperDTO> rooms = new ArrayList<>();
}
