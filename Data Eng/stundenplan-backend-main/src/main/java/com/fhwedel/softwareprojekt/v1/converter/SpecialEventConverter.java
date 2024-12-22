package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.SpecialEventResDTO;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A component responsible for converting between SpecialEvent entities and DTOs. */
@Component
@RequiredArgsConstructor
public class SpecialEventConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a SpecialEventReqDTO to a SpecialEvent entity.
     *
     * @param specialEventReqDTO The SpecialEventReqDTO to convert.
     * @return The converted SpecialEvent entity.
     */
    public SpecialEvent convertDtoToEntity(SpecialEventReqDTO specialEventReqDTO) {
        return modelMapper.map(specialEventReqDTO, SpecialEvent.class);
    }

    /**
     * Converts a SpecialEvent entity to a SpecialEventResDTO.
     *
     * @param specialEvent The SpecialEvent entity to convert.
     * @return The converted SpecialEventResDTO.
     */
    public SpecialEventResDTO convertEntityToResponseDTO(SpecialEvent specialEvent) {
        return modelMapper.map(specialEvent, SpecialEventResDTO.class);
    }

    /**
     * Converts a collection of SpecialEvent entities to a list of SpecialEventResDTOs.
     *
     * @param specialEvents The collection of SpecialEvent entities to convert.
     * @return A list of converted SpecialEventResDTOs.
     */
    public List<SpecialEventResDTO> convertEntitiesToResponseDTOList(
            Collection<SpecialEvent> specialEvents) {
        return specialEvents.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
