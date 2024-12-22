package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.delete;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.DegreeSemesterConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterResDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration Tests for the Degree Controller across all layers to test end-to-end behaviour.
 *
 * <p>The `@SpringBootTest` annotation tells Spring Boot to load the entire application context. The
 * use of <code>WebEnvironment.RANDOM_PORT</code> starts a server with a random port.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class DegreeSemesterIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;

    @Autowired private DegreeRepository degreeRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Autowired private CourseRepository courseRepository;

    @Autowired private TimetableRepository timetableRepository;

    /** Autowired converter spares us from having to convert manually between Entity and DTO */
    @Autowired private DegreeSemesterConverter semesterConverter;

    @AfterEach
    void rollbackDB() {
        degreeSemesterRepository.deleteAll();
        degreeRepository.deleteAll();
        courseRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void whenPostSemesterWithoutDegreesAndCourses_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        // when
        DegreeSemesterResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/semesters", timetable.getId()),
                        DegreeSemesterResDTO.class,
                        semesterReqDTO);

        // then
        assertNotNull(response.getId());
        assertEquals(semesterReqDTO.getExtensionName(), response.getExtensionName());
    }

    @Test
    void whenPostSemesterWithExistingDegrees_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("14");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .degree(degree.getId())
                        .build();

        // when
        DegreeSemesterResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/semesters", timetable.getId()),
                        DegreeSemesterResDTO.class,
                        semesterReqDTO);

        // then
        assertNotNull(response.getId());
        assertEquals(semesterReqDTO.getExtensionName(), response.getExtensionName());
        assertEquals(degree.getName(), response.getDegree().getName());
    }

    @Test
    void whenPostSemesterWithExistingCourses_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Course course = new Course();
        course.setCourseType(null);
        course.setAbbreviation("name");
        course.setName("name");
        course.setCasID("name");
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setTimetable(timetable);
        course.setBlockSize(1);

        course = courseRepository.saveAndFlush(course);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .courses(List.of(course.getId()))
                        .build();

        // when
        DegreeSemesterResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/semesters", timetable.getId()),
                        DegreeSemesterResDTO.class,
                        semesterReqDTO);

        // then
        assertNotNull(response.getId());
        assertEquals(semesterReqDTO.getExtensionName(), response.getExtensionName());
        assertEquals(course.getName(), response.getCourses().get(0).getName());
    }

    @Test
    void whenPostSemesterWithExistingCoursesAndDegrees_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("14");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);

        Course course = new Course();
        course.setCourseType(null);
        course.setAbbreviation("name");
        course.setName("name");
        course.setCasID("name");
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setBlockSize(1);
        course.setTimetable(timetable);

        course = courseRepository.saveAndFlush(course);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .courses(List.of(course.getId()))
                        .degree(degree.getId())
                        .build();

        // when
        DegreeSemesterResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/semesters", timetable.getId()),
                        DegreeSemesterResDTO.class,
                        semesterReqDTO);

        // then
        assertNotNull(response.getId());
        assertEquals(semesterReqDTO.getExtensionName(), response.getExtensionName());
        assertEquals(course.getName(), response.getCourses().get(0).getName());
    }

    @Test
    void whenPostSemesterWithExistingCoursesAndNonExistingDegrees_thenBadRequest()
            throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Course course = new Course();
        course.setCourseType(null);
        course.setAbbreviation("name");
        course.setName("name");
        course.setCasID("name");
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setBlockSize(1);
        course.setTimetable(timetable);

        course = courseRepository.saveAndFlush(course);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .courses(List.of(course.getId()))
                        .degree(UUID.randomUUID())
                        .build();

        // when / then
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPostSemesterWithExistingDegreesAndNonExistingCourses_thenBadRequest()
            throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("14");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .degree(degree.getId())
                        .courses(List.of(UUID.randomUUID()))
                        .build();

        // when / then
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenSemesters_whenGetSemesters_thenReturnListOfSemesters() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setCourses(new ArrayList<>());
        semester.setTimetable(timetable);

        DegreeSemester semester2 = new DegreeSemester();
        semester2.setId(UUID.randomUUID());
        semester2.setSemesterNumber(1);
        semester2.setAttendees(1);
        semester2.setExtensionName("test");
        semester2.setCourses(new ArrayList<>());
        semester2.setTimetable(timetable);

        semester = degreeSemesterRepository.saveAndFlush(semester);
        semester2 = degreeSemesterRepository.saveAndFlush(semester2);

        // when
        DegreeSemesterResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/semesters", timetable.getId()),
                        DegreeSemesterResDTO[].class);

        // then
        assertEquals(2, response.length);
        assertEquals(semesterConverter.convertEntityToResponseDTO(semester), response[0]);
        assertEquals(semesterConverter.convertEntityToResponseDTO(semester2), response[1]);
    }

    @Test
    public void givenSemester_whenGetSemesterById_thenReturnSemester() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setCourses(new ArrayList<>());
        semester.setTimetable(timetable);

        semester = degreeSemesterRepository.saveAndFlush(semester);

        DegreeSemesterResDTO expected = semesterConverter.convertEntityToResponseDTO(semester);

        // when
        String url = format("/v1/timetable/%s/semesters/%s", timetable.getId(), semester.getId());
        DegreeSemesterResDTO response = mockMvcTestUtil.get(url, DegreeSemesterResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenSemester_whenPatchSemesterExtName_thenResponseOk() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setCourses(new ArrayList<>());
        semester.setTimetable(timetable);

        semester = degreeSemesterRepository.saveAndFlush(semester);

        DegreeSemesterReqDTO requestDTO = semesterConverter.convertEntityToDto(semester);
        requestDTO.setExtensionName("TEST2");

        // when
        String url = format("/v1/timetable/%s/semesters/%s", timetable.getId(), semester.getId());
        DegreeSemesterResDTO response =
                mockMvcTestUtil.patch(url, DegreeSemesterResDTO.class, requestDTO);

        // then
        DegreeSemester savedSemester =
                degreeSemesterRepository.findById(semester.getId()).orElse(new DegreeSemester());
        assertThat(savedSemester.getExtensionName()).isEqualTo("TEST2");
        assertEquals(requestDTO.getExtensionName(), response.getExtensionName());
    }

    @Test
    public void givenSemester_whenDeleteSemester_thenNoContentResponse() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setCourses(new ArrayList<>());
        semester.setTimetable(timetable);

        semester = degreeSemesterRepository.saveAndFlush(semester);

        // when
        String url = format("/v1/timetable/%s/semesters/%s", timetable.getId(), semester.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(degreeSemesterRepository.existsById(semester.getId())).isFalse();
    }

    @Test
    public void whenDeleteWithDegreeAndSemestersBuildingRelation_thenRemoveCascade()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .semesterAmount(1)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetable.getId()), degreeReqDTO))
                .andExpect(status().isOk());

        Degree degree = degreeRepository.findAll().get(0);

        DegreeSemesterReqDTO degreeSemesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .extensionName("s")
                        .attendees(1)
                        .degree(degree.getId())
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                degreeSemesterReqDTO))
                .andExpect(status().isOk());

        DegreeSemester degreeSemester = degreeSemesterRepository.findAll().get(0);

        // when
        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), degree.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(degreeRepository.findAll().size()).isEqualTo(0);
        assertThat(degreeSemesterRepository.findById(degreeSemester.getId()).isPresent())
                .isEqualTo(false);
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
