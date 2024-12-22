package com.fhwedel.softwareprojekt.v1.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Represents the result of the scheduling process. Contains the scheduled event with its ID. "
                        + "May provide further information for example if the scheduling is force and some constraints are violated.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulingResultDTO implements Serializable {
    @Schema(
            description =
                    "The scheduled events, may only be multiple if the event was planned in all weeks")
    private WeekEventResDTO[] events;
}
