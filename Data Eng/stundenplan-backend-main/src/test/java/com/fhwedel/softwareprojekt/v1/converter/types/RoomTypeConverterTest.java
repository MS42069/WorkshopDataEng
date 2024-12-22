package com.fhwedel.softwareprojekt.v1.converter.types;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class RoomTypeConverterTest {
    private final RoomTypeConverter roomTypeConverter = new RoomTypeConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        RoomTypeReqDTO roomTypeReqDTO = RoomTypeReqDTO.builder().name("Seminarraum").build();

        RoomType roomType = roomTypeConverter.convertRoomTypeDtoToEntity(roomTypeReqDTO);

        assertThat(roomType).isNotNull();
        assertThat(roomType.getName()).isEqualTo("Seminarraum");
    }

    @Test
    void convertEntityToResponseDTO() {

        RoomType type = new RoomType();
        type.setId(UUID.randomUUID());
        type.setName("test");

        RoomTypeResDTO convertDTO = roomTypeConverter.convertRoomTypeEntityToResponseDTO(type);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {

        RoomType t1 = new RoomType();
        t1.setId(UUID.randomUUID());
        t1.setName("test");

        RoomType t2 = new RoomType();
        t2.setId(UUID.randomUUID());
        t2.setName("test2");

        List<RoomType> types = new ArrayList<>();
        types.add(t1);
        types.add(t2);

        List<RoomTypeResDTO> convertDTO =
                roomTypeConverter.convertRoomTypeEntitiesToResponseDTOList(types);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
