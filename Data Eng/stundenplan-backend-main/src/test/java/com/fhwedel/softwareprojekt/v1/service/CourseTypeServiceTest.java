package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.types.CourseTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import com.fhwedel.softwareprojekt.v1.repository.types.CourseTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.CourseTypeService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class CourseTypeServiceTest {

    private final CourseTypeConverter courseTypeConverter =
            new CourseTypeConverter(new ModelMapper());
    @Mock private CourseTypeRepository courseTypeRepository;
    @InjectMocks private CourseTypeService underTest;

    public CourseTypeServiceTest() {}

    @BeforeEach
    void setUp() {
        underTest = new CourseTypeService(courseTypeRepository, courseTypeConverter);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<CourseType> emptyOptional = Optional.empty();
        // when
        when(courseTypeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.types.CourseType' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsCourseType() {
        CourseType courseType = new CourseType();
        courseType.setName("VL");

        Optional<CourseType> optionalCourse = Optional.of(courseType);
        // when
        when(courseTypeRepository.findById(any(UUID.class))).thenReturn(optionalCourse);

        UUID id = UUID.randomUUID();
        // then
        CourseType actualCourseType = underTest.findByID(id);

        assertThat(actualCourseType).isEqualTo(courseType);
    }

    @Test
    void save() {
        // given
        CourseTypeReqDTO courseTypeReqDTO = CourseTypeReqDTO.builder().name("VL").build();

        CourseType courseType = new CourseType();

        courseType.setName("VL");

        // when
        when(courseTypeRepository.save(any(CourseType.class))).thenReturn(courseType);

        CourseType actualCourseType = underTest.save(courseTypeReqDTO);

        // then
        assertThat(actualCourseType).isNotNull();
        assertThat(actualCourseType.getName()).isEqualTo("VL");
    }

    @Test
    void updateCourse() {
        // given
        CourseTypeReqDTO courseTypeReqDTO = CourseTypeReqDTO.builder().name("VL").build();

        CourseType courseType = new CourseType();
        courseType.setName("UEB");
        UUID uuid = UUID.randomUUID();

        // when
        when(courseTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(courseType));

        CourseType actualCourseType = underTest.updateCourseType(uuid, courseTypeReqDTO);

        // then
        assertThat(actualCourseType).isNotNull();
        assertThat(actualCourseType.getName()).isEqualTo("VL");
    }

    @Test
    void deleteCourse() {
        // given
        CourseType courseType = new CourseType();
        courseType.setName("VL");

        UUID uuid = UUID.randomUUID();

        // when
        when(courseTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(courseType));

        doNothing().when(courseTypeRepository).deleteById(any(UUID.class));

        underTest.deleteCourseType(uuid);

        verify(courseTypeRepository, times(1)).deleteById(uuid);
    }
}
