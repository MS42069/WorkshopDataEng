package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * A service for evaluating student schedules based on the presence of days with only one event.
 * This service calculates a score based on the number of days with only one event, where more such
 * days result in a lower score.
 */
@Service
public class StudentOneEventOnlyEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /** Weight assigned to this evaluation. */
    static final int WEIGHT = 447;

    /** Description of the evaluation criteria. */
    private static final String DAYS_WITH_ONE_EVENT_DESCRIPTION =
            "bewertet Tage mit nur einem Event negativ. (viele"
                    + " Tage mit nur einem Event = niedriger Score)";

    /**
     * Evaluates the student's weekly schedule and calculates a score based on the number of days
     * with only one event.
     *
     * @param week The weekly schedule data.
     * @return The evaluation score and weight.
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> daysWithOneEvent = getData(week);
        // 0 days -> 100
        float[] point1 = new float[] {0, 100};
        // 2 days -> 40
        float[] point2 = new float[] {2, 50};
        // 4 or more days -> 0
        float[] point3 = new float[] {3, 10};

        return StudentEvaluationScoreAndWeight.builder()
                .score(
                        Math.round(
                                quadraticDecrease(point1, point2, point3, daysWithOneEvent.size())))
                .weight(WEIGHT)
                .desc(DAYS_WITH_ONE_EVENT_DESCRIPTION)
                .build();
    }

    /**
     * Retrieves the set of days with only one event based on the schedule data.
     *
     * @param week The weekly schedule data.
     * @return The set of days with only one event.
     */
    private Set<DayOfWeek> getData(MultiValueMap<DayOfWeek, WeekEvent> week) {
        return checkWeek(week, events -> events.size() == 1, DAY_OF_WEEK_PREDICATE);
    }
}
