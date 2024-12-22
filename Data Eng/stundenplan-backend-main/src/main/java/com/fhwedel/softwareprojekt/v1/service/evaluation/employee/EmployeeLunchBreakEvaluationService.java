package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.LunchBreakConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeLunchBreakEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeLunchBreakConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeLunchBreakConstraintRepository;
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
 * Service class for evaluating employee lunch break constraints.
 */
@Service
@RequiredArgsConstructor
public class EmployeeLunchBreakEvaluationService {
    /**
     * Repository for EmployeeLunchBreakConstraint entities.
     */
    private final EmployeeLunchBreakConstraintRepository employeeLunchBreakConstraintRepository;

    /**
     * Converter for LunchBreakConstraint entities.
     */
    private final LunchBreakConstraintConverter lunchBreakConstraintConverter;

    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /**
     * Evaluates the employee lunch break constraint.
     *
     * @param abbreviation The abbreviation of the employee.
     * @param weekEvents   The list of weekEvents associated with the employee.
     * @return The evaluation result DTO.
     */
    List<EmployeeLunchBreakEvaluationDTO> evaluate(
            String abbreviation, List<WeekEvent> weekEvents) {

        if (weekEvents.isEmpty()) {
            return Collections.emptyList();
        }

        Optional<EmployeeLunchBreakConstraint> optional =
                employeeLunchBreakConstraintRepository.findByEmployeeAbbreviation(abbreviation);

        if (optional.isEmpty()) {
            return Collections.emptyList();
        }

        EmployeeLunchBreakConstraint constraint = optional.get();

        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();

        List<WeekEvent> weekEventsPerWeek;
        List<EmployeeLunchBreakEvaluationDTO> evaluations = new ArrayList<>();
        for (Integer week : weeks) {
            weekEventsPerWeek = weekEvents.stream()
                    .filter(weekEvent -> weekEvent.getWeek()
                            .equals(week))
                    .toList();
            evaluations.add(evaluateWeek(week, weekEventsPerWeek, constraint));
        }

        return evaluations;
    }

    private EmployeeLunchBreakEvaluationDTO evaluateWeek(Integer week, List<WeekEvent> weekEvents,
                                                         EmployeeLunchBreakConstraint constraint) {
        Set<DayOfWeek> weekdays =
                weekEvents.stream()
                        .map(WeekEvent::getWeekday)
                        .collect(Collectors.toSet());

        int amountOfWeekDaysWithCourses = weekdays.size();

        Set<DayOfWeek> daysWithoutLunchBreaks =
                weekEvents.stream()
                        .filter(
                                weekEvent ->
                                        weekEvent.getTimeslots()
                                                .stream()
                                                .anyMatch(timeslot -> timeslot.getIndex() == 3))
                        .map(WeekEvent::getWeekday)
                        .collect(Collectors.toSet());

        if (constraint.getConstraintValue()
                .equals(ConstraintValue.WANTED)) {
            weekdays.removeAll(daysWithoutLunchBreaks);
        } else {
            weekdays.retainAll(daysWithoutLunchBreaks);
        }
        return EmployeeLunchBreakEvaluationDTO.builder()
                .week(week)
                .constraint(lunchBreakConstraintConverter.convertToResDTO(constraint))
                .harmedDays(weekdays)
                .score(
                        Math.round(
                                amountOfWeekDaysWithCourses > 0
                                        ? (float) weekdays.size()
                                        / amountOfWeekDaysWithCourses
                                        * 100
                                        : 100))
                .build();
    }
}
