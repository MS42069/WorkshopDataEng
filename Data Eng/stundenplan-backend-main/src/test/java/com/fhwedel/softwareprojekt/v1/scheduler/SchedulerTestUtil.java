package com.fhwedel.softwareprojekt.v1.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.util.Pair;

public class SchedulerTestUtil {

    public static List<Timeslot> generateTimeslots(int amountOfLessons) {
        LocalTime lessonDuration = LocalTime.of(1, 15);
        LocalTime breakDuration = LocalTime.of(0, 15);
        LocalTime curr = LocalTime.of(8, 0);

        Timeslot[] tss = new Timeslot[amountOfLessons];
        for (int i = 0; i < amountOfLessons; i++) {
            Timeslot ts1 = new Timeslot();
            ts1.setId(UUID.randomUUID());
            ts1.setStartTime(curr);
            curr = curr.plusHours(lessonDuration.getHour()).plusMinutes(lessonDuration.getMinute());
            ts1.setEndTime(curr);
            curr = curr.plusHours(breakDuration.getHour()).plusMinutes(breakDuration.getMinute());
            tss[i] = ts1;
        }

        return new ArrayList<>(Arrays.stream(tss).toList());
    }

    public static Timeslot newTimeSlot(int startHour, int startMinute, int endHour, int endMinute) {
        Timeslot ts = new Timeslot();
        ts.setId(UUID.randomUUID());
        ts.setStartTime(LocalTime.of(startHour, startMinute));
        ts.setEndTime(LocalTime.of(endHour, endMinute));
        return ts;
    }

    public static void testRelationForLecturer(
            Scheduler underTest,
            Employee employee,
            Integer week,
            List<DayOfWeek> days,
            List<Timeslot> timeslots,
            List<Course> courses,
            int[] holdsLecture,
            int[][] employeeWorkTime) {
        assertNotNull(employee);
        Optional<Curriculum<Employee>> _employee_Curriculum =
                underTest.getScheduleForEmployee(employee);
        assertTrue(_employee_Curriculum.isPresent(), "_employee_Curriculum");

        Curriculum<Employee> employee_curr = _employee_Curriculum.get();

        assertEquals(employee, employee_curr.getOwner());

        assertEquals(employee.getId(), employee_curr.getOwner().getId());
        assertEquals(employee.getAbbreviation(), employee_curr.getOwner().getAbbreviation());
        for (DayOfWeek d : days) {
            int index = 0;
            for (int isWorking : employeeWorkTime[d.ordinal()]) {
                assertEquals(
                        isWorking != 0,
                        employee_curr.isTimeslotAvailable(week, d, timeslots.get(index)));
                index++;
            }
        }

        assertEquals(employee, employee_curr.getOwner());

        int index = 0;
        for (int isLecturingCourse : holdsLecture) {
            assertEquals(
                    isLecturingCourse != 0, courses.get(index).getLecturers().contains(employee));
            assertEquals(
                    isLecturingCourse == 0, !courses.get(index).getLecturers().contains(employee));
            index++;
        }
    }

    public static void testRelationsForCourse(
            Scheduler underTest,
            Course course_1,
            int amountRooms,
            int amountTimeslots,
            List<DayOfWeek> days,
            List<Timeslot> timeslots,
            List<Room> rooms,
            int expected_degrees_of_freedom,
            int[][][] expected_course_1_options,
            int[][][][] expected_course_1_options_available_rooms) {

        assertNotNull(underTest);
        assertNotNull(course_1);
        assertNotNull(days);
        assertNotNull(timeslots);
        assertNotNull(rooms);
        assertNotNull(expected_course_1_options);
        assertNotNull(expected_course_1_options_available_rooms);

        assertEquals(days.size(), expected_course_1_options.length);
        assertEquals(days.size(), expected_course_1_options_available_rooms.length);

        Optional<Options> _course_options = underTest.getOptionsForCourse(course_1);
        assertTrue(_course_options.isPresent());

        Options course_options = _course_options.get();

        assertEquals(course_1, course_options.getCourse());

        for (int weekDayIndex = 0; weekDayIndex < days.size(); weekDayIndex++) {
            int[][] expected_options_on_weekday = expected_course_1_options[weekDayIndex];
            DayOfWeek day = DayOfWeek.of(weekDayIndex + 1);
            NavigableSet<Option> actual_options_on_weekday =
                    course_options.getScheduleForDay(1, day);

            assertEquals(
                    expected_options_on_weekday.length,
                    actual_options_on_weekday.size(),
                    "unexpected expected amount of options for weekday");
            assertEquals(
                    expected_options_on_weekday.length,
                    expected_course_1_options_available_rooms[weekDayIndex].length,
                    "unexpected length for room options, "
                            + "does not match the expected amount of options");

            int[][][] expected_available_rooms_for_options_on_weekday =
                    expected_course_1_options_available_rooms[weekDayIndex];

            for (int expected_option_index = 0;
                    expected_option_index < expected_options_on_weekday.length;
                    expected_option_index++) {
                int[] expected_option = expected_options_on_weekday[expected_option_index];
                int[][] expected_available_rooms =
                        expected_available_rooms_for_options_on_weekday[expected_option_index];

                assertEquals(
                        amountTimeslots,
                        expected_option.length,
                        "expected options must span the specified amount of timeslots");

                List<Timeslot> expected_timeslots = new ArrayList<>();
                int lastIndex = -1;
                for (int timeslot_index_associated_with_option = 0;
                        timeslot_index_associated_with_option < expected_option.length;
                        timeslot_index_associated_with_option++) {
                    if (expected_option[timeslot_index_associated_with_option] != 0) {
                        if (!expected_timeslots.isEmpty()) {
                            assertEquals(
                                    lastIndex,
                                    timeslot_index_associated_with_option - 1,
                                    "expected timeslots must be consecutive");
                        }

                        expected_timeslots.add(
                                timeslots.get(timeslot_index_associated_with_option));
                        lastIndex = timeslot_index_associated_with_option;
                    }
                }
                assertEquals(
                        course_1.getBlockSize(),
                        expected_timeslots.size(),
                        "expected options must contain the same amount of timeslots");

                // Find first occurence of the option
                Optional<Option> _associatedOption =
                        actual_options_on_weekday.stream()
                                .filter(
                                        segments ->
                                                expected_timeslots.stream()
                                                        .map(Interval::new)
                                                        .allMatch(segments::isIntersecting))
                                .findFirst();

                assertTrue(_associatedOption.isPresent(), "associated option not found");
                Option associatedOption = _associatedOption.get();

                assertEquals(course_1, course_options.getCourse());

                assertFalse(associatedOption.isBlocked());

                Set<Room> expected_claims_for_rooms = new HashSet<>();
                Set<Set<Room>> expected_combination = new HashSet<>();
                for (int[] roomCombination : expected_available_rooms) {
                    assertEquals(
                            amountRooms,
                            roomCombination.length,
                            "unexpected room amount in options");

                    Set<Room> roomComb = new HashSet<>();
                    int roomIndex = 0;
                    for (int isInCombination : roomCombination) {
                        if (isInCombination != 0) {
                            roomComb.add(rooms.get(roomIndex));
                        }
                        roomIndex++;
                    }

                    expected_combination.add(roomComb);
                    expected_claims_for_rooms.addAll(roomComb);
                }
                assertEquals(
                        expected_available_rooms.length,
                        expected_combination.size(),
                        "unexpected amount of room combination found ");

                Set<Set<Room>> actualCombination = associatedOption.getRoomCombinations();
                assertEquals(
                        expected_available_rooms.length,
                        actualCombination.size(),
                        "unexpected amount of rooms combination in course ");

                for (Set<Room> roomComb : actualCombination) {
                    expected_combination.remove(roomComb);
                }

                assertEquals(
                        0,
                        expected_combination.size(),
                        String.format(
                                "unexpected room combination %s || %s",
                                expected_combination, actualCombination));

                for (Room r : expected_claims_for_rooms) {
                    Optional<RoomClaims> _room_claim = underTest.getClaimsForRoom(r);
                    assertTrue(_room_claim.isPresent(), "room claim present");

                    RoomClaims room_claim = _room_claim.get();
                    assertNotNull(room_claim);

                    assertEquals(r, room_claim.getRoom());

                    NavigableSet<RoomClaim> room_claims_for_day =
                            room_claim.getScheduleForDay(1, day);

                    Optional<RoomClaim> _associated_room_claim =
                            room_claims_for_day.stream()
                                    .filter(
                                            segments ->
                                                    segments.getCourse()
                                                                    .getId()
                                                                    .equals(course_1.getId())
                                                            && expected_timeslots.stream()
                                                                    .map(Interval::new)
                                                                    .allMatch(
                                                                            segments
                                                                                    ::isIntersecting))
                                    .findFirst();

                    assertTrue(_associated_room_claim.isPresent(), "associated room claim present");
                    RoomClaim associated_room_claim = _associated_room_claim.get();
                    //                    assertFalse(associated_room_claim.isActiveClaim());
                    assertFalse(associated_room_claim.isAlreadyClaimedByOthers());
                }
            }
        }
        assertEquals(
                expected_degrees_of_freedom,
                course_options.getDegreesOfFreedomWithRooms(),
                "mismatch between expected degrees of freedom");
    }

    @Getter
    public static class Generator {
        private final List<Timeslot> timeslots;
        private final List<Room> rooms = new ArrayList<>();
        private List<Course> courses = new ArrayList<>();
        private final List<Degree> degrees = new ArrayList<>();
        private final List<DegreeSemester> degreeSemesters = new ArrayList<>();
        private final List<Employee> employees = new ArrayList<>();
        private final List<WorkTime> workTimes = new ArrayList<>();
        private final List<DayOfWeek> days = new ArrayList<>();

        public Generator(
                List<DayOfWeek> days,
                int amountOfTimeSlots,
                int amount,
                int[] blocksizes,
                int[] slotsPerWeek,
                int[][] lecture_is_before,
                int[][] lecture_is_parallel,
                int[][]... schedules) {
            this.timeslots = generateTimeslots(amountOfTimeSlots);
            this.days.clear();
            this.days.addAll(days);

            assert amount <= schedules.length;
            assert amount <= blocksizes.length;
            assert amount <= slotsPerWeek.length;

            String prefixName = "Course";
            String prefixAbbr = "C";
            String prefixCas = "CAS";

            List<Course> newCourses = new ArrayList<>();
            for (int index = 0; index < amount; index++) {
                assert amount <= lecture_is_before[index].length;
                assert amount <= lecture_is_parallel[index].length;

                Course c = new Course();
                c.setId(UUID.randomUUID());
                c.setName(prefixName.concat(String.valueOf(index + 1)));
                c.setAbbreviation(prefixAbbr.concat(String.valueOf(index + 1)));
                c.setCasID(prefixCas.concat(String.valueOf(index + 1)));
                c.setCourseType(null);
                c.setDescription("Test Course".concat(String.valueOf(index + 1)));

                c.setBlockSize(blocksizes[index]);
                c.setSlotsPerWeek(slotsPerWeek[index]);

                List<CourseTimeslot> coursTimeSlots = new LinkedList<>();

                int[][] courseSchedule = schedules[index];
                assert courseSchedule.length <= this.days.size();
                for (int days_idx = 0; days_idx < courseSchedule.length; days_idx++) {
                    int[] courseDaysSchedule = courseSchedule[days_idx];
                    assert timeslots.size() >= courseDaysSchedule.length;
                    DayOfWeek d = this.days.get(days_idx);

                    for (int timeslotidx = 0;
                            timeslotidx < courseDaysSchedule.length;
                            timeslotidx++) {
                        if (courseDaysSchedule[timeslotidx] > 0) {
                            CourseTimeslot cts = new CourseTimeslot();
                            cts.setCourse(c);
                            cts.setId(UUID.randomUUID());
                            cts.setWeekday(d);
                            cts.setTimeslot(this.timeslots.get(timeslotidx));
                            coursTimeSlots.add(cts);
                        }
                    }
                }

                c.setCourseTimeslots(coursTimeSlots);
                c.setWeeksPerSemester(12);
                newCourses.add(c);
            }

            this.courses = newCourses;

            // Build Course Relations
            for (int index = 0; index < amount; index++) {
                assert amount <= lecture_is_before[index].length;
                assert amount <= lecture_is_parallel[index].length;

                int[] concurrentCourse = lecture_is_parallel[index];

                Course c = courses.get(index);
                List<CourseRelation> courseRelationsA = new LinkedList<>();

                for (int courseIdx = 0; courseIdx < concurrentCourse.length; courseIdx++) {
                    if (courseIdx != index && concurrentCourse[courseIdx] > 0) {
                        Course b = courses.get(courseIdx);
                        CourseRelation maybeParallel = new CourseRelation();
                        maybeParallel.setRelationType(RelationType.MAY_BE_PARALLEL);
                        maybeParallel.setId(UUID.randomUUID());
                        maybeParallel.setCourseA(c);
                        maybeParallel.setCourseB(b);

                        courseRelationsA.add(maybeParallel);

                        CourseRelation mustParallel = new CourseRelation();
                        mustParallel.setRelationType(RelationType.MUST_BE_PARALLEL);
                        mustParallel.setId(UUID.randomUUID());
                        mustParallel.setCourseA(c);
                        mustParallel.setCourseB(b);

                        courseRelationsA.add(mustParallel);
                    }
                }

                int[] beforeCourse = lecture_is_before[index];
                for (int courseIdx = 0; courseIdx < beforeCourse.length; courseIdx++) {
                    if (courseIdx != index && beforeCourse[courseIdx] > 0) {
                        Course b = courses.get(courseIdx);
                        CourseRelation courseIsBefore = new CourseRelation();
                        courseIsBefore.setRelationType(RelationType.A_MUST_BE_BEFORE_B);
                        courseIsBefore.setId(UUID.randomUUID());
                        courseIsBefore.setCourseA(c);
                        courseIsBefore.setCourseB(b);

                        courseRelationsA.add(courseIsBefore);
                    }
                }

                c.setCourseRelationsA(courseRelationsA);
                courses.set(index, c);
            }
        }

        public List<Employee> generateEmployees(int amount, int[][] hosts, int[][]... schedules) {
            assert amount <= hosts.length;
            assert amount <= schedules.length;

            List<Employee> newEmployees = new ArrayList<>();

            for (int index = 0; index < amount; index++) {
                int[] isHosting = hosts[index];
                assert this.courses.size() <= isHosting.length;
                int[][] schedule = schedules[index];
                assert this.days.size() <= schedule.length;

                Employee newEmployee = new Employee();
                newEmployee.setId(UUID.randomUUID());
                newEmployee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
                newEmployee.setLastname(
                        String.format("Nachname %d", index + this.employees.size() + 1));
                newEmployee.setFirstname(
                        String.format("Vorname %d", index + this.employees.size() + 1));
                newEmployee.setAbbreviation(
                        String.format("emp.%d", index + this.employees.size() + 1));

                List<Course> courses = new ArrayList<>();
                for (int ii = 0; ii < isHosting.length; ii++) {
                    if (isHosting[ii] != 0) {
                        Course c = this.courses.get(ii);
                        c.getLecturers().add(newEmployee);
                        courses.add(c);
                    }
                }

                newEmployee.setCourses(courses);
                List<WorkTime> wts = new ArrayList<>();
                for (int ii = 0; ii < schedule.length; ii++) {
                    int[] scheduleOnDay = schedule[ii];
                    assert this.timeslots.size() <= scheduleOnDay.length;

                    DayOfWeek dw = this.days.get(ii);
                    for (int iii = 0; iii < scheduleOnDay.length; iii++) {
                        if (scheduleOnDay[iii] != 0) {
                            WorkTime wt = new WorkTime();
                            wt.setEmployee(newEmployee);
                            wt.setWeekday(dw);
                            wt.setTimeslot(this.timeslots.get(iii));
                            wt.setId(UUID.randomUUID());
                            wts.add(wt);
                        }
                    }
                }
                newEmployee.setWorkTimes(wts);
                workTimes.addAll(wts);
                newEmployees.add(newEmployee);
            }

            this.employees.addAll(newEmployees);
            return newEmployees;
        }

        public List<Pair<Degree, List<DegreeSemester>>> generateDegrees(
                int amount, int[][]... courseMatrices) {
            List<Pair<Degree, List<DegreeSemester>>> lists = new ArrayList<>();

            assert amount <= courseMatrices.length;

            for (int index = 0; index < amount; index++) {
                int[][] degreeSemsCourses = courseMatrices[index];

                Degree newDegree = new Degree();
                newDegree.setName(String.format("degree: %d", index + this.degrees.size() + 1));
                newDegree.setId(UUID.randomUUID());
                newDegree.setSemesterAmount(degreeSemsCourses.length);

                List<DegreeSemester> degreeSems = new ArrayList<>();

                for (int i = 0; i < degreeSemsCourses.length; i++) {
                    DegreeSemester newDegreeSemester = new DegreeSemester();
                    newDegreeSemester.setId(UUID.randomUUID());
                    newDegreeSemester.setDegree(newDegree);
                    newDegreeSemester.setSemesterNumber(i);
                    newDegreeSemester.setExtensionName(
                            String.format(
                                    "degreesemester: %d %d", index + this.degrees.size() + 1, i));

                    int[] isVisitingCourse = degreeSemsCourses[i];
                    List<Course> courses = new ArrayList<>();

                    for (int ii = 0; ii < isVisitingCourse.length; ii++) {
                        if (isVisitingCourse[ii] != 0) {
                            Course c = this.courses.get(ii);
                            c.getSemesters().add(newDegreeSemester);
                            courses.add(c);
                        }
                    }
                    newDegreeSemester.setCourses(courses);
                    degreeSems.add(newDegreeSemester);
                }
                newDegree.setSemesters(degreeSems);

                this.degreeSemesters.addAll(degreeSems);
                this.degrees.add(newDegree);

                Pair<Degree, List<DegreeSemester>> p = Pair.of(newDegree, degreeSems);
                lists.add(p);
            }

            return lists;
        }

        public List<Room> generateRooms(int amount, RoomType roomType, int size) {
            List<Room> rooms = new ArrayList<>();
            for (int index = 0; index < amount; index++) {

                Room r = new Room();
                r.setId(UUID.randomUUID());
                r.setCapacity(size);
                r.setRoomType(roomType);
                r.setName(String.format("Hörsaal %d", index + this.rooms.size() + 1));
                r.setAbbreviation(String.format("HS %d", index + this.rooms.size() + 1));
                r.setIdentifier(String.format("CA.HS %d", index + this.rooms.size() + 1));
                rooms.add(r);
            }
            this.rooms.addAll(rooms);
            return rooms;
        }
    }
}
