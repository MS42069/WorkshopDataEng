package com.fhwedel.softwareprojekt.v1.repository.types;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class SemesterTypeRepositoryTest {

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Test
    void saveSuccessfully() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("Wintersemester");

        SemesterType newSemesterType = semesterTypeRepository.save(semesterType);

        assertNotNull(newSemesterType);
        assertNotNull(newSemesterType.getId());
    }

    @Test
    void whenSaveSemesterTypeWithNoUniqueName_thenThrowException() {

        SemesterType semesterType = new SemesterType();
        semesterType.setName("test");

        semesterTypeRepository.saveAndFlush(semesterType);

        SemesterType same = new SemesterType();
        same.setName("test");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    semesterTypeRepository.save(same);
                    semesterTypeRepository.flush();
                });
    }

    @Test
    void updateSemesterTypeSuccessfully() {

        SemesterType semesterType = new SemesterType();
        semesterType.setName("test");

        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        SemesterType existing =
                semesterTypeRepository.findById(semesterType.getId()).orElse(new SemesterType());
        existing.setName("newName");

        SemesterType updated = semesterTypeRepository.save(semesterType);
        semesterTypeRepository.flush();

        assertEquals("newName", updated.getName());
    }

    @Test
    void whenUpdateSemesterTypeWithNoUniqueName_thenThrowException() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("test");

        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        SemesterType semesterType1 = new SemesterType();
        semesterType1.setName("unique");

        semesterTypeRepository.saveAndFlush(semesterType1);

        SemesterType semesterType2 =
                semesterTypeRepository.findById(semesterType.getId()).orElse(new SemesterType());
        semesterType2.setName("unique");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    semesterTypeRepository.save(semesterType2);
                    semesterTypeRepository.flush();
                });
    }
}
