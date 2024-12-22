package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Unit Tests for the {@link com.fhwedel.softwareprojekt.v1.model.DegreeSemester} persistence layer,
 * using an embedded in-memory database.
 */
@DataJpaTest
public class DegreeSemesterRepositoryTest {
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;

    /**
     * Autowired Test-EntityManger, helpful for setting up persistence layer tests, especially when
     * having multi-entity-relationships.
     */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void givenSemesterWithoutDegreeAndCourses_whenSaveCourseRelation_thenSuccess() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setExtensionName("name");
        semester.setSemesterNumber(1);
        semester.setTimetable(timetable);
        // when
        semester = degreeSemesterRepository.saveAndFlush(semester);
        // then
        assertNotNull(semester);
        assertNotNull(semester.getId());
    }

    @Test
    void givenSemesterWithDegree_whenSaveCourseRelation_thenSuccess() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("14");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setDegree(degree);
        semester.setExtensionName("name");
        semester.setSemesterNumber(1);
        semester.setTimetable(timetable);
        // when
        testEntityManager.persist(degree);
        semester = degreeSemesterRepository.saveAndFlush(semester);
        // then
        assertNotNull(semester);
        assertNotNull(semester.getId());
    }

    @Test
    void givenSemesterWithCourses_whenSaveCourseRelation_thenSuccess() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Course course = new Course();
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setCasID("casID3");
        course.setBlockSize(1);
        course.setName("e2");
        course.setSlotsPerWeek(1);
        course.setAbbreviation("e2");
        course.setTimetable(timetable);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setSemesterNumber(1);
        semester.setExtensionName("name");
        semester.setCourses(courses);
        semester.setTimetable(timetable);

        // when
        testEntityManager.persist(course);
        semester = degreeSemesterRepository.saveAndFlush(semester);
        // then
        assertNotNull(semester);
        assertNotNull(semester.getId());
    }

    @Test
    void givenSemesterWithDegreeAndCourses_whenSaveCourseRelation_thenSuccess() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("name");
        degree.setShortName("name");
        degree.setStudyRegulation("14");
        degree.setSchoolType(null);
        degree.setTimetable(timetable);

        Course course = new Course();
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setCasID("casID3");
        course.setBlockSize(1);
        course.setName("e2");
        course.setSlotsPerWeek(1);
        course.setAbbreviation("e2");
        course.setTimetable(timetable);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setDegree(degree);
        semester.setExtensionName("name");
        semester.setCourses(courses);
        semester.setSemesterNumber(1);
        semester.setTimetable(timetable);

        // when
        testEntityManager.persist(degree);
        testEntityManager.persist(course);
        semester = degreeSemesterRepository.saveAndFlush(semester);
        // then
        assertNotNull(semester);
        assertNotNull(semester.getId());
    }

    @Test
    void
            givenSemesterWithoutDegreeAndCoursesAndNegativeAttendees_whenSaveCourseRelation_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(-1);
        semester.setSemesterNumber(1);
        semester.setExtensionName("name");
        semester.setTimetable(timetable);

        // when
        assertThatThrownBy(() -> degreeSemesterRepository.saveAndFlush(semester))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void
            givenSemesterWithoutDegreeAndCoursesAndNullAttendees_whenSaveCourseRelation_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(null);
        semester.setSemesterNumber(1);
        semester.setExtensionName("name");
        semester.setTimetable(timetable);

        // when
        assertThatThrownBy(() -> degreeSemesterRepository.saveAndFlush(semester))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void
            givenSemesterWithoutDegreeAndCoursesAndZeroSemesterNumber_whenSaveCourseRelation_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(0);
        semester.setSemesterNumber(0);
        semester.setExtensionName("name");
        semester.setTimetable(timetable);

        // when
        assertThatThrownBy(() -> degreeSemesterRepository.saveAndFlush(semester))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void
            givenSemesterWithoutDegreeAndCoursesAndNullSemesterNumber_whenSaveCourseRelation_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(1);
        semester.setSemesterNumber(null);
        semester.setExtensionName("name");
        semester.setTimetable(timetable);

        // when
        assertThatThrownBy(() -> degreeSemesterRepository.saveAndFlush(semester))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(testEntityManager.persist(timetable.getSemesterType()));
        timetable = testEntityManager.persist(timetable);
        return timetable;
    }
}
