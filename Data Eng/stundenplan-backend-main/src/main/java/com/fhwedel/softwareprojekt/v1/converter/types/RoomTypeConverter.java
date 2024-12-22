package com.fhwedel.softwareprojekt.v1.converter.types;

import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Converter class for mapping RoomType related DTOs and entities. */
@Component
@RequiredArgsConstructor
public class RoomTypeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a RoomTypeReqDTO to a RoomType entity.
     *
     * @param roomTypeReqDto the RoomTypeReqDTO to be converted
     * @return a RoomType entity
     */
    public RoomType convertRoomTypeDtoToEntity(RoomTypeReqDTO roomTypeReqDto) {
        return modelMapper.map(roomTypeReqDto, RoomType.class);
    }

    /**
     * Converts a RoomType entity to a RoomTypeResDTO.
     *
     * @param room the RoomType entity to be converted
     * @return a RoomTypeResDTO
     */
    public RoomTypeResDTO convertRoomTypeEntityToResponseDTO(RoomType room) {
        return modelMapper.map(room, RoomTypeResDTO.class);
    }

    /**
     * Converts a collection of RoomType entities to a list of RoomTypeResDTOs.
     *
     * @param roomTypes the collection of RoomType entities to be converted
     * @return a list of RoomTypeResDTOs
     */
    public List<RoomTypeResDTO> convertRoomTypeEntitiesToResponseDTOList(
            Collection<RoomType> roomTypes) {
        return roomTypes.stream()
                .map(this::convertRoomTypeEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
