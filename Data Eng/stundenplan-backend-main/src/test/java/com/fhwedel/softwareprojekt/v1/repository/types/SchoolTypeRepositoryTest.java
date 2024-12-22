package com.fhwedel.softwareprojekt.v1.repository.types;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class SchoolTypeRepositoryTest {
    @Autowired private SchoolTypeRepository schoolTypeRepository;

    @Test
    void saveSuccessfully() {
        SchoolType schoolType = new SchoolType();
        schoolType.setName("Kindergarten");

        SchoolType newSchoolType = schoolTypeRepository.save(schoolType);

        assertNotNull(newSchoolType);
        assertNotNull(newSchoolType.getId());
    }

    @Test
    void whenSaveSchoolTypeWithNoUniqueName_thenThrowException() {

        SchoolType schoolType = new SchoolType();
        schoolType.setName("test");

        schoolTypeRepository.saveAndFlush(schoolType);

        SchoolType same = new SchoolType();
        same.setName("test");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    schoolTypeRepository.save(same);
                    schoolTypeRepository.flush();
                });
    }

    @Test
    void updateSchoolTypeSuccessfully() {

        SchoolType schoolType = new SchoolType();
        schoolType.setName("test");

        schoolType = schoolTypeRepository.saveAndFlush(schoolType);

        SchoolType existingSchoolType =
                schoolTypeRepository.findById(schoolType.getId()).orElse(new SchoolType());
        existingSchoolType.setName("newName");

        SchoolType updatedSchoolType = schoolTypeRepository.save(schoolType);
        schoolTypeRepository.flush();

        assertEquals("newName", updatedSchoolType.getName());
    }

    @Test
    void whenUpdateSchoolTypeWithNoUniqueName_thenThrowException() {
        SchoolType schoolType1 = new SchoolType();
        schoolType1.setName("test");

        schoolType1 = schoolTypeRepository.saveAndFlush(schoolType1);

        SchoolType schoolType2 = new SchoolType();
        schoolType2.setName("unique");

        schoolTypeRepository.saveAndFlush(schoolType2);

        SchoolType existingSchoolType =
                schoolTypeRepository.findById(schoolType1.getId()).orElse(new SchoolType());
        existingSchoolType.setName("unique");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    schoolTypeRepository.save(existingSchoolType);
                    schoolTypeRepository.flush();
                });
    }
}
