package com.fhwedel.softwareprojekt.v1.converter.types;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class SchoolTypeConverterTest {
    private final SchoolTypeConverter schoolTypeConverter =
            new SchoolTypeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        SchoolTypeReqDTO schoolTypeReqDTO = SchoolTypeReqDTO.builder().name("Uni").build();

        SchoolType schoolType = schoolTypeConverter.convertDtoToEntity(schoolTypeReqDTO);

        assertThat(schoolType).isNotNull();
        assertThat(schoolType.getName()).isEqualTo("Uni");
    }

    @Test
    void convertEntityToResponseDTO() {

        SchoolType type = new SchoolType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        SchoolTypeResDTO convertDTO = schoolTypeConverter.convertEntityToResponseDTO(type);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {

        SchoolType t1 = new SchoolType();
        t1.setId(UUID.randomUUID());
        t1.setName("test");

        SchoolType t2 = new SchoolType();
        t2.setId(UUID.randomUUID());
        t2.setName("test2");

        List<SchoolType> types = new ArrayList<>();
        types.add(t1);
        types.add(t2);

        List<SchoolTypeResDTO> convertDTO =
                schoolTypeConverter.convertEntitiesToResponseDTOList(types);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
