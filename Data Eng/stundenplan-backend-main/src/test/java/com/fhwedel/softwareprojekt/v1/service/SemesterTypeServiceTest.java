package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.types.SemesterTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
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
public class SemesterTypeServiceTest {

    private final SemesterTypeConverter semesterTypeConverter =
            new SemesterTypeConverter(new ModelMapper());
    @Mock private SemesterTypeRepository semesterTypeRepository;

    @InjectMocks private SemesterTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SemesterTypeService(semesterTypeRepository, semesterTypeConverter);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<SemesterType> emptyOptional = Optional.empty();
        // when
        when(semesterTypeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.types.SemesterType' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsSemesterType() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("Wintersemester");

        Optional<SemesterType> optionalSemesterType = Optional.of(semesterType);
        // when
        when(semesterTypeRepository.findById(any(UUID.class))).thenReturn(optionalSemesterType);

        UUID id = UUID.randomUUID();
        // then
        SemesterType actualSemesterType = underTest.findByID(id);

        assertThat(actualSemesterType).isEqualTo(semesterType);
    }

    @Test
    void save() {
        // given
        SemesterTypeReqDTO semesterTypeReqDTO =
                SemesterTypeReqDTO.builder().name("Wintersemester").build();

        SemesterType semesterType = new SemesterType();

        semesterType.setName("Wintersemester");

        // when
        when(semesterTypeRepository.save(any(SemesterType.class))).thenReturn(semesterType);

        SemesterType actual = underTest.save(semesterTypeReqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Wintersemester");
    }

    @Test
    void update() {
        // given
        SemesterTypeReqDTO semesterTypeReqDTO =
                SemesterTypeReqDTO.builder().name("Wintersemester").build();

        SemesterType semesterType = new SemesterType();
        semesterType.setName("Wintersemester");
        UUID uuid = UUID.randomUUID();

        // when
        when(semesterTypeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(semesterType));

        SemesterType actual = underTest.updateSemesterType(uuid, semesterTypeReqDTO);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Wintersemester");
    }

    @Test
    void delete() {
        // given
        SemesterType semesterType = new SemesterType();
        semesterType.setName("Wintersemester");

        UUID uuid = UUID.randomUUID();

        // when
        when(semesterTypeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(semesterType));

        doNothing().when(semesterTypeRepository).deleteById(any(UUID.class));

        underTest.deleteSemesterType(uuid);

        verify(semesterTypeRepository, times(1)).deleteById(uuid);
    }
}
