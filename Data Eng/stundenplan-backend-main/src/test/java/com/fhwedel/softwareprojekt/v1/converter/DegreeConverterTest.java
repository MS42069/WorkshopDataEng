package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class DegreeConverterTest {
    private final DegreeConverter degreeConverter = new DegreeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        DegreeReqDTO degreeReqDTO =
                DegreeReqDTO.builder()
                        .name("degree")
                        .schoolType(null)
                        .semesterAmount(7)
                        .studyRegulation("14.0")
                        .shortName("degree")
                        .build();

        Degree degree = degreeConverter.convertDtoToEntity(degreeReqDTO);

        assertThat(degree).isNotNull();
        assertThat(degree.getName()).isEqualTo("degree");
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

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setTimetable(timetable);

        DegreeReqDTO convertDTO = degreeConverter.convertEntityToDto(degree);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("degree2");
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
        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setTimetable(timetable);
        DegreeResDTO convertDTO = degreeConverter.convertEntityToResponseDTO(degree);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("degree2");
    }

    @Test
    void convertEntityWithSemestersToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        degree.setTimetable(timetable);
        DegreeSemester degreeSemester = new DegreeSemester();
        degreeSemester.setSemesterNumber(1);
        degreeSemester.setExtensionName("ext");
        degreeSemester.setDegree(degree);
        degree.setSemesters(List.of(degreeSemester));
        DegreeResDTO convertDTO = degreeConverter.convertEntityToResponseDTO(degree);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("degree2");
        assertThat(convertDTO.getSemesters().get(0)).isNotNull();
    }

    @Test
    void convertEntitiesToResponseDTOList() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Degree degree1 = new Degree();
        degree1.setId(UUID.randomUUID());
        degree1.setName("degree");
        degree1.setSchoolType(null);
        degree1.setStudyRegulation("14.0");
        degree1.setShortName("degree");
        degree1.setSemesterAmount(7);
        degree1.setTimetable(timetable);
        Degree degree2 = new Degree();
        degree2.setId(UUID.randomUUID());
        degree2.setName("degree2");
        degree2.setSchoolType(null);
        degree2.setStudyRegulation("14.0");
        degree2.setShortName("degree2");
        degree2.setSemesterAmount(7);
        degree2.setTimetable(timetable);

        List<Degree> degrees = new ArrayList<>();
        degrees.add(degree1);
        degrees.add(degree2);

        List<DegreeResDTO> convertDTO = degreeConverter.convertEntitiesToResponseDTOList(degrees);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
