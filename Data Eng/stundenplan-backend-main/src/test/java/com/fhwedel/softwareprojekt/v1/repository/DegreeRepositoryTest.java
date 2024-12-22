package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Persistence Layer for testing the creation and deletion of {@link Degree events} and associating
 * them with {@link com.fhwedel.softwareprojekt.v1.model.DegreeSemester degree semesters}.
 */
@DataJpaTest
public class DegreeRepositoryTest {

    @Autowired private DegreeRepository degreeRepository;

    @Autowired private DegreeSemesterRepository degreeSemesterRepository;

    /** Test-EntityManager for setting up the persistence layer tests. */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void whenSaveDegreeWithoutSemesters_thenSuccessful() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());
        Degree degree = createDegreeWithoutSemesters(timetable);

        // when

        degree = degreeRepository.save(degree);
        degreeRepository.flush();

        // then

        assertNotNull(degree);
        assertNotNull(degree.getId());
    }

    @Test
    void whenSaveDegreeWithSemesters_thenSuccessful() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());
        Degree degree = createDegreeWithSemester(timetable);

        // when
        testEntityManager.persist(degree.getSemesters().get(0).getCourses().get(0));
        degree = degreeRepository.save(degree);
        testEntityManager.persist(degree.getSemesters().get(0));
        degreeRepository.flush();

        // then

        assertNotNull(degree);
        assertNotNull(degree.getId());
        Degree persistedDegree = degreeRepository.findById(degree.getId()).orElse(new Degree());
        DegreeSemester persistedDegreeSemester = persistedDegree.getSemesters().get(0);
        assertNotNull(persistedDegreeSemester);
        assertNotNull(persistedDegreeSemester.getId());
    }

    @Test
    void whenDeleteDegreeWithSemesters_thenCascadeRemove() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Degree degree = createDegreeWithoutSemesters(timetable);
        degree = degreeRepository.saveAndFlush(degree);

        Course course = new Course();
        course.setName("name");
        course.setCasID("name");
        course.setAbbreviation("name");
        course.setBlockSize(1);
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setTimetable(timetable);
        course = testEntityManager.persist(course);

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setDegree(degree);
        semester.setExtensionName("name");
        semester.setCourses(List.of(course));
        semester.setSemesterNumber(1);
        semester.setTimetable(timetable);
        semester = testEntityManager.persist(semester);
        degree.setSemesters(List.of(semester));

        // assertSetup

        assertNotNull(degree);
        assertNotNull(degree.getId());
        assertThat(degreeSemesterRepository.findAll().size()).isEqualTo(1);
        Degree persistedDegree = degreeRepository.findById(degree.getId()).orElse(new Degree());
        DegreeSemester persistedDegreeSemester = persistedDegree.getSemesters().get(0);
        assertNotNull(persistedDegreeSemester);
        assertNotNull(persistedDegreeSemester.getId());

        // when
        degreeRepository.delete(degree);
        // then
        assertThat(degreeRepository.findAll().size()).isEqualTo(0);
        assertThat(degreeSemesterRepository.findAll().size()).isEqualTo(0);
    }

    private Degree createDegreeWithoutSemesters(Timetable timetable) {
        Degree degree = new Degree();
        degree.setSemesterAmount(7);
        degree.setName("test");
        degree.setShortName("test");
        degree.setStudyRegulation("14");
        degree.setTimetable(timetable);
        degree.setSchoolType(null);
        return degree;
    }

    private Degree createDegreeWithSemester(Timetable timetable) {
        Degree degree = createDegreeWithoutSemesters(timetable);

        Course course = new Course();
        course.setName("test");
        course.setCasID("test");
        course.setAbbreviation("test");
        course.setBlockSize(1);
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setTimetable(timetable);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(12);
        semester.setDegree(degree);
        semester.setExtensionName("test");
        semester.setCourses(courses);
        semester.setSemesterNumber(1);
        semester.setTimetable(timetable);

        List<DegreeSemester> semesters = new ArrayList<>();
        semesters.add(semester);
        degree.setSemesters(semesters);
        course.setSemesters(semesters);
        return degree;
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(testEntityManager.persist(timetable.getSemesterType()));
        timetable = testEntityManager.persist(timetable);
        return timetable;
    }
}
