package com.fhwedel.softwareprojekt.v1.service.evaluation.employee;

import com.fhwedel.softwareprojekt.v1.converter.constraints.BreaksBetweenConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.EmployeeBreaksBetweenEvaluationDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeBreaksBetweenConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeBreaksBetweenConstraintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for evaluating EmployeeBreaksBetweenConstraint.
 */
@Service
@RequiredArgsConstructor
public class EmployeeBreaksBetweenEvaluationService {

	/**
	 * Repository for EmployeeBreaksBetweenConstraint entities.
	 */
	private final EmployeeBreaksBetweenConstraintRepository
			employeeBreaksBetweenConstraintRepository;

	/**
	 * Converter for BreaksBetweenConstraint entities and DTOs.
	 */
	private final BreaksBetweenConstraintConverter breaksBetweenConstraintConverter;

	/**
	 * Evaluates the soft constraint for breaks between weekEvents.
	 *
	 * @param abbreviation The employee's abbreviation.
	 * @param weekEvents   List of weekEvents.
	 * @return An optional containing the evaluation result DTO.
	 */
	List<EmployeeBreaksBetweenEvaluationDTO> evaluate(
			String abbreviation, List<WeekEvent> weekEvents) {
		if (weekEvents.isEmpty()) {
			return Collections.emptyList();
		}

		Optional<EmployeeBreaksBetweenConstraint> employeeConstraint =
				employeeBreaksBetweenConstraintRepository.findByEmployeeAbbreviation(abbreviation);

		if (employeeConstraint.isEmpty()) {
			return Collections.emptyList();
		}

		EmployeeBreaksBetweenConstraint constraint = employeeConstraint.get();
		List<Integer> weeks = weekEvents.stream()
				.map(WeekEvent::getWeek)
				.distinct()
				.toList();

		List<EmployeeBreaksBetweenEvaluationDTO> evaluations = new ArrayList<>();
		List<WeekEvent> weekEventsForWeek;
		for (Integer week : weeks) {
			weekEventsForWeek = weekEvents.stream()
					.filter(weekEvent -> Objects.equals(weekEvent.getWeek(), week))
					.toList();
			evaluations.add(evaluateForWeek(week, weekEventsForWeek, constraint));
		}
		return evaluations;
	}


	private EmployeeBreaksBetweenEvaluationDTO evaluateForWeek(Integer week, List<WeekEvent> weekEvents,
															   EmployeeBreaksBetweenConstraint constraint) {

		Map<DayOfWeek, List<WeekEvent>> weekEventsByDayOrdered = groupWeekEventsFromCoursesByWeekDayOrdered(weekEvents);
		float score;
		int possibleBreaks = getPossibleBreaksAmount(weekEventsByDayOrdered);
		if (possibleBreaks == 0) {
			score = 100;
		} else {
			int amountHarmed =
					constraint.getConstraintValue()
							.equals(ConstraintValue.WANTED)
							? getNonExistingBreaksAmount(weekEventsByDayOrdered)
							: getExistingBreaksAmount(weekEventsByDayOrdered);
			score = (float) (possibleBreaks - amountHarmed) / possibleBreaks * 100;
		}
		return EmployeeBreaksBetweenEvaluationDTO.builder()
				.week(week)
				.constraint(breaksBetweenConstraintConverter.convertToResDTO(constraint))
				.score(score)
				.build();
	}

    /**
     * Groups WeekEvents from courses by weekday and orders them.
     *
     * @param weekEvents List of weekEvents.
     * @return A map of WeekEvents grouped by weekday and ordered.
     */
    private static Map<DayOfWeek, List<WeekEvent>> groupWeekEventsFromCoursesByWeekDayOrdered(
            List<WeekEvent> weekEvents) {
        return weekEvents.stream()
                .collect(
                        Collectors.groupingBy(
                                WeekEvent::getWeekday,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        we ->
                                                we.stream()
                                                        .sorted(
                                                                Comparator.comparing(
                                                                        event ->
                                                                                event.getTimeslots()
                                                                                        .get(0)
                                                                                        .getIndex()))  // Assuming each WeekEvent has at least
                                                        // one Timeslot
                                                        .collect(Collectors.toList()))));
    }

	/**
	 * Calculates the amount of non-existing breaks between courses.
	 *
	 * @param weekEventsByDayOrdered WeekEvents grouped by weekday and ordered.
	 * @return The amount of non-existing breaks.
	 */
	private static int getNonExistingBreaksAmount(Map<DayOfWeek, List<WeekEvent>> weekEventsByDayOrdered) {
		return weekEventsByDayOrdered.values()
				.stream()
				.flatMap(
						weekEvents -> {
							int count = 0;
							for (int i = 0; i < weekEvents.size() - 1; i++) {
								int lastTimeslotOfCurrentEvent =
										weekEvents.get(i)
												.getTimeslots()
												.stream()
												.mapToInt(Timeslot::getIndex)
												.max()
												.orElse(-1);

								int firstTimeslotOfNextEvent =
										weekEvents.get(i + 1)
												.getTimeslots()
												.stream()
												.mapToInt(Timeslot::getIndex)
												.min()
												.orElse(Integer.MAX_VALUE);

								if (lastTimeslotOfCurrentEvent + 1 == firstTimeslotOfNextEvent) {
									count++;
								}
							}
							return Stream.of(count);
						})
				.mapToInt(Integer::intValue)
				.sum();
	}

	/**
	 * Calculates the amount of existing breaks between courses.
	 *
	 * @param weekEventsByDayOrdered WeekEvents grouped by weekday and ordered.
	 * @return The amount of existing breaks.
	 */
	private static int getExistingBreaksAmount(Map<DayOfWeek, List<WeekEvent>> weekEventsByDayOrdered) {
		return weekEventsByDayOrdered.values()
				.stream()
				.flatMap(
						weekEvents -> {
							int count = 0;
							for (int i = 0; i < weekEvents.size() - 1; i++) {
								int lastTimeslotOfCurrentEvent =
										weekEvents.get(i)
												.getTimeslots()
												.stream()
												.mapToInt(Timeslot::getIndex)
												.max()
												.orElse(-1);

								int firstTimeslotOfNextEvent =
										weekEvents.get(i + 1)
												.getTimeslots()
												.stream()
												.mapToInt(Timeslot::getIndex)
												.min()
												.orElse(Integer.MAX_VALUE);

								if (lastTimeslotOfCurrentEvent == firstTimeslotOfNextEvent) {
									count++;
								}
							}
							return Stream.of(count);
						})
				.mapToInt(Integer::intValue)
				.sum();
	}

	/**
	 * Calculates the amount of possible breaks between courses.
	 *
	 * @param weekEventsByDayOrdered WeekEvents grouped by weekday and ordered.
	 * @return The amount of possible breaks.
	 */
	private static int getPossibleBreaksAmount(Map<DayOfWeek, List<WeekEvent>> weekEventsByDayOrdered) {
		return weekEventsByDayOrdered.values()
				.stream()
				.mapToInt(weekEvents -> weekEvents.size() - 1)
				.sum();
	}
}
