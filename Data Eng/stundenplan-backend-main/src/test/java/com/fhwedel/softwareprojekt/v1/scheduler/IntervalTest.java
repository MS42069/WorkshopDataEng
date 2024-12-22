package com.fhwedel.softwareprojekt.v1.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class IntervalTest {
    final Interval intervalA = new Interval(new Segment(LocalTime.of(8, 0), LocalTime.of(9, 15)));
    final Interval intervalB = new Interval(new Segment(LocalTime.of(9, 30), LocalTime.of(10, 45)));

    @Test
    public void Test_Interval_IsBefore() {
        assertTrue(intervalA.isBefore(intervalB));
        assertFalse(intervalB.isBefore(intervalA));
    }

    @Test
    public void Test_Interval_IsAfter() {
        assertFalse(intervalA.isAfter(intervalB));
        assertTrue(intervalB.isAfter(intervalA));
    }

    @Test
    public void Test_Interval_Intersecting_Simple() {
        assertFalse(intervalA.isIntersecting(intervalB));
        assertFalse(intervalB.isIntersecting(intervalA));
    }

    @Test
    public void Test_Interval_Same() {
        Interval intervalC = new Interval();
        intervalC.addSegment(LocalTime.of(8, 0), LocalTime.of(9, 15));

        assertFalse(intervalC.isBefore(intervalA));
        assertFalse(intervalC.isAfter(intervalA));

        assertTrue(intervalC.isIntersecting(intervalA));
        assertTrue(intervalA.isIntersecting(intervalC));
    }

    @Test
    public void Test_Interval_Larger() {
        Interval intervalC = new Interval();
        intervalC.addSegment(LocalTime.of(7, 0), LocalTime.of(8, 15));
        intervalC.addSegment(LocalTime.of(8, 30), LocalTime.of(9, 45));

        assertFalse(intervalC.isBefore(intervalA));
        assertFalse(intervalC.isAfter(intervalA));

        assertTrue(intervalC.isIntersecting(intervalA));
        assertTrue(intervalA.isIntersecting(intervalC));
    }

    @Test
    public void Test_Interval_Smaller() {
        Interval intervalC = new Interval();
        assertTrue(intervalC.addSegment(LocalTime.of(8, 30), LocalTime.of(9, 45)));
        assertFalse(intervalC.isEmpty());

        Interval intervalD = new Interval();
        assertTrue(intervalD.addSegment(LocalTime.of(7, 15), LocalTime.of(8, 30)));
        assertTrue(intervalD.addSegment(LocalTime.of(8, 45), LocalTime.of(10, 0)));
        assertFalse(intervalD.isEmpty());

        assertFalse(intervalC.isBefore(intervalD));
        assertFalse(intervalC.isAfter(intervalD));

        assertFalse(intervalD.isBefore(intervalC));
        assertFalse(intervalD.isAfter(intervalC));

        assertTrue(intervalC.isIntersecting(intervalD));
        assertTrue(intervalD.isIntersecting(intervalC));
    }

    @Test
    public void Test_Interval_Segmentation_Range() {
        Interval v = new Interval();
        Segment[] segments =
                new Segment[] {
                    new Segment(LocalTime.of(8, 0), LocalTime.of(9, 15)),
                    new Segment(LocalTime.of(9, 30), LocalTime.of(10, 45))
                };

        for (Segment s : segments) {
            assertTrue(v.addSegment(s));
        }
        assertTrue(v.getStart().isPresent());
        assertTrue(v.getEnd().isPresent());
        assertEquals(segments[0].getStart(), v.getStart().get());
        assertEquals(segments[segments.length - 1].getEnd(), v.getEnd().get());
    }

    @Test
    public void Test_Interval_Size() {
        Interval intervalC = new Interval();
        assertEquals(0, intervalC.size());
        intervalC.addSegment(new Segment(LocalTime.of(10, 0), LocalTime.of(11, 15)));
        assertEquals(1, intervalC.size());
        intervalC.addSegment(new Segment(LocalTime.of(11, 30), LocalTime.of(12, 45)));
        assertEquals(2, intervalC.size());

        assertFalse(intervalC.addSegment(new Segment(LocalTime.of(14, 0), LocalTime.of(15, 15))));
        assertEquals(2, intervalC.size());
    }

    @Test
    public void Test_Interval_InReach() {
        LocalTime breakLen = LocalTime.of(0, 15);

        Segment segmentA = new Segment(LocalTime.of(8, 0), LocalTime.of(9, 15));

        Segment segmentB = new Segment(LocalTime.of(9, 30), LocalTime.of(10, 45));

        Segment segmentC = new Segment(LocalTime.of(11, 0), LocalTime.of(12, 15));

        assertTrue(Interval.inReach(segmentA, segmentB, breakLen));
        assertTrue(Interval.inReach(segmentB, segmentA, breakLen));
        assertTrue(Interval.inReach(segmentB, segmentC, breakLen));
        assertTrue(Interval.inReach(segmentC, segmentB, breakLen));

        assertFalse(Interval.inReach(segmentA, segmentC, breakLen));
        assertFalse(Interval.inReach(segmentC, segmentA, breakLen));
    }

    @Test
    public void Test_Interval_Equal() {
        Interval a = new Interval();
        a.addSegment(LocalTime.of(8, 0), LocalTime.of(9, 15));
        Interval b = new Interval();
        b.addSegment(LocalTime.of(8, 0), LocalTime.of(9, 15));

        Interval c = new Interval();
        c.addSegment(LocalTime.of(9, 30), LocalTime.of(10, 45));

        assertNotEquals(a, c);
        assertEquals(a, b);
    }

    @Test
    public void givenInterval_whenConvertToTimeslots_thenCorrectResult() {
        // given
        Timeslot ts1 = new Timeslot();
        ts1.setId(UUID.randomUUID());
        ts1.setStartTime(LocalTime.of(8, 0));
        ts1.setEndTime(LocalTime.of(9, 15));

        Timeslot ts2 = new Timeslot();
        ts2.setId(UUID.randomUUID());
        ts2.setStartTime(LocalTime.of(9, 30));
        ts2.setEndTime(LocalTime.of(10, 45));

        Map<LocalTime, Timeslot> tsStartLookupMap =
                Interval.mapTimeslotsToStartTime(List.of(ts1, ts2));

        Interval interval = Interval.from(List.of(ts1, ts2));
        assertTrue(interval.getStart().isPresent());
        assertTrue(interval.getEnd().isPresent());
        assertEquals(LocalTime.of(8, 0), interval.getStart().get());
        assertEquals(LocalTime.of(10, 45), interval.getEnd().get());

        // when
        List<Timeslot> result = interval.convertToTimeslots(tsStartLookupMap);

        assertEquals(2, result.size());
        result.sort(Comparator.comparing(Timeslot::getStartTime)); // sort by start time
        assertEquals(ts1, result.get(0));
        assertEquals(ts2, result.get(1));
    }
}
