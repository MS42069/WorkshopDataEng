package com.fhwedel.softwareprojekt.v1.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Provides possible options of week dates for a specific course, on which an event of the course may take place. "
                        + "(= Ordnet einem Kurs eine Menge möglicher Optionen für wöchentliche Veranstaltungstermine zu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionsDTO implements Serializable {

    @Schema(description = "The course whose options for event dates this dto contains.")
    private UUID course;

    @Schema(description = "Degree of freedom ('Freiheitsgrad')")
    private int degreeOfFreedom;

    @Schema(description = "List of possible (weekly) event dates")
    @Builder.Default
    private List<OptionDTO> options = new LinkedList<>();
}
