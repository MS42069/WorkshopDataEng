package com.fhwedel.softwareprojekt.v1.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeTimeslotConstraintRepository;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.CheckedEvent;
import java.time.DayOfWeek;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

/*
How to Read the Tests:
    0 = is not associated to
    1 = is associated to

        Aud Foop BWL
 uhl      1    1   0
 harms    0    0   1
 heins    1    0   0


Working times für Uhlig
uhl       0 1 2 3 4 5
-------+-------------
Montag    1 1 0 0 0 0
Dienstag  0 0 1 0 1 1



 informatik
    Fachsemester\Veranstaltung      Aud | Foop  | BWL
     Fachsemester 1                 1   | 0     |   0
     Fachsemester 2                 0   | 1     |   0
     Fachsemester 3                 0   | 0     |   1


Veranstaltung Aud Options (Blockgröße 1)  -> 3 Mögliche Termine
    Montag \ TimeSlots    1| 2 | 3
    Option 1              1| 0 | 0
    Option 2              0| 1 | 0
    Option 2              0| 0 | 1

Veranstaltung FooP Options (Blockgröße 2)  -> 2 Mögliche Termine
    Montag \ TimeSlots    1| 2 | 3
    Option 1              1| 1 | 0
    Option 2              0| 1 | 1


Veranstaltung AuD am Montag 8:00 (=Option 1)
    Raum Kombinationen                   Raum 1 | Raum 2 | Raum 3
    Kombination 1                            1 |      0 |     0
    Kombination 2                            0 |      1 |     1

Veranstaltung AuD am Montag 9:30     (=Option 2)
    RaumKombinationen                   Raum 1 | Raum 2 | Raum 3
    Kombination 1                            1 |      1 |     1

Veranstaltung AuD am Montag 11:00 (=Option 3)
    RaumKombinationen                   Raum 1 | Raum 2 | Raum 3
    Kombination 1                            1 |      0 |     0


RoomClaim
    Course AUD am Montag 8:00-11:00

 */

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {

    static final int MON = 0;
    static final int TUE = 1;
    static final int WED = 2;
    static final int THU = 3;
    static final int FRI = 4;
    static final int SAT = 5;
    static final int SUN = 6;

    final int EMPLOY_1 = 0;
    final int EMPLOY_2 = 1;
    final int EMPLOY_3 = 2;
    final int EMPLOY_4 = 3;
    final int EMPLOY_5 = 4;
    final int EMPLOY_6 = 5;
    final int EMPLOY_7 = 6;

    @Mock private CourseRepository courseRepository;
    @Mock private RoomRepository roomRepository;
    @Mock private TimeslotRepository timeslotRepository;
    @Mock private WeekEventRepository weekEventRepository;
    @Mock private EmployeeRepository employeeRepository;
    @Mock private DegreeSemesterRepository degreeSemesterRepository;
    @Mock private CourseRelationRepository courseRelationRepository;
    @Mock private TimetableRepository timetableRepository;
    @Mock private EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository;

    @InjectMocks private Scheduler underTest;

    private final UUID timetableID = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        Timetable timetable = new Timetable();
        timetable.setId(timetableID);
        timetable.setNumberOfWeeks(12);
        when(timetableRepository.findById(any(UUID.class))).thenReturn(Optional.of(timetable));
        underTest =
                new Scheduler(
                        timetableID,
                        courseRepository,
                        roomRepository,
                        timeslotRepository,
                        weekEventRepository,
                        employeeRepository,
                        degreeSemesterRepository,
                        courseRelationRepository,
                        timetableRepository,
                        employeeTimeslotConstraintRepository);
    }

    @Test
    public void Test_3Courses_3Employees_3Days_3Timeslots_1Room_1Degree_3Semesters_Relations() {
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 1;
        final int amountCourses = 3;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 0, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        ///////////////////////////////////////////////////////////////////////

        /*
            Begin Test
        */

        assertTrue(underTest.initialize());
        // Test employee schedule for correct worktime blockers
        int[][][] employeeWorkTimes =
                new int[][][] {work_time_employee_1, work_time_employee_2, work_time_employee_3};

        for (int i = 0; i < amountEmployees; i++) {
            SchedulerTestUtil.testRelationForLecturer(
                    underTest,
                    employees.get(i),
                    1,
                    days,
                    timeslots,
                    courses,
                    employeeHoldsLecture[i],
                    employeeWorkTimes[i]);
        }

        {

            // Expected Options for course 1

            Course course_1 = courses.get(0);
            assertEquals(3 * 12, underTest.getDegreesOfFreedom(course_1));

            int[][][] expected_course_options = new int[days.size()][][];
            // Expected For Monday= Two Options [08:00-9:15] and [09:30-10:45]
            expected_course_options[MON] =
                    new int[][] { //
                        new int[] {1, 0, 0}, // Monday: Option A
                        new int[] {0, 1, 0} // Monday: Option B
                    };
            // Expected For Tuesday One Options [08:00-9:15]
            expected_course_options[TUE] =
                    new int[][] { //
                        new int[] {1, 0, 0}, // Tuesday: Option A
                    };

            expected_course_options[WED] = new int[][] {};

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            // Expected For Monday  Option A and B both claim room_1
            expected_course_options_available_rooms[MON] =
                    new int[][][] { //
                        new int[][] {
                            // Monday:Option A -> claims use of room_1 in a single combination
                            new int[] {1} //
                        }, //
                        new int[][] { // Monday:Option B -> claims use of room_1 in a single
                            // combination
                            new int[] {1} //
                        } //
                    };

            // Expected For Tuesday  Option A claims room_1
            expected_course_options_available_rooms[TUE] =
                    new int[][][] { //
                        new int[][] {
                            // Tuesday:Option A -> claims use of room_1 in a single combination
                            new int[] {1} //
                        } //
                    };

            // Expected no claims for Wednesday, because no option specified
            expected_course_options_available_rooms[WED] = new int[][][] {};

            int expected_course_freedom_degree = 3 * 12;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_1, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }

        {
            // Expected Options for course 2:
            // no options available
            // Due to there being no intersections between the working times of lecturing employees

            Course course_2 = courses.get(1);
            int[][][] expected_course_options = new int[days.size()][][];
            expected_course_options[MON] = new int[][] {};
            expected_course_options[TUE] = new int[][] {};
            expected_course_options[WED] = new int[][] {};

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            expected_course_options_available_rooms[MON] = new int[0][][];
            expected_course_options_available_rooms[TUE] = new int[0][][];
            expected_course_options_available_rooms[WED] = new int[0][][];

            int expected_course_freedom_degree = 0;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_2, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }

        {
            /*
            Expected Options for course 3:
               - TuesDay: 2nd timeslot
               - WednesDay: 3rd timeslot

            */

            Course course_3 = courses.get(2);
            int[][][] expected_course_options = new int[days.size()][][];
            expected_course_options[MON] = new int[][] {};
            expected_course_options[TUE] =
                    new int[][] { //
                        new int[] {0, 1, 0}, //
                    };
            expected_course_options[WED] =
                    new int[][] { //
                        new int[] {0, 0, 1}, //
                    };

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            expected_course_options_available_rooms[MON] = new int[0][][];
            expected_course_options_available_rooms[TUE] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1}, //
                        }, //
                    };

            expected_course_options_available_rooms[WED] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1}, //
                        }, //
                    };
            int expected_course_freedom_degree = 2 * 12;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_3, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }
    }

    @Test
    public void Test_3Courses_3Employees_3Days_3Timeslots_3Room_1Degree_3Semesters_Relations() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 3;

        final int EMPLOY_1 = 0;
        final int EMPLOY_2 = 1;
        final int EMPLOY_3 = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        ///////////////////////////////////////////////////////////////////////

        /*
            Begin Test
        */

        assertTrue(underTest.initialize());
        // Test employee schedule for correct worktime blockers
        int[][][] employeeWorkTimes =
                new int[][][] {work_time_employee_1, work_time_employee_2, work_time_employee_3};

        for (int i = 0; i < amountEmployees; i++) {
            SchedulerTestUtil.testRelationForLecturer(
                    underTest,
                    employees.get(i),
                    1,
                    days,
                    timeslots,
                    courses,
                    employeeHoldsLecture[i],
                    employeeWorkTimes[i]);
        }

        {

            // Expected Options for course 1

            Course course_1 = courses.get(0);
            int[][][] expected_course_options = new int[days.size()][][];
            expected_course_options[MON] =
                    new int[][] { //
                        new int[] {1, 0, 0}, //
                        new int[] {0, 1, 0} //
                    };
            expected_course_options[TUE] =
                    new int[][] { //
                        new int[] {1, 0, 0}, //
                        new int[] {0, 1, 0}, //
                    };

            expected_course_options[WED] = new int[][] {};

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            // Expected For Monday  Option A and B both claim room_1
            expected_course_options_available_rooms[MON] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                    };

            // Expected For Tuesday  Option A claims room_1
            expected_course_options_available_rooms[TUE] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                    };

            // Expected no claims for Wednesday, because no option specified
            expected_course_options_available_rooms[WED] = new int[][][] {};

            int expected_course_freedom_degree = 12 * 12;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_1, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }

        {
            // Expected:
            // no options available
            // Due to there being no intersections between the working times of lecturing employees

            Course course_2 = courses.get(1);
            int[][][] expected_course_options = new int[days.size()][][];
            expected_course_options[MON] = new int[][] {};
            expected_course_options[TUE] =
                    new int[][] { //
                        new int[] {0, 1, 0}, //
                    };
            expected_course_options[WED] =
                    new int[][] { //
                    };

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            expected_course_options_available_rooms[MON] = new int[0][][];
            expected_course_options_available_rooms[TUE] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1}, //
                        }, //
                    };

            expected_course_options_available_rooms[WED] = new int[0][][];
            int expected_course_freedom_degree = 3 * 12;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_2, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }

        {
            /*
            Expected:
               - TuesDay: 2nd timeslot
               - WednesDay: 3rd timeslot

            */

            Course course_3 = courses.get(2);
            int[][][] expected_course_options = new int[days.size()][][];
            expected_course_options[MON] = new int[][] {};
            expected_course_options[TUE] =
                    new int[][] { //
                        new int[] {0, 1, 0}, //
                    };
            expected_course_options[WED] =
                    new int[][] { //
                        new int[] {0, 0, 1}, //
                    };

            int[][][][] expected_course_options_available_rooms = new int[days.size()][][][];
            expected_course_options_available_rooms[MON] = new int[0][][];
            expected_course_options_available_rooms[TUE] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                    };

            expected_course_options_available_rooms[WED] =
                    new int[][][] { //
                        new int[][] { //
                            new int[] {1, 0, 0}, //
                            new int[] {0, 1, 0}, //
                            new int[] {0, 0, 1} //
                        }, //
                    };
            int expected_course_freedom_degree = 6 * 12;

            SchedulerTestUtil.testRelationsForCourse( //
                    underTest, //
                    course_3, //
                    amountRooms, //
                    amountTimeslots, //
                    days, //
                    timeslots, //
                    rooms, //
                    expected_course_freedom_degree, //
                    expected_course_options, //
                    expected_course_options_available_rooms);
        }
    }

    @Test
    public void
            Test_1_3Courses_3Employees_3Days_3Timeslots_3Room_1Degree_3Semesters_ScheduleEvent_Cascades() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 3;

        final int EMPLOY_1 = 0;
        final int EMPLOY_2 = 1;
        final int EMPLOY_3 = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        ///////////////////////////////////////////////////////////////////////

        /*
            Begin Test
        */

        assertTrue(underTest.initialize());

        {
            /*
                 Schedule Course 1 for Monday timeslot 1 using Room
                 Course Employee Matrix -> 1 0 0

                 Options has event planned

                 Degree 1:
                 Semester 1.1
                 - new entry for Monday: 08:30

                 Room r1
                 - new entry for Monday: 08:30

                RoomClaims
                - claims for Monday 8:30 isActive

            */

            Course c1 = courses.get(0);
            List<Room> usedRooms = List.of(rooms.get(0));
            List<Timeslot> usedTimeslots = List.of(timeslots.get(0));
            DayOfWeek usedDay = DayOfWeek.MONDAY;

            assertNotNull(c1);

            assertEquals(48, underTest.getDegreesOfFreedom(c1));

            WeekEvent expected_event = new WeekEvent();
            expected_event.setId(UUID.randomUUID());
            expected_event.setCourse(c1);
            expected_event.setRooms(usedRooms);
            expected_event.setWeekday(usedDay);
            expected_event.setWeek(1);
            expected_event.setTimeslots(usedTimeslots);
            when(weekEventRepository.getWeekEventGraphTimeslotById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphRoomsById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphCourseById(expected_event.getId()))
                    .thenReturn(expected_event);

            assertTrue(underTest.scheduleEvent(expected_event, true));

            assertTrue(underTest.getOptionsForCourse(c1).isPresent());
            Options c1_options = underTest.getOptionsForCourse(c1).get();

            Set<WeekEvent> scheduled_event = c1_options.getAlreadyScheduled();
            assertEquals(1, scheduled_event.size());
            WeekEvent actual_event = scheduled_event.iterator().next();

            assertEquals(expected_event, actual_event);

            Set<Option> effected_exact_options =
                    c1_options.findMatchingAllTimeslots(1, usedDay, usedTimeslots);
            assertEquals(1, effected_exact_options.size());
            Option associated_option = effected_exact_options.iterator().next();

            Set<Option> effected_options =
                    c1_options.findMatchingAnyTimeslots(1, usedDay, usedTimeslots);
            assertEquals(
                    1,
                    effected_exact_options.size(),
                    "should only be one, check equality of options");

            for (DayOfWeek day : days) {
                NavigableSet<Option> option_on_weekday = c1_options.getScheduleForDay(1, day);
                for (Option o : option_on_weekday) {
                    if (effected_options.contains(o)) {
                        assertTrue(
                                o.isBlocked(),
                                String.format(
                                        "should be blocked %s %s %s %s",
                                        usedDay, day, o, o == associated_option));
                        assertTrue(
                                o.getIntersectingEvents().contains(expected_event),
                                String.format("%s doesnt contain event %s", o, expected_event));
                    } else {
                        assertFalse(o.isBlocked());
                        assertTrue(o.getIntersectingEvents().isEmpty());
                    }
                }
            }
            assertFalse(underTest.scheduleEvent(expected_event, true));
            assertEquals(47, underTest.getOptionsForCourse(c1).get().getDegreesOfFreedom());
        }
    }

    @Test
    public void
            Test_2_3Courses_3Employees_3Days_3Timeslots_3Room_1Degree_3Semesters_ScheduleEvent_Cascades() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 3;

        final int EMPLOY_1 = 0;
        final int EMPLOY_2 = 1;
        final int EMPLOY_3 = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3
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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        ///////////////////////////////////////////////////////////////////////

        assertTrue(underTest.initialize());
        {
            Course c1 = courses.get(0);
            Course c2 = courses.get(1);
            Course c3 = courses.get(2);

            List<Room> usedRooms = List.of(rooms.get(0));
            List<Timeslot> usedTimeslots = List.of(timeslots.get(1));
            DayOfWeek usedDay = DayOfWeek.TUESDAY;

            assertNotNull(c1);

            assertEquals(4 * 12, underTest.getDegreesOfFreedom(c1));
            assertEquals(1 * 12, underTest.getDegreesOfFreedom(c2));
            assertEquals(2 * 12, underTest.getDegreesOfFreedom(c3));

            WeekEvent expected_event = new WeekEvent();
            expected_event.setId(UUID.randomUUID());
            expected_event.setCourse(c1);
            expected_event.setRooms(usedRooms);
            expected_event.setWeekday(usedDay);
            expected_event.setWeek(1);
            expected_event.setTimeslots(usedTimeslots);
            when(weekEventRepository.getWeekEventGraphTimeslotById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphRoomsById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphCourseById(expected_event.getId()))
                    .thenReturn(expected_event);

            boolean conflict = underTest.checkAdmissibility(expected_event).isCausingConflict();
            assertFalse(underTest.checkAdmissibility(expected_event).isCausingConflict());
            assertTrue(underTest.scheduleEvent(expected_event, true));
            assertTrue(underTest.checkAdmissibility(expected_event).isCausingConflict());

            assertEquals(4 * 12 - 1, underTest.getDegreesOfFreedom(c1));
            assertEquals(1 * 12 - 1, underTest.getDegreesOfFreedom(c2));
            assertEquals(2 * 12, underTest.getDegreesOfFreedom(c3));
        }
    }

    /**
     * Tests {@link Scheduler#findAllOptionsFor(Course)}:
     *
     * <p>Consider the following test setup:
     *
     * <pre>
     *  Entities:
     *  3 Courses: C1, C2, C3
     *  3 Employees: E1, E2, E3
     *  3 Timeslots: T1, T2, T3
     *  1 Room: R1
     *  1 Degree: D1
     *     with 3 semesters: D11, D12, D13
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: [D11]
     *          suitedRooms: [[R1]]
     *      C2:
     *          lecturers: [E1, E2, E3]
     *          semesters: [D12]
     *          suitedRooms: [[R1]]
     *      C3:
     *          lecturers: [E2, E3]
     *          semesters: [D11]
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employees:
     *      E1:
     *          Day\Ts T1 T2 T3
     *          Mon    1, 1, 0
     *          Tue    1, 0, 0
     *          Wed    0, 1, 1
     *      E2:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 1, 0
     *          Tue    1, 1, 0
     *          Wed    0, 0, 1
     *      E2:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 0, 0
     *          Tue    0, 1, 1
     *          Wed    0, 1, 1
     *  This should result in the following
     *  options:
     *          course: C1
     *          degreeOfFreedom: 3
     *          options: [
     *              (Mon, TS1, R1)
     *              (Mon, T2, R1)
     *              (Tue, T1, R1)]
     *
     *          course: C2
     *          degreeOfFreedom: 0
     *          options: []
     *
     *          course: C3
     *          degreeOfFreedom: 2
     *          options: [
     *              (Tue, T2, R1)
     *              (Wed, T3, R1)]
     * </pre>
     */
    @Test
    public void
            Test_FindAllOptions_3Courses_3Employees_3Days_3Timeslots_1Room_1Degree_3Semesters_Relations() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 1;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 0, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);
        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        ///////////////////////////////////////////////////////////////////////

        /*
            Begin Test
        */
        assertTrue(underTest.initialize());

        Course c1 = graphGen.getCourses().get(0);
        Course c2 = graphGen.getCourses().get(1);
        Course c3 = graphGen.getCourses().get(2);

        Timeslot t1 = graphGen.getTimeslots().get(0);
        Timeslot t2 = graphGen.getTimeslots().get(1);
        Timeslot t3 = graphGen.getTimeslots().get(2);

        Room r1 = graphGen.getRooms().get(0);

        OptionsDTO resultForC1 = underTest.findAllOptionsFor(c1);
        OptionsDTO resultForC2 = underTest.findAllOptionsFor(c2);
        OptionsDTO resultForC3 = underTest.findAllOptionsFor(c3);

        // then
        // verify options for c1
        assertEquals(c1.getId(), resultForC1.getCourse());
        assertEquals(3 * 12, resultForC1.getDegreeOfFreedom());
        Set<OptionDTO> expOptionsForC1 = new HashSet<>();
        for (int i = 0; i < 12; i++) {
            expOptionsForC1.add(
                    new OptionDTO(
                            i + 1, DayOfWeek.MONDAY, List.of(t1.getId()), List.of(r1.getId())));
            expOptionsForC1.add(
                    new OptionDTO(
                            i + 1, DayOfWeek.MONDAY, List.of(t2.getId()), List.of(r1.getId())));
            expOptionsForC1.add(
                    new OptionDTO(
                            i + 1, DayOfWeek.TUESDAY, List.of(t1.getId()), List.of(r1.getId())));
        }
        assertEquals(expOptionsForC1, new HashSet<>(resultForC1.getOptions()));

        // verify options for c2
        assertEquals(c2.getId(), resultForC2.getCourse());
        assertEquals(0, resultForC2.getDegreeOfFreedom());
        Set<OptionDTO> expOptionsForC2 = new HashSet<>();
        assertEquals(expOptionsForC2, new HashSet<>(resultForC2.getOptions()));

        // verify options for c3
        assertEquals(c3.getId(), resultForC3.getCourse());
        assertEquals(2 * 12, resultForC3.getDegreeOfFreedom());
        Set<OptionDTO> expOptionsForC3 = new HashSet<>();
        for (int i = 0; i < 12; i++) {
            expOptionsForC3.add(
                    new OptionDTO(
                            i + 1, DayOfWeek.TUESDAY, List.of(t2.getId()), List.of(r1.getId())));
            expOptionsForC3.add(
                    new OptionDTO(
                            i + 1, DayOfWeek.WEDNESDAY, List.of(t3.getId()), List.of(r1.getId())));
        }
        assertEquals(expOptionsForC3, new HashSet<>(resultForC3.getOptions()));
    }

    @Test
    public void test_curriculum() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 1;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];

        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

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

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {0, 0, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        assertTrue(underTest.initialize());

        Timeslot t1 = graphGen.getTimeslots().get(0);
        assertFalse(
                underTest
                        .getScheduleForEmployee(employees.get(1))
                        .get()
                        .isTimeslotAvailable(1, DayOfWeek.MONDAY, t1));
        assertFalse(
                underTest
                        .getScheduleForEmployee(employees.get(0))
                        .get()
                        .isTimeslotAvailable(1, DayOfWeek.MONDAY, t1));
    }

    @Test
    public void test_parallel() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

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
                    new int[] {0, 0, 1}, //
                    new int[] {0, 1, 0} //
                };

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];
        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {0, 1, 0}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 0}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {0, 0, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {0, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {0, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
            when(courseRelationRepository.findByCourseA_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsA());
            when(courseRelationRepository.findByCourseB_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsB());
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        {
            assertTrue(underTest.initialize());

            Course c1 = graphGen.getCourses().get(0);
            Course c2 = graphGen.getCourses().get(1);
            Course c3 = graphGen.getCourses().get(2);

            assertEquals(3 * 12, underTest.getDegreesOfFreedom(c1));
            assertEquals(1 * 12, underTest.getDegreesOfFreedom(c2));
            assertEquals(2 * 12, underTest.getDegreesOfFreedom(c3));

            List<Room> usedRooms = List.of(rooms.get(0));
            List<Timeslot> usedTimeslots = List.of(timeslots.get(1));
            DayOfWeek usedDay = DayOfWeek.TUESDAY;

            WeekEvent expected_event = new WeekEvent();
            expected_event.setId(UUID.randomUUID());
            expected_event.setCourse(c2);
            expected_event.setRooms(usedRooms);
            expected_event.setWeekday(usedDay);
            expected_event.setWeek(1);
            expected_event.setTimeslots(usedTimeslots);

            when(weekEventRepository.getWeekEventGraphTimeslotById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphRoomsById(expected_event.getId()))
                    .thenReturn(expected_event);
            when(weekEventRepository.getWeekEventGraphCourseById(expected_event.getId()))
                    .thenReturn(expected_event);

            assertTrue(underTest.scheduleEvent(expected_event, true));

            assertEquals(3 * 12 - 1, underTest.getDegreesOfFreedom(c1));
            assertEquals(1 * 12, underTest.getDegreesOfFreedom(c2));
            assertEquals(2 * 12, underTest.getDegreesOfFreedom(c3));

            usedRooms = List.of(rooms.get(1));

            WeekEvent event2 = new WeekEvent();
            event2.setId(UUID.randomUUID());
            event2.setCourse(c3);
            event2.setRooms(usedRooms);
            event2.setWeekday(usedDay);
            event2.setWeek(1);
            event2.setTimeslots(usedTimeslots);

            when(weekEventRepository.getWeekEventGraphTimeslotById(event2.getId()))
                    .thenReturn(event2);
            when(weekEventRepository.getWeekEventGraphRoomsById(event2.getId())).thenReturn(event2);
            when(weekEventRepository.getWeekEventGraphCourseById(event2.getId()))
                    .thenReturn(event2);

            assertTrue(underTest.scheduleEvent(event2, true));
            assertEquals(3 * 12 - 1, underTest.getDegreesOfFreedom(c1));
            assertEquals(1 * 12 - 1, underTest.getDegreesOfFreedom(c2));
            assertEquals(2 * 12 - 1, underTest.getDegreesOfFreedom(c3));
        }
    }

    @Test
    public void test_course_relations_before_after_1() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

        int[] blockSizes = new int[] {1, 1, 1};
        int[] slotsPerWeek = new int[] {1, 1, 1};

        // Course 1 | Course 2 | Course 3
        int[][] lecture_is_before =
                new int[][] { //
                    new int[] {0, 1, 0}, // | Course 1
                    new int[] {0, 0, 1}, // | Course 2
                    new int[] {0, 0, 0} //  | Course 3
                };
        // Course 1 | Course 2 | Course 3
        int[][] lecture_is_parallel =
                new int[][] { //
                    new int[] {0, 0, 0}, // | Course 1
                    new int[] {0, 0, 0}, // | Course 2
                    new int[] {0, 0, 0} //  | Course 3
                };

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];
        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
            when(courseRelationRepository.findByCourseA_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsA());
            when(courseRelationRepository.findByCourseB_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsB());
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        {
            assertTrue(underTest.initialize());
            List<Room> usedRooms = List.of(rooms.get(0));
            List<Timeslot> usedTimeslots = List.of(timeslots.get(1));
            DayOfWeek usedDay = DayOfWeek.TUESDAY;

            Course c1 = graphGen.getCourses().get(0);
            Course c2 = graphGen.getCourses().get(1);
            Course c3 = graphGen.getCourses().get(2);

            Options opts1 = underTest.getOptionsForCourse(c1).get();
            Options opts2 = underTest.getOptionsForCourse(c2).get();
            Options opts3 = underTest.getOptionsForCourse(c3).get();

            assertEquals(0, opts1.coursesBefore.size());
            assertEquals(1, opts1.coursesAfter.size());
            assertEquals(c2.getId(), opts1.coursesAfter.iterator().next());

            assertEquals(1, opts2.coursesBefore.size());
            assertEquals(1, opts2.coursesAfter.size());
            assertEquals(c1.getId(), opts2.coursesBefore.iterator().next());
            assertEquals(c3.getId(), opts2.coursesAfter.iterator().next());

            assertEquals(1, opts3.coursesBefore.size());
            assertEquals(0, opts3.coursesAfter.size());
            assertEquals(c2.getId(), opts3.coursesBefore.iterator().next());

            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c1));
            assertEquals(3 * 12, underTest.getDegreesOfFreedom(c2));
            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c3));

            WeekEvent weekEvent = new WeekEvent();
            weekEvent.setId(UUID.randomUUID());
            weekEvent.setCourse(c2);
            weekEvent.setRooms(usedRooms);
            weekEvent.setWeekday(usedDay);
            weekEvent.setWeek(1);
            weekEvent.setTimeslots(usedTimeslots);

            when(weekEventRepository.getWeekEventGraphTimeslotById(weekEvent.getId()))
                    .thenReturn(weekEvent);
            when(weekEventRepository.getWeekEventGraphRoomsById(weekEvent.getId()))
                    .thenReturn(weekEvent);
            when(weekEventRepository.getWeekEventGraphCourseById(weekEvent.getId()))
                    .thenReturn(weekEvent);

            assertTrue(underTest.scheduleEvent(weekEvent, true));

            System.out.println(
                    opts1.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(4 * 12, underTest.getDegreesOfFreedom(c1));

            System.out.println(
                    opts2.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(3 * 12 - 1, underTest.getDegreesOfFreedom(c2));

            System.out.println(
                    opts3.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(4 * 12, underTest.getDegreesOfFreedom(c3));
        }
    }

    @Test
    public void test_course_relations_before_after_2() {
        final int amountCourses = 3;
        final int amountEmployees = 3;
        final int amountDegrees = 1;
        final int amountSemesters = 3;
        final int amountRooms = 2;

        final int DEGREE_SEM_1 = 0;
        final int DEGREE_SEM_2 = 1;
        final int DEGREE_SEM_3 = 2;

        final List<DayOfWeek> days =
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        final int amountTimeslots = 3;

        int[][] coursesVisitedBy = new int[3][amountCourses];
        // 1 = is Visisted/Hosted by Employe ; 0 = is not hosted by employee
        // Course 1 | Course 2 | Course 3
        coursesVisitedBy[DEGREE_SEM_1] = new int[] {1, 0, 0}; // | DegreeSemester 1
        coursesVisitedBy[DEGREE_SEM_2] = new int[] {0, 1, 0}; // | DegreeSemester 2
        coursesVisitedBy[DEGREE_SEM_3] = new int[] {0, 0, 1}; // | DegreeSemester 3

        int[] blockSizes = new int[] {1, 1, 1};
        int[] slotsPerWeek = new int[] {1, 1, 1};

        // Course 1 | Course 2 | Course 3
        int[][] lecture_is_before =
                new int[][] { //
                    new int[] {0, 1, 0}, // | Course 1
                    new int[] {0, 0, 1}, // | Course 2
                    new int[] {0, 0, 0} //  | Course 3
                };
        // Course 1 | Course 2 | Course 3
        int[][] lecture_is_parallel =
                new int[][] { //
                    new int[] {0, 0, 0}, // | Course 1
                    new int[] {0, 0, 0}, // | Course 2
                    new int[] {0, 0, 0} //  | Course 3
                };

        int[][] c1_is_available_for = new int[days.size()][amountTimeslots];
        c1_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c1_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c1_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c2_is_available_for = new int[days.size()][amountTimeslots];
        c2_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c2_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c2_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] c3_is_available_for = new int[days.size()][amountTimeslots];
        c3_is_available_for[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        c3_is_available_for[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        c3_is_available_for[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] employeeHoldsLecture = new int[amountEmployees][amountCourses];
        // Course 1 | Course 2 | Course 3
        employeeHoldsLecture[EMPLOY_1] = new int[] {1, 1, 0}; // | Employee 1
        employeeHoldsLecture[EMPLOY_2] = new int[] {0, 1, 1}; // | Employee 2
        employeeHoldsLecture[EMPLOY_3] = new int[] {0, 1, 1}; // | Employee 3

        // 1 = is has Working time set  ; 0 = is not working
        // Timeslot 1 | Timeslot 2 | Timeslot 3
        int[][] work_time_employee_1 = new int[days.size()][amountTimeslots];
        work_time_employee_1[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        work_time_employee_1[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_1[WED] = new int[] {0, 0, 0}; // | WeekDay 3

        int[][] work_time_employee_2 = new int[days.size()][amountTimeslots];
        work_time_employee_2[MON] = new int[] {1, 1, 1}; // | WeekDay 1
        work_time_employee_2[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_2[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        int[][] work_time_employee_3 = new int[days.size()][amountTimeslots];
        work_time_employee_3[MON] = new int[] {0, 0, 0}; // | WeekDay 1
        work_time_employee_3[TUE] = new int[] {1, 1, 1}; // | WeekDay 2
        work_time_employee_3[WED] = new int[] {1, 1, 1}; // | WeekDay 3

        ///////////////////////////////////////////////////////////////////////
        // Setup Mocks
        SchedulerTestUtil.Generator graphGen =
                new SchedulerTestUtil.Generator(
                        days,
                        amountTimeslots,
                        amountCourses,
                        blockSizes,
                        slotsPerWeek,
                        lecture_is_before,
                        lecture_is_parallel,
                        c1_is_available_for,
                        c2_is_available_for,
                        c3_is_available_for);

        List<Employee> employees =
                graphGen.generateEmployees(
                        amountEmployees,
                        employeeHoldsLecture,
                        work_time_employee_1,
                        work_time_employee_2,
                        work_time_employee_3);
        assertEquals(amountEmployees, employees.size());

        List<Pair<Degree, List<DegreeSemester>>> degrees =
                graphGen.generateDegrees(amountDegrees, coursesVisitedBy);
        assertEquals(amountDegrees, degrees.size());
        for (Pair<Degree, List<DegreeSemester>> p : degrees) {
            assertEquals(amountSemesters, p.getSecond().size());
        }

        List<Room> rooms = graphGen.generateRooms(amountRooms, null, 10);
        assertEquals(amountRooms, rooms.size());

        List<Timeslot> timeslots = new ArrayList<>(graphGen.getTimeslots());
        assertEquals(amountTimeslots, timeslots.size());
        List<Course> courses = new ArrayList<>(graphGen.getCourses());

        for (Employee emp : graphGen.getEmployees()) {
            when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        }

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(roomRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(new ArrayList<>(graphGen.getRooms()));

        when(courseRepository.findByTimetableId(any(UUID.class))).thenReturn(courses);
        for (Course course : graphGen.getCourses()) {
            when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
            when(courseRelationRepository.findByCourseA_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsA());
            when(courseRelationRepository.findByCourseB_Id(course.getId()))
                    .thenReturn(course.getCourseRelationsB());
        }

        //        when(degreeRepository.findAll()).thenReturn(new ArrayList<>(graphGen.degrees));
        //        when(degreeSemesterRepository.findAll()).thenReturn(new
        // ArrayList<>(graphGen.degreeSemesters));

        {
            assertTrue(underTest.initialize());
            List<Room> usedRooms = List.of(rooms.get(0));
            List<Timeslot> usedTimeslots = List.of(timeslots.get(0));
            DayOfWeek usedDay = DayOfWeek.TUESDAY;

            Course c1 = graphGen.getCourses().get(0);
            Course c2 = graphGen.getCourses().get(1);
            Course c3 = graphGen.getCourses().get(2);

            Options opts1 = underTest.getOptionsForCourse(c1).get();
            Options opts2 = underTest.getOptionsForCourse(c2).get();
            Options opts3 = underTest.getOptionsForCourse(c3).get();

            assertEquals(0, opts1.coursesBefore.size());
            assertEquals(1, opts1.coursesAfter.size());
            assertEquals(c2.getId(), opts1.coursesAfter.iterator().next());

            assertEquals(1, opts2.coursesBefore.size());
            assertEquals(1, opts2.coursesAfter.size());
            assertEquals(c1.getId(), opts2.coursesBefore.iterator().next());
            assertEquals(c3.getId(), opts2.coursesAfter.iterator().next());

            assertEquals(1, opts3.coursesBefore.size());
            assertEquals(0, opts3.coursesAfter.size());
            assertEquals(c2.getId(), opts3.coursesBefore.iterator().next());

            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c1));
            assertEquals(3 * 12, underTest.getDegreesOfFreedom(c2));
            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c3));

            WeekEvent weekEvent = new WeekEvent();
            weekEvent.setId(UUID.randomUUID());
            weekEvent.setCourse(c3);
            weekEvent.setRooms(usedRooms);
            weekEvent.setWeekday(usedDay);
            weekEvent.setWeek(1);
            weekEvent.setTimeslots(usedTimeslots);

            when(weekEventRepository.getWeekEventGraphTimeslotById(weekEvent.getId()))
                    .thenReturn(weekEvent);
            when(weekEventRepository.getWeekEventGraphRoomsById(weekEvent.getId()))
                    .thenReturn(weekEvent);
            when(weekEventRepository.getWeekEventGraphCourseById(weekEvent.getId()))
                    .thenReturn(weekEvent);

            assertTrue(underTest.scheduleEvent(weekEvent, true));

            System.out.println(
                    opts1.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c1));

            System.out.println(
                    opts2.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(0, underTest.getDegreesOfFreedom(c2));

            System.out.println(
                    opts3.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(6 * 12 - 1, underTest.getDegreesOfFreedom(c3));

            WeekEvent inAdmissableWeekEvent = new WeekEvent();
            inAdmissableWeekEvent.setId(UUID.randomUUID());
            inAdmissableWeekEvent.setCourse(c2);
            inAdmissableWeekEvent.setTimeslots(List.of(timeslots.get(1)));
            inAdmissableWeekEvent.setRooms(List.of(rooms.get(0)));
            inAdmissableWeekEvent.setWeekday(DayOfWeek.TUESDAY);
            inAdmissableWeekEvent.setWeek(1);

            CheckedEvent returnedAdmissibility =
                    underTest.checkAdmissibility(inAdmissableWeekEvent);

            assertNotNull(returnedAdmissibility);
            assertTrue(returnedAdmissibility.isCausingConflict());

            assertEquals(1, returnedAdmissibility.getConflicts().size());
            assertEquals(
                    Constraint.COURSE_RELATION,
                    returnedAdmissibility.getConflicts().get(0).getConstraint());

            assertTrue(underTest.unscheduleEvent(weekEvent));

            System.out.println(
                    opts1.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c1));

            System.out.println(
                    opts2.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(3 * 12, underTest.getDegreesOfFreedom(c2));

            System.out.println(
                    opts3.toStringifiedMatrix(
                            Collections.emptyList(),
                            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                            timeslots));
            assertEquals(6 * 12, underTest.getDegreesOfFreedom(c3));
        }
    }
}
