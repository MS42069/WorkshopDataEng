package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.SubsequentTimeslotsConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeSubsequentTimeslotsEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeSubsequentTimeslotsConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeSubsequentTimeslotsConstraintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for evaluating employee subsequent timeslots constraints.
 */
@Service
@RequiredArgsConstructor
public class EmployeeSubsequentTimeslotsEvaluationService {
    /**
     * Repository for EmployeeSubsequentTimeslotsConstraint entities.
     */
    private final EmployeeSubsequentTimeslotsConstraintRepository
            employeeSubsequentTimeslotsConstraintRepository;

    /**
     * Converter for SubsequentTimeslotsConstraint entities.
     */
    private final SubsequentTimeslotsConstraintConverter subsequentTimeslotsConstraintConverter;

    /**
     * Evaluates the employee subsequent timeslots constraint.
     *
     * @param abbreviation The abbreviation of the employee.
     * @param weekEvents   The list of weekEvents associated with the employee.
     * @return The evaluation result DTO.
     */
    List<EmployeeSubsequentTimeslotsEvaluationDTO> evaluate(
            String abbreviation, List<WeekEvent> weekEvents) {
        if (weekEvents.isEmpty()) {
            return Collections.emptyList();
        }

        Optional<EmployeeSubsequentTimeslotsConstraint> optional =
                employeeSubsequentTimeslotsConstraintRepository.findByEmployeeAbbreviation(
                        abbreviation);

        if (optional.isEmpty()) {
            return Collections.emptyList();
        }

        EmployeeSubsequentTimeslotsConstraint constraint = optional.get();

        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();

        List<WeekEvent> weekEventsPerWeek;
        List<EmployeeSubsequentTimeslotsEvaluationDTO> evaluations = new ArrayList<>();

        for (Integer week : weeks) {
            weekEventsPerWeek = weekEvents.stream()
                    .filter(weekEvent -> Objects.equals(weekEvent.getWeek(), week))
                    .toList();
            evaluations.add(evaluateWeek(week, weekEventsPerWeek, constraint));
        }
        return evaluations;
    }

    private EmployeeSubsequentTimeslotsEvaluationDTO evaluateWeek(Integer week, List<WeekEvent> weekEvents,
                                                                  EmployeeSubsequentTimeslotsConstraint constraint) {
        Map<DayOfWeek, List<Timeslot>> timeslotsByDay = getTimeslotsPerDay(weekEvents);

        Integer maximumSubsequentTimeslots = constraint.getTimeslotAmount();

        Set<DayOfWeek> invalidDays = getInvalidDays(timeslotsByDay, maximumSubsequentTimeslots);

        int daysWithCourses = timeslotsByDay.size();
        float score =
                !timeslotsByDay.isEmpty()
                        ? (float) (daysWithCourses - invalidDays.size())
                        / timeslotsByDay.size()
                        * 100
                        : 100;

        return EmployeeSubsequentTimeslotsEvaluationDTO.builder()
                .week(week)
                .constraint(
                        subsequentTimeslotsConstraintConverter.convertToResDTO(constraint))
                .harmedDays(invalidDays)
                .score(score)
                .build();
    }

    /**
     * Groups timeslots by day of the week.
     *
     * @param weekEvents The list of weekEvents.
     * @return A map of day of the week to the list of timeslots for that day.
     */
    private static Map<DayOfWeek, List<Timeslot>> getTimeslotsPerDay(List<WeekEvent> weekEvents) {
        return weekEvents.stream()
                .flatMap(
                        weekEvent ->
                                weekEvent.getTimeslots()
                                        .stream()
                                        .map(
                                                timeslot ->
                                                        new AbstractMap.SimpleEntry<>(
                                                                weekEvent.getWeekday(),
                                                                timeslot)))
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(
                                        Map.Entry::getValue, Collectors.toList())));
    }

    /**
     * Identifies the days with an excessive number of subsequent timeslots.
     *
     * @param timeslotsByDay             A map of day of the week to the list of timeslots for that day.
     * @param maximumSubsequentTimeslots The maximum number of subsequent timeslots allowed.
     * @return A set of days of the week that violate the constraint.
     */
    private static Set<DayOfWeek> getInvalidDays(
            Map<DayOfWeek, List<Timeslot>> timeslotsByDay, Integer maximumSubsequentTimeslots) {
        return timeslotsByDay.entrySet()
                .stream()
                .filter(
                        entry -> {
                            List<Timeslot> sortedTimeslots =
                                    entry.getValue()
                                            .stream()
                                            .sorted(Comparator.comparingInt(Timeslot::getIndex))
                                            .toList();

                            int count = 1; // to count the current sequence length
                            for (int i = 1; i < sortedTimeslots.size(); i++) {
                                if (sortedTimeslots.get(i)
                                        .getIndex()
                                        - sortedTimeslots.get(i - 1)
                                        .getIndex()
                                        == 1) {
                                    count++;
                                } else {
                                    count = 1; // reset the count
                                }

                                if (count > maximumSubsequentTimeslots) {
                                    return true; // found a sequence longer than allowed
                                }
                            }
                            return false; // no sequence longer than allowed was found
                        })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
