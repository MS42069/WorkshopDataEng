package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.CourseDistributionConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeDistributionEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeCourseDistributionConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeCourseDistributionConstraintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for evaluating employee distribution constraints.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDistributionEvaluationService {

    /**
     * Repository for EmployeeCourseDistributionConstraint entities.
     */
    private final EmployeeCourseDistributionConstraintRepository
            employeeCourseDistributionConstraintRepository;

    /**
     * Converter for CourseDistributionConstraint entities.
     */
    private final CourseDistributionConstraintConverter courseDistributionConstraintConverter;

    /**
     * Evaluates the employee distribution constraint.
     *
     * @param abbreviation The abbreviation of the employee.
     * @param weekEvents   The list of weekEvents associated with the employee.
     * @return The evaluation result DTO.
     */
    List<EmployeeDistributionEvaluationDTO> evaluate(
            String abbreviation, List<WeekEvent> weekEvents) {
        Optional<EmployeeCourseDistributionConstraint> optional =
                employeeCourseDistributionConstraintRepository.findByEmployeeAbbreviation(
                        abbreviation);

        if (optional.isEmpty()) {
            return Collections.emptyList();
        }


        EmployeeCourseDistributionConstraint constraint = optional.get();

        List<EmployeeDistributionEvaluationDTO> evaluations = new ArrayList<>();
        List<WeekEvent> weekEventsPerWeek;
        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();
        for (Integer week : weeks) {
            weekEventsPerWeek = weekEvents.stream()
                    .filter(weekEvent -> Objects.equals(weekEvent.getWeek(), week))
                    .toList();
            evaluations.add(evaluateForWeek(week, weekEventsPerWeek, constraint));
        }
        return evaluations;
    }

    private EmployeeDistributionEvaluationDTO evaluateForWeek(Integer week, List<WeekEvent> weekEvents,
                                                              EmployeeCourseDistributionConstraint constraint) {
        Map<DayOfWeek, List<Timeslot>> timeslotsByDay = weekEvents.stream()
                .collect(
                        Collectors.groupingBy(
                                WeekEvent::getWeekday,
                                Collectors.flatMapping(
                                        weekEvent -> weekEvent.getTimeslots()
                                                .stream(),
                                        Collectors.toList()
                                )
                        )
                );

        long averageTimeslotsPerDay =
                Math.round(
                        timeslotsByDay.values()
                                .stream()
                                .mapToInt(List::size) // Convert each list to its size (number of
                                // timeslots)
                                .average() // Calculate the average
                                .orElse(0.0));

        long daysWithinRange =
                timeslotsByDay.values()
                        .stream()
                        .filter(
                                timeslots ->
                                        timeslots.size() >= averageTimeslotsPerDay - 1
                                                && timeslots.size() <= averageTimeslotsPerDay + 1)
                        .count();
        float score;

        if (timeslotsByDay.isEmpty()) {
            score = 100;
        } else if (constraint.getConstraintValue()
                .equals(ConstraintValue.WANTED)) {
            score = (float) daysWithinRange / timeslotsByDay.size() * 100;
        } else {
            score = (float) (timeslotsByDay.size() - daysWithinRange) / timeslotsByDay.size() * 100;
        }
        return EmployeeDistributionEvaluationDTO.builder()
                .week(week)
                .constraint(courseDistributionConstraintConverter.convertToResDTO(constraint))
                .score(score)
                .build();
    }
}
