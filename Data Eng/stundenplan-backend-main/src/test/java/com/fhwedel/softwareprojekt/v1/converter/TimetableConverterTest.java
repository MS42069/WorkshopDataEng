package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableResDTO;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@ExtendWith(MockitoExtension.class)
public class TimetableConverterTest {

    private final ModelMapper modelMapper = setModelMapper();
    private final TimetableConverter timetableConverter = new TimetableConverter(modelMapper);

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Test
    void convertDtoToEntity() {
        SpecialEventReqDTO specialEventReqDTO =
                SpecialEventReqDTO.builder()
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .specialEventType(SpecialEventType.FREE)
                        .build();
        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(List.of(specialEventReqDTO))
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        Timetable timetable = timetableConverter.convertDtoToEntity(reqDTO);

        assertThat(timetable).isNotNull();
        assertThat(timetable.getName()).isEqualTo("name");
        assertThat(timetable.getSpecialEvents().size()).isEqualTo(1);
    }

    @Test
    void convertEntityToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        TimetableResDTO convertDTO = timetableConverter.convertEntityToResponseDTO(timetable);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {
        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        Timetable timetable2 = new Timetable();
        timetable2.setEndDate(LocalDate.of(2022, 1, 31));
        timetable2.setStartDate(LocalDate.of(2022, 1, 1));
        timetable2.setName("test");
        timetable2.setSpecialEvents(new ArrayList<>());
        timetable2.setNumberOfWeeks(12);

        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetable);
        timetables.add(timetable2);

        List<TimetableResDTO> convertDTO =
                timetableConverter.convertEntitiesToResponseDTOList(timetables);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }

    @Test
    void convertEntityWithSpecialEventsToResponseDTO() {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(List.of(specialEvent));
        timetable.setNumberOfWeeks(12);
        specialEvent.setTimetable(timetable);

        TimetableResDTO convertDTO = timetableConverter.convertEntityToResponseDTO(timetable);

        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
        assertThat(convertDTO.getSpecialEvents().size()).isEqualTo(1);
        assertThat(convertDTO.getSpecialEvents().get(0).getStartDate().toString())
                .isEqualTo("2022-01-01");
    }
}
