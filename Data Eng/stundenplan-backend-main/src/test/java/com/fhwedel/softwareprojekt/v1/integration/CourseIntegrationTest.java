package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.CourseController;
import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseDetailResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRoomComboReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRoomComboResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseToPlanResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.RoomBasicResDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomCombinationRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.CourseTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Tests for the {@link CourseController} across all layers to test end-to-end
 * behaviour.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WeekEventRepository weekEventRepository;

    @Autowired
    private RoomCombinationRepository roomCombinationRepository;
    @Autowired
    private CourseRelationRepository courseRelationRepository;

    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @AfterEach
    void rollbackDB() {
        weekEventRepository.deleteAll();
        courseRelationRepository.deleteAll();
        courseRepository.deleteAll();
        roomCombinationRepository.deleteAll();
        employeeRepository.deleteAll();
        roomRepository.deleteAll();
        courseTypeRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    @Test
    void givenCourses_whenGetAllCourses_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourseOne = createCourseOne();
        givenCourseOne.setTimetable(timetable);
        givenCourseOne = courseRepository.saveAndFlush(givenCourseOne);

        Course givenCourseTwo = createCourseTwo(timetable);
        givenCourseTwo = courseRepository.saveAndFlush(givenCourseTwo);

        CourseDetailResDTO expCourseOneResDTO =
                courseConverter.convertCourseEntityToDetailResDTO(givenCourseOne);
        CourseDetailResDTO expCourseTwoResDTO =
                courseConverter.convertCourseEntityToDetailResDTO(givenCourseTwo);

        // when
        CourseDetailResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO[].class);

        // then
        assertEquals(2, response.length);
        assertTrue(List.of(response)
                .contains(expCourseOneResDTO));
        assertTrue(List.of(response)
                .contains(expCourseTwoResDTO));
    }

    @Test
    void givenCourses_withCourseType_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        CourseType ct = new CourseType();
        ct.setName("Test");
        ct = courseTypeRepository.saveAndFlush(ct);
        Course givenCourseOne = createCourseOne();
        givenCourseOne.setCourseType(ct);
        givenCourseOne.setTimetable(timetable);
        givenCourseOne = courseRepository.saveAndFlush(givenCourseOne);

        Course givenCourseTwo = createCourseTwo(timetable);
        givenCourseTwo = courseRepository.saveAndFlush(givenCourseTwo);

        CourseDetailResDTO expCourseOneResDTO =
                courseConverter.convertCourseEntityToDetailResDTO(givenCourseOne);
        CourseDetailResDTO expCourseTwoResDTO =
                courseConverter.convertCourseEntityToDetailResDTO(givenCourseTwo);

        // when
        CourseDetailResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO[].class);

        // then
        assertEquals(2, response.length);
        assertTrue(List.of(response)
                .contains(expCourseOneResDTO));
        assertTrue(List.of(response)
                .contains(expCourseTwoResDTO));
        assertEquals(response[0].getCourseType()
                .getId(), ct.getId());
        assertEquals(response[0].getCourseType()
                .getName(), ct.getName());
    }

    @Test
    void givenCourse_whenGetCourseById_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();

        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);

        CourseDetailResDTO expectedCourseResDTO =
                courseConverter.convertCourseEntityToDetailResDTO(givenCourse);

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response = mockMvcTestUtil.get(url, CourseDetailResDTO.class);

        // then
        assertEquals(expectedCourseResDTO, response);
    }

    @Test
    void whenPostFlatCourse_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Lectures in Physics")
                        .abbreviation("Physics")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        // when
        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertNotNull(response.getId());
        assertEquals(courseReqDTO.getCasID(), response.getCasID());
        assertEquals(courseReqDTO.getName(), response.getName());
        assertEquals(courseReqDTO.getAbbreviation(), response.getAbbreviation());
        assertEquals(courseReqDTO.getDescription(), response.getDescription());
        assertEquals(courseReqDTO.getSlotsPerWeek(), response.getSlotsPerWeek());
        assertEquals(courseReqDTO.getBlockSize(), response.getBlockSize());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList.size()).isEqualTo(1);
        assertThat(courseList.get(0)
                .getCasID()).isEqualTo(courseReqDTO.getCasID());
    }

    @Test
    void givenEmployee_whenPostCourseWithEmployees_thenCorrectResponse() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Employee givenEmployee = createAndPersistEmployeeOne(timetable);

        Set<IdWrapperDTO> employeeIds = new HashSet<>();
        employeeIds.add(new IdWrapperDTO(givenEmployee.getId()));

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Lectures in Physics")
                        .abbreviation("Physics")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .lecturers(employeeIds)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        // when
        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        // verify returned lecturers
        List<EmployeeResDTO> resultLecturers = response.getLecturers();
        assertEquals(1, resultLecturers.size());

        EmployeeResDTO employeeResDTO = resultLecturers.get(0);
        assertEquals(givenEmployee.getId(), employeeResDTO.getId());
        assertEquals(givenEmployee.getAbbreviation(), employeeResDTO.getAbbreviation());
        assertEquals(givenEmployee.getLastname(), employeeResDTO.getLastname());
        assertEquals(givenEmployee.getFirstname(), employeeResDTO.getFirstname());

        // verify persistence using a get request
        CourseDetailResDTO persistedCourse =
                this.mockMvcGetCourseById(timetable.getId(), response.getId());

        assertEquals(
                1,
                persistedCourse.getLecturers()
                        .size(),
                "One employee has been assigned to the course");
        assertEquals(
                givenEmployee.getId(),
                persistedCourse.getLecturers()
                        .get(0)
                        .getId(),
                "The assigned employee corresponds to the expected given employee");

        assertEquals(1, courseRepository.findAll()
                .size());
    }

    @Test
    void givenRoom_whenPostCourseWithSuitedRooms_thenCorrectResponse() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Room givenRoom = createAndPersistRoomOne(timetable);

        CourseRoomComboReqDTO courseRoomComboReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoom.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Lectures in Physics")
                        .abbreviation("Physics")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .suitedRooms(new HashSet<>(List.of(courseRoomComboReqDTO)))
                        .build();

        // when
        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertEquals(1, response.getSuitedRooms()
                .size());
        assertEquals(1, response.getSuitedRooms()
                .get(0)
                .getRooms()
                .size());
        assertEquals(givenRoom.getId(), response.getSuitedRooms()
                .get(0)
                .getRooms()
                .get(0)
                .getId());
        assertEquals(
                givenRoom.getName(), response.getSuitedRooms()
                        .get(0)
                        .getRooms()
                        .get(0)
                        .getName());

        // verify persistence using a get request
        CourseDetailResDTO persistedCourse =
                mockMvcGetCourseById(timetable.getId(), response.getId());

        RoomBasicResDTO expRoomBasicResDto =
                RoomBasicResDTO.builder()
                        .id(givenRoom.getId())
                        .abbreviation(givenRoom.getAbbreviation())
                        .name(givenRoom.getName())
                        .build();
        CourseRoomComboResDTO expRoomComboDTO =
                CourseRoomComboResDTO.builder()
                        .rooms(new ArrayList<>(List.of(expRoomBasicResDto)))
                        .build();

        List<CourseRoomComboResDTO> resultSuitedRooms = persistedCourse.getSuitedRooms();
        assertEquals(List.of(expRoomComboDTO), resultSuitedRooms);

        assertEquals(1, courseRepository.findAll()
                .size());
    }

    @Test
    void whenPostCompleteCourse_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        Employee givenEmployee = createAndPersistEmployeeOne(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        courseReqDTO.getLecturers()
                .add(new IdWrapperDTO(givenEmployee.getId()));

        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        assertNotNull(response.getId());
        assertEquals(courseReqDTO.getCasID(), response.getCasID());
    }

    @Test
    void givenCourseWithSuitedRooms_whenPutSameCourse_thenNoChanges() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        CourseDetailResDTO givenCourse =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        assertNotNull(givenCourse.getId());
        assertEquals(2, givenCourse.getSuitedRooms()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(2, response.getSuitedRooms()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
    }

    @Test
    void giveCourse_whenPostCompleteCourseWithNoUniqueCasID_thenBadRequestAndNoPersist()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        Employee givenEmployee = createAndPersistEmployeeOne(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        courseReqDTO.getLecturers()
                .add(new IdWrapperDTO(givenEmployee.getId()));

        courseReqDTO.setCasID(givenCourse.getCasID()); // not unique casID

        // when
        mockMvc.perform(
                        MockMvcTestUtil.post(
                                format("/v1/timetable/%s/courses", timetable.getId()),
                                courseReqDTO))
                .andExpect(status().isBadRequest());

        // verify in particular that no room combinations are persisted
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
        assertEquals(1, courseRepository.findAll()
                .size());
        assertEquals(givenCourse.getId(), courseRepository.findAll()
                .get(0)
                .getId());
    }

    @Test
    void whenPostCompleteCourseWithNonExistingEmployees_thenNotFoundAndNoPersist()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();

        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        courseReqDTO
                .getLecturers()
                .add(new IdWrapperDTO(UUID.randomUUID())); // non-existing employee
        // when, then
        mockMvc.perform(
                        MockMvcTestUtil.post(
                                format("/v1/timetable/%s/courses", timetable.getId()),
                                courseReqDTO))
                .andExpect(status().isNotFound());

        // verify in particular that no room combinations are persisted
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
        assertEquals(1, courseRepository.findAll()
                .size());
        assertEquals(givenCourse.getId(), courseRepository.findAll()
                .get(0)
                .getId());
    }

    @Test
    void whenPostCompleteCourseWithNonExistingRoom_thenNotFoundAndNoPersist() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);
        Room givenRoomOne = createAndPersistRoomOne(timetable);

        Employee givenEmployee = createAndPersistEmployeeOne(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(
                                new HashSet<>(
                                        List.of(
                                                new IdWrapperDTO(
                                                        UUID.randomUUID())))) // non-existing room
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        courseReqDTO.getLecturers()
                .add(new IdWrapperDTO(givenEmployee.getId()));

        // when
        mockMvc.perform(
                        MockMvcTestUtil.post(
                                format("/v1/timetable/%s/courses", timetable.getId()),
                                courseReqDTO))
                .andExpect(status().isNotFound());

        // then
        // verify in particular that no room combinations are persisted
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
        assertEquals(1, courseRepository.findAll()
                .size());
        assertEquals(givenCourse.getId(), courseRepository.findAll()
                .get(0)
                .getId());
    }

    @Test
    void givenCourse_whenPutChangedCourse_thenCorrectlyChangedCourse() throws Exception {
        CourseDetailResDTO givenCourse = mockMvcPostCompleteCourse();
        Timetable timetable = timetableRepository.findAll()
                .get(0);
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB044")
                        .name("Creative Uncertainty")
                        .abbreviation("CU")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseTimeslots(new ArrayList<>())
                        .courseType(null)
                        .build();

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertThat(response.getId()).isEqualTo(givenCourse.getId());
        assertThat(response.getCasID()).isEqualTo(courseReqDTO.getCasID());
        assertThat(response.getName()).isEqualTo(courseReqDTO.getName());
        assertThat(response.getDescription()).isEqualTo(courseReqDTO.getDescription());
        assertThat(response.getBlockSize()).isEqualTo(courseReqDTO.getBlockSize());
        assertThat(response.getSlotsPerWeek()).isEqualTo(courseReqDTO.getSlotsPerWeek());
        assertThat(response.getCourseType()).isEqualTo(courseReqDTO.getCourseType());
        assertThat(response.getAbbreviation()).isEqualTo(courseReqDTO.getAbbreviation());

        // verify that the name was also changed in the database
        assertNotEquals(givenCourse.getName(), courseReqDTO.getName());
        assertEquals(
                courseReqDTO.getName(),
                courseRepository.findById(givenCourse.getId())
                        .orElse(new Course())
                        .getName());
    }

    @Test
    void givenCourseWithOutEmployees_whenPutCourse_thenCorrectlyAddEmployeeToList()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);
        assertTrue(givenCourse.getLecturers()
                .isEmpty());

        Employee employeeOne = createAndPersistEmployeeOne(timetable);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.setLecturers(
                new LinkedHashSet<>(List.of(new IdWrapperDTO(employeeOne.getId()))));

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(1, response.getLecturers()
                .size());
        assertEquals(employeeOne.getId(), response.getLecturers()
                .get(0)
                .getId());
    }

    @Test
    void givenCourseWithEmployee_whenPutCourse_thenCorrectlyReplaceEmployeeList() throws Exception {
        // given course with an employee
        CourseDetailResDTO givenCourse = mockMvcPostCompleteCourse();
        assertEquals(1, givenCourse.getLecturers()
                .size());
        Timetable timetable = timetableRepository.findAll()
                .get(0);

        Employee employeeTwo = createAndPersistEmployeeTwo(timetable);
        // verify that employeeTwo is different from the employee that is currently associated with
        // the course
        assertNotEquals(employeeTwo.getId(), givenCourse.getLecturers()
                .get(0)
                .getId());

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.setLecturers(
                new LinkedHashSet<>(List.of(new IdWrapperDTO(employeeTwo.getId()))));

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(1, response.getLecturers()
                .size());
        assertEquals(employeeTwo.getId(), response.getLecturers()
                .get(0)
                .getId());
    }

    @Test
    void givenCourseWithSuitedRooms_whenPutCourseWithoutSuitedRooms_thenCorrectlyDeleteRoomCombos()
            throws Exception {
        CourseDetailResDTO givenCourse = mockMvcPostCompleteCourse();
        // verify setup
        assertEquals(2, givenCourse.getSuitedRooms()
                .size());
        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        Timetable timetable = timetableRepository.findAll()
                .get(0);
        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.setSuitedRooms(new HashSet<>());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(0, response.getSuitedRooms()
                .size());

        assertEquals(0, roomCombinationRepository.findAll()
                .size());
    }

    @Test
    void givenCourseWithoutSuitedRooms_whenPutCourseWithSuitedRooms_thenCorrectlyAddRoomCombos()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);
        // verify setup
        assertTrue(givenCourse.getLecturers()
                .isEmpty());
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.setSuitedRooms(
                new LinkedHashSet<>(List.of(roomComboOneReqDTO, roomComboTwoReqDTO)));
        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO response =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(2, response.getSuitedRooms()
                .size());

        assertEquals(2, roomCombinationRepository.findAll()
                .size());
    }

    @Test
    void givenCourseWithSuitedRooms_whenPutCourse_thenCorrectlyChangeSuitedRooms()
            throws Exception {
        // given course with 2 room combinations
        Timetable timetable = createAndPersistTimetable();
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.setSuitedRooms(
                new LinkedHashSet<>(List.of(roomComboOneReqDTO, roomComboTwoReqDTO)));

        CourseDetailResDTO givenCourse =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        assertEquals(2, givenCourse.getSuitedRooms()
                .size());

        // build update request dto
        Room givenRoomThree = createAndPersistRoomThree(timetable);

        CourseRoomComboReqDTO roomComboThreeReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomThree.getId()))))
                        .build();

        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        updateCourseReqDTO.setSuitedRooms(
                new LinkedHashSet<>(
                        List.of(
                                roomComboOneReqDTO, // keep room combo one
                                // remove room combo two
                                roomComboThreeReqDTO // add room combo three
                        )));

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
        assertEquals(2, updatedCourse.getSuitedRooms()
                .size());

        RoomBasicResDTO roomOneBasicResDTO =
                RoomBasicResDTO.builder()
                        .id(givenRoomOne.getId())
                        .name(givenRoomOne.getName())
                        .abbreviation(givenRoomOne.getAbbreviation())
                        .build();

        RoomBasicResDTO roomThreeBasicResDTO =
                RoomBasicResDTO.builder()
                        .id(givenRoomOne.getId())
                        .name(givenRoomOne.getName())
                        .abbreviation(givenRoomOne.getAbbreviation())
                        .build();
        CourseRoomComboResDTO expectedRoomComboOneResDTO =
                CourseRoomComboResDTO.builder()
                        .rooms(new ArrayList<>(List.of(roomOneBasicResDTO)))
                        .build();
        CourseRoomComboResDTO expectedRoomComboThreeResDTO =
                CourseRoomComboResDTO.builder()
                        .rooms(new ArrayList<>(List.of(roomThreeBasicResDTO)))
                        .build();

        assertTrue(updatedCourse.getSuitedRooms()
                .contains(expectedRoomComboOneResDTO));
        assertTrue(updatedCourse.getSuitedRooms()
                .contains(expectedRoomComboThreeResDTO));
    }

    @Test
    void givenCourseWithRoomCombos_whenPutCourseWithNonExistingRooms_thenNotFoundAndNoChanges()
            throws Exception {
        CourseDetailResDTO givenCourse = mockMvcPostCompleteCourse();
        assertEquals(2, roomCombinationRepository.findAll()
                .size());
        Timetable timetable = timetableRepository.findAll()
                .get(0);
        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(
                                new HashSet<>(
                                        List.of(
                                                new IdWrapperDTO(
                                                        UUID.randomUUID())))) // non-existing room
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();

        courseReqDTO.setSuitedRooms(new LinkedHashSet<>(List.of(roomComboOneReqDTO)));

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        mockMvc.perform(MockMvcTestUtil.put(url, courseReqDTO))
                // then
                .andExpect(status().isNotFound());

        assertEquals(2, roomCombinationRepository.findAll()
                .size());
    }

    @Test
    void whenPutNotExistingCourse_thenNotFound() throws Exception {
        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();

        String url = format("/v1/timetable/%s/courses/%s", UUID.randomUUID(), UUID.randomUUID());
        mockMvc.perform(MockMvcTestUtil.put(url, courseReqDTO))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetNotExistingCourse_thenNotFound() throws Exception {

        String url = format("/v1/timetable/%s/courses/%s", UUID.randomUUID(), UUID.randomUUID());
        mockMvc.perform(MockMvcTestUtil.get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteCourse_thenCorrectResponseAndCourseDeleted() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course givenCourse = createCourseOne();
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);

        givenCourse.setTimetable(timetableRepository.findAll()
                .get(0));
        // verify setup
        assertEquals(1, courseRepository.findAll()
                .size());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        mockMvc.perform(MockMvcTestUtil.delete(url))
                .andExpect(status().isNoContent());

        // then
        assertEquals(0, courseRepository.findAll()
                .size());
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
    }

    @Test
    void whenDeleteCourseWithRoomCombinations_thenCorrectResponseAndDeletionCascadedToRoomCombos()
            throws Exception {
        CourseDetailResDTO givenCourse = mockMvcPostCompleteCourse();
        Timetable timetable = timetableRepository.findAll()
                .get(0);
        givenCourse.setTimetable(timetable.getId());
        // verify setup
        assertEquals(1, courseRepository.findAll()
                .size());
        assertTrue(courseRepository.existsById(givenCourse.getId()));

        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        mockMvc.perform(MockMvcTestUtil.delete(url))
                .andExpect(status().isNoContent());

        assertEquals(0, courseRepository.findAll()
                .size());
        assertEquals(0, roomCombinationRepository.findAll()
                .size());
    }

    /**
     * Tests that the correct course and the correct number of timeslots to be scheduled are
     * returned if two events are already persisted. Note: explicitly does not test that a correct
     * value of the degree of freedom is returned
     *
     * @throws Exception exception
     */
    @Test
    void given2Events_whenGetAllCoursesStillToPlan_thenOnlyReturnC2AndCorrectAmountOfTimeslots()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Room r1 = createAndPersistRoomOne(timetable);

        Course c1 = TestDataUtil.createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        c1.setBlockSize(1);
        Course c2 = TestDataUtil.createCourseTwo(timetable);
        c2.setSlotsPerWeek(2);
        c2.setBlockSize(1);

        List<Timeslot> tss = TestDataUtil.generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        c1 = courseRepository.saveAndFlush(c1);
        c2 = courseRepository.saveAndFlush(c2);
        t1 = timeslotRepository.saveAndFlush(t1);
        t2 = timeslotRepository.saveAndFlush(t2);

        for (int i = 0; i < timetable.getNumberOfWeeks(); i++) {
            WeekEvent eventC1 = new WeekEvent();
            eventC1.setWeekday(DayOfWeek.MONDAY);
            eventC1.setWeek(i + 1);
            eventC1.setTimeslots(List.of(t1));
            eventC1.setTimetable(timetable);
            eventC1.setCourse(c1);
            eventC1.setRooms(List.of(r1));

            weekEventRepository.saveAndFlush(eventC1);
        }

        for (int i = 0; i < timetable.getNumberOfWeeks(); i++) {
            WeekEvent eventC2 = new WeekEvent();
            eventC2.setWeekday(DayOfWeek.MONDAY);
            eventC2.setWeek(i + 1);
            eventC2.getTimeslots()
                    .add(t2);
            eventC2.setTimetable(timetable);
            eventC2.setCourse(c2);
            eventC2.setRooms(List.of(r1));

            weekEventRepository.saveAndFlush(eventC2);
        }

        // when
        String url = format("/v1/timetable/%s/courses/toPlan", timetable.getId());
        CourseToPlanResDTO[] response = mockMvcTestUtil.get(url, CourseToPlanResDTO[].class);

        // then
        Course expectedCourse = c2;
        // 2 slots per week N weeks to plan, 1 slot peer week already planned, N left to plan
        int expectedAmountOfSlotsToPlan = 1 * timetable.getNumberOfWeeks();
        assertThat(response.length).isEqualTo(1);

        assertEquals(expectedCourse.getId(), response[0].getId());
        assertEquals(expectedAmountOfSlotsToPlan, response[0].getAmountOfSlotsToPlan());
        assertTrue(response[0].getDegreeOfFreedom() > -1);
    }

    @Test
    void givenGiven2CoursesNoEvents_whenGetAllCoursesStillToPlan_thenReturnAllCourses()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        createAndPersistRoomOne(timetable);

        Course c1 = TestDataUtil.createCourseOne(timetable);
        c1.setSlotsPerWeek(1);
        c1.setBlockSize(1);
        Course c2 = TestDataUtil.createCourseTwo(timetable);
        c2.setSlotsPerWeek(2);
        c2.setBlockSize(1);

        List<Timeslot> tss = TestDataUtil.generateTimeslots(timetable, 2);
        Timeslot t1 = tss.get(0);
        Timeslot t2 = tss.get(1);

        courseRepository.saveAndFlush(c1);
        courseRepository.saveAndFlush(c2);
        timeslotRepository.saveAndFlush(t1);
        timeslotRepository.saveAndFlush(t2);

        // when
        String url = format("/v1/timetable/%s/courses/toPlan", timetable.getId());
        CourseToPlanResDTO[] response = mockMvcTestUtil.get(url, CourseToPlanResDTO[].class);

        assertThat(response.length).isEqualTo(2);
    }

    private Employee createAndPersistEmployeeOne(Timetable timetable) {
        Employee employee = new Employee();
        employee.setFirstname("Brian W.");
        employee.setLastname("Kernighan");
        employee.setAbbreviation("bwk");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employee = employeeRepository.saveAndFlush(employee);

        assertThat(employee.getId()).isNotNull();

        return employee;
    }

    private Employee createAndPersistEmployeeTwo(Timetable timetable) {
        Employee employee = new Employee();
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setAbbreviation("rfp");
        employee.setEmployeeType(createAndPersistEmployeeTypeTwo());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employee = employeeRepository.saveAndFlush(employee);

        assertThat(employee.getId()).isNotNull();

        return employee;
    }

    private Room createAndPersistRoomOne(Timetable timetable) {
        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("Lecture Hall");

        roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Hoersaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("C0.02");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        assertThat(room.getId()).isNotNull();

        return room;
    }

    private Room createAndPersistRoomTwo(Timetable timetable) {
        RoomType roomType = new RoomType();
        roomType.setName("PC Pool");
        roomType.setOnline(false);

        roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Hörsaal 2");
        room.setAbbreviation("HS02");
        room.setIdentifier("C0.03");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        assertThat(room.getId()).isNotNull();

        return room;
    }

    private Room createAndPersistRoomThree(Timetable timetable) {
        RoomType roomType = new RoomType();
        roomType.setName("Fussballfeld");
        roomType.setOnline(false);

        roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Hörsaal 3");
        room.setAbbreviation("HS03");
        room.setIdentifier("C0.04");
        room.setCapacity(50);
        room.setRoomType(roomType);
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        assertThat(room.getId()).isNotNull();

        return room;
    }

    private Timetable createAndPersistTimetable() {
        Timetable timetable = TestDataUtil.createTimetableWS22();
        semesterTypeRepository.saveAndFlush(timetable.getSemesterType());
        timetableRepository.saveAndFlush(timetable);
        return timetable;
    }

    private CourseDetailResDTO mockMvcGetCourseById(UUID timetableID, UUID courseID)
            throws Exception {
        String url = format("/v1/timetable/%s/courses/%s", timetableID, courseID);
        return mockMvcTestUtil.get(url, CourseDetailResDTO.class);
    }

    private CourseDetailResDTO mockMvcPostCompleteCourse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Room givenRoomOne = createAndPersistRoomOne(timetable);
        Room givenRoomTwo = createAndPersistRoomTwo(timetable);

        Employee givenEmployee = createAndPersistEmployeeOne(timetable);

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomOne.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new HashSet<>(List.of(new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO.getSuitedRooms()
                .add(roomComboOneReqDTO);
        courseReqDTO.getSuitedRooms()
                .add(roomComboTwoReqDTO);

        courseReqDTO.getLecturers()
                .add(new IdWrapperDTO(givenEmployee.getId()));

        CourseDetailResDTO result =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        assertEquals(1, courseRepository.findAll()
                .size());
        assertTrue(courseRepository.existsById(result.getId()));
        assertEquals(2, roomCombinationRepository.findAll()
                .size());

        return result;
    }

    private Course createCourseOne() {
        Course course = new Course();
        course.setCasID("WS22SB037");
        course.setName("Lectures On Physics");
        course.setAbbreviation("Physics");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        return course;
    }

    private Course createCourseTwo(Timetable timetable) {
        Course course = new Course();
        course.setTimetable(timetable);
        course.setCasID("WS22SB011");
        course.setName("Recondite Architecture and Origami Map Folding");
        course.setAbbreviation("RAOMF");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        return course;
    }

    /**
     * Builds a request dto for a "flat" course that does only contain empty collections.
     *
     * @return an instance of {@link CourseReqDTO} without nested resources.
     */
    private CourseReqDTO buildDefaultCourseReqDTO() {
        return CourseReqDTO.builder()
                .casID("WS22D000")
                .name("Default")
                .abbreviation("DT")
                .description("description")
                .blockSize(1)
                .weeksPerSemester(12)
                .courseTimeslots(new ArrayList<>())
                .slotsPerWeek(2)
                .courseType(null)
                .build();
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
