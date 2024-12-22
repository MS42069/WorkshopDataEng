package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.DegreeSemesterConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class DegreeSemesterServiceTest {

    private final UUID timetableId = UUID.randomUUID();
    private final ModelMapper modelMapper = setModelMapper();
    private final DegreeSemesterConverter semesterConverter =
            new DegreeSemesterConverter(modelMapper);
    @Mock private DegreeRepository degreeRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private TimetableService timetableService;
    @Mock private DegreeSemesterRepository degreeSemesterRepository;
    @InjectMocks private DegreeSemesterService underTest;

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @BeforeEach
    void setUp() {
        underTest =
                new DegreeSemesterService(
                        degreeSemesterRepository,
                        degreeRepository,
                        courseRepository,
                        semesterConverter,
                        timetableService);
    }

    @Test
    void findAll() {
        // when
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);
        underTest.findAll(UUID.randomUUID());
        // then
        verify(degreeSemesterRepository).findByTimetable(any(Timetable.class));
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<DegreeSemester> emptyOptional = Optional.empty();
        // when
        when(degreeSemesterRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.DegreeSemester' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsDegree() {
        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");

        Optional<DegreeSemester> optionalSemester = Optional.of(semester);
        // when
        when(degreeSemesterRepository.findById(any(UUID.class))).thenReturn(optionalSemester);

        UUID id = UUID.randomUUID();
        // then
        DegreeSemester actualSemester = underTest.findByID(id);

        assertThat(actualSemester).isEqualTo(semester);
    }

    @Test
    void saveSemesterWithoutDegreeAndCourses() {
        // given
        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");

        // when
        when(degreeSemesterRepository.save(any(DegreeSemester.class))).thenReturn(semester);

        DegreeSemester actualSemester = underTest.save(semesterReqDTO, timetableId);

        // then
        assertThat(actualSemester).isNotNull();
        assertThat(actualSemester.getExtensionName()).isEqualTo("test");
    }

    @Test
    void updateSemester() {
        // given
        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test2");

        UUID uuid = UUID.randomUUID();

        // when
        when(degreeSemesterRepository.findById(any(UUID.class))).thenReturn(Optional.of(semester));

        DegreeSemester actualSemester = underTest.updateSemester(uuid, semesterReqDTO, timetableId);

        // then
        assertThat(actualSemester).isNotNull();
        assertThat(actualSemester.getExtensionName()).isEqualTo("test");
    }

    @Test
    void deleteSemester() {
        // given
        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test2");

        UUID uuid = UUID.randomUUID();

        // when
        when(degreeSemesterRepository.findById(any(UUID.class))).thenReturn(Optional.of(semester));

        doNothing().when(degreeSemesterRepository).deleteById(any(UUID.class));

        underTest.deleteSemester(uuid);

        verify(degreeSemesterRepository, times(1)).deleteById(uuid);
    }
}
