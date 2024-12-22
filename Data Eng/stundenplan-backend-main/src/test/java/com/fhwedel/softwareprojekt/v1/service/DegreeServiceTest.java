package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.DegreeConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.service.types.SchoolTypeService;
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
public class DegreeServiceTest {

    private final UUID timetableId = UUID.randomUUID();
    private final ModelMapper modelMapper = setModelMapper();
    private final DegreeConverter degreeConverter = new DegreeConverter(modelMapper);
    @Mock private DegreeRepository degreeRepository;
    @Mock private DegreeSemesterRepository degreeSemesterRepository;
    @Mock private TimetableService timetableService;
    @Mock private SchoolTypeService schoolTypeService;
    @InjectMocks private DegreeService underTest;

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @BeforeEach
    void setUp() {
        underTest =
                new DegreeService(
                        degreeRepository,
                        timetableService,
                        degreeConverter,
                        schoolTypeService,
                        degreeSemesterRepository);
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

        when(timetableService.findByID(timetable.getId())).thenReturn(timetable);
        underTest.findAll(timetable.getId());
        // then
        verify(degreeRepository).findByTimetable(timetable);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<Degree> emptyOptional = Optional.empty();
        // when
        when(degreeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Degree' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsDegree() {
        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);

        Optional<Degree> optionalEmployee = Optional.of(degree);
        // when
        when(degreeRepository.findById(any(UUID.class))).thenReturn(optionalEmployee);

        UUID id = UUID.randomUUID();
        // then
        Degree actualDegree = underTest.findByID(id);

        assertThat(actualDegree).isEqualTo(degree);
    }

    @Test
    void saveDegreeWithoutSemesters() {
        // given
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);

        // when
        when(degreeRepository.save(any(Degree.class))).thenReturn(degree);

        Degree actualDegree = underTest.save(degreeReqDTO, timetableId);

        // then
        assertThat(actualDegree).isNotNull();
        assertThat(actualDegree.getName()).isEqualTo("degree2");
    }

    @Test
    void saveDegreeWithSemester() {
        // given
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .semesters(List.of(UUID.randomUUID()))
                        .build();

        DegreeSemester semester = new DegreeSemester();
        semester.setAttendees(1);
        semester.setSemesterNumber(1);
        semester.setExtensionName("test");

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setSemesters(List.of(semester));

        // when
        when(degreeRepository.save(any(Degree.class))).thenReturn(degree);
        when(degreeSemesterRepository.findById(any(UUID.class))).thenReturn(Optional.of(semester));
        Degree actualDegree = underTest.save(degreeReqDTO, timetableId);

        // then
        assertThat(actualDegree).isNotNull();
        assertThat(actualDegree.getName()).isEqualTo("degree2");
        assertThat(actualDegree.getSemesters().size()).isEqualTo(1);
        assertThat(actualDegree.getSemesters().get(0)).isEqualTo(semester);
    }

    @Test
    void updateDegree() {
        // given
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);

        UUID uuid = UUID.randomUUID();

        // when
        when(degreeRepository.findById(any(UUID.class))).thenReturn(Optional.of(degree));

        Degree actualDegree = underTest.updateDegree(uuid, degreeReqDTO, timetableId);

        // then
        assertThat(actualDegree).isNotNull();
        assertThat(actualDegree.getName()).isEqualTo("degree");
    }

    @Test
    void deleteDegree() {
        // given
        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);

        UUID uuid = UUID.randomUUID();

        // when
        when(degreeRepository.findById(any(UUID.class))).thenReturn(Optional.of(degree));

        doNothing().when(degreeRepository).deleteById(any(UUID.class));

        underTest.deleteDegree(uuid);

        verify(degreeRepository, times(1)).deleteById(uuid);
    }
}
