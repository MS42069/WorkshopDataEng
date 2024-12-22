package com.fhwedel.softwareprojekt.v1.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Schema(
        description =
                "Represents an option, meaning a possible week date on which a course event can be scheduled. "
                        + "(= möglicher Veranstaltungstermin in Bezug auf einen Wochentag unabhängig vom konkreten Kurs)")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OptionDTO implements Serializable {

    @Schema(description = "The week of the option")
    private Integer week;

    @Schema(description = "The day of the week")
    private DayOfWeek weekday;

    @Schema(
            description =
                    "Single timeslot or contiguous block of timeslots if the event must be scheduled in a block; "
                            + "timeslot UUIDs are sorted in ascending order by start time")
    @Builder.Default
    private List<UUID> timeslots = new ArrayList<>();

    @Schema(description = "Single room or a combination of rooms in which the event may take place")
    @Builder.Default
    private List<UUID> rooms = new LinkedList<>();
}
