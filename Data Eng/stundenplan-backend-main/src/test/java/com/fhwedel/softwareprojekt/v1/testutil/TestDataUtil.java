package com.fhwedel.softwareprojekt.v1.testutil;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.scheduler.Option;
import com.fhwedel.softwareprojekt.v1.util.RelationType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

/**
 * Utility class for the generation of test data, especially the creation of entities
 */
public class TestDataUtil {

    public static Course createCourseOne(Timetable timetable) {
        Course course = new Course();
        course.setCasID("WS22A001");
        course.setName("Course One");
        course.setAbbreviation("CO");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        course.setSemesters(new ArrayList<>());
        course.setTimetable(timetable);

        return course;
    }

    public static Course createCourseTwo(Timetable timetable) {
        Course course = new Course();
        course.setCasID("WS22B001");
        course.setName("Course Two");
        course.setAbbreviation("CT");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        course.setSemesters(new ArrayList<>());
        course.setTimetable(timetable);

        return course;
    }

    public static Course createCourse(Timetable timetable, int identifier) {
        Course course = new Course();
        course.setCasID("WS22Q00" + identifier);
        course.setName("Course " + identifier);
        course.setAbbreviation("CT" + identifier);
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        course.setSemesters(new ArrayList<>());
        course.setTimetable(timetable);

        return course;
    }

    public static CourseTimeslot createCourseTimeslot(
            Course course, DayOfWeek weekday, Timeslot timeslot) {
        CourseTimeslot courseTimeslot = new CourseTimeslot();
        courseTimeslot.setCourse(course);
        courseTimeslot.setWeekday(weekday);
        courseTimeslot.setTimeslot(timeslot);

        course.getCourseTimeslots()
                .add(courseTimeslot);

        return courseTimeslot;
    }

    public static List<CourseTimeslot> generateCourseTimeslots(
            Course course, List<DayOfWeek> weekdays, List<Timeslot> timeslots) {
        List<CourseTimeslot> result = new ArrayList<>();
        // generates for all given weekdays and timeslots a CourseTimeslot
        for (DayOfWeek weekday : weekdays) {
            for (Timeslot timeslot : timeslots) {
                result.add(createCourseTimeslot(course, weekday, timeslot));
            }
        }
        return result;
    }

    public static Timeslot createTimeslotOne(Timetable timetable) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.of(8, 0));
        timeslot.setEndTime(LocalTime.of(9, 15));
        timeslot.setIndex(0);
        timeslot.setTimetable(timetable);

        return timeslot;
    }

    public static Timeslot createTimeslotTwo(Timetable timetable) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.of(9, 30));
        timeslot.setEndTime(LocalTime.of(10, 45));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        return timeslot;
    }

    public static List<Timeslot> generateTimeslots(Timetable timetable, int amountOfLessons) {
        final LocalTime lessonDuration = LocalTime.of(1, 15);
        final LocalTime breakDuration = LocalTime.of(0, 15);
        LocalTime curr = LocalTime.of(8, 0);

        Timeslot[] tss = new Timeslot[amountOfLessons];
        for (int i = 0; i < amountOfLessons; i++) {
            Timeslot ts = new Timeslot();
            ts.setStartTime(curr);
            curr = curr.plusHours(lessonDuration.getHour())
                    .plusMinutes(lessonDuration.getMinute());
            ts.setEndTime(curr);
            curr = curr.plusHours(breakDuration.getHour())
                    .plusMinutes(breakDuration.getMinute());
            tss[i] = ts;

            ts.setIndex(i);
            ts.setTimetable(timetable);
        }

        return new ArrayList<>(Arrays.stream(tss)
                .toList());
    }

    public static WeekEvent createEventOnlyWithTimetable(Timetable timetable) {
        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setTimetable(timetable);

        return weekEvent;
    }

    public static WeekEvent createWeekEvent(
            Timetable timetable,
            Integer week,
            DayOfWeek weekday,
            Course c,
            List<Room> rooms,
            List<Timeslot> timeslots) {
        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setTimetable(timetable);
        weekEvent.setCourse(c);
        weekEvent.setWeekday(weekday);
        weekEvent.setWeek(week);
        weekEvent.setTimeslots(timeslots);
        weekEvent.setRooms(rooms);

        return weekEvent;
    }

    public static Room createRoomOne(Timetable timetable, RoomType roomType) {
        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("A0.01");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        return room;
    }

    public static Room createRoomOne(Timetable timetable) {
        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("A0.01");
        room.setCapacity(50);
        room.setRoomType(createRoomTypeTwo());
        room.setTimetable(timetable);

        return room;
    }

    public static RoomType createRoomTypeOne() {
        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("Pc Pool");
        return roomType;
    }

    public static RoomType createRoomTypeTwo() {
        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("Lecture Hall");
        return roomType;
    }

    public static RoomCombination createRoomComboFor(Course course, List<Room> rooms) {
        RoomCombination roomCombo = new RoomCombination();
        roomCombo.setRooms(new ArrayList<>(rooms));
        roomCombo.setCourse(course);

        course.getSuitedRooms()
                .add(roomCombo);
        return roomCombo;
    }

    public static Timetable createTimetableWS22() {
        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2022, 10, 18));
        timetable.setEndDate(LocalDate.of(2023, 1, 20));
        timetable.setName("Test timetable");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(createSemesterTypeWS());
        return timetable;
    }

    public static Timetable createTimetableSS23() {
        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2023, 4, 18));
        timetable.setEndDate(LocalDate.of(2023, 10, 20));
        timetable.setName("Test timetable 2023");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(createSemesterTypeSS());
        return timetable;
    }

    public static SemesterType createSemesterTypeWS() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        return semesterType;
    }

    public static SemesterType createSemesterTypeSS() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("SS");
        return semesterType;
    }

    public static Degree createDegreeOne(Timetable timetable) {
        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("Degree one");
        degree.setShortName("Degree one");
        degree.setStudyRegulation("1.0");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);
        degree.setSemesters(new ArrayList<>());

        return degree;
    }

    public static DegreeSemester createDegreeSemesterFor(Degree degree) {
        DegreeSemester semester = new DegreeSemester();
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setTimetable(degree.getTimetable());
        semester.setDegree(degree);

        degree.getSemesters()
                .add(semester);

        return semester;
    }

    public static DegreeSemester createDegreeSemesterTwoFor(Degree degree) {
        DegreeSemester semester = new DegreeSemester();
        semester.setSemesterNumber(2);
        semester.setAttendees(2);
        semester.setExtensionName("test2");
        semester.setTimetable(degree.getTimetable());
        semester.setDegree(degree);

        degree.getSemesters()
                .add(semester);

        return semester;
    }

    public static DegreeSemester createDegreeSemester(Degree degree, int semesterNumber) {
        DegreeSemester semester = new DegreeSemester();
        semester.setSemesterNumber(semesterNumber);
        semester.setAttendees(10);
        semester.setExtensionName("Vertiefung");
        semester.setTimetable(degree.getTimetable());
        semester.setDegree(degree);

        degree.getSemesters()
                .add(semester);

        return semester;
    }

    public static Employee createEmployeeOne(Timetable timetable) {
        Employee employee = new Employee();
        employee.setFirstname("Employee");
        employee.setLastname("One");
        employee.setEmployeeType(createEmployeeTypeDozent());
        employee.setAbbreviation("E1");
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        return employee;
    }

    public static Employee createEmployee(Timetable timetable, int number) {
        Employee employee = new Employee();
        employee.setFirstname("Employee");
        employee.setLastname(String.valueOf(number));
        employee.setEmployeeType(createEmployeeTypeAssistent());
        employee.setAbbreviation("E" + number);
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        return employee;
    }

    public static Employee createEmployee(Timetable timetable, int number, EmployeeType type) {
        Employee employee = new Employee();
        employee.setFirstname("Employee");
        employee.setLastname(String.valueOf(number));
        employee.setEmployeeType(type);
        employee.setAbbreviation("E" + number);
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        return employee;
    }

    public static Employee createEmployeeTwo(Timetable timetable, int number) {
        Employee employee = new Employee();
        employee.setFirstname("Employee");
        employee.setLastname(String.valueOf(number));
        employee.setEmployeeType(createEmployeeTypeDozent());
        employee.setAbbreviation("E" + number);
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        return employee;
    }

    public static EmployeeType createEmployeeTypeDozent() {
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Dozent");
        return employeeType;
    }

    public static EmployeeType createEmployeeTypeAssistent() {
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Assistent");
        return employeeType;
    }

    public static void addLecturerToCourse(Course course, Employee employee) {
        course.getLecturers()
                .add(employee);
        employee.getCourses()
                .add(course);
    }

    public static void addDegreeSemesterToCourse(DegreeSemester semester, Course course) {
        course.getSemesters()
                .add(semester);
        semester.getCourses()
                .add(course);
    }

    public static void associateDegreeSemesterWithCourses(
            DegreeSemester semester, List<Course> courses) {
        for (Course course : courses) {
            course.getSemesters()
                    .add(semester);
            semester.getCourses()
                    .add(course);
        }
    }

    public static WorkTime createWorkTimeFor(
            Employee employee, DayOfWeek weekday, Timeslot timeslot) {
        WorkTime workTime = new WorkTime();
        workTime.setEmployee(employee);
        workTime.setWeekday(weekday);
        workTime.setTimeslot(timeslot);

        employee.getWorkTimes()
                .add(workTime);

        return workTime;
    }

    /**
     * Creates working times for an employee using the given map. The map specifies the timeslots
     * for a day on which the employee should work.
     *
     * @param employee               Employee for whom working times are to be created (and associated with them)
     * @param workTimeslotsByWeekday contains for each day the timeslots on which the employee
     *                               should work.
     * @return List of working times created for the employee
     */
    public static List<WorkTime> createWorkTimesFor(
            Employee employee, Map<DayOfWeek, List<Timeslot>> workTimeslotsByWeekday) {
        List<WorkTime> result = new ArrayList<>();

        for (Map.Entry<DayOfWeek, List<Timeslot>> entry : workTimeslotsByWeekday.entrySet()) {
            DayOfWeek weekday = entry.getKey();
            List<Timeslot> timeslots = entry.getValue();
            for (Timeslot ts : timeslots) {
                WorkTime workTime = new WorkTime();
                workTime.setEmployee(employee);
                workTime.setWeekday(weekday);
                workTime.setTimeslot(ts);
                employee.getWorkTimes()
                        .add(workTime);

                result.add(workTime);
            }
        }

        return result;
    }

    public static Room createRoomTwo(Timetable timetable, RoomType roomType) {
        Room room = new Room();
        room.setName("Hörsaal 2");
        room.setAbbreviation("HS02");
        room.setIdentifier("B0.02");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        return room;
    }

    public static Room createRoomThree(Timetable timetable, RoomType roomType) {
        Room room = new Room();
        room.setName("Hörsaal 3");
        room.setAbbreviation("HS03");
        room.setIdentifier("C0.03");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        return room;
    }

    public static Room createRoomFour(Timetable timetable, RoomType roomType) {
        Room room = new Room();
        room.setName("Hörsaal 4");
        room.setAbbreviation("HS04");
        room.setIdentifier("D0.04");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        return room;
    }

    /**
     * Groups the given List of {@link OptionDTO} by their weekdays.
     *
     * @param optionDTOs set of optionDTOs
     * @return a Map mapping a day of week to a List of optionDTOs
     */
    public static Map<DayOfWeek, List<OptionDTO>> groupOptionsByDayOfWeek(
            List<OptionDTO> optionDTOs) {
        // accumulates the set of optionDTOs for each day of week
        Map<DayOfWeek, List<OptionDTO>> opts =
                optionDTOs.stream()
                        .collect(
                                groupingBy(
                                        OptionDTO::getWeekday,
                                        mapping(
                                                Function.identity(),
                                                toCollection(LinkedList::new))));

        opts.replaceAll((d, v) -> v.stream()
                .sorted(Option.optionDTOComparator())
                .toList());
        return opts;
    }

    public static CourseRelation createCourseRelation(RelationType type, Course a, Course b) {
        CourseRelation relation = new CourseRelation();
        relation.setRelationType(type);
        relation.setCourseA(a);
        relation.setCourseB(b);

        return relation;
    }

    /**
     * Generates work times for an employee using the given list of weekdays and timeslots.
     *
     * @param employee  Employee for whom working times are to be created (and associated with them)
     * @param weekdays  contains the days on which the employee should work
     * @param timeslots contains the time slots on which the employee should work each day
     * @return List of working times generated for the employee
     */
    public static List<WorkTime> generateWorkTimes(
            Employee employee, List<DayOfWeek> weekdays, List<Timeslot> timeslots) {
        List<WorkTime> result = new ArrayList<>();
        // generates for all given weekdays and timeslots a CourseTimeslot
        for (DayOfWeek weekday : weekdays) {
            for (Timeslot timeslot : timeslots) {
                result.add(createWorkTimeFor(employee, weekday, timeslot));
            }
        }
        return result;
    }
}
