package com.fhwedel.softwareprojekt.v1.dto.schedule;

import com.fhwedel.softwareprojekt.v1.dto.DegreeWithoutSemestersResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.RoomBasicResDTO;
import com.fhwedel.softwareprojekt.v1.scheduler.Constraint;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Provides information about encountered problems, conflicts or violated constraints"
                        + "during scheduling.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemDTO implements Serializable {

    @Schema(description = "Enumeration constant representing the violated constraint")
    private Constraint violatedConstraint;

    @Schema(description = "Description of the problem encountered during scheduling")
    private String description;

    @Schema(description = "The employees for whom the problem occurred")
    @Builder.Default
    private List<EmployeeBasicDTO> conflictedEmployees = new ArrayList<>();

    @Schema(description = "The rooms affected by the problem")
    @Builder.Default
    private List<RoomBasicResDTO> conflictedRooms = new ArrayList<>();

    @Schema(description = "The subject semesters affected by the problem.")
    @Builder.Default
    private List<DegreeSemesterBasicDTO> conflictedSemesters = new ArrayList<>();

    @Override
    public String toString() {
        return "ProblemDTO("
                + "\n    violatedConstraint="
                + violatedConstraint
                + ",\n    description='"
                + description
                + '\''
                + ",\n    conflictedEmployees="
                + conflictedEmployees
                + ",\n    conflictedRooms="
                + conflictedRooms
                + ",\n    conflictedSemesters="
                + conflictedSemesters
                + "\n    )";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class EmployeeBasicDTO implements Serializable {
        @Schema(description = "Employee identifier (UUID from the database)")
        private UUID id;

        @Schema(description = "Unique employee abbreviation (= Mitarbeiterkürzel)", example = "wol")
        private String abbreviation;

        @Schema(description = "First name", example = "Birger")
        private String firstname;

        @Schema(description = "Last name", example = "Wolter")
        private String lastname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DegreeSemesterBasicDTO implements Serializable {
        @Schema(description = "Identifier in the database (UUID generated upon creation)")
        private UUID id;

        @Schema(description = "Number of semester")
        private Integer semesterNumber;

        @Schema(description = "Name of the Extension", example = "Vertiefung Wirtschaft")
        private String extensionName;

        @Schema(description = "Degree this semester belongs to")
        private DegreeWithoutSemestersResDTO degree;
    }
}
