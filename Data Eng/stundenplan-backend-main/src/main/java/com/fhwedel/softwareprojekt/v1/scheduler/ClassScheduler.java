package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.CheckedEvent;
import javax.validation.constraints.NotNull;

/**
 * Represents a class scheduler that is used to schedule course appointments. This means both
 * persistence in the form of events and checking the consistency of the schedule (all defined
 * constraints are respected: no two events at the same time in the same room, no overlaps regarding
 * the appointments of an employee and so on).
 *
 * <p>Also provides the functionality to generate so-called "Freiheitsgerade". "Freiheitsgerade"
 * represent the possible options for a course, where it can be scheduled without violating
 * constraints.
 */
public interface ClassScheduler {

    /**
     * Schedule the course and updates all intersecting entities associated by: - Employee -
     * degreesSemester - rooms if the event causes a SchedulerException the new event is not being
     * applied to the schedule
     *
     * @param e the to be scheduled event
     * @param force apply changed event though a conflict has been detected
     * @return true if the event effected other courses
     */
    boolean scheduleEvent(WeekEvent e, boolean force);

    /**
     * Informs the scheduler that a scheduled event was deleted from the database and has to be
     * unscheduled.
     *
     * @param e the deleted event
     */
    boolean unscheduleEvent(WeekEvent e);

    /** Informs the scheduler to reload all dependencies */
    void regenerate();

    /**
     * Searches for all possible week dates on which the given course can be scheduled as {@link
     * WeekEvent} and returns the result as {@link OptionsDTO}.
     *
     * @param course the course
     * @return {@link OptionsDTO} that contains all options, each consisting of day of the week,
     *     timeslot block and room combination, at which the given course can be scheduled.
     */
    OptionsDTO findAllOptionsFor(Course course);

    /**
     * Checks if the given potential event is admissible. This is the case if all constraints are
     * satisfied and no conflicts are encountered due to other scheduled events.
     *
     * <p>The following constraints are checked:
     *
     * <ol>
     *   <li>Constraint that two events must not take place at the same time in the same room
     *   <li>Constraint that an employee is not already scheduled for another event at the same time
     *   <li>Constraint that one {@link DegreeSemester} does not have another course at the same
     *       time
     *   <li>Constraint regarding the block size in terms of timeslots
     *   <li>Constraint regarding the number of slots per week on which the course is to be
     *       scheduled, defined by {@link Course#getSlotsPerWeek()}
     *   <li>Constraint regarding the admissible room combination, defined by {@link
     *       Course#getSuitedRooms()}
     *   <li>Constraint regarding the allowed working hours of an employee associated with the
     *       course, defined by {@link Employee#getWorkTimes()}
     *   <li>Constraint regarding the permitted time slots (defined by {@link
     *       Course#getCourseTimeslots()} at which an event of a course may be scheduled
     *   <li>Constraint regarding the maximum number of timeslots a course may be scheduled for
     *       (defined by {@link Course#getBlockSize()}
     * </ol>
     *
     * @param event the {@link WeekEvent} to check
     * @return true, if the event is admissible, otherwise false.
     */
    CheckedEvent checkAdmissibility(@NotNull WeekEvent event);

    /**
     * Returns the degree of freedom ('Freiheitsgrad') for a course, which describes the number of
     * possible options at which an event of a course can be scheduled without the consideration of
     * rooms.
     *
     * @param c the course
     * @return integer value representing a degree of freedom with respect to the scheduling of the
     *     given course; -1 if the scheduler has not been initialized for the given course
     */
    int getDegreesOfFreedom(@NotNull Course c);

    /**
     * Returns the degree of freedom ('Freiheitsgrad') for a course, which describes the number of
     * possible options at which an event of a course can be scheduled with the consideration of
     * rooms.
     *
     * @param c the course
     * @return integer value representing a degree of freedom with respect to the scheduling of the
     *     given course; -1 if the scheduler has not been initialized for the given course
     */
    int getDegreesOfFreedomWithRooms(@NotNull Course c);
}
