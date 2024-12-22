package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class represents a scheduling option for a course. It extends the Interval class and
 * includes information about the course, day of the week, room combinations, and other related
 * data.
 */
@Slf4j
public class Option extends Interval implements Comparable<Interval> {

    /** The course associated with this option. */
    @Getter private final Course course;

    @Getter private final Integer week;

    /** The day of the week for this option. */
    @Getter private final DayOfWeek day;

    /** Stores every WeekEvent which intersects with this option */
    private final Set<WeekEvent> intersectingEvents = new HashSet<>();

    /**
     * constrainedbyWeekEvent describes the weekevents in which the option is constrained by the
     * "before/after" relation due to the WeekEvent
     */
    private final Set<WeekEvent> constrainingWeekEvents = new HashSet<>();

    /** roomGroups stores a list of sets of rooms which provide a room combination */
    private final List<Set<UUID>> roomGroups = new ArrayList<>();

    /** A map that allows looking up rooms by their unique UUID. */
    private final Map<UUID, Room> lookupRoomByUUID = new HashMap<>();

    /**
     * Lookup for a given room for all associated indexes in roomGroups Hs1 -> {index:0, index:3} ->
     * roomgroups.get(0) contains HS1
     */
    private final Map<UUID, Set<Integer>> indexLookup = new HashMap<>();

    /**
     * Constructs an Option object.
     *
     * @param c The course associated with this option.
     * @param i The interval for this option.
     * @param d The day of the week.
     */
    public Option(
            @NotNull Course c, @NotNull Interval i, @NotNull Integer week, @NotNull DayOfWeek d) {
        super(i);
        this.course = c;
        this.week = week;
        this.day = d;
    }

    /**
     * Constructs an Option object with possible room combinations.
     *
     * @param course The course associated with this option.
     * @param i The interval for this option.
     * @param d The day of the week.
     * @param possibleRooms Collection of possible room combinations.
     */
    public Option(
            @NotNull Course course,
            @NotNull Interval i,
            @NotNull Integer week,
            @NotNull DayOfWeek d,
            Collection<Set<Room>> possibleRooms) {
        this(course, i, week, d);
        addRoomCombinations(possibleRooms);
    }

    /**
     * Adds a room combination to this option.
     *
     * @param roomCombination The room combination to add.
     * @return True if the room combination was added, false if it already exists.
     */
    public boolean addRoomCombination(@NotNull Set<Room> roomCombination) {
        if (containsRoomGroup(roomCombination)) {
            return false;
        }

        int index = roomGroups.size();
        this.roomGroups.add(roomCombination.stream().map(Room::getId).collect(Collectors.toSet()));
        for (Room r : roomCombination) {
            // Given the room r, we
            Set<Integer> associatedRoomCombs =
                    this.indexLookup.getOrDefault(r.getId(), new HashSet<>());
            associatedRoomCombs.add(index);
            this.indexLookup.put(r.getId(), associatedRoomCombs);
            this.lookupRoomByUUID.put(r.getId(), r);
        }

        return true;
    }

    /**
     * Checks if a room combination already exists in this option.
     *
     * @param roomCombination The room combination to check.
     * @return True if the room combination exists, false otherwise.
     */
    public boolean containsRoomGroup(Set<Room> roomCombination) {

        Set<UUID> roomsAsUUIDs =
                roomCombination.stream().map(Room::getId).collect(Collectors.toSet());

        //        for (Room r : roomCombination) {
        //            Set<Integer> indexes = this.indexLookup.getOrDefault(r, new HashSet<>());
        //            for (Integer index : indexes) {
        //                Set<Room> copyRoomGroup = new HashSet<>(this.roomGroups.get(index));
        //                if (copyRoomGroup.size() == roomCombination.size()) {
        //                    copyRoomGroup.removeAll(roomCombination);
        //                    if (copyRoomGroup.size() == 0) {
        //                        return true;
        //                    }
        //                }
        //            }
        //        }

        return this.roomGroups.contains(roomsAsUUIDs);
    }

    /**
     * Adds multiple room combinations to this option.
     *
     * @param possibleRooms The collection of possible room combinations to add.
     * @return True if any room combinations were added, false otherwise.
     */
    public boolean addRoomCombinations(Collection<Set<Room>> possibleRooms) {
        boolean changes = false;

        for (Set<Room> roomCombination : possibleRooms) {
            boolean causedChanges = addRoomCombination(roomCombination);
            changes = changes || causedChanges;
        }
        return changes;
    }
    /**
     * Returns a string representation of this Option object, including information about the
     * associated course.
     *
     * @return A string containing the course details and the interval representation.
     */
    @Override
    public String toString() {
        return course.toString() + " " + super.toString();
    }

    /**
     * Gets all room combinations for this option.
     *
     * @return A set of sets of rooms representing room combinations.
     */
    public Set<Set<Room>> getRoomCombinations() {
        log.info("roomGroups {}", this.roomGroups);
        Set<Set<Room>> roomComps = new HashSet<>();
        for (Set<UUID> rooms : this.roomGroups) {
            roomComps.add(
                    rooms.stream().map(this.lookupRoomByUUID::get).collect(Collectors.toSet()));
        }
        return roomComps;
    }

    /**
     * Compares this Option object with another Interval object. This comparison considers the
     * interval and the course associated with the option.
     *
     * @param other The Interval object to compare with.
     * @return A negative integer, zero, or a positive integer if this Option is less than, equal
     *     to, or greater than the specified Interval, respectively.
     */
    @Override
    public int compareTo(Interval other) {
        int result = super.compareTo(other);

        if (result != 0) {
            return result;
        }

        if (!(other instanceof Option asOption)) {
            return result;
        }

        if (this.course == null || asOption.course == null) {
            if (this.course == null && asOption.course == null) {
                return 0;
            }

            if (this.course == null) {
                return 1;
            }
            return -1;
        }

        return this.course.getName().compareTo(asOption.getCourse().getName());
    }

    /**
     * Indicates whether some other object is "equal to" this Option. Two Option objects are
     * considered equal if they have the same course, day, interval, and room combinations.
     *
     * @param o The reference object with which to compare.
     * @return true if this Option is equal to the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Option otherOption = (Option) o;

        if (!this.day.equals(otherOption.day)) {
            return false;
        }

        if (!this.getStart().equals(otherOption.getStart())) {
            return false;
        }

        if (!this.getEnd().equals(otherOption.getEnd())) {
            return false;
        }

        if (!this.segments.equals(otherOption.segments)) {
            return false;
        }

        if (!course.equals(otherOption.course)) {
            return false;
        }

        if (roomGroups.size() != (otherOption.roomGroups.size())) {
            return false;
        }

        return new HashSet<>(this.roomGroups).containsAll(otherOption.roomGroups);
    }

    /**
     * Returns a hash code value for this Option object, based on its course, room combinations,
     * day, and interval.
     *
     * @return A hash code value for this Option.
     */
    @Override
    public int hashCode() {
        return Objects.hash(course, roomGroups, day, this.segments);
    }

    /**
     * Checks if this option is blocked due to constraints.
     *
     * @return True if the option is blocked, false otherwise.
     */
    public boolean isBlocked() {
        if (!this.constrainingWeekEvents.isEmpty()) {
            return true;
        }

        // if the provided event has the same course and intersects with the options
        // or if the event shares the same employee course
        return this.intersectingEvents.stream().anyMatch(this::isAffectingOption);
    }

    /**
     * Checks if a WeekEvent affects this option by analyzing whether they share a relation that
     * could cause a conflict. It considers factors such as intersecting timeslots, parallel course
     * relations, and shared lecturers or lecturees.
     *
     * @param e The WeekEvent to check for affecting this option.
     * @return True if the WeekEvent affects this option, false otherwise.
     */
    @Transactional
    private boolean isAffectingOption(@NotNull WeekEvent e) {
        Interval i = Interval.from(e.getTimeslots());
        assert i.size() != 0;

        // if it does not intersect with this option, there is no relation
        if (!isIntersecting(this)) {
            return false;
        }

        boolean allowedToBeParallel =
                this.course.getCourseRelationsA().stream()
                                .filter(
                                        courseRelation ->
                                                courseRelation.getRelationType()
                                                                == RelationType.MUST_BE_PARALLEL
                                                        || courseRelation.getRelationType()
                                                                == RelationType.MAY_BE_PARALLEL)
                                .anyMatch(
                                        courseRelation ->
                                                courseRelation
                                                                .getCourseA()
                                                                .getId()
                                                                .equals(e.getCourse().getId())
                                                        || courseRelation
                                                                .getCourseB()
                                                                .getId()
                                                                .equals(e.getCourse().getId()))
                        || this.course.getCourseRelationsB().stream()
                                .filter(
                                        courseRelation ->
                                                courseRelation.getRelationType()
                                                                == RelationType.MUST_BE_PARALLEL
                                                        || courseRelation.getRelationType()
                                                                == RelationType.MAY_BE_PARALLEL)
                                .anyMatch(
                                        courseRelation ->
                                                courseRelation
                                                                .getCourseA()
                                                                .getId()
                                                                .equals(e.getCourse().getId())
                                                        || courseRelation
                                                                .getCourseB()
                                                                .getId()
                                                                .equals(e.getCourse().getId()));

        if (allowedToBeParallel) {
            return false;
        }

        /*
        I: is Intersecting
        P: allowed to be parrallel
        S: shares lecturers/lecturees
        B: is blocking option

        I | P | S |  B
        1 | 0 | 0 | ß
        1 | 0 | 1 | 1
        1 | 1 | 0 | 0
        1 | 1 | 1 | 0
         */
        return isSharingRelationToEvent(e);
    }

    /**
     * @return all rooms combinations free as well as already allocated, sorted by Size ASC, and
     *     each room combination by UUID ASC
     */
    public List<List<Room>> getAllRoomCombinations() {
        List<List<Room>> allRoomCombs = new LinkedList<>();
        for (Set<UUID> roomComb : this.roomGroups) {
            List<Room> rooms =
                    roomComb.stream()
                            .map(this.lookupRoomByUUID::get)
                            .sorted(Comparator.comparing(Room::getId))
                            .toList();
            allRoomCombs.add(rooms);
        }

        return allRoomCombs.stream().sorted(Comparator.comparingInt(List::size)).toList();
    }

    /**
     * @return all free rooms combinations which can be allocated
     */
    public List<List<Room>> getAllFreeRoomCombinations() {
        return getAllRoomCombinations().stream()
                .filter(
                        rooms ->
                                rooms.stream()
                                        .noneMatch(
                                                room ->
                                                        intersectingEvents.stream()
                                                                .anyMatch(
                                                                        weekEvent ->
                                                                                weekEvent
                                                                                        .getRooms()
                                                                                        .contains(
                                                                                                room))))
                .toList();
    }

    /**
     * Gets the degrees of freedom for this option.
     *
     * @return The degrees of freedom for this option.
     */
    public int getDegreesOfFreedom() {
        // Option can only be considered if the provided date and time is not
        // being used by any entities which are associated by the course
        if (isBlocked()) {
            return 0;
        }

        // Since all rooms are
        return getAllFreeRoomCombinations().size();
    }

    /**
     * Gets the intersecting events with this option.
     *
     * @return A set of intersecting events.
     */
    public Set<WeekEvent> getIntersectingEvents() {
        return new HashSet<>(this.intersectingEvents);
    }

    /**
     * Adds a WeekEvent to the list of intersecting events.
     *
     * @param e The WeekEvent to add.
     * @return True if the event was added, false otherwise.
     */
    public boolean addEvent(WeekEvent e) {
        log.info("Adding weekevent={} to course={} interval={}...", e, this.course, this);
        return this.intersectingEvents.add(e);
    }

    /**
     * Removes a WeekEvent from the list of intersecting events.
     *
     * @param e The WeekEvent to remove.
     * @return True if the event was removed, false otherwise.
     */
    public boolean removeEvent(WeekEvent e) {
        return this.intersectingEvents.remove(e);
    }

    /**
     * Adds a constraint WeekEvent to this option.
     *
     * @param e The WeekEvent to add as a constraint.
     * @return True if the constraint was added, false otherwise.
     */
    public boolean addConstraint(WeekEvent e) {
        return this.constrainingWeekEvents.add(e);
    }

    /**
     * Removes a constraint WeekEvent from this option.
     *
     * @param e The WeekEvent to remove as a constraint.
     * @return True if the constraint was removed, false otherwise.
     */
    public boolean removeConstraint(WeekEvent e) {
        return this.constrainingWeekEvents.remove(e);
    }

    /**
     * Gets the set of constraining WeekEvents for this option.
     *
     * @return A set of constraining WeekEvents.
     */
    public Set<WeekEvent> getConstrainingEvents() {
        return new HashSet<>(this.constrainingWeekEvents);
    }

    /**
     * Creates a copy of this Option object.
     *
     * @param o The Option to copy.
     * @return A new Option object with the same attributes.
     */
    public static Option copy(Option o) {
        Option newOpt = new Option(o.getCourse(), o, o.week, o.day);
        for (List<Room> roomComb : o.getAllRoomCombinations()) {
            newOpt.addRoomCombination(new HashSet<>(roomComb));
        }

        newOpt.intersectingEvents.addAll(o.intersectingEvents);
        return newOpt;
    }

    /**
     * Creates OptionDTOs based on the provided timeslots.
     *
     * @param timeslots The list of timeslots to create OptionDTOs for.
     * @return A list of OptionDTOs.
     */
    @Transactional
    public List<OptionDTO> createOptionDTOs(@NotNull List<Timeslot> timeslots) {
        if (isBlocked()) {
            return Collections.emptyList();
        }

        List<OptionDTO> optionDTOS = new LinkedList<>();

        List<UUID> optionTimeslots =
                Interval.mapToTimeslot(this, timeslots).stream()
                        // sort timeslots by start time (important for the display in the frontend)
                        .sorted(Comparator.comparing(Timeslot::getStartTime))
                        .map(Timeslot::getId)
                        .toList();

        for (List<Room> roomComb : this.getAllFreeRoomCombinations()) {
            List<UUID> roomCombUUIDs =
                    roomComb.stream().map(Room::getId).sorted(uuidComparator()).toList();

            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setWeek(this.week);
            optionDTO.setWeekday(this.day);
            optionDTO.setRooms(roomCombUUIDs);
            optionDTO.setTimeslots(optionTimeslots);
            optionDTOS.add(optionDTO);
        }

        optionDTOS = optionDTOS.stream().sorted(optionDTOComparator()).toList();
        return optionDTOS;
    }

    /**
     * A comparator for OptionDTO objects that compares them based on the size of their room sets
     * and the UUIDs of the rooms. It first compares the sizes of the room sets, and if they are
     * equal, it iterates over the UUIDs to compare them.
     *
     * @return A comparator for OptionDTO objects.
     */
    public static Comparator<OptionDTO> optionDTOComparator() {
        return (a, b) -> {
            int compareOnSize = a.getRooms().size() - b.getRooms().size();
            if (compareOnSize != 0) {
                return compareOnSize;
            }
            Iterator<UUID> bIterator = b.getRooms().iterator();
            for (UUID r : a.getRooms()) {
                int compareOnUUID = r.compareTo(bIterator.next());
                if (compareOnUUID != 0) {
                    return compareOnUUID;
                }
            }
            return 0;
        };
    }

    /**
     * Checks if a sharing relation exists between this option and a WeekEvent.
     *
     * @param e The WeekEvent to check for a sharing relation.
     * @return True if a sharing relation exists, false otherwise.
     */
    @Transactional
    public boolean isSharingRelationToEvent(@NotNull WeekEvent e) {
        System.out.printf(
                "checking if relation exist for option=%s and for weekevent=%s%n",
                this, Schedule.logRepresentation(e));

        if (e.getWeek() != this.getWeek()) {
            return false;
        }

        if (e.getCourse().getId() == this.course.getId()) {
            return true;
        }

        System.out.printf(
                "checking relation weekevent=%s has  lecturers=%s:%d and semester=%s:%d%n",
                e,
                e.getCourse().getLecturers(),
                e.getCourse().getLecturers().size(),
                e.getCourse().getSemesters(),
                e.getCourse().getSemesters().size());

        System.out.printf(
                "checking relation option=%s has  lecturers=%s:%d and semester=%s:%d%n",
                this,
                this.getCourse().getLecturers(),
                this.getCourse().getLecturers().size(),
                this.getCourse().getSemesters(),
                this.getCourse().getSemesters().size());

        Set<UUID> associatedEmployee =
                e.getCourse().getLecturers().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toSet());
        Set<UUID> associatedDegreeSemester =
                e.getCourse().getSemesters().stream()
                        .map(DegreeSemester::getId)
                        .collect(Collectors.toSet());

        associatedEmployee.retainAll(
                this.course.getLecturers().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toSet()));

        associatedDegreeSemester.retainAll(
                this.course.getSemesters().stream()
                        .map(DegreeSemester::getId)
                        .collect(Collectors.toSet()));

        System.out.printf(
                "checking result yielded for relation for option=%s and for weekevent=%s, found for lecturers=%s:%d and degreesemester=%s:%d%n",
                this,
                e,
                associatedEmployee,
                associatedEmployee.size(),
                associatedDegreeSemester,
                associatedDegreeSemester.size());

        boolean isSharingNoEmployees = associatedEmployee.size() == 0;
        boolean isSharingNoDegreeSemester = associatedDegreeSemester.size() == 0;

        return !isSharingNoEmployees || !isSharingNoDegreeSemester;
    }

    /**
     * Comparator for UUID objects.
     *
     * @return A comparator for UUID objects.
     */
    public static Comparator<UUID> uuidComparator() {
        return UUID::compareTo;
    }

    /**
     * Converts this Option's availability to a binary vector.
     *
     * @param ts The list of timeslots to consider.
     * @return An integer array representing the availability.
     */
    public int[] asVector(List<Timeslot> ts) {
        int[] vec = new int[ts.size()];
        int index = 0;
        for (int i :
                ts.stream()
                        .map(
                                timeslot ->
                                        !this.isBlocked()
                                                && this.isIntersecting(
                                                        new Interval(new Segment(timeslot))))
                        .map(aBoolean -> aBoolean ? 1 : 0)
                        .toList()) {
            vec[index] = i;
            index++;
        }
        return vec;
    }
}
