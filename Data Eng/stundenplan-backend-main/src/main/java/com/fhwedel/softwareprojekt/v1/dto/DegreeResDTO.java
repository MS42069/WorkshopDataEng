package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response DTO that provides the data of a degree along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeResDTO {
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

    @Schema(description = "List of semesters belonging to this degree")
    private List<DegreeSemesterWithoutDegreeResDTO> semesters;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
