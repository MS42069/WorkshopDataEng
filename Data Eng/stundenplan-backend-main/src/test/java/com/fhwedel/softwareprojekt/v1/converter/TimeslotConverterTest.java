package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class TimeslotConverterTest {

    private final TimeslotConverter timeslotConverter = new TimeslotConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(1)
                        .build();

        Timeslot timeslot = timeslotConverter.convertDtoToEntity(timeslotReqDTO);

        assertThat(timeslot).isNotNull();
        assertThat(timeslot.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(timeslot.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(timeslot.getIndex()).isEqualTo(1);
    }

    @Test
    void convertEntityToDto() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);
        // when
        TimeslotReqDTO convertDTO = timeslotConverter.convertEntityToDto(timeslot);
        // then
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(convertDTO.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(convertDTO.getIndex()).isEqualTo(1);
    }

    @Test
    void convertEntityToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        // when
        TimeslotResDTO convertDTO = timeslotConverter.convertEntityToResponseDTO(timeslot);
        // then
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getId()).isEqualTo(convertDTO.getId());
        assertThat(convertDTO.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(convertDTO.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(convertDTO.getIndex()).isEqualTo(1);
    }

    @Test
    void convertEntitiesToResponseDTOList() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        Timeslot timeslot1 = new Timeslot();
        timeslot1.setId(UUID.randomUUID());
        timeslot1.setStartTime(LocalTime.parse("08:00:00"));
        timeslot1.setEndTime(LocalTime.parse("09:15:00"));
        timeslot1.setIndex(1);
        timeslot1.setTimetable(timetable);

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(timeslot);
        timeslots.add(timeslot1);
        // when
        List<TimeslotResDTO> resultList =
                timeslotConverter.convertEntitiesToResponseDTOList(timeslots);

        // then
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);

        TimeslotResDTO dto1 = resultList.get(0);
        assertThat(dto1).isNotNull();
        assertThat(dto1.getId()).isEqualTo(timeslot.getId());
        assertThat(dto1.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(dto1.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(dto1.getIndex()).isEqualTo(1);

        TimeslotResDTO dto2 = resultList.get(1);
        assertThat(dto2).isNotNull();
        assertThat(dto2.getId()).isEqualTo(timeslot1.getId());
        assertThat(dto2.getStartTime()).isEqualTo(LocalTime.parse("08:00:00"));
        assertThat(dto2.getEndTime()).isEqualTo(LocalTime.parse("09:15:00"));
        assertThat(dto2.getIndex()).isEqualTo(1);
    }
}
