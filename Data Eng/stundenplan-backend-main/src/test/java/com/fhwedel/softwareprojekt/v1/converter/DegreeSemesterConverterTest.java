package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterResDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
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
public class DegreeSemesterConverterTest {

    private final DegreeSemesterConverter semesterConverter =
            new DegreeSemesterConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        DegreeSemesterReqDTO semesterReqDTO =
                DegreeSemesterReqDTO.builder()
                        .semesterNumber(1)
                        .attendees(1)
                        .extensionName("test")
                        .build();

        DegreeSemester semester = semesterConverter.convertDtoToEntity(semesterReqDTO);

        assertThat(semester).isNotNull();
        assertThat(semester.getExtensionName()).isEqualTo("test");
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

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setTimetable(timetable);

        DegreeSemesterReqDTO convertDTO = semesterConverter.convertEntityToDto(semester);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getExtensionName()).isEqualTo("test");
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

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setTimetable(timetable);
        DegreeSemesterResDTO convertDTO = semesterConverter.convertEntityToResponseDTO(semester);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getExtensionName()).isEqualTo("test");
    }

    @Test
    void convertEntityWithDegreesAndCoursesToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Course course = new Course();
        course.setCourseType(null);
        course.setAbbreviation("name");
        course.setName("name");
        course.setCasID("name");
        course.setSlotsPerWeek(1);
        course.setWeeksPerSemester(1);
        course.setBlockSize(1);
        Degree degree = new Degree();
        degree.setId(UUID.randomUUID());
        degree.setName("degree2");
        degree.setSchoolType(null);
        degree.setStudyRegulation("14.0");
        degree.setShortName("degree");
        degree.setSemesterAmount(7);
        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setDegree(degree);
        semester.setCourses(List.of(course));
        semester.setTimetable(timetable);

        DegreeSemesterResDTO convertDTO = semesterConverter.convertEntityToResponseDTO(semester);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getExtensionName()).isEqualTo("test");
        assertThat(convertDTO.getDegree().getName()).isEqualTo("degree2");
        assertThat(convertDTO.getCourses().get(0)).isNotNull();
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

        DegreeSemester semester = new DegreeSemester();
        semester.setId(UUID.randomUUID());
        semester.setSemesterNumber(1);
        semester.setAttendees(1);
        semester.setExtensionName("test");
        semester.setTimetable(timetable);
        DegreeSemester semester2 = new DegreeSemester();
        semester2.setId(UUID.randomUUID());
        semester2.setSemesterNumber(1);
        semester2.setAttendees(1);
        semester2.setExtensionName("test2");
        semester2.setTimetable(timetable);

        List<DegreeSemester> semesters = new ArrayList<>();
        semesters.add(semester);
        semesters.add(semester2);

        List<DegreeSemesterResDTO> convertDTO =
                semesterConverter.convertEntitiesToResponseDTOList(semesters);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
