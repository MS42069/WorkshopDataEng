package com.fhwedel.softwareprojekt.v1.converter.types;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class SemesterTypeConverterTest {

    private final SemesterTypeConverter semesterTypeConverter =
            new SemesterTypeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        SemesterTypeReqDTO semesterTypeReqDTO =
                SemesterTypeReqDTO.builder().name("Wintersemester").build();

        SemesterType semesterType =
                semesterTypeConverter.convertSemesterTypeDtoToEntity(semesterTypeReqDTO);

        assertThat(semesterType).isNotNull();
        assertThat(semesterType.getName()).isEqualTo("Wintersemester");
    }

    @Test
    void convertEntityToResponseDTO() {

        SemesterType type = new SemesterType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        SemesterTypeResDTO convertDTO =
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(type);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {

        SemesterType type = new SemesterType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        SemesterType type2 = new SemesterType();
        type.setId(UUID.randomUUID());
        type.setName("test2");

        List<SemesterType> types = new ArrayList<>();
        types.add(type);
        types.add(type2);

        List<SemesterTypeResDTO> convertDTO =
                semesterTypeConverter.convertSemesterTypeEntitiesToResponseDTOList(types);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
