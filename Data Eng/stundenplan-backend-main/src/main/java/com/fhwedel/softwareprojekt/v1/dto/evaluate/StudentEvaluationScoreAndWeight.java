package com.fhwedel.softwareprojekt.v1.dto.evaluate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/** DTO for providing student evaluation scores and weights. */
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "DTO for providing student evaluation scores and weights.")
public class StudentEvaluationScoreAndWeight {
    @Schema(description = "The score for the evaluation.")
    private Integer score;

    @Schema(description = "The weight associated with the evaluation score.")
    private Integer weight;

    @Schema(description = "A description of the evaluation.")
    private String desc;
}
