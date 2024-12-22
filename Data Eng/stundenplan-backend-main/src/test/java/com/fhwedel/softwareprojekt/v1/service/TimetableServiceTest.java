package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.TimetableConverter;
import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class TimetableServiceTest {

    private final ModelMapper modelMapper = setModelMapper();
    private final TimetableConverter timetableConverter = new TimetableConverter(modelMapper);
    @Mock private TimetableRepository timetableRepository;
    @Mock private TimeslotRepository timeslotRepository;
    @InjectMocks private TimetableService underTest;
    @Mock private SpecialEventService specialEventService;

    @Mock private CourseRepository courseRepository;

    @Mock private DegreeRepository degreeRepository;

    @Mock private SemesterTypeService semesterTypeService;

    @Mock private EmployeeRepository employeeRepository;

    @Mock private RoomRepository roomRepository;

    @Mock private WeekEventRepository weekEventRepository;

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @BeforeEach
    void setUp() {
        underTest =
                new TimetableService(
                        timetableRepository,
                        semesterTypeService,
                        timetableConverter,
                        specialEventService,
                        timeslotRepository,
                        courseRepository,
                        degreeRepository,
                        employeeRepository,
                        roomRepository,
                        weekEventRepository);
    }

    @Test
    void findAll() {
        // when
        underTest.findAll();
        // then
        verify(timetableRepository).findAll();
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<Timetable> emptyOptional = Optional.empty();
        // when
        when(timetableRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Timetable' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsTimetable() {
        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        Optional<Timetable> optionalTimetable = Optional.of(timetable);
        // when
        when(timetableRepository.findById(any(UUID.class))).thenReturn(optionalTimetable);

        UUID id = UUID.randomUUID();
        // then
        Timetable foundTimetable = underTest.findByID(id);

        assertThat(foundTimetable).isEqualTo(timetable);
    }

    @Test
    void saveTimetableWithoutSpecialEvents() {
        // given
        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        // when
        when(timetableRepository.save(any(Timetable.class))).thenReturn(timetable);

        Timetable savedTimetable = underTest.save(reqDTO);

        // then
        assertThat(savedTimetable).isNotNull();
        assertThat(savedTimetable.getName()).isEqualTo("test");
    }

    @Test
    void saveEmployeeWithSpecialEvents() {
        // given
        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();
        SpecialEventReqDTO specialEventReqDTO =
                SpecialEventReqDTO.builder()
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .specialEventType(SpecialEventType.FREE)
                        .build();

        reqDTO.getSpecialEvents().add(specialEventReqDTO);

        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        // Timeslot and Date of a specialEvent only concern the specialEventService thus are
        // negligible in this test
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setId(UUID.randomUUID());

        // when
        when(timetableRepository.save(any(Timetable.class))).thenReturn(timetable);
        when(specialEventService.save(specialEventReqDTO, timetable)).thenReturn(specialEvent);

        Timetable actual = underTest.save(reqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("test");
        // verify that a special event was added
        verify(specialEventService, times(1)).save(specialEventReqDTO, timetable);
        assertThat(actual.getSpecialEvents().size()).isEqualTo(1);
        assertThat(actual.getSpecialEvents().get(0)).isEqualTo(specialEvent);
    }

    @Test
    void updateTimetable() {
        // given
        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();

        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        UUID uuid = UUID.randomUUID();

        // when
        when(timetableRepository.findById(any(UUID.class))).thenReturn(Optional.of(timetable));

        Timetable actual = underTest.updateTimetable(uuid, reqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("name");
    }

    @Test
    void updateTimetableWithSpecialEvents() {
        // given
        TimetableReqDTO reqDTO =
                TimetableReqDTO.builder()
                        .specialEvents(new ArrayList<>())
                        .name("name")
                        .numberOfWeeks(12)
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .build();
        SpecialEventReqDTO specialEventReqDTO =
                SpecialEventReqDTO.builder()
                        .endDate(LocalDate.of(2022, 1, 31))
                        .startDate(LocalDate.of(2022, 1, 31))
                        .specialEventType(SpecialEventType.FREE)
                        .build();

        reqDTO.setSpecialEvents(List.of(specialEventReqDTO));

        SpecialEvent old = new SpecialEvent();
        old.setId(UUID.randomUUID());

        Timetable toUpdate = new Timetable();
        toUpdate.setId(UUID.randomUUID());
        toUpdate.setEndDate(LocalDate.of(2022, 1, 31));
        toUpdate.setStartDate(LocalDate.of(2022, 1, 1));
        toUpdate.setName("test");
        toUpdate.setSpecialEvents(new ArrayList<>());
        toUpdate.setNumberOfWeeks(12);

        // when
        when(timetableRepository.findById(toUpdate.getId())).thenReturn(Optional.of(toUpdate));
        SpecialEvent newEvent = new SpecialEvent();
        newEvent.setId(UUID.randomUUID());
        when(specialEventService.save(any(SpecialEventReqDTO.class), any(Timetable.class)))
                .thenReturn(newEvent);

        Timetable actual = underTest.updateTimetable(toUpdate.getId(), reqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("name");

        List<SpecialEvent> actualEvents = actual.getSpecialEvents();
        assertThat(actualEvents.size()).isEqualTo(1);
        assertThat(actualEvents.get(0).getId()).isEqualTo(newEvent.getId());
    }

    @Test
    void deleteTimetable() {
        // given
        Timetable timetable = new Timetable();
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        UUID uuid = UUID.randomUUID();

        // when
        when(timetableRepository.findById(any(UUID.class))).thenReturn(Optional.of(timetable));

        doNothing().when(timetableRepository).deleteById(any(UUID.class));

        underTest.deleteTimetable(uuid);

        verify(timetableRepository, times(1)).deleteById(uuid);
    }
}
