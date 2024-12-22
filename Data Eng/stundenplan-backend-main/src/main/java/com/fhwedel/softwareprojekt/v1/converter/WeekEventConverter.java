package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventResDTO;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping between different representations of WeekEvent objects. */
@Component
@RequiredArgsConstructor
@Slf4j
public class WeekEventConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a WeekEvent entity to a WeekEventResDTO.
     *
     * @param event The WeekEvent entity to be converted.
     * @return The converted WeekEventResDTO.
     */
    public WeekEventResDTO convertEntityToResDTO(WeekEvent event) {
        return modelMapper.map(event, WeekEventResDTO.class);
    }

    /**
     * Converts a collection of WeekEvent entities to a list of WeekEventResDTOs.
     *
     * @param events The collection of WeekEvent entities to be converted.
     * @return A list of converted WeekEventResDTOs.
     */
    public List<WeekEventResDTO> convertEntitiesToResDTOList(Collection<WeekEvent> events) {
        return events.stream().map(this::convertEntityToResDTO).collect(Collectors.toList());
    }
}
