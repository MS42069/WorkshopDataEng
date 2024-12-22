package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
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
                "Response DTO that provides all data associated with a course (= Veranstaltung).")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDetailResDTO implements Serializable {

    @Schema(description = "Course identifier (UUID from the database)")
    private UUID id;

    @Schema(
            description = "Unique alphanumeric Identifier used in CAS system",
            example = "WS22SB037")
    private String casID;

    @Schema(description = "Course name", example = "Anwendungsentwicklung in ERP-Systemen")
    private String name;

    @Schema(description = "Short course name", example = "ERP-Systeme")
    private String abbreviation;

    @Schema(description = "Additional description")
    private String description;

    @Schema(
            description = "Number of consecutive course sessions to be held in one block",
            example = "1")
    private Integer blockSize;

    @Schema(
            description =
                    "Number of weeks in the semester for which the course should be scheduled",
            example = "12")
    private Integer weeksPerSemester;

    @Schema(description = "Frequency per week (number of course sessions per week)", example = "2")
    private Integer slotsPerWeek;

    @Schema(description = "Teaching method", example = "Vorlesung")
    private CourseTypeResDTO courseType;

    @Schema(
            description =
                    "List of suitable rooms or room combinations in which a course can take place.")
    @Builder.Default
    private List<CourseRoomComboResDTO> suitedRooms = new ArrayList<>();

    @Schema(description = "List of staff members who teach this course")
    @Builder.Default
    private List<EmployeeResDTO> lecturers = new ArrayList<>();

    @Schema(description = "Relationships to other courses")
    @Builder.Default
    private CourseRelationResDTO courseRelations = new CourseRelationResDTO();

    @Schema(description = "List of times when a course may take place.")
    @Builder.Default
    private List<CourseTimeslotResponseDTO> courseTimeslots = new ArrayList<>();

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
