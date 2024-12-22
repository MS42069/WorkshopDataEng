package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.IdentifiableEntity;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Curriculum stores every already allocated time frame Therefor if there arent any intersecting
 * intervals, a queried timeslot is considered available
 *
 * @param <T>
 */
public class Curriculum<T extends IdentifiableEntity> extends Schedule<Interval> {

    /** The entity that owns the curriculum. */
    protected final T owner;

    /** A set of WeekEvents that are planned within this curriculum. */
    protected final Set<WeekEvent> plannedWeekEvent = new HashSet<>();

    /**
     * Constructs a new Curriculum instance.
     *
     * @param owner The entity that owns the curriculum.
     */
    public Curriculum(T owner) {
        this.owner = owner;
    }

    /**
     * Adds a WeekEvent to the curriculum's schedule.
     *
     * @param e The WeekEvent to add.
     * @return true if the WeekEvent was successfully added, false otherwise.
     */
    public boolean addToSchedule(WeekEvent e) {
        assert e.getTimeslots().size() != 0;

        return plannedWeekEvent.add(e);
    }

    /**
     * Removes a WeekEvent from the curriculum's schedule.
     *
     * @param e The WeekEvent to remove.
     * @return true if the WeekEvent was successfully removed, false otherwise.
     */
    public boolean removeFromSchedule(WeekEvent e) {
        assert e.getTimeslots().size() != 0;

        return plannedWeekEvent.remove(e);
    }

    /**
     * Checks if a specific timeslot on a given day is available.
     *
     * @param d The day of the week.
     * @param ts The timeslot to check for availability.
     * @return true if the timeslot is available, false otherwise.
     */
    public boolean isTimeslotAvailable(Integer week, DayOfWeek d, Timeslot ts) {
        return this.isTimeslotAvailable(week, d, ts.getStartTime(), ts.getEndTime());
    }

    /**
     * Checks if a list of timeslots on a given day are all available.
     *
     * @param d The day of the week.
     * @param tss The list of timeslots to check for availability.
     * @return true if all timeslots are available, false otherwise.
     */
    public boolean areTimeslotsAvailable(Integer week, DayOfWeek d, List<Timeslot> tss) {
        for (Timeslot t : tss) {
            if (!this.isTimeslotAvailable(week, d, t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a NavigableSet of intersecting Intervals for a specific day and Interval.
     *
     * @param day The day of the week.
     * @param i The Interval to check for intersections.
     * @return A NavigableSet of intersecting Intervals.
     */
    @Override
    public NavigableSet<Interval> getIntersectingIntervals(
            Integer week, DayOfWeek day, Interval i) {
        if (i.isEmpty()) {
            return new TreeSet<>();
        }

        Map<DayOfWeek, TreeSet<Interval>> weeklySchedule = this.schedule.get(week);
        if (weeklySchedule == null || !weeklySchedule.containsKey(day)) {
            return new TreeSet<>();
        }

        TreeSet<Interval> scheduleOnDay = weeklySchedule.get(day);

        Interval lowestIntervalFound = i;
        boolean foundLowest = false;
        Interval low = scheduleOnDay.floor(lowestIntervalFound);
        while (!foundLowest) {
            if (low == null || !i.isIntersecting(low)) {
                foundLowest = true;
            } else {
                lowestIntervalFound = low;
                low = scheduleOnDay.lower(lowestIntervalFound);
            }
        }

        boolean foundHighest = false;
        Interval highestIntervalFound = i;
        Interval high = scheduleOnDay.ceiling(highestIntervalFound);
        while (!foundHighest) {
            if (high == null || !i.isIntersecting(high)) {
                foundHighest = true;
            } else {
                highestIntervalFound = high;
                high = scheduleOnDay.higher(highestIntervalFound);
            }
        }
        return scheduleOnDay.subSet(lowestIntervalFound, true, highestIntervalFound, true);
    }

    /**
     * Gets a copied schedule for each day of the week.
     *
     * @return A Map containing the copied schedule for each day of the week.
     */
    @Override
    public Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> getSchedule() {
        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> copiedSchedule = new HashMap<>();

        for (Integer week : schedule.keySet()) {
            Map<DayOfWeek, TreeSet<Interval>> weeklySchedule =
                    schedule.getOrDefault(week, new HashMap<>());
            Map<DayOfWeek, NavigableSet<Interval>> copiedWeeklySchedule = new HashMap<>();

            for (DayOfWeek day : weeklySchedule.keySet()) {
                NavigableSet<Interval> cpy = new TreeSet<>();
                for (Interval inter : weeklySchedule.getOrDefault(day, new TreeSet<>())) {
                    Interval in = new Interval();
                    for (Segment s : inter) {
                        in.addSegment(s);
                    }
                    cpy.add(in);
                }
                copiedWeeklySchedule.put(day, cpy);
            }

            copiedSchedule.put(week, copiedWeeklySchedule);
        }

        return copiedSchedule;
    }

    /**
     * Gets a list of WeekEvents that are blocked by events on a specific day and time frame.
     *
     * @param day The day of the week.
     * @param start The start time.
     * @param end The end time.
     * @return A list of WeekEvents that are blocked by other events.
     */
    public List<WeekEvent> getBlockedByEvents(
            Integer week, DayOfWeek day, LocalTime start, LocalTime end) {
        // only check week events planned at the same day
        // for the matching day, find all every WeekEvent with intersecting
        // time frames
        Interval i = new Interval(new Segment(start, end));

        return this.plannedWeekEvent.stream()
                .filter(weekEvent -> weekEvent.getWeek().equals(week))
                .filter(weekEvent -> weekEvent.getWeekday().equals(day))
                .filter(
                        weekEvent ->
                                weekEvent.getTimeslots().stream()
                                        .anyMatch(
                                                timeslot ->
                                                        i.isIntersecting(new Interval(timeslot))))
                .toList();
    }

    public boolean isTimeslotAvailable(
            Integer week, DayOfWeek day, LocalTime start, LocalTime end) {
        Interval i = new Interval(new Segment(start, end));

        return this.getIntersectingIntervals(week, day, i).isEmpty()
                && getBlockedByEvents(week, day, start, end).isEmpty();
    }

    /**
     * Checks if a WeekEvent is admissible to be scheduled in the curriculum.
     *
     * @param event The WeekEvent to check for admissibility.
     * @param s The Scheduler instance.
     * @return true if the WeekEvent is admissible, false otherwise.
     */
    public boolean isTimeslotAdmissable(WeekEvent event, Scheduler s) {
        Course tobeplannedCourse = event.getCourse();
        boolean isAdmissable = true;
        DayOfWeek day = event.getWeekday();
        Integer week = event.getWeek();

        for (Timeslot t : event.getTimeslots()) {

            Interval i = new Interval(new Segment(t.getStartTime(), t.getEndTime()));
            if (!this.getIntersectingIntervals(week, day, i).isEmpty()) {
                return false;
            }

            List<WeekEvent> events =
                    this.getBlockedByEvents(week, day, t.getStartTime(), t.getEndTime());
            if (events.isEmpty()) {
                continue;
            }

            for (WeekEvent e : events) {
                Course c = s.getOptionsForCourse(e.getCourse().getId()).get().getCourse();

                boolean allowedToBeParallel =
                        c.getCourseRelationsA().stream()
                                        .filter(
                                                courseRelation ->
                                                        courseRelation.getRelationType()
                                                                        == RelationType
                                                                                .MUST_BE_PARALLEL
                                                                || courseRelation.getRelationType()
                                                                        == RelationType
                                                                                .MAY_BE_PARALLEL)
                                        .anyMatch(
                                                courseRelation ->
                                                        courseRelation
                                                                        .getCourseA()
                                                                        .getId()
                                                                        .equals(
                                                                                tobeplannedCourse
                                                                                        .getId())
                                                                || courseRelation
                                                                        .getCourseB()
                                                                        .getId()
                                                                        .equals(
                                                                                tobeplannedCourse
                                                                                        .getId()))
                                || c.getCourseRelationsB().stream()
                                        .filter(
                                                courseRelation ->
                                                        courseRelation.getRelationType()
                                                                        == RelationType
                                                                                .MUST_BE_PARALLEL
                                                                || courseRelation.getRelationType()
                                                                        == RelationType
                                                                                .MAY_BE_PARALLEL)
                                        .anyMatch(
                                                courseRelation ->
                                                        courseRelation
                                                                        .getCourseA()
                                                                        .getId()
                                                                        .equals(
                                                                                tobeplannedCourse
                                                                                        .getId())
                                                                || courseRelation
                                                                        .getCourseB()
                                                                        .getId()
                                                                        .equals(
                                                                                tobeplannedCourse
                                                                                        .getId()));

                if (!allowedToBeParallel) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets the owner entity of the curriculum.
     *
     * @return The owner entity.
     */
    public T getOwner() {
        return this.owner;
    }
}
