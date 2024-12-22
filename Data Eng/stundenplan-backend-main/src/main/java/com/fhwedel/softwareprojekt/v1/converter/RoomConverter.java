package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.RoomResDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A component responsible for converting between Room-related DTOs and Room entities. */
@Component
@RequiredArgsConstructor
public class RoomConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a RoomReqDTO to a Room entity.
     *
     * @param roomReqDto The RoomReqDTO to convert
     * @return The converted Room entity
     */
    public Room convertDtoToEntity(RoomReqDTO roomReqDto) {
        return modelMapper.map(roomReqDto, Room.class);
    }

    /**
     * Converts a Room entity to a RoomReqDTO.
     *
     * @param room The Room entity to convert
     * @return The converted RoomReqDTO
     */
    public RoomReqDTO convertEntityToDto(Room room) {
        return modelMapper.map(room, RoomReqDTO.class);
    }

    /**
     * Converts a Room entity to a RoomResDTO for response purposes.
     *
     * @param room The Room entity to convert
     * @return The converted RoomResDTO
     */
    public RoomResDTO convertEntityToResponseDTO(Room room) {
        RoomResDTO resDTO = modelMapper.map(room, RoomResDTO.class);
        resDTO.setTimetable(room.getTimetable().getId());
        return resDTO;
    }

    /**
     * Converts a collection of Room entities to a list of RoomResDTOs for response purposes.
     *
     * @param rooms The collection of Room entities to convert
     * @return The list of converted RoomResDTOs
     */
    public List<RoomResDTO> convertEntitiesToResponseDTOList(Collection<Room> rooms) {
        return rooms.stream().map(this::convertEntityToResponseDTO).collect(Collectors.toList());
    }
}
