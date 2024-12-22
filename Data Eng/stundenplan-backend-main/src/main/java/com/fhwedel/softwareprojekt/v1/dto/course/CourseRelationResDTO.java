package com.fhwedel.softwareprojekt.v1.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Contains for each different relationship-type a list of courses to which the owner "
                        + "course of this DTO has a corresponding relationship.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRelationResDTO {
    @Schema(description = "Courses that may take place in parallel to the owner course of this DTO")
    private List<CourseBasicResDTO> mayBeParallelTo = new ArrayList<>();

    @Schema(description = "Courses that must be held before the owner course of this DTO.")
    private List<CourseBasicResDTO> mustBeHeldBefore = new ArrayList<>();

    @Schema(description = "Courses that must be held after the owner course of this DTO.")
    private List<CourseBasicResDTO> mustBeHeldAfter = new ArrayList<>();
}
