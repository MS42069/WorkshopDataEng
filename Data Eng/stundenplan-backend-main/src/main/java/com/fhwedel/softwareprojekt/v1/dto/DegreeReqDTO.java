package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create a new degree or to modify the properties of an existing degree")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeReqDTO implements Serializable {
    @Schema(
            description = "Degree name long",
            minLength = 1,
            example = "Bachelor of Science Informatik")
    @NotBlank
    private String name;

    @Schema(description = "Degree name short", minLength = 1, example = "B. Sc. Informatik")
    @NotBlank(message = "shortName can't be Blank")
    private String shortName;

    @Schema(description = "Amount of semesters to study for degree", minimum = "1")
    @NotNull
    @Positive
    private Integer semesterAmount;

    @Schema(description = "ID of schoolType")
    private UUID schoolType;

    @Schema(description = "studyRegulation of degree", minLength = 1, example = "18.0")
    @NotBlank(message = "studyRegulation can't be Blank")
    private String studyRegulation;

    @Schema(
            description =
                    "List of semesters IDs belonging to this degree. The semesters have to exist in DB")
    private List<@NotNull UUID> semesters;
}
