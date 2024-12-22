package com.fhwedel.softwareprojekt.v1.scheduler;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.time.DayOfWeek;
import java.util.*;
import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void Test_Schedule_Add() {
        Employee a = new Employee();
        a.setId(UUID.randomUUID());

        TestSchedule schedule = new TestSchedule(a);
        List<Timeslot> timeslots = SchedulerTestUtil.generateTimeslots(7);

        for (Timeslot ts : timeslots) {
            assertTrue(
                    schedule.add(
                            0,
                            DayOfWeek.MONDAY,
                            new Interval(new Segment(ts.getStartTime(), ts.getEndTime()))));
        }

        for (int i = 0; i < timeslots.size(); i++) {
            Interval inter = new Interval();
            Set<Interval> addIntervals = new HashSet<>();
            for (int ii = i; ii < timeslots.size(); ii++) {
                Segment seg =
                        new Segment(
                                timeslots.get(ii).getStartTime(), timeslots.get(ii).getEndTime());
                addIntervals.add(new Interval(seg));
                inter.addSegment(seg);

                Set<Interval> intervals =
                        schedule.getIntersectingIntervals(0, DayOfWeek.MONDAY, inter);
                assertEquals(addIntervals.size(), intervals.size());
                assertTrue(intervals.containsAll(addIntervals));
            }
        }
    }

    @Test
    public void Test_Schedule_Blocks() {
        Employee a = new Employee();
        a.setId(UUID.randomUUID());

        TestSchedule schedule = new TestSchedule(a);
        List<Timeslot> timeslots = SchedulerTestUtil.generateTimeslots(5);
        for (Timeslot ts : timeslots) {
            Interval i = new Interval(new Segment(ts.getStartTime(), ts.getEndTime()));
            assertTrue(schedule.add(0, DayOfWeek.MONDAY, i));
        }

        // Test of Block Size 1
        Set<Interval> groupedByBlockOf_1 =
                Schedule.groupIntervalsInBlocksForDay(
                        schedule.getSchedule().get(0), DayOfWeek.MONDAY, 1);
        assertEquals(5, groupedByBlockOf_1.size());

        Interval expected_1_1 = new Interval(timeslots.get(0));
        assertTrue(groupedByBlockOf_1.contains(expected_1_1));

        Interval expected_1_2 = new Interval(timeslots.get(1));
        assertTrue(groupedByBlockOf_1.contains(expected_1_2));

        Interval expected_1_3 = new Interval(timeslots.get(2));
        assertTrue(groupedByBlockOf_1.contains(expected_1_3));

        Interval expected_1_4 = new Interval(timeslots.get(3));
        assertTrue(groupedByBlockOf_1.contains(expected_1_4));

        Interval expected_1_5 = new Interval(timeslots.get(4));
        assertTrue(groupedByBlockOf_1.contains(expected_1_5));

        System.out.println(groupedByBlockOf_1);

        /*

        [08:00-09:15], [09:30-10:45], [11:00-12:15], [12:30-13:45], [14:00-15:15]

        [08:00-09:15, 09:30-10:45], [8:00-10:45]
        [09:30-10:45, 11:00-12:15],
        [11:00-12:15, 12:30-13:45],
        [12:30-13:45, 14:00-15:15]

        [08:00-09:15, 09:30-10:45, 11:00-12:15],  [8:00-12:15]
        [09:30-10:45, 11:00-12:15, 12:30-13:45],
        [11:00-12:15, 12:30-13:45, 14:00-15:15]

        [08:00-09:15, 09:30-10:45, 11:00-12:15, 12:30-13:45], [8:00-13:45]
        [09:30-10:45, 11:00-12:15, 12:30-13:45, 14:00-15:15]

        [08:00-09:15, 09:30-10:45, 11:00-12:15, 12:30-13:45, 14:00-15:15] [8:00-15:15]


        */

        // Test of Block Size 2
        Set<Interval> groupedByBlockOf_2 =
                Schedule.groupIntervalsInBlocksForDay(
                        schedule.getSchedule().get(0), DayOfWeek.MONDAY, 2);
        System.out.println(groupedByBlockOf_2);

        assertEquals(4, groupedByBlockOf_2.size());
        Interval expected_2_1 = new Interval();
        expected_2_1.addSegment(timeslots.get(0));
        expected_2_1.addSegment(timeslots.get(1));
        assertTrue(groupedByBlockOf_2.contains(expected_2_1));

        Interval expected_2_2 = new Interval();
        expected_2_2.addSegment(timeslots.get(1));
        expected_2_2.addSegment(timeslots.get(2));
        assertTrue(groupedByBlockOf_2.contains(expected_2_2));

        Interval expected_2_3 = new Interval();
        expected_2_3.addSegment(timeslots.get(2));
        expected_2_3.addSegment(timeslots.get(3));
        assertTrue(groupedByBlockOf_2.contains(expected_2_3));

        Interval expected_2_4 = new Interval();
        expected_2_4.addSegment(timeslots.get(3));
        expected_2_4.addSegment(timeslots.get(4));
        assertTrue(groupedByBlockOf_2.contains(expected_2_4));

        // Test of Block Size 3
        Set<Interval> groupedByBlockOf_3 =
                Schedule.groupIntervalsInBlocksForDay(
                        schedule.getSchedule().get(0), DayOfWeek.MONDAY, 3);
        System.out.println(groupedByBlockOf_3);

        assertEquals(3, groupedByBlockOf_3.size());

        Interval expected_3_1 = new Interval();
        expected_3_1.addSegment(timeslots.get(0));
        expected_3_1.addSegment(timeslots.get(1));
        expected_3_1.addSegment(timeslots.get(2));
        assertTrue(groupedByBlockOf_3.contains(expected_3_1));

        Interval expected_3_2 = new Interval();
        expected_3_2.addSegment(timeslots.get(1));
        expected_3_2.addSegment(timeslots.get(2));
        expected_3_2.addSegment(timeslots.get(3));
        assertTrue(groupedByBlockOf_3.contains(expected_3_2));

        Interval expected_3_3 = new Interval();
        expected_3_3.addSegment(timeslots.get(2));
        expected_3_3.addSegment(timeslots.get(3));
        expected_3_3.addSegment(timeslots.get(4));
        assertTrue(groupedByBlockOf_3.contains(expected_3_3));

        // Test of Block Size 4
        Set<Interval> groupedByBlockOf_4 =
                Schedule.groupIntervalsInBlocksForDay(
                        schedule.getSchedule().get(0), DayOfWeek.MONDAY, 4);
        System.out.println(groupedByBlockOf_4);
        assertEquals(2, groupedByBlockOf_4.size());

        Interval expected_4_1 = new Interval();
        expected_4_1.addSegment(timeslots.get(0));
        expected_4_1.addSegment(timeslots.get(1));
        expected_4_1.addSegment(timeslots.get(2));
        expected_4_1.addSegment(timeslots.get(3));
        assertTrue(groupedByBlockOf_4.contains(expected_4_1));

        Interval expected_4_2 = new Interval();
        expected_4_2.addSegment(timeslots.get(1));
        expected_4_2.addSegment(timeslots.get(2));
        expected_4_2.addSegment(timeslots.get(3));
        expected_4_2.addSegment(timeslots.get(4));
        assertTrue(groupedByBlockOf_4.contains(expected_4_2));

        // Test of Block Size 5
        Set<Interval> groupedByBlockOf_5 =
                Schedule.groupIntervalsInBlocksForDay(
                        schedule.getSchedule().get(0), DayOfWeek.MONDAY, 5);
        System.out.println(groupedByBlockOf_5);

        assertEquals(1, groupedByBlockOf_5.size());
        Interval expected_5_1 = new Interval();
        for (Timeslot ts : timeslots) {
            expected_5_1.addSegment(ts);
        }
        assertTrue(groupedByBlockOf_5.contains(expected_5_1));
    }

    @Test
    public void Test_Schedule_Merge_No_Intersections() {
        Employee a = new Employee();
        a.setId(UUID.randomUUID());
        TestSchedule schedule_A = new TestSchedule(a);

        Employee b = new Employee();
        b.setId(UUID.randomUUID());
        TestSchedule schedule_B = new TestSchedule(b);

        Timeslot[] timeslots =
                new Timeslot[] {
                    SchedulerTestUtil.newTimeSlot(8, 0, 9, 15),
                    SchedulerTestUtil.newTimeSlot(9, 30, 10, 45),
                    SchedulerTestUtil.newTimeSlot(11, 0, 12, 15),
                    SchedulerTestUtil.newTimeSlot(12, 30, 13, 45),
                    SchedulerTestUtil.newTimeSlot(14, 0, 15, 15)
                };

        Interval i_0800_0915 = new Interval(new Segment(8, 0, 9, 15));
        Interval i_0930_1045 = new Interval(new Segment(9, 30, 10, 45));
        Interval i_1100_1215 = new Interval(new Segment(11, 0, 12, 15));
        Interval i_1230_1345 = new Interval(new Segment(12, 30, 13, 45));
        Interval i_1400_1515 = new Interval(new Segment(14, 0, 15, 15));

        // Expected: For every WeekDay there should be 5 Timeslots created, due to no intersecting
        // TimeSlots could be determined
        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> mergedSchedule =
                Schedule.merge(
                        Set.of(0),
                        EnumSet.allOf(DayOfWeek.class),
                        Set.of(timeslots),
                        Set.of(schedule_A, schedule_B));

        for (DayOfWeek d : DayOfWeek.values()) {
            assertEquals(
                    timeslots.length,
                    mergedSchedule
                            .getOrDefault(0, new HashMap<>())
                            .getOrDefault(d, new TreeSet<>())
                            .size());
        }

        DayOfWeek[] weekDays = DayOfWeek.values();
        for (DayOfWeek dw : weekDays) {
            NavigableSet<Interval> mergedSchedule_OnWeekDay =
                    mergedSchedule.getOrDefault(0, new HashMap<>()).get(dw);
            assertNotNull(mergedSchedule_OnWeekDay);
            assertEquals(timeslots.length, mergedSchedule_OnWeekDay.size());

            assertTrue(mergedSchedule_OnWeekDay.contains(i_0800_0915));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_0930_1045));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1100_1215));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1230_1345));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1400_1515));
        }
    }

    @Test
    public void Test_Schedule_Merge_With_Intersections() {
        Employee a = new Employee();
        a.setId(UUID.randomUUID());
        TestSchedule schedule_A = new TestSchedule(a);

        Employee b = new Employee();
        b.setId(UUID.randomUUID());
        TestSchedule schedule_B = new TestSchedule(b);

        Timeslot[] timeslots =
                new Timeslot[] {
                    SchedulerTestUtil.newTimeSlot(8, 0, 9, 15),
                    SchedulerTestUtil.newTimeSlot(9, 30, 10, 45),
                    SchedulerTestUtil.newTimeSlot(11, 0, 12, 15),
                    SchedulerTestUtil.newTimeSlot(12, 30, 13, 45),
                    SchedulerTestUtil.newTimeSlot(14, 0, 15, 15)
                };

        Interval i_0800_0915 = new Interval(new Segment(8, 0, 9, 15));
        Interval i_0930_1045 = new Interval(new Segment(9, 30, 10, 45));
        Interval i_1100_1215 = new Interval(new Segment(11, 0, 12, 15));
        Interval i_1230_1345 = new Interval(new Segment(12, 30, 13, 45));
        Interval i_1400_1515 = new Interval(new Segment(14, 0, 15, 15));

        // Add interval to busy schedule
        assertTrue(schedule_A.add(0, DayOfWeek.MONDAY, i_0800_0915));
        assertTrue(schedule_A.add(0, DayOfWeek.MONDAY, i_0930_1045));
        assertTrue(schedule_A.add(0, DayOfWeek.MONDAY, i_1230_1345));
        assertTrue(schedule_A.add(0, DayOfWeek.MONDAY, i_1400_1515));
        // Monday: Only Available Interval -> 11:00-12:15

        // Keep schedule_B empty

        // Expected: For every WeekDay  except Monday there should be 5 Timeslots created, due to no
        // intersecting
        // But for Monday there should only be ony timeslot available
        Interval[] expectedMonday =
                new Interval[] {
                    i_1100_1215,
                };

        Set<DayOfWeek> weekDays = new HashSet<>(Set.of(DayOfWeek.values()));
        weekDays.remove(DayOfWeek.MONDAY);

        // TimeSlots could be determined
        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> mergedSchedule =
                Schedule.merge(
                        Set.of(0),
                        EnumSet.allOf(DayOfWeek.class),
                        Set.of(timeslots),
                        Set.of(schedule_A, schedule_B));

        Set<Interval> mergedSchedule_OnMonday = mergedSchedule.get(0).get(DayOfWeek.MONDAY);
        for (Interval t : expectedMonday) {
            assertTrue(mergedSchedule_OnMonday.contains(t), String.format("Should contain %s", t));
        }

        assertEquals(expectedMonday.length, mergedSchedule_OnMonday.size());

        Set<DayOfWeek> days = EnumSet.allOf(DayOfWeek.class);
        days.remove(DayOfWeek.MONDAY);
        for (DayOfWeek d : days) {
            assertEquals(
                    timeslots.length,
                    mergedSchedule.get(0).getOrDefault(d, new TreeSet<>()).size());
        }

        for (DayOfWeek dw : weekDays) {
            Set<Interval> mergedSchedule_OnWeekDay = mergedSchedule.get(0).get(dw);
            assertNotNull(mergedSchedule_OnWeekDay);
            assertEquals(timeslots.length, mergedSchedule_OnWeekDay.size());

            assertTrue(mergedSchedule_OnWeekDay.contains(i_0800_0915));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_0930_1045));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1100_1215));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1230_1345));
            assertTrue(mergedSchedule_OnWeekDay.contains(i_1400_1515));
        }
    }

    @Test
    public void Test_Schedule_Merge_With_Intersections_Yielding_NoResults() {
        Employee a = new Employee();
        a.setId(UUID.randomUUID());
        TestSchedule schedule_A = new TestSchedule(a);

        Employee b = new Employee();
        b.setId(UUID.randomUUID());
        TestSchedule schedule_B = new TestSchedule(b);

        TestSchedule[] schedules = new TestSchedule[] {schedule_A, schedule_B};

        Timeslot[] timeslots =
                new Timeslot[] {
                    SchedulerTestUtil.newTimeSlot(8, 0, 9, 15),
                    SchedulerTestUtil.newTimeSlot(9, 30, 10, 45),
                    SchedulerTestUtil.newTimeSlot(11, 0, 12, 15),
                    SchedulerTestUtil.newTimeSlot(12, 30, 13, 45),
                    SchedulerTestUtil.newTimeSlot(14, 0, 15, 15)
                };

        DayOfWeek[] days = DayOfWeek.values();
        for (int index = 0; index < days.length; index++) {
            DayOfWeek d = days[index];
            for (int ii = 0; ii < timeslots.length; ii++) {
                Timeslot t = timeslots[ii];
                TestSchedule schedule = schedules[(index + ii) % schedules.length];
                schedule.add(0, d, new Interval(t));
            }
        }

        // TimeSlots could be determined
        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> mergedSchedule =
                Schedule.merge(
                        Set.of(0),
                        EnumSet.allOf(DayOfWeek.class),
                        Set.of(timeslots),
                        Set.of(schedule_A, schedule_B));

        Interval i_0800_0915 = new Interval(new Segment(8, 0, 9, 15));
        Interval i_0930_1045 = new Interval(new Segment(9, 30, 10, 45));
        Interval i_1100_1215 = new Interval(new Segment(11, 0, 12, 15));
        Interval i_1230_1345 = new Interval(new Segment(12, 30, 13, 45));
        Interval i_1400_1515 = new Interval(new Segment(14, 0, 15, 15));

        for (DayOfWeek d : days) {
            Set<Interval> s = mergedSchedule.get(0).get(d);

            assertFalse(s.contains(i_0800_0915));
            assertFalse(s.contains(i_0930_1045));
            assertFalse(s.contains(i_1100_1215));
            assertFalse(s.contains(i_1230_1345));
            assertFalse(s.contains(i_1400_1515));

            assertEquals(0, s.size());
        }

        for (DayOfWeek d : DayOfWeek.values()) {
            assertEquals(0, mergedSchedule.get(0).getOrDefault(d, new TreeSet<>()).size());
        }
    }
}
