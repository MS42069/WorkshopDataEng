package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.*;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.WeekEventController;
import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemsDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventReqDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.scheduler.Constraint;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Integration tests that focus on the scheduling of week events and checking for admissibility,
 * more precisely testing the endpoint provided by {@link WeekEventController#scheduleWeekEvent}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class SchedulerAdmissibilityIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private WeekEventRepository weekEventRepository;

    @Autowired private CourseRepository courseRepository;

    @Autowired private TimetableRepository timetableRepository;

    @Autowired private RoomRepository roomRepository;

    @Autowired private TimeslotRepository timeslotRepository;

    @Autowired private WorkTimeRepository workTimeRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EmployeeTypeRepository employeeTypeRepository;
    @Autowired private DegreeRepository degreeRepository;
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;
    @Autowired private CourseTimeslotRepository courseTimeslotRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;
    @Autowired private CourseRelationRepository courseRelationRepository;

    /**
     * Autowired EntityManagerFactory to obtain EntityManagers in order to facilitate persisting of
     * entities
     */
    @Autowired private EntityManagerFactory emf;

    private EntityManager em;
    @Autowired private RoomTypeRepository roomTypeRepository;

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

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void closeDBConnection() {
        em.close();
    }

    /**
     * Test case: Tests the scheduling of two week events for one course in a row, where both are
     * valid.
     *
     * <pre>
     *  Entities:
     *   - 1 Courses: C1
     *   - 1 Employees: E1
     *   - 2 Timeslots: T1, T2
     *   - 1 Room: R1
     *
     *  Course relations:
     *      C1:
     *          lecturers: [E1]
     *          semesters: []
     *          suitedRooms: [[R1]]
     *
     *  Working times of the employee(s):
     *      E1:
     *          Day\Ts T1 T2
     *          Mon    1  1
     *
     *  Schedule C1 on:
     *      (Mon, [T1], [R1])
     *      (Mon, [T2], [R1])
     * </pre>
     */
    @Test
    public void
            givenCourseWithEmployees_whenPostTwoAdmissibleWeekEvents_thenPersistAndRespondWithOk()
                    throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk());

        assertEquals(1, weekEventRepository.findAll().size());

        WeekEventReqDTO event2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event2ReqDTO))
                .andExpect(status().isOk());
        assertEquals(2, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Schedule course with one employee add different weekdays at the same time
     *
     * @throws Exception exception
     */
    @Test
    public void whenScheduleEventForSameCourseSameTimeslotDifferentWeekday_thenRespondWithOk()
            throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        c1.setBlockSize(1);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        DayOfWeek tues = DayOfWeek.TUESDAY;
        WorkTime workTimeE1TuesT1 = createWorkTimeFor(e1, tues, t1);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1TuesT1));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk());

        assertEquals(1, weekEventRepository.findAll().size());

        WeekEventReqDTO event2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.TUESDAY)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event2ReqDTO))
                .andExpect(status().isOk());
        assertEquals(13, weekEventRepository.findAll().size());
    }

    /** Test case: Inadmissible WeekEvent because exactly the same event is already scheduled */
    @Test
    public void givenEvent_whenPostSameWeekEvent_thenInadmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk());

        assertEquals(1, weekEventRepository.findAll().size());

        WeekEventReqDTO event2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event2ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.ROOM_AVAILABILITY),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(1, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Inadmissible WeekEvent, because a different course is already scheduled in this
     * timeslot and room.
     */
    @Test
    public void
            givenEvent_whenPostWeekEventForDifferentCourseWithSameTimeslotAndRoom_thenInadmissible()
                    throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        assertEquals(1, weekEventRepository.findAll().size());

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();
        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.ROOM_AVAILABILITY),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(1, weekEventRepository.findAll().size());
    }

    @Test
    public void testWhenCoursesHaveSameTimeSlotButDifferentRoom_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 1);
        Timeslot t1 = tss.get(0);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(roomComboR1);
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        assertEquals(1, weekEventRepository.findAll().size());

        // when
        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventReqDTO))
                .andExpect(status().isOk());
        assertEquals(2, weekEventRepository.findAll().size());
    }

    @Test
    public void testWhenCoursesOfEmployeeHaveSameTimeslots_thenInadmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        Course c2 = createCourseTwo(timetable);
        c2.setSlotsPerWeek(1);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination roomComboR2 = createRoomComboFor(c2, List.of(r2));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(roomComboR1);
        em.persist(roomComboR2);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.EMPLOYEE_AVAILABILITY),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(1, weekEventRepository.findAll().size());
    }

    @Test
    public void testWhenCoursesOfEmployeeHaveDifferentTimeslots_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        Course c2 = createCourseTwo(timetable);
        c2.setSlotsPerWeek(1);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination roomComboR2 = createRoomComboFor(c2, List.of(r2));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(roomComboR1);
        em.persist(roomComboR2);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(2, weekEventRepository.findAll().size());
    }

    @Test
    public void testCoursesOfDegreeSemesterHaveSameTimeSlot_thenInadmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        Course c2 = createCourseTwo(timetable);
        c2.setSlotsPerWeek(1);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination roomComboR2 = createRoomComboFor(c2, List.of(r2));

        Employee e1 = createEmployeeOne(timetable);
        Employee e2 = createEmployee(timetable, 2);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE2MoT1 = createWorkTimeFor(e2, mo, t1);
        WorkTime workTimeE2MoT2 = createWorkTimeFor(e2, mo, t2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);

        Degree degree = createDegreeOne(timetable);
        DegreeSemester degreeSemester = createDegreeSemesterFor(degree);
        associateDegreeSemesterWithCourses(degreeSemester, List.of(c1, c2));

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e2.getEmployeeType());
        em.persist(e1);
        em.persist(e2);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(roomComboR1);
        em.persist(roomComboR2);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        persistAllEntities(em, List.of(workTimeE2MoT1, workTimeE2MoT2));
        em.persist(degree);
        em.persist(degreeSemester);
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.DEGREE_SEMESTER_CONFLICT),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(1, weekEventRepository.findAll().size());
    }

    @Test
    public void testCoursesOfDegreeSemesterHaveDifferentTimeSlot_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        Course c2 = createCourseTwo(timetable);
        c2.setSlotsPerWeek(1);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination roomComboR2 = createRoomComboFor(c2, List.of(r2));

        Employee e1 = createEmployeeOne(timetable);
        Employee e2 = createEmployee(timetable, 2);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);
        WorkTime workTimeE2MoT1 = createWorkTimeFor(e2, mo, t1);
        WorkTime workTimeE2MoT2 = createWorkTimeFor(e2, mo, t2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);

        Degree degree = createDegreeOne(timetable);
        DegreeSemester degreeSemester = createDegreeSemesterFor(degree);
        associateDegreeSemesterWithCourses(degreeSemester, List.of(c1, c2));

        WeekEvent givenEvent = createEventOnlyWithTimetable(timetable);
        givenEvent.setCourse(c1);
        givenEvent.getRooms().add(r1);
        givenEvent.getTimeslots().add(t1);
        givenEvent.setWeekday(DayOfWeek.MONDAY);
        givenEvent.setWeek(0);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(e2.getEmployeeType());
        em.persist(e2);
        em.persist(c1);
        em.persist(c2);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        persistAllEntities(em, c2Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(roomComboR1);
        em.persist(roomComboR2);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        persistAllEntities(em, List.of(workTimeE2MoT1, workTimeE2MoT2));
        em.persist(degree);
        em.persist(degreeSemester);
        em.persist(givenEvent);
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(2, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseHasInadmissibleRoomCombination_thenInadmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        c1.setBlockSize(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);

        RoomCombination roomComboR1R2 = createRoomComboFor(c1, List.of(r1, r2));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(roomComboR1R2);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r3.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.ROOM_COMBINATION),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(0, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseHasAdmissibleRoomCombination_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(2);
        c1.setSlotsPerWeek(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);

        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1, r2));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(roomComboR1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseIsOutsideWorkingHours_thenInadmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);

        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1, r2));

        Employee e1 = createEmployeeOne(timetable);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(roomComboR1);
        persistAllEntities(em, List.of(workTimeE1MoT1));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(1)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r2.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.EMPLOYEE_AVAILABILITY),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(0, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseIsInsideWorkingHours_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);
        Room r3 = createRoomThree(timetable, rt);

        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1, r2));

        Employee e1 = createEmployeeOne(timetable);
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, DayOfWeek.MONDAY, t1);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(roomComboR1);
        persistAllEntities(em, List.of(workTimeE1MoT1));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId(), r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseHasWrongSizeInBlock_thenInadmissible() throws Exception {
        // given
        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        c1.setBlockSize(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);
        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        System.out.println("ContentAsString: " + result.getResponse().getContentAsString());

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.BLOCK_SIZE),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(0, weekEventRepository.findAll().size());
    }

    @Test
    public void testCourseHasSizeTwoAndScheduledTwoTimesInBlock_thenAdmissible() throws Exception {
        // given

        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        c1.setBlockSize(2);

        RoomType rt = createRoomTypeTwo();

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(DayOfWeek.values()), tss);

        Room r1 = createRoomOne(timetable, rt);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1MoT2 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(t1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        em.persist(c1);
        persistAllEntities(em, tss);
        persistAllEntities(em, c1Tss);
        em.persist(rt);
        em.persist(r1);
        em.persist(roomComboR1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1MoT2));
        em.flush();

        em.getTransaction().commit();

        // when
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Inadmissible WeekEvent, because the timeslot is not marked as admissible {@link
     * Course#getCourseTimeslots() course timeslot}.
     */
    @Test
    public void whenScheduleEventAtTimeslotAllowedForTheCourse_thenInAdmissible() throws Exception {
        em.getTransaction().begin();

        Timetable timetable = createTimetableWS22();
        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        c1.setBlockSize(1);

        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        // restrict course by potential timeslots
        CourseTimeslot c1Ts = createCourseTimeslot(c1, MONDAY, t1);

        Room r1 = createRoomOne(timetable);
        RoomCombination roomComboR1 = createRoomComboFor(c1, List.of(r1));

        Employee e1 = createEmployeeOne(timetable);
        DayOfWeek mo = DayOfWeek.MONDAY;
        WorkTime workTimeE1MoT1 = createWorkTimeFor(e1, mo, t1);
        WorkTime workTimeE1TuesT1 = createWorkTimeFor(e1, mo, t2);

        addLecturerToCourse(c1, e1);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        em.persist(c1);
        persistAllEntities(em, tss);
        em.persist(c1Ts);
        persistRoom(r1);
        em.persist(roomComboR1);
        em.persist(e1.getEmployeeType());
        em.persist(e1);
        persistAllEntities(em, List.of(workTimeE1MoT1, workTimeE1TuesT1));
        em.flush();

        em.getTransaction().commit();

        // when schedule at t2
        WeekEventReqDTO event1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(1)
                        .blockOfTimeslots(Set.of(t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), event1ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.COURSE_TIMESLOT),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(0, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Given a "may be parallel relation" between to courses which have the same
     * employees and degree semesters, tests the scheduling of two week events parallel to each
     * other.
     */
    @Test
    public void
            givenMayBeParallelRel_whenScheduleParallelToCourseWithSameEmployeeAndDegreeSemester_thenAdmissible()
                    throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);

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
        RoomCombination c2RoomComboR2 = createRoomComboFor(c1, List.of(r2));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC1ReqDTO))
                .andExpect(status().isOk());

        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                .andExpect(status().isOk());

        assertEquals(2, weekEventRepository.findAll().size());
    }

    @Test
    public void givenMustBeBeforeRel_whenScheduleCourseOverlapping_thenInadmissible()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(2);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, c1, c2);

        List<CourseTimeslot> c1Tss = generateCourseTimeslots(c1, List.of(MONDAY), tss);
        List<CourseTimeslot> c2Tss = generateCourseTimeslots(c2, List.of(MONDAY), tss);

        EmployeeType employeeType = createEmployeeTypeDozent();
        Employee e1 = createEmployee(timetable, 1, employeeType);
        Employee e2 = createEmployee(timetable, 2, employeeType);
        List<WorkTime> e1WorkTimes = generateWorkTimes(e1, List.of(MONDAY), tss);
        List<WorkTime> e2WorkTimes = generateWorkTimes(e2, List.of(MONDAY), tss);
        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);

        RoomType rt = createRoomTypeTwo();
        Room r1 = createRoomOne(timetable, rt);
        Room r2 = createRoomTwo(timetable, rt);

        RoomCombination c1RoomComboR1 = createRoomComboFor(c1, List.of(r1));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c2, List.of(r2), List.of(t2));

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
        persistAllEntities(e2WorkTimes);
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(1)
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.COURSE_RELATION),
                problems.getViolatedConstraints(),
                "Validate violated constraints");
    }

    @Test
    public void givenMustBeBeforeRel_whenScheduleAfterCourse_thenInadmissible() throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, c1, c2);

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
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c2, List.of(r2), List.of(t2));

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(12)
                        .blockOfTimeslots(Set.of(t3.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();

        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);
        assertEquals(
                Set.of(Constraint.COURSE_RELATION),
                problems.getViolatedConstraints(),
                "Validate violated constraints");
    }

    @Test
    public void givenMustBeBeforeRel_whenScheduleBeforeCourse_thenAdmissible() throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        CourseRelation relation = createCourseRelation(RelationType.A_MUST_BE_BEFORE_B, c1, c2);

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
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        WeekEvent givenEventC1 =
                createWeekEvent(timetable, 0, MONDAY, c2, List.of(r2), List.of(t2));

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2));
        em.persist(givenEventC1);

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                .andExpect(status().isOk());
    }

    /**
     * Test case: Given a "may be parallel relation" between to courses which have the same
     * employees and degree semesters, tests the scheduling of two week events overlapping each
     * other
     */
    @Test
    public void givenMayBeParallelRel_whenScheduleOverlappingWithOtherCourse_thenAdmissible()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);
        Timeslot t3 = tss.get(2);

        Course c1 = createCourseOne(timetable);
        c1.setBlockSize(2);
        Course c2 = createCourseTwo(timetable);
        c2.setBlockSize(2);

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
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t2.getId(), t3.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC1ReqDTO))
                .andExpect(status().isOk());

        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c2.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId(), t2.getId())) // events overlap in t2
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                .andExpect(status().isOk());

        assertEquals(2, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Given a "may be parallel relation" between two different courses, it's not
     * admissible to schedule an events parallel to a not related course (with same employee and
     * degree semester).
     */
    @Test
    public void givenMayBeParallelRel_whenScheduleCourseParallelToThirdCourse_thenInadmissible()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);

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
        RoomCombination c1RoomComboR2 = createRoomComboFor(c1, List.of(r2));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));
        RoomCombination c3RoomComboR2 = createRoomComboFor(c3, List.of(r2));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);
        addDegreeSemesterToCourse(d1s1, c3);

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c1RoomComboR2, c3RoomComboR2));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c3.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC1ReqDTO))
                .andExpect(status().isOk());
        assertEquals(1, weekEventRepository.findAll().size());

        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();
        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);

        assertEquals(
                Set.of(Constraint.EMPLOYEE_AVAILABILITY, Constraint.DEGREE_SEMESTER_CONFLICT),
                problems.getViolatedConstraints(),
                "Validate violated constraints");
        assertEquals(1, weekEventRepository.findAll().size());
    }

    /**
     * Test case: Given a "may be parallel relation" between two different courses, it's not
     * admissible to schedule an event parallel to another not related course which has the same
     * employee.
     */
    @Test
    public void
            givenMayBeParallelRel_whenScheduleCourseParallelToNotRelatedCourse_thenInadmissible()
                    throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();
        List<Timeslot> tss = generateTimeslots(timetable, 3);
        Timeslot t1 = tss.get(0);

        Course c1 = createCourseOne(timetable);
        c1.setSlotsPerWeek(2);
        Course c2 = createCourseTwo(timetable);
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
        RoomCombination c1RoomComboR2 = createRoomComboFor(c1, List.of(r2));
        RoomCombination c2RoomComboR2 = createRoomComboFor(c2, List.of(r2));
        RoomCombination c3RoomComboR2 = createRoomComboFor(c3, List.of(r2));

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);

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
        persistAllEntities(List.of(c1RoomComboR1, c2RoomComboR2, c1RoomComboR2, c3RoomComboR2));
        em.persist(d1);
        persistAllEntities(List.of(d1s1));

        em.flush();
        em.getTransaction().commit();

        // when
        WeekEventReqDTO eventC1ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c1.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r1.getId()))
                        .build();

        mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC1ReqDTO))
                .andExpect(status().isOk());

        WeekEventReqDTO eventC2ReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(c3.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(t1.getId()))
                        .takesPlaceInRooms(Set.of(r2.getId()))
                        .build();

        MvcResult result =
                mockMvc.perform(post(getScheduleEventURL(timetable.getId()), eventC2ReqDTO))
                        .andExpect(status().isConflict())
                        .andReturn();
        ProblemsDTO problems = MockMvcTestUtil.mapResult(result, ProblemsDTO.class);

        assertEquals(
                Set.of(Constraint.EMPLOYEE_AVAILABILITY),
                problems.getViolatedConstraints(),
                "Validate violated constraints");

        assertEquals(1, weekEventRepository.findAll().size());
    }

    private <T> void persistAllEntities(EntityManager em, Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    private <T> void persistAllEntities(Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    private void persistRoom(Room room) {
        em.persist(room.getRoomType());
        em.persist(room);
    }

    private String getScheduleEventURL(UUID timetableId) {
        return String.format("/v1/timetable/%s/week-events/schedule", timetableId);
    }
}
