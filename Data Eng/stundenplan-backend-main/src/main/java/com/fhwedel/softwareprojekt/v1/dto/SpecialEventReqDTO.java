package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Represents a timetable's special event that is to be created and associated with it")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialEventReqDTO implements Serializable {

    @NotNull
    @Schema(
            description = "Date the special event starts at; CANNOT BE NULL!",
            example = "2022-01-01")
    private LocalDate startDate;

    @NotNull
    @Schema(description = "Date the special event ends at; CANNOT BE NULL!", example = "2022-01-31")
    private LocalDate endDate;

    @NotNull
    @Schema(description = "Type of the special event; CANNOT BE NULL", example = "HOLIDAY")
    private SpecialEventType specialEventType;
}
