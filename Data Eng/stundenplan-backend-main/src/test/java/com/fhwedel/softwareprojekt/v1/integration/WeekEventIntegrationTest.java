package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.*;
import static java.lang.String.format;
import static java.time.DayOfWeek.MONDAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.WeekEventController;
import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.SchedulingResultDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventResDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Integration Tests for the {@link WeekEventController} across all layers to test end-to-end
 * behaviour.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class WeekEventIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    @Autowired private WeekEventRepository weekEventRepository;

    @Autowired private RoomTypeRepository roomTypeRepository;

    @Autowired private CourseRepository courseRepository;

    @Autowired private TimetableRepository timetableRepository;

    @Autowired private RoomRepository roomRepository;

    @Autowired private TimeslotRepository timeslotRepository;

    @Autowired private CourseConverter courseConverter;

    @Autowired private EmployeeTypeRepository employeeTypeRepository;
    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Autowired private WorkTimeRepository workTimeRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DegreeRepository degreeRepository;
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;

    @Autowired private CourseTimeslotRepository courseTimeslotRepository;

    @AfterEach
    void rollbackDB() {
        weekEventRepository.deleteAll();
        workTimeRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        courseTimeslotRepository.deleteAll();
        courseRepository.deleteAll();
        employeeRepository.deleteAll();
        degreeRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    @Test
    void givenWeekEvent_whenGetAllWeekEvents_thenCorrectResponse() throws Exception {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        Course course = persistCourse(createCourseOne(timetable));
        Timeslot ts = persistTimeslot(createTimeslotOne(timetable));
        RoomType rt = persistRoomType(createRoomTypeOne());
        Room room = persistRoom(createRoomOne(timetable, rt));

        WeekEvent event = new WeekEvent();
        event.setWeekday(MONDAY);
        event.setWeek(0);
        event.getTimeslots().add(ts);
        event.getRooms().add(room);
        event.setTimetable(timetable);
        event.setCourse(course);

        event = weekEventRepository.saveAndFlush(event);

        WeekEventResDTO expEventResDTO =
                WeekEventResDTO.builder()
                        .id(event.getId())
                        .weekday(MONDAY)
                        .week(0)
                        .rooms(List.of(new IdWrapperDTO(room.getId())))
                        .course(courseConverter.convertCourseEntityToBasicResDTO(course))
                        .timeslots(List.of(new IdWrapperDTO(ts.getId())))
                        .build();

        // when
        String url = String.format("/v1/timetable/%s/week-events", timetable.getId());
        WeekEventResDTO[] response = mockMvcTestUtil.get(url, WeekEventResDTO[].class);

        assertEquals(1, response.length);
        assertEquals(Set.of(expEventResDTO), Set.of(response));
    }

    @Test
    void givenWeekEvents_whenGetAllWeekEventsWithWeekdayQueryParamNull_thenReturnAllWeekEvents()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        Course course = persistCourse(createCourseOne(timetable));
        Timeslot ts = persistTimeslot(createTimeslotOne(timetable));
        RoomType rt = persistRoomType(createRoomTypeOne());
        Room room = persistRoom(createRoomOne(timetable, rt));

        WeekEvent eventOne = new WeekEvent();
        eventOne.setWeekday(MONDAY);
        eventOne.setWeek(0);
        eventOne.getTimeslots().add(ts);
        eventOne.getRooms().add(room);
        eventOne.setTimetable(timetable);
        eventOne.setCourse(course);
        eventOne = weekEventRepository.saveAndFlush(eventOne);

        // event two on a different day
        WeekEvent eventTwo = new WeekEvent();
        eventTwo.setWeekday(DayOfWeek.TUESDAY);
        eventTwo.setWeek(0);
        eventTwo.getTimeslots().add(ts);
        eventTwo.getRooms().add(room);
        eventTwo.setTimetable(timetable);
        eventTwo.setCourse(course);

        eventTwo = weekEventRepository.saveAndFlush(eventTwo);

        WeekEventResDTO expEventOneResDTO =
                WeekEventResDTO.builder()
                        .id(eventOne.getId())
                        .weekday(MONDAY)
                        .week(0)
                        .rooms(List.of(new IdWrapperDTO(room.getId())))
                        .course(courseConverter.convertCourseEntityToBasicResDTO(course))
                        .timeslots(List.of(new IdWrapperDTO(ts.getId())))
                        .build();

        WeekEventResDTO expEventTwoResDTO =
                WeekEventResDTO.builder()
                        .id(eventTwo.getId())
                        .weekday(DayOfWeek.TUESDAY)
                        .week(0)
                        .rooms(List.of(new IdWrapperDTO(room.getId())))
                        .course(courseConverter.convertCourseEntityToBasicResDTO(course))
                        .timeslots(List.of(new IdWrapperDTO(ts.getId())))
                        .build();

        // when
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("weekdayId", null);
        String url = String.format("/v1/timetable/%s/week-events", timetable.getId());
        WeekEventResDTO[] response =
                mockMvcTestUtil.getWithRequestParams(url, requestParams, WeekEventResDTO[].class);

        assertEquals(2, response.length);
        assertEquals(Set.of(expEventOneResDTO, expEventTwoResDTO), Set.of(response));
    }

    @Test
    void
            givenWeekEvents_whenGetAllWeekEventsWithWeekdayQueryParamSpecified_thenReturnCorrectWeekEvents()
                    throws Exception {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        Course course = persistCourse(createCourseOne(timetable));
        Timeslot ts = persistTimeslot(createTimeslotOne(timetable));
        RoomType rt = persistRoomType(createRoomTypeOne());
        Room room = persistRoom(createRoomOne(timetable, rt));

        WeekEvent eventOne = new WeekEvent();
        eventOne.setWeekday(MONDAY);
        eventOne.setWeek(0);
        eventOne.getTimeslots().add(ts);
        eventOne.getRooms().add(room);
        eventOne.setTimetable(timetable);
        eventOne.setCourse(course);
        eventOne = weekEventRepository.saveAndFlush(eventOne);

        // event two on a different day
        WeekEvent eventTwo = new WeekEvent();
        eventTwo.setWeekday(DayOfWeek.TUESDAY);
        eventTwo.setWeek(0);
        eventTwo.getTimeslots().add(ts);
        eventTwo.getRooms().add(room);
        eventTwo.setTimetable(timetable);
        eventTwo.setCourse(course);

        weekEventRepository.saveAndFlush(eventTwo);

        WeekEventResDTO expEventOneResDTO =
                WeekEventResDTO.builder()
                        .id(eventOne.getId())
                        .weekday(MONDAY)
                        .week(0)
                        .rooms(List.of(new IdWrapperDTO(room.getId())))
                        .course(courseConverter.convertCourseEntityToBasicResDTO(course))
                        .timeslots(List.of(new IdWrapperDTO(ts.getId())))
                        .build();

        // when
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("weekday", MONDAY.toString());
        requestParams.add("week", "0");
        String url = String.format("/v1/timetable/%s/week-events", timetable.getId());
        WeekEventResDTO[] response =
                mockMvcTestUtil.getWithRequestParams(url, requestParams, WeekEventResDTO[].class);

        // expected to return only week events that take place on monday
        assertEquals(2, weekEventRepository.findAll().size());
        assertEquals(1, response.length, Arrays.toString(response));
        assertEquals(Set.of(expEventOneResDTO), Set.of(response));
    }

    @Test
    void whenPostWeekEvent_thenCorrectResponseAndPersistence() throws Exception {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        Course course = persistCourse(createCourseOne(timetable));
        Timeslot ts = persistTimeslot(createTimeslotOne(timetable));
        RoomType rt = persistRoomType(createRoomTypeOne());
        Room room = persistRoom(createRoomOne(timetable, rt));
        persistCourseTimeslot(createCourseTimeslot(course, MONDAY, ts));

        WeekEventReqDTO eventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(course.getId())
                        .weekday(MONDAY)
                        .week(0)
                        .blockOfTimeslots(Set.of(ts.getId()))
                        .takesPlaceInRooms(Set.of(room.getId()))
                        .build();

        // when
        String url = String.format("/v1/timetable/%s/week-events/schedule", timetable.getId());
        SchedulingResultDTO response =
                mockMvcTestUtil.post(url, SchedulingResultDTO.class, eventReqDTO);

        // then
        assertThat(response.getEvents()[0].getId()).isNotNull();

        assertEquals(1, weekEventRepository.findAll().size());
        assertEquals(MONDAY, weekEventRepository.findAll().get(0).getWeekday());
        assertEquals(0, weekEventRepository.findAll().get(0).getWeek());

        // verify correct persistence using a get request
        WeekEventResDTO requestedEvent =
                this.mockMvcGetWeekEventById(timetable.getId(), response.getEvents()[0].getId());

        assertEquals(1, requestedEvent.getRooms().size());
        assertEquals(room.getId(), requestedEvent.getRooms().get(0).getId());

        assertEquals(1, requestedEvent.getTimeslots().size());
        assertEquals(ts.getId(), requestedEvent.getTimeslots().get(0).getId());

        assertEquals(eventReqDTO.getWeekday(), requestedEvent.getWeekday());
        assertEquals(eventReqDTO.getWeek(), requestedEvent.getWeek());
    }

    @Test
    void whenDeleteWeekEvent_thenCorrectResponseAndEventDeleted() throws Exception {
        WeekEvent givenEvent = createAndPersistWeekEvent();

        // verify setup
        assertEquals(1, weekEventRepository.findAll().size());

        // when
        String url =
                format(
                        "/v1/timetable/%s/week-events/%s",
                        givenEvent.getTimetable().getId(), givenEvent.getId());
        mockMvc.perform(MockMvcTestUtil.delete(url)).andExpect(status().isNoContent());

        // then
        assertEquals(0, weekEventRepository.findAll().size());
    }

    @Test
    void whenFindByEmployee_thenReturnAllEventsOfEmployeeAsDTO() throws Exception {
        WeekEvent givenEventOne = createAndPersistWeekEvent();
        createAndPersistWeekEventTwo(givenEventOne.getTimetable());

        // verify setup
        assertEquals(2, weekEventRepository.findAll().size());

        // when
        String url =
                format(
                        "/v1/timetable/%s/week-events/employee/%s",
                        givenEventOne.getTimetable().getId(),
                        givenEventOne.getCourse().getLecturers().get(0).getId());
        WeekEventResDTO[] response = mockMvcTestUtil.get(url, WeekEventResDTO[].class);
        // then
        assertThat(response.length).isEqualTo(1);
        assertThat(response[0].getCourse().getName()).isEqualTo("Course One");
    }

    @Test
    void whenFindByDegreeSemester_thenReturnAllEventsOfDegreeSemesterAsDTO() throws Exception {
        WeekEvent givenEventOne = createAndPersistWeekEvent();
        WeekEvent givenEventTwo = createAndPersistWeekEventTwo(givenEventOne.getTimetable());

        // verify setup
        assertEquals(2, weekEventRepository.findAll().size());
        assertThat(givenEventOne.getCourse().getSemesters().size()).isPositive();
        assertThat(givenEventTwo.getCourse().getSemesters().size()).isPositive();

        // when
        String url =
                format(
                        "/v1/timetable/%s/week-events/semester/%s",
                        givenEventOne.getTimetable().getId(),
                        givenEventOne.getCourse().getSemesters().get(0).getId());
        WeekEventResDTO[] response = mockMvcTestUtil.get(url, WeekEventResDTO[].class);
        // then
        assertThat(response.length).isEqualTo(1);
        assertThat(response[0].getCourse().getName()).isEqualTo("Course One");
    }

    @Test
    void whenFindByRoom_thenReturnAllEventsOfRoomAsDTO() throws Exception {
        WeekEvent givenEventOne = createAndPersistWeekEvent();
        createAndPersistWeekEventTwo(givenEventOne.getTimetable());

        // verify setup
        assertEquals(2, weekEventRepository.findAll().size());

        // when
        String url =
                format(
                        "/v1/timetable/%s/week-events/room/%s",
                        givenEventOne.getTimetable().getId(),
                        givenEventOne.getRooms().get(0).getId());
        WeekEventResDTO[] response = mockMvcTestUtil.get(url, WeekEventResDTO[].class);
        // then
        assertThat(response.length).isEqualTo(1);
        assertThat(response[0].getCourse().getName()).isEqualTo("Course One");
    }

    private WeekEvent createAndPersistWeekEvent() {
        Timetable timetable = persistTimetable(createTimetableWS22());
        RoomType rt = createRoomTypeOne();
        rt = roomTypeRepository.saveAndFlush(rt);
        Room room = persistRoom(createRoomOne(timetable, rt));
        Course course = persistCourse(createCourseOne(timetable));
        Degree degree = persistDegree(createDegreeOne(timetable));
        DegreeSemester semester = persistSemester(createDegreeSemesterFor(degree));
        Timeslot ts = persistTimeslot(createTimeslotOne(timetable));
        Employee employee = persistEmployee(createEmployee(timetable, 2));
        course.getSemesters().add(semester);
        course.getLecturers().add(employee);
        courseRepository.saveAndFlush(course);
        semester.getCourses().add(course);
        degreeSemesterRepository.saveAndFlush(semester);
        WeekEvent event = new WeekEvent();
        event.setRooms(List.of(room));
        event.setWeekday(MONDAY);
        event.setWeek(0);
        event.getTimeslots().add(ts);
        event.setTimetable(timetable);
        event.setCourse(course);

        event = weekEventRepository.saveAndFlush(event);

        assertThat(event.getId()).isNotNull();

        return event;
    }

    private WeekEvent createAndPersistWeekEventTwo(Timetable timetable) {
        Course course = persistCourse(createCourseTwo(timetable));
        RoomType rt = createRoomTypeTwo();
        rt = roomTypeRepository.saveAndFlush(rt);
        Room room = persistRoom(createRoomTwo(timetable, rt));
        Timeslot ts = persistTimeslot(createTimeslotTwo(timetable));
        Employee employee = persistEmployee(createEmployeeTwo(timetable, 1));
        Degree degree = persistDegree(createDegreeOne(timetable));
        DegreeSemester semester = persistSemester(createDegreeSemesterTwoFor(degree));
        course.getLecturers().add(employee);
        course.getSemesters().add(semester);
        courseRepository.saveAndFlush(course);
        semester.getCourses().add(course);
        degreeSemesterRepository.saveAndFlush(semester);
        WeekEvent event = new WeekEvent();
        event.setWeekday(MONDAY);
        event.setWeek(0);
        event.getTimeslots().add(ts);
        event.setTimetable(timetable);
        event.setCourse(course);
        event.setRooms(List.of(room));

        event = weekEventRepository.saveAndFlush(event);

        assertThat(event.getId()).isNotNull();

        return event;
    }

    private WeekEventResDTO mockMvcGetWeekEventById(UUID timetableId, UUID eventId)
            throws Exception {
        String url = format("/v1/timetable/%s/week-events/%s", timetableId, eventId);
        return mockMvcTestUtil.get(url, WeekEventResDTO.class);
    }

    private Timeslot persistTimeslot(Timeslot timeslot) {
        timeslot = timeslotRepository.saveAndFlush(timeslot);

        assertThat(timeslot.getId()).isNotNull();

        return timeslot;
    }

    private Degree persistDegree(Degree degree) {
        degree = degreeRepository.saveAndFlush(degree);

        assertThat(degree.getId()).isNotNull();

        return degree;
    }

    private DegreeSemester persistSemester(DegreeSemester semester) {
        semester = degreeSemesterRepository.saveAndFlush(semester);

        assertThat(semester.getId()).isNotNull();

        return semester;
    }

    private Course persistCourse(Course course) {
        course = courseRepository.saveAndFlush(course);

        assertThat(course.getId()).isNotNull();

        return course;
    }

    private RoomType persistRoomType(RoomType roomType) {
        roomType = roomTypeRepository.saveAndFlush(roomType);

        assertThat(roomType.getId()).isNotNull();

        return roomType;
    }

    private Room persistRoom(Room room) {
        room = roomRepository.saveAndFlush(room);

        assertThat(room.getId()).isNotNull();

        return room;
    }

    private Employee persistEmployee(Employee employee) {
        employeeTypeRepository.saveAndFlush(employee.getEmployeeType());
        employee = employeeRepository.saveAndFlush(employee);

        assertThat(employee.getId()).isNotNull();

        return employee;
    }

    private void persistCourseTimeslot(CourseTimeslot courseTimeslot) {
        courseTimeslot = courseTimeslotRepository.saveAndFlush(courseTimeslot);

        assertThat(courseTimeslot.getId()).isNotNull();
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
