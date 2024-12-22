package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A DTO for the {@link com.fhwedel.softwareprojekt.v1.model.Room} entity */
@Schema(
        description =
                "DTO that is sent as request to the server to create a new room or to modify the properties of an existing room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomReqDTO implements Serializable {

    @Schema(description = "Room name long; MUST be unique", minLength = 1, example = "Hörsaal 1")
    @NotBlank(message = "no name was given")
    private String name;

    @Schema(
            description = "Room name short (= Raumkürzel); MUST be unique",
            minLength = 1,
            example = "HS01")
    @NotBlank(message = "no abbreviation was given")
    private String abbreviation;

    @Schema(
            description = "Alphanumeric CAS room identifier; MUST be unique",
            minLength = 1,
            example = "C0.02")
    @NotBlank(message = "no identifier was given")
    private String identifier;

    @Schema(description = "Number of seats", minimum = "0")
    @NotNull
    @PositiveOrZero
    private Integer capacity;

    @Schema(description = "Room type or category of usage e.g. Lecture hall, PC-Pool, Seminar room")
    @NotNull
    private UUID roomType;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    @NotNull
    private UUID timetable;
}
