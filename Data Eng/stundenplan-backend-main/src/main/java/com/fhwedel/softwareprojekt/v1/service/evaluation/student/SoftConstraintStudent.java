package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.springframework.util.MultiValueMap;

/** Interface for evaluating soft constraints for students. */
public interface SoftConstraintStudent {
    /** The worst score value. */
    int WORST_SCORE = 0;

    /** The best score value. */
    int BEST_SCORE = 100;

    /** The score value for a free day on Tuesday or Thursday. */
    int FREE_DAY_TUESDAY_THURSDAY_SCORE = 80;

    /** The index of the first timeslot. */
    int FIRST_TIMESLOT = 0;

    /**
     * A predicate that checks if a given day of the week is within the range of valid days (Monday
     * to Friday).
     */
    Predicate<DayOfWeek> DAY_OF_WEEK_PREDICATE = day -> day.getValue() >= 1 && day.getValue() <= 5;

    /**
     * Evaluates a student's week based on soft constraints and returns a score and weight.
     *
     * @param week The week to be evaluated.
     * @return The evaluation result containing both the score and weight.
     */
    StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week);

    /**
     * Evaluates a quadratic decrease function based on given points.
     *
     * @param point1 The first point as an array containing x and y values.
     * @param point2 The second point as an array containing x and y values.
     * @param point3 The third point as an array containing x and y values.
     * @param x The input value for the quadratic decrease function.
     * @return The result of the quadratic decrease function.
     * @throws IllegalArgumentException If the input points do not contain exactly 2 values each.
     */
    default float quadraticDecrease(float[] point1, float[] point2, float[] point3, float x) {
        if (point1.length != 2 || point2.length != 2 || point3.length != 2)
            throw new IllegalArgumentException(
                    "Punkte in quadraticDecrease müssen immer 2 Werte enthalten");
        return Math.max(
                WORST_SCORE,
                (Math.min(BEST_SCORE, (x - point2[0]) * (x - point3[0]))
                                / ((point1[0] - point2[0]) * (point1[0] - point3[0]))
                                * point1[1]
                        + ((x - point1[0]) * (x - point3[0]))
                                / ((point2[0] - point1[0]) * (point2[0] - point3[0]))
                                * point2[1]
                        + ((x - point1[0]) * (x - point2[0]))
                                / ((point3[0] - point1[0]) * (point3[0] - point2[0]))
                                * point3[1]));
    }

    /**
     * Evaluates a week with given predicates.
     *
     * @param week The week to be evaluated
     * @param weekEventPredicate Predicate checking the weekEvents of all days
     * @param dayOfWeekPredicate Predicate excluding certain days from the evaluation (e.g. the
     *     weeekend)
     * @return A set of days that fulfill the predicates
     */
    default Set<DayOfWeek> checkWeek(
            MultiValueMap<DayOfWeek, WeekEvent> week,
            Predicate<List<WeekEvent>> weekEventPredicate,
            Predicate<DayOfWeek> dayOfWeekPredicate) {

        Set<DayOfWeek> days = new HashSet<>();

        for (Map.Entry<DayOfWeek, List<WeekEvent>> entry : week.entrySet()) {
            DayOfWeek day = entry.getKey();
            List<WeekEvent> events = entry.getValue();

            if (weekEventPredicate.test(events) && dayOfWeekPredicate.test(day)) days.add(day);
        }
        return days;
    }
}
