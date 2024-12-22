package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object for providing the client with the ID of a room along with its data. */
@Schema(
        description =
                "Response DTO that provides the data of a time slot along with its assigned ID.",
        example =
                """
        {
            "id": "4ebb8678-731c-11ed-a1eb-0242ac120002",
            "startTime": "08:00:00",
            "endTime": "09:00:00",
            "index": 0
        }
        """)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeslotResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Start time of the time slot")
    private LocalTime startTime;

    @Schema(description = "End time of the time slot")
    private LocalTime endTime;

    @Schema(description = "Unique index that may be used to define an order")
    private Integer index;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
