package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.CourseController;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseBasicResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseToPlanResDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Tests the endpoint for querying the courses that are still to be planned, provided by {@link
 * CourseController#getAllCoursesToPlan(UUID, UUID, UUID)}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class CoursesToPlanIntegrationTest {

    @Autowired private MockMvcTestUtil mockMvcTestUtil;
    @Autowired private WeekEventRepository weekEventRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private SemesterTypeRepository semesterTypeRepository;
    @Autowired private TimetableRepository timetableRepository;
    @Autowired private TimeslotRepository timeslotRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DegreeRepository degreeRepository;
    @Autowired private EmployeeTypeRepository employeeTypeRepository;
    @Autowired private DegreeSemesterRepository degreeSemesterRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private RoomTypeRepository roomTypeRepository;

    @Autowired private EntityManagerFactory emf;

    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void closeDBConnection() {
        em.close();
    }

    @AfterEach
    void rollbackDB() {
        weekEventRepository.deleteAll();
        degreeSemesterRepository.deleteAll();
        courseRepository.deleteAll();
        degreeRepository.deleteAll();
        employeeRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
    }

    @Test
    public void whenGetCoursesToPlanAndFilterByEmployeeId_thenReturnFilteredCourses()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();

        Course c1 = createCourse(timetable, 1);
        Course c2 = createCourse(timetable, 2);
        Course c3 = createCourse(timetable, 3);

        EmployeeType et = createEmployeeTypeDozent();
        Employee e1 = createEmployee(timetable, 1, et);
        Employee e2 = createEmployee(timetable, 2, et);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);
        addLecturerToCourse(c3, e2);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);
        DegreeSemester d1s2 = createDegreeSemester(d1, 2);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s1, c2);
        addDegreeSemesterToCourse(d1s2, c3);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(List.of(c1, c2, c3));
        em.persist(et);
        em.persist(e1);
        em.persist(e2);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(d1s2);

        em.flush();
        em.getTransaction().commit();

        // when filter by e2
        CourseToPlanResDTO[] result =
                mockMvcGetCoursesToPlan(
                        timetable.getId(), Optional.of(e2.getId()), Optional.empty());

        // then
        assertEquals(2, result.length);

        Set<UUID> expCourseSet = new HashSet<>(List.of(c2.getId(), c3.getId()));
        assertCoursesToPlan(result, expCourseSet);
    }

    @Test
    public void whenGetCoursesToPlanAndFilterByDegreeSemesterId_thenReturnFilteredCourses()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();

        Course c1 = createCourse(timetable, 1);
        Course c2 = createCourse(timetable, 2);
        Course c3 = createCourse(timetable, 3);

        EmployeeType et = createEmployeeTypeDozent();
        Employee e1 = createEmployee(timetable, 1, et);
        Employee e2 = createEmployee(timetable, 2, et);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e2);
        addLecturerToCourse(c3, e2);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);
        DegreeSemester d1s2 = createDegreeSemester(d1, 2);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s2, c2);
        addDegreeSemesterToCourse(d1s2, c3);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(List.of(c1, c2, c3));
        em.persist(et);
        em.persist(e1);
        em.persist(e2);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(d1s2);

        em.flush();
        em.getTransaction().commit();

        // when filter by e2
        CourseToPlanResDTO[] result =
                mockMvcGetCoursesToPlan(
                        timetable.getId(), Optional.empty(), Optional.of(d1s1.getId()));

        // then
        assertEquals(1, result.length);

        Set<UUID> expCourseSet = new HashSet<>(List.of(c1.getId()));
        assertCoursesToPlan(result, expCourseSet);
    }

    @Test
    public void whenGetCoursesToPlanAndFilterByEmployeeAndDegreeSemester_thenReturnFilteredCourses()
            throws Exception {
        em.getTransaction().begin();
        Timetable timetable = createTimetableWS22();

        Course c1 = createCourse(timetable, 1);
        Course c2 = createCourse(timetable, 2);
        Course c3 = createCourse(timetable, 3);

        EmployeeType et = createEmployeeTypeDozent();
        Employee e1 = createEmployee(timetable, 1, et);
        Employee e2 = createEmployee(timetable, 2, et);

        addLecturerToCourse(c1, e1);
        addLecturerToCourse(c2, e1);
        addLecturerToCourse(c3, e2);

        Degree d1 = createDegreeOne(timetable);
        DegreeSemester d1s1 = createDegreeSemester(d1, 1);
        DegreeSemester d1s2 = createDegreeSemester(d1, 2);

        addDegreeSemesterToCourse(d1s1, c1);
        addDegreeSemesterToCourse(d1s2, c2);
        addDegreeSemesterToCourse(d1s2, c3);

        em.persist(timetable.getSemesterType());
        em.persist(timetable);
        persistAllEntities(List.of(c1, c2, c3));
        em.persist(et);
        em.persist(e1);
        em.persist(e2);
        em.persist(d1);
        em.persist(d1s1);
        em.persist(d1s2);

        em.flush();
        em.getTransaction().commit();

        // when filter by e2
        CourseToPlanResDTO[] result =
                mockMvcGetCoursesToPlan(
                        timetable.getId(), Optional.of(e2.getId()), Optional.of(d1s2.getId()));

        // then
        assertEquals(1, result.length);

        Set<UUID> expCourseSet = new HashSet<>(List.of(c3.getId()));
        assertCoursesToPlan(result, expCourseSet);
    }

    private <T> void persistAllEntities(Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
    }

    private CourseToPlanResDTO[] mockMvcGetCoursesToPlan(
            UUID timetableId, Optional<UUID> optEmployeeId, Optional<UUID> optDegreeSemesterId)
            throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        optEmployeeId.ifPresent(
                employeeId -> requestParams.add("employeeId", employeeId.toString()));
        optDegreeSemesterId.ifPresent(
                semesterId ->
                        requestParams.add(
                                "degreeSemesterId", optDegreeSemesterId.get().toString()));

        String url = format("/v1/timetable/%s/courses/toPlan", timetableId);
        return mockMvcTestUtil.getWithRequestParams(url, requestParams, CourseToPlanResDTO[].class);
    }

    private void assertCoursesToPlan(CourseToPlanResDTO[] result, Set<UUID> expectedCourseSet) {
        // map DTOs to their respective courseId and collect them in Hashset
        Set<UUID> mappedResult =
                Arrays.stream(result)
                        .map(CourseBasicResDTO::getId)
                        .collect(Collectors.toCollection(HashSet::new));

        assertEquals(
                expectedCourseSet,
                mappedResult,
                "The result is equal to the expected set of courseIDs");
    }
}
