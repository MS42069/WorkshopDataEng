package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for receiving {@link com.fhwedel.softwareprojekt.v1.model.Timetable} data from the client.
 */
@Schema(
        description =
                "DTO that is sent as request to the server to create a new timetable or to modify the properties of an existing timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableReqDTO implements Serializable {

    @NotNull
    @Schema(
            description = "Date the timetable starts at (Erster Vorlesungstag)",
            example = "2022-01-01")
    private LocalDate startDate;

    @NotNull
    @Schema(
            description = "Date the timetable ends at (Letzter Vorlesungstag)",
            example = "2022-01-31")
    private LocalDate endDate;

    @Positive
    @NotNull
    @Schema(description = "Number of study weeks this timetable has", minimum = "1", example = "1")
    private Integer numberOfWeeks;

    @NotBlank(message = "name can't be blank")
    @Schema(
            description = "Name of the timetable; MUST BE UNIQUE",
            minLength = 1,
            example = "Wintersemester 2022/23")
    private String name;

    @Schema(description = "Id of a semester type for example Id of summer-semester")
    private UUID semesterType;

    @Schema(description = "List of special events that occur during the timetable")
    @NotNull
    private List<@NotNull @Valid SpecialEventReqDTO> specialEvents;
}
