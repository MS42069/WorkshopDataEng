package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import com.fhwedel.softwareprojekt.v1.dto.DegreeScoreDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response DTO that provides the data of the score of a timetable. */
@Schema(description = "Response DTO that provides the data of the score of a timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEvaluationScoreDTO implements Serializable {

    @Schema(description = "The score of the entire timetable")
    private float totalScore;

    @Schema(description = "A list of degree scores")
    private List<DegreeScoreDTO> degreeScores;
}
