package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.CourseController;
import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseDetailResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRelationReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRoomComboReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.CourseService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/** Spring MVC Tests that focus on the {@link CourseController} */
@WebMvcTest(CourseController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class CourseControllerTest {
    private final UUID timetableId = UUID.randomUUID();
    @Autowired private ObjectMapper objectMapper;
    @MockBean private CourseService courseService;
    @MockBean private CourseConverter courseConverter;
    @MockBean private SchedulerService schedulerService;
    @Autowired private MockMvc mockMvc;

    @Test
    void whenPostValidCourse_thenOkAndCorrectResponse() throws Exception {
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
                        .build();

        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Woolly Thinking");

        // Note: the actual return value is not relevant, we only test that the proper service and
        // converter methode is called
        CourseDetailResDTO courseDetailResDTO =
                CourseDetailResDTO.builder().id(course.getId()).name(course.getName()).build();

        when(courseService.saveCourse(courseReqDTO, timetableId)).thenReturn(course);
        when(courseConverter.convertCourseEntityToDetailResDTO(course))
                .thenReturn(courseDetailResDTO);

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(course.getId().toString())))
                .andExpect(jsonPath("$.name", is(course.getName())));
    }

    @Test
    void whenPostValidCourseWithRoomCombos_thenOk() throws Exception {
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
                        .suitedRooms(
                                new HashSet<>(
                                        List.of(
                                                new CourseRoomComboReqDTO(
                                                        new HashSet<>(
                                                                List.of(
                                                                        new IdWrapperDTO(
                                                                                UUID
                                                                                        .randomUUID())))))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostValidCourseWithLecturers_thenOk() throws Exception {
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
                        .lecturers(new HashSet<>(List.of(new IdWrapperDTO(UUID.randomUUID()))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostValidCourseWithCourseRelations_thenOk() throws Exception {

        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(
                                        List.of(
                                                new IdWrapperDTO(UUID.randomUUID()),
                                                new IdWrapperDTO(UUID.randomUUID()))))
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
                        .courseRelations(courseRelationReqDTO)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    void whenPutValidCourse_thenCorrectResponse() throws Exception {
        CourseRelationReqDTO courseRelationReqDTO =
                CourseRelationReqDTO.builder()
                        .mayBeParallelTo(
                                new HashSet<>(
                                        List.of(
                                                new IdWrapperDTO(UUID.randomUUID()),
                                                new IdWrapperDTO(UUID.randomUUID()))))
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
                        .suitedRooms(
                                new HashSet<>(
                                        List.of(
                                                new CourseRoomComboReqDTO(
                                                        new HashSet<>(
                                                                List.of(
                                                                        new IdWrapperDTO(
                                                                                UUID
                                                                                        .randomUUID())))))))
                        .lecturers(new HashSet<>(List.of(new IdWrapperDTO(UUID.randomUUID()))))
                        .courseRelations(courseRelationReqDTO)
                        .build();

        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Woolly Thinking");

        CourseDetailResDTO courseDetailResDTO =
                CourseDetailResDTO.builder().id(course.getId()).name(course.getName()).build();

        when(courseService.updateCourse(course.getId(), courseReqDTO, timetableId))
                .thenReturn(course);
        when(courseConverter.convertCourseEntityToDetailResDTO(course))
                .thenReturn(courseDetailResDTO);

        String url = format("/v1/timetable/%s/courses/%s", timetableId, course.getId());
        mockMvc.perform(
                        put(url, courseReqDTO)
                                .content(objectMapper.writeValueAsString(courseReqDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(course.getId().toString())))
                .andExpect(jsonPath("$.name", is(course.getName())));
    }

    @Test
    void whenGetAllCourses_thenCorrectResponse() throws Exception {

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Course course1 = new Course();
        course1.setId(UUID.randomUUID());
        course1.setName("Woolly Thinking");
        course1.setTimetable(timetable);

        Course course2 = new Course();
        course2.setId(UUID.randomUUID());
        course2.setName("Extreme Horticulture");
        course2.setTimetable(timetable);

        CourseDetailResDTO courseDetailResDTO1 =
                CourseDetailResDTO.builder().id(course1.getId()).name(course1.getName()).build();

        CourseDetailResDTO courseDetailResDTO2 =
                CourseDetailResDTO.builder().id(course2.getId()).name(course2.getName()).build();

        when(courseService.findAllCourses(timetable.getId())).thenReturn(List.of(course1, course2));
        when(courseConverter.convertCourseEntitiesToDetailResDtoList(List.of(course1, course2)))
                .thenReturn(List.of(courseDetailResDTO1, courseDetailResDTO2));
        this.mockMvc
                .perform(get(format("/v1/timetable/%s/courses", timetable.getId().toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is(course1.getName())))
                .andExpect(jsonPath("$[1].name", is(course2.getName())));
    }

    @Test
    void whenGetSingleRoom_thenCorrectResponse() throws Exception {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Woolly Thinking");

        CourseDetailResDTO courseDetailResDTO =
                CourseDetailResDTO.builder().id(course.getId()).name(course.getName()).build();

        when(courseService.findCourseByID(course.getId())).thenReturn(course);
        when(courseConverter.convertCourseEntityToDetailResDTO(course))
                .thenReturn(courseDetailResDTO);

        mockMvc.perform(get(format("/v1/timetable/%s/courses/%s", timetableId, course.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(course.getId().toString())))
                .andExpect(jsonPath("$.name", is(course.getName())));
    }

    @Test
    void whenDeleteCourse_thenCorrectResponse() throws Exception {
        doNothing().when(courseService).deleteCourse(any(UUID.class));

        UUID id = UUID.randomUUID();
        String url = format("/v1/timetable/%s/courses/%s", timetableId, id);
        this.mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        verify(courseService, times(1)).deleteCourse(id);
    }

    @Test
    void whenPostCourseAndCasIDIsBlank_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndNameIsBlank_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndBlockSizeLessThanOne_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(0)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndWeeksPerSemesterLessThanOne_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(0)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndSlotsPerWeekLessThanOne_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(0)
                        .courseType(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndBlockSizeGreaterThanSlotsPerWeek_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(4)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .build();
        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndSuitedRoomsIsNull_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndSuitedRoomsContainsNullValues_thenBadRequest() throws Exception {
        List<CourseRoomComboReqDTO> suitedRooms = new ArrayList<>();
        suitedRooms.add(null);

        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(new HashSet<>(suitedRooms))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndRoomsListInRoomComboReqDtoIsNull_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(new HashSet<>(List.of(new CourseRoomComboReqDTO(null))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndRoomComboReqDtoContainsNullValues_thenBadRequest() throws Exception {
        List<IdWrapperDTO> rooms = new ArrayList<>();
        rooms.add(null);
        rooms.add(new IdWrapperDTO(UUID.randomUUID()));
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(
                                new HashSet<>(
                                        List.of(new CourseRoomComboReqDTO(new HashSet<>(rooms)))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndRoomIdIsNull_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(
                                new HashSet<>(
                                        List.of(
                                                new CourseRoomComboReqDTO(
                                                        new HashSet<>(
                                                                List.of(new IdWrapperDTO(null)))))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndRoomCombinationWithNoRooms_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .suitedRooms(
                                new HashSet<>(
                                        List.of(
                                                new CourseRoomComboReqDTO(
                                                        new HashSet<>(
                                                                new ArrayList<>() // empty list of
                                                                // rooms
                                                                )))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndLecturersIsNull_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .lecturers(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndLecturerListContainsNullValues_thenBadRequest() throws Exception {
        List<IdWrapperDTO> lecturers = new ArrayList<>();
        lecturers.add(null);
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .lecturers(new HashSet<>(lecturers))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostCourseAndLecturerIdIsNull_thenBadRequest() throws Exception {
        CourseReqDTO courseReqDTO =
                CourseReqDTO.builder()
                        .casID("WS22SB037")
                        .name("Woolly Thinking")
                        .abbreviation("WT")
                        .description("description")
                        .blockSize(1)
                        .weeksPerSemester(12)
                        .slotsPerWeek(2)
                        .courseType(null)
                        .lecturers(new HashSet<>(List.of(new IdWrapperDTO(null))))
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/courses", timetableId), courseReqDTO))
                .andExpect(status().isBadRequest());
    }
}
