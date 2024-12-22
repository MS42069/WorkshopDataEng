package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * A service for evaluating student schedules based on the presence of long gaps between events.
 * This service calculates a score based on the presence of long breaks between events.
 */
@Service
public class StudentLongGapsEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    /** Definition of a long gap (in timeslots). */
    private final int DEFINITION_OF_LONG_GAP = 2;

    /** Weight assigned to this evaluation. */
    static final int WEIGHT = 173;

    /** Description of the evaluation criteria. */
    static final String LONG_BREAKS_DESCRIPTION =
            "bewertet Tage mit Events mit langen Pausen negativ (eine lange "
                    + "Pause sind mindestens 2 Zeitslots Pause)";

    // 0 Tage -> score = 100
    private static final float[] point1 = new float[] {0, 100};
    // 1 Tage -> score = 85
    private static final float[] point2 = new float[] {1, 85};
    // 3 Tage oder mehr → score = 0
    private static final float[] point3 = new float[] {3, 0};

    /**
     * Evaluates the student's weekly schedule and calculates a score based on the presence of long
     * gaps between events.
     *
     * @param week The weekly schedule data.
     * @return The evaluation score and weight.
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> dayWithLongGaps = getData(week);

        return StudentEvaluationScoreAndWeight.builder()
                .score(
                        Math.round(
                                quadraticDecrease(point1, point2, point3, dayWithLongGaps.size())))
                .weight(WEIGHT)
                .desc(LONG_BREAKS_DESCRIPTION)
                .build();
    }

    /**
     * Retrieves the set of days with events having long gaps based on the schedule data.
     *
     * @param week The weekly schedule data.
     * @return The set of days with events having long gaps.
     */
    private Set<DayOfWeek> getData(MultiValueMap<DayOfWeek, WeekEvent> week) {
        Set<DayOfWeek> result = new HashSet<>();
        for (Map.Entry<DayOfWeek, List<WeekEvent>> entry : week.entrySet()) {
            List<WeekEvent> events = entry.getValue();

            events.stream()
                    .sorted(
                            (o1, o2) -> {
                                if (!o1.getTimeslots().isEmpty() && !o2.getTimeslots().isEmpty()) {
                                    return o1.getTimeslots()
                                            .get(0)
                                            .getIndex()
                                            .compareTo(o2.getTimeslots().get(0).getIndex());
                                }
                                return 0;
                            })
                    .forEach(
                            new Consumer<>() {
                                int prevIndex = -1;

                                @Override
                                public void accept(WeekEvent weekEvent) {

                                    if (!weekEvent.getTimeslots().isEmpty()) {
                                        int currIndex = weekEvent.getTimeslots().get(0).getIndex();

                                        if (prevIndex != -1
                                                && Math.abs(currIndex - prevIndex)
                                                        > DEFINITION_OF_LONG_GAP) {
                                            result.add(entry.getKey());
                                        }

                                        prevIndex =
                                                weekEvent
                                                        .getTimeslots()
                                                        .get(weekEvent.getTimeslots().size() - 1)
                                                        .getIndex();
                                    }
                                }
                            });
        }

        return result;
    }
}
