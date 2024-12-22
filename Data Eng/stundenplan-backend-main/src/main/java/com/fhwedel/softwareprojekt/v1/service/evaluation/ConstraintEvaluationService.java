package com.fhwedel.softwareprojekt.v1.service.evaluation;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeEvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.service.evaluation.employee.EmployeeConstraintEvaluationService;
import com.fhwedel.softwareprojekt.v1.service.evaluation.student.StudentConstraintEvaluationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service for evaluating timetable constraints for both employees and students. This service
 * calculates the overall evaluation score based on the scores of employee and student evaluations.
 */
@Service
@RequiredArgsConstructor
public class ConstraintEvaluationService {

    /** The service for evaluating constraints related to employees. */
    private final EmployeeConstraintEvaluationService employeeConstraintEvaluationService;

    /** The service for evaluating constraints related to students. */
    private final StudentConstraintEvaluationService studentConstraintEvaluationService;

    /**
     * Evaluates the timetable constraints for both employees and students and calculates the
     * overall evaluation score.
     *
     * @param timetableId The UUID of the timetable to be evaluated.
     * @return The overall evaluation score.
     */
    public EvaluationScoreDTO evaluateTimetable(UUID timetableId) {
        StudentEvaluationScoreDTO studentScore =
                studentConstraintEvaluationService.evaluateStudentScore(timetableId);
        EmployeeEvaluationScoreDTO employeeScore =
                employeeConstraintEvaluationService.evaluateEmployeeScore(timetableId);

        Float score = (studentScore.getTotalScore() + employeeScore.getTotalScore()) / 2;

        return EvaluationScoreDTO.builder()
                .employeeEvaluationScoreDTO(employeeScore)
                .studentEvaluationScoreDTO(studentScore)
                .score(score)
                .build();
    }
}
