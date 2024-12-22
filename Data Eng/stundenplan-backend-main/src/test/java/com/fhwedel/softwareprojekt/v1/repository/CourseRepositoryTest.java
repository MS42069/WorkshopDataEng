package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.addLecturerToCourse;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseOne;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createCourseTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployee;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createEmployeeTwo;
import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.createTimetableWS22;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Persistence Layer Tests for the creation and deletion of {@link Course courses} and associating
 * them with {@link com.fhwedel.softwareprojekt.v1.model.RoomCombination room combinations}.
 */
@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RoomCombinationRepository roomCombinationRepository;
    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;
    /**
     * Test-EntityManager for setting up the persistence layer tests.
     */
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void
    givenTwoCourses_whenFindAllDistinctByLecturersId_thenReturnOnlyCoursesOfSpecifiedLecturer() {
        Timetable timetable = persistTimetable(createTimetableWS22());
        Course c1 = createCourseOne(timetable);
        Course c2 = createCourseTwo(timetable);

        Employee e1 = createEmployee(timetable, 1);
        Employee e2 = createEmployeeTwo(timetable, 2);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c1, e2);

        addLecturerToCourse(c2, e2);
        testEntityManager.persist(e1.getEmployeeType());
        testEntityManager.persist(e2.getEmployeeType());
        testEntityManager.persist(e1);
        testEntityManager.persist(e2);
        testEntityManager.persist(c1);
        testEntityManager.persist(c2);
        testEntityManager.flush();

        // when find all courses of lecturer e1
        List<Course> result = courseRepository.findAllDistinctByLecturersId(e1.getId());

        // then return c1
        assertEquals(1, result.size());
        assertEquals(c1.getId(), result.get(0)
                .getId());
    }

    @Test
    void whenSaveCourseWithTimeslots_thenSuccessfulPersistence() {
        // given

        // when
        Timetable timetable = persistTimetable(createTimetableWS22());
        EmployeeType employeeType =
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent());
        Timeslot nineToTen = createNineToTenTimeslot();
        nineToTen.setTimetable(timetable);
        nineToTen = testEntityManager.persist(nineToTen);

        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("Pool");
        rt = testEntityManager.persist(rt);

        Course courseSameTime = createTestEvent("same", null, null, timetable, rt, employeeType);
        List<Course> courseSameTimeList = new ArrayList<>();
        courseSameTimeList.add(courseSameTime);
        Course courseBefore = createTestEvent("before", null, null, timetable, rt, employeeType);
        List<Course> courseBeforeList = new ArrayList<>();
        courseBeforeList.add(courseSameTime);
        Course course =
                createTestEvent(
                        "test", courseBeforeList, courseSameTimeList, timetable, rt, employeeType);
        course = courseRepository.save(course);
        testEntityManager.persist(course.getSuitedRooms()
                .get(0));
        testEntityManager.persist(courseSameTime);
        testEntityManager.persist(courseBefore);

        CourseTimeslot courseTimeslot = new CourseTimeslot();
        courseTimeslot.setTimeslot(nineToTen);
        courseTimeslot.setWeekday(DayOfWeek.MONDAY);
        courseTimeslot.setCourse(course);

        course.getCourseTimeslots()
                .add(courseTimeslot);

        testEntityManager.persist(courseTimeslot);
        course = courseRepository.save(course);

        courseRepository.flush();

        // then
        assertNotNull(course);
        assertNotNull(course.getId());
        assertNotNull(course.getCourseTimeslots());
        Course persistedCourse = courseRepository.findById(course.getId())
                .orElse(new Course());

        CourseTimeslot persistedCourseTimeslot = persistedCourse.getCourseTimeslots()
                .get(0);
        assertThat(persistedCourseTimeslot).isNotNull();
        assertThat(persistedCourseTimeslot.getId())
                .overridingErrorMessage(
                        "Expecting " + "CourseTimeslot id to be not null but was actual null.")
                .isNotNull();
    }

    @Test
    void whenSaveEvent_thenSuccessful() {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        EmployeeType employeeType =
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent());
        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("Pool");
        rt = testEntityManager.persist(rt);

        Course courseSameTime = createTestEvent("same", null, null, timetable, rt, employeeType);
        courseSameTime.setTimetable(timetable);
        List<Course> courseSameTimeList = new ArrayList<>();
        courseSameTimeList.add(courseSameTime);
        Course courseBefore = createTestEvent("before", null, null, timetable, rt, employeeType);
        courseBefore.setTimetable(timetable);
        List<Course> courseBeforeList = new ArrayList<>();
        courseBeforeList.add(courseSameTime);
        Course course =
                createTestEvent(
                        "test", courseBeforeList, courseSameTimeList, timetable, rt, employeeType);
        course.setTimetable(timetable);
        // when

        course = courseRepository.save(course);
        testEntityManager.persist(course.getSuitedRooms()
                .get(0));
        testEntityManager.persist(courseSameTime);
        testEntityManager.persist(courseBefore);

        courseRepository.flush();

        // then

        assertNotNull(course);
        assertNotNull(course.getId());
        assertNotNull(course.getSuitedRooms());
        Course persistedCourse = courseRepository.findById(course.getId())
                .orElse(new Course());

        RoomCombination persistedRoomCombination = persistedCourse.getSuitedRooms()
                .get(0);
        assertThat(persistedRoomCombination).isNotNull();
        assertThat(persistedRoomCombination.getId())
                .overridingErrorMessage(
                        "Expecting " + "RoomCombination id to be not null but was actual null.")
                .isNotNull();
    }

    @Test
    void whenSaveEventWithNonUniqueCasID_thenException() {
        // given
        Timetable timetable = persistTimetable(createTimetableWS22());
        EmployeeType employeeType =
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent());
        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("Pool");
        rt = testEntityManager.persist(rt);

        Course course = createTestEvent("test", null, null, timetable, rt, employeeType);
        course.setTimetable(timetable);
        Course course2 = createTestEvent("testEvent2", null, null, timetable, rt, employeeType);
        course2.setTimetable(timetable);
        course2.setCasID("test");

        // when

        course = courseRepository.save(course);
        testEntityManager.persist(course.getSuitedRooms()
                .get(0));
        courseRepository.flush();

        course2 = courseRepository.save(course2);
        testEntityManager.persist(course2.getSuitedRooms()
                .get(0));

        // then

        assertThrows(DataIntegrityViolationException.class, () -> courseRepository.flush());
    }

    @Test
    void whenDeleteEvent_thenCascadeRemovalOfRoomCombinations() {
        // given
        EmployeeType employeeType =
                testEntityManager.persist(TestDataUtil.createEmployeeTypeAssistent());
        Timetable timetable = persistTimetable(createTimetableWS22());

        RoomType rt = new RoomType();
        rt.setOnline(false);
        rt.setName("Pool");
        rt = testEntityManager.persist(rt);

        Course course = createTestEvent("test", null, null, timetable, rt, employeeType);
        course.setTimetable(timetable);
        // when

        course = courseRepository.save(course);
        RoomCombination combination = testEntityManager.persist(course.getSuitedRooms()
                .get(0));

        courseRepository.flush();

        // assertTestSetup
        assertNotNull(course);
        assertNotNull(course.getId());
        assertNotNull(course.getSuitedRooms());
        assertNotNull(combination);

        courseRepository.delete(course);

        assertThat(courseRepository.findAll()
                .size()).isEqualTo(0);
        assertThat(roomCombinationRepository.findAll()
                .size()).isEqualTo(0);
        // then

    }

    private List<Employee> createTestEmployeeList(
            String name, Timetable timetable, EmployeeType employeeType) {
        Employee employee = new Employee();
        employee.setEmployeeType(employeeType);
        employee.setFirstname(name);
        employee.setLastname(name);
        employee.setAbbreviation(name);
        employee.setTimetable(timetable);
        testEntityManager.persist(employee.getEmployeeType());
        employee = testEntityManager.persist(employee);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        return employees;
    }

    private List<Room> createTestRoomList(String name, Timetable timetable, RoomType roomType) {

        Room r1 = new Room();
        r1.setAbbreviation(name);
        r1.setName(name);
        r1.setCapacity(0);
        r1.setIdentifier(name);
        r1.setRoomType(roomType);
        r1.setTimetable(timetable);

        Room r2 = new Room();
        r2.setAbbreviation(name + "2");
        r2.setName(name + "2");
        r2.setCapacity(0);
        r2.setIdentifier(name + "2");
        r2.setRoomType(roomType);
        r2.setTimetable(timetable);
        List<Room> rooms = new ArrayList<>();
        r1 = testEntityManager.persist(r1);
        r2 = testEntityManager.persist(r2);
        rooms.add(r1);
        rooms.add(r2);
        return rooms;
    }

    private Course createTestEvent(
            String name,
            List<Course> before,
            List<Course> sameTime,
            Timetable timetable,
            RoomType roomType,
            EmployeeType employeeType) {

        List<Room> rooms = createTestRoomList(name, timetable, roomType);
        List<Employee> employees = createTestEmployeeList(name, timetable, employeeType);
        Course course = new Course();
        course.setCourseType(null);
        course.setAbbreviation(name);
        course.setName(name);
        course.setCasID(name);
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setBlockSize(1);
        course.setLecturers(employees);
        course.setTimetable(timetable);

        List<RoomCombination> combinations = new ArrayList<>();
        RoomCombination combination = new RoomCombination();
        combination.setRooms(rooms);
        combination.setCourse(course);
        combinations.add(combination);
        course.setSuitedRooms(combinations);

        return course;
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

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
