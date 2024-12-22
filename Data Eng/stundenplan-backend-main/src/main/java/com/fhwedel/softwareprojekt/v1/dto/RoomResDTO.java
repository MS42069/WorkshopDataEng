package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object for providing the client with the ID of a room along with its data. */
@Schema(description = "Response DTO that provides the data of a room along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Unique room name long", example = "Hörsaal 1")
    private String name;

    @Schema(description = "Unique room name short (=Raumkürzel)", example = "HS01")
    private String abbreviation;

    @Schema(description = "Unique alphanumeric CAS room identifier", example = "C0.02")
    private String identifier;

    @Schema(description = "Number of seats")
    private int capacity;

    @Schema(description = "Room type or category of usage e.g. Lecture hall, PC-Pool, Seminar room")
    private RoomTypeResDTO roomType;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
