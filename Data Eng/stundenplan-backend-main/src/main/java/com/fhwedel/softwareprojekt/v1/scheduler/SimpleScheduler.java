package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.CheckedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Simple implementation of a class scheduler */
@RequiredArgsConstructor
@Slf4j
public class SimpleScheduler implements ClassScheduler {

    /**
     * Schedules a week event.
     *
     * @param e The week event to schedule.
     * @param force If true, forces the scheduling even if there are conflicts.
     * @return true if the scheduling was successful, false otherwise.
     */
    @Override
    public boolean scheduleEvent(WeekEvent e, boolean force) {
        return true;
    }

    /**
     * Unschedules a week event.
     *
     * @param e The week event to unschedule.
     * @return true if the unscheduling was successful, false otherwise.
     */
    @Override
    public boolean unscheduleEvent(WeekEvent e) {
        return false;
    }

    /** Regenerates the scheduling plan. */
    @Override
    public void regenerate() {}

    /**
     * Finds all scheduling options for a given course.
     *
     * @param course The course for which scheduling options are sought.
     * @return An {@link OptionsDTO} containing scheduling options for the course.
     */
    @Override
    public OptionsDTO findAllOptionsFor(Course course) {
        return null;
    }

    /**
     * Checks the admissibility of a week event.
     *
     * @param event The week event to check.
     * @return A {@link CheckedEvent} object containing information about the admissibility check.
     */
    @Override
    public CheckedEvent checkAdmissibility(WeekEvent event) {
        return null;
    }

    /**
     * Retrieves the degrees of freedom for a course.
     *
     * @param c The course for which degrees of freedom are retrieved.
     * @return The degrees of freedom for the course.
     */
    @Override
    public int getDegreesOfFreedom(Course c) {
        return 0;
    }

    /**
     * Retrieves the degrees of freedom for a course with consideration for rooms.
     *
     * @param c The course for which degrees of freedom are retrieved.
     * @return The degrees of freedom for the course with room constraints.
     */
    @Override
    public int getDegreesOfFreedomWithRooms(Course c) {
        return 0;
    }
}
