package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.EvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.service.evaluation.ConstraintEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for handling score-related operations. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/score")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "score", description = "Operations about the score")
public class ScoreController {

    /** Service for evaluating constraints. */
    private final ConstraintEvaluationService evaluationService;

    /**
     * Retrieves the score for a specified timetable.
     *
     * @param id The unique identifier of the timetable
     * @return The evaluation score DTO
     */
    @GetMapping
    @Operation(summary = "Find score", description = "Returns the score")
    public EvaluationScoreDTO getScore(@PathVariable("timetableId") UUID id) {
        log.info("GetScore aufgerufen");
        return evaluationService.evaluateTimetable(id);
    }
}
