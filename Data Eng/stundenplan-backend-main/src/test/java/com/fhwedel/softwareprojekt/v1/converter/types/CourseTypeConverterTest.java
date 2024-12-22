package com.fhwedel.softwareprojekt.v1.converter.types;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CourseTypeConverterTest {
    private final CourseTypeConverter courseTypeConverter =
            new CourseTypeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        CourseTypeReqDTO courseTypeReqDTO = CourseTypeReqDTO.builder().name("Vorlesung").build();

        CourseType courseType = courseTypeConverter.convertDtoToEntity(courseTypeReqDTO);

        assertThat(courseType).isNotNull();
        assertThat(courseType.getName()).isEqualTo("Vorlesung");
    }

    @Test
    void convertEntityToResponseDTO() {

        CourseType type = new CourseType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        CourseTypeResDTO convertDTO = courseTypeConverter.convertEntityToResponseDTO(type);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {

        CourseType t1 = new CourseType();
        t1.setId(UUID.randomUUID());
        t1.setName("test");

        CourseType t2 = new CourseType();
        t2.setId(UUID.randomUUID());
        t2.setName("test2");

        List<CourseType> types = new ArrayList<>();
        types.add(t1);
        types.add(t2);

        List<CourseTypeResDTO> convertDTO =
                courseTypeConverter.convertEntitiesToResponseDTOList(types);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
