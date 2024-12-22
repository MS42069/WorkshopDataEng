package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.SpecialEventRepository;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class SpecialEventServiceTest {

    @Mock private SpecialEventRepository specialEventRepository;

    @InjectMocks private SpecialEventService underTest;

    @Test
    void findAll() {

        Timetable timetable = new Timetable();
        // when
        underTest.findAll(timetable);
        // then
        verify(specialEventRepository).findByTimetableId(timetable.getId());
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<SpecialEvent> emptyOptional = Optional.empty();
        // when
        when(specialEventRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.SpecialEvent' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsSpecialEvent() {

        Timeslot start = new Timeslot();
        start.setId(UUID.randomUUID());
        start.setStartTime(LocalTime.parse("08:00"));
        start.setEndTime(LocalTime.parse("09:00"));
        start.setIndex(1);

        Timeslot end = new Timeslot();
        end.setId(UUID.randomUUID());
        end.setStartTime(LocalTime.parse("08:00"));
        end.setEndTime(LocalTime.parse("09:00"));
        end.setIndex(1);

        new Timetable();

        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));

        Optional<SpecialEvent> optional = Optional.of(specialEvent);
        // when
        when(specialEventRepository.findById(any(UUID.class))).thenReturn(optional);

        UUID id = UUID.randomUUID();
        // then
        SpecialEvent actual = underTest.findByID(id);

        assertThat(actual).isEqualTo(specialEvent);
    }

    @Test
    void whenSaveSpecialEvent_thenSuccess() {
        // given
        // Note: for testing the SpecialEventService it's not important that timeslot or timetable
        // are valid
        // objects, only the ID is relevant

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());

        SpecialEventReqDTO specialEventReqDTO =
                SpecialEventReqDTO.builder()
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .specialEventType(SpecialEventType.FREE)
                        .build();

        // when

        SpecialEvent expected = new SpecialEvent();
        expected.setTimetable(timetable);
        expected.setSpecialEventType(SpecialEventType.FREE);
        expected.setStartDate(LocalDate.of(2022, 1, 1));
        expected.setEndDate(LocalDate.of(2022, 1, 31));

        when(specialEventRepository.save(any(SpecialEvent.class))).thenReturn(expected);

        SpecialEvent actual = underTest.save(specialEventReqDTO, timetable);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getTimetable().getId()).isEqualTo(timetable.getId());

        // capture and verify the method argument of SpecialEvent.save() method
        ArgumentCaptor<SpecialEvent> argument = ArgumentCaptor.forClass(SpecialEvent.class);
        verify(specialEventRepository, times(1)).save(argument.capture());
        assertEquals(timetable.getId(), argument.getValue().getTimetable().getId());
    }

    @Test
    void deleteWorkTime() {
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setId(UUID.randomUUID());

        // when
        when(specialEventRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(specialEvent));
        doNothing().when(specialEventRepository).deleteById(any(UUID.class));

        underTest.deleteSpecialEvent(specialEvent.getId());

        // then
        verify(specialEventRepository, times(1)).deleteById(specialEvent.getId());
    }
}
