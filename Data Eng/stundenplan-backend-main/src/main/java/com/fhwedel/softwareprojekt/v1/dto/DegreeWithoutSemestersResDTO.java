package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a degree without its semesters along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeWithoutSemestersResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Degree name long", example = "Bachelor of Science Informatik")
    private String name;

    @Schema(description = "Degree name short", example = "B. Sc. Informatik")
    private String shortName;

    @Schema(description = "Amount of semesters to study for degree")
    private Integer semesterAmount;

    @Schema(description = "type of school e.g. University, School")
    private SchoolTypeResDTO schoolType;

    @Schema(description = "studyRegulation of degree", example = "18.0")
    private String studyRegulation;
}
