package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping between different representations of Timeslot objects. */
@Component
@RequiredArgsConstructor
public class TimeslotConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a TimeslotReqDTO to a Timeslot entity.
     *
     * @param timeslotReqDTO The TimeslotReqDTO to convert
     * @return The corresponding Timeslot entity
     */
    public Timeslot convertDtoToEntity(TimeslotReqDTO timeslotReqDTO) {
        return modelMapper.map(timeslotReqDTO, Timeslot.class);
    }

    /**
     * Converts a Timeslot entity to a TimeslotReqDTO.
     *
     * @param timeslot The Timeslot entity to convert
     * @return The corresponding TimeslotReqDTO
     */
    public TimeslotReqDTO convertEntityToDto(Timeslot timeslot) {
        return modelMapper.map(timeslot, TimeslotReqDTO.class);
    }

    /**
     * Converts a Timeslot entity to a TimeslotResDTO.
     *
     * @param timeslot The Timeslot entity to convert
     * @return The corresponding TimeslotResDTO
     */
    public TimeslotResDTO convertEntityToResponseDTO(Timeslot timeslot) {
        TimeslotResDTO resDTO = modelMapper.map(timeslot, TimeslotResDTO.class);
        resDTO.setTimetable(timeslot.getTimetable().getId());
        return resDTO;
    }

    /**
     * Converts a collection of Timeslot entities to a list of TimeslotResDTOs.
     *
     * @param timeslots The collection of Timeslot entities to convert
     * @return A list of corresponding TimeslotResDTOs
     */
    public List<TimeslotResDTO> convertEntitiesToResponseDTOList(Collection<Timeslot> timeslots) {
        return timeslots.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
