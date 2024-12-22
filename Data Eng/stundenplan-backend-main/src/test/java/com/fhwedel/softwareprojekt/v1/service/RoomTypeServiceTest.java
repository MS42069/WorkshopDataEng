package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
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
class RoomTypeServiceTest {

    private final RoomTypeConverter roomTypeConverter = new RoomTypeConverter(new ModelMapper());
    @Mock private RoomTypeRepository roomTypeRepository;

    @InjectMocks private RoomTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RoomTypeService(roomTypeRepository, roomTypeConverter);
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<RoomType> emptyOptional = Optional.empty();
        // when
        when(roomTypeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.types.RoomType' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsRoomType() {
        RoomType roomType = new RoomType();
        roomType.setName("PC Pool");

        Optional<RoomType> optionalRoom = Optional.of(roomType);
        // when
        when(roomTypeRepository.findById(any(UUID.class))).thenReturn(optionalRoom);

        UUID id = UUID.randomUUID();
        // then
        RoomType actualRoomType = underTest.findByID(id);

        assertThat(actualRoomType).isEqualTo(roomType);
    }

    @Test
    void save() {
        // given
        RoomTypeReqDTO roomTypeReqDTO = RoomTypeReqDTO.builder().name("PC Pool").build();

        RoomType roomType = new RoomType();

        roomType.setName("PC Pool");

        // when
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType);

        RoomType actualRoomType = underTest.save(roomTypeReqDTO);

        // then
        assertThat(actualRoomType).isNotNull();
        assertThat(actualRoomType.getName()).isEqualTo("PC Pool");
    }

    @Test
    void updateRoom() {
        // given
        RoomTypeReqDTO roomTypeReqDTO = RoomTypeReqDTO.builder().name("Lecture Hall").build();

        RoomType roomType = new RoomType();
        roomType.setName("PC Pool");
        UUID uuid = UUID.randomUUID();

        // when
        when(roomTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(roomType));

        RoomType actualRoomType = underTest.updateRoomType(uuid, roomTypeReqDTO);

        // then
        assertThat(actualRoomType).isNotNull();
        assertThat(actualRoomType.getName()).isEqualTo("Lecture Hall");
    }

    @Test
    void deleteRoom() {
        // given
        RoomType roomType = new RoomType();
        roomType.setName("PC pool");

        UUID uuid = UUID.randomUUID();

        // when
        when(roomTypeRepository.findById(any(UUID.class))).thenReturn(Optional.of(roomType));

        doNothing().when(roomTypeRepository).deleteById(any(UUID.class));

        underTest.deleteRoomType(uuid);

        verify(roomTypeRepository, times(1)).deleteById(uuid);
    }
}
