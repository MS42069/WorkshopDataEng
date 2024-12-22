package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for converting between Timetable entities and Timetable DTOs. */
@Component
@RequiredArgsConstructor
@Slf4j
public class TimetableConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a TimetableReqDTO to a Timetable entity.
     *
     * @param timetableReqDTO The TimetableReqDTO to convert
     * @return The converted Timetable entity
     */
    public Timetable convertDtoToEntity(TimetableReqDTO timetableReqDTO) {
        Timetable timetable = modelMapper.map(timetableReqDTO, Timetable.class);
        log.debug("Converted timetableDTO {} to entity {}", timetableReqDTO, timetable);
        return timetable;
    }

    /**
     * Converts a Timetable entity to a TimetableResDTO.
     *
     * @param timetable The Timetable entity to convert
     * @return The converted TimetableResDTO
     */
    public TimetableResDTO convertEntityToResponseDTO(Timetable timetable) {
        return modelMapper.map(timetable, TimetableResDTO.class);
    }

    /**
     * Converts a collection of Timetable entities to a list of TimetableResDTOs.
     *
     * @param timetables The collection of Timetable entities to convert
     * @return A list of converted TimetableResDTOs
     */
    public List<TimetableResDTO> convertEntitiesToResponseDTOList(
            Collection<Timetable> timetables) {
        return timetables.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
