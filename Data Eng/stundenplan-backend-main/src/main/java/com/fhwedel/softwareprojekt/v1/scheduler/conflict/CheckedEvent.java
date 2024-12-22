package com.fhwedel.softwareprojekt.v1.scheduler.conflict;

import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.ClassScheduler;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

/**
 * Represents the result of {@link ClassScheduler#checkAdmissibility(WeekEvent)}. Wraps an event and
 * contains encountered conflicts or violated constraints.
 */
@ToString
public class CheckedEvent {

    /** The event whose admissibility was checked */
    private final WeekEvent event;
    /** Contains all encountered conflicts */
    private final List<Conflict> conflicts = new ArrayList<>();

    public CheckedEvent(WeekEvent event) {
        this.event = event;
    }

    /**
     * Returns whether the checked event fulfills all constraints or causes conflicts.
     *
     * @return true, if the event is causing conflicts; false, if the even does not cause conflicts
     *     and therefore is admissible.
     */
    public boolean isCausingConflict() {
        return conflicts.size() != 0;
    }

    /**
     * Adds the given conflict.
     *
     * @param conflict the conflict to add
     */
    public void addConflict(Conflict conflict) {
        conflicts.add(conflict);
    }

    /**
     * Returns all added conflicts
     *
     * @return list of conflicts.
     */
    public List<Conflict> getConflicts() {
        return new ArrayList<>(conflicts);
    }

    /**
     * Returns the event whose admissibility was checked by the scheduler.
     *
     * @return checked event
     */
    public WeekEvent getEvent() {
        return this.event;
    }
}
