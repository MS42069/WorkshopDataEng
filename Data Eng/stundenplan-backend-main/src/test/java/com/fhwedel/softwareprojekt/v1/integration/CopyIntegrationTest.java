package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.TimetableResDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseTimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomCombinationRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.SpecialEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class CopyIntegrationTest {

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;
    @Autowired
    private SpecialEventRepository specialEventRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRelationRepository courseRelationRepository;

    @Autowired
    private RoomCombinationRepository roomCombinationRepository;

    @Autowired
    private CourseTimeslotRepository courseTimeslotRepository;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private DegreeSemesterRepository degreeSemesterRepository;

    @Autowired
    private WeekEventRepository weekEventRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @AfterEach
    void rollback() {
        // note, the deletion order is important: e.g. timetable have to be deleted before timeslots
        weekEventRepository.deleteAll();
        specialEventRepository.deleteAll();
        courseRelationRepository.deleteAll();
        courseTimeslotRepository.deleteAll();
        roomCombinationRepository.deleteAll();
        courseRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        degreeRepository.deleteAll();
        workTimeRepository.deleteAll();
        employeeRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    /**
     * Testsetup: Aus Timetable tt1 wird Room in Timetable tt2 kopiert, sodass am Ende 2 Räume mit
     * unterschiedlichen Timetables aber ansonsten gleichen Werten in der DB persistiert werden
     */
    @Test
    void copyRoomSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        RoomType rt = createRoomType();
        createRoom1(tt1, rt);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, roomRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(1, roomRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(2, roomRepository.findAll()
                .size());
    }

    @Test
    void copyRooms() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        RoomType rt = createRoomType();
        // auf TT1 setzen
        createRoom1(tt1, rt);
        createRoom2(tt1, rt);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, roomRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(2, roomRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(4, roomRepository.findAll()
                .size());
    }

    @Test
    void copyTimeslotSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createTimeslot1(tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, timeslotRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(1, timeslotRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(2, timeslotRepository.findAll()
                .size());
    }

    @Test
    void copyTimeslots() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createTimeslot1(tt1);
        createTimeslot2(tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, timeslotRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(2, timeslotRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(4, timeslotRepository.findAll()
                .size());
    }

    @Test
    void copyEmployees() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createEmployee1(tt1);
        createEmployee2(tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, employeeRepository.findByTimetable(tt1)
                .size());
        assertEquals(2, employeeRepository.findByTimetable(tt2)
                .size());
        assertEquals(4, employeeRepository.findAll()
                .size());
    }

    @Test
    void copyWorkTimeSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Employee e1 = createEmployee1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        createWorkTime(e1, ts1);

        assertEquals(1, workTimeRepository.findAll()
                .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, workTimeRepository.findAll()
                .size());
        assertNotEquals(workTimeRepository.findAll()
                .get(0), workTimeRepository.findAll()
                .get(1));
        // Different Timeslots -> Timeslots got changed accordingly
        assertNotEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getTimeslot()
                        .getId(),
                workTimeRepository.findAll()
                        .get(1)
                        .getTimeslot()
                        .getId());
        // Attributes of Timeslots are equal
        assertEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getTimeslot()
                        .getIndex(),
                workTimeRepository.findAll()
                        .get(1)
                        .getTimeslot()
                        .getIndex());
    }

    @Test
    void copyWorkTimes() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Employee e1 = createEmployee1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        Timeslot ts2 = createTimeslot2(tt1);
        createWorkTime(e1, ts1);
        createWorkTime(e1, ts2);

        assertEquals(2, workTimeRepository.findAll()
                .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(4, workTimeRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyEmployeeAndWorkTime() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Employee e1 = createEmployee1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        WorkTime wt1 = createWorkTime(e1, ts1);
        e1.setWorkTimes(List.of(wt1));
        assertEquals(1, workTimeRepository.findAll()
                .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, workTimeRepository.findAll()
                .size());
        assertEquals(
                workTimeRepository.findAll()
                        .get(0),
                employeeRepository.findAll()
                        .get(0)
                        .getWorkTimes()
                        .get(0));
        assertEquals(
                workTimeRepository.findAll()
                        .get(1),
                employeeRepository.findAll()
                        .get(1)
                        .getWorkTimes()
                        .get(0));
        assertEquals(
                employeeRepository
                        .findAll()
                        .get(0)
                        .getWorkTimes()
                        .get(0)
                        .getEmployee()
                        .getAbbreviation(),
                employeeRepository
                        .findAll()
                        .get(1)
                        .getWorkTimes()
                        .get(0)
                        .getEmployee()
                        .getAbbreviation());
        assertNotEquals(
                employeeRepository.findAll()
                        .get(0)
                        .getWorkTimes()
                        .get(0)
                        .getEmployee()
                        .getId(),
                employeeRepository.findAll()
                        .get(1)
                        .getWorkTimes()
                        .get(0)
                        .getEmployee()
                        .getId());
        assertNotEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getId(),
                workTimeRepository.findAll()
                        .get(1)
                        .getId());
        assertNotEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getEmployee()
                        .getId(),
                workTimeRepository.findAll()
                        .get(1)
                        .getEmployee()
                        .getId());
        assertNotEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getTimeslot()
                        .getId(),
                workTimeRepository.findAll()
                        .get(1)
                        .getTimeslot()
                        .getId());
        assertEquals(
                workTimeRepository.findAll()
                        .get(0)
                        .getTimeslot()
                        .getIndex(),
                workTimeRepository.findAll()
                        .get(1)
                        .getTimeslot()
                        .getIndex());
    }

    @Test
    void copyCourseSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createCourse1(tt1);
        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyCourseLecturerSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        List<Employee> eList1 = courseRepository.findAll()
                .get(0)
                .getLecturers();
        List<Employee> eList2 = courseRepository.findAll()
                .get(1)
                .getLecturers();

        assertEquals(eList1.get(0)
                .getAbbreviation(), eList2.get(0)
                .getAbbreviation());
        assertNotEquals(eList1.get(0)
                .getId(), eList2.get(0)
                .getId());
    }

    @Test
    void copyCourseRelationSingle() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Course c2 = createCourse2(tt1);
        createCourseRelation1(c1, c2);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, courseRelationRepository.findAll()
                .size());
        assertNotEquals(
                courseRelationRepository.findAll()
                        .get(0)
                        .getCourseA(),
                courseRelationRepository.findAll()
                        .get(1)
                        .getCourseA());
        assertEquals(
                courseRelationRepository.findAll()
                        .get(0)
                        .getCourseA()
                        .getName(),
                courseRelationRepository.findAll()
                        .get(1)
                        .getCourseA()
                        .getName());
    }

    @Test
    @Transactional
    void copyEmployeeAndCourse_BothDirections() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);
        e1.getCourses()
                .add(c1);

        assertEquals(
                employeeRepository.findAll()
                        .get(0)
                        .getCourses()
                        .get(0)
                        .getName(), c1.getName());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, employeeRepository.findAll()
                .size());
        // course has filled EmployeeList
        assertEquals(1, courseRepository.findAll()
                .get(1)
                .getLecturers()
                .size());
        // employee has filled courseList
        assertEquals(1, employeeRepository.findAll()
                .get(1)
                .getCourses()
                .size());
    }

    @Test
    @Transactional
    void copyCourseAndCourseRelation() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Course c2 = createCourse2(tt1);
        CourseRelation cr1 = createCourseRelation1(c1, c2);
        c1.getCourseRelationsA()
                .add(cr1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(4, courseRepository.findAll()
                .size());

        assertEquals(
                courseRelationRepository.findAll()
                        .get(0),
                courseRepository.findAll()
                        .get(0)
                        .getCourseRelationsA()
                        .get(0));
        assertEquals(
                courseRelationRepository.findAll()
                        .get(0),
                courseRepository.findAll()
                        .get(0)
                        .getCourseRelationsA()
                        .get(0));

        // Beide Listen sind nicht leer und liefern gleiches Objekt. Kopierter Kurs und kopierte
        // Relation
        assertEquals(
                courseRelationRepository.findAll()
                        .get(1),
                courseRepository.findAll()
                        .get(2)
                        .getCourseRelationsA()
                        .get(0));
    }

    @Test
    void copyCourseTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);

        createCourseTimeslot(c1, ts1);
        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, courseTimeslotRepository.findAll()
                .size());

        assertEquals(
                courseTimeslotRepository.findAll()
                        .get(0)
                        .getWeekday(),
                courseTimeslotRepository.findAll()
                        .get(1)
                        .getWeekday());

        assertNotEquals(
                courseTimeslotRepository.findAll()
                        .get(0)
                        .getId(),
                courseTimeslotRepository.findAll()
                        .get(1)
                        .getId());
    }

    @Test
    @Transactional
    void copyCourseAndCourseTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        CourseTimeslot cts1 = createCourseTimeslot(c1, ts1);
        c1.getCourseTimeslots()
                .add(cts1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, courseTimeslotRepository.findAll()
                .size());

        assertEquals(1, courseRepository.findAll()
                .get(1)
                .getCourseTimeslots()
                .size());
    }

    @Test
    @Transactional
    void copyCourseAndWeekEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        createWeekEvent(c1, tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, weekEventRepository.findAll()
                .size());

        // WeekEvents get saved in Course in a List
        assertEquals(1, courseRepository.findAll()
                .get(1)
                .getWeekEvents()
                .size());
    }

    @Test
    @Transactional
    void copyWeekEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        RoomType rt = createRoomType();
        Room r1 = createRoom1(tt1, rt);
        Timeslot ts1 = createTimeslot1(tt1);
        WeekEvent we1 = createWeekEvent(c1, tt1);
        we1.getRooms()
                .add(r1);
        we1.getTimeslots()
                .add(ts1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, weekEventRepository.findAll()
                .size());
        List<WeekEvent> weekEvents = weekEventRepository.findAll();

        assertNotEquals(weekEvents.get(0)
                .getId(), weekEvents.get(1)
                .getId());
        assertEquals(weekEvents.get(0)
                .getWeekday(), weekEvents.get(1)
                .getWeekday());
        // Course
        assertNotEquals(
                weekEvents.get(0)
                        .getCourse()
                        .getId(), weekEvents.get(1)
                        .getCourse()
                        .getId());
        assertEquals(
                weekEvents.get(0)
                        .getCourse()
                        .getAbbreviation(),
                weekEvents.get(1)
                        .getCourse()
                        .getAbbreviation());
        // week_events_timeslots
        assertNotEquals(
                weekEvents.get(0)
                        .getTimeslots()
                        .get(0)
                        .getId(),
                weekEvents.get(1)
                        .getTimeslots()
                        .get(0)
                        .getId());
        assertEquals(
                weekEvents.get(0)
                        .getTimeslots()
                        .size(), weekEvents.get(1)
                        .getTimeslots()
                        .size());
        assertEquals(
                weekEvents.get(0)
                        .getTimeslots()
                        .get(0)
                        .getIndex(),
                weekEvents.get(1)
                        .getTimeslots()
                        .get(0)
                        .getIndex());
        // week_events_rooms
        assertNotEquals(
                weekEvents.get(0)
                        .getRooms()
                        .get(0)
                        .getId(),
                weekEvents.get(1)
                        .getRooms()
                        .get(0)
                        .getId());
        assertEquals(weekEvents.get(0)
                .getRooms()
                .size(), weekEvents.get(1)
                .getRooms()
                .size());
        assertEquals(
                weekEvents.get(0)
                        .getRooms()
                        .get(0)
                        .getAbbreviation(),
                weekEvents.get(1)
                        .getRooms()
                        .get(0)
                        .getAbbreviation());
    }

    @Test
    void copyDegree() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createDegree1(tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, degreeRepository.findAll()
                .size());

        assertEquals(
                degreeRepository.findAll()
                        .get(0)
                        .getShortName(),
                degreeRepository.findAll()
                        .get(1)
                        .getShortName());

        assertNotEquals(
                degreeRepository.findAll()
                        .get(0)
                        .getId(),
                degreeRepository.findAll()
                        .get(1)
                        .getId());
    }

    @Test
    void copyDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        createDegreeSemester1(d1, tt1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, degreeSemesterRepository.findAll()
                .size());

        assertEquals(
                degreeSemesterRepository.findAll()
                        .get(0)
                        .getSemesterNumber(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getSemesterNumber());

        assertNotEquals(
                degreeSemesterRepository.findAll()
                        .get(0)
                        .getId(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getId());
    }

    @Test
    @Transactional
    void copyDegreeAndDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.getSemesters()
                .add(ds1);
        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, degreeRepository.findAll()
                .size());

        assertEquals(
                degreeRepository.findAll()
                        .get(0)
                        .getSemesters()
                        .get(0),
                degreeSemesterRepository.findAll()
                        .get(0));

        assertNotEquals(
                degreeSemesterRepository.findAll()
                        .get(0)
                        .getId(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getId());

        assertEquals(
                degreeRepository.findAll()
                        .get(1)
                        .getSemesters()
                        .get(0)
                        .getId(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getId());

        assertEquals(
                degreeRepository.findAll()
                        .get(1)
                        .getSemesters()
                        .get(0),
                degreeSemesterRepository.findAll()
                        .get(1));
    }

    @Test
    @Transactional
    void copyDegreeSemesterAndCourse_BothDirections() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        Course c1 = createCourse1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.getSemesters()
                .add(ds1);
        c1.getSemesters()
                .add(ds1);
        ds1.getCourses()
                .add(c1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, degreeRepository.findAll()
                .size());
        assertEquals(2, degreeSemesterRepository.findAll()
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        assertNotEquals(
                courseRepository.findAll()
                        .get(0)
                        .getId(),
                courseRepository.findAll()
                        .get(1)
                        .getId());
        // IDs unterschiedlich
        assertNotEquals(
                courseRepository.findAll()
                        .get(0)
                        .getSemesters()
                        .get(0)
                        .getId(),
                courseRepository.findAll()
                        .get(1)
                        .getSemesters()
                        .get(0)
                        .getId());
        // Daten gleich
        assertEquals(
                courseRepository.findAll()
                        .get(0)
                        .getSemesters()
                        .get(0)
                        .getSemesterNumber(),
                courseRepository.findAll()
                        .get(1)
                        .getSemesters()
                        .get(0)
                        .getSemesterNumber());

        assertNotEquals(
                degreeSemesterRepository.findAll()
                        .get(0)
                        .getCourses()
                        .get(0)
                        .getId(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getCourses()
                        .get(0)
                        .getId());
        assertEquals(
                degreeSemesterRepository.findAll()
                        .get(0)
                        .getCourses()
                        .get(0)
                        .getName(),
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getCourses()
                        .get(0)
                        .getName());
        // gleiche Entity
        assertEquals(
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getCourses()
                        .get(0)
                        .getId(),
                courseRepository.findAll()
                        .get(1)
                        .getId());

        assertEquals(
                degreeSemesterRepository.findAll()
                        .get(1)
                        .getId(),
                courseRepository.findAll()
                        .get(1)
                        .getSemesters()
                        .get(0)
                        .getId());
    }

    @Test
    @Transactional
    void copyRoomCombination() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        RoomType rt = createRoomType();
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Room r1 = createRoom1(tt1, rt);
        createRoomCombination(List.of(r1), c1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        assertNotEquals(
                roomCombinationRepository.findAll()
                        .get(0)
                        .getId(),
                roomCombinationRepository.findAll()
                        .get(1)
                        .getId());
    }

    @Test
    @Transactional
    void copyRoomCombinationAndCourse_BothDirections() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        RoomType rt = createRoomType();
        Room r1 = createRoom1(tt1, rt);
        RoomCombination rc1 = createRoomCombination(List.of(r1), c1);
        c1.getSuitedRooms()
                .add(rc1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        assertNotEquals(
                roomCombinationRepository.findAll()
                        .get(0)
                        .getId(),
                roomCombinationRepository.findAll()
                        .get(1)
                        .getId());
        assertEquals(
                roomCombinationRepository.findAll()
                        .get(0)
                        .getCourse()
                        .getName(),
                roomCombinationRepository.findAll()
                        .get(1)
                        .getCourse()
                        .getName());

        assertNotEquals(
                roomCombinationRepository.findAll()
                        .get(0)
                        .getRooms()
                        .get(0)
                        .getId(),
                roomCombinationRepository.findAll()
                        .get(1)
                        .getRooms()
                        .get(0)
                        .getId());
        assertEquals(
                roomCombinationRepository.findAll()
                        .get(0)
                        .getRooms()
                        .get(0)
                        .getAbbreviation(),
                roomCombinationRepository.findAll()
                        .get(1)
                        .getRooms()
                        .get(0)
                        .getAbbreviation());
        // Kopie hat ander ID (und ist vorhanden)
        assertNotEquals(
                courseRepository.findAll()
                        .get(0)
                        .getSuitedRooms()
                        .get(0)
                        .getId(),
                courseRepository.findAll()
                        .get(1)
                        .getSuitedRooms()
                        .get(0)
                        .getId());
        // gleiches Objekt
        assertEquals(
                courseRepository.findAll()
                        .get(0)
                        .getSuitedRooms()
                        .get(0)
                        .getId(),
                roomCombinationRepository.findAll()
                        .get(0)
                        .getId());
    }

    @Test
    @Transactional
    void copySpecialEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        SpecialEvent se1 = createSpecialEvent1(tt1);
        tt1.setSpecialEvents(List.of(se1));
        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, specialEventRepository.findAll()
                .size());

        assertNotEquals(
                specialEventRepository.findAll()
                        .get(0)
                        .getId(),
                specialEventRepository.findAll()
                        .get(1)
                        .getId());
        assertEquals(
                specialEventRepository.findAll()
                        .get(0)
                        .getEndDate(),
                specialEventRepository.findAll()
                        .get(1)
                        .getEndDate());

        assertEquals(
                timetableRepository
                        .findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(0)
                        .getSpecialEventType(),
                timetableRepository
                        .findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getSpecialEventType());
        assertNotEquals(
                timetableRepository.findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(0)
                        .getId(),
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getId());
        assertEquals(
                timetableRepository
                        .findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getSpecialEventType(),
                se1.getSpecialEventType());
        assertEquals(
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getEndDate(),
                se1.getEndDate());
    }

    @Test
    void copyDeleteOldData() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());

        RoomType rt = createRoomType();

        Room r1 = createRoom1(tt2, rt);
        Timeslot ts1 = createTimeslot1(tt2);
        createRoom2(tt2, rt);

        Employee e1 = createEmployee1(tt2);
        createWorkTime(e1, ts1);
        Course c1 = createCourse1(tt2);
        Course c2 = createCourse2(tt2);
        createCourseRelation1(c1, c2);
        createCourseTimeslot(c1, ts1);
        Degree d1 = createDegree1(tt2);
        createWeekEvent(c1, tt2);
        createDegreeSemester1(d1, tt2);
        createRoomCombination(List.of(r1), c1);
        createSpecialEvent1(tt2);

        String url = format("/v1/timetables/%s/copyfrom/%s", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(0, roomRepository.findAll()
                .size());
        assertEquals(0, timeslotRepository.findAll()
                .size());
        assertEquals(0, employeeRepository.findAll()
                .size());
        assertEquals(0, workTimeRepository.findAll()
                .size());
        assertEquals(0, courseRepository.findAll()
                .size());
        assertEquals(0, courseRelationRepository.findAll()
                .size());
        assertEquals(0, courseTimeslotRepository.findAll()
                .size());
        assertEquals(0, degreeRepository.findAll()
                .size());
        assertEquals(0, degreeSemesterRepository.findAll()
                .size());
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
        assertEquals(0, specialEventRepository.findAll()
                .size());
        assertEquals(0, weekEventRepository.findAll()
                .size());
        assertEquals(2, timetableRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyBigTimetable() throws Exception {
        // Alles erstellen
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());

        RoomType rt = createRoomType();

        Room r1 = createRoom1(tt1, rt);
        Room r2 = createRoom2(tt1, rt);

        Timeslot ts1 = createTimeslot1(tt1);
        Timeslot ts2 = createTimeslot2(tt1);

        Employee e1 = createEmployee1(tt1);
        Employee e2 = createEmployee2(tt1);

        WorkTime e1ts1 = createWorkTime(e1, ts1);
        WorkTime e1ts2 = createWorkTime(e1, ts2);
        WorkTime e2ts1 = createWorkTime(e2, ts1);
        WorkTime e2ts2 = createWorkTime(e2, ts2);

        Course c1 = createCourse1(tt1);
        Course c2 = createCourse2(tt1);

        CourseRelation cr1 = createCourseRelation1(c1, c2);
        CourseRelation cr2 = createCourseRelation1(c2, c1);

        CourseTimeslot c1ts1 = createCourseTimeslot(c1, ts1);
        CourseTimeslot c1ts2 = createCourseTimeslot(c1, ts2);
        CourseTimeslot c2ts1 = createCourseTimeslot(c2, ts1);
        CourseTimeslot c2ts2 = createCourseTimeslot(c2, ts2);

        Degree d1 = createDegree1(tt1);
        Degree d2 = createDegree2(tt1);

        WeekEvent we1 = createWeekEvent(c1, tt1);
        WeekEvent we2 = createWeekEvent(c2, tt1);

        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        DegreeSemester ds2 = createDegreeSemester1(d2, tt1);

        RoomCombination rc1 = createRoomCombination(List.of(r1), c1);
        RoomCombination rc2 = createRoomCombination(List.of(r1, r2), c2);

        SpecialEvent se1 = createSpecialEvent1(tt1);
        SpecialEvent se2 = createSpecialEvent2(tt1);

        // Lists setzen
        tt1.setSpecialEvents(List.of(se1, se2));

        e1.setWorkTimes(List.of(e1ts1, e1ts2));
        e2.setWorkTimes(List.of(e2ts1, e2ts2));

        e1.setCourses(List.of(c1));
        e2.setCourses(List.of(c1, c2));

        c1.setCourseRelationsA(List.of(cr1));
        c1.setCourseRelationsB(List.of(cr2));
        c2.setCourseRelationsA(List.of(cr2));
        c2.setCourseRelationsB(List.of(cr1));

        c1.setCourseTimeslots(List.of(c1ts1, c1ts2));
        c2.setCourseTimeslots(List.of(c2ts1, c2ts2));
        c1.setLecturers(List.of(e1));
        c2.setLecturers(List.of(e1, e2));

        c1.setSuitedRooms(List.of(rc1));
        c2.setSuitedRooms(List.of(rc2));

        c1.setSemesters(List.of(ds1, ds2));
        c2.setSemesters(List.of(ds1, ds2));

        c1.setWeekEvents(List.of(we1));
        c2.setWeekEvents(List.of(we2));

        d1.setSemesters(List.of(ds1));
        d2.setSemesters(List.of(ds2));

        ds1.setCourses(List.of(c1, c2));
        ds2.setCourses(List.of(c1, c2));

        tt1.setSpecialEvents(List.of(se1, se2));

        assertEquals(2, roomRepository.findAll()
                .size());
        assertEquals(2, timeslotRepository.findAll()
                .size());
        assertEquals(2, employeeRepository.findAll()
                .size());
        assertEquals(4, workTimeRepository.findAll()
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());
        assertEquals(2, courseRelationRepository.findAll()
                .size());
        assertEquals(4, courseTimeslotRepository.findAll()
                .size());
        assertEquals(2, degreeRepository.findAll()
                .size());
        assertEquals(2, degreeSemesterRepository.findAll()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
        assertEquals(2, specialEventRepository.findAll()
                .size());
        assertEquals(2, weekEventRepository.findAll()
                .size());

        assertEquals(2, timetableRepository.findAll()
                .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&employee=1&timeslot=1"
                                + "&course=1&degree=1&degreesemester=1&weekevent=1&specialevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        // doppelt so viele Einträge überall
        assertEquals(4, roomRepository.findAll()
                .size());
        assertEquals(4, timeslotRepository.findAll()
                .size());
        assertEquals(4, employeeRepository.findAll()
                .size());
        assertEquals(8, workTimeRepository.findAll()
                .size());
        assertEquals(4, courseRepository.findAll()
                .size());
        assertEquals(4, courseRelationRepository.findAll()
                .size());
        assertEquals(8, courseTimeslotRepository.findAll()
                .size());
        assertEquals(4, degreeRepository.findAll()
                .size());
        assertEquals(4, degreeSemesterRepository.findAll()
                .size());
        assertEquals(4, roomCombinationRepository.findAll()
                .size());
        assertEquals(4, specialEventRepository.findAll()
                .size());
        assertEquals(4, weekEventRepository.findAll()
                .size());

        // size == 4? dann 0-1 == tt1    2-3 == tt2
        // size == 8? dann 0-3 == tt1    4-7 == tt2
        // Auf korrekte Timetables untersuchen
        assertEquals(specialEventRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(specialEventRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(roomRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(roomRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(employeeRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(employeeRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(workTimeRepository.findAll()
                .get(3)
                .getEmployee()
                .getTimetable(), tt1);
        assertEquals(workTimeRepository.findAll()
                .get(6)
                .getEmployee()
                .getTimetable(), tt2);

        assertEquals(courseRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(courseRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(courseRelationRepository.findAll()
                .get(1)
                .getCourseA()
                .getTimetable(), tt1);
        assertEquals(courseRelationRepository.findAll()
                .get(2)
                .getCourseA()
                .getTimetable(), tt2);

        assertEquals(courseTimeslotRepository.findAll()
                .get(2)
                .getTimeslot()
                .getTimetable(), tt1);
        assertEquals(courseTimeslotRepository.findAll()
                .get(5)
                .getTimeslot()
                .getTimetable(), tt2);

        assertEquals(degreeRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(degreeRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(degreeSemesterRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(degreeSemesterRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        assertEquals(roomCombinationRepository.findAll()
                .get(1)
                .getCourse()
                .getTimetable(), tt1);
        assertEquals(roomCombinationRepository.findAll()
                .get(2)
                .getCourse()
                .getTimetable(), tt2);

        assertEquals(weekEventRepository.findAll()
                .get(1)
                .getTimetable(), tt1);
        assertEquals(weekEventRepository.findAll()
                .get(2)
                .getTimetable(), tt2);

        // tt2 auf korrekte Listen prüfen
        assertEquals(2, timetableRepository.findAll()
                .get(1)
                .getSpecialEvents()
                .size());
        assertNotEquals(
                timetableRepository.findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(0)
                        .getId(),
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getId());
        assertNotEquals(
                timetableRepository.findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(1)
                        .getId(),
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(1)
                        .getId());
        assertEquals(
                timetableRepository.findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(0)
                        .getEndDate(),
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(0)
                        .getEndDate());
        assertEquals(
                timetableRepository.findAll()
                        .get(0)
                        .getSpecialEvents()
                        .get(1)
                        .getEndDate(),
                timetableRepository.findAll()
                        .get(1)
                        .getSpecialEvents()
                        .get(1)
                        .getEndDate());

        // employee Listen
        assertEquals(2, employeeRepository.findAll()
                .get(2)
                .getWorkTimes()
                .size());
        assertEquals(2, employeeRepository.findAll()
                .get(3)
                .getWorkTimes()
                .size());
        assertNotEquals(
                employeeRepository.findAll()
                        .get(0)
                        .getWorkTimes()
                        .get(0)
                        .getTimeslot()
                        .getId(),
                employeeRepository.findAll()
                        .get(2)
                        .getWorkTimes()
                        .get(0)
                        .getTimeslot()
                        .getId());
        assertEquals(
                employeeRepository.findAll()
                        .get(0)
                        .getWorkTimes()
                        .get(0)
                        .getTimeslot()
                        .getIndex(),
                employeeRepository.findAll()
                        .get(2)
                        .getWorkTimes()
                        .get(0)
                        .getTimeslot()
                        .getIndex());
        // Course Listen
        // eigentlich sollte all das in vorherigen Tests schon getestet worden sein
        // ...

    }

    @Test
    void copyNothing() throws Exception {
        // Alles erstellen
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());

        RoomType rt = createRoomType();

        Room r1 = createRoom1(tt1, rt);
        Room r2 = createRoom2(tt1, rt);

        Timeslot ts1 = createTimeslot1(tt1);
        Timeslot ts2 = createTimeslot2(tt1);

        Employee e1 = createEmployee1(tt1);
        Employee e2 = createEmployee2(tt1);

        WorkTime e1ts1 = createWorkTime(e1, ts1);
        WorkTime e1ts2 = createWorkTime(e1, ts2);
        WorkTime e2ts1 = createWorkTime(e2, ts1);
        WorkTime e2ts2 = createWorkTime(e2, ts2);

        Course c1 = createCourse1(tt1);
        Course c2 = createCourse2(tt1);

        CourseRelation cr1 = createCourseRelation1(c1, c2);
        CourseRelation cr2 = createCourseRelation1(c2, c1);

        CourseTimeslot c1ts1 = createCourseTimeslot(c1, ts1);
        CourseTimeslot c1ts2 = createCourseTimeslot(c1, ts2);
        CourseTimeslot c2ts1 = createCourseTimeslot(c2, ts1);
        CourseTimeslot c2ts2 = createCourseTimeslot(c2, ts2);

        Degree d1 = createDegree1(tt1);
        Degree d2 = createDegree2(tt1);

        WeekEvent we1 = createWeekEvent(c1, tt1);
        WeekEvent we2 = createWeekEvent(c2, tt1);

        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        DegreeSemester ds2 = createDegreeSemester1(d2, tt1);

        RoomCombination rc1 = createRoomCombination(List.of(r1), c1);
        RoomCombination rc2 = createRoomCombination(List.of(r1, r2), c2);

        SpecialEvent se1 = createSpecialEvent1(tt1);
        SpecialEvent se2 = createSpecialEvent2(tt1);

        // Lists setzen
        tt1.setSpecialEvents(List.of(se1, se2));

        e1.setWorkTimes(List.of(e1ts1, e1ts2));
        e2.setWorkTimes(List.of(e2ts1, e2ts2));

        e1.setCourses(List.of(c1));
        e2.setCourses(List.of(c1, c2));

        c1.setCourseRelationsA(List.of(cr1));
        c1.setCourseRelationsB(List.of(cr2));
        c2.setCourseRelationsA(List.of(cr2));
        c2.setCourseRelationsB(List.of(cr1));

        c1.setCourseTimeslots(List.of(c1ts1, c1ts2));
        c2.setCourseTimeslots(List.of(c2ts1, c2ts2));
        c1.setLecturers(List.of(e1));
        c2.setLecturers(List.of(e1, e2));

        c1.setSuitedRooms(List.of(rc1));
        c2.setSuitedRooms(List.of(rc2));

        c1.setSemesters(List.of(ds1, ds2));
        c2.setSemesters(List.of(ds1, ds2));

        c1.setWeekEvents(List.of(we1));
        c2.setWeekEvents(List.of(we2));

        d1.setSemesters(List.of(ds1));
        d2.setSemesters(List.of(ds2));

        ds1.setCourses(List.of(c1, c2));
        ds2.setCourses(List.of(c1, c2));

        tt1.setSpecialEvents(List.of(se1, se2));

        assertEquals(2, roomRepository.findAll()
                .size());
        assertEquals(2, timeslotRepository.findAll()
                .size());
        assertEquals(2, employeeRepository.findAll()
                .size());
        assertEquals(4, workTimeRepository.findAll()
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());
        assertEquals(2, courseRelationRepository.findAll()
                .size());
        assertEquals(4, courseTimeslotRepository.findAll()
                .size());
        assertEquals(2, degreeRepository.findAll()
                .size());
        assertEquals(2, degreeSemesterRepository.findAll()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
        assertEquals(2, specialEventRepository.findAll()
                .size());
        assertEquals(2, weekEventRepository.findAll()
                .size());

        assertEquals(2, timetableRepository.findAll()
                .size());

        // no query statements
        String url = format("/v1/timetables/%s/copyfrom/%s", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        // keine neuen Einträge
        assertEquals(2, roomRepository.findAll()
                .size());
        assertEquals(2, timeslotRepository.findAll()
                .size());
        assertEquals(2, employeeRepository.findAll()
                .size());
        assertEquals(4, workTimeRepository.findAll()
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());
        assertEquals(2, courseRelationRepository.findAll()
                .size());
        assertEquals(4, courseTimeslotRepository.findAll()
                .size());
        assertEquals(2, degreeRepository.findAll()
                .size());
        assertEquals(2, degreeSemesterRepository.findAll()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
        assertEquals(2, specialEventRepository.findAll()
                .size());
        assertEquals(2, weekEventRepository.findAll()
                .size());
    }

    @Test
    void copyOnlyRoom() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        RoomType rt = createRoomType();
        createRoom1(tt1, rt);
        createEmployee1(tt1);

        String url = format("/v1/timetables/%s/copyfrom/%s?room=true", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, roomRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(1, roomRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(2, roomRepository.findAll()
                .size());
        assertEquals(1, employeeRepository.findAll()
                .size());
    }

    @Test
    void copyEmployeeWithWorktimes() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Employee e1 = createEmployee1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        Timeslot ts2 = createTimeslot2(tt1);
        createWorkTime(e1, ts1);
        createWorkTime(e1, ts2);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?employee=true&timeslot=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(4, workTimeRepository.findAll()
                .size());
    }

    @Test
    void copyEmployeeWithoutWorktimes_BecauseTimeslotsNotCopied() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Employee e1 = createEmployee1(tt1);
        Timeslot ts1 = createTimeslot1(tt1);
        Timeslot ts2 = createTimeslot2(tt1);
        createWorkTime(e1, ts1);
        createWorkTime(e1, ts2);

        // timeslots werden nicht kopiert -> Worktimes können somit nicht kopiert werden
        String url =
                format("/v1/timetables/%s/copyfrom/%s?employee=true", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, workTimeRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyEmployeeWithCourse() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);
        e1.setCourses(List.of(c1));

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?employee=1&course=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, employeeRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, employeeRepository.findByTimetable(tt2)
                .size());

        assertEquals(1, employeeRepository.findByTimetable(tt1)
                .get(0)
                .getCourses()
                .size());
        assertEquals(1, employeeRepository.findByTimetable(tt2)
                .get(0)
                .getCourses()
                .size());
    }

    @Test
    @Transactional
    void copyEmployeeWithoutCourse() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);
        e1.setCourses(List.of(c1));

        String url = format("/v1/timetables/%s/copyfrom/%s?employee=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, employeeRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, employeeRepository.findByTimetable(tt2)
                .size());

        assertEquals(1, employeeRepository.findByTimetable(tt1)
                .get(0)
                .getCourses()
                .size());
        assertEquals(0, employeeRepository.findByTimetable(tt2)
                .get(0)
                .getCourses()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithoutEmployee() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);

        String url = format("/v1/timetables/%s/copyfrom/%s?course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        List<Employee> eList1 = courseRepository.findAll()
                .get(0)
                .getLecturers();
        List<Employee> eList2 = courseRepository.findAll()
                .get(1)
                .getLecturers();

        assertNotNull(eList1.get(0));
        assertEquals(0, eList2.size());
    }

    @Test
    @Transactional
    void copyCourseWithEmployee() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Employee e1 = createEmployee1(tt1);
        c1.getLecturers()
                .add(e1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&employee=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        List<Employee> eList1 = courseRepository.findAll()
                .get(0)
                .getLecturers();
        List<Employee> eList2 = courseRepository.findAll()
                .get(1)
                .getLecturers();

        assertNotNull(eList1.get(0));
        assertEquals(1, eList2.size());
    }

    @Test
    @Transactional
    void copyCourseWithWeekEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        WeekEvent w1 = createWeekEvent(c1, tt1);
        c1.setWeekEvents(List.of(w1));

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&weekevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        assertEquals(1, courseRepository.findByTimetable(tt2)
                .get(0)
                .getWeekEvents()
                .size());
        assertEquals(2, weekEventRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithoutWeekEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        WeekEvent w1 = createWeekEvent(c1, tt1);
        c1.setWeekEvents(List.of(w1));

        String url = format("/v1/timetables/%s/copyfrom/%s?course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, courseRepository.findAll()
                .size());

        assertEquals(0, courseRepository.findByTimetable(tt2)
                .get(0)
                .getWeekEvents()
                .size());
        assertEquals(1, weekEventRepository.findAll()
                .size());
    }

    @Test
    @Transactional
    void copyCourseAndRoomCombination() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        RoomType rt = createRoomType();
        Room r1 = createRoom1(tt1, rt);
        RoomCombination rc1 = createRoomCombination(List.of(r1), c1);
        c1.getSuitedRooms()
                .add(rc1);

        String url =
                format("/v1/timetables/%s/copyfrom/%s?room=1&course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .get(0)
                .getSuitedRooms()
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .get(0)
                .getSuitedRooms()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithoutRoomCombination() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        RoomType rt = createRoomType();
        Room r1 = createRoom1(tt1, rt);
        RoomCombination rc1 = createRoomCombination(List.of(r1), c1);
        c1.getSuitedRooms()
                .add(rc1);

        String url = format("/v1/timetables/%s/copyfrom/%s?course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, roomCombinationRepository.findAll()
                .size());
        assertEquals(0, courseRepository.findByTimetable(tt2)
                .get(0)
                .getSuitedRooms()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithCourseTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Timeslot t1 = createTimeslot1(tt1);
        CourseTimeslot ct1 = createCourseTimeslot(c1, t1);
        c1.setCourseTimeslots(List.of(ct1));

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&timeslot=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .get(0)
                .getCourseTimeslots()
                .size());
        assertEquals(1, courseRepository.findByTimetable(tt2)
                .get(0)
                .getCourseTimeslots()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithoutCourseTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Timeslot t1 = createTimeslot1(tt1);
        CourseTimeslot ct1 = createCourseTimeslot(c1, t1);
        c1.setCourseTimeslots(List.of(ct1));

        String url = format("/v1/timetables/%s/copyfrom/%s?course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt1)
                .get(0)
                .getCourseTimeslots()
                .size());
        assertEquals(0, courseRepository.findByTimetable(tt2)
                .get(0)
                .getCourseTimeslots()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        c1.setSemesters(List.of(ds1));
        ds1.getCourses()
                .add(c1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&degreesemester=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(2, degreeSemesterRepository.findAll()
                .size());

        assertEquals(1, courseRepository.findByTimetable(tt2)
                .get(0)
                .getSemesters()
                .size());
    }

    @Test
    @Transactional
    void copyCourseWithoutDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        c1.setSemesters(List.of(ds1));
        ds1.getCourses()
                .add(c1);

        String url = format("/v1/timetables/%s/copyfrom/%s?course=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, courseRepository.findByTimetable(tt2)
                .size());
        assertEquals(1, degreeSemesterRepository.findAll()
                .size());

        assertEquals(0, courseRepository.findByTimetable(tt2)
                .get(0)
                .getSemesters()
                .size());
    }

    @Test
    @Transactional
    void copyDegreeSemesterWithDegree() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.setSemesters(List.of(ds1));

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?degree=1&degreesemester=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeSemesterRepository.findByTimetable(tt2)
                .size());

        assertNotNull(degreeSemesterRepository.findByTimetable(tt2)
                .get(0)
                .getDegree());
    }

    @Test
    @Transactional
    void copyDegreeSemesterWithoutDegree() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.setSemesters(List.of(ds1));

        String url =
                format("/v1/timetables/%s/copyfrom/%s?degreesemester=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeSemesterRepository.findByTimetable(tt2)
                .size());

        assertNull(degreeSemesterRepository.findByTimetable(tt2)
                .get(0)
                .getDegree());
    }

    @Test
    @Transactional
    void copyDegreeWithDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.setSemesters(List.of(ds1));

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?degree=1&degreesemester=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeRepository.findByTimetable(tt2)
                .size());

        assertEquals(1, degreeRepository.findByTimetable(tt2)
                .get(0)
                .getSemesters()
                .size());
    }

    @Test
    @Transactional
    void copyDegreeWithoutDegreeSemester() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        d1.setSemesters(List.of(ds1));

        String url = format("/v1/timetables/%s/copyfrom/%s?degree=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeRepository.findByTimetable(tt2)
                .size());

        assertEquals(0, degreeRepository.findByTimetable(tt2)
                .get(0)
                .getSemesters()
                .size());
    }

    @Test
    @Transactional
    void copyDegreeSemesterWithCourse() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        c1.setSemesters(List.of(ds1));
        ds1.getCourses()
                .add(c1);

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&degreesemester=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeSemesterRepository.findByTimetable(tt2)
                .size());

        assertEquals(1, degreeSemesterRepository.findByTimetable(tt2)
                .get(0)
                .getCourses()
                .size());
    }

    @Test
    @Transactional
    void copyDegreeSemesterWithoutCourse() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        Course c1 = createCourse1(tt1);
        Degree d1 = createDegree1(tt1);
        DegreeSemester ds1 = createDegreeSemester1(d1, tt1);
        c1.setSemesters(List.of(ds1));
        ds1.getCourses()
                .add(c1);

        String url =
                format("/v1/timetables/%s/copyfrom/%s?degreesemester=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, degreeSemesterRepository.findByTimetable(tt2)
                .size());

        assertEquals(0, degreeSemesterRepository.findByTimetable(tt2)
                .get(0)
                .getCourses()
                .size());
    }

    @Test
    @Transactional
    void copyWeekEventWithRoom() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        RoomType rt1 = createRoomType();
        Room r1 = createRoom1(tt1, rt1);
        Timeslot t1 = createTimeslot1(tt1);
        Course c1 = createCourse1(tt1);
        WeekEvent we1 = createWeekEvent(c1, tt1);
        we1.setRooms(List.of(r1));
        we1.setTimeslots(List.of(t1));

        assertEquals(1, weekEventRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt1.getId())
                        .get(0)
                        .getRooms()
                        .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?room=1&course=1&weekevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, weekEventRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt2.getId())
                        .get(0)
                        .getRooms()
                        .size());
    }

    @Test
    @Transactional
    void copyWeekEventWithoutRoom() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        RoomType rt1 = createRoomType();
        Room r1 = createRoom1(tt1, rt1);
        Timeslot t1 = createTimeslot1(tt1);
        Course c1 = createCourse1(tt1);
        WeekEvent we1 = createWeekEvent(c1, tt1);
        we1.setRooms(List.of(r1));
        we1.setTimeslots(List.of(t1));

        assertEquals(1, weekEventRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt1.getId())
                        .get(0)
                        .getRooms()
                        .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&weekevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, weekEventRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(
                0, weekEventRepository.findByTimetableId(tt2.getId())
                        .get(0)
                        .getRooms()
                        .size());
    }

    @Test
    @Transactional
    void copyWeekEventWithTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        RoomType rt1 = createRoomType();
        Room r1 = createRoom1(tt1, rt1);
        Timeslot t1 = createTimeslot1(tt1);
        Course c1 = createCourse1(tt1);
        WeekEvent we1 = createWeekEvent(c1, tt1);
        we1.setRooms(List.of(r1));
        we1.setTimeslots(List.of(t1));

        assertEquals(1, weekEventRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt1.getId())
                        .get(0)
                        .getTimeslots()
                        .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?timeslot=1&course=1&weekevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, weekEventRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt2.getId())
                        .get(0)
                        .getTimeslots()
                        .size());
    }

    @Test
    @Transactional
    void copyWeekEventWithoutTimeslot() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        RoomType rt1 = createRoomType();
        Room r1 = createRoom1(tt1, rt1);
        Timeslot t1 = createTimeslot1(tt1);
        Course c1 = createCourse1(tt1);
        WeekEvent we1 = createWeekEvent(c1, tt1);
        we1.setRooms(List.of(r1));
        we1.setTimeslots(List.of(t1));

        assertEquals(1, weekEventRepository.findByTimetableId(tt1.getId())
                .size());
        assertEquals(
                1, weekEventRepository.findByTimetableId(tt1.getId())
                        .get(0)
                        .getTimeslots()
                        .size());

        String url =
                format(
                        "/v1/timetables/%s/copyfrom/%s?course=1&weekevent=1",
                        tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, weekEventRepository.findByTimetableId(tt2.getId())
                .size());
        assertEquals(
                0, weekEventRepository.findByTimetableId(tt2.getId())
                        .get(0)
                        .getTimeslots()
                        .size());
    }

    @Test
    @Transactional
    void copyOnlySpecialEvent() throws Exception {
        Timetable tt1 = persistTimetable(TestDataUtil.createTimetableWS22());
        Timetable tt2 = persistTimetable(TestDataUtil.createTimetableSS23());
        // auf TT1 setzen
        createSpecialEvent1(tt1);

        assertEquals(1, specialEventRepository.findByTimetableId(tt1.getId())
                .size());

        String url =
                format("/v1/timetables/%s/copyfrom/%s?specialevent=1", tt2.getId(), tt1.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, null);

        assertEquals(1, specialEventRepository.findByTimetableId(tt2.getId())
                .size());
    }

    private RoomType createRoomType() {
        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("test");
        rt = roomTypeRepository.saveAndFlush(rt);
        return rt;
    }

    private Room createRoom1(Timetable timetable, RoomType rt) {
        Room room = new Room();
        room.setTimetable(timetable);
        room.setCapacity(100);
        room.setIdentifier("C.01");
        room.setAbbreviation("HS1");
        room.setName("Hörsaal 1");
        room.setRoomType(rt);
        roomRepository.saveAndFlush(room);
        return room;
    }

    private Room createRoom2(Timetable timetable, RoomType rt) {
        Room room = new Room();
        room.setTimetable(timetable);
        room.setCapacity(100);
        room.setIdentifier("C.02");
        room.setAbbreviation("HS2");
        room.setName("Hörsaal 2");
        room.setRoomType(rt);
        roomRepository.saveAndFlush(room);
        return room;
    }

    private Timeslot createTimeslot1(Timetable timetable) {
        Timeslot ts = new Timeslot();
        ts.setTimetable(timetable);
        ts.setEndTime(LocalTime.parse("10:30"));
        ts.setStartTime(LocalTime.parse("08:30"));
        ts.setIndex(1);
        timeslotRepository.saveAndFlush(ts);
        return ts;
    }

    private Timeslot createTimeslot2(Timetable timetable) {
        Timeslot ts = new Timeslot();
        ts.setTimetable(timetable);
        ts.setEndTime(LocalTime.parse("19:30"));
        ts.setStartTime(LocalTime.parse("12:30"));
        ts.setIndex(10);
        timeslotRepository.saveAndFlush(ts);
        return ts;
    }

    private Employee createEmployee1(Timetable timetable) {
        Employee employee = new Employee();
        employee.setFirstname("Brian W.");
        employee.setLastname("Kernighan");
        employee.setAbbreviation("bwk");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    private Employee createEmployee2(Timetable timetable) {
        Employee employee = new Employee();
        employee.setFirstname("Max");
        employee.setLastname("Muster");
        employee.setAbbreviation("mmu");
        employee.setEmployeeType(createAndPersistEmployeeTypeTwo());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    private WorkTime createWorkTime(Employee employee, Timeslot timeslot) {
        WorkTime workTime = new WorkTime();
        workTime.setWeekday(DayOfWeek.SATURDAY);
        workTime.setEmployee(employee);
        workTime.setTimeslot(timeslot);
        workTimeRepository.saveAndFlush(workTime);
        return workTime;
    }

    private Course createCourse1(Timetable timetable) {
        Course course = new Course();
        course.setTimetable(timetable);
        course.setCasID("WS22C003");
        course.setName("Woolly Thinking");
        course.setAbbreviation("WT");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        courseRepository.saveAndFlush(course);
        return course;
    }

    private Course createCourse2(Timetable timetable) {
        Course course = new Course();
        course.setTimetable(timetable);
        course.setCasID("WS22C004");
        course.setName("Head-Bashing");
        course.setAbbreviation("HB");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        courseRepository.saveAndFlush(course);
        return course;
    }

    private CourseRelation createCourseRelation1(Course c1, Course c2) {
        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setCourseA(c1);
        courseRelation.setCourseB(c2);
        courseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationRepository.saveAndFlush(courseRelation);
        return courseRelation;
    }

    private CourseTimeslot createCourseTimeslot(Course c, Timeslot ts) {
        CourseTimeslot cts = new CourseTimeslot();
        cts.setTimeslot(ts);
        cts.setCourse(c);
        cts.setWeekday(DayOfWeek.WEDNESDAY);
        courseTimeslotRepository.saveAndFlush(cts);
        return cts;
    }

    private WeekEvent createWeekEvent(Course course, Timetable timetable) {
        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setWeekday(DayOfWeek.MONDAY);
        weekEvent.setWeek(0);
        weekEvent.setTimetable(timetable);
        weekEvent.setCourse(course);

        weekEvent.setRooms(new ArrayList<>());
        weekEvent.setTimeslots(new ArrayList<>());
        weekEventRepository.saveAndFlush(weekEvent);
        return weekEvent;
    }

    private Degree createDegree1(Timetable timetable) {
        Degree degree = new Degree();
        degree.setName("degree");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);
        degreeRepository.saveAndFlush(degree);
        return degree;
    }

    private Degree createDegree2(Timetable timetable) {
        Degree degree = new Degree();
        degree.setName("degreeTWO");
        degree.setSchoolType(null);
        degree.setStudyRegulation("20.0");
        degree.setShortName("testTWO");
        degree.setSemesterAmount(6);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);
        degreeRepository.saveAndFlush(degree);
        return degree;
    }

    private DegreeSemester createDegreeSemester1(Degree degree, Timetable timetable) {
        DegreeSemester semester = new DegreeSemester();
        semester.setDegree(degree);
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setCourses(new ArrayList<>());
        semester.setTimetable(timetable);
        degreeSemesterRepository.saveAndFlush(semester);
        return semester;
    }

    private RoomCombination createRoomCombination(List<Room> rList, Course c) {
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(rList);
        roomCombination.setCourse(c);
        roomCombinationRepository.saveAndFlush(roomCombination);
        return roomCombination;
    }

    private SpecialEvent createSpecialEvent1(Timetable timetable) {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.MONDAY_PLAN);
        specialEvent.setTimetable(timetable);
        specialEvent.setStartDate(LocalDate.of(2023, 5, 31));
        specialEvent.setEndDate(LocalDate.of(2023, 8, 31));
        specialEventRepository.saveAndFlush(specialEvent);
        return specialEvent;
    }

    private SpecialEvent createSpecialEvent2(Timetable timetable) {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.TUESDAY_PLAN);
        specialEvent.setTimetable(timetable);
        specialEvent.setStartDate(LocalDate.of(2029, 5, 31));
        specialEvent.setEndDate(LocalDate.of(2029, 8, 31));
        specialEventRepository.saveAndFlush(specialEvent);
        return specialEvent;
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }

    private EmployeeType createAndPersistEmployeeType() {
        EmployeeType employeeType = TestDataUtil.createEmployeeTypeDozent();
        return employeeTypeRepository.saveAndFlush(employeeType);
    }

    private EmployeeType createAndPersistEmployeeTypeTwo() {
        EmployeeType employeeType = TestDataUtil.createEmployeeTypeAssistent();
        return employeeTypeRepository.saveAndFlush(employeeType);
    }
}
