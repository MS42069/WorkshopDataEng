package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
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
public class WorkTimeServiceTest {

    @Mock private WorkTimeRepository workTimeRepository;

    @Mock private TimeslotService timeSlotService;

    @InjectMocks private WorkTimeService underTest;

    @Test
    void findAll() {
        // when
        underTest.findAll();
        // then
        verify(workTimeRepository).findAll();
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<WorkTime> emptyOptional = Optional.empty();
        // when
        when(workTimeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.WorkTime' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsWorkTime() {

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());
        workTime.setTimeslot(timeslot);
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setEmployee(employee);

        Optional<WorkTime> optionalWorkTime = Optional.of(workTime);
        // when
        when(workTimeRepository.findById(any(UUID.class))).thenReturn(optionalWorkTime);

        UUID id = UUID.randomUUID();
        // then
        WorkTime actualWorkTime = underTest.findByID(id);

        assertThat(actualWorkTime).isEqualTo(workTime);
    }

    @Test
    void whenSaveWorkTime_thenSuccess() {
        // given
        // Note: for testing the WorkTimeService it's not important that weekday, timeslot or
        // employee are valid
        // objects, only the ID is relevant

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());

        WorkTimeDTO workTimeDTO =
                WorkTimeDTO.builder()
                        .timeslotID(timeslot.getId())
                        .weekday(DayOfWeek.MONDAY)
                        .build();

        // when(weekdayService.findById(any(UUID.class))).thenReturn(weekday);
        when(timeSlotService.findByID(any(UUID.class))).thenReturn(timeslot);

        WorkTime expectedWorkTime =
                new WorkTime(UUID.randomUUID(), employee, DayOfWeek.MONDAY, timeslot);
        when(workTimeRepository.save(any(WorkTime.class))).thenReturn(expectedWorkTime);

        WorkTime actualWorkTime = underTest.save(workTimeDTO, employee);

        // then
        assertThat(actualWorkTime).isNotNull();
        assertThat(actualWorkTime.getTimeslot().getId()).isEqualTo(timeslot.getId());
        assertThat(actualWorkTime.getWeekday()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(actualWorkTime.getEmployee().getId()).isEqualTo(employee.getId());

        // verify(weekdayService, times(1)).findById(weekday.getId());
        verify(timeSlotService, times(1)).findByID(timeslot.getId());

        // capture and verify the method argument of workTimeRepository.save() method
        ArgumentCaptor<WorkTime> argument = ArgumentCaptor.forClass(WorkTime.class);
        verify(workTimeRepository, times(1)).save(argument.capture());
        assertEquals(DayOfWeek.MONDAY, argument.getValue().getWeekday());
        assertEquals(timeslot.getId(), argument.getValue().getTimeslot().getId());
        assertEquals(employee.getId(), argument.getValue().getEmployee().getId());
    }

    @Test
    void deleteWorkTime() {
        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());

        // when
        when(workTimeRepository.findById(any(UUID.class))).thenReturn(Optional.of(workTime));
        doNothing().when(workTimeRepository).deleteById(any(UUID.class));

        underTest.deleteWorkTime(workTime.getId());

        // then
        verify(workTimeRepository, times(1)).deleteById(workTime.getId());
    }
}
