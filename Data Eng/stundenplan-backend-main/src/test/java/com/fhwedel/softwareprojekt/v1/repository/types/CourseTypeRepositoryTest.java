package com.fhwedel.softwareprojekt.v1.repository.types;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class CourseTypeRepositoryTest {
    @Autowired private CourseTypeRepository courseTypeRepository;

    @Test
    void saveSuccessfully() {
        CourseType courseType = new CourseType();
        courseType.setName("workshop");

        CourseType newCourseType = courseTypeRepository.save(courseType);

        assertNotNull(newCourseType);
        assertNotNull(newCourseType.getId());
    }

    @Test
    void whenSaveCourseTypeWithNoUniqueName_thenThrowException() {

        CourseType courseType = new CourseType();
        courseType.setName("test");

        courseTypeRepository.saveAndFlush(courseType);

        CourseType same = new CourseType();
        same.setName("test");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    courseTypeRepository.save(same);
                    courseTypeRepository.flush();
                });
    }

    @Test
    void updateCourseTypeSuccessfully() {

        CourseType courseType = new CourseType();
        courseType.setName("test");

        courseType = courseTypeRepository.saveAndFlush(courseType);

        CourseType existingCourseType =
                courseTypeRepository.findById(courseType.getId()).orElse(new CourseType());
        existingCourseType.setName("newName");

        CourseType updatedCourseType = courseTypeRepository.save(courseType);
        courseTypeRepository.flush();

        assertEquals("newName", updatedCourseType.getName());
    }

    @Test
    void whenUpdateCourseTypeWithNoUniqueName_thenThrowException() {
        CourseType courseType1 = new CourseType();
        courseType1.setName("test");

        courseType1 = courseTypeRepository.saveAndFlush(courseType1);

        CourseType courseType2 = new CourseType();
        courseType2.setName("unique");

        courseTypeRepository.saveAndFlush(courseType2);

        CourseType existingCourseType =
                courseTypeRepository.findById(courseType1.getId()).orElse(new CourseType());
        existingCourseType.setName("unique");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    courseTypeRepository.save(existingCourseType);
                    courseTypeRepository.flush();
                });
    }
}
