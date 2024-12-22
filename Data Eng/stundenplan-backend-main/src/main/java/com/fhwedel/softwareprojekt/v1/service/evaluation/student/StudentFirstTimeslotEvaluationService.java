package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * A service for evaluating student schedules based on the presence of events in the first timeslot.
 * This service calculates a score based on the number of days with events in the first timeslot.
 */
@Service
public class StudentFirstTimeslotEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /** The weight value used in calculations. */
    static final int WEIGHT = 358;

    /** Description for the event in the first timeslot condition. */
    private static final String EVENT_IN_FIRST_TIMESLOT_DESCRIPTION =
            "bewertet Tage mit Events im ersten Timeslot " + "negativ";

    /** The index of the first timeslot. */
    private static final int FIRST_TIMESLOT = 0;

    // 0 Tage -> score = 100
    private static final float[] POINT_1 = new float[] {0, 100};
    // 1 Tage -> score = 90
    private static final float[] POINT_2 = new float[] {1, 90};
    // 5 Tage -> score = 0
    private static final float[] POINT_3 = new float[] {5, 0};

    /**
     * Evaluates the student's weekly schedule and calculates a score based on the presence of
     * events in the first timeslot.
     *
     * @param week The weekly schedule data.
     * @return The evaluation score and weight.
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> daysWithEventInFirstTimeslot = getData(week);
        return StudentEvaluationScoreAndWeight.builder()
                .score(
                        Math.round(
                                quadraticDecrease(
                                        POINT_1,
                                        POINT_2,
                                        POINT_3,
                                        daysWithEventInFirstTimeslot.size())))
                .weight(WEIGHT)
                .desc(EVENT_IN_FIRST_TIMESLOT_DESCRIPTION)
                .build();
    }

    /**
     * Retrieves the set of days with events in the first timeslot.
     *
     * @param week The weekly schedule data.
     * @return The set of days with events in the first timeslot.
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
                                                                                == FIRST_TIMESLOT)),
                DAY_OF_WEEK_PREDICATE);
    }
}
