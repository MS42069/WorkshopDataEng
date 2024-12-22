package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * A service for evaluating student schedules based on the presence of free days in the week. This
 * service calculates a score based on the availability of free days, with a focus on having Mondays
 * or Fridays off.
 */
@Service
public class StudentFreeDaysEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /** Weight assigned to this evaluation. */
    static final int WEIGHT = 478;

    /** Description of the evaluation criteria. */
    private static final String FREE_DAYS_DESCRIPTION =
            "bewertet Positiv wenn der Student einen Freien Tag in der Woche hat. Besonders gut wird es bewertet, wenn Montag oder Freitag frei ist.";

    /**
     * Evaluates the student's weekly schedule and calculates a score based on the presence of free
     * days.
     *
     * @param week The weekly schedule data.
     * @return The evaluation score and weight.
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> freeDays = getData(week);
        int score;
        if (freeDays.isEmpty()) {
            // WORST-CASE kein freier Tag -> score = WORST_SCORE
            score = WORST_SCORE;
        } else if (freeDays.contains(DayOfWeek.MONDAY) || freeDays.contains(DayOfWeek.FRIDAY)) {
            // BEST-CASE 1 Freier Tag am Montag oder Freitag -> score = 100
            score = BEST_SCORE;
        } else {
            // MIDDLE 1 freier Tag Dienstag - Donnerstag -> score = 80
            score = FREE_DAY_TUESDAY_THURSDAY_SCORE;
        }

        return StudentEvaluationScoreAndWeight.builder()
                .score(score)
                .weight(WEIGHT)
                .desc(FREE_DAYS_DESCRIPTION)
                .build();
    }

    /**
     * Retrieves the set of free days in the week based on the absence of events.
     *
     * @param week The weekly schedule data.
     * @return The set of free days.
     */
    private Set<DayOfWeek> getData(MultiValueMap<DayOfWeek, WeekEvent> week) {
        return checkWeek(week, List::isEmpty, DAY_OF_WEEK_PREDICATE);
    }
}
