package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fhwedel.softwareprojekt.v1.config.AppConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.*;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Test;

/** Unit-Tests for the {@link CourseConverter}. */
public class CourseConverterTest {

    /**
     * Converter under test, manual injection of the ModelMapper dependency using the
     * Bean-definition in the AppConfiguration
     */
    private final CourseConverter courseConverter =
            new CourseConverter(new AppConfiguration().modelMapper());

    @Test
    public void convertRoomComboEntityToResDTO_CombinationWithMultipleRooms() {
        // given
        Room room1 = createRoom1();
        Room room2 = createRoom2();

        RoomCombination roomCombination = createRoomCombination(List.of(room1, room2));
        // when
        CourseRoomComboResDTO dto = courseConverter.convertRoomComboEntityToResDTO(roomCombination);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getRooms().size()).isEqualTo(2);
        RoomBasicResDTO roomDto1 = dto.getRooms().get(0);
        RoomBasicResDTO roomDto2 = dto.getRooms().get(1);

        assertThat(roomDto1.getId()).isEqualTo(room1.getId());
        assertThat(roomDto1.getName()).isEqualTo(room1.getName());
        assertThat(roomDto1.getAbbreviation()).isEqualTo(room1.getAbbreviation());

        assertThat(roomDto2.getId()).isEqualTo(room2.getId());
        assertThat(roomDto2.getName()).isEqualTo(room2.getName());
        assertThat(roomDto2.getAbbreviation()).isEqualTo(room2.getAbbreviation());
    }

    @Test
    public void convertCourseEntityToDetailResDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        // given course entity
        Course course = createCourseOne();
        course.setTimetable(timetable);

        // suited rooms
        Room room1 = createRoom1();
        Room room2 = createRoom2();

        RoomCombination roomCombination = createRoomCombination(List.of(room1, room2));

        course.getSuitedRooms().add(roomCombination);

        // lecturers
        Employee employee = createEmployee();
        course.getLecturers().add(employee);

        // expected
        RoomBasicResDTO expRoomBasicResDto1 =
                RoomBasicResDTO.builder()
                        .id(room1.getId())
                        .abbreviation(room1.getAbbreviation())
                        .name(room1.getName())
                        .build();
        RoomBasicResDTO expRoomBasicResDto2 =
                RoomBasicResDTO.builder()
                        .id(room2.getId())
                        .abbreviation(room2.getAbbreviation())
                        .name(room2.getName())
                        .build();
        CourseRoomComboResDTO expRoomComboDTO =
                CourseRoomComboResDTO.builder()
                        .rooms(new ArrayList<>(List.of(expRoomBasicResDto1, expRoomBasicResDto2)))
                        .build();
        EmployeeTypeResDTO employeeTypeResDTO =
                EmployeeTypeResDTO.builder()
                        .id(UUID.randomUUID())
                        .name(employee.getEmployeeType().getName())
                        .build();

        EmployeeResDTO expEmployeeDTO =
                EmployeeResDTO.builder()
                        .id(employee.getId())
                        .firstname(employee.getFirstname())
                        .lastname(employee.getLastname())
                        .abbreviation(employee.getAbbreviation())
                        .employeeType(employeeTypeResDTO)
                        .build();
        CourseDetailResDTO expCourseDTO =
                CourseDetailResDTO.builder()
                        .id(course.getId())
                        .casID(course.getCasID())
                        .name(course.getName())
                        .abbreviation(course.getAbbreviation())
                        .description(course.getDescription())
                        .blockSize(course.getBlockSize())
                        .weeksPerSemester(course.getWeeksPerSemester())
                        .slotsPerWeek(course.getSlotsPerWeek())
                        .courseType(null)
                        .suitedRooms(new ArrayList<>(List.of(expRoomComboDTO)))
                        .lecturers(new ArrayList<>(List.of(expEmployeeDTO)))
                        .timetable(timetable.getId())
                        .build();

        // when
        CourseDetailResDTO actualCourseDTO =
                courseConverter.convertCourseEntityToDetailResDTO(course);

        // then
        assertEquals(expCourseDTO.getCourseType(), actualCourseDTO.getCourseType());
        assertEquals(expCourseDTO.getName(), actualCourseDTO.getName());
    }

    @Test
    void
            givenCourseRelations_whenConvertCourseEntityToDetailResDTO_thenCorrectConvertedCourseRelations() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Course target = createCourseOne();
        target.setTimetable(timetable);
        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setId(UUID.randomUUID());
        courseRelationOne.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationOne.setCourseA(target);
        courseRelationOne.setCourseB(courseTwo);

        target.getCourseRelationsA().add(courseRelationOne);

        // when
        CourseDetailResDTO result = courseConverter.convertCourseEntityToDetailResDTO(target);

        // then
        List<CourseBasicResDTO> resultMayBeParallelTo =
                result.getCourseRelations().getMayBeParallelTo();
        assertThat(resultMayBeParallelTo.size()).isEqualTo(1);
        assertThat(resultMayBeParallelTo.get(0).getId()).isEqualTo(courseTwo.getId());
    }

    @Test
    public void convertCourseReqDtoToEntity() {
        // build CourseReqDto
        IdWrapperDTO idDtoRoom1 = new IdWrapperDTO(UUID.randomUUID());
        IdWrapperDTO idDtoRoom2 = new IdWrapperDTO(UUID.randomUUID());

        CourseRoomComboReqDTO courseRoomComboReqDTO =
                CourseRoomComboReqDTO.builder()
                        .rooms(new LinkedHashSet<>(List.of(idDtoRoom1, idDtoRoom2)))
                        .build();

        IdWrapperDTO idDtoEmployee1 = new IdWrapperDTO(UUID.randomUUID());
        IdWrapperDTO idDtoEmployee2 = new IdWrapperDTO(UUID.randomUUID());

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22S037")
                        .name("Lectures On Physics")
                        .abbreviation("Physics")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(new LinkedHashSet<>(List.of(courseRoomComboReqDTO)))
                        .lecturers(new LinkedHashSet<>(List.of(idDtoEmployee1, idDtoEmployee2)))
                        .build();

        // when
        Course result = courseConverter.convertCourseReqDtoToEntity(courseReqDTO);

        // then
        assertThat(result.getCasID()).isEqualTo(courseReqDTO.getCasID());
        assertThat(result.getName()).isEqualTo(courseReqDTO.getName());
        assertThat(result.getAbbreviation()).isEqualTo(courseReqDTO.getAbbreviation());
        assertThat(result.getBlockSize()).isEqualTo(courseReqDTO.getBlockSize());
        assertThat(result.getSlotsPerWeek()).isEqualTo(courseReqDTO.getSlotsPerWeek());
        assertThat(result.getWeeksPerSemester()).isEqualTo(courseReqDTO.getWeeksPerSemester());
        assertThat(result.getCourseType()).isEqualTo(courseReqDTO.getCourseType());

        assertEquals(
                0,
                result.getLecturers().size(),
                "Not enough information for a conversion thus no employee should have been created\n"
                        + result.getLecturers());
        assertEquals(
                0,
                result.getSuitedRooms().size(),
                "Not enough information for a conversion thus no room combination should have "
                        + "been created\n"
                        + result.getSuitedRooms());

        assertEquals(0, result.getCourseRelationsA().size());
        assertEquals(0, result.getCourseRelationsB().size());
    }

    @Test
    public void convertCourseEntityToBasicResDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Course course = createCourseOne();
        course.setTimetable(timetable);

        CourseBasicResDTO expected =
                CourseBasicResDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .casID(course.getCasID())
                        .courseType(null)
                        .build();

        // when
        CourseBasicResDTO result = courseConverter.convertCourseEntityToBasicResDTO(course);

        assertEquals(expected, result);
    }

    @Test
    public void whenConvertCourseReqDtoToEntityAndNotAbbreviationGiven_thenAbbrevDefaultsToName() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22S037")
                        .name("Lectures On Physics")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();

        Course result = courseConverter.convertCourseReqDtoToEntity(courseReqDTO);
        assertThat(result.getName()).isEqualTo(courseReqDTO.getName());
        assertThat(result.getAbbreviation()).isEqualTo(courseReqDTO.getName());
    }

    @Test
    void givenMayBeParallelRelations_whenConvertCourseRelationsToResDTO_thenCorrectResult() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        Course owner = createCourseOne(); // owner of the relations
        owner.setTimetable(timetable);

        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);

        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);

        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationOne.setCourseA(owner);
        courseRelationOne.setCourseB(courseTwo);

        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setRelationType(RelationType.MAY_BE_PARALLEL);
        courseRelationTwo.setCourseA(courseThree);
        courseRelationTwo.setCourseB(owner);

        List<CourseRelation> courseRelations =
                new ArrayList<>(List.of(courseRelationOne, courseRelationTwo));

        // when
        CourseRelationResDTO result =
                courseConverter.convertCourseRelationsToResDTO(owner, courseRelations);

        assertEquals(2, result.getMayBeParallelTo().size());

        // use of a course converter method to verify the result (more concise, avoids code
        // duplication)
        CourseBasicResDTO expectedCourseTwoDTO =
                courseConverter.convertCourseEntityToBasicResDTO(courseTwo);
        CourseBasicResDTO expectedCourseThreeDTO =
                courseConverter.convertCourseEntityToBasicResDTO(courseThree);

        assertThat(result.getMayBeParallelTo().get(0)).isEqualTo(expectedCourseTwoDTO);
        assertThat(result.getMayBeParallelTo().get(1)).isEqualTo(expectedCourseThreeDTO);
    }

    @Test
    void givenMustBeBeforeRelations_whenConvertCourseRelationsToResDTO_thenCorrectResult() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Course owner = createCourseOne(); // owner of the relations
        owner.setTimetable(timetable);

        Course courseTwo = createCourseTwo();
        courseTwo.setTimetable(timetable);
        Course courseThree = createCourseThree();
        courseThree.setTimetable(timetable);

        // courseTwo must be held after the owner course
        CourseRelation courseRelationOne = new CourseRelation();
        courseRelationOne.setRelationType(RelationType.A_MUST_BE_BEFORE_B);
        courseRelationOne.setCourseA(owner);
        courseRelationOne.setCourseB(courseTwo);

        // courseThree must be held before owner course
        CourseRelation courseRelationTwo = new CourseRelation();
        courseRelationTwo.setRelationType(RelationType.A_MUST_BE_BEFORE_B);
        courseRelationTwo.setCourseA(courseThree);
        courseRelationTwo.setCourseB(owner);

        List<CourseRelation> courseRelations =
                new ArrayList<>(List.of(courseRelationOne, courseRelationTwo));

        // when
        CourseRelationResDTO result =
                courseConverter.convertCourseRelationsToResDTO(owner, courseRelations);

        assertEquals(1, result.getMustBeHeldBefore().size());
        assertEquals(1, result.getMustBeHeldAfter().size());

        // use of a course converter method to verify the result (more concise, avoids code
        // duplication)
        CourseBasicResDTO expectedCourseTwoDTO =
                courseConverter.convertCourseEntityToBasicResDTO(courseTwo);
        CourseBasicResDTO expectedCourseThreeDTO =
                courseConverter.convertCourseEntityToBasicResDTO(courseThree);

        assertThat(result.getMustBeHeldBefore().get(0)).isEqualTo(expectedCourseTwoDTO);
        assertThat(result.getMustBeHeldAfter().get(0)).isEqualTo(expectedCourseThreeDTO);
    }

    private Room createRoom1() {
        Room room1 = new Room();
        room1.setId(UUID.randomUUID());
        room1.setName("Hörsaal 1");
        room1.setAbbreviation("HS01");
        return room1;
    }

    private Room createRoom2() {
        Room room2 = new Room();
        room2.setId(UUID.randomUUID());
        room2.setName("Hörsaal 2");
        room2.setAbbreviation("HS02");
        return room2;
    }

    private Course createCourseOne() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22A001");
        course.setName("Approximate Accuracy");
        course.setAbbreviation("Approx. Accuracy");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        return course;
    }

    private Course createCourseTwo() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22C002");
        course.setName("Creative Uncertainty");
        course.setAbbreviation("CU");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        return course;
    }

    private Course createCourseThree() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22R003");
        course.setName("Recondite Architecture and Origami Map Folding");
        course.setAbbreviation("RAOMF");
        course.setDescription("description");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        return course;
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setAbbreviation("rpf");
        employee.setFirstname("Richard");
        employee.setLastname("Feynman");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());

        return employee;
    }

    private RoomCombination createRoomCombination(Collection<Room> rooms) {
        RoomCombination roomCombination = new RoomCombination();
        roomCombination.setRooms(new ArrayList<>(rooms));
        return roomCombination;
    }
}
