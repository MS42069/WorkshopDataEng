package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.config.AppConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRelationReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRoomComboReqDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomCombinationRepository;
import com.fhwedel.softwareprojekt.v1.service.types.CourseTypeService;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Tests for the {@link CourseService}, indirectly tests {@link CourseRelationService} */
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    private final UUID timetableId = UUID.randomUUID();
    @Mock private CourseRepository courseRepository;
    @Mock private RoomCombinationRepository roomCombinationRepository;
    @Mock private RoomService roomService;
    @Mock private EmployeeService employeeService;
    @Mock private CourseRelationRepository courseRelationRepository;
    @Mock private CourseTimeslotService courseTimeslotService;
    @Mock private CourseTypeService courseTypeService;
    @Mock private TimetableService timetableService;
    /**
     * Instance of CourseConverter wrapped in a spy object, allowing us to use the real
     * implementation without having to mock converter methods (which is often tedious).
     */
    @Spy
    private final CourseConverter courseConverter =
            new CourseConverter(new AppConfiguration().modelMapper());

    private CourseService underTest;

    @BeforeEach
    void setUp() {
        // do manual injection to be able to test CourseService and CourseRelationService
        // simultaneously
        CourseRelationService courseRelationService =
                new CourseRelationService(courseRelationRepository);
        underTest =
                new CourseService(
                        courseRepository,
                        roomCombinationRepository,
                        courseConverter,
                        roomService,
                        employeeService,
                        courseRelationService,
                        courseRelationRepository,
                        courseTimeslotService,
                        courseTypeService,
                        timetableService);
    }

    @Test
    void whenFindCourseByIDAndCourseDoesNotExist_thenThrowCorrectException() {
        Optional<Course> emptyOptional = Optional.empty();

        UUID id = UUID.randomUUID();
        // when
        when(courseRepository.findById(id)).thenReturn(emptyOptional);

        // then
        assertThatThrownBy(() -> underTest.findCourseByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Course' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void whenSaveCourseWithoutRelations_thenCorrectResult() {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        // when
        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        // then
        verify(courseRepository, times(1)).save(any(Course.class));

        assertThat(actualCourse.getCasID()).isEqualTo(courseReqDTO.getCasID());
        assertThat(actualCourse.getName()).isEqualTo(courseReqDTO.getName());
        assertThat(actualCourse.getSuitedRooms().size()).isEqualTo(0);
        assertThat(actualCourse.getLecturers().size()).isEqualTo(0);
    }

    @Test
    void whenSaveCourseWithRoomCombinations_thenCorrectResult() {
        Room givenRoomOne = createRoomOne();
        Room givenRoomTwo = createRoomTwo();
        Room givenRoomThree = createRoomThree();

        CourseRoomComboReqDTO roomComboOneReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(
                                new LinkedHashSet<>(
                                        List.of(
                                                new IdWrapperDTO(givenRoomOne.getId()),
                                                new IdWrapperDTO(givenRoomTwo.getId()))))
                        .build();
        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(
                                new LinkedHashSet<>(
                                        List.of(new IdWrapperDTO(givenRoomThree.getId()))))
                        .build();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .suitedRooms(
                                new LinkedHashSet<>(
                                        List.of(roomComboOneReqDTO, roomComboTwoReqDTO)))
                        .build();

        // when
        when(roomService.findByID(givenRoomOne.getId())).thenReturn(givenRoomOne);
        when(roomService.findByID(givenRoomTwo.getId())).thenReturn(givenRoomTwo);
        when(roomService.findByID(givenRoomThree.getId())).thenReturn(givenRoomThree);
        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        // then
        assertEquals(2, actualCourse.getSuitedRooms().size());

        RoomCombination roomComboOne = actualCourse.getSuitedRooms().get(0);
        assertThat(roomComboOne.getRooms()).isEqualTo(List.of(givenRoomOne, givenRoomTwo));
        RoomCombination roomComboTwo = actualCourse.getSuitedRooms().get(1);
        assertThat(roomComboTwo.getRooms()).isEqualTo(List.of(givenRoomThree));
    }

    @Test
    void whenSaveCourseWithEmployee_thenCorrectResult() {
        Employee givenEmployee = createEmployeeOne();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .lecturers(
                                new LinkedHashSet<>(
                                        List.of(new IdWrapperDTO(givenEmployee.getId()))))
                        .build();

        // when
        when(employeeService.findByID(givenEmployee.getId())).thenReturn(givenEmployee);
        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        assertEquals(1, actualCourse.getLecturers().size());
        Employee lecturer = actualCourse.getLecturers().get(0);
        assertEquals(givenEmployee, lecturer, "The course is associated with the given employee");

        assertEquals(1, lecturer.getCourses().size());
        assertEquals(
                actualCourse,
                lecturer.getCourses().get(0),
                "Employee is associated with the course");
    }

    @Test
    void whenSaveCourseWithMayBeParallelRelations_thenVerifyCorrectBehaviour() {
        Course courseOne = createCourseOne();

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();
        CourseReqDTO courseReqDTO = buildCourseReqDTO();
        courseReqDTO.setCourseRelations(courseRelationReqDTO);

        // when
        when(courseRepository.findById(courseOne.getId())).thenReturn(Optional.of(courseOne));
        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);
        when(courseRelationRepository.save(any(CourseRelation.class)))
                .thenAnswer(this::answerCourseRelationRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        assertEquals(1, actualCourse.getCourseRelationsA().size());
        assertEquals(0, actualCourse.getCourseRelationsB().size());

        assertEquals(
                RelationType.MAY_BE_PARALLEL,
                actualCourse.getCourseRelationsA().get(0).getRelationType());
        assertSame(courseOne, actualCourse.getCourseRelationsA().get(0).getCourseB());
    }

    @Test
    void givenCourse_whenUpdateCourseWithCourseRelations_thenCorrectlyReplaceCourseRelations() {
        Course courseOne = createCourseOne();
        Course courseTwo = createCourseTwo();

        Course targetCourse = createCourseWithoutRelations();

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setId(UUID.randomUUID());
        courseRelationOne.setRelationType(RelationType.MUST_BE_PARALLEL);
        courseRelationOne.setCourseA(courseOne);
        courseRelationOne.setCourseB(targetCourse);

        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setId(UUID.randomUUID());
        courseRelationTwo.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationTwo.setCourseA(targetCourse);
        courseRelationTwo.setCourseB(courseTwo);

        targetCourse.getCourseRelationsB().add(courseRelationTwo);
        targetCourse.getCourseRelationsA().add(courseRelationTwo);

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(List.of(new IdWrapperDTO(courseOne.getId()))))
                        .build();
        CourseReqDTO courseReqDTO = buildCourseReqDTO();
        courseReqDTO.setCourseRelations(courseRelationReqDTO);

        // when
        when(courseRepository.findById(targetCourse.getId())).thenReturn(Optional.of(targetCourse));
        when(courseRepository.findById(courseOne.getId())).thenReturn(Optional.of(courseOne));
        when(courseRelationRepository.save(any(CourseRelation.class)))
                .thenAnswer(this::answerCourseRelationRepositorySave);

        Course actualCourse =
                underTest.updateCourse(targetCourse.getId(), courseReqDTO, timetableId);

        // then
        assertEquals(1, actualCourse.getCourseRelationsA().size());
        assertEquals(0, actualCourse.getCourseRelationsB().size());

        assertEquals(
                RelationType.MAY_BE_PARALLEL,
                actualCourse.getCourseRelationsA().get(0).getRelationType());
        assertSame(courseOne, actualCourse.getCourseRelationsA().get(0).getCourseB());
    }

    @Test
    void whenSaveCourseWithBlankAbbreviation_thenDefaultValue() {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        assertEquals("Woolly Thinking", actualCourse.getAbbreviation());
    }

    @Test
    void whenSaveCourseWithNullAbbreviation_thenDefaultValue() {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation(null)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        when(courseRepository.save(any(Course.class))).thenAnswer(this::answerCourseRepositorySave);

        Course actualCourse = underTest.saveCourse(courseReqDTO, timetableId);

        assertEquals("Woolly Thinking", actualCourse.getAbbreviation());
    }

    @Test
    void whenUpdateCourseWithBlankAbbreviation_thenDefaultValue() {
        Course givenCourse = createCourseWithoutRelations();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("")
                        .description("description")
                        .courseTimeslots(new ArrayList<>())
                        .build();

        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);
        assertEquals("Woolly Thinking", actualCourse.getAbbreviation());
    }

    @Test
    void whenUpdateCourseWithNullAbbreviation_thenDefaultValue() {
        Course givenCourse = createCourseWithoutRelations();

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation(null)
                        .description("description")
                        .courseTimeslots(new ArrayList<>())
                        .build();

        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);
        assertEquals("Woolly Thinking", actualCourse.getAbbreviation());
    }

    @Test
    void whenUpdateCourseWithoutRelations_thenCorrectResult() {
        Course givenCourse = new Course();
        givenCourse.setId(UUID.randomUUID());
        givenCourse.setCasID("WS22SB037");
        givenCourse.setName("Lectures On Physics");
        givenCourse.setAbbreviation("Physics");
        givenCourse.setDescription("description");
        givenCourse.setBlockSize(2);
        givenCourse.setWeeksPerSemester(11);
        givenCourse.setSlotsPerWeek(4);
        givenCourse.setCourseType(null);
        givenCourse.setSuitedRooms(new ArrayList<>());
        givenCourse.setLecturers(new ArrayList<>());

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .courseTimeslots(new ArrayList<>())
                        .build();

        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);

        assertThat(actualCourse.getCasID()).isEqualTo(courseReqDTO.getCasID());
        assertThat(actualCourse.getName()).isEqualTo(courseReqDTO.getName());
        assertThat(actualCourse.getAbbreviation()).isEqualTo(courseReqDTO.getAbbreviation());
        assertThat(actualCourse.getDescription()).isEqualTo(courseReqDTO.getDescription());
        assertThat(actualCourse.getBlockSize()).isEqualTo(courseReqDTO.getBlockSize());
        assertThat(actualCourse.getSlotsPerWeek()).isEqualTo(courseReqDTO.getSlotsPerWeek());
        assertThat(actualCourse.getWeeksPerSemester())
                .isEqualTo(courseReqDTO.getWeeksPerSemester());

        assertThat(actualCourse.getSuitedRooms().size()).isEqualTo(0);
        assertThat(actualCourse.getLecturers().size()).isEqualTo(0);
    }

    @Test
    void whenUpdateCourseWithRoomCombinations_thenReplaceSuitedRoomsCorrectly() {
        Room givenRoomOne = createRoomOne();

        RoomCombination givenRoomComboOne = new RoomCombination();
        givenRoomComboOne.setId(UUID.randomUUID());
        givenRoomComboOne.setRooms(new ArrayList<>(List.of(givenRoomOne)));

        Course givenCourse = createCourseWithoutRelations();
        givenCourse.setSuitedRooms(new ArrayList<>(List.of(givenRoomComboOne)));

        Room giveRoomTwo = createRoomTwo();

        CourseRoomComboReqDTO roomComboTwoReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new LinkedHashSet<>(List.of(new IdWrapperDTO(giveRoomTwo.getId()))))
                        .build();
        CourseReqDTO courseReqDTO = buildCourseReqDTO();
        courseReqDTO.setSuitedRooms(new LinkedHashSet<>(List.of(roomComboTwoReqDTO)));

        // when
        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));
        when(roomService.findByID(giveRoomTwo.getId())).thenReturn(giveRoomTwo);

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);

        // then
        assertEquals(1, actualCourse.getSuitedRooms().size());

        RoomCombination actualRoomComboTwo = actualCourse.getSuitedRooms().get(0);
        assertThat(actualRoomComboTwo.getRooms()).isEqualTo(List.of(giveRoomTwo));

        verify(roomCombinationRepository, times(1)).deleteById(givenRoomComboOne.getId());
    }

    @Test
    void whenUpdateCourseWithEmployee_thenReplaceLecturersCorrectly() {
        // replace list of one employee with list of the same employee plus another one
        Employee givenEmployeeOne = createEmployeeOne();

        Course givenCourse = createCourseWithoutRelations();
        givenCourse.setLecturers(new ArrayList<>(List.of(givenEmployeeOne)));
        givenEmployeeOne.getCourses().add(givenCourse);

        Employee giveEmployeeTwo = createEmployeeTwo();
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseTimeslots(new ArrayList<>())
                        .courseType(null)
                        .lecturers(
                                new LinkedHashSet<>(
                                        List.of(
                                                new IdWrapperDTO(givenEmployeeOne.getId()),
                                                new IdWrapperDTO(giveEmployeeTwo.getId()))))
                        .build();

        // when
        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));
        when(employeeService.findByID(givenEmployeeOne.getId())).thenReturn(givenEmployeeOne);
        when(employeeService.findByID(giveEmployeeTwo.getId())).thenReturn(giveEmployeeTwo);

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);

        assertEquals(2, actualCourse.getLecturers().size());
        // get employeeOne object regardless of its index position (fails if not contains)
        Employee actualEmployeeOne =
                actualCourse.getLecturers().stream()
                        .filter(employee -> employee.equals(givenEmployeeOne))
                        .findFirst()
                        .orElse(new Employee());

        assertEquals(1, actualEmployeeOne.getCourses().size());
        assertEquals(
                actualCourse,
                actualEmployeeOne.getCourses().get(0),
                "Employee is associated with the course");

        // get employeeTwo object regardless of its index position (fails if not contained)
        Employee actualEmployeeTwo =
                actualCourse.getLecturers().stream()
                        .filter(employee -> employee.equals(giveEmployeeTwo))
                        .findFirst()
                        .orElse(new Employee());

        assertEquals(1, actualEmployeeTwo.getCourses().size());
        assertEquals(
                actualCourse,
                actualEmployeeTwo.getCourses().get(0),
                "Employee is associated with the course");
    }

    @Test
    void giveCourseWithEmployees_whenUpdateCourseWithoutEmployees_thenRemoveLecturersCorrectly() {
        Employee givenEmployeeOne = createEmployeeOne();

        Course givenCourse = createCourseWithoutRelations();
        givenCourse.setLecturers(new ArrayList<>(List.of(givenEmployeeOne)));
        givenEmployeeOne.getCourses().add(givenCourse);

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB099")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseTimeslots(new ArrayList<>())
                        .courseType(null)
                        .lecturers(new LinkedHashSet<>())
                        .build();

        // when
        when(courseRepository.findById(givenCourse.getId())).thenReturn(Optional.of(givenCourse));

        Course actualCourse =
                underTest.updateCourse(givenCourse.getId(), courseReqDTO, timetableId);

        assertEquals(0, actualCourse.getLecturers().size());

        assertEquals(0, givenEmployeeOne.getCourses().size());
    }

    private Room createRoomOne() {
        Room room = new Room();
        room.setId(UUID.randomUUID());
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("C0.02");
        room.setCapacity(50);
        room.setRoomType(null);

        return room;
    }

    private Room createRoomTwo() {
        Room room = new Room();
        room.setId(UUID.randomUUID());
        room.setName("Hörsaal 2");
        room.setAbbreviation("HS02");
        room.setIdentifier("C0.03");
        room.setCapacity(50);
        room.setRoomType(null);

        return room;
    }

    private Room createRoomThree() {
        Room room = new Room();
        room.setId(UUID.randomUUID());
        room.setName("Hörsaal 3");
        room.setAbbreviation("HS03");
        room.setIdentifier("C0.04");
        room.setCapacity(50);
        room.setRoomType(null);

        return room;
    }

    private Employee createEmployeeOne() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Brian W.");
        employee.setLastname("Kernighan");
        employee.setAbbreviation("bwk");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setWorkTimes(new ArrayList<>());
        employee.setCourses(new ArrayList<>());

        return employee;
    }

    private Course createCourseWithoutRelations() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22SB037");
        course.setName("Lectures On Physics");
        course.setAbbreviation("Physics");
        course.setDescription("description");
        course.setBlockSize(2);
        course.setWeeksPerSemester(11);
        course.setSlotsPerWeek(4);
        course.setCourseType(null);
        course.setLecturers(new ArrayList<>());
        course.setSuitedRooms(new ArrayList<>());

        return course;
    }

    private Course createCourseOne() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22A0001");
        course.setName("Course One");
        course.setAbbreviation("CO");
        course.setDescription("description");
        course.setBlockSize(2);
        course.setWeeksPerSemester(11);
        course.setSlotsPerWeek(4);
        course.setCourseType(null);
        course.setLecturers(new ArrayList<>());
        course.setSuitedRooms(new ArrayList<>());

        return course;
    }

    private Course createCourseTwo() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22A0002");
        course.setName("Course Two");
        course.setAbbreviation("CT");
        course.setDescription("description");
        course.setBlockSize(2);
        course.setWeeksPerSemester(11);
        course.setSlotsPerWeek(4);
        course.setCourseType(null);
        course.setLecturers(new ArrayList<>());
        course.setSuitedRooms(new ArrayList<>());

        return course;
    }

    private CourseReqDTO buildCourseReqDTO() {
        return CourseReqDTO.builder()
                .casID("WS22SB099")
                .name("Woolly Thinking")
                .abbreviation("WT")
                .description("description")
                .blockSize(1)
                .weeksPerSemester(12)
                .slotsPerWeek(2)
                .courseTimeslots(new ArrayList<>())
                .courseType(null)
                .build();
    }

    private Employee createEmployeeTwo() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setAbbreviation("rfp");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeDozent());
        employee.setWorkTimes(new ArrayList<>());
        employee.setCourses(new ArrayList<>());

        return employee;
    }

    private Course answerCourseRepositorySave(InvocationOnMock invocation) {
        // answer courseRepository.save() by returning the given argument assigned with a random
        // UUID
        Course course = (Course) invocation.getArguments()[0];
        course.setId(UUID.randomUUID());
        return course;
    }

    private CourseRelation answerCourseRelationRepositorySave(InvocationOnMock invocation) {
        // answer courseRelation.save() by returning the given argument assigned with a random UUID
        CourseRelation course = (CourseRelation) invocation.getArguments()[0];
        course.setId(UUID.randomUUID());
        return course;
    }
}
