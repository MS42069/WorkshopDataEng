package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

/** Represents a claim on a room within a specific time interval for a course. */
public class RoomClaim extends Interval {

    /** The course associated with this room claim. */
    private final Course course;

    /** The room that is being claimed. */
    private final Room room;

    /** A set of week events that claim this room. */
    private final Set<WeekEvent> shadowedBy = new HashSet<>();

    /**
     * Initializes a new instance of the {@code RoomClaim} class.
     *
     * @param r The room being claimed.
     * @param c The course associated with the claim.
     * @param i The time interval for the claim.
     */
    public RoomClaim(@NotNull Room r, @NotNull Course c, @NotNull Interval i) {
        super(i);
        this.course = c;
        this.room = r;
    }

    /**
     * Gets the course associated with the room claim.
     *
     * @return The associated course.
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * Gets a set of events shadowed by this room claim.
     *
     * @return A set of shadowed events.
     */
    public Set<WeekEvent> getEvents() {
        return new HashSet<>(shadowedBy);
    }

    /**
     * Gets the room associated with the room claim.
     *
     * @return The associated room.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Compares this room claim to another interval, considering start and end times, course ID, and
     * ownership.
     *
     * @param other The other interval to compare to.
     * @return A negative integer if this claim comes before the other, a positive integer if after,
     *     or zero if they are equal.
     */
    @Override
    public int compareTo(Interval other) {
        if (!(other instanceof RoomClaim asOtherClaim)) {
            return super.compareTo(other);
        }
        if (this.isEmpty()) {
            return -1;
        }

        if (other.isEmpty()) {
            return 1;
        }

        // Sort via Start Asc, End Desc, UID ASC

        if (this.getStart().get().isBefore(asOtherClaim.getStart().get())) {
            return -1;
        }

        if (this.getStart().get().isAfter(asOtherClaim.getStart().get())) {
            return 1;
        }

        if (this.getEnd().get().isAfter(asOtherClaim.getEnd().get())) {
            return -1;
        }

        if (this.getEnd().get().isBefore(asOtherClaim.getEnd().get())) {
            return 1;
        }

        if (this.getCourse() == null) {
            return -1;
        }
        if (asOtherClaim.getCourse() == null) {
            return 1;
        }

        if (this.getCourse() == null && asOtherClaim.getCourse() == null) {
            return 0;
        }

        return this.getCourse().getId().compareTo(asOtherClaim.getCourse().getId());
    }

    /**
     * Checks if this room claim is equal to another object based on interval equality, course ID,
     * and claiming events.
     *
     * @param o The other object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        if (!(o instanceof RoomClaim otherClaim)) {
            return false;
        }

        if (!this.getCourse().equals(otherClaim.getCourse())) {
            return false;
        }

        return this.getClaimingEvents().equals(otherClaim.getClaimingEvents());
    }

    /**
     * Adds a week event to the set of events shadowed by this room claim.
     *
     * @param event The week event to add.
     * @return True if the event was successfully added, false otherwise.
     */
    public boolean addEvent(@NotNull WeekEvent event) {
        return this.shadowedBy.add(event);
    }

    /**
     * Removes a week event from the set of events shadowed by this room claim.
     *
     * @param event The week event to remove.
     * @return True if the event was successfully removed, false otherwise.
     */
    public boolean removeEvent(@NotNull WeekEvent event) {
        return this.shadowedBy.remove(event);
    }

    /**
     * Checks if this room claim is already claimed by other events.
     *
     * @return True if the room claim is already claimed by other events, false otherwise.
     */
    public boolean isAlreadyClaimedByOthers() {
        return this.shadowedBy.size() > 0;
    }

    /**
     * Gets a set of week events claiming this room.
     *
     * @return A set of week events claiming this room.
     */
    private Set<WeekEvent> getClaimingEvents() {
        return new HashSet<>(this.shadowedBy);
    }
}
