package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.course.CourseTimeslotDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a course, without the semesters the course is in, along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseWithoutSemestersResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "ID of the Course in CAS-System", example = "WS21SA008V")
    private String casID;

    @Schema(description = "Name of the course", example = "Grundlagen der Linearen Algebra")
    private String name;

    @Schema(description = "Abbreviation of the course", example = "B023A")
    private String abbreviation;

    @Schema(
            description = "Description of the course",
            example = "Kurs zu Vorbereitung auf Analysis I Klausur")
    private String description;

    @Schema(description = "Amount of slots the course has to be planned as block")
    private Integer blockSize;

    @Schema(description = "Slots per week the course has to be planned")
    private Integer slotsPerWeek;

    @Schema(description = "Amount of weeks the course has to be planned")
    private Integer weeksPerSemester;

    @Schema(description = "Type of the course e.g. LECTURE, PRACTICE, TRAINING_COURSE")
    private CourseTypeResDTO courseType;

    @Schema(description = "List of times when a course may take place.")
    private List<@Valid CourseTimeslotDTO> courseTimeslots;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
