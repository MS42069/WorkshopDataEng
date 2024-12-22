package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.TimeslotController;
import com.fhwedel.softwareprojekt.v1.converter.TimeslotConverter;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.TimeslotService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TimeslotController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account", password = "pwd", roles = "USER")
public class TimeslotControllerTest {

    private final UUID timetableId = UUID.randomUUID();
    @Autowired ObjectMapper objectMapper;
    @MockBean private TimeslotService timeslotService;
    @MockBean private TimeslotConverter timeslotConverter;
    @MockBean private SchedulerService schedulerService;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidTimeslot_thenCorrectResponse() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(0)
                        .build();

        mockMvc.perform(
                        post(format("/v1/timetable/%s/timeslots", timetableId), timeslotReqDTO)
                                .content(objectMapper.writeValueAsString(timeslotReqDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostAndInvalidTimeslotStartNull_thenBadRequestResponse() throws Exception {
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(null)
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(0)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/timeslots", timetableId), timeslotReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidTimeslotEndNull_thenBadRequestResponse() throws Exception {
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(null)
                        .index(0)
                        .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                        format("/v1/timetable/%s/timeslots", timetableId))
                                .content(objectMapper.writeValueAsString(timeslotReqDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidTimeslotIndexNegative_thenBadRequestResponse() throws Exception {
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(-1)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/timeslots", timetableId), timeslotReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidTimeslotIndexNull_thenBadRequestResponse() throws Exception {
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(null)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/timeslots", timetableId), timeslotReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidTimeslotStartTimeHigherThenEndTime_thenBadRequestResponse()
            throws Exception {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("08:05"));
        timeslot.setIndex(1);
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("07:00"))
                        .endTime(LocalTime.parse("06:00"))
                        .index(1)
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/timeslots", timetableId), timeslotReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenTimeslots_whenGetAllTimeslots_thenReturnListOfTimeslots() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        Timeslot timeslot1 = new Timeslot();
        timeslot1.setStartTime(LocalTime.parse("09:30:00"));
        timeslot1.setEndTime(LocalTime.parse("10:45:00"));
        timeslot1.setIndex(2);
        timeslot1.setTimetable(timetable);

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(timeslot);
        timeslots.add(timeslot1);

        TimeslotResDTO timeslotResDTO =
                TimeslotResDTO.builder()
                        .id(UUID.randomUUID())
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(1)
                        .timetable(timetable.getId())
                        .build();

        TimeslotResDTO timeslotResDTO2 =
                TimeslotResDTO.builder()
                        .id(UUID.randomUUID())
                        .startTime(LocalTime.parse("09:30:00"))
                        .endTime(LocalTime.parse("10:45:00"))
                        .index(2)
                        .timetable(timetable.getId())
                        .build();

        List<TimeslotResDTO> responseDTOList = List.of(timeslotResDTO, timeslotResDTO2);

        when(timeslotService.findAll(timetable.getId())).thenReturn(timeslots);
        when(timeslotConverter.convertEntitiesToResponseDTOList(ArgumentMatchers.anyCollection()))
                .thenReturn(responseDTOList);

        this.mockMvc
                .perform(get(format("/v1/timetable/%s/timeslots", timetableId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(timeslots.size())))
                .andExpect(jsonPath("$[0].index", is(timeslot.getIndex())))
                .andExpect(jsonPath("$[1].index", is(timeslot1.getIndex())));
    }

    @Test
    public void giveTimeslot_whenGetTimeslotById_thenReturnTimeslot() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        TimeslotResDTO timeslotResDTO =
                TimeslotResDTO.builder()
                        .id(UUID.randomUUID())
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(1)
                        .timetable(timetable.getId())
                        .build();
        when(timeslotService.findByID(any(UUID.class))).thenReturn(timeslot);
        when(timeslotConverter.convertEntityToResponseDTO(any(Timeslot.class)))
                .thenReturn(timeslotResDTO);

        this.mockMvc
                .perform(
                        get(format("/v1/timetable/%s/timeslots/%s", timetableId, timeslot.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime", is("08:00:00")))
                .andExpect(jsonPath("$.endTime", is("09:15:00")));
    }

    @Test
    public void whenEditTimeslot_thenReturnModifiedTimeslot() throws Exception {
        final String modifiedStartTime = "08:15:00";
        final String modifiedEndTime = "09:30:00";

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);
        timeslot.setId(UUID.randomUUID());

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse(modifiedStartTime))
                        .endTime(LocalTime.parse(modifiedEndTime))
                        .index(1)
                        .build();

        TimeslotResDTO timeslotResDTO =
                TimeslotResDTO.builder()
                        .id(UUID.randomUUID())
                        .startTime(LocalTime.parse("08:15:00"))
                        .endTime(LocalTime.parse("09:30:00"))
                        .index(1)
                        .timetable(timetable.getId())
                        .build();

        when(timeslotService.updateTimeslot(
                        any(UUID.class), any(TimeslotReqDTO.class), any(UUID.class)))
                .thenReturn(timeslot);
        when(timeslotConverter.convertEntityToResponseDTO(any(Timeslot.class)))
                .thenReturn(timeslotResDTO);

        this.mockMvc
                .perform(
                        patch(
                                format(
                                        "/v1/timetable/%s/timeslots/%s",
                                        timetableId, timeslot.getId()),
                                timeslotReqDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime", is(modifiedStartTime)))
                .andExpect(jsonPath("$.endTime", is(modifiedEndTime)));
    }

    @Test
    public void whenDeleteTimeslotThenReturnSuccessful() throws Exception {
        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);

        doNothing().when(timeslotService).deleteTimeslot(any(UUID.class));

        this.mockMvc
                .perform(
                        delete(
                                format(
                                        "/v1/timetable/%s/timeslots/%s",
                                        timetableId, timeslot.getId())))
                .andExpect(status().isNoContent());

        verify(timeslotService, times(1)).deleteTimeslot(timeslot.getId());
    }
}
