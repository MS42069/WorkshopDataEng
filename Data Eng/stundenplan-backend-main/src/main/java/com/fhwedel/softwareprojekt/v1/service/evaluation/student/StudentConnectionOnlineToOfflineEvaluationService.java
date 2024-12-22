package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/** Service for evaluating student soft constraints related to online-to-offline connections. */
@Service
public class StudentConnectionOnlineToOfflineEvaluationService implements SoftConstraintStudent {
    // TODO: Anpassung an WeeklyPlanning wenn das fertig ist

    // Weight assigned to this evaluation
    static final int WEIGHT = 413;

    // Description of the evaluation
    static final String CONNECTIONS_ONLINE_TO_OFFLINE_DESCRIPTION =
            "bewertet Tage mit einer Online "
                    + "Veranstaltung in direkter Folge auf eine Präsenzveranstaltung oder anders herum negativ.";
    // prozentsatz wieviele der Connections badConnections sind
    // 50% -> 0
    private static final float[] POINT_1 = new float[] {0, 100};
    // 15% -> 90
    private static final float[] POINT_2 = new float[] {2, 65};
    // 0%  -> 100
    private static final float[] POINT_3 = new float[] {4, 0};

    /**
     * Evaluates the student's preference for online-to-offline connections in the weekly schedule.
     *
     * @param week MultiValueMap containing WeekEvents grouped by DayOfWeek
     * @return StudentEvaluationScoreAndWeight object containing the score and weight for this
     *     evaluation
     */
    @Override
    public StudentEvaluationScoreAndWeight evaluate(MultiValueMap<DayOfWeek, WeekEvent> week) {
        int badConnections = getData(week);
        return StudentEvaluationScoreAndWeight.builder()
                .score(Math.round(quadraticDecrease(POINT_1, POINT_2, POINT_3, badConnections)))
                .weight(WEIGHT)
                .desc(CONNECTIONS_ONLINE_TO_OFFLINE_DESCRIPTION)
                .build();
    }

    /**
     * Calculates the number of bad online-to-offline connections in the weekly schedule.
     *
     * @param week MultiValueMap containing WeekEvents grouped by DayOfWeek
     * @return The number of bad online-to-offline connections
     */
    private Integer getData(MultiValueMap<DayOfWeek, WeekEvent> week) {
        int numOfBadConnections = 0;

        for (Map.Entry<DayOfWeek, List<WeekEvent>> entry : week.entrySet()) {
            List<WeekEvent> events = entry.getValue();
            // für jedes Event checken ob es ein nachfolgendes Event mit einer anderen
            // Lokation(online/offline gibt)
            for (WeekEvent event : events) {
                for (WeekEvent otherEvent : events) {
                    if (isBadConnection(event, otherEvent)) numOfBadConnections++;
                }
            }
        }
        return numOfBadConnections;
    }

    /**
     * Determines if two WeekEvents represent a bad online-to-offline connection.
     *
     * @param event The first WeekEvent
     * @param otherEvent The second WeekEvent
     * @return True if the connection is bad, false otherwise
     */
    private boolean isBadConnection(WeekEvent event, WeekEvent otherEvent) {
        // liegt otherEvent direkt hinter event?
        if (!haveTimeConnection(event, otherEvent)) return false;

        // haben diese beiden Veranstaltungen einen Übergang von online zu Offline
        Location eventLocation = getLocation(event.getRooms());
        Location otherEventLocation = getLocation(otherEvent.getRooms());

        if (eventLocation == Location.HYBRID || otherEventLocation == Location.HYBRID) return false;
        else return eventLocation != otherEventLocation;
    }

    /**
     * Checks if two WeekEvents have a time connection (adjacent timeslots).
     *
     * @param event The first WeekEvent
     * @param otherEvent The second WeekEvent
     * @return True if there is a time connection, false otherwise
     */
    private boolean haveTimeConnection(WeekEvent event, WeekEvent otherEvent) {
        Optional<Integer> lastTimeslotEvent =
                event.getTimeslots().stream().map(Timeslot::getIndex).max(Integer::compareTo);
        Optional<Integer> firstTimeslotOtherEvent =
                otherEvent.getTimeslots().stream().map(Timeslot::getIndex).min(Integer::compareTo);
        return lastTimeslotEvent.isPresent()
                && firstTimeslotOtherEvent.isPresent()
                && lastTimeslotEvent.get() == firstTimeslotOtherEvent.get() - 1;
    }

    /**
     * Determines the location type (online, offline, hybrid) based on a list of rooms.
     *
     * @param rooms The list of rooms for a WeekEvent
     * @return The location type
     */
    private Location getLocation(List<Room> rooms) {
        boolean online = false;
        boolean offline = false;
        for (Room room : rooms) {
            if (room.getRoomType().getOnline()) online = true;
            else offline = true;
        }
        if (online && offline) return Location.HYBRID;
        else if (online) return Location.ONLINE;
        else return Location.OFFLINE;
    }

    /** Describes whether a room offers online, offline, or hybrid events. */
    enum Location {
        ONLINE,
        OFFLINE,
        HYBRID
    }
}
