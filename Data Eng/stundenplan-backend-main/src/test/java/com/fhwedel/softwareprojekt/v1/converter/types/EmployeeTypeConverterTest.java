package com.fhwedel.softwareprojekt.v1.converter.types;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class EmployeeTypeConverterTest {

    private final EmployeeTypeConverter employeeTypeConverter =
            new EmployeeTypeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        EmployeeTypeReqDTO employeeTypeReqDTO =
                EmployeeTypeReqDTO.builder().name("Assistent").build();

        EmployeeType employeeType =
                employeeTypeConverter.convertEmployeeTypeDtoToEntity(employeeTypeReqDTO);

        assertThat(employeeType).isNotNull();
        assertThat(employeeType.getName()).isEqualTo("Assistent");
    }

    @Test
    void convertEntityToResponseDTO() {

        EmployeeType type = new EmployeeType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        EmployeeTypeResDTO convertDTO =
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(type);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {

        EmployeeType type = new EmployeeType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        EmployeeType type2 = new EmployeeType();
        type2.setId(UUID.randomUUID());
        type2.setName("test2");

        List<EmployeeType> types = new ArrayList<>();
        types.add(type);
        types.add(type2);

        List<EmployeeTypeResDTO> convertDTO =
                employeeTypeConverter.convertEmployeeTypeEntitiesToResponseDTOList(types);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
