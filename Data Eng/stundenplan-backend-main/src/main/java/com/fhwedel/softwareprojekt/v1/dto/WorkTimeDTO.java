package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for receiving {@link WorkTime} data from the client. */
@Schema(
        description =
                "Represents an employee's working time that is to be created and associated with them")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkTimeDTO implements Serializable {
    @Schema(description = "Weekday")
    @NotNull
    private DayOfWeek weekday;

    @Schema(description = "Timeslot Identifier (UUID from the database)")
    @NotNull
    private UUID timeslotID;
}
