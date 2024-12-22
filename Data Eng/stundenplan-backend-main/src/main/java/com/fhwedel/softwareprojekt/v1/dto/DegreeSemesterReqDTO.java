package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create a new degreeSemester or to modify the properties of an existing degreeSemester")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeSemesterReqDTO implements Serializable {

    @Schema(description = "Number of semester", minimum = "1")
    @NotNull
    @Positive
    private Integer semesterNumber;

    @Schema(description = "Name of the Extension", example = "Vertiefung Wirtschaft")
    private String extensionName;

    @Schema(description = "Amount of attendees in current semester", minimum = "0")
    @NotNull
    @PositiveOrZero
    private Integer attendees;

    @Schema(description = "ID of Degree this semester belongs to")
    private UUID degree;

    @Schema(description = "ID of Courses belonging to this semester")
    private List<UUID> courses;
}
