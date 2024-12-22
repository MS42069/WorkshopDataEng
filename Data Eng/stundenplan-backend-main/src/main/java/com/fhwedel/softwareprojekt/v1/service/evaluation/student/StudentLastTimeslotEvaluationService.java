package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * A service for evaluating student schedules based on the presence of events in the last timeslot
 * of the day. This service calculates a score based on the absence of events in the last timeslot.
 */
@Service
public class StudentLastTimeslotEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /** Weight assigned to this evaluation. */
    static final int WEIGHT = 384;

    /** Description of the evaluation criteria. */
    static final String EVENT_IN_LAST_TIMESLOT_DESCRIPTION =
            "bewertet Tage mit Events im letzten Timeslot negativ";

    private static final int LAST_TIMESLOT = 6;

    // 0 Tage -> score = 100
    private static final float[] point1 = new float[] {0, 100};
    // 1 Tage -> score = 90
    private static final float[] point2 = new float[] {1, 90};
    // 5 Tage -> score = 0
    private static final float[] point3 = new float[] {5, 0};

    /**
     * Evaluates the student's weekly schedule and calculates a score based on the presence of
     * events in the last timeslot.
     *
     * @param week The weekly schedule data.
     * @return The evaluation score and weight.
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> daysWithEventInLastTimeslot = getData(week);

        return StudentEvaluationScoreAndWeight.builder()
                .score(
                        Math.round(
                                quadraticDecrease(
                                        point1,
                                        point2,
                                        point3,
                                        daysWithEventInLastTimeslot.size())))
                .weight(WEIGHT)
                .desc(EVENT_IN_LAST_TIMESLOT_DESCRIPTION)
                .build();
    }

    /**
     * Retrieves the set of days with events in the last timeslot based on the schedule data.
     *
     * @param week The weekly schedule data.
     * @return The set of days with events in the last timeslot.
     */
    private Set<DayOfWeek> getData(MultiValueMap<DayOfWeek, WeekEvent> week) {
        return checkWeek(
                week,
                events ->
                        events.stream()
                                .anyMatch(
                                        event ->
                                                event.getTimeslots().stream()
                                                        .anyMatch(
                                                                timeslot ->
                                                                        timeslot.getIndex()
                                                                                == LAST_TIMESLOT)),
                DAY_OF_WEEK_PREDICATE);
    }
}
