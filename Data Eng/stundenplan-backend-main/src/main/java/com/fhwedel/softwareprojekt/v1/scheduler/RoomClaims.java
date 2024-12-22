package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

/**
 * RoomClaims stores for a single room, all the claims made by every option associated with every
 * course for a given (week)day and specified duration (timeslots) every single claim will be
 * represented by a RoomClaim Example: For Room HS1 there are claims consisting of: - AUD Monday
 * [8:00-9:15,9:30-10:45] - FOOP Monday [8:00-9:15,9:30-10:45] - Analysis Monday [8:00-9:15]
 */
public class RoomClaims extends Schedule<RoomClaim> {

    /** The room associated with these claims. */
    private final Room room;

    /** A set of WeekEvents that have claimed this room. */
    private final Set<WeekEvent> claimedByEvents = new HashSet<>();

    /**
     * Constructs a RoomClaims instance for the specified room.
     *
     * @param r The room for which claims will be stored.
     */
    public RoomClaims(@NotNull Room r) {
        this.room = r;
    }

    /**
     * Retrieves the room associated with these claims.
     *
     * @return The room for which claims are stored.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Retrieves a set of RoomClaims that intersect with the specified day and interval. An empty
     * set is returned if there are no intersecting claims.
     *
     * @param day The day of the week to check.
     * @param i The interval (timeslots) to check.
     * @return A set of intersecting RoomClaims.
     */
    @Override
    public NavigableSet<RoomClaim> getIntersectingIntervals(
            Integer week, DayOfWeek day, Interval i) {
        if (i.isEmpty()) {
            return new TreeSet<>();
        }

        Map<DayOfWeek, TreeSet<RoomClaim>> weeklySchedule = this.schedule.get(week);
        if (weeklySchedule == null) {
            return new TreeSet<>();
        }

        TreeSet<RoomClaim> scheduleOnDay = weeklySchedule.get(day);
        if (scheduleOnDay == null) {
            return new TreeSet<>();
        }

        return scheduleOnDay.stream()
                .filter(segments -> segments.isIntersecting(i))
                .collect(Collectors.toCollection(TreeSet::new));
        //
        //        Interval lowestIntervalFound = i;
        //        boolean foundLowest = false;
        //        Interval low = scheduleOnDay.floor(lowestIntervalFound);
        //        while (!foundLowest) {
        //            if (low == null || !i.isIntersecting(low)) {
        //                foundLowest = true;
        //            } else {
        //                lowestIntervalFound = low;
        //                low = scheduleOnDay.lower(lowestIntervalFound);
        //            }
        //        }
        //
        //        boolean foundHighest = false;
        //        Interval highestIntervalFound = i;
        //        Interval high = scheduleOnDay.ceiling(highestIntervalFound);
        //        while (!foundHighest) {
        //            if (high == null || !i.isIntersecting(high)) {
        //                foundHighest = true;
        //            } else {
        //                highestIntervalFound = high;
        //                high = scheduleOnDay.higher(highestIntervalFound);
        //            }
        //        }
        //        return scheduleOnDay.subSet(lowestIntervalFound, true, highestIntervalFound,
        // true);
        //
        //
        //
        //        return null;
    }

    /**
     * Retrieves a copy of the schedule, with all RoomClaims for each day of the week.
     *
     * @return A copy of the schedule with RoomClaims.
     */
    @Override
    public Map<Integer, Map<DayOfWeek, NavigableSet<RoomClaim>>> getSchedule() {
        Map<Integer, Map<DayOfWeek, NavigableSet<RoomClaim>>> copiedSchedule = new HashMap<>();

        for (Map.Entry<Integer, Map<DayOfWeek, TreeSet<RoomClaim>>> weekEntry :
                schedule.entrySet()) {
            Integer week = weekEntry.getKey();
            Map<DayOfWeek, TreeSet<RoomClaim>> weeklySchedule = weekEntry.getValue();

            Map<DayOfWeek, NavigableSet<RoomClaim>> copiedWeeklySchedule = new HashMap<>();
            for (Map.Entry<DayOfWeek, TreeSet<RoomClaim>> dayEntry : weeklySchedule.entrySet()) {
                DayOfWeek day = dayEntry.getKey();
                TreeSet<RoomClaim> claimsForDay = dayEntry.getValue();

                NavigableSet<RoomClaim> cpy = new TreeSet<>();
                for (RoomClaim claim : claimsForDay) {
                    RoomClaim copiedClaim = new RoomClaim(this.room, claim.getCourse(), claim);
                    for (WeekEvent event : copiedClaim.getEvents()) {
                        copiedClaim.addEvent(event);
                    }
                    cpy.add(copiedClaim);
                }

                copiedWeeklySchedule.put(day, cpy);
            }

            copiedSchedule.put(week, copiedWeeklySchedule);
        }

        return copiedSchedule;
    }

    /**
     * Adds a claim for the specified WeekEvent.
     *
     * @param event The WeekEvent for which to add a claim.
     * @return A set of RoomClaims affected by this claim addition.
     */
    public Set<RoomClaim> addClaimFor(@NotNull WeekEvent event) {
        claimedByEvents.add(event);
        Set<RoomClaim> claims =
                this.getIntersectingIntervals(
                        event.getWeek(), event.getWeekday(), Interval.from(event.getTimeslots()));
        return claims.stream()
                .filter(segments -> segments.addEvent(event))
                .collect(Collectors.toSet());
    }

    /**
     * Removes a claim for the specified WeekEvent.
     *
     * @param event The WeekEvent for which to remove a claim.
     * @return A set of RoomClaims affected by this claim removal.
     */
    public Set<RoomClaim> removeClaimFor(@NotNull WeekEvent event) {
        claimedByEvents.remove(event);
        Set<RoomClaim> claims =
                this.getIntersectingIntervals(
                        event.getWeek(), event.getWeekday(), Interval.from(event.getTimeslots()));
        return claims.stream()
                .filter(segments -> segments.removeEvent(event))
                .collect(Collectors.toSet());
    }

    /**
     * Checks whether the specified timeslot (interval) is available for scheduling on the given
     * day.
     *
     * @param d The day of the week to check.
     * @param start The start time of the timeslot.
     * @param end The end time of the timeslot.
     * @return {@code true} if the timeslot is available, {@code false} otherwise.
     */
    @Override
    public boolean isTimeslotAvailable(Integer week, DayOfWeek d, LocalTime start, LocalTime end) {
        return false;
    }
}
