package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.DegreeConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SchoolTypeRepository;
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
public class DegreeIntegrationTest {
    /** Autoconfigured version of the WebTestClient */
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private DegreeRepository degreeRepository;

    @Autowired private TimetableRepository timetableRepository;
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    /** Autowired converter spares us from having to convert manually between Entity and DTO */
    @Autowired private DegreeConverter degreeConverter;

    @Autowired private SchoolTypeRepository schoolTypeRepository;

    @AfterEach
    void rollbackDB() {
        degreeRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        schoolTypeRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void whenPostDegreeWithoutSemesters_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();
        // when
        DegreeResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/degrees", timetable.getId()),
                        DegreeResDTO.class,
                        degreeReqDTO);

        // then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(degreeReqDTO.getName(), response.getName());
    }

    @Test
    void whenPostDegreeWithSchoolType_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        SchoolType st = new SchoolType();
        st.setName("type-test");
        st = schoolTypeRepository.saveAndFlush(st);

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(st.getId())
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();
        // when
        DegreeResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/degrees", timetable.getId()),
                        DegreeResDTO.class,
                        degreeReqDTO);

        // then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(degreeReqDTO.getName(), response.getName());
        assertEquals(st.getName(), response.getSchoolType().getName());
        assertEquals(st.getId(), response.getSchoolType().getId());
    }

    @Test
    void whenPostDegreeWithExistingSemesters_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester degreeSemester = new DegreeSemester();
        degreeSemester.setSemesterNumber(6);
        degreeSemester.setExtensionName("ext");
        degreeSemester.setAttendees(1000);
        degreeSemester.setTimetable(timetable);
        degreeSemester = degreeSemesterRepository.saveAndFlush(degreeSemester);

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .semesters(List.of(degreeSemester.getId()))
                        .build();

        // when
        DegreeResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/degrees", timetable.getId()),
                        DegreeResDTO.class,
                        degreeReqDTO);

        // then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(degreeReqDTO.getName(), response.getName());
        assertEquals(
                degreeSemester.getExtensionName(),
                response.getSemesters().get(0).getExtensionName());
    }

    @Test
    void whenPostDegreeWithNonExistingSemesters_thenBadRequestResponse() throws Exception {
        persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .semesters(List.of(UUID.randomUUID()))
                        .build();

        // when
        mockMvc.perform(post("/v1/degrees", degreeReqDTO)).andExpect(status().isNotFound());

        assertThat(degreeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void givenDegrees_whenGetDegrees_thenReturnListOfDegrees() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);

        Degree degree2 = new Degree();
        degree2.setId(UUID.randomUUID());
        degree2.setName("degree2");
        degree2.setSchoolType(null);
        degree2.setStudyRegulation("14.0");
        degree2.setShortName("degree");
        degree2.setSemesterAmount(7);
        degree2.setSemesters(new ArrayList<>());
        degree2.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);
        degree2 = degreeRepository.saveAndFlush(degree2);

        // when
        DegreeResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/degrees", timetable.getId()),
                        DegreeResDTO[].class);

        // then
        assertEquals(2, response.length);
        assertEquals(degreeConverter.convertEntityToResponseDTO(degree), response[0]);
        assertEquals(degreeConverter.convertEntityToResponseDTO(degree2), response[1]);
    }

    @Test
    public void givenDegree_whenGetDegreeById_thenReturnDegree() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);

        DegreeResDTO expResponseBody = degreeConverter.convertEntityToResponseDTO(degree);

        // when
        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), degree.getId());
        DegreeResDTO response = mockMvcTestUtil.get(url, DegreeResDTO.class);

        // then
        assertEquals(expResponseBody, response);
    }

    @Test
    public void givenDegree_whenPatchDegreeName_thenResponseOk() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);
        degree = degreeRepository.saveAndFlush(degree);

        DegreeReqDTO requestDTO = degreeConverter.convertEntityToDto(degree);
        requestDTO.setName("TEST");

        // when
        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), degree.getId());
        DegreeResDTO response = mockMvcTestUtil.patch(url, DegreeResDTO.class, requestDTO);

        Degree savedDegree = degreeRepository.findById(degree.getId()).orElse(new Degree());
        assertEquals("TEST", savedDegree.getName());
        assertEquals("TEST", response.getName());
    }

    @Test
    public void givenDegree_whenDeleteDegree_thenNoContentResponse() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(new ArrayList<>());
        degree.setTimetable(timetable);

        degree = degreeRepository.saveAndFlush(degree);

        // when
        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), degree.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        assertFalse(degreeRepository.existsById(degree.getId()));
    }

    @Test
    public void whenGetNotExistingDegree_thenNotFoundResponse() throws Exception {
        String url = format("/v1/degrees/%s", UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingDegree_thenNotFoundResponse() throws Exception {
        String url = format("/v1/degrees/%s", UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingDegree_thenNotFoundResponse() throws Exception {
        persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        String url = format("/v1/degrees/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, degreeReqDTO)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteWithSemesters_thenRemoveCascade() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemesterReqDTO degreeSemesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .extensionName("s")
                        .attendees(1)
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                degreeSemesterReqDTO))
                .andExpect(status().isOk());

        DegreeSemester degreeSemester = degreeSemesterRepository.findAll().get(0);

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .semesterAmount(1)
                        .semesters(List.of(degreeSemester.getId()))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetable.getId()), degreeReqDTO))
                .andExpect(status().isOk());

        Degree degree = degreeRepository.findAll().get(0);

        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), degree.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

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
