package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that is used to provide the client with the data of an {@link
 * com.fhwedel.softwareprojekt.v1.model.Timetable} entity.
 */
@Schema(
        description =
                "Response DTO that provides the data of an timetable along with their assigned ID "
                        + "their special events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableResDTO implements Serializable {

    @Schema(description = "Timetable identifier (UUID from the database)")
    private UUID id;

    @Schema(description = "Date the timetable starts at (Erster Vorlesungstag)")
    private LocalDate startDate;

    @Schema(description = "Date the timetable ends at (Letzter Vorlesungstag)")
    private LocalDate endDate;

    @Schema(description = "Number of study weeks this timetable has")
    private Integer numberOfWeeks;

    @Schema(description = "Name of the timetable")
    private String name;

    @Schema(description = "Type of the Semester")
    private SemesterTypeResDTO semesterType;

    @Schema(description = "List of special events that occur during the timetable")
    private List<SpecialEventResDTO> specialEvents;
}
