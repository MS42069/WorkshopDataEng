package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.CourseController;
import com.fhwedel.softwareprojekt.v1.controller.timetable.WeekEventController;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseToPlanResDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.SchedulingResultDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventReqDTO;
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
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseTimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.addDegreeSemesterToCourse;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.addLecturerToCourse;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourse;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseRelation;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseTimeslot;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createDegreeOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createDegreeSemester;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createDegreeSemesterFor;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployee;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployeeOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployeeTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployeeTypeDozent;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomComboFor;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomFour;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomThree;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomTypeOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createRoomTypeTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createTimetableWS22;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createWeekEvent;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createWorkTimeFor;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.generateCourseTimeslots;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.generateTimeslots;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.generateWorkTimes;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.groupOptionsByDayOfWeek;
import static java.lang.String.format;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests that focus on generating admissible options for the scheduling of events, more
 * precisely testing the endpoint provided by {@link WeekEventController#getEventOptions}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class SchedulerOptionsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    @Autowired
    private WeekEventRepository weekEventRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private WorkTimeRepository workTimeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DegreeRepository degreeRepository;
    @Autowired
    private DegreeSemesterRepository degreeSemesterRepository;
    @Autowired
    private CourseTimeslotRepository courseTimeslotRepository;
    @Autowired
    private CourseRelationRepository courseRelationRepository;

    /**
     * Autowired EntityManagerFactory to obtain EntityManagers in order to facilitate persisting of
     * entities
     */
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void closeDBConnection() {
        em.close();
    }

    @AfterEach
    void rollbackDB() {
        weekEventRepository.deleteAll();
        workTimeRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        courseTimeslotRepository.deleteAll();
        courseRelationRepository.deleteAll();
        courseRepository.deleteAll();
        degreeRepository.deleteAll();
        employeeRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    /**
     * Test case: Tests the restriction of options by the potential timeslots at which a course can
     * take place.
     */
    @Test
    public void givenCourseTimeslots_whenGetEventOptions_thenReturn1Option() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        RoomType rt = createRoomTypeOne();

        Room r1 = createRoomOne(timetable, rt);

        // restrict course by potential timeslots
        CourseTimeslot c1Ts = createCourseTimeslot(c1, MONDAY, t2);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemesterFor(d1);
        addDegreeSemesterToCourse(d1s1, c1);

        Employee e1 = createEmployeeOne(timetable);

        // Based on the working hours, there would be three options
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t3);

        c1.getLecturers()
                .add(e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(rt);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(c1Ts);
        em.persist(r1);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2, workTimeE1MoT3));
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] result = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        // then
        assertEquals(1, result.length);
        assertEquals(c1.getId(), result[0].getCourse());

        Set<OptionDTO> expOptionsForC1 =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r1.getId()))));
        assertEquals(expOptionsForC1, new HashSet<>(result[0].getOptions()));
        assertEquals(12, result[0].getDegreeOfFreedom());

        em.close();
    }

    /**
     * Tests for {@link WeekEventController#getEventOptions}:
     *
     * <p>Test case: Tests the restriction of options by an employee's working times
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Employees: E1
     *   - 3 Timeslots: T1, T2, T3
     *   - 1 Room: R1
     *   - 1 Degree: D1
     *       and 1 Degree Semester: D11
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: [D1S1]
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employee:
     *      E1:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 1, 0
     *  Expected options:
     *          course: C1
     *          degreeOfFreedom: 1
     *          options: [(Mon, T2, R1)]
     * </pre>
     */
    @Test
    public void given1Course1Employee1WorkTime_whenGetEventOptions_thenReturn1Options()
            throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        RoomType rt = createRoomTypeOne();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemesterFor(d1);
        addDegreeSemesterToCourse(d1s1, c1);

        Employee e1 = createEmployeeOne(timetable);
        Timeslot t2 = tss.get(1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t2);

        c1.getLecturers()
                .add(e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(workTimeE1MoT2);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] result = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        // then
        assertEquals(1, result.length);
        assertEquals(c1.getId(), result[0].getCourse());

        Set<OptionDTO> expOptionsForC1 =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r1.getId()))));
        assertEquals(expOptionsForC1, new HashSet<>(result[0].getOptions()));
        assertEquals(12, result[0].getDegreeOfFreedom());

        em.close();
    }

    /**
     * Tests for {@link WeekEventController#getEventOptions}:
     *
     * <p>Test case: Tests the restriction of options for course by multiple employee's working
     * times, no option possible
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Employees: E1, E2
     *   - 3 Timeslots: T1, T2, T3
     *   - 1 Room: R1
     *   - 1 Degree: D1
     *       and 1 Degree Semester: D11
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1, E2]
     *          semesters: [D1S1]
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employees:
     *      E1:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 1, 0
     *      E2:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 0, 1
     *  Expected options:
     *          course: C1
     *          degreeOfFreedom: 0
     *          options: []
     * </pre>
     */
    @Test
    public void given1Course2EmployeeDifferentWorkTimes_whenGetEventOptions_thenReturnNoOptions()
            throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemesterFor(d1);

        Employee e1 = createEmployee(timetable, 1);
        DayOfWeek mo = DayOfWeek.MONDAY;
        Timeslot t2 = tss.get(1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        Employee e2 = createEmployeeTwo(timetable, 2);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE2MoT3 = createWorkTimeFor(e2, mo, t3);

        c1.getLecturers()
                .addAll(List.of(e1, e2));
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(e1.getEmployeeType());
        em.persist(e2.getEmployeeType());
        em.persist(e1);
        em.persist(e2);
        em.persist(workTimeE1MoT2);
        em.persist(workTimeE2MoT3);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] result = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        // then
        assertEquals(1, result.length);
        assertEquals(c1.getId(), result[0].getCourse());

        Set<OptionDTO> expOptionsForC1 = new HashSet<>(List.of());
        assertEquals(expOptionsForC1, new HashSet<>(result[0].getOptions()));
        assertEquals(0, result[0].getDegreeOfFreedom());
    }

    @Test
    public void getOptionsMultipleEmployeesDifferentAmountOfOptions() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        createRoomTwo(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        Employee e2 = createEmployee(timetable, 2);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE2MoT2 = createWorkTimeFor(e2, mo, t2);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE2MoT2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e2.getEmployeeType());
        em.persist(e1);
        em.persist(e2);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId(), c2.getId()), Set.of(1));
        assertEquals(
                24, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        assertEquals(
                12, optionsBeforePost[1].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
    }

    /**
     * Test case: Tests that after scheduling an event for a particular option, the option is
     * removed accordingly
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Employees: E1
     *   - 1 Timeslots: T1
     *   - 1 Room: R1
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: []
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employee:
     *      E1:
     *          Day\Ts T1
     *          Mon    1
     *  Expected options:
     *          course: C1
     *          degreeOfFreedom: 1
     *          options: [(Mon, T1, R1)]
     *  Expected options after scheduling:
     *          course: C1
     *          degreeOfFreedom: 0
     *          options: []
     * </pre>
     */
    @Test
    public void whenPostWeekEvent_thenRemoveOption() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);

        Timeslot t1 = tss.get(0);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(workTimeE1MoT1);
        em.persist(ComboR1);
        em.flush();

        em.getTransaction()
                .commit();

        // validate options before post
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        OptionDTO expOptionBeforePost =
                OptionDTO.builder()
                        .weekday(MONDAY)
                        .week(1)
                        .timeslots(List.of(t1.getId()))
                        .rooms(List.of(r1.getId()))
                        .build();
        assertEquals(
                12, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        Set<OptionDTO> setOfOptionsBeforePost = new HashSet<>(optionsBeforePost[0].getOptions());
        assertEquals(Set.of(expOptionBeforePost), setOfOptionsBeforePost);

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                0, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(0, optionsAfterPost[0].getOptions()
                .size());
    }

    /**
     * Tests that the correct Freedom of Degree is returned when a request is made to the endpoint
     * handled by {@link CourseController#getAllCoursesToPlan}
     *
     * @throws Exception exception
     */
    @Test
    public void whenGetAllCoursesToPlan_thenReturnCorrectFreedomOfDegree() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);

        Timeslot t1 = tss.get(0);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(workTimeE1MoT1);
        em.persist(ComboR1);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        String url = format("/v1/timetable/%s/courses/toPlan", timetable.getId());
        CourseToPlanResDTO[] response = mockMvcTestUtil.get(url, CourseToPlanResDTO[].class);

        assertEquals(1, response.length);
        assertEquals(c1.getId(), response[0].getId());
        assertEquals(
                12,
                response[0].getDegreeOfFreedom(),
                "Expect one degree of freedom, because c1 can only be scheduled (Mo, t1, r1)");
    }

    /**
     * Test case: Test that when an event is scheduled for a specific room, all options that include
     * that room are removed for other courses
     */
    @Test
    public void
    givenCourseWithRoomComboR1R2_whenGetOptionsAfterSchedulingEventForR1_thenRemoveOptionContainingR1()
            throws Exception {
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);

        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("test");

        List<CourseTimeslot> c1Tss =
                generateCourseTimeslots(c1, List.of(DayOfWeek.values()), List.of(t1));
        List<CourseTimeslot> c2Tss =
                generateCourseTimeslots(c2, List.of(DayOfWeek.values()), List.of(t1));

        Employee e1 = createEmployee(timetable, 1);
        Employee e2 = createEmployeeTwo(timetable, 2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);
        WorkTime workTimeE2MoT1 = createWorkTimeFor(e2, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);

        // c1 may take place in r1, but c2 can only take place in r1 AND r2
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination ComboR1R2 = createRoomComboFor(c2, List.of(r1, r2));
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(rt);
        persistAllEntities(em, tss);
        persistAllEntities(em, List.of(c1, c2));
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        persistAllEntities(em, List.of(r1, r2));
        persistAllEntities(em, List.of(ComboR1, ComboR1R2));
        em.persist(e1.getEmployeeType());
        em.persist(e2.getEmployeeType());
        persistAllEntities(em, List.of(e1, e2));
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE2MoT1));
        em.flush();

        em.getTransaction()
                .commit();

        // when
        // Initialize Options
        OptionsDTO[] optionsC1BeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        OptionDTO expOptionsC1BeforePost =
                OptionDTO.builder()
                        .weekday(MONDAY)
                        .week(1)
                        .timeslots(List.of(t1.getId()))
                        .rooms(List.of(r1.getId()))
                        .build();
        assertEquals(
                new HashSet<>(List.of(expOptionsC1BeforePost)),
                new HashSet<>(optionsC1BeforePost[0].getOptions()));
        assertEquals(12, optionsC1BeforePost[0].getDegreeOfFreedom());

        OptionsDTO[] optionsC2BeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));

        OptionDTO expOptionC2BeforePost =
                OptionDTO.builder()
                        .weekday(MONDAY)
                        .week(1)
                        .timeslots(List.of(t1.getId()))
                        .rooms(List.of(r1.getId(), r2.getId()))
                        .build();
        assertOptionDTO(expOptionC2BeforePost, optionsC2BeforePost[0].getOptions()
                .get(0));
        assertEquals(12, optionsC2BeforePost[0].getDegreeOfFreedom());

        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        // schedule
        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsC1AfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        List<OptionDTO> expOptionsForC1 =
                new ArrayList<>(
                        List.of(
                                new OptionDTO(
                                        1, MONDAY, List.of(t1.getId()), List.of(r1.getId()))));
        assertEquals(expOptionsForC1, optionsC1AfterPost[0].getOptions());
        assertEquals(12, optionsC1AfterPost[0].getDegreeOfFreedom());

        List<OptionDTO> expOptionsForC2 =
                new ArrayList<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId()),
                                        List.of(r2.getId(), r1.getId()))));
        OptionsDTO[] optionsC2AfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));
        assertEquals(expOptionsForC2, optionsC2AfterPost[0].getOptions());
        assertEquals(12, optionsC2AfterPost[0].getDegreeOfFreedom());
    }

    @Test
    public void
    givenCourse_whenGetAllCoursesToPlanAndSchedulerNotInitializedForCourse_thenNegativeFreedomOfDegree()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        em.persist(timetable.getSemesterType());
        em.persist(timetable);

        em.getTransaction()
                .commit();

        // trigger the initialization of the scheduler
        String url = format("/v1/timetable/%s/courses/toPlan", timetable.getId());
        CourseToPlanResDTO[] firstResponse = mockMvcTestUtil.get(url, CourseToPlanResDTO[].class);
        assertEquals(0, firstResponse.length);

        // persist given course
        em.getTransaction()
                .begin();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);

        Timeslot t1 = tss.get(0);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);

        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(workTimeE1MoT1);
        em.persist(ComboR1);
        em.flush();
        em.getTransaction()
                .commit();

        // when
        CourseToPlanResDTO[] secondResponse = mockMvcTestUtil.get(url, CourseToPlanResDTO[].class);

        // then
        assertEquals(1, secondResponse.length);
        assertEquals(-1, secondResponse[0].getDegreeOfFreedom());
    }

    /**
     * Tests for {@link WeekEventController#getEventOptions}:
     *
     * <p>Test case: Tests that an option is generated for each available room combination
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Timeslots: T1
     *   - 1 Room: R1, R2, R3
     *   - 2 RoomCombination: [R1], [R2, R3]
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: [D1S1]
     *          suitedRooms: [[R1], [R2, R3]
     *
     *  Working times of the employee:
     *      E1:
     *          Day\Ts T1 T2 T3
     *          Mon    0, 1, 0
     *  Expected options:
     *          course: C1
     *          degreeOfFreedom: 1
     *          options: [(Mon, T2, R1)]
     * </pre>
     */
    @Test
    public void
    given1CourseWithMultipleRoomCombos_whenGetEventOptions_thenReturnOptionForEachRoomCombo()
            throws Exception {
        // given

        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);
        RoomType rt = createRoomTypeOne();

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);
        Room r4 = createRoomFour(timetable, rt);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination ComboR2R3 = createRoomComboFor(c1, List.of(r2, r3));
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, List.of(t1));
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        persistAllEntities(em, List.of(r1, r2, r3, r4));
        em.persist(ComboR1);
        em.persist(ComboR2R3);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] result = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        // then
        assertEquals(1, result.length);
        assertEquals(c1.getId(), result[0].getCourse());

        Set<OptionDTO> expOptionsForC1 =
                new HashSet<>(
                        List.of(
                                new OptionDTO(1, MONDAY, List.of(t1.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId()),
                                        Stream.of(r2.getId(), r3.getId())
                                                .sorted(UUID::compareTo)
                                                .toList())));
        List<OptionDTO> actualOptionsForC1 = result[0].getOptions();

        // validate the result for Monday
        Map<DayOfWeek, List<OptionDTO>> actualOptionsByDayOfWeek =
                groupOptionsByDayOfWeek(actualOptionsForC1);

        assertEquals(
                expOptionsForC1,
                new HashSet<>(actualOptionsByDayOfWeek.get(MONDAY)),
                "expected size: "
                        + expOptionsForC1.size()
                        + ", actual size: "
                        + actualOptionsByDayOfWeek.get(MONDAY)
                        .size());
        // validate result for all weekdays, on each day t1 should be an option
        assertEquals(84, result[0].getDegreeOfFreedom());
        assertEquals(14, result[0].getOptions()
                .size());
    }

    /**
     * Test case: Tests the restriction of options for a course by participating subject semester
     * (that no course of the subject semester overlap).
     */
    @Test
    public void given2Course1Event1DegreeSemester_whenGetEventOptions_thenReturnNoOptionsForMonday()
            throws Exception {
        // given

        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);
        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);
        RoomType rt = createRoomTypeOne();

        Room r1 = createRoomOne(timetable, rt);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemesterFor(d1);
        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);

        WeekEvent givenEvent = createWeekEvent(timetable, 0, MONDAY, c2, List.of(r1), List.of(t1));
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, List.of(t1));
        em.persist(rt);
        persistAllEntities(em, List.of(r1));
        persistAllEntities(em, c1Tss);
        em.persist(ComboR1);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(givenEvent);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] result = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        // then
        assertEquals(1, result.length);

        List<OptionDTO> actualOptionsForC1 = result[0].getOptions();
        // validate the result for Monday
        Map<DayOfWeek, List<OptionDTO>> actualOptionsByDayOfWeek =
                groupOptionsByDayOfWeek(actualOptionsForC1);

        List<OptionDTO> expOptionsForC1 =
                new ArrayList<>(
                        List.of(
                                new OptionDTO(
                                        1, MONDAY, List.of(t1.getId()), List.of(r1.getId()))));

        assertEquals(expOptionsForC1, actualOptionsByDayOfWeek.get(MONDAY));

        assertEquals(7, actualOptionsForC1.size());
    }

    /**
     * Test case: Tests that after scheduling an event for a particular option, the option is
     * removed accordingly, Variation for three timeslots (3 -> 2 Optionen)
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Employees: E1
     *   - 3 Timeslots: T1,T2,T3
     *   - 1 Room: R1
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: []
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employee:
     *      E1:
     *          Day\Ts T1,T2,T3
     *          Mon    1
     *  Expected options:
     *          course: C1
     *          degreeOfFreedom: 3
     *          options: [(Mon, T1, R1),(Mon, T2, R1),(Mon, T3, R1)]
     *  Expected options after scheduling:
     *          course: C1
     *          degreeOfFreedom: 2
     *          options: [(Mon, T2, R1),(Mon, T3, R1)]
     * </pre>
     */
    @Test
    public void given3Timeslots_3DoF_andThenRemoveOption() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void whenNoMatchingTimeslots() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                24, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventReqDTO))
                .andExpect(status().isOk());

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                24, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(2, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void
    givenCourseBlockSize2And3Timeslots_whenScheduleEvent_thenCorrectDecreaseOptionsToZero()
            throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(2);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        Set<OptionDTO> expOptionsBeforePost =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t1.getId(), t2.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId(), t3.getId()),
                                        List.of(r1.getId()))));
        assertEquals(
                2, optionsBeforePost[0].getOptions()
                        .size(), Arrays.toString(optionsBeforePost));
        assertEquals(expOptionsBeforePost, new HashSet<>(optionsBeforePost[0].getOptions()));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        // 2 Timeslots selected
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(2, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void when2GetOptionRequests_scheduleAfterEachOther() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        OptionsDTO[] optionsBeforePost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36,
                optionsBeforePost2[0].getDegreeOfFreedom(),
                Arrays.toString(optionsBeforePost2));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        WeekEventReqDTO eventReqDTO2 =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO2);

        OptionsDTO[] optionsAfterPost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost2[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost2[0].getOptions()
                .size());
    }

    @Test
    public void whenPost2WeekEvents_thenReduceDoFIncrementallyBy1() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        WeekEventReqDTO eventReqDTO2 =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO2);

        OptionsDTO[] optionsAfterPost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost2[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost2[0].getOptions()
                .size());
    }

    @Test
    public void when2CoursesAndPost2WeekEvents() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId(), c2.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        assertEquals(
                36, optionsBeforePost[1].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        WeekEventReqDTO eventReqDTO2 =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId(), c2.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());
        assertEquals(
                36, optionsAfterPost[1].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[1].getOptions()
                .size());

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO2);

        OptionsDTO[] optionsAfterPost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId(), c2.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost2[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost2));
        assertEquals(3, optionsAfterPost2[0].getOptions()
                .size());
        assertEquals(
                36, optionsAfterPost2[1].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost2));
        assertEquals(3, optionsAfterPost2[1].getOptions()
                .size());
    }

    @Test
    public void getOptionsAfterDelete() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        SchedulingResultDTO resultDTO = mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());

        // WeekEvent aus DB löschen
        String url =
                format(
                        "/v1/timetable/%s/week-events/%s",
                        timetable.getId(), resultDTO.getEvents()[0].getId());
        mockMvc.perform(MockMvcTestUtil.delete(url))
                .andExpect(status().isNoContent());

        // Es sollte wieder 3 Optionen geben
        OptionsDTO[] optionsAfterPost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost2[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost2));
        assertEquals(3, optionsAfterPost2[0].getOptions()
                .size());
    }

    @Test
    public void getOptionsAfterDelete_ThenPostAgain() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        SchedulingResultDTO resultDTO = mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(3, optionsAfterPost[0].getOptions()
                .size());

        // WeekEvent aus DB löschen
        String url =
                format(
                        "/v1/timetable/%s/week-events/%s",
                        timetable.getId(), resultDTO.getEvents()[0].getId());
        mockMvc.perform(MockMvcTestUtil.delete(url))
                .andExpect(status().isNoContent());

        // Es sollte wieder 3 Optionen geben
        OptionsDTO[] optionsAfterPost2 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost2[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost2));
        assertEquals(3, optionsAfterPost2[0].getOptions()
                .size());

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost3 =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                36, optionsAfterPost3[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost3));
        assertEquals(3, optionsAfterPost3[0].getOptions()
                .size());
    }

    @Test
    public void getOptionsWithMultipleRoomsAndWorkTimes() throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                24, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        assertEquals(
                4, optionsBeforePost[0].getOptions()
                        .size(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(null)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        assertEquals(12, weekEventRepository.findAll()
                .size());

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));

        Set<OptionDTO> expOptionsAfterPost =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r2.getId()))));

        // expect two options because t1 is blocked for e1
        assertEquals(expOptionsAfterPost, new HashSet<>(optionsAfterPost[0].getOptions()));
        assertEquals(
                12, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(2, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void getOptionsBIG() throws Exception {
        // given

        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 9);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        Timeslot t4 = tss.get(3);
        Timeslot t5 = tss.get(4);
        Timeslot t6 = tss.get(5);
        Timeslot t7 = tss.get(6);
        Timeslot t8 = tss.get(7);
        Timeslot t9 = tss.get(8);

        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);
        WorkTime workTimeE1MoT4 = createWorkTimeFor(e1, mo, t4);
        WorkTime workTimeE1MoT5 = createWorkTimeFor(e1, mo, t5);
        WorkTime workTimeE1MoT6 = createWorkTimeFor(e1, mo, t6);
        WorkTime workTimeE1MoT7 = createWorkTimeFor(e1, mo, t7);
        WorkTime workTimeE1MoT8 = createWorkTimeFor(e1, mo, t8);
        WorkTime workTimeE1MoT9 = createWorkTimeFor(e1, mo, t9);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);
        workTimes.add(workTimeE1MoT4);
        workTimes.add(workTimeE1MoT5);
        workTimes.add(workTimeE1MoT6);
        workTimes.add(workTimeE1MoT7);
        workTimes.add(workTimeE1MoT8);
        workTimes.add(workTimeE1MoT9);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                108, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        assertEquals(
                27, optionsBeforePost[0].getOptions()
                        .size(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(1)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r2.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                107, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        // expect (1 Timeslot * 3 rooms) options to be dropped
        assertEquals(24, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void getOptionsTakesPlaceInMultipleRooms() throws Exception {
        // given

        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.flush();

        em.getTransaction()
                .commit();

        // verify options
        OptionsDTO[] optionsBeforePost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                24, optionsBeforePost[0].getDegreeOfFreedom(), Arrays.toString(optionsBeforePost));
        assertEquals(
                4, optionsBeforePost[0].getOptions()
                        .size(), Arrays.toString(optionsBeforePost));

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r2.getId()))
                        .build();

        mockMvcPostWeekEvent(timetable.getId(), eventReqDTO);

        OptionsDTO[] optionsAfterPost =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        assertEquals(
                24, optionsAfterPost[0].getDegreeOfFreedom(), Arrays.toString(optionsAfterPost));
        assertEquals(4, optionsAfterPost[0].getOptions()
                .size());
    }

    @Test
    public void whenGetEventOptionsAndFilterByWeekdays_thenReturnFilteredOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        // given 1 Course, 1 Timeslot, 1 Room
        Timetable timetable = createTimetableWS22();

        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(1);

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Timeslot t1 = tss.get(0);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(rt);
        persistAllEntities(em, c1Tss);
        em.persist(r1);
        em.persist(ComboR1);
        em.flush();

        em.getTransaction()
                .commit();

        // validate options without filtering
        OptionsDTO[] optionsForAllWeekdays =
                mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        OptionsDTO optionsForC1AllWeekdays = optionsForAllWeekdays[0];

        assertEquals(
                84,
                optionsForC1AllWeekdays.getDegreeOfFreedom(),
                Arrays.toString(optionsForAllWeekdays));
        assertEquals(
                7,
                optionsForC1AllWeekdays.getOptions()
                        .size(),
                Arrays.toString(optionsForAllWeekdays));

        // when
        OptionsDTO[] optionsOnlyOnMonday =
                mockMvcGetOptions(
                        timetable.getId(), List.of(c1.getId()), List.of(MONDAY), Set.of(1));
        OptionsDTO optionsForC1OnlyOnMonday = optionsOnlyOnMonday[0];
        OptionDTO expOption =
                OptionDTO.builder()
                        .weekday(MONDAY)
                        .week(1)
                        .timeslots(List.of(t1.getId()))
                        .rooms(List.of(r1.getId()))
                        .build();

        assertEquals(
                84,
                optionsForC1OnlyOnMonday.getDegreeOfFreedom(),
                "expect unchanged degree of freedom");
        assertEquals(1, optionsForC1OnlyOnMonday.getOptions()
                .size());
        assertEquals(
                new HashSet<>(List.of(expOption)),
                new HashSet<>(optionsForC1OnlyOnMonday.getOptions()),
                "expect filtered list of options");
    }

    @Test
    public void givenCourseBlockSize2_whenGetEventOptions_thenTimeslotsSortedInAscOrderByStartTime()
            throws Exception {
        // given
        em.getTransaction()
                .begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(2);

        List<Timeslot> tss = generateTimeslots(timetable, 3);
        RoomType rt = createRoomTypeOne();
        Room r1 = createRoomOne(timetable, rt);
        RoomCombination ComboR1 = createRoomComboFor(c1, List.of(r1));

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;

        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE1MoT3 = createWorkTimeFor(e1, mo, t3);

        List<WorkTime> workTimes = new ArrayList<>();
        workTimes.add(workTimeE1MoT1);
        workTimes.add(workTimeE1MoT2);
        workTimes.add(workTimeE1MoT3);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, workTimes);
        em.persist(ComboR1);
        em.flush();

        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c1.getId()), Set.of(1));
        Set<OptionDTO> expOptionsBeforePost =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t1.getId(), t2.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId(), t3.getId()),
                                        List.of(r1.getId()))));
        assertEquals(2, options[0].getOptions()
                .size(), Arrays.toString(options));
        assertEquals(expOptionsBeforePost, new HashSet<>(options[0].getOptions()));

        List<UUID> expTimeslotOrder1 = new ArrayList<>(List.of(t1.getId(), t2.getId()));
        List<UUID> expTimeslotOrder2 = new ArrayList<>(List.of(t2.getId(), t3.getId()));

        for (OptionDTO option : options[0].getOptions()) {
            // validate that the timeslots are sorted by start time
            List<UUID> actualTimeslots = new ArrayList<>(option.getTimeslots());
            assertTrue(
                    actualTimeslots.equals(expTimeslotOrder1)
                            || actualTimeslots.equals(expTimeslotOrder2));
        }
    }

    /**
     * Test case: "May be parallel" relation between two courses and the courses are assigned to the
     * same employee. Expected result: Generated options include time slots that are parallel to an
     * already scheduled event.
     */
    @Test
    public void
    givenMayBeParallelRelation_givenCoursesWithSameEmployees_whenGetOptions_thenIncludeParallelSlots()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.MAY_BE_PARALLEL, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);

        Employee e1 = createEmployeeOne(timetable);
        List<WorkTime> e1WorkTimes = generateWorkTimes(e1, List.of(MONDAY), tss);
        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR1 = createRoomComboFor(c2, List.of(r1));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c1, List.of(r1), List.of(t1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        em.persist(relation);
        persistAllEntities(tss);
        persistAllEntities(c1Tss);
        persistAllEntities(c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(e1WorkTimes);
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c2RoomComboR1));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId()), // option parallel to givenEventC1
                                        List.of(r1.getId())),
                                new OptionDTO(1, MONDAY, List.of(t1.getId()), List.of(r2.getId())),
                                new OptionDTO(1, MONDAY, List.of(t2.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, MONDAY, List.of(t2.getId()), List.of(r2.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test case: "May be parallel" relation between two courses, one of the employees of the
     * courses is the same, but one course has another employee whose working time is not released
     * for certain timeslots. Expected result: Timeslots that are not available als work times
     * should not be included in the options.
     */
    @Test
    public void
    givenMayBeParallelRel_givenCoursesEmployeeWorkTimeNotAvailable_whenGetOptions_thenDontIncludeNotAvailableSlots()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.MAY_BE_PARALLEL, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);

        EmployeeType employeeType = createEmployeeTypeDozent();
        Employee e1 = createEmployee(timetable, 1, employeeType);
        Employee e2 = createEmployee(timetable, 2, employeeType);
        List<WorkTime> e1WorkTimes = generateWorkTimes(e1, List.of(MONDAY), tss);
        WorkTime e2WorkTime =
                createWorkTimeFor(e2, MONDAY, t1); // e2 has only one available work time
        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);
        addLecturerToCourse(c2, e2);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR1 = createRoomComboFor(c2, List.of(r1));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c1, List.of(r1), List.of(t1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        em.persist(relation);
        persistAllEntities(tss);
        persistAllEntities(c1Tss);
        persistAllEntities(c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(employeeType);
        em.persist(e1);
        em.persist(e2);
        persistAllEntities(e1WorkTimes);
        persistAllEntities(List.of(e2WorkTime));
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c2RoomComboR1));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId()), // option parallel to givenEventC1
                                        List.of(r2.getId())),
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId()), // option parallel to givenEventC1
                                        List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test case: Given three courses, "may be parallel" relation between two of the courses and
     * Expected result: Generated options should not include time slots that are parallel to an
     * already scheduled event of the not related course.
     */
    @Test
    public void
    givenMayBeParallelRelation_givenThirdCourseWithSameEmployees_whenGetOptions_thenDontIncludeParallelSlots()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        Course c1 = createCourse(timetable, 1);
        Course c2 = createCourse(timetable, 2);
        Course c3 = createCourse(timetable, 3);

        CourseRelation relation = createCourseRelation(RelationType.MAY_BE_PARALLEL, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);
        List<CourseTimeslot> c3Tss = generateCourseTimeslots(c3, List.of(MONDAY), tss);

        Employee e1 = createEmployeeOne(timetable);
        List<WorkTime> e1WorkTimes = generateWorkTimes(e1, List.of(MONDAY), tss);
        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);
        addLecturerToCourse(c3, e1);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c3RoomComboR2 = createRoomComboFor(c3, List.of(r2));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);
        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c3);

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c1, List.of(r1), List.of(t1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(List.of(c1, c2, c3));
        em.persist(relation);
        persistAllEntities(tss);
        persistAllEntities(c1Tss);
        persistAllEntities(c2Tss);
        persistAllEntities(c3Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(e1WorkTimes);
        persistAllEntities(List.of(c1RoomComboR1, c3RoomComboR2));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c3.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(1, MONDAY, List.of(t2.getId()), List.of(r2.getId())),
                                new OptionDTO(
                                        1, MONDAY, List.of(t1.getId()), List.of(r2.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test case: "May be parallel" relation between two courses and the courses overlap with regard
     * to the degree semester cohorts. Expected result: Generated options include time slots that
     * are parallel to an already scheduled event.
     */
    @Test
    public void
    givenMayBeParallelRelation_givenCoursesWithSameDegreeSemesters_whenGetOptions_thenIncludeParallelSlots()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.MAY_BE_PARALLEL, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);
        DegreeSemester d1s2 = createDegreeSemester(d1, 2);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s2, c2);
        addDegreeSemesterToCourse(d1s1, c2);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c1, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c1, List.of(r1), List.of(t1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        em.persist(relation);
        persistAllEntities(tss);
        persistAllEntities(c1Tss);
        persistAllEntities(c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c2RoomComboR1));
        em.persist(d1);
        persistAllEntities(List.of(d1s1, d1s2));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(1, MONDAY, List.of(t1.getId()), List.of(r1.getId())),
                                new OptionDTO(1, MONDAY, List.of(t1.getId()), List.of(r2.getId())),
                                new OptionDTO(1, MONDAY, List.of(t2.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, MONDAY, List.of(t2.getId()), List.of(r2.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test case: "May be parallel" relation between two courses with the same degree semesters, but
     * different block sizes
     */
    @Test
    public void
    givenMayBeParallelRelation_givenCoursesOfDifferentBlockSize_whenGetOptions_thenReturnCorrectOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(1);
        Course c2 = createCourseTwo(timetable);
        c2.setBlockSize(2);

        CourseRelation relation = createCourseRelation(RelationType.MAY_BE_PARALLEL, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c1, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c1, List.of(r1), List.of(t2));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        em.persist(relation);
        persistAllEntities(tss);
        persistAllEntities(c1Tss);
        persistAllEntities(c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c2RoomComboR1));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));
        em.persist(givenEventC1);
        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options = mockMvcGetOptions(timetable.getId(), List.of(c2.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId(), t2.getId()),
                                        List.of(r2.getId())),
                                new OptionDTO(
                                        1,
                                        MONDAY,
                                        List.of(t1.getId(), t2.getId()),
                                        List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    @Test
    public void
    givenCourseEventAndMustBeBeforeRelation_whenGetEventOptions_thenOnlyOptionsBeforeEvent()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);
        Course courseB = createCourseTwo(timetable);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);

        List<CourseTimeslot> aTss =
                generateCourseTimeslots(courseA, List.of(TUESDAY, WEDNESDAY), tss);
        aTss.add(createCourseTimeslot(courseA, MONDAY, t3));
        List<CourseTimeslot> bTss =
                generateCourseTimeslots(courseB, List.of(TUESDAY, WEDNESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1 = createRoomComboFor(courseA, List.of(r1));

        WeekEvent givenEventB =
                createWeekEvent(timetable, 0, TUESDAY, courseB, List.of(r1), List.of(t3));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        em.persist(ComboR1);
        em.persist(givenEventB);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseA.getId()), Set.of(1));

        // then
        // there should be no options that take place after course b
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t3.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(1, TUESDAY, List.of(t1.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, TUESDAY, List.of(t2.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    @Test
    public void
    givenCourseEventAndMustBeBeforeRelation_whenGetEventOptionsAndBlockSize2_thenOnlyOptionsBeforeEvent()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);
        courseA.setBlockSize(2);
        Course courseB = createCourseTwo(timetable);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);

        List<CourseTimeslot> aTss =
                generateCourseTimeslots(courseA, List.of(TUESDAY, WEDNESDAY), tss);
        List<CourseTimeslot> bTss =
                generateCourseTimeslots(courseB, List.of(TUESDAY, WEDNESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1 = createRoomComboFor(courseA, List.of(r1));

        WeekEvent givenEventB =
                createWeekEvent(timetable, 0, TUESDAY, courseB, List.of(r1), List.of(t3));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        em.persist(ComboR1);
        em.persist(givenEventB);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseA.getId()), Set.of(1));

        // then
        // there should be no options that take place after course b
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        TUESDAY,
                                        List.of(t1.getId(), t2.getId()),
                                        List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    @Test
    public void givenMustBeBeforeRelationToItself_whenGetEventOptions_thenAllOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseA);

        List<CourseTimeslot> aTss = generateCourseTimeslots(courseA, List.of(MONDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1 = createRoomComboFor(courseA, List.of(r1));

        WeekEvent givenEventB =
                createWeekEvent(timetable, 0, MONDAY, courseA, List.of(r1), List.of(t2));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(relation);
        persistAllEntities(em, aTss);
        em.persist(r1);
        em.persist(ComboR1);
        em.persist(givenEventB);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseA.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(1, MONDAY, List.of(t3.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, MONDAY, List.of(t1.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Tests case: Given two courses, each of which must be before the other Exceptions: The
     * relations cancel each other out, no restriction, all options possible.
     *
     * @throws Exception exception
     */
    @Test
    public void giveMustBeBeforeRelationInBothDirections_whenGetEventOptions_thenAllOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);
        Course courseB = createCourseTwo(timetable);

        CourseRelation relation1 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);
        CourseRelation relation2 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseB, courseA);

        List<CourseTimeslot> aTss = generateCourseTimeslots(courseA, List.of(TUESDAY), tss);
        List<CourseTimeslot> bTss = generateCourseTimeslots(courseB, List.of(TUESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1CA = createRoomComboFor(courseA, List.of(r1));
        RoomCombination ComboR1CB = createRoomComboFor(courseB, List.of(r1));

        WeekEvent givenEventA =
                createWeekEvent(timetable, 1, TUESDAY, courseA, List.of(r1), List.of(t2));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation1);
        em.persist(relation2);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        persistAllEntities(List.of(ComboR1CA, ComboR1CB));
        em.persist(givenEventA);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseB.getId()), Set.of(1));

        // then
        // there should be no options that take place before course B
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(1, TUESDAY, List.of(t1.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, TUESDAY, List.of(t3.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test cases: Must-be-before relation to multiple courses and the events are placed in such a
     * way that there are no options possible
     *
     * @throws Exception
     */
    @Test
    public void giveMustBeBeforeRelationToMultipleCourses_whenGetEventOptions_thenNoOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourse(timetable, 1);
        Course courseB = createCourse(timetable, 2);
        Course courseC = createCourse(timetable, 3);

        CourseRelation relation1 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseB, courseA);
        CourseRelation relation2 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseC, courseB);

        List<CourseTimeslot> aTss =
                generateCourseTimeslots(courseA, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);
        List<CourseTimeslot> bTss =
                generateCourseTimeslots(courseB, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1CA = createRoomComboFor(courseA, List.of(r1));
        RoomCombination ComboR1CB = createRoomComboFor(courseB, List.of(r1));
        RoomCombination ComboR1CC = createRoomComboFor(courseC, List.of(r1));

        WeekEvent givenEventA =
                createWeekEvent(timetable, 0, TUESDAY, courseA, List.of(r1), List.of(t1));

        WeekEvent givenEventC =
                createWeekEvent(timetable, 0, TUESDAY, courseC, List.of(r1), List.of(t3));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(courseC);
        em.persist(relation1);
        em.persist(relation2);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        persistAllEntities(List.of(ComboR1CA, ComboR1CB, ComboR1CC));
        persistAllEntities(List.of(givenEventA, givenEventC));

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseB.getId()), Set.of(1));

        // then
        // there should be no options that take place before course B
        Set<OptionDTO> expOptions = new HashSet<>();
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test cases: Course B must be before Course C but after Course B, resulting in only one
     * possible option
     *
     * @throws Exception exception
     */
    @Test
    public void giveMustBeBeforeRelationToMultipleCourses_whenGetEventOptions_thenExactlyOneOption()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourse(timetable, 1);
        Course courseB = createCourse(timetable, 2);
        Course courseC = createCourse(timetable, 3);

        CourseRelation relation1 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);
        CourseRelation relation2 =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseB, courseC);

        List<CourseTimeslot> aTss =
                generateCourseTimeslots(courseA, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);
        List<CourseTimeslot> bTss =
                generateCourseTimeslots(courseB, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1CA = createRoomComboFor(courseA, List.of(r1));
        RoomCombination ComboR1CB = createRoomComboFor(courseB, List.of(r1));
        RoomCombination ComboR1CC = createRoomComboFor(courseC, List.of(r1));

        WeekEvent givenEventA =
                createWeekEvent(timetable, 0, TUESDAY, courseA, List.of(r1), List.of(t1));

        WeekEvent givenEventC =
                createWeekEvent(timetable, 0, TUESDAY, courseC, List.of(r1), List.of(t3));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(courseC);
        em.persist(relation1);
        em.persist(relation2);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        persistAllEntities(List.of(ComboR1CA, ComboR1CB, ComboR1CC));
        persistAllEntities(List.of(givenEventA, givenEventC));

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseB.getId()), Set.of(1));

        // then
        // there should be no options that take place before course B
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1, TUESDAY, List.of(t2.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    @Test
    public void
    givenCourseEventAndMustBeBeforeRelation_whenGetEventOptions_thenOnlyOptionsAfterEvent()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);
        Course courseB = createCourseTwo(timetable);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);

        List<CourseTimeslot> aTss =
                generateCourseTimeslots(courseA, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);
        List<CourseTimeslot> bTss =
                generateCourseTimeslots(courseB, List.of(MONDAY, TUESDAY, WEDNESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1 = createRoomComboFor(courseA, List.of(r1));

        WeekEvent givenEventB =
                createWeekEvent(timetable, 0, TUESDAY, courseA, List.of(r1), List.of(t2));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        em.persist(ComboR1);
        em.persist(givenEventB);

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] options =
                mockMvcGetOptions(timetable.getId(), List.of(courseB.getId()), Set.of(1));

        // then
        // there should be no options that take place before course B
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1, WEDNESDAY, List.of(t1.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, WEDNESDAY, List.of(t2.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, WEDNESDAY, List.of(t3.getId()), List.of(r1.getId())),
                                new OptionDTO(
                                        1, TUESDAY, List.of(t3.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(options[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + options[0].getOptions()
                        .size());
    }

    /**
     * Test case: must-be-before relation between two course, but no event has been scheduled yet
     * Expected result: no restriction of options regarding the relation
     *
     * @throws Exception exception
     */
    @Test
    public void givenMustBeBeforeRelationAndNoEvent_whenGetEventOptions_thenReturnAllOptions()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);

        Course courseA = createCourseOne(timetable);
        Course courseB = createCourseTwo(timetable);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);

        List<CourseTimeslot> aTss = generateCourseTimeslots(courseA, List.of(MONDAY, TUESDAY), tss);
        List<CourseTimeslot> bTss = generateCourseTimeslots(courseB, List.of(MONDAY, TUESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1CA = createRoomComboFor(courseA, List.of(r1));
        RoomCombination ComboR1CB = createRoomComboFor(courseA, List.of(r1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        persistAllEntities(List.of(ComboR1CA, ComboR1CB));

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] optionsCourseA =
                mockMvcGetOptions(timetable.getId(), List.of(courseA.getId()), Set.of(1));
        OptionsDTO[] optionsCourseB =
                mockMvcGetOptions(timetable.getId(), List.of(courseB.getId()), Set.of(1));

        // then
        // there should be no options that take place after course b
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t1.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(
                                        1, TUESDAY, List.of(t1.getId()), List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(optionsCourseA[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + optionsCourseA[0].getOptions()
                        .size());
        assertEquals(
                expOptions,
                new HashSet<>(optionsCourseB[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + optionsCourseA[0].getOptions()
                        .size());
    }

    /**
     * Test case: must-be-before relation between two courses, and there are multiple events already
     * scheduled. Expected result: for course A only options that take place before all events of
     * course B
     *
     * @throws Exception exception
     */
    @Test
    public void
    givenMultipleEventsAndMustBeBeforeRelation_whenGetEventOptions_thenOnlyOptionsBeforeAllEvents()
            throws Exception {
        em.getTransaction()
                .begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course courseA = createCourseOne(timetable);
        courseA.setSlotsPerWeek(2);
        Course courseB = createCourseTwo(timetable);
        courseB.setSlotsPerWeek(2);

        CourseRelation relation =
                createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, courseA, courseB);

        List<CourseTimeslot> aTss = generateCourseTimeslots(courseA, List.of(MONDAY, TUESDAY), tss);
        List<CourseTimeslot> bTss = generateCourseTimeslots(courseB, List.of(MONDAY, TUESDAY), tss);

        Room r1 = createRoomOne(timetable);
        RoomCombination ComboR1 = createRoomComboFor(courseA, List.of(r1));

        WeekEvent givenEventB1 =
                createWeekEvent(timetable, 0, TUESDAY, courseB, List.of(r1), List.of(t3));
        WeekEvent givenEventB2 =
                createWeekEvent(timetable, 0, MONDAY, courseB, List.of(r1), List.of(t3));

        WeekEvent givenEventA1 =
                createWeekEvent(timetable, 0, MONDAY, courseA, List.of(r1), List.of(t2));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(em, tss);
        em.persist(r1.getRoomType());
        em.persist(courseA);
        em.persist(courseB);
        em.persist(relation);
        persistAllEntities(em, aTss);
        persistAllEntities(em, bTss);
        em.persist(r1);
        em.persist(ComboR1);
        persistAllEntities(List.of(givenEventA1, givenEventB1, givenEventB2));

        em.flush();
        em.getTransaction()
                .commit();

        // when
        OptionsDTO[] optionsCourseA =
                mockMvcGetOptions(timetable.getId(), List.of(courseA.getId()), Set.of(1));

        // then
        Set<OptionDTO> expOptions =
                new HashSet<>(
                        List.of(
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t2.getId()),
                                        List.of(r1.getId())),
                                new OptionDTO(
                                        1,
                                        DayOfWeek.MONDAY,
                                        List.of(t1.getId()),
                                        List.of(r1.getId()))));
        assertEquals(
                expOptions,
                new HashSet<>(optionsCourseA[0].getOptions()),
                "expected options: "
                        + expOptions.size()
                        + ", actual options: "
                        + optionsCourseA[0].getOptions()
                        .size());
    }

    private static void assertOptionDTO(OptionDTO expOptionDTO, OptionDTO actualOptionDTO) {
        assertEquals(expOptionDTO.getWeekday(), actualOptionDTO.getWeekday());
        assertEquals(
                new HashSet<>(expOptionDTO.getTimeslots()),
                new HashSet<>(expOptionDTO.getTimeslots()));
        assertEquals(
                new HashSet<>(expOptionDTO.getRooms()), new HashSet<>(actualOptionDTO.getRooms()));
    }

    private static <T> void persistAllEntities(EntityManager em, Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    private <T> void persistAllEntities(Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    private OptionsDTO[] mockMvcGetOptions(
            UUID timetableId, List<UUID> courseIds, Set<Integer> weeks) throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        for (UUID courseId : courseIds) {
            requestParams.add("courseIds", courseId.toString());
        }
        for (Integer week : weeks) {
            requestParams.add("week", week.toString());
        }
        String url = String.format("/v1/timetable/%s/week-events/options", timetableId);
        return mockMvcTestUtil.getWithRequestParams(url, requestParams, OptionsDTO[].class);
    }

    private OptionsDTO[] mockMvcGetOptions(
            UUID timetableId, List<UUID> courseIds, List<DayOfWeek> weekdays, Set<Integer> weeks)
            throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        for (UUID courseId : courseIds) {
            requestParams.add("courseIds", courseId.toString());
        }
        for (DayOfWeek weekday : weekdays) {
            requestParams.add("weekdays", weekday.toString());
        }
        for (Integer week : weeks) {
            requestParams.add("week", week.toString());
        }
        String url = String.format("/v1/timetable/%s/week-events/options", timetableId);
        return mockMvcTestUtil.getWithRequestParams(url, requestParams, OptionsDTO[].class);
    }

    private SchedulingResultDTO mockMvcPostWeekEvent(UUID timetableId, WeekEventReqDTO dto)
            throws Exception {
        String url = String.format("/v1/timetable/%s/week-events/schedule", timetableId);

        return mockMvcTestUtil.post(url, SchedulingResultDTO.class, dto);
    }

    private String getScheduleEventURL(UUID timetableId) {
        return String.format("/v1/timetable/%s/week-events/schedule", timetableId);
    }
}
