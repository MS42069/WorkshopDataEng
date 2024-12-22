package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for providing the client with the data of an {@link
 * com.fhwedel.softwareprojekt.v1.model.SpecialEvent} entity.
 */
@Schema(
        description =
                "Represents a timetable's special event: starting at a specific date and timeslot and can go until an other date and timeslot")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialEventResDTO implements Serializable {
    @Schema(description = "Date the special event starts at")
    private LocalDate startDate;

    @Schema(description = "Date the special event ends at")
    private LocalDate endDate;

    @Schema(description = "Type of the special event")
    private SpecialEventType specialEventType;
}
