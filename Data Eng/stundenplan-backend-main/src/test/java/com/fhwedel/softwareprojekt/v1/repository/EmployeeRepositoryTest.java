package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Persistence Layer for testing the creation and deletion of {@link Employee employees} and
 * associating them with {@link WorkTime working times}.
 */
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private WorkTimeRepository worktimeRepository;
    @Autowired private TimetableRepository timetableRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    /** Test-EntityManager for setting up the persistence layer tests. */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void whenSaveEmployeeWithoutWorkTime_thenSuccessfulPersistence() {
        Employee employee = new Employee();
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setAbbreviation("rpf");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
    }

    @Test
    void whenSaveEmployeeWithWorkTime_thenSuccessfulPersistence() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot nineToTen = createNineToTenTimeslot();
        nineToTen.setTimetable(timetable);
        nineToTen = testEntityManager.persist(nineToTen);

        // when
        Employee employee = new Employee();
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setAbbreviation("rpf");
        employee.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        WorkTime workTime = new WorkTime();
        workTime.setTimeslot(nineToTen);
        workTime.setWeekday(DayOfWeek.MONDAY);
        // associate workTime to the employee
        workTime.setEmployee(employee);

        // Note: For the persistence it doesn't matter at which moment the working time is added to
        // the
        // employee, it could also be done after saving and flushing the employee
        employee.getWorkTimes().add(workTime);

        testEntityManager.persist(workTime);
        employee = employeeRepository.save(employee);
        // Note: both instances (workTime and employee) have to be saved before flushing, the order
        // does
        // not matter
        employeeRepository.flush();

        // then
        assertNotNull(employee);
        assertNotNull(employee.getId());
        assertNotNull(employee.getWorkTimes());
        Employee persistedEmployee =
                employeeRepository.findById(employee.getId()).orElse(new Employee());

        WorkTime persistedWorkTime = persistedEmployee.getWorkTimes().get(0);
        assertThat(persistedWorkTime).isNotNull();
        assertThat(persistedWorkTime.getId())
                .overridingErrorMessage(
                        "Expecting " + "WorkTime id to be not null but was actual null.")
                .isNotNull();
    }

    @Test
    void whenSaveEmployeeWithNoUniqueAbbrev_thenThrowException() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Employee employeeOne = new Employee();
        employeeOne.setFirstname("firstname");
        employeeOne.setLastname("lastname");
        employeeOne.setAbbreviation("wol");
        employeeOne.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employeeOne.setTimetable(timetable);

        Employee employeeTwo = new Employee();
        employeeTwo.setFirstname("firstname");
        employeeTwo.setLastname("lastname");
        employeeTwo.setAbbreviation("wol");
        employeeTwo.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeDozent()));
        employeeTwo.setTimetable(timetable);

        employeeRepository.save(employeeOne);
        employeeRepository.flush();

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    employeeRepository.save(employeeTwo);
                    employeeRepository.flush();
                });
    }

    @Test
    void givenEmployeeWithWorkTimes_whenDeleteEmployee_thenCascadeRemovalOfWorkTimes() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot nineToTen = createNineToTenTimeslot();
        nineToTen.setTimetable(timetable);

        Employee employee = createEmployeeRichard();
        employee.setTimetable(timetable);

        WorkTime workTime = new WorkTime();
        workTime.setTimeslot(nineToTen);
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setEmployee(employee);

        employee.getWorkTimes().add(workTime);

        testEntityManager.persist(nineToTen);
        employee = testEntityManager.persist(employee);
        workTime = testEntityManager.persist(workTime);
        testEntityManager.flush();

        // assert test setup
        assertNotNull(employee.getId());
        assertNotNull(workTime.getId());
        assertThat(employeeRepository.findAll().size()).isEqualTo(1);
        assertThat(worktimeRepository.findAll().size()).isEqualTo(1);

        // when
        employeeRepository.delete(employee);

        // then
        assertThat(employeeRepository.findAll().size()).isEqualTo(0);
        assertThat(worktimeRepository.findAll().size()).isEqualTo(0);
    }

    /**
     * Helper method for creating a new {@link Timeslot} instance that represents a timeslot from 9
     * to 10 o'clock.
     *
     * @return instance of {@link Timeslot}
     */
    private Timeslot createNineToTenTimeslot() {
        Timeslot nineToTen = new Timeslot();
        nineToTen.setStartTime(LocalTime.of(9, 0));
        nineToTen.setEndTime(LocalTime.of(10, 0));
        nineToTen.setIndex(0);

        return nineToTen;
    }

    /**
     * Helper method for creating a new instance of {@link Employee} (without any associated work
     * times).
     *
     * @return instance of {@link Employee}
     */
    private Employee createEmployeeRichard() {
        Employee employee = new Employee();
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setAbbreviation("rpf");
        employee.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employee.setWorkTimes(new ArrayList<>());
        return employee;
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
