package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.TimetableController;
import com.fhwedel.softwareprojekt.v1.converter.SpecialEventConverter;
import com.fhwedel.softwareprojekt.v1.converter.TimetableConverter;
import com.fhwedel.softwareprojekt.v1.dto.SpecialEventResDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableResDTO;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.CopyService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.TimetableService;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TimetableController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class TimetableControllerTest {

    @MockBean private TimetableService timetableService;

    @MockBean private SemesterTypeService semesterTypeService;

    @MockBean private CopyService copyService;
    @MockBean private TimetableConverter timetableConverter;
    @MockBean private SpecialEventConverter specialEventConverter;

    @MockBean private SchedulerService schedulerService;

    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidTimetable_thenCorrectResponse() throws Exception {
        TimetableReqDTO timetableReqDTO =
                TimetableReqDTO.builder()
                        .startDate(LocalDate.of(2022, 1, 1))
                        .endDate(LocalDate.of(2022, 1, 31))
                        .name("Test")
                        .numberOfWeeks(12)
                        .specialEvents(new ArrayList<>())
                        .build();

        mockMvc.perform(post("/v1/timetables", timetableReqDTO)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndNoNameGiven() throws Exception {
        TimetableReqDTO timetableReqDTO =
                TimetableReqDTO.builder()
                        .startDate(LocalDate.of(2022, 1, 1))
                        .endDate(LocalDate.of(2022, 1, 31))
                        .numberOfWeeks(12)
                        .specialEvents(new ArrayList<>())
                        .build();

        mockMvc.perform(post("/v1/timetables", timetableReqDTO)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndNameIsEmpty() throws Exception {
        TimetableReqDTO timetableReqDTO =
                TimetableReqDTO.builder()
                        .startDate(LocalDate.of(2022, 1, 1))
                        .endDate(LocalDate.of(2022, 1, 31))
                        .numberOfWeeks(12)
                        .name("")
                        .specialEvents(new ArrayList<>())
                        .build();

        mockMvc.perform(post("/v1/timetables", timetableReqDTO)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndSpecialEventsIsNull() throws Exception {
        TimetableReqDTO timetableReqDTO =
                TimetableReqDTO.builder()
                        .startDate(LocalDate.of(2022, 1, 1))
                        .endDate(LocalDate.of(2022, 1, 31))
                        .numberOfWeeks(12)
                        .name("")
                        .build();

        mockMvc.perform(post("/v1/timetables", timetableReqDTO)).andExpect(status().isBadRequest());
    }

    @Test
    public void givenTimetables_whenGetAllTimetables_thenReturnListOfTimetables() throws Exception {
        Timetable timetable1 = new Timetable();
        timetable1.setNumberOfWeeks(12);
        timetable1.setEndDate(LocalDate.of(2022, 1, 31));
        timetable1.setStartDate(LocalDate.of(2022, 1, 1));
        timetable1.setName("t1");
        timetable1.setSpecialEvents(new ArrayList<>());

        Timetable timetable2 = new Timetable();
        timetable2.setNumberOfWeeks(12);
        timetable2.setEndDate(LocalDate.of(2022, 1, 31));
        timetable2.setStartDate(LocalDate.of(2022, 1, 1));
        timetable2.setName("t2");
        timetable2.setSpecialEvents(new ArrayList<>());

        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetable1);
        timetables.add(timetable2);

        TimetableResDTO timetableResDTO1 =
                TimetableResDTO.builder()
                        .name(timetable1.getName())
                        .startDate(timetable1.getStartDate())
                        .endDate(timetable1.getEndDate())
                        .numberOfWeeks(timetable1.getNumberOfWeeks())
                        .build();
        TimetableResDTO timetableResDTO2 =
                TimetableResDTO.builder()
                        .name(timetable2.getName())
                        .startDate(timetable2.getStartDate())
                        .endDate(timetable2.getEndDate())
                        .numberOfWeeks(timetable2.getNumberOfWeeks())
                        .build();

        List<TimetableResDTO> responseDTOList = List.of(timetableResDTO1, timetableResDTO2);

        when(timetableService.findAll()).thenReturn(timetables);
        when(timetableConverter.convertEntitiesToResponseDTOList(ArgumentMatchers.anyCollection()))
                .thenReturn(responseDTOList);

        this.mockMvc
                .perform(get("/v1/timetables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(timetables.size())))
                .andExpect(jsonPath("$[0].name", is(timetable1.getName())))
                .andExpect(jsonPath("$[1].name", is(timetable2.getName())));
    }

    @Test
    public void giveTimetable_whenGetTimetableById_thenReturnTimetable() throws Exception {
        Timetable timetable1 = new Timetable();
        timetable1.setNumberOfWeeks(12);
        timetable1.setEndDate(LocalDate.of(2022, 1, 31));
        timetable1.setStartDate(LocalDate.of(2022, 1, 1));
        timetable1.setName("t1");
        timetable1.setSpecialEvents(new ArrayList<>());

        Timeslot startTimeslot = new Timeslot();
        startTimeslot.setStartTime(LocalTime.parse("08:00"));
        startTimeslot.setEndTime(LocalTime.parse("09:00"));
        startTimeslot.setIndex(1);
        startTimeslot.setId(UUID.randomUUID());

        Timeslot endTimeslot = new Timeslot();
        endTimeslot.setStartTime(LocalTime.parse("12:00"));
        endTimeslot.setEndTime(LocalTime.parse("13:00"));
        endTimeslot.setIndex(1);
        endTimeslot.setId(UUID.randomUUID());

        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setTimetable(timetable1);
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));

        timetable1.getSpecialEvents().add(specialEvent);

        SpecialEventResDTO resDTO =
                SpecialEventResDTO.builder()
                        .specialEventType(specialEvent.getSpecialEventType())
                        .endDate(specialEvent.getEndDate())
                        .startDate(specialEvent.getStartDate())
                        .build();

        TimetableResDTO timetableResDTO1 =
                TimetableResDTO.builder()
                        .name(timetable1.getName())
                        .startDate(timetable1.getStartDate())
                        .endDate(timetable1.getEndDate())
                        .numberOfWeeks(timetable1.getNumberOfWeeks())
                        .specialEvents(List.of(resDTO))
                        .build();

        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable1);
        when(timetableConverter.convertEntityToResponseDTO(any(Timetable.class)))
                .thenReturn(timetableResDTO1);

        this.mockMvc
                .perform(get(format("/v1/timetables/%s", UUID.randomUUID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(timetable1.getName())))
                .andExpect(
                        jsonPath(
                                "$.specialEvents.size()",
                                is(timetable1.getSpecialEvents().size())));
    }

    @Test
    public void whenUpdateTimetable_thenReturnModifiedTimetable() throws Exception {
        Timetable timetable1 = new Timetable();
        timetable1.setId(UUID.randomUUID());
        timetable1.setNumberOfWeeks(12);
        timetable1.setEndDate(LocalDate.of(2022, 1, 31));
        timetable1.setStartDate(LocalDate.of(2022, 1, 1));
        timetable1.setName("t1");
        timetable1.setSpecialEvents(new ArrayList<>());

        TimetableReqDTO timetableReqDTO =
                TimetableReqDTO.builder()
                        .startDate(LocalDate.of(2022, 1, 1))
                        .endDate(LocalDate.of(2022, 1, 31))
                        .numberOfWeeks(12)
                        .name("t2")
                        .specialEvents(new ArrayList<>())
                        .build();

        TimetableResDTO responseDTO =
                TimetableResDTO.builder()
                        .name("t1")
                        .endDate(timetable1.getEndDate())
                        .startDate(timetable1.getStartDate())
                        .numberOfWeeks(timetable1.getNumberOfWeeks())
                        .build();

        when(timetableService.updateTimetable(any(UUID.class), any(TimetableReqDTO.class)))
                .thenReturn(timetable1);
        when(timetableConverter.convertEntityToResponseDTO(any(Timetable.class)))
                .thenReturn(responseDTO);

        this.mockMvc
                .perform(put(format("/v1/timetables/%s", timetable1.getId()), timetableReqDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(timetable1.getName())));
    }

    @Test
    public void whenDeleteEmployee_thenReturnSuccessful() throws Exception {
        Timetable timetable1 = new Timetable();
        timetable1.setId(UUID.randomUUID());
        timetable1.setNumberOfWeeks(12);
        timetable1.setEndDate(LocalDate.of(2022, 1, 31));
        timetable1.setStartDate(LocalDate.of(2022, 1, 1));
        timetable1.setName("t1");
        timetable1.setSpecialEvents(new ArrayList<>());

        doNothing().when(timetableService).deleteTimetable(any(UUID.class));

        this.mockMvc
                .perform(delete(format("/v1/timetables/%s", timetable1.getId())))
                .andExpect(status().isNoContent());

        verify(timetableService, times(1)).deleteTimetable(timetable1.getId());
    }
}
