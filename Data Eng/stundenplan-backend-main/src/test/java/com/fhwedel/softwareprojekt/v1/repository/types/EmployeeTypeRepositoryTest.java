package com.fhwedel.softwareprojekt.v1.repository.types;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class EmployeeTypeRepositoryTest {
    @Autowired private EmployeeTypeRepository employeeTypeRepository;

    @Test
    void saveSuccessfully() {
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Test");

        EmployeeType newType = employeeTypeRepository.save(employeeType);

        assertNotNull(newType);
        assertNotNull(newType.getId());
    }

    @Test
    void whenSaveEmployeeTypeWithNoUniqueName_thenThrowException() {

        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("test");

        employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeType same = new EmployeeType();
        same.setName("test");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    employeeTypeRepository.save(same);
                    employeeTypeRepository.flush();
                });
    }

    @Test
    void updateEmployeeTypeSuccessfully() {

        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("test");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeType existing =
                employeeTypeRepository.findById(employeeType.getId()).orElse(new EmployeeType());
        existing.setName("newName");

        EmployeeType updated = employeeTypeRepository.save(employeeType);
        employeeTypeRepository.flush();

        assertEquals("newName", updated.getName());
    }

    @Test
    void whenUpdateEmployeeTypeWithNoUniqueName_thenThrowException() {
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("test");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeType employeeType1 = new EmployeeType();
        employeeType1.setName("unique");

        employeeTypeRepository.saveAndFlush(employeeType1);

        EmployeeType existing =
                employeeTypeRepository.findById(employeeType.getId()).orElse(new EmployeeType());
        existing.setName("unique");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    employeeTypeRepository.save(existing);
                    employeeTypeRepository.flush();
                });
    }
}
