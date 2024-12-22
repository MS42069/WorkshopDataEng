package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.time.LocalTime;
import java.util.Objects;

/** Represents a time segment with a start and end time. */
public class Segment implements Comparable<Segment> {

    /** The start time of the segment. */
    private final LocalTime start;

    /** The end time of the segment. */
    private final LocalTime end;

    /**
     * Constructs a Segment with a start and end time.
     *
     * @param start The start time of the segment.
     * @param end The end time of the segment.
     */
    public Segment(LocalTime start, LocalTime end) {
        assert start.isBefore(end);

        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a Segment from a Timeslot.
     *
     * @param ts The Timeslot to create the Segment from.
     */
    public Segment(Timeslot ts) {
        this(ts.getStartTime(), ts.getEndTime());
    }

    /**
     * Constructs a Segment with specified start and end hours and minutes.
     *
     * @param startHour The hour of the start time.
     * @param startMinute The minute of the start time.
     * @param endHour The hour of the end time.
     * @param endMinute The minute of the end time.
     */
    public Segment(int startHour, int startMinute, int endHour, int endMinute) {
        this(LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute));
    }

    /**
     * Checks if Segment 'a' is exclusively before Segment 'b' with no overlap.
     *
     * @param a The first Segment to compare.
     * @param b The second Segment to compare.
     * @return True if 'a' is exclusively before 'b', false otherwise.
     */
    public static boolean isBefore(Segment a, Segment b) {
        LocalTime aEnd = a.end;
        LocalTime bStart = b.start;

        return aEnd.isBefore(b.end) && (aEnd.isBefore(bStart) || aEnd.equals(bStart));
    }

    /**
     * Checks if Segment 'a' is exclusively after Segment 'b' with no overlap.
     *
     * @param a The first Segment to compare.
     * @param b The second Segment to compare.
     * @return True if 'a' is exclusively after 'b', false otherwise.
     */
    public static boolean isAfter(Segment a, Segment b) {
        LocalTime aStart = a.start;
        LocalTime bEnd = b.end;

        return aStart.isAfter(b.start) && (aStart.isAfter(bEnd) || aStart.equals(bEnd));
    }

    /**
     * Compares this Segment to another Segment.
     *
     * @param other The other Segment to compare to.
     * @return -1 if this Segment is before 'other', 1 if this Segment is after 'other', 0 if they
     *     are equal.
     */
    @Override
    public int compareTo(Segment other) {
        if (this.start.isBefore(other.start)) {
            return -1;
        }

        if (this.start.isAfter(other.start)) {
            return 1;
        }

        if (this.end.isBefore(other.end)) {
            return -1;
        }

        if (this.end.isAfter(other.end)) {
            return 1;
        }

        return 0;
    }

    /**
     * Checks if this Segment is equal to another object.
     *
     * @param o The object to compare to.
     * @return True if the object is a Segment and has the same start and end times, false
     *     otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Segment other)) {
            return false;
        }

        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    /**
     * Generates a hash code for this Segment.
     *
     * @return The hash code based on the start and end times.
     */
    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    /**
     * Gets the start time of the Segment.
     *
     * @return The start time.
     */
    public LocalTime getStart() {
        return this.start;
    }

    /**
     * Gets the end time of the Segment.
     *
     * @return The end time.
     */
    public LocalTime getEnd() {
        return this.end;
    }

    /**
     * Checks if two Segments overlap.
     *
     * @param a The first Segment to check.
     * @param b The second Segment to check.
     * @return True if the Segments overlap, false otherwise.
     */
    public boolean isOverlapping(Segment a, Segment b) {
        return !(Segment.isBefore(a, b) || Segment.isAfter(a, b));
    }

    /**
     * Converts the Segment to a string representation.
     *
     * @return The string representation of the Segment in the format "start-end".
     */
    @Override
    public String toString() {
        return String.format("%s-%s", this.start.toString(), this.end.toString());
    }
}
