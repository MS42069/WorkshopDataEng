package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a degree semester without the degree along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeSemesterWithoutDegreeResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Number of semester")
    private Integer semesterNumber;

    @Schema(description = "Name of the Extension", example = "Vertiefung Wirtschaft")
    private String extensionName;

    @Schema(description = "Amount of attendees in current semester")
    private Integer attendees;
}
