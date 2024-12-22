package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.FreeDayConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeFreeDayEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeFreeDayConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeFreeDayConstraintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for evaluating employee free day constraints.
 */
@Service
@RequiredArgsConstructor
public class EmployeeFreeDayEvaluationService {
    /**
     * Repository for EmployeeFreeDayConstraint entities.
     */
    private final EmployeeFreeDayConstraintRepository employeeFreeDayConstraintRepository;

    /**
     * Repository for WorkTime entities.
     */
    private final WorkTimeRepository workTimeRepository;

    /**
     * Converter for FreeDayConstraint entities.
     */
    private final FreeDayConstraintConverter freeDayConstraintConverter;

    /**
     * Evaluates the employee free day constraint.
     *
     * @param abbreviation The abbreviation of the employee.
     * @param weekEvents   The list of weekEvents associated with the employee.
     * @return The evaluation result DTO.
     */
    public List<EmployeeFreeDayEvaluationDTO> evaluateWeek(
            String abbreviation, List<WeekEvent> weekEvents) {
        Set<DayOfWeek> workingDays =
                workTimeRepository.findByEmployeeAbbreviation(abbreviation)
                        .stream()
                        .map(WorkTime::getWeekday)
                        .collect(Collectors.toSet());

        if (workingDays.isEmpty()) {
            return Collections.emptyList();
        }

        Optional<EmployeeFreeDayConstraint> optional =
                employeeFreeDayConstraintRepository.findByEmployeeAbbreviation(abbreviation);

        if (optional.isEmpty()) {
            return Collections.emptyList();
        }

        EmployeeFreeDayConstraint constraint = optional.get();
        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();

        List<EmployeeFreeDayEvaluationDTO> evaluations = new ArrayList<>();

        Set<DayOfWeek> daysPerWeek;
        for (Integer week : weeks) {
            daysPerWeek = weekEvents.stream()
                    .filter(weekEvent -> weekEvent.getWeek()
                            .equals(week))
                    .map(WeekEvent::getWeekday)
                    .collect(Collectors.toSet());

            evaluations.add(evaluateForWeek(week, constraint, workingDays, daysPerWeek));
        }
        return evaluations;
    }

    /**
     * Calculates the score for the employee free day constraint.
     *
     * @param constraint      The employee free day constraint.
     * @param workingDays     The set of working days for the employee.
     * @param daysWithCourses The set of days with courses scheduled.
     * @return The calculated score.
     */
    private EmployeeFreeDayEvaluationDTO evaluateForWeek(Integer week,
                                                         EmployeeFreeDayConstraint constraint,
                                                         Set<DayOfWeek> workingDays,
                                                         Set<DayOfWeek> daysWithCourses) {
        float score;

        if (constraint.getConstraintValue()
                .equals(ConstraintValue.WANTED)) {
            boolean isFavoriteDayIncluded = daysWithCourses.contains(constraint.getFavoriteDay());
            boolean isDaysWithCoursesLessThanWorkingDays =
                    daysWithCourses.size() < workingDays.size();

            score = isDaysWithCoursesLessThanWorkingDays ? (isFavoriteDayIncluded ? 75 : 100) : 0;
        } else {
            score = (daysWithCourses.size() == workingDays.size()) ? 100 : 0;
        }
        return EmployeeFreeDayEvaluationDTO.builder()
                .constraint(freeDayConstraintConverter.convertToResDTO(constraint))
                .week(week)
                .score(score)
                .build();
    }
}
