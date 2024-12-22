package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.types.SchoolTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import com.fhwedel.softwareprojekt.v1.repository.types.SchoolTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.SchoolTypeService;
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
public class SchoolTypeServiceTest {
    private final SchoolTypeConverter schoolTypeConverter =
            new SchoolTypeConverter(new ModelMapper());
    @Mock private SchoolTypeRepository schoolTypeRepository;
    @InjectMocks private SchoolTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SchoolTypeService(schoolTypeRepository, schoolTypeConverter);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<SchoolType> emptyOptional = Optional.empty();
        // when
        when(schoolTypeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.types.SchoolType' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsSchoolType() {
        SchoolType schoolType = new SchoolType();
        schoolType.setName("UNI");

        Optional<SchoolType> optionalSchool = Optional.of(schoolType);
        // when
        when(schoolTypeRepository.findById(any(UUID.class))).thenReturn(optionalSchool);

        UUID id = UUID.randomUUID();
        // then
        SchoolType actualSchoolType = underTest.findByID(id);

        assertThat(actualSchoolType).isEqualTo(schoolType);
    }

    @Test
    void save() {
        // given
        SchoolTypeReqDTO schoolTypeReqDTO = SchoolTypeReqDTO.builder().name("UNI").build();

        SchoolType schoolType = new SchoolType();

        schoolType.setName("UNI");

        // when
        when(schoolTypeRepository.save(any(SchoolType.class))).thenReturn(schoolType);

        SchoolType actualSchoolType = underTest.save(schoolTypeReqDTO);

        // then
        assertThat(actualSchoolType).isNotNull();
        assertThat(actualSchoolType.getName()).isEqualTo("UNI");
    }

    @Test
    void updateSchool() {
        // given
        SchoolTypeReqDTO schoolTypeReqDTO = SchoolTypeReqDTO.builder().name("UNI").build();

        SchoolType schoolType = new SchoolType();
        schoolType.setName("SCHULE");
        UUID uuid = UUID.randomUUID();

        // when
        when(schoolTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(schoolType));

        SchoolType actualSchoolType = underTest.updateSchoolType(uuid, schoolTypeReqDTO);

        // then
        assertThat(actualSchoolType).isNotNull();
        assertThat(actualSchoolType.getName()).isEqualTo("UNI");
    }

    @Test
    void deleteSchool() {
        // given
        SchoolType schoolType = new SchoolType();
        schoolType.setName("uni");

        UUID uuid = UUID.randomUUID();

        // when
        when(schoolTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(schoolType));

        doNothing().when(schoolTypeRepository).deleteById(any(UUID.class));

        underTest.deleteSchoolType(uuid);

        verify(schoolTypeRepository, times(1)).deleteById(uuid);
    }
}
