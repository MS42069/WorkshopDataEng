package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
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
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.delete;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.get;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.put;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Tests for the Timetable Controller across all layers to test end-to-end behaviour.
 *
 * <p>The `@SpringBootTest` annotation tells Spring Boot to load the entire application context. The
 * use of <code>WebEnvironment.RANDOM_PORT</code> starts a server with a random port.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class TimetableIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired
    private TimetableRepository timetableRepository;

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
    private SemesterTypeRepository semesterTypeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @AfterEach
    void rollback() {
        // not the deletion order is important: e.g. timetable have to be deleted before timeslots
        weekEventRepository.deleteAll();
        specialEventRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        courseRepository.deleteAll();
        courseRelationRepository.deleteAll();
        roomCombinationRepository.deleteAll();
        courseTimeslotRepository.deleteAll();
        degreeRepository.deleteAll();
        employeeRepository.deleteAll();
        workTimeRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    @Test
    void whenPostTimetable_thenCorrectResponse() throws Exception {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("SS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .semesterType(semesterType.getId())
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        // when
        TimetableResDTO response =
                mockMvcTestUtil.post("/v1/timetables", TimetableResDTO.class, reqDTO);

        assertEquals("name", response.getName());
        assertNotNull(response.getId());
    }

    @Test
    void whenPostTimetableWithSemesterType_thenCorrectResponse() throws Exception {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .semesterType(semesterType.getId())
                        .build();

        // when
        TimetableResDTO response =
                mockMvcTestUtil.post("/v1/timetables", TimetableResDTO.class, reqDTO);

        assertEquals("name", response.getName());
        assertNotNull(response.getId());
    }

    @Test
    public void givenTimetable_whenGetTimetables_thenReturnListOfTimetables() throws Exception {
        // given
        Timetable timetable1 = createDefaultTimetable();

        Timetable timetable2 = createDefaultTimetable2();
        timetable2.setName("Nicht 1");

        timetable1 = timetableRepository.saveAndFlush(timetable1);
        timetable2 = timetableRepository.saveAndFlush(timetable2);

        // when
        TimetableResDTO[] response = mockMvcTestUtil.get("/v1/timetables", TimetableResDTO[].class);

        assertEquals(timetable1.getId(), response[0].getId());
        assertEquals(timetable2.getId(), response[1].getId());
    }

    @Test
    public void givenTimetable_whenGetTimetableById_thenReturnTimetable() throws Exception {
        // given
        Timetable timetable = createDefaultTimetable();
        timetable = timetableRepository.saveAndFlush(timetable);

        // when
        String url = format("/v1/timetables/%s", timetable.getId());
        TimetableResDTO response = mockMvcTestUtil.get(url, TimetableResDTO.class);

        assertEquals(timetable.getId(), response.getId());
    }

    @Test
    public void givenTimetable_whenPutTimetableName_thenResponseOk() throws Exception {
        // given
        Timetable timetable = createDefaultTimetable();
        timetable = timetableRepository.saveAndFlush(timetable);

        TimetableReqDTO requestDTO =
                TimetableReqDTO.builder()
                        .semesterType(timetable.getSemesterType()
                                .getId())
                        .name("TEST2")
                        .numberOfWeeks(timetable.getNumberOfWeeks())
                        .startDate(timetable.getStartDate())
                        .endDate(timetable.getEndDate())
                        .specialEvents(new ArrayList<>())
                        .build();

        // when
        String url = format("/v1/timetables/%s", timetable.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, requestDTO);

        // then
        Timetable timetable1 =
                timetableRepository.findById(timetable.getId())
                        .orElse(new Timetable());
        assertThat(timetable1.getName()).isEqualTo("TEST2");
    }

    @Test
    public void givenTimetable_whenPutTimetableWithSemesterType_thenChangeSemesterType()
            throws Exception {
        // given

        Timetable timetable = createDefaultTimetable();
        timetable = timetableRepository.saveAndFlush(timetable);

        TimetableReqDTO requestDTO =
                TimetableReqDTO.builder()
                        .semesterType(timetable.getSemesterType()
                                .getId())
                        .name("TEST2")
                        .specialEvents(new ArrayList<>())
                        .numberOfWeeks(timetable.getNumberOfWeeks())
                        .startDate(timetable.getStartDate())
                        .endDate(timetable.getEndDate())
                        .build();

        // when
        String url = format("/v1/timetables/%s", timetable.getId());
        mockMvcTestUtil.put(url, TimetableResDTO.class, requestDTO);

        // then
        Timetable timetable1 =
                timetableRepository.findById(timetable.getId())
                        .orElse(new Timetable());
        assertThat(timetable1.getName()).isEqualTo("TEST2");
    }

    @Test
    public void givenTimetable_whenDeleteTimetable_thenNoContentResponse() throws Exception {
        // given
        Timetable timetable = createDefaultTimetable();
        timetable = timetableRepository.saveAndFlush(timetable);

        // when
        String url = format("/v1/timetables/%s", timetable.getId());
        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());

        assertThat(timetableRepository.existsById(timetable.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingTimetable_thenNotFoundResponse() throws Exception {
        String url = format("/v1/timetables/%s", UUID.randomUUID());
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingTimetables_thenNotFoundResponse() throws Exception {
        String url = format("/v1/timetables/%s", UUID.randomUUID());
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPutNotExistingTimetable_thenNotFoundResponse() throws Exception {

        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("test")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        String url = format("/v1/timetables/%s", UUID.randomUUID());
        mockMvc.perform(put(url, reqDTO))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateTimetableThenAddTimeslotsForIt_thenNotFoundResponse() throws Exception {

        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("test")
                        .numberOfWeeks(12)
                        .semesterType(semesterType.getId())
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();
        mockMvcTestUtil.post("/v1/timetables", TimetableResDTO.class, reqDTO);
        assertThat(timeslotRepository.findAll()
                .size()).isEqualTo(7);
        assertThat(timeslotRepository.findAll()
                .get(6)
                .getEndTime())
                .isEqualTo(LocalTime.parse("18:15"));
    }

    @Test
    void givenTimetablePutChangeSpecialEventsDeletingAndSavingSpecialEventsCorrectly()
            throws Exception {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        Timetable table = createDefaultTimetable();
        table.getSpecialEvents()
                .add(specialEvent);
        table = timetableRepository.saveAndFlush(table);
        specialEvent.setTimetable(table);
        specialEvent = specialEventRepository.saveAndFlush(specialEvent);

        SpecialEventReqDTO specialEventReqDTO =
                SpecialEventReqDTO.builder()
                        .endDate(LocalDate.of(2023, 1, 31))
                        .startDate(LocalDate.of(2023, 1, 31))
                        .specialEventType(SpecialEventType.FREE)
                        .build();

        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(List.of(specialEventReqDTO))
                        .name("updated")
                        .numberOfWeeks(12)
                        .semesterType(table.getSemesterType()
                                .getId())
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        String url = format("/v1/timetables/%s", table.getId());

        TimetableResDTO response = mockMvcTestUtil.put(url, TimetableResDTO.class, reqDTO);

        assertEquals(reqDTO.getName(), response.getName());
        assertThat(specialEventRepository.findById(specialEvent.getId())
                .isPresent()).isFalse();
        assertThat(specialEventRepository.findAll()
                .size()).isEqualTo(1);
        assertEquals(response.getSpecialEvents()
                .size(), response.getSpecialEvents()
                .size());
        assertEquals(
                response.getSpecialEvents()
                        .get(0)
                        .getStartDate(),
                response.getSpecialEvents()
                        .get(0)
                        .getStartDate());
    }

    @Test
    void givenTimetableAndAllExistingEntitiesForTimetableWhenDeleteThenDeleteAllEntities()
            throws Exception {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        Timetable timetable = createDefaultTimetable();
        timetable.getSpecialEvents()
                .add(specialEvent);
        timetable = timetableRepository.saveAndFlush(timetable);
        specialEvent.setTimetable(timetable);
        specialEventRepository.saveAndFlush(specialEvent);
        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("typeTEST");
        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room roo = new Room();
        roo.setRoomType(roomType);
        roo.setName("Room One");
        roo.setCapacity(0);
        roo.setAbbreviation("r1");
        roo.setIdentifier("id");
        roo.setTimetable(timetable);
        roo = roomRepository.saveAndFlush(roo);

        Timeslot timeslot = new Timeslot();
        timeslot.setTimetable(timetable);
        timeslot.setIndex(0);
        timeslot.setStartTime(LocalTime.of(8, 0));
        timeslot.setEndTime(LocalTime.of(9, 0));
        timeslot = timeslotRepository.saveAndFlush(timeslot);

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(List.of());
        employee.setTimetable(timetable);
        employee = employeeRepository.saveAndFlush(employee);

        WorkTime workTime = new WorkTime();
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setTimeslot(timeslot);
        workTime.setEmployee(employee);
        workTime = workTimeRepository.saveAndFlush(workTime);
        employee.setWorkTimes(List.of(workTime));

        Course course = new Course();
        course.setTimetable(timetable);
        course.setCourseType(null);
        course.setAbbreviation("Abbr");
        course.setName("name");
        course.setWeeksPerSemester(1);
        course.setSlotsPerWeek(1);
        course.setCasID("casId");
        course.setBlockSize(1);
        course.setDescription("desc");
        course.setCourseRelationsA(new ArrayList<>());
        course.setCourseRelationsB(new ArrayList<>());
        course.setCourseTimeslots(new ArrayList<>());
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(List.of(employee));
        course.setSemesters(new ArrayList<>());
        course.setWeekEvents(new ArrayList<>());
        course = courseRepository.saveAndFlush(course);

        CourseTimeslot courseTimeslot = new CourseTimeslot();
        courseTimeslot.setCourse(course);
        courseTimeslot.setWeekday(DayOfWeek.MONDAY);
        courseTimeslot.setTimeslot(timeslot);
        courseTimeslot = courseTimeslotRepository.saveAndFlush(courseTimeslot);
        course.setCourseTimeslots(List.of(courseTimeslot));

        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(List.of(roo));
        roomCombination.setCourse(course);
        roomCombination = roomCombinationRepository.saveAndFlush(roomCombination);
        course.setSuitedRooms(List.of(roomCombination));

        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setCourseA(course);
        courseRelation.setCourseB(course);
        courseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelation = courseRelationRepository.saveAndFlush(courseRelation);
        course.setCourseRelationsA(List.of(courseRelation));
        course.setCourseRelationsB(List.of(courseRelation));

        Degree degree = new Degree();
        degree.setTimetable(timetable);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("13.0");
        degree.setSchoolType(null);
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree = degreeRepository.saveAndFlush(degree);

        DegreeSemester degreeSemester = new DegreeSemester();
        degreeSemester.setTimetable(timetable);
        degreeSemester.setSemesterNumber(1);
        degreeSemester.setDegree(degree);
        degreeSemester.setAttendees(100);
        degreeSemester.setExtensionName("name");
        degreeSemester.setCourses(List.of(course));
        degreeSemester = degreeSemesterRepository.saveAndFlush(degreeSemester);
        degree.setSemesters(List.of(degreeSemester));
        course.setSemesters(List.of(degreeSemester));

        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setTimetable(timetable);
        weekEvent.setRooms(List.of(roo));
        weekEvent.setTimeslots(List.of(timeslot));
        weekEvent.setWeekday(DayOfWeek.MONDAY);
        weekEvent.setWeek(0);
        weekEvent.setCourse(course);
        weekEvent = weekEventRepository.saveAndFlush(weekEvent);
        course.setWeekEvents(List.of(weekEvent));

        // when

        String url = format("/v1/timetables/%s", timetable.getId());

        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());

        // then

        assertThat(timetableRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(specialEventRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(roomRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(timeslotRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(workTimeRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(employeeRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(courseRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(courseTimeslotRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(roomCombinationRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(courseRelationRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(degreeRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(degreeSemesterRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(weekEventRepository.findAll()
                .size()).isEqualTo(0);
    }

    private Timetable createDefaultTimetable() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);

        return timetable;
    }

    private Timetable createDefaultTimetable2() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("SS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);

        return timetable;
    }

    private EmployeeType createAndPersistEmployeeType() {
        EmployeeType employeeType = TestDataUtil.createEmployeeTypeDozent();
        return employeeTypeRepository.saveAndFlush(employeeType);
    }
}
