package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Tests for the {@link com.fhwedel.softwareprojekt.v1.model.CourseRelation} persistence layer,
 * using an embedded in-memory database.
 */
@DataJpaTest
public class CourseRelationRepositoryTest {

    @Autowired private CourseRelationRepository courseRelationRepository;

    @Autowired private CourseRepository courseRepository;

    @Autowired private TimetableRepository timetableRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    /**
     * Autowired Test-EntityManger, helpful for setting up persistence layer tests, especially when
     * having multi-entity-relationships.
     */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void givenCoursedAndRelationType_whenSaveCourseRelation_thenSuccess() {
        // given
        Timetable timetable = createAndPersistTimetable();

        Course courseA = new Course();
        courseA.setWeeksPerSemester(1);
        courseA.setCourseType(null);
        courseA.setCasID("casID");
        courseA.setBlockSize(1);
        courseA.setName("e");
        courseA.setSlotsPerWeek(1);
        courseA.setAbbreviation("e");
        courseA.setTimetable(timetable);

        Course courseB = new Course();
        courseB.setWeeksPerSemester(1);
        courseB.setCourseType(null);
        courseB.setCasID("casID3");
        courseB.setBlockSize(1);
        courseB.setName("e2");
        courseB.setSlotsPerWeek(1);
        courseB.setAbbreviation("e2");
        courseB.setTimetable(timetable);

        // when
        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelation.setCourseA(courseA);
        courseRelation.setCourseB(courseB);

        testEntityManager.persist(courseA);
        testEntityManager.persist(courseB);
        courseRelation = courseRelationRepository.saveAndFlush(courseRelation);
        // then
        assertNotNull(courseRelation);
        assertNotNull(courseRelation.getId());
    }

    @Test
    void whenSaveCourseRelationAndCourseAIsNull_thenThrowDataIntegrityViolationException() {
        // given
        Timetable timetable = createAndPersistTimetable();

        Course courseB = new Course();
        courseB.setWeeksPerSemester(1);
        courseB.setCourseType(null);
        courseB.setCasID("casID3");
        courseB.setBlockSize(1);
        courseB.setName("e2");
        courseB.setSlotsPerWeek(1);
        courseB.setAbbreviation("e2");
        courseB.setTimetable(timetable);

        // when
        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelation.setCourseA(null);
        courseRelation.setCourseB(courseB);

        testEntityManager.persist(courseB);
        assertThatThrownBy(() -> courseRelationRepository.saveAndFlush(courseRelation))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void whenSaveCourseRelationAndCourseBIsNull_thenThrowDataIntegrityViolationException() {
        // given
        Course courseA = new Course();
        courseA.setWeeksPerSemester(1);
        courseA.setCourseType(null);
        courseA.setCasID("casID");
        courseA.setBlockSize(1);
        courseA.setName("e");
        courseA.setSlotsPerWeek(1);
        courseA.setAbbreviation("e");

        // when
        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelation.setCourseA(courseA);
        courseRelation.setCourseB(null);

        testEntityManager.persist(courseA);

        assertThatThrownBy(() -> courseRelationRepository.saveAndFlush(courseRelation))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void whenSaveCourseRelationAndRelationTypeIsNull_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = createAndPersistTimetable();

        Course courseA = new Course();
        courseA.setWeeksPerSemester(1);
        courseA.setCourseType(null);
        courseA.setCasID("casID");
        courseA.setBlockSize(1);
        courseA.setName("e");
        courseA.setSlotsPerWeek(1);
        courseA.setAbbreviation("e");
        courseA.setTimetable(timetable);

        Course courseB = new Course();
        courseB.setWeeksPerSemester(1);
        courseB.setCourseType(null);
        courseB.setCasID("casID3");
        courseB.setBlockSize(1);
        courseB.setName("e2");
        courseB.setSlotsPerWeek(1);
        courseB.setAbbreviation("e2");
        courseB.setTimetable(timetable);

        // when
        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setRelationType(null);
        courseRelation.setCourseA(courseA);
        courseRelation.setCourseB(courseB);

        testEntityManager.persist(courseA);
        testEntityManager.persist(courseB);
        assertThatThrownBy(() -> courseRelationRepository.saveAndFlush(courseRelation))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void
            givenMultipleCourseRelations_findByRelationTypeAndCourseA_IdAndCourseB_Id_thenReturnCorrectResult() {
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

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationOne.setCourseA(courseOne);
        courseRelationOne.setCourseB(courseTwo);

        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setRelationType(RelationType.MUST_BE_PARALLEL);
        courseRelationTwo.setCourseA(courseThree);
        courseRelationTwo.setCourseB(courseOne);

        courseRelationOne = courseRelationRepository.saveAndFlush(courseRelationOne);
        courseRelationRepository.saveAndFlush(courseRelationTwo);

        // when
        // find courseRelationOne
        Optional<CourseRelation> result =
                courseRelationRepository.findByRelationTypeAndCourseA_IdAndCourseB_Id(
                        RelationType.MAY_BE_PARALLEL, courseOne.getId(), courseTwo.getId());

        assertThat(result.isPresent()).isTrue();
        if (result.isPresent()) {
            assertThat(result.get().getId()).isEqualTo(courseRelationOne.getId());
        }
    }

    @Test
    void whenUniqueConstraintViolated_thenThrowException() {
        // unique constraint on the combination of relationType, courseA and courseB
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationOne.setCourseA(courseOne);
        courseRelationOne.setCourseB(courseTwo);

        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setRelationType(RelationType.MUST_BE_PARALLEL);
        courseRelationTwo.setCourseA(courseOne);
        courseRelationTwo.setCourseB(courseTwo);

        CourseRelation notUniqueCourseRelation = new CourseRelation();
        notUniqueCourseRelation.setRelationType(RelationType.MAY_BE_PARALLEL);
        notUniqueCourseRelation.setCourseA(courseOne);
        notUniqueCourseRelation.setCourseB(courseTwo);

        // this should work
        courseRelationRepository.saveAndFlush(courseRelationOne);
        courseRelationRepository.saveAndFlush(courseRelationTwo);

        assertEquals(2, courseRelationRepository.findAll().size());

        // when
        assertThatThrownBy(() -> courseRelationRepository.saveAndFlush(notUniqueCourseRelation))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void whenDeleteCourse_thenCascadeRemovalOfCourseRelations() {
        // given
        Timetable timetable = createAndPersistTimetable();
        Course courseOne = createCourseOne();
        courseOne.setTimetable(timetable);
        courseOne = courseRepository.saveAndFlush(courseOne);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        courseTwo = courseRepository.saveAndFlush(courseTwo);

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationOne.setCourseA(courseOne);
        courseRelationOne.setCourseB(courseTwo);

        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationTwo.setCourseA(courseTwo);
        courseRelationTwo.setCourseB(courseOne);

        courseRelationOne = testEntityManager.persistAndFlush(courseRelationOne);
        courseRelationTwo = testEntityManager.persistAndFlush(courseRelationTwo);

        courseOne.getCourseRelationsA().add(courseRelationOne);
        courseOne.getCourseRelationsB().add(courseRelationTwo);

        // assert TestSetup
        assertEquals(2, courseRelationRepository.findAll().size());

        // when
        courseRepository.delete(courseOne);

        // then
        assertNull(testEntityManager.find(Course.class, courseOne.getId()));
        assertEquals(0, courseRelationRepository.findAll().size());
    }

    private Timetable createAndPersistTimetable() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        Timetable timetable = new Timetable();
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);
        timetable = timetableRepository.saveAndFlush(timetable);
        assertThat(timetable.getId()).isNotNull();
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
        course.setCasID("WS22OL033");
        course.setName("Ornithology");
        course.setAbbreviation("OOO");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());
        return course;
    }
}
