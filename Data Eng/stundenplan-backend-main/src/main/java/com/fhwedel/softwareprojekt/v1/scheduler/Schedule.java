package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.data.util.Pair;

/**
 * Abstract class representing a schedule for events on specific days of the week.
 *
 * @param <T> The type of interval used in the schedule.
 */
public abstract class Schedule<T extends Interval> implements Iterable<Pair<DayOfWeek, List<T>>> {

    /**
     * A mapping of each day of the week to a TreeSet of intervals representing the schedule. The
     * intervals are sorted within the TreeSet.
     */
    protected final Map<Integer, Map<DayOfWeek, TreeSet<T>>> schedule = new HashMap<>();

    /**
     * Groups intervals into blocks of a desired size for a given day.
     *
     * @param intervals The intervals to group.
     * @param block The desired size of intervals.
     * @param allowedOverhang The allowed overhang between intervals.
     * @return Set of intervals that fulfill the desired block size (if any).
     */
    public static NavigableSet<Interval> groupIntervalsInBlocksForDay(
            NavigableSet<Interval> intervals, int block, LocalTime allowedOverhang) {
        NavigableSet<Segment> sortedIntervals = new TreeSet<>();
        // for Every interval get all intervals Segments
        for (Interval inter : intervals) {
            for (Segment seg : inter) {
                sortedIntervals.add(seg);
            }
        }

        NavigableSet<Interval> intervalsBlocks = new TreeSet<>();
        List<Segment> segments = sortedIntervals.stream().toList();

        // For Every interval segment found do the following ...
        // 1. Get the segment associated by the index
        // 2. For Every successor do the following
        // - While the desired block size isn't reached we do not overreach
        // - Determine Successor by seek index and offset by the current index
        // - Check if successor is an actual temporal successor (8:00 is followed by
        // 9:00 -> true successor)
        // - Check if the successor is in reach defined by the all allowedOverhang
        // threshold (due to timeslots being disjointed due to break time)
        // - Valid Candidate has been found and therefor we append to the interval

        for (int index = 0;
                index < segments.size() && (index + block <= segments.size());
                index++) {
            Segment currentSegment = segments.get(index);
            Interval inter = new Interval(currentSegment);

            // After the first segment has been found, which is out of reach
            // skip the entire search for the current index due to no
            boolean canSkip = false;
            int seekIndex = index + 1;

            while (!canSkip && inter.size() < block && (seekIndex < segments.size())) {
                Segment nextSegment = segments.get(seekIndex);
                seekIndex++;
                if (!Segment.isBefore(currentSegment, nextSegment)) {
                    continue;
                }

                if (!Interval.inReach(
                        currentSegment.getStart(),
                        currentSegment.getEnd(),
                        nextSegment.getStart(),
                        nextSegment.getEnd(),
                        allowedOverhang)) {
                    // Due to the list being sorted
                    // Every next Segment will be out of reach after first occurrence of said
                    // segment
                    canSkip = true;
                    continue;
                }

                inter.addSegment(nextSegment.getStart(), nextSegment.getEnd());
                currentSegment = nextSegment;
            }

            // Only add Interval, if criteria has been fulfilled
            if (inter.size() == block) {
                intervalsBlocks.add(inter);
            }
        }
        return intervalsBlocks;
    }

    /**
     * Group intervals takes a Schedule and creates a new Set of Intervals with the desired block
     * size for the provided Day
     *
     * @param s The Schedule in which the groups should be created
     * @param d Day
     * @param block Desired size of intervals
     * @return Set of intervals which fulfill the desired block size (if any)
     */
    public static NavigableSet<Interval> groupIntervalsInBlocksForDay(
            Map<DayOfWeek, NavigableSet<Interval>> s, DayOfWeek d, int block) {
        return groupIntervalsInBlocksForDay(
                s.getOrDefault(d, new TreeSet<>()), block, Interval.DEFAULT_BREAK_DURATION);
    }

    /**
     * Groups intervals into blocks for all days of the week.
     *
     * @param s The schedule containing intervals.
     * @param block The desired size of intervals.
     * @param allowedOverhang The allowed overhang between intervals.
     * @return Map of days of the week to sets of intervals.
     */
    public static NavigableSet<Interval> groupIntervalsInBlocksForDay(
            Map<DayOfWeek, NavigableSet<Interval>> s,
            DayOfWeek d,
            int block,
            LocalTime allowedOverhang) {
        return groupIntervalsInBlocksForDay(
                s.getOrDefault(d, new TreeSet<>()), block, allowedOverhang);
    }

    /**
     * Merges timeslots from different schedules into a new schedule.
     *
     * @param days The days of the week to consider.
     * @param tss The timeslots to merge.
     * @param schedules The schedules containing timeslots.
     * @return Map of days of the week to sets of intervals.
     */
    public static Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> groupIntervalsInBlocks(
            Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> schedules,
            int block,
            LocalTime allowedOverhang) {

        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> groups = new HashMap<>();
        for (Integer week : schedules.keySet()) {
            Map<DayOfWeek, NavigableSet<Interval>> group = new HashMap<>();
            groups.put(week, group);
            for (DayOfWeek d : DayOfWeek.values()) {
                NavigableSet<Interval> ts =
                        Schedule.groupIntervalsInBlocksForDay(
                                schedules.get(week), d, block, allowedOverhang);
                group.put(d, ts);
            }
        }

        return groups;
    }

    /**
     * Groups intervals in blocks for each day of the week with the specified block size and default
     * allowed overhang duration.
     *
     * @param s A map of day-of-the-week to sets of intervals.
     * @param block The desired size of intervals.
     * @return A map of day-of-the-week to sets of intervals grouped in blocks.
     */
    public static Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> groupIntervalsInBlocks(
            Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> s, int block) {
        return Schedule.groupIntervalsInBlocks(s, block, Interval.DEFAULT_BREAK_DURATION);
    }

    /**
     * Merges timeslots and schedules to create a new schedule for each day of the week.
     *
     * @param days The set of days for which to create a new schedule.
     * @param tss The set of timeslots to merge.
     * @param schedules The set of schedules to consider for merging.
     * @return A map of day-of-the-week to sets of merged intervals.
     */
    public static Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> merge(
            Set<Integer> weeks,
            Set<DayOfWeek> days,
            Set<Timeslot> tss,
            Set<? extends Schedule<?>> schedules) {
        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> newSchedule = new HashMap<>();

        for (Integer week : weeks) {
            Map<DayOfWeek, NavigableSet<Interval>> weeklySchedule = new HashMap<>();
            for (DayOfWeek dw : days) {
                TreeSet<Interval> day = new TreeSet<>();
                for (Timeslot ts : tss) {
                    Interval inter = new Interval(new Segment(ts.getStartTime(), ts.getEndTime()));
                    boolean timeSlotAvailable = true;

                    Iterator<? extends Schedule<?>> iterator = schedules.iterator();
                    while (iterator.hasNext() && timeSlotAvailable) {
                        Schedule<?> current = iterator.next();
                        if (current == null) {
                            continue;
                        }

                        timeSlotAvailable =
                                current.isTimeslotAvailable(
                                        week, dw, ts.getStartTime(), ts.getEndTime());
                    }

                    if (timeSlotAvailable) {
                        day.add(inter);
                    }
                }
                weeklySchedule.put(dw, day);
            }
            newSchedule.put(week, weeklySchedule);
        }
        return newSchedule;
    }

    public static String logRepresentation(@NotNull WeekEvent e) {
        return String.format(
                "WeekEvent(Week:%d, %s::%s TimeFrame[%s]:%d Rooms[%s])",
                e.getWeek(),
                (e.getId() != null ? e.getId() : "uninitialized").toString().substring(0, 12),
                e.getWeekday(),
                Interval.from(e.getTimeslots()),
                e.getTimeslots().size(),
                e.getRooms().stream()
                        .map(Room::getId)
                        .map(id -> id.toString().substring(0, 12))
                        .toList());
    }

    /**
     * Adds an interval to the schedule for a specific day.
     *
     * @param day The day of the week.
     * @param i The interval to add.
     * @return True if the interval was added successfully.
     */
    public <E extends T> boolean add(Integer week, DayOfWeek day, E i) {
        Map<DayOfWeek, TreeSet<T>> weeklySchedule =
                this.schedule.getOrDefault(week, new HashMap<>());
        TreeSet<T> scheduleOnDay = weeklySchedule.getOrDefault(day, new TreeSet<>());

        boolean ok = scheduleOnDay.add(i);

        weeklySchedule.put(day, scheduleOnDay);
        this.schedule.put(week, weeklySchedule);

        return ok;
    }

    /**
     * Removes an interval from the schedule for a specific day.
     *
     * @param day The day of the week.
     * @param i The interval to remove.
     * @return The removed intervals.
     */
    public NavigableSet<T> remove(Integer week, DayOfWeek day, T i) {
        Map<DayOfWeek, TreeSet<T>> weeklySchedule = this.schedule.get(week);
        if (weeklySchedule == null) {
            return new TreeSet<>();
        }

        NavigableSet<T> intersectingIntervals = this.getIntersectingIntervals(week, day, i);
        if (intersectingIntervals.isEmpty()) {
            return intersectingIntervals;
        }

        TreeSet<T> scheduleOnDay = weeklySchedule.get(day);
        if (scheduleOnDay != null) {
            scheduleOnDay.removeAll(intersectingIntervals);
        }

        return intersectingIntervals;
    }

    /**
     * Retrieves all intersecting intervals for a specific day and interval.
     *
     * @param day The day of the week.
     * @param i The interval to check for intersections.
     * @return A set of intersecting intervals.
     */
    public abstract NavigableSet<T> getIntersectingIntervals(
            Integer week, DayOfWeek day, Interval i);

    /**
     * Returns the total number of intervals in the schedule.
     *
     * @return The total number of intervals.
     */
    public int size() {
        int size = 0;
        for (Map<DayOfWeek, TreeSet<T>> weeklySchedule : this.schedule.values()) {
            for (TreeSet<T> dailySchedule : weeklySchedule.values()) {
                size += dailySchedule.size();
            }
        }
        return size;
    }

    /**
     * Only for testing
     *
     * @return map of planned intervals
     */
    abstract Map<Integer, Map<DayOfWeek, NavigableSet<T>>> getSchedule();

    /**
     * Retrieves the schedule for a specific day of the week.
     *
     * @param dw The day of the week.
     * @return The schedule for the specified day.
     */
    public NavigableSet<T> getScheduleForDay(Integer week, DayOfWeek dw) {
        Map<DayOfWeek, TreeSet<T>> weeklySchedule =
                this.schedule.getOrDefault(week, new HashMap<>());
        return weeklySchedule.getOrDefault(dw, new TreeSet<>());
    }

    /**
     * Finds intervals matching all given timeslots for a specific day.
     *
     * @param dw The day of the week.
     * @param ts The list of timeslots to match.
     * @return A set of matching intervals.
     */
    public Set<T> findMatchingAllTimeslots(Integer week, DayOfWeek dw, List<Timeslot> ts) {
        Map<DayOfWeek, TreeSet<T>> weeklySchedule =
                this.schedule.getOrDefault(week, new HashMap<>());
        TreeSet<T> scheduleForDay = weeklySchedule.getOrDefault(dw, new TreeSet<>());

        return scheduleForDay.stream()
                .filter(
                        segments ->
                                ts.stream().map(Interval::new).allMatch(segments::isIntersecting))
                .collect(Collectors.toSet());
    }

    /**
     * Finds intervals matching any of the given timeslots for a specific day.
     *
     * @param dw The day of the week.
     * @param ts The list of timeslots to match.
     * @return A set of matching intervals.
     */
    public Set<T> findMatchingAnyTimeslots(Integer week, DayOfWeek dw, List<Timeslot> ts) {
        Map<DayOfWeek, TreeSet<T>> weeklySchedule =
                this.schedule.getOrDefault(week, new HashMap<>());
        TreeSet<T> scheduleForDay = weeklySchedule.getOrDefault(dw, new TreeSet<>());

        return scheduleForDay.stream()
                .filter(
                        segments ->
                                ts.stream().map(Interval::new).anyMatch(segments::isIntersecting))
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a timeslot is available for scheduling on a specific day.
     *
     * @param d The day of the week.
     * @param start The start time of the timeslot.
     * @param end The end time of the timeslot.
     * @return True if the timeslot is available.
     */
    public abstract boolean isTimeslotAvailable(
            Integer week, DayOfWeek d, LocalTime start, LocalTime end);

    /**
     * Returns an iterator over pairs of days of the week and lists of intervals.
     *
     * @return An iterator over pairs of days and intervals.
     */
    @Override
    public Iterator<Pair<DayOfWeek, List<T>>> iterator() {
        List<Pair<DayOfWeek, List<T>>> combinedList = new ArrayList<>();

        for (Map<DayOfWeek, TreeSet<T>> weeklySchedule : this.schedule.values()) {
            for (DayOfWeek day : weeklySchedule.keySet()) {
                combinedList.add(Pair.of(day, new ArrayList<>(weeklySchedule.get(day))));
            }
        }

        return combinedList.iterator();
    }
}
