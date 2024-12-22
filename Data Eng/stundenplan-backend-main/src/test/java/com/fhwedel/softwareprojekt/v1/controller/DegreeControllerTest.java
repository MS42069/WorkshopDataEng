package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.DegreeController;
import com.fhwedel.softwareprojekt.v1.converter.DegreeConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.DegreeService;
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

@WebMvcTest(DegreeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class DegreeControllerTest {
    private final UUID timetableId = UUID.randomUUID();
    @Autowired ObjectMapper objectMapper;
    @MockBean private DegreeService degreeService;
    @MockBean private DegreeConverter degreeConverter;
    @MockBean private SchedulerService schedulerService;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidDegree_thenCorrectResponse() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostAndInvalidBlankDegreeName_thenBadRequestResponse() throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullDegreeName_thenBadRequestResponse() throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name(null)
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidBlankDegreeShortName_thenBadRequestResponse() throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullDegreeShortName_thenBadRequestResponse() throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidZeroDegreeSemesterAmount_thenBadRequestResponse()
            throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(0)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullDegreeSemesterAmount_thenBadRequestResponse()
            throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(null)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndNullDegreeSchoolType_thenOkResponse() throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostAndInvalidBlankDegreeStudyRegulation_thenBadRequestResponse()
            throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("")
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullDegreeStudyRegulation_thenBadRequestResponse()
            throws Exception {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation(null)
                        .shortName("degree")
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/degrees", timetableId), degreeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenEditDegree_thenReturnModifiedDegree() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Degree updatedDegree = new Degree();
        updatedDegree.setId(UUID.randomUUID());
        updatedDegree.setName("degree2");
        updatedDegree.setSchoolType(null);
        updatedDegree.setStudyRegulation("14.0");
        updatedDegree.setShortName("degree");
        updatedDegree.setSemesterAmount(7);
        updatedDegree.setTimetable(timetable);

        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        DegreeResDTO responseDTO =
                DegreeResDTO.builder()
                        .id(updatedDegree.getId())
                        .name(updatedDegree.getName())
                        .schoolType(null)
                        .semesterAmount(updatedDegree.getSemesterAmount())
                        .studyRegulation(updatedDegree.getStudyRegulation())
                        .shortName(updatedDegree.getShortName())
                        .timetable(timetable.getId())
                        .build();

        when(degreeService.updateDegree(any(UUID.class), any(DegreeReqDTO.class), any(UUID.class)))
                .thenReturn(updatedDegree);
        when(degreeConverter.convertEntityToResponseDTO(any(Degree.class))).thenReturn(responseDTO);

        String url = format("/v1/timetable/%s/degrees/%s", timetable.getId(), UUID.randomUUID());
        this.mockMvc
                .perform(patch(url, degreeReqDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedDegree.getName())))
                .andExpect(jsonPath("$.shortName", is(updatedDegree.getShortName())));
    }

    @Test
    public void whenDeleteDegree_thenReturnSuccessful() throws Exception {
        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);

        doNothing().when(degreeService).deleteDegree(any(UUID.class));

        String url =
                format(
                        format("/v1/timetable/%s/degrees/%s", timetableId, degree.getId()),
                        degree.getId());
        this.mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        verify(degreeService, times(1)).deleteDegree(degree.getId());
    }
}
