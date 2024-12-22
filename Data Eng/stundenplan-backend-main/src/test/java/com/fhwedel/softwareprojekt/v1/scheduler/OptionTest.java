package com.fhwedel.softwareprojekt.v1.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @Test
    public void TestOption() {
        Timeslot t = new Timeslot();
        t.setId(UUID.randomUUID());
        t.setIndex(0);
        t.setStartTime(LocalTime.of(8, 0));
        t.setEndTime(LocalTime.of(9, 15));
        Interval i = new Interval(t);

        Course c = new Course();
        c.setId(UUID.randomUUID());
        c.setBlockSize(1);
        c.setSlotsPerWeek(1);
        c.setAbbreviation("c1");
        c.setName("c1");
        c.setCasID("cas.c1");
        c.setWeeksPerSemester(12);
        c.setDescription("desc");
        c.setCourseType(null);

        Option opt = new Option(c, i, 0, DayOfWeek.MONDAY);

        assertEquals(c, opt.getCourse());
        assertEquals(i.getStart(), opt.getStart());
        assertEquals(i.getEnd(), opt.getEnd());

        List<Room> rooms = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            Room r1 = new Room();
            r1.setId(UUID.randomUUID());
            r1.setAbbreviation("hs" + (index + 1));
            r1.setName("Hörsaal " + (index + 1));
            r1.setCapacity((int) Math.log((index + 1) * 2));
            r1.setIdentifier("cas.hs" + (index + 1));
            r1.setRoomType(null);
            rooms.add(r1);
        }

        Set<Room> roomComp1 = Set.of(rooms.get(0), rooms.get(1));
        Set<Room> roomComp2 = Set.of(rooms.get(1), rooms.get(2));
        assertTrue(opt.addRoomCombination(roomComp1));
        assertTrue(opt.addRoomCombination(roomComp2));

        Set<List<Room>> roomCombinations = new HashSet<>();
        roomCombinations.add(roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList());
        roomCombinations.add(roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList());

        assertEquals(roomCombinations, new HashSet<>(opt.getAllRoomCombinations()));
        assertEquals(roomCombinations, new HashSet<>(opt.getAllFreeRoomCombinations()));

        assertEquals(2, opt.getDegreesOfFreedom());

        assertFalse(opt.isBlocked());

        // Create Identical Rooms but solely define UUID to check for containing Rooms

        Room r1_onlyuuid = new Room();
        r1_onlyuuid.setId(rooms.get(0).getId());

        Room r2_onlyuuid = new Room();
        r2_onlyuuid.setId(rooms.get(1).getId());

        Room r3_onlyuuid = new Room();
        r3_onlyuuid.setId(rooms.get(2).getId());

        assertTrue(opt.containsRoomGroup(Set.of(r1_onlyuuid, r2_onlyuuid)));
        assertTrue(opt.containsRoomGroup(Set.of(r2_onlyuuid, r1_onlyuuid)));

        assertTrue(opt.containsRoomGroup(Set.of(r2_onlyuuid, r3_onlyuuid)));
        assertTrue(opt.containsRoomGroup(Set.of(r3_onlyuuid, r2_onlyuuid)));

        assertFalse(opt.containsRoomGroup(Set.of(r1_onlyuuid)));
        assertFalse(opt.containsRoomGroup(Set.of(r2_onlyuuid)));
        assertFalse(opt.containsRoomGroup(Set.of(r3_onlyuuid)));
        assertFalse(opt.containsRoomGroup(Set.of(r1_onlyuuid, r3_onlyuuid)));
        assertFalse(opt.containsRoomGroup(Set.of(r3_onlyuuid, r1_onlyuuid)));
    }

    @Test
    public void Test_Option_copy() {
        Timeslot t = new Timeslot();
        t.setId(UUID.randomUUID());
        t.setIndex(0);
        t.setStartTime(LocalTime.of(8, 0));
        t.setEndTime(LocalTime.of(9, 15));
        Interval i = new Interval(t);

        Course c = new Course();
        c.setId(UUID.randomUUID());
        c.setBlockSize(1);
        c.setSlotsPerWeek(1);
        c.setAbbreviation("c1");
        c.setName("c1");
        c.setCasID("cas.c1");
        c.setWeeksPerSemester(12);
        c.setDescription("desc");
        c.setCourseType(null);

        Option opt = new Option(c, i, 0, DayOfWeek.MONDAY);

        assertEquals(c, opt.getCourse());
        assertEquals(i.getStart(), opt.getStart());
        assertEquals(i.getEnd(), opt.getEnd());

        List<Room> rooms = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            Room r1 = new Room();
            r1.setId(UUID.randomUUID());
            r1.setAbbreviation("hs" + (index + 1));
            r1.setName("Hörsaal " + (index + 1));
            r1.setCapacity((int) Math.log((index + 1) * 2));
            r1.setIdentifier("cas.hs" + (index + 1));
            r1.setRoomType(null);
            rooms.add(r1);
        }

        Set<Room> roomComp1 = Set.of(rooms.get(0), rooms.get(1));
        Set<Room> roomComp2 = Set.of(rooms.get(1), rooms.get(2));

        assertTrue(opt.addRoomCombination(roomComp1));
        assertTrue(opt.addRoomCombination(roomComp2));

        Set<List<Room>> roomCombinations = new HashSet<>();
        roomCombinations.add(roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList());
        roomCombinations.add(roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList());

        assertEquals(roomCombinations, new HashSet<>(opt.getAllRoomCombinations()));
        assertEquals(roomCombinations, new HashSet<>(opt.getAllFreeRoomCombinations()));
        assertEquals(2, opt.getDegreesOfFreedom());
        assertFalse(opt.isBlocked());

        Option cloneOpts = Option.copy(opt);
        assertEquals(roomCombinations, new HashSet<>(cloneOpts.getAllRoomCombinations()));
        assertEquals(roomCombinations, new HashSet<>(cloneOpts.getAllFreeRoomCombinations()));
        assertEquals(2, cloneOpts.getDegreesOfFreedom());
        assertFalse(cloneOpts.isBlocked());

        assertEquals(cloneOpts, opt);
        assertEquals(cloneOpts.segments, opt.segments);
        assertEquals(cloneOpts.getStart(), opt.getStart());
        assertEquals(cloneOpts.getEnd(), opt.getEnd());
        assertEquals(cloneOpts.getDuration(), opt.getDuration());
        assertEquals(cloneOpts.size(), opt.size());
        assertEquals(cloneOpts.allowedOverhang, opt.allowedOverhang);
        assertEquals(cloneOpts.getCourse(), opt.getCourse());
        assertEquals(cloneOpts.getDay(), opt.getDay());

        Set<Room> roomComp3 = Set.of(rooms.get(0), rooms.get(2));
        cloneOpts.addRoomCombination(roomComp3);

        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp3.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(cloneOpts.getAllRoomCombinations()));
        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp3.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(cloneOpts.getAllFreeRoomCombinations()));
        assertEquals(3, cloneOpts.getDegreesOfFreedom());

        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllRoomCombinations()));
        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllFreeRoomCombinations()));
        assertEquals(2, opt.getDegreesOfFreedom());
    }

    @Test
    public void Test_Option_WeekEvent_usesOneRoom() {
        List<Timeslot> timeslots = SchedulerTestUtil.generateTimeslots(5);

        Timeslot t = timeslots.get(1);

        Interval i = new Interval(t);

        Course c = new Course();
        c.setId(UUID.randomUUID());
        c.setBlockSize(1);
        c.setSlotsPerWeek(1);
        c.setAbbreviation("c1");
        c.setName("c1");
        c.setCasID("cas.c1");
        c.setWeeksPerSemester(12);
        c.setDescription("desc");
        c.setCourseType(null);

        Option opt = new Option(c, i, 0, DayOfWeek.MONDAY);

        assertEquals(c, opt.getCourse());
        assertEquals(i.getStart(), opt.getStart());
        assertEquals(i.getEnd(), opt.getEnd());

        List<Room> rooms = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            Room r1 = new Room();
            r1.setId(UUID.randomUUID());
            r1.setAbbreviation("hs" + (index + 1));
            r1.setName("Hörsaal " + (index + 1));
            r1.setCapacity((int) Math.log((index + 1) * 2));
            r1.setIdentifier("cas.hs" + (index + 1));
            r1.setRoomType(null);
            rooms.add(r1);
        }

        Set<Room> roomComp1 = Set.of(rooms.get(0), rooms.get(1));
        Set<Room> roomComp2 = Set.of(rooms.get(1), rooms.get(2));

        assertTrue(opt.addRoomCombination(roomComp1));
        assertTrue(opt.addRoomCombination(roomComp2));
        assertEquals(2, opt.getDegreesOfFreedom());

        // Create new Event from a Course which only share room
        Course otherCourse = new Course();
        otherCourse.setId(UUID.randomUUID());
        otherCourse.setCourseType(null);
        otherCourse.setBlockSize(1);
        otherCourse.setSlotsPerWeek(1);
        otherCourse.setName("course2");
        otherCourse.setCasID("cas.course2");
        otherCourse.setLecturers(Collections.emptyList());
        otherCourse.setSemesters(Collections.emptyList());

        /*
           Use Rooms 0
           therefore roomcombination1 has overlap and cant be used
        */
        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setRooms(List.of(rooms.get(0)));
        weekEvent.setCourse(otherCourse);
        weekEvent.setWeekday(DayOfWeek.MONDAY);
        weekEvent.setWeek(0);
        weekEvent.setTimeslots(timeslots.subList(0, 2));

        assertTrue(opt.addEvent(weekEvent));

        assertTrue(opt.getIntersectingEvents().contains(weekEvent));

        WeekEvent weekEventOnlyUUID = new WeekEvent();
        weekEventOnlyUUID.setId(weekEvent.getId());
        assertTrue(opt.getIntersectingEvents().contains(weekEventOnlyUUID));

        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllRoomCombinations()));
        assertEquals(
                Set.of(roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllFreeRoomCombinations()));

        assertFalse(opt.isBlocked());
        assertEquals(1, opt.getDegreesOfFreedom());

        assertFalse(opt.addEvent(weekEvent));

        assertTrue(opt.removeEvent(weekEvent));

        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllRoomCombinations()));
        assertEquals(
                Set.of(
                        roomComp1.stream().sorted(Comparator.comparing(Room::getId)).toList(),
                        roomComp2.stream().sorted(Comparator.comparing(Room::getId)).toList()),
                new HashSet<>(opt.getAllFreeRoomCombinations()));

        assertEquals(2, opt.getDegreesOfFreedom());
        assertFalse(opt.removeEvent(weekEvent));
    }

    @Test
    public void Test_Option_DTOs() {
        final DayOfWeek expected_day = DayOfWeek.MONDAY;
        List<Timeslot> timeslots = SchedulerTestUtil.generateTimeslots(5);

        Timeslot t = timeslots.get(1);
        Interval i = new Interval(t);

        Course c = new Course();
        c.setId(UUID.randomUUID());
        c.setBlockSize(1);
        c.setSlotsPerWeek(1);
        c.setAbbreviation("c1");
        c.setName("c1");
        c.setCasID("cas.c1");
        c.setWeeksPerSemester(12);
        c.setDescription("desc");
        c.setCourseType(null);

        Employee e0 = new Employee();
        e0.setId(UUID.randomUUID());
        e0.setFirstname("e1");
        e0.setLastname("e1");
        e0.setAbbreviation("e1");
        e0.setCourses(List.of(c));
        c.setLecturers(List.of(e0));

        Course otherCourse = new Course();
        Employee e1 = new Employee();
        e1.setId(UUID.randomUUID());
        e1.setFirstname("e1");
        e1.setLastname("e1");
        e1.setAbbreviation("e1");
        e1.setCourses(List.of(otherCourse));

        otherCourse.setCourseType(null);
        otherCourse.setId(UUID.randomUUID());
        otherCourse.setBlockSize(1);
        otherCourse.setSlotsPerWeek(1);
        otherCourse.setName("course2");
        otherCourse.setCasID("cas.course2");
        otherCourse.setLecturers(List.of(e1));
        otherCourse.setSemesters(Collections.emptyList());

        List<Room> rooms = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            Room r1 = new Room();
            r1.setId(UUID.randomUUID());
            r1.setAbbreviation("hs" + (index + 1));
            r1.setName("Hörsaal " + (index + 1));
            r1.setCapacity((int) Math.log((index + 1) * 2));
            r1.setIdentifier("cas.hs" + (index + 1));
            r1.setRoomType(null);
            rooms.add(r1);
        }

        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setRooms(List.of(rooms.get(0)));
        weekEvent.setCourse(otherCourse);
        weekEvent.setWeekday(expected_day);
        weekEvent.setWeek(0);
        weekEvent.setTimeslots(timeslots.subList(0, 2));

        /////////////////////////////////////////////////////////
        // Test case
        // - For every room Combination get an equal DTO
        //
        {
            // Setup
            Option opt = new Option(c, i, 0, expected_day);
            Set<Room> roomComp1 = Set.of(rooms.get(0), rooms.get(1));
            Set<Room> roomComp2 = Set.of(rooms.get(1), rooms.get(2));

            assertTrue(opt.addRoomCombination(roomComp1));
            assertTrue(opt.addRoomCombination(roomComp2));
            assertEquals(2, opt.getDegreesOfFreedom());

            OptionDTO expected_dto_forRoomComb_1 = new OptionDTO();
            expected_dto_forRoomComb_1.setTimeslots(List.of(t.getId()));
            expected_dto_forRoomComb_1.setWeekday(DayOfWeek.MONDAY);
            expected_dto_forRoomComb_1.setWeek(0);
            expected_dto_forRoomComb_1.setRooms(
                    roomComp1.stream().map(Room::getId).sorted(UUID::compareTo).toList());

            OptionDTO expected_dto_forRoomComb_2 = new OptionDTO();
            expected_dto_forRoomComb_2.setTimeslots(List.of(t.getId()));
            expected_dto_forRoomComb_2.setWeekday(expected_day);
            expected_dto_forRoomComb_2.setWeek(0);
            expected_dto_forRoomComb_2.setRooms(
                    roomComp2.stream().map(Room::getId).sorted(UUID::compareTo).toList());

            List<OptionDTO> expected_optionDTOS =
                    List.of(expected_dto_forRoomComb_1, expected_dto_forRoomComb_2);
            expected_optionDTOS =
                    expected_optionDTOS.stream().sorted(Option.optionDTOComparator()).toList();

            // Test Subject
            List<OptionDTO> actual_dtos = opt.createOptionDTOs(timeslots);
            assertEquals(2, actual_dtos.size());

            // Evaluate
            assertTrue(actual_dtos.contains(expected_dto_forRoomComb_1));
            assertTrue(actual_dtos.contains(expected_dto_forRoomComb_2));

            assertEquals(expected_optionDTOS, actual_dtos);
            assertEquals(2, opt.getDegreesOfFreedom());
        }

        /////////////////////////////////////////////////////////
        // Test case
        // -
        // - For every room Combination get an equal DTO
        // - exclude room combination 1
        //
        {

            // Setup
            Option opt = new Option(c, i, 0, expected_day);
            Set<Room> roomComp1 = Set.of(rooms.get(0), rooms.get(1));
            Set<Room> roomComp2 = Set.of(rooms.get(1), rooms.get(2));

            assertTrue(opt.addRoomCombination(roomComp1));
            assertTrue(opt.addRoomCombination(roomComp2));
            assertEquals(2, opt.getDegreesOfFreedom());

            OptionDTO expected_dto_forRoomComb_1 = new OptionDTO();
            expected_dto_forRoomComb_1.setTimeslots(List.of(t.getId()));
            expected_dto_forRoomComb_1.setWeekday(expected_day);
            expected_dto_forRoomComb_1.setWeek(0);
            expected_dto_forRoomComb_1.setRooms(
                    roomComp1.stream().map(Room::getId).sorted(Option.uuidComparator()).toList());

            OptionDTO expected_dto_forRoomComb_2 = new OptionDTO();
            expected_dto_forRoomComb_2.setTimeslots(List.of(t.getId()));
            expected_dto_forRoomComb_2.setWeekday(expected_day);
            expected_dto_forRoomComb_2.setWeek(0);
            expected_dto_forRoomComb_2.setRooms(
                    roomComp2.stream().map(Room::getId).sorted(Option.uuidComparator()).toList());

            List<OptionDTO> expected_optionDTOS = List.of(expected_dto_forRoomComb_2);
            expected_optionDTOS =
                    expected_optionDTOS.stream().sorted(Option.optionDTOComparator()).toList();

            // Start Test
            assertTrue(opt.addEvent(weekEvent));
            List<OptionDTO> actual_dtos = opt.createOptionDTOs(timeslots);

            // Evaluate
            assertFalse(actual_dtos.contains(expected_dto_forRoomComb_1));
            assertTrue(actual_dtos.contains(expected_dto_forRoomComb_2));
            assertEquals(expected_optionDTOS, actual_dtos);

            assertEquals(1, opt.getDegreesOfFreedom());
        }
    }

    @Test
    public void Test_Option_Block() {
        List<DayOfWeek> days = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        int MON = 0;
        int TUE = 1;

        int expected_timeslots = 4;
        int expected_amount_courses = 3;
        int expected_amount_rooms = 3;

        int[] blockSizes = new int[] {1, 1, 1};
        int[] slotsPerWeek = new int[] {1, 1, 1};
        int[][] lecture_is_before =
                new int[][] { //
                    new int[] {0, 0, 0}, //
                    new int[] {0, 0, 0}, //
                    new int[] {0, 0, 0} //
                };
        int[][] lecture_is_parallel =
                new int[][] { //
                    new int[] {0, 0, 0}, //
                    new int[] {0, 0, 0}, //
                    new int[] {0, 0, 0} //
                };

        int[][] c1_is_available_for = new int[days.size()][expected_timeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1, 1}; // | WeekDay 2

        int[][] c2_is_available_for = new int[days.size()][expected_timeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1, 1}; // | WeekDay 2

        int[][] c3_is_available_for = new int[days.size()][expected_timeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1, 1}; // | WeekDay 2

        SchedulerTestUtil.Generator generator =
                new SchedulerTestUtil.Generator(
                        days,
                        expected_timeslots,
                        expected_amount_courses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        int[][] employeeHostCourse =
                new int[][] { //
                    new int[] {1, 1, 0}, //
                    new int[] {0, 1, 1}, //
                };

        int[][][] employeeWorktingtimes =
                new int[][][] {
                    new int[][] { //
                        new int[] {0, 0, 0, 0}, //
                        new int[] {0, 0, 0, 0}, //
                    }, //
                    new int[][] { //
                        new int[] {0, 0, 0, 0}, //
                        new int[] {0, 0, 0, 0}, //
                    }, //
                    new int[][] { //
                        new int[] {0, 0, 0, 0}, //
                        new int[] {0, 0, 0, 0}, //
                    } //
                };

        List<Employee> employees =
                generator.generateEmployees(2, employeeHostCourse, employeeWorktingtimes);
        List<Timeslot> timeslots = generator.getTimeslots();
        List<Room> rooms = generator.generateRooms(3, null, 10);
        List<Course> courses = generator.getCourses();

        {
            Option opt =
                    new Option(
                            courses.get(0),
                            Interval.from(timeslots.subList(0, 1)),
                            0,
                            DayOfWeek.MONDAY);

            Set<Room> roomCombination1 = new HashSet<>(rooms.subList(0, 2));
            Set<Room> roomCombination2 = new HashSet<>(rooms.subList(1, 3));

            assertTrue(opt.addRoomCombination(roomCombination1));
            assertTrue(opt.addRoomCombination(roomCombination2));
            assertEquals(2, opt.getDegreesOfFreedom());

            WeekEvent event = new WeekEvent();
            event.setId(UUID.randomUUID());
            event.setCourse(courses.get(2));
            event.setRooms(List.of(rooms.get(0)));
            event.setTimeslots(timeslots.subList(0, 1));
            event.setWeekday(DayOfWeek.MONDAY);
            event.setWeek(0);

            assertTrue(opt.addEvent(event));
            assertFalse(opt.isBlocked());
            assertEquals(1, opt.getDegreesOfFreedom());
        }

        {
            Option opt =
                    new Option(
                            courses.get(0),
                            Interval.from(timeslots.subList(0, 1)),
                            0,
                            DayOfWeek.MONDAY);

            Set<Room> roomCombination1 = new HashSet<>(rooms.subList(0, 1));
            Set<Room> roomCombination2 = new HashSet<>(rooms.subList(1, 2));
            Set<Room> roomCombination3 = new HashSet<>(rooms.subList(2, 3));

            assertTrue(opt.addRoomCombination(roomCombination1));
            assertTrue(opt.addRoomCombination(roomCombination2));
            assertTrue(opt.addRoomCombination(roomCombination3));
            assertEquals(3, opt.getDegreesOfFreedom());
            assertFalse(opt.isBlocked());

            WeekEvent event = new WeekEvent();
            event.setId(UUID.randomUUID());
            event.setCourse(courses.get(0));
            event.setRooms(List.of(rooms.get(0)));
            event.setTimeslots(timeslots.subList(0, 1));
            event.setWeekday(DayOfWeek.MONDAY);
            event.setWeek(0);
            assertTrue(opt.addEvent(event));
            assertEquals(0, opt.getDegreesOfFreedom());
            assertTrue(opt.isBlocked());
        }
        {
            Option opt =
                    new Option(
                            courses.get(0),
                            Interval.from(timeslots.subList(0, 1)),
                            0,
                            DayOfWeek.MONDAY);

            Set<Room> roomCombination1 = new HashSet<>(rooms.subList(0, 1));
            Set<Room> roomCombination2 = new HashSet<>(rooms.subList(1, 2));
            Set<Room> roomCombination3 = new HashSet<>(rooms.subList(2, 3));

            assertTrue(opt.addRoomCombination(roomCombination1));
            assertTrue(opt.addRoomCombination(roomCombination2));
            assertTrue(opt.addRoomCombination(roomCombination3));
            assertEquals(3, opt.getDegreesOfFreedom());
            assertFalse(opt.isBlocked());

            WeekEvent event = new WeekEvent();
            event.setId(UUID.randomUUID());
            event.setCourse(courses.get(1));
            event.setRooms(List.of(rooms.get(0)));
            event.setTimeslots(timeslots.subList(0, 1));
            event.setWeekday(DayOfWeek.MONDAY);
            event.setWeek(0);
            assertTrue(opt.addEvent(event));
            assertEquals(0, opt.getDegreesOfFreedom());
            assertTrue(opt.isBlocked());
        }
    }
}
