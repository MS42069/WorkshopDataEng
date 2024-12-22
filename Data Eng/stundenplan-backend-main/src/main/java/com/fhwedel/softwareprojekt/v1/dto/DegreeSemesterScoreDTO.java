package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Response DTO that provides the data of the score of a timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeSemesterScoreDTO implements Serializable {

    /** Gesamtscore */
    @Schema(description = "Total score of the semester")
    float totalScore;

    /** Nummer des Semesters */
    @Schema(description = "Semester number")
    int semesterNumber;


    @Schema(description = "die einzelnen Wochen des Semesters")
    private List<DegreeSemesterWeekScoreDTO> weeks;
}
