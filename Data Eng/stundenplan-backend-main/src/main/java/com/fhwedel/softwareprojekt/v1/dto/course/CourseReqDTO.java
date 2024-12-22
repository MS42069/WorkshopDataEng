package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.validation.ValidCourseBlockSize;
import com.fhwedel.softwareprojekt.v1.validation.group.ExtendedConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.*;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Represents a course (= Veranstaltung) such as a lecture. "
                        + "Serves as DTO that may be sent to the server to create a new course or modify an existing one.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@GroupSequence({CourseReqDTO.class, ExtendedConstraint.class})
@ValidCourseBlockSize(groups = ExtendedConstraint.class)
public class CourseReqDTO implements Serializable {
    @Schema(
            description = "Alphanumeric Identifier used in CAS system; must be unique",
            minLength = 1,
            example = "WS22SB037")
    @NotBlank
    private String casID;

    @Schema(
            description = "Course name",
            minLength = 1,
            example = "Anwendungsentwicklung in ERP-Systemen")
    @NotBlank
    private String name;

    @Schema(
            description =
                    "Optional short course name; defaults to course name, if not explicitly specified otherwise or a blank abbrev is given",
            example = "ERP-Systeme")
    private String abbreviation;

    @Schema(
            description =
                    "Optional description to provide additional information about the course; defaults to an empty string if not specified")
    @Builder.Default
    private String description = "";

    @Schema(
            description =
                    "Number of consecutive course sessions (for planning the course in blocks);"
                            + "\n must be less than or equal to slotsPerWeek",
            example = "1")
    @NotNull
    @Min(1)
    private Integer blockSize;

    @Schema(
            description =
                    "Number of weeks in the semester for which the course should be scheduled",
            example = "12")
    @NotNull
    @Min(1)
    private Integer weeksPerSemester;

    @Schema(description = "Frequency per week (number of course sessions per week)", example = "2")
    @NotNull
    @Min(1)
    private Integer slotsPerWeek;

    @Schema(description = "Teaching method", example = "Vorlesung")
    private UUID courseType;

    @Schema(
            description =
                    "Set of possible room combinations in which the course can take place. Duplicates will be ignored. "
                            + "For the room combinations a logical OR operation applies, for the rooms in a single combination a logical AND operation applies.")
    @NotNull
    @Builder.Default
    private Set<@Valid @NotNull CourseRoomComboReqDTO> suitedRooms = new LinkedHashSet<>();

    @Schema(
            description =
                    "Set of IDs of staff members to teach this course. Duplicates will be ignored.")
    @NotNull
    @Builder.Default
    private Set<@Valid @NotNull IdWrapperDTO> lecturers = new LinkedHashSet<>();

    @Schema(
            description =
                    "Relationships to other courses. If not specified (and not null) then a Default Empty Set "
                            + "of course-relations is assumed, meaning no course-relationships are created.")
    @NotNull
    @Builder.Default
    private CourseRelationReqDTO courseRelations = new CourseRelationReqDTO();

    @Schema(description = "List of times when a course may take place.")
    private List<@Valid CourseTimeslotDTO> courseTimeslots = new ArrayList<>();
}
