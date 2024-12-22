package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.validation.constraints.NotNull;

/** Represents an interval of time divided into segments. */
public class Interval implements Comparable<Interval>, Iterable<Segment> {

    /** The default duration for breaks between segments (15 minutes). */
    public static final LocalTime DEFAULT_BREAK_DURATION = LocalTime.of(0, 15);

    /** The default duration for lessons (1 hour and 15 minutes). */
    public static final LocalTime DEFAULT_LESSON_DURATION = LocalTime.of(1, 15);

    /** A sorted set of segments representing the schedule intervals. */
    protected SortedSet<Segment> segments = new TreeSet<>();

    /**
     * Describes the threshold in which Interval.Segments are considered to be continuous. Default
     * is the break duration of 15 Minutes 8:00 - 9:15 -> 9:30 - 10:45 allowedOverhang -> 0:15
     * allowedOverhang <->
     */
    protected final LocalTime allowedOverhang;

    /** durationSize defines the allowed duration for each interval segment */
    protected final LocalTime durationSize;

    /** Creates an empty interval with default break duration and lesson duration. */
    public Interval() {
        // Default break length defined as 15min
        this.allowedOverhang = LocalTime.of(0, 15);
        this.durationSize = LocalTime.of(1, 15);
    }

    /**
     * Creates a copy of another interval.
     *
     * @param other The interval to copy.
     */
    protected Interval(Interval other) {
        assert !other.isEmpty();
        this.allowedOverhang = other.allowedOverhang;
        this.durationSize = other.durationSize;
        this.segments = new TreeSet<>(other.segments);
    }

    /**
     * Creates an interval with custom allowed overhang and duration size.
     *
     * @param allowedOverhang The allowed overhang duration.
     * @param durationSize The duration size for each segment.
     */
    public Interval(LocalTime allowedOverhang, LocalTime durationSize) {
        this.allowedOverhang = allowedOverhang;
        this.durationSize = durationSize;
    }

    /**
     * Creates an interval with a single segment based on a given Segment.
     *
     * @param s The segment to use for creating the interval.
     */
    public Interval(Segment s) {
        this(
                DEFAULT_BREAK_DURATION,
                s.getEnd()
                        .minusHours(s.getStart().getHour())
                        .minusMinutes(s.getStart().getMinute()));
        this.addSegment(s);
    }

    /**
     * Creates an interval with a single segment based on a given Timeslot.
     *
     * @param ts The Timeslot to use for creating the interval.
     */
    public Interval(Timeslot ts) {
        this();
        this.addSegment(new Segment(ts.getStartTime(), ts.getEndTime()));
    }

    /**
     * Gets the start time of the interval.
     *
     * @return An optional containing the start time if available, otherwise empty.
     */
    public Optional<LocalTime> getStart() {
        if (this.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(segments.first().getStart());
    }

    /**
     * Gets the end time of the interval.
     *
     * @return An optional containing the end time if available, otherwise empty.
     */
    public Optional<LocalTime> getEnd() {
        if (this.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(segments.last().getEnd());
    }

    /**
     * Checks if the interval is empty (contains no segments).
     *
     * @return True if the interval is empty, otherwise false.
     */
    public boolean isEmpty() {
        return this.segments.isEmpty();
    }

    /**
     * Checks if this interval intersects with another interval.
     *
     * @param b The other interval to check for intersection.
     * @return True if there is an intersection, otherwise false.
     */
    public boolean isIntersecting(Interval b) {
        if (this.isEmpty() || b.isEmpty()) {
            return false;
        }

        return !(this.isBefore(b) || this.isAfter(b));
    }

    /**
     * Checks if this interval is strictly before another interval with no overlap.
     *
     * @param b The other interval to compare to.
     * @return True if this interval is strictly before, otherwise false.
     */
    public boolean isBefore(Interval b) {
        if (this.isEmpty() || b.isEmpty()) {
            return false;
        }

        LocalTime aEnd = this.getEnd().get();
        LocalTime bStart = b.getStart().get();
        LocalTime bEnd = b.getEnd().get();

        return aEnd.isBefore(bEnd) && (aEnd.isBefore(bStart) || aEnd.equals(bStart));
    }

    /**
     * Checks if this interval is strictly after another interval with no overlap.
     *
     * @param b The other interval to compare to.
     * @return True if this interval is strictly after, otherwise false.
     */
    public boolean isAfter(Interval b) {
        if (this.isEmpty() || b.isEmpty()) {
            return false;
        }

        LocalTime aStart = this.getStart().get();
        LocalTime bStart = b.getStart().get();
        LocalTime bEnd = b.getEnd().get();

        return aStart.isAfter(bStart) && (aStart.isAfter(bEnd) || aStart.equals(bEnd));
    }

    /**
     * Adds a new segment to the interval.
     *
     * @param start The start time of the new segment.
     * @param end The end time of the new segment.
     * @return True if the segment was added successfully, otherwise false.
     */
    public boolean addSegment(LocalTime start, LocalTime end) {
        assert start.isBefore(end);
        assert end.minusHours(start.getHour())
                .minusMinutes(start.getMinute())
                .equals(this.durationSize);

        if (this.isEmpty()) {
            return this.segments.add(new Segment(start, end));
        }
        if (!Interval.inReach(
                this.getStart().get(), this.getEnd().get(), start, end, this.allowedOverhang)) {
            return false;
        }

        return this.segments.add(new Segment(start, end));
    }

    /**
     * Adds a new segment to the interval.
     *
     * @param s The segment to add to the interval.
     * @return True if the segment was added successfully, otherwise false.
     */
    public boolean addSegment(Segment s) {
        return this.addSegment(s.getStart(), s.getEnd());
    }

    /**
     * Adds a new segment to the interval based on a Timeslot.
     *
     * @param s The Timeslot to use for creating the segment and adding it to the interval.
     * @return True if the segment was added successfully, otherwise false.
     */
    public boolean addSegment(Timeslot s) {
        return this.addSegment(s.getStartTime(), s.getEndTime());
    }

    @Override
    public int compareTo(Interval other) {
        if (this.isEmpty()) {
            return -1;
        }
        if (other.isEmpty()) {
            return 1;
        }

        LocalTime thisStart = this.getStart().get();
        LocalTime otherStart = other.getStart().get();

        if (thisStart.isAfter(otherStart)) {
            return 1;
        }

        if (thisStart.isBefore(otherStart)) {
            return -1;
        }

        return 0;
    }

    /**
     * Checks if this interval is equal to another object.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Interval other)) {
            return false;
        }

        if (this.isEmpty() || other.isEmpty()) {
            return this.isEmpty() && other.isEmpty();
        }

        if (this.size() != other.size()) {
            return false;
        }

        if (!(this.getStart().get().equals(other.getStart().get())
                && this.getEnd().get().equals(other.getEnd().get()))) {
            return false;
        }

        return this.segments.equals(other.segments);
    }

    /**
     * Returns a string representation of this interval.
     *
     * @return a string representing the interval and its segments
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (Segment s : this.segments) {
            sb.append(s.toString());
            sb.append(", ");
        }

        if (0 < this.segments.size()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.append("]").toString();
    }

    /**
     * Creates an interval from a list of timeslots.
     *
     * @param timeslotList the list of timeslots
     * @return an interval created from the timeslots
     */
    public static Interval from(List<Timeslot> timeslotList) {
        assert timeslotList != null;
        assert !timeslotList.isEmpty();

        Interval interval = new Interval();

        for (Timeslot ts : timeslotList) {
            boolean ok = interval.addSegment(ts.getStartTime(), ts.getEndTime());
            assert ok;
        }

        assert !interval.isEmpty();
        assert interval.size() == timeslotList.size();

        return interval;
    }

    /**
     * Converts this interval to a list of timeslots with the help of the given map, which allows to
     * lookup timeslots by their start time.
     *
     * @param tsStartLookupMap contains all possible timeslots mapped to their start times
     * @return list of timeslots representing this interval
     */
    public List<Timeslot> convertToTimeslots(Map<LocalTime, Timeslot> tsStartLookupMap) {
        List<Timeslot> result = new ArrayList<>();
        for (Segment segment : this.segments) {
            Timeslot timeslot = tsStartLookupMap.get(segment.getStart());

            if (timeslot != null && segment.getEnd().equals(timeslot.getEndTime())) {
                // Note: should be always true, if the same set of timeslots was used to create the
                // interval
                result.add(timeslot);
            }
        }
        return result;
    }

    /**
     * Maps each timeslot in the given list to its start time.
     *
     * @param timeslots list of timeslots, should not contain timeslots with the same start time
     * @return timeslots identifiable by their start time.
     */
    public static Map<LocalTime, Timeslot> mapTimeslotsToStartTime(List<Timeslot> timeslots) {
        Map<LocalTime, Timeslot> tssByStartTime = new HashMap<>();
        for (Timeslot ts : timeslots) {
            tssByStartTime.put(ts.getStartTime(), ts);
        }
        return tssByStartTime;
    }

    /**
     * Creates a clone of this interval.
     *
     * @return a cloned copy of this interval
     */
    public Interval clone() {
        Interval i = new Interval(this.allowedOverhang, this.durationSize);

        for (Segment seg : this.segments) {
            i.addSegment(seg.getStart(), seg.getEnd());
        }

        return i;
    }

    /**
     * Returns an iterator for the segments in this interval.
     *
     * @return an iterator for the segments
     */
    @Override
    public Iterator<Segment> iterator() {
        return this.segments.iterator();
    }

    /**
     * Gets the number of segments in this interval.
     *
     * @return the number of segments
     */
    public int size() {
        return this.segments.size();
    }

    /**
     * Gets the allowed overhang for segments in this interval.
     *
     * @return the allowed overhang
     */
    public LocalTime getAllowedOverhang() {
        return this.allowedOverhang;
    }

    /**
     * Gets the duration size for segments in this interval.
     *
     * @return the duration size
     */
    public LocalTime getDuration() {
        return this.durationSize;
    }

    /**
     * Checks if two segments are within reach of each other based on the maximum allowed overhang.
     *
     * @param aStart the start time of the first segment
     * @param aEnd the end time of the first segment
     * @param bStart the start time of the second segment
     * @param bEnd the end time of the second segment
     * @param maxOverhang the maximum allowed overhang
     * @return true if the segments are within reach, false otherwise
     */
    public static boolean inReach(
            LocalTime aStart,
            LocalTime aEnd,
            LocalTime bStart,
            LocalTime bEnd,
            LocalTime maxOverhang) {
        // | ->||< |
        // aEnd][bStart
        LocalTime delta = bStart;
        LocalTime subtractedBy = aEnd;

        if (bEnd.isBefore(aStart)) {
            // | ->||< |
            // bEnd][aStart
            delta = aStart;
            subtractedBy = bEnd;
        }

        delta = delta.minusHours(subtractedBy.getHour());
        delta = delta.minusMinutes(subtractedBy.getMinute());

        // gap in between Segments need to be smaller/equal than/to maxOverhang
        return !delta.isAfter(maxOverhang);
    }

    /**
     * Checks if two segments are within reach of each other based on the maximum allowed overhang.
     *
     * @param a the first segment
     * @param b the second segment
     * @param maxOverhang the maximum allowed overhang
     * @return true if the segments are within reach, false otherwise
     */
    public static boolean inReach(Segment a, Segment b, LocalTime maxOverhang) {
        return inReach(a.getStart(), a.getEnd(), b.getStart(), b.getEnd(), maxOverhang);
    }

    /**
     * Maps the segments in this interval to a list of timeslots based on a provided list of
     * timeslots.
     *
     * @param interval the interval to map
     * @param timeslots the list of timeslots
     * @return a list of timeslots mapped from the segments in the interval
     */
    public static List<Timeslot> mapToTimeslot(
            @NotNull Interval interval, @NotNull List<Timeslot> timeslots) {
        assert !interval.isEmpty();
        assert interval.size() != 0;
        assert !timeslots.isEmpty();

        timeslots =
                timeslots.stream()
                        .distinct()
                        .sorted(Comparator.comparing(Timeslot::getStartTime))
                        .toList();

        List<Timeslot> mapped = new LinkedList<>();
        for (Segment s : interval) {
            Optional<Timeslot> ts =
                    timeslots.stream()
                            .filter(
                                    timeslot ->
                                            timeslot.getStartTime().equals(s.getStart())
                                                    && timeslot.getEndTime().equals(s.getEnd()))
                            .findFirst();

            assert ts.isPresent();
            mapped.add(ts.get());
        }
        return mapped.stream().sorted(Comparator.comparing(Timeslot::getStartTime)).toList();
    }
}
