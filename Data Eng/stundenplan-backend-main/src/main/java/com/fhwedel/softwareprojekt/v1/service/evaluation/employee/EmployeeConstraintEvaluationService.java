package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeBreaksBetweenEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeDistributionEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeEvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeFreeDayEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeLunchBreakEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeSubsequentTimeslotsEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeTimeslotEvaluationHardDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeTimeslotEvaluationSoftDTO;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for evaluating employee constraints.
 */
@Service
@RequiredArgsConstructor
public class EmployeeConstraintEvaluationService {

    /**
     * Service for evaluating employee timeslot constraints.
     */
    private final EmployeeTimeslotEvaluationService employeeTimeslotEvaluationService;

    /**
     * Service for evaluating employee lunch break constraints.
     */
    private final EmployeeLunchBreakEvaluationService employeeLunchBreakEvaluationService;

    /**
     * Service for evaluating employee free day constraints.
     */
    private final EmployeeFreeDayEvaluationService employeeFreeDayEvaluationService;

    /**
     * Service for evaluating employee breaks between constraints.
     */
    private final EmployeeBreaksBetweenEvaluationService employeeBreaksBetweenEvaluationService;

    /**
     * Service for evaluating employee distribution constraints.
     */
    private final EmployeeDistributionEvaluationService employeeDistributionEvaluationService;

    /**
     * Service for evaluating employee subsequent timeslots constraints.
     */
    private final EmployeeSubsequentTimeslotsEvaluationService
            employeeSubsequentTimeslotsEvaluationService;

    /**
     * Repository for Course entities.
     */
    private final CourseRepository courseRepository;

    /**
     * Evaluates the score for employee constraints.
     *
     * @param timetableId The ID of the timetable.
     * @return The evaluation score DTO.
     */
    public EmployeeEvaluationScoreDTO evaluateEmployeeScore(UUID timetableId) {
        Map<String, List<WeekEvent>> weekEventsOrderedByWeekdayForEmployee =
                getWeekEventsGroupedByWeekDayAndWeekForEmployees(timetableId);

        List<EmployeeEvaluationDTO> employeeEvaluations = new ArrayList<>();

        for (Map.Entry<String, List<WeekEvent>> entry : weekEventsOrderedByWeekdayForEmployee.entrySet()) {
            String employeeAbbreviation = entry.getKey();
            List<WeekEvent> weekEvents = entry.getValue();

            List<EmployeeTimeslotEvaluationSoftDTO> employeesTimeslotEvaluationSoftDTOs =
                    employeeTimeslotEvaluationService.evaluateSoft(
                            employeeAbbreviation, weekEvents);
            List<EmployeeLunchBreakEvaluationDTO> employeesLunchBreakEvaluationDTOs =
                    employeeLunchBreakEvaluationService.evaluate(
                            employeeAbbreviation, weekEvents);
            List<EmployeeFreeDayEvaluationDTO> employeesFreeDayEvaluationDTOs =
                    employeeFreeDayEvaluationService.evaluateWeek(entry.getKey(), weekEvents);
            List<EmployeeBreaksBetweenEvaluationDTO> employeesBreaksBetweenEvaluationDTOs =
                    employeeBreaksBetweenEvaluationService.evaluate(
                            employeeAbbreviation, weekEvents);
            List<EmployeeDistributionEvaluationDTO> employeesDistributionEvaluationDTOs =
                    employeeDistributionEvaluationService.evaluate(
                            employeeAbbreviation, weekEvents);
            List<EmployeeSubsequentTimeslotsEvaluationDTO>
                    employeesSubsequentTimeslotsEvaluationDTOs =
                    employeeSubsequentTimeslotsEvaluationService.evaluate(
                            employeeAbbreviation, weekEvents);

            List<EmployeeTimeslotEvaluationHardDTO>
                    employeeTimeslotEvaluationHardDTOS =
                    employeeTimeslotEvaluationService.evaluateHard(employeeAbbreviation, weekEvents);

            EmployeeEvaluationDTO evaluation =
                    EmployeeEvaluationDTO.builder()
                            .timeslotEvaluationSoft(employeesTimeslotEvaluationSoftDTOs)
                            .timeslotEvaluationHard(employeeTimeslotEvaluationHardDTOS)
                            .lunchBreakEvaluation(employeesLunchBreakEvaluationDTOs)
                            .freeDayEvaluation(employeesFreeDayEvaluationDTOs)
                            .breaksBetweenEvaluation(
                                    employeesBreaksBetweenEvaluationDTOs)
                            .distributionEvaluation(employeesDistributionEvaluationDTOs)
                            .subsequentTimeslotsEvaluation(
                                    employeesSubsequentTimeslotsEvaluationDTOs)
                            .abbreviation(entry.getKey())
                            .build();

            double score =
                    employeesTimeslotEvaluationSoftDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeTimeslotEvaluationSoftDTO::getScore));

            score +=
                    employeesLunchBreakEvaluationDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeLunchBreakEvaluationDTO::getScore));
            score +=
                    employeesFreeDayEvaluationDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeFreeDayEvaluationDTO::getScore));
            score +=
                    employeesBreaksBetweenEvaluationDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeBreaksBetweenEvaluationDTO::getScore));
            score +=
                    employeesDistributionEvaluationDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeDistributionEvaluationDTO::getScore));
            score +=
                    employeesSubsequentTimeslotsEvaluationDTOs.stream()
                            .collect(Collectors.averagingDouble(EmployeeSubsequentTimeslotsEvaluationDTO::getScore));

            int amount = 6;
            if (employeesTimeslotEvaluationSoftDTOs.isEmpty()) {
                amount--;
            }
            if (employeesLunchBreakEvaluationDTOs.isEmpty()) {
                amount--;
            }
            if (employeesFreeDayEvaluationDTOs.isEmpty()) {
                amount--;
            }
            if (employeesBreaksBetweenEvaluationDTOs.isEmpty()) {
                amount--;
            }
            if (employeesDistributionEvaluationDTOs.isEmpty()) {
                amount--;
            }
            if (employeesSubsequentTimeslotsEvaluationDTOs.isEmpty()) {
                amount--;
            }

            if (amount == 0) {
                score = 100;
            } else {
                score /= amount;
            }

            if (amount > 0) {
                evaluation.setScore((int) score);
                employeeEvaluations.add(evaluation);
            }
        }

        float totalScore = calculateTotalScore(employeeEvaluations);

        List<EmployeeEvaluationDTO> listWithoutMaxValues =
                employeeEvaluations.stream()
                        .filter(elem -> elem.getScore() != 100)
                        .toList();

        listWithoutMaxValues.forEach(elem -> {
            elem.getTimeslotEvaluationHard()
                    .removeIf(
                            timeslotEvaluationDTO -> timeslotEvaluationDTO.getHarmedConstraints()
                                    .isEmpty()
                    );
        });

        return EmployeeEvaluationScoreDTO.builder()
                .employeeEvaluations(listWithoutMaxValues)
                .totalScore(totalScore)
                .build();
    }

    /**
     * Calculates the total score for employee evaluations.
     *
     * @param evaluations The list of employee evaluations.
     * @return The total score.
     */
    private float calculateTotalScore(List<EmployeeEvaluationDTO> evaluations) {
        return (float)
                evaluations.stream()
                        .mapToDouble(EmployeeEvaluationDTO::getScore)
                        .average()
                        .orElse(100);
    }

    private Map<String, List<WeekEvent>> getWeekEventsGroupedByWeekDayAndWeekForEmployees(
            UUID timetableId) {
        return courseRepository.findByTimetableId(timetableId)
                .stream()
                .flatMap(course ->
                        course.getLecturers()
                                .stream()
                                .flatMap(lecturer ->
                                        course.getWeekEvents()
                                                .stream()
                                                .map(weekEvent ->
                                                        new AbstractMap.SimpleEntry<>(
                                                                lecturer.getAbbreviation(),
                                                                weekEvent
                                                        )
                                                )
                                )
                )
                .collect(
                        Collectors.groupingBy(
                                AbstractMap.SimpleEntry::getKey,
                                Collectors.mapping(
                                        AbstractMap.SimpleEntry::getValue,
                                        Collectors.toList()
                                )
                        )
                );
    }
}
