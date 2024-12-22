package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.EmployeeTypeService;
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
public class EmployeeTypeServiceTest {
    private final EmployeeTypeConverter employeeTypeConverter =
            new EmployeeTypeConverter(new ModelMapper());
    @Mock private EmployeeTypeRepository employeeTypeRepository;
    @InjectMocks private EmployeeTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeTypeService(employeeTypeRepository, employeeTypeConverter);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<EmployeeType> emptyOptional = Optional.empty();
        // when
        when(employeeTypeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.types.EmployeeType' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsEmployeeType() {
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Assistent");

        Optional<EmployeeType> optionalType = Optional.of(employeeType);
        // when
        when(employeeTypeRepository.findById(any(UUID.class))).thenReturn(optionalType);

        UUID id = UUID.randomUUID();
        // then
        EmployeeType actual = underTest.findByID(id);

        assertThat(actual).isEqualTo(employeeType);
    }

    @Test
    void save() {
        // given
        EmployeeTypeReqDTO employeeTypeReqDTO =
                EmployeeTypeReqDTO.builder().name("Assistent").build();

        EmployeeType employeeType = new EmployeeType();

        employeeType.setName("Assistent");

        // when
        when(employeeTypeRepository.save(any(EmployeeType.class))).thenReturn(employeeType);

        EmployeeType actual = underTest.save(employeeTypeReqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Assistent");
    }

    @Test
    void update() {
        // given
        EmployeeTypeReqDTO employeeTypeReqDTO = EmployeeTypeReqDTO.builder().name("Test").build();

        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Test");
        UUID uuid = UUID.randomUUID();

        // when
        when(employeeTypeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(employeeType));

        EmployeeType actual = underTest.updateEmployeeType(uuid, employeeTypeReqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Test");
    }

    @Test
    void delete() {
        // given
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("Test");

        UUID uuid = UUID.randomUUID();

        // when
        when(employeeTypeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(employeeType));

        doNothing().when(employeeTypeRepository).deleteById(any(UUID.class));

        underTest.deleteEmployeeType(uuid);

        verify(employeeTypeRepository, times(1)).deleteById(uuid);
    }
}
