package com.fhwedel.softwareprojekt.v1.integration;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.CourseController;
import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseBasicResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseDetailResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRelationReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration Tests for the {@link CourseController} across all layers to test end-to-end behaviour
 * regarding the creation and removal of {@link CourseRelation}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class CourseRelationIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseRelationRepository courseRelationRepository;
    @Autowired private TimetableRepository timetableRepository;
    @Autowired private CourseConverter courseConverter;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @AfterEach
    void rollbackDB() {
        courseRelationRepository.deleteAll();
        courseRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void givenCourseWithCourseRelations_whenGetCourseById_thenCorrectResponse() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);
        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);
        courseThree = courseRepository.saveAndFlush(courseThree);

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(
                                        List.of(
                                                new IdWrapperDTO(courseOne.getId()),
                                                new IdWrapperDTO(courseTwo.getId()),
                                                new IdWrapperDTO(courseThree.getId()))))
                        .mustBeHeldBefore(
                                new HashSet<>(List.of(new IdWrapperDTO(courseThree.getId()))))
                        .build();

        CourseReqDTO givenCourseReqDTO = buildDefaultCourseReqDTO();
        givenCourseReqDTO.setCourseRelations(courseRelationReqDTO);

        CourseDetailResDTO postResponse = mockMvcPostCourse(givenCourseReqDTO, timetable);

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), postResponse.getId());
        CourseDetailResDTO response = mockMvcTestUtil.get(url, CourseDetailResDTO.class);
        // then
        assertEquals(postResponse, response);

        // verify also the relationships of the other three courses
        CourseDetailResDTO responseCourseOne =
                mockMvcGetCourseById(timetable.getId(), courseOne.getId());
        assertEquals(1, responseCourseOne.getCourseRelations().getMayBeParallelTo().size());

        CourseDetailResDTO responseCourseTwo =
                mockMvcGetCourseById(timetable.getId(), courseTwo.getId());
        assertEquals(1, responseCourseTwo.getCourseRelations().getMayBeParallelTo().size());

        CourseDetailResDTO responseCourseThree =
                mockMvcGetCourseById(timetable.getId(), courseThree.getId());
        assertEquals(1, responseCourseThree.getCourseRelations().getMayBeParallelTo().size());
    }

    @Test
    void whenPostCourseWithMustBeHeldBeforeRelation_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mustBeHeldBefore(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22C002")
                        .name("Creative Uncertainty")
                        .abbreviation("CU")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseTimeslots(new ArrayList<>())
                        .courseType(null)
                        .courseRelations(courseRelationReqDTO)
                        .build();

        // when
        CourseDetailResDTO postedCourse =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertEquals(1, postedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, postedCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(
                courseOne.getId(),
                postedCourse.getCourseRelations().getMustBeHeldBefore().get(0).getId());

        List<CourseRelation> persistedCourseRelations = courseRelationRepository.findAll();
        assertEquals(1, persistedCourseRelations.size());

        CourseRelation courseRelation = persistedCourseRelations.get(0);
        assertEquals(RelationType.A_MUST_BE_BEFORE_B, courseRelation.getRelationType());
        assertEquals(courseOne.getId(), courseRelation.getCourseB().getId());
        assertEquals(postedCourse.getId(), courseRelation.getCourseA().getId());
    }

    @Test
    void whenPostCourseWithMustBeHeldAfterRelation_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mustBeHeldAfter(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22C002")
                        .name("Creative Uncertainty")
                        .abbreviation("CU")
                        .description("description")
                        .blockSize(1)
                        .courseTimeslots(new ArrayList<>())
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseRelations(courseRelationReqDTO)
                        .build();

        // when
        CourseDetailResDTO postedCourse =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertEquals(0, postedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, postedCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(
                courseOne.getId(),
                postedCourse.getCourseRelations().getMustBeHeldAfter().get(0).getId());

        List<CourseRelation> persistedCourseRelations = courseRelationRepository.findAll();
        assertEquals(1, persistedCourseRelations.size());

        CourseRelation courseRelation = persistedCourseRelations.get(0);
        assertEquals(RelationType.A_MUST_BE_BEFORE_B, courseRelation.getRelationType());
        assertEquals(postedCourse.getId(), courseRelation.getCourseB().getId());
        assertEquals(courseOne.getId(), courseRelation.getCourseA().getId());
    }

    @Test
    void whenPostCourse_withSameMustBeHeldAfterAndMustBeHeldBeforeRelation_thenCorrectResponse()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mustBeHeldAfter(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .mustBeHeldBefore(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22C002")
                        .name("Creative Uncertainty")
                        .abbreviation("CU")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseTimeslots(new ArrayList<>())
                        .courseType(null)
                        .courseRelations(courseRelationReqDTO)
                        .build();

        // when
        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertEquals(1, response.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, response.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(
                courseOne.getId(),
                response.getCourseRelations().getMustBeHeldAfter().get(0).getId());
        assertEquals(
                courseOne.getId(),
                response.getCourseRelations().getMustBeHeldAfter().get(0).getId());

        List<CourseRelation> persistedCourseRelations = courseRelationRepository.findAll();
        assertEquals(2, persistedCourseRelations.size());
    }

    @Test
    void whenPostCourseWithMayBeParallelRelation_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22C002")
                        .name("Creative Uncertainty")
                        .abbreviation("CU")
                        .description("description")
                        .blockSize(1)
                        .courseTimeslots(new ArrayList<>())
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseRelations(courseRelationReqDTO)
                        .build();

        // when
        CourseDetailResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/courses", timetable.getId()),
                        CourseDetailResDTO.class,
                        courseReqDTO);

        // then
        assertEquals(1, response.getCourseRelations().getMayBeParallelTo().size());

        assertEquals(
                courseOne.getId(),
                response.getCourseRelations().getMayBeParallelTo().get(0).getId());

        List<CourseRelation> persistedCourseRelations = courseRelationRepository.findAll();
        assertEquals(1, persistedCourseRelations.size());

        CourseRelation courseRelation = persistedCourseRelations.get(0);
        assertEquals(RelationType.MAY_BE_PARALLEL, courseRelation.getRelationType());
        assertEquals(courseOne.getId(), courseRelation.getCourseB().getId());
        assertEquals(response.getId(), courseRelation.getCourseA().getId());
    }

    @Test
    void givenCourseWithMustBeBeforeAndMustBeAfterRelations_whenPutCourse_thenDeleteRelation()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);
        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldBefore(new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))));
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldAfter(new HashSet<>(List.of(new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO targetCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(1, targetCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, targetCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                courseOne.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                courseTwo.getId(),
                                targetCourse.getId())
                        .isPresent());

        // when
        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        updateCourseReqDTO.getCourseRelations().setMustBeHeldBefore(new HashSet<>()); // empty set
        updateCourseReqDTO.getCourseRelations().setMustBeHeldAfter(new HashSet<>()); // empty set

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, updateCourseReqDTO);

        assertEquals(0, updatedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, updatedCourse.getCourseRelations().getMustBeHeldAfter().size());
        assertEquals(0, courseRelationRepository.findAll().size());
    }

    @Test
    void
            givenCourseWithMustBeHeldBeforeAndAfterRelation_whenPutSameCourse_thenCorrectlyKeepCourseRelations()
                    throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldBefore(new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))));
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldAfter(new HashSet<>(List.of(new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO targetCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(1, targetCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, targetCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                courseOne.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                courseTwo.getId(),
                                targetCourse.getId())
                        .isPresent());
        assertEquals(2, courseRelationRepository.findAll().size());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        assertEquals(1, updatedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, updatedCourse.getCourseRelations().getMustBeHeldAfter().size());
        assertEquals(2, courseRelationRepository.findAll().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                courseOne.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                courseTwo.getId(),
                                targetCourse.getId())
                        .isPresent());
    }

    @Test
    void givenCourse_whenPutCourseWithMustBeHeldBeforeRelations_thenCorrectlyAddCourseRelations()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();

        CourseDetailResDTO targetCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(0, courseRelationRepository.findAll().size());

        // when
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldBefore(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()),
                                        new IdWrapperDTO(courseTwo.getId()))));

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        assertEquals(2, updatedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, updatedCourse.getCourseRelations().getMustBeHeldAfter().size());
        assertEquals(
                2,
                courseRelationRepository
                        .findByCourseA_IdOrCourseB_Id(updatedCourse.getId(), updatedCourse.getId())
                        .size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                courseOne.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
    }

    @Test
    void givenCourse_whenPutCourseWithMustBeHeldBeforeAndAfterRelationToItself_thenCreateRelation()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();

        CourseDetailResDTO targetCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(0, courseRelationRepository.findAll().size());

        // when
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldBefore(
                        new HashSet<>(List.of(new IdWrapperDTO(targetCourse.getId()))));
        courseReqDTO
                .getCourseRelations()
                .setMustBeHeldAfter(new HashSet<>(List.of(new IdWrapperDTO(targetCourse.getId()))));

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        assertEquals(1, courseRelationRepository.findAll().size());
        assertEquals(1, updatedCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(1, updatedCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.A_MUST_BE_BEFORE_B,
                                targetCourse.getId(),
                                targetCourse.getId())
                        .isPresent());
    }

    @Test
    void
            givenCourseWithPutCourseWithMayBeParallelRelationToItself_whenPutCourseWithoutRel_thenDeleteRelation()
                    throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();

        CourseDetailResDTO targetCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldBefore().size());
        assertEquals(0, targetCourse.getCourseRelations().getMustBeHeldAfter().size());

        assertEquals(0, courseRelationRepository.findAll().size());

        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(new HashSet<>(List.of(new IdWrapperDTO(targetCourse.getId()))));

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);
        assertEquals(1, updatedCourse.getCourseRelations().getMayBeParallelTo().size());
        assertEquals(1, courseRelationRepository.findAll().size());

        // when
        url = format("/v1/timetable/%s/courses/%s", timetable.getId(), targetCourse.getId());
        courseReqDTO = buildDefaultCourseReqDTO();
        updatedCourse = mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        assertEquals(0, updatedCourse.getCourseRelations().getMayBeParallelTo().size());
        assertEquals(0, courseRelationRepository.findAll().size());
    }

    @Test
    void givenCourseWithMayBeParallelRelation_whenPutCourse_thenCorrectlyChangeCourseRelations()
            throws Exception {
        // remove one relation, add one
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(new HashSet<>(List.of(new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO givenCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(1, givenCourse.getCourseRelations().getMayBeParallelTo().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());

        // when
        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);
        courseThree = courseRepository.saveAndFlush(courseThree);
        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        updateCourseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(new HashSet<>(List.of(new IdWrapperDTO(courseThree.getId()))));

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, updateCourseReqDTO);

        assertEquals(1, givenCourse.getCourseRelations().getMayBeParallelTo().size());
        assertFalse(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseThree.getId())
                        .isPresent());

        assertEquals(1, updatedCourse.getCourseRelations().getMayBeParallelTo().size());
        assertEquals(
                courseThree.getId(),
                updatedCourse.getCourseRelations().getMayBeParallelTo().get(0).getId());
    }

    @Test
    void
            givenCourseWithMultipleMayBeParallelRelation_whenPutCourse_thenCorrectlyChangeCourseRelations()
                    throws Exception {
        // remove one relation, keep one, add one
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()),
                                        new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO givenCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(2, givenCourse.getCourseRelations().getMayBeParallelTo().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());

        // when
        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);
        courseThree = courseRepository.saveAndFlush(courseThree);

        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        updateCourseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()), // keep Course One
                                        new IdWrapperDTO(courseThree.getId()) // add Course Three
                                        )));

        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, updateCourseReqDTO);

        assertEquals(2, givenCourse.getCourseRelations().getMayBeParallelTo().size());
        assertFalse(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseThree.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());

        assertEquals(2, updatedCourse.getCourseRelations().getMayBeParallelTo().size());

        CourseBasicResDTO expectedCourseOne =
                courseConverter.convertCourseEntityToBasicResDTO(courseOne);
        CourseBasicResDTO expectedCourseThree =
                courseConverter.convertCourseEntityToBasicResDTO(courseThree);
        assertTrue(
                updatedCourse
                        .getCourseRelations()
                        .getMayBeParallelTo()
                        .contains(expectedCourseOne));
        assertTrue(
                updatedCourse
                        .getCourseRelations()
                        .getMayBeParallelTo()
                        .contains(expectedCourseThree));
    }

    @Test
    void givenCourseWithCourseRelations_whenPutSameCourse_thenNoChanges() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()),
                                        new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO givenCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(2, givenCourse.getCourseRelations().getMayBeParallelTo().size());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());

        // when
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        CourseDetailResDTO updatedCourse =
                mockMvcTestUtil.put(url, CourseDetailResDTO.class, courseReqDTO);

        // then
        assertEquals(2, updatedCourse.getCourseRelations().getMayBeParallelTo().size());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());
    }

    @Test
    void givenCourseWithCourseRelations_whenPutCourseWithInvalidCourseIDs_thenNoChanges()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()),
                                        new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO givenCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(2, courseRelationRepository.findAll().size());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());

        // when
        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        updateCourseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(UUID.randomUUID()) // invalid course ID
                                        )));
        String url = format("/v1/timetable/%s,courses/%s", timetable.getId(), givenCourse.getId());
        mockMvc.perform(MockMvcTestUtil.put(url, updateCourseReqDTO))
                .andExpect(status().isNotFound());
        // then
        assertEquals(2, courseRelationRepository.findAll().size());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());
    }

    @Test
    void givenCourseWithRelation_whenPutCourseWithoutRelations_thenCorrectlyDeleteCourseRelations()
            throws Exception {
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseReqDTO courseReqDTO = buildDefaultCourseReqDTO();
        courseReqDTO
                .getCourseRelations()
                .setMayBeParallelTo(
                        new HashSet<>(
                                List.of(
                                        new IdWrapperDTO(courseOne.getId()),
                                        new IdWrapperDTO(courseTwo.getId()))));

        CourseDetailResDTO givenCourse = mockMvcPostCourse(courseReqDTO, timetable);

        assertEquals(2, givenCourse.getCourseRelations().getMayBeParallelTo().size());

        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseTwo.getId())
                        .isPresent());
        assertTrue(
                courseRelationRepository
                        .findByRelationTypeAndCourseA_IdAndCourseB_Id(
                                RelationType.MAY_BE_PARALLEL,
                                givenCourse.getId(),
                                courseOne.getId())
                        .isPresent());

        // when
        CourseReqDTO updateCourseReqDTO = buildDefaultCourseReqDTO();
        String url = format("/v1/timetable/%s/courses/%s", timetable.getId(), givenCourse.getId());
        mockMvcTestUtil.put(url, CourseDetailResDTO.class, updateCourseReqDTO);

        assertEquals(0, courseRelationRepository.findAll().size());
    }

    private CourseDetailResDTO mockMvcPostCourse(CourseReqDTO courseReqDTO, Timetable timetable)
            throws Exception {
        return mockMvcTestUtil.post(
                format("/v1/timetable/%s/courses", timetable.getId()),
                CourseDetailResDTO.class,
                courseReqDTO);
    }

    private CourseDetailResDTO mockMvcGetCourseById(UUID timetableID, UUID courseID)
            throws Exception {
        String url = format("/v1/timetable/%s/courses/%s", timetableID, courseID);
        return mockMvcTestUtil.get(url, CourseDetailResDTO.class);
    }

    private Timetable createAndPersistTimetable() {
        Timetable timetable = TestDataUtil.createTimetableWS22();
        semesterTypeRepository.saveAndFlush(timetable.getSemesterType());
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
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

    private Course createCourseTwo() {
        Course course = new Course();
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

    private Course createCourseThree() {
        Course course = new Course();
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

        return course;
    }

    private CourseReqDTO buildDefaultCourseReqDTO() {
        return CourseReqDTO.builder()
                .casID("WS22D000")
                .name("Default")
                .abbreviation("DT")
                .description("description")
                .blockSize(1)
                .weeksPerSemester(12)
                .slotsPerWeek(2)
                .courseTimeslots(new ArrayList<>())
                .courseType(null)
                .build();
    }
}
