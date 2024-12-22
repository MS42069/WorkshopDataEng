package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.DegreeSemesterController;
import com.fhwedel.softwareprojekt.v1.converter.DegreeSemesterConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterResDTO;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.DegreeSemesterService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DegreeSemesterController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class DegreeSemesterControllerTest {

    @Autowired ObjectMapper objectMapper;
    @MockBean private DegreeSemesterService semesterService;
    @MockBean private DegreeSemesterConverter semesterConverter;
    @MockBean private SchedulerService schedulerService;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidSemester_thenCorrectResponse() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostAndInvalidZeroSemesterNumber_thenBadRequest() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(0)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullSemesterNumber_thenBadRequest() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(null)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNegativeSemesterAttendees_thenBadRequest() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(-1)
                        .extensionName("test")
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullSemesterAttendees_thenBadRequest() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(null)
                        .extensionName("test")
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/semesters", timetable.getId()),
                                semesterReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenEditSemester_thenReturnModifiedSemester() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemester updatedSemester = new DegreeSemester();
        updatedSemester.setId(UUID.randomUUID());
        updatedSemester.setSemesterNumber(1);
        updatedSemester.setAttendees(1);
        updatedSemester.setExtensionName("test");
        updatedSemester.setTimetable(timetable);

        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        DegreeSemesterResDTO responseDTO =
                DegreeSemesterResDTO.builder()
                        .id(updatedSemester.getId())
                        .semesterNumber(updatedSemester.getSemesterNumber())
                        .attendees(updatedSemester.getAttendees())
                        .extensionName(updatedSemester.getExtensionName())
                        .timetable(timetable.getId())
                        .build();

        when(semesterService.updateSemester(
                        any(UUID.class), any(DegreeSemesterReqDTO.class), any(UUID.class)))
                .thenReturn(updatedSemester);
        when(semesterConverter.convertEntityToResponseDTO(any(DegreeSemester.class)))
                .thenReturn(responseDTO);

        String url =
                format("/v1/timetable/%s/semesters/%s", timetable.getId(), updatedSemester.getId());
        this.mockMvc
                .perform(patch(url, semesterReqDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.extensionName", is(updatedSemester.getExtensionName())));
    }

    @Test
    public void whenDeleteSemester_thenReturnSuccessful() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setTimetable(timetable);

        doNothing().when(semesterService).deleteSemester(any(UUID.class));

        String url = format("/v1/timetable/%s/semesters/%s", timetable.getId(), semester.getId());
        this.mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        verify(semesterService, times(1)).deleteSemester(semester.getId());
    }
}
