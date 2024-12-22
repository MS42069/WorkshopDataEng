package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response DTO that provides the data of the score of a timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeScoreDTO implements Serializable {

    /** Gesamtscore des Studiengangs */
    @Schema(description = "Total score of the degree program")
    private float totalDegreeScore;

    /** Name des Studiengangs */
    @Schema(description = "Name of the degree program")
    private String degreeName;

    /** Studiengangsordnung */
    @Schema(description = "Regulation of the degree program")
    private String degreeRegulation;

    /** Liste der Semesterscores des Studiengangs */
    @Schema(description = "List of the scores of all semesters")
    private List<DegreeSemesterScoreDTO> degreeSemesterScores;
}
