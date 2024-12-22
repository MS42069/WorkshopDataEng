package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit Tests for the {@link com.fhwedel.softwareprojekt.v1.model.RoomCombination} persistence
 * layer, using an embedded in-memory database.
 */
@DataJpaTest
public class RoomCombinationRepositoryTest {
    @Autowired
    private RoomCombinationRepository roomCombinationRepository;

    /**
     * Autowired Test-EntityManger, helpful for setting up persistence layer tests, especially when
     * having multi-entity-relationships.
     */
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    @Test
    void givenRoomsAndEvent_whenSaveRoomCombination_thenSuccess() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Employee employee = new Employee();
        employee.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employee.setFirstname("e");
        employee.setLastname("e");
        employee.setAbbreviation("e");
        employee.setTimetable(timetable);
        employee = testEntityManager.persist(employee);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Course course = new Course();
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setCasID("casID");
        course.setBlockSize(1);
        course.setName("e");
        course.setSlotsPerWeek(1);
        course.setLecturers(employees);
        course.setAbbreviation("e");
        course.setTimetable(timetable);

        RoomType rt = new RoomType();
        rt.setName("Type1");
        rt.setOnline(false);
        rt = testEntityManager.persist(rt);

        Room r1 = new Room();
        r1.setAbbreviation("r1");
        r1.setName("r1");
        r1.setCapacity(0);
        r1.setIdentifier("r1");
        r1.setRoomType(rt);
        r1.setTimetable(timetable);

        Room r2 = new Room();
        r2.setAbbreviation("r2");
        r2.setName("r2");
        r2.setCapacity(0);
        r2.setIdentifier("r2");
        r2.setRoomType(rt);
        r2.setTimetable(timetable);

        r1 = testEntityManager.persist(r1);
        r2 = testEntityManager.persist(r2);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);

        // when
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(rooms);
        roomCombination.setCourse(course);
        List<RoomCombination> roomCombinations = new ArrayList<>();
        roomCombinations.add(roomCombination);
        course.setSuitedRooms(roomCombinations);
        testEntityManager.persist(course);
        roomCombination = roomCombinationRepository.saveAndFlush(roomCombination);
        // then
        assertNotNull(roomCombination);
        assertNotNull(roomCombination.getId());
    }

    @Test
    void whenSaveRoomCombinationAndRoomsIsNull_thenThrowConstraintViolationException() {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Employee employee = new Employee();
        employee.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employee.setFirstname("e");
        employee.setLastname("e");
        employee.setAbbreviation("e");
        employee.setTimetable(timetable);
        employee = testEntityManager.persist(employee);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Course course = new Course();
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setCasID("casID");
        course.setBlockSize(1);
        course.setName("e");
        course.setSlotsPerWeek(1);
        course.setLecturers(employees);
        course.setAbbreviation("e");
        course.setTimetable(timetable);
        course = testEntityManager.persist(course);
        // when
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(null);
        roomCombination.setCourse(course);
        assertThatThrownBy(() -> roomCombinationRepository.saveAndFlush(roomCombination))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void whenSaveRoomCombinationAndRoomsHasZeroRooms_thenThrowConstraintViolationException() {
        List<Room> rooms = new ArrayList<>();
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        Employee employee = new Employee();
        employee.setEmployeeType(
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent()));
        employee.setFirstname("e");
        employee.setLastname("e");
        employee.setAbbreviation("e");
        employee.setTimetable(timetable);
        employee = testEntityManager.persist(employee);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Course course = new Course();
        course.setWeeksPerSemester(1);
        course.setCourseType(null);
        course.setCasID("casID");
        course.setBlockSize(1);
        course.setName("e");
        course.setSlotsPerWeek(1);
        course.setLecturers(employees);
        course.setAbbreviation("e");
        course.setTimetable(timetable);
        course = testEntityManager.persist(course);
        // when
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(rooms);
        roomCombination.setCourse(course);
        assertThatThrownBy(() -> roomCombinationRepository.saveAndFlush(roomCombination))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void whenSaveRoomCombinationAndEventNull_thenThrowDataIntegrityViolationException() {
        // given
        Room r1 = new Room();
        r1.setAbbreviation("r1");
        r1.setName("r1");
        r1.setCapacity(0);
        r1.setIdentifier("r1");
        r1.setRoomType(null);

        Room r2 = new Room();
        r2.setAbbreviation("r2");
        r2.setName("r2");
        r2.setCapacity(0);
        r2.setIdentifier("r2");
        r2.setRoomType(null);

        r1 = testEntityManager.persist(r1);
        r2 = testEntityManager.persist(r2);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);
        // when
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(rooms);
        roomCombination.setCourse(null);
        assertThatThrownBy(() -> roomCombinationRepository.saveAndFlush(roomCombination))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
