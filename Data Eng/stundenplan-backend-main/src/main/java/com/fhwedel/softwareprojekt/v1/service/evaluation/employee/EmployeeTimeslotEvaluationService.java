package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.TimeslotConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeTimeslotEvaluationHardDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeTimeslotEvaluationSoftDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintType;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeTimeslotConstraintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeTimeslotEvaluationService {

    private final EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository;

    private final TimeslotConstraintConverter timeslotConstraintConverter;

    public List<EmployeeTimeslotEvaluationSoftDTO> evaluateSoft(
            String abbreviation, List<WeekEvent> weekEvents) {
        if (weekEvents.isEmpty()) {
            return Collections.emptyList();
        }

        List<EmployeeTimeslotConstraint> constraints =
                employeeTimeslotConstraintRepository.findByEmployeeAbbreviationAndConstraintType(
                        abbreviation, ConstraintType.SOFT);
        List<EmployeeTimeslotConstraint> unapprovedHardConstraints =
                employeeTimeslotConstraintRepository.findByEmployeeAbbreviationAndConstraintTypeAndIsAccepted(
                        abbreviation, ConstraintType.HARD, false);
        constraints.addAll(unapprovedHardConstraints);

        if (constraints.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();

        List<EmployeeTimeslotEvaluationSoftDTO> evaluations = new ArrayList<>();
        List<WeekEvent> weekEventsForWeek;
        for (Integer week : weeks) {
            weekEventsForWeek = weekEvents.stream()
                    .filter(weekEvent -> Objects.equals(weekEvent.getWeek(), week))
                    .toList();
            evaluations.add(evaluateWeekSoft(week, weekEventsForWeek, constraints));
        }
        return evaluations;
    }

    public List<EmployeeTimeslotEvaluationHardDTO> evaluateHard(String employeeAbbreviation,
                                                                List<WeekEvent> weekEvents) {
        if (weekEvents.isEmpty()) {
            return Collections.emptyList();
        }
        List<EmployeeTimeslotConstraint> approvedHardConstraints =
                employeeTimeslotConstraintRepository.findByEmployeeAbbreviationAndConstraintTypeAndIsAccepted(
                        employeeAbbreviation, ConstraintType.HARD, true);
        if (approvedHardConstraints.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> weeks = weekEvents.stream()
                .map(WeekEvent::getWeek)
                .distinct()
                .toList();

        List<EmployeeTimeslotEvaluationHardDTO> evaluations = new ArrayList<>();
        List<WeekEvent> weekEventsForWeek;
        for (Integer week : weeks) {
            weekEventsForWeek = weekEvents.stream()
                    .filter(weekEvent -> Objects.equals(weekEvent.getWeek(), week))
                    .toList();
            evaluations.add(evaluateWeekHard(week, weekEventsForWeek, approvedHardConstraints));
        }
        return evaluations;
    }

    private EmployeeTimeslotEvaluationSoftDTO evaluateWeekSoft(Integer week, List<WeekEvent> weekEvents,
                                                               List<EmployeeTimeslotConstraint> constraints) {
        Map<DayOfWeek, List<Integer>> groupWeekEventsByDay = groupTimeslotIndexesByDay(weekEvents);
        List<EmployeeTimeslotConstraint> harmedConstraints =
                constraints.stream()
                        .filter(isHarmedPredicate(groupWeekEventsByDay))
                        .collect(Collectors.toList());

        int amountSoftConstraints = constraints.size();
        int amountHarmedConstraints = harmedConstraints.size();
        int fulfilledConstraints = amountSoftConstraints - amountHarmedConstraints;

        return EmployeeTimeslotEvaluationSoftDTO.builder()
                .week(week)
                .harmedConstraints(
                        timeslotConstraintConverter.convertToDTOList(harmedConstraints))
                .score((float) (fulfilledConstraints / amountSoftConstraints) * 100)
                .build();
    }

    private EmployeeTimeslotEvaluationHardDTO evaluateWeekHard(Integer week, List<WeekEvent> weekEvents,
                                                               List<EmployeeTimeslotConstraint> constraints) {
        Map<DayOfWeek, List<Integer>> groupWeekEventsByDay = groupTimeslotIndexesByDay(weekEvents);
        List<EmployeeTimeslotConstraintResDTO> harmedConstraints =
                constraints.stream()
                        .filter(isHarmedPredicate(groupWeekEventsByDay))
                        .map(timeslotConstraintConverter::convertToDTO)
                        .toList();

        return EmployeeTimeslotEvaluationHardDTO.builder()
                .week(week)
                .harmedConstraints(harmedConstraints)
                .build();
    }

    private Map<DayOfWeek, List<Integer>> groupTimeslotIndexesByDay(List<WeekEvent> weekEvents) {
        return weekEvents.stream()
                .collect(
                        Collectors.groupingBy(
                                WeekEvent::getWeekday,
                                Collectors.flatMapping(
                                        weekEvent ->
                                                weekEvent.getTimeslots()
                                                        .stream()
                                                        .map(Timeslot::getIndex),
                                        Collectors.toList())));
    }

    private Predicate<EmployeeTimeslotConstraint> isHarmedPredicate(
            Map<DayOfWeek, List<Integer>> dayToTimeslotIndexes) {
        return preference -> {
            List<Integer> timeslots =
                    Optional.ofNullable(dayToTimeslotIndexes.get(preference.getWeekday()))
                            .orElse(Collections.emptyList());

            return switch (preference.getConstraintValue()) {
                case NOT_WANTED -> timeslots.contains(preference.getTimeslotIndex());
                case WANTED -> !timeslots.contains(preference.getTimeslotIndex());
            };
        };
    }
}
