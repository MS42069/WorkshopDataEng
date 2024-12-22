package com.fhwedel.softwareprojekt.v1.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                """
        Represents week event, meaning a potential weekly date on which an course should be scheduled.
        Specifies a timeslot or a block of timeslots during which the event should take place in the specified
        combination of rooms.""")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeekEventReqDTO implements Serializable {

    @Schema(description = "The ID of the course to be scheduled")
    @NotNull
    private UUID courseId;

    @Schema(description = "Week number for the event or null for all weeks")
    private Integer week;

    @Schema(description = "Weekday on which the event takes place")
    @NotNull
    private DayOfWeek weekday;

    @Schema(
            description =
                    "Single timeslot or contiguous block of timeslots (more precisely their ids)")
    @Size(min = 1)
    @NotNull
    @Builder.Default
    private Set<@NotNull UUID> blockOfTimeslots = new HashSet<>();

    @Schema(description = "Single room or combination of rooms in which the event will take place")
    @Size(min = 1)
    @NotNull
    @Builder.Default
    private Set<@NotNull UUID> takesPlaceInRooms = new HashSet<>();
}
