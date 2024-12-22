package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.TimeslotConverter;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeslotServiceTest {

    private final TimeslotConverter timeslotConverter = new TimeslotConverter(new ModelMapper());
    @Mock
    private TimeslotRepository timeslotRepository;
    @Mock
    private TimetableService timetableService;
    @InjectMocks
    private TimeslotService underTest;
    private Timetable timetable;

    @BeforeEach
    void setUp() {
        underTest = new TimeslotService(timeslotRepository, timetableService, timeslotConverter);

        timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
    }

    @Test
    void findAll() {

        // when
        when(timetableService.findByID(timetable.getId())).thenReturn(timetable);
        underTest.findAll(timetable.getId());
        // then
        verify(timeslotRepository).findByTimetableId(timetable.getId());
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<Timeslot> emptyOptional = Optional.empty();
        // when
        when(timeslotRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Timeslot' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsTimeslot() {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        Optional<Timeslot> optionalRoom = Optional.of(timeslot);
        // when
        when(timeslotRepository.findById(any(UUID.class))).thenReturn(optionalRoom);

        UUID id = UUID.randomUUID();
        // then
        Timeslot actualTimeslot = underTest.findByID(id);

        assertThat(actualTimeslot).isEqualTo(timeslot);
    }

    @Test
    void save() {
        // given
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(1)
                        .build();

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);

        // when
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);
        when(timeslotRepository.save(any(Timeslot.class))).thenReturn(timeslot);

        Timeslot actualTimeslot = underTest.save(timetable.getId(), timeslotReqDTO);

        // then
        assertThat(actualTimeslot).isNotNull();
        assertThat(actualTimeslot.getIndex()).isEqualTo(1);
    }

    @Test
    void updateRoom() {
        // given
        UUID uuid = UUID.randomUUID();
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .build();

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:15:00"));
        timeslot.setId(uuid);
        timeslot.setEndTime(LocalTime.parse("09:30:00"));
        timeslot.setIndex(1);


        // when
        when(timetableService.findByID(any(UUID.class)))
                .thenReturn(timetable);
        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(new ArrayList<>(List.of(timeslot)));
        Timeslot actualTimeslot = underTest.updateTimeslot(uuid, timeslotReqDTO, timetable.getId());

        // then
        assertThat(actualTimeslot).isNotNull();
        assertThat(actualTimeslot.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(actualTimeslot.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(actualTimeslot.getIndex()).isEqualTo(0);
    }

    @Test
    void deleteTimeslot() {
        // given
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);

        UUID uuid = UUID.randomUUID();

        // when
        when(timeslotRepository.findById(any(UUID.class))).thenReturn(Optional.of(timeslot));

        doNothing().when(timeslotRepository)
                .deleteById(any(UUID.class));

        underTest.deleteTimeslot(uuid);

        verify(timeslotRepository, times(1)).deleteById(uuid);
    }

    @Test
    public void
    givenTimeslot_whenSaveAndNewTimeslotIsContainedInGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("08:05"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("07:05"))
                        .endTime(LocalTime.parse("09:00"))
                        .index(2)
                        .build();

        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);
        when(timeslotRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(List.of(oldTimeslot));
        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void givenTimeslot_whenSaveAndNewTimeslotContainsGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("08:05"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("06:00"))
                        .endTime(LocalTime.parse("12:00"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void giveTimeslot_whenSaveAndEndTimeOverlapsInGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("08:05"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("07:05"))
                        .endTime(LocalTime.parse("08:15"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void givenTimeslot_whenSaveAndStartTimeOverlapsInGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("09:00"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:30"))
                        .endTime(LocalTime.parse("09:30"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void
    givenTimeslot_whenSaveAndStartTimeIsEqualEndTimeOfGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("08:30"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:30"))
                        .endTime(LocalTime.parse("09:15"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void
    givenTimeslot_whenSaveAndEndTimeIsEqualStartTimeOfGivenTimeslot_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("08:30"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("07:00"))
                        .endTime(LocalTime.parse("08:00"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void givenTimeslot_whenSaveTimeslotWithEqualTimeInterval_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setId(UUID.randomUUID());
        oldTimeslot.setStartTime(LocalTime.parse("08:00"));
        oldTimeslot.setEndTime(LocalTime.parse("09:00"));
        oldTimeslot.setIndex(1);
        oldTimeslot.setTimetable(timetable);

        TimeslotReqDTO newTimeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00"))
                        .endTime(LocalTime.parse("09:00"))
                        .index(2)
                        .build();

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(oldTimeslot);

        when(timeslotRepository.findByTimetableId(any(UUID.class))).thenReturn(timeslots);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        // when
        assertThatThrownBy(() -> underTest.save(timetable.getId(), newTimeslotReqDTO))
                // then
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.BAD_REQUEST
                                + " \"timeslot intervall overlaps with an existing timeslot");
    }

    @Test
    public void givenTimeslots_whenUpdateAndTimeslotOverlaps_thenThrowException() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot tsOne = new Timeslot();
        tsOne.setId(UUID.randomUUID());
        tsOne.setStartTime(LocalTime.parse("08:00"));
        tsOne.setEndTime(LocalTime.parse("09:15"));
        tsOne.setIndex(1);
        tsOne.setTimetable(timetable);

        Timeslot tsTwo = new Timeslot();
        tsTwo.setId(UUID.randomUUID());
        tsTwo.setStartTime(LocalTime.parse("09:30"));
        tsTwo.setEndTime(LocalTime.parse("10:45"));
        tsTwo.setIndex(2);
        tsTwo.setTimetable(timetable);

        when(timeslotRepository.findByTimetableId(any(UUID.class)))
                .thenReturn(List.of(tsOne, tsTwo));
        lenient().when(timeslotRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(tsOne));
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        TimeslotReqDTO updateDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00"))
                        .endTime(LocalTime.parse("10:00"))
                        .build();

        // when
        ResponseStatusException ex =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                underTest.updateTimeslot(
                                        tsOne.getId(), updateDTO, timetable.getId()));
        // then
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).contains("overlap");
    }
}
