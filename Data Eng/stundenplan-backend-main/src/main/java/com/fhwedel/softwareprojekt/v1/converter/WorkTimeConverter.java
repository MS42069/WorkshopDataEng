package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeResponseDTO;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for converting between WorkTime entities and DTOs. */
@Component
@RequiredArgsConstructor
public class WorkTimeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a WorkTimeDTO to a WorkTime entity.
     *
     * @param workTimeDTO The WorkTimeDTO to convert
     * @return The corresponding WorkTime entity
     */
    public WorkTime convertDtoToEntity(WorkTimeDTO workTimeDTO) {
        return modelMapper.map(workTimeDTO, WorkTime.class);
    }

    /**
     * Converts a WorkTime entity to a WorkTimeResponseDTO.
     *
     * @param workTime The WorkTime entity to convert
     * @return The corresponding WorkTimeResponseDTO
     */
    public WorkTimeResponseDTO convertEntityToResponseDTO(WorkTime workTime) {
        return modelMapper.map(workTime, WorkTimeResponseDTO.class);
    }

    /**
     * Converts a collection of WorkTime entities to a list of WorkTimeResponseDTOs.
     *
     * @param workTimes The collection of WorkTime entities to convert
     * @return A list of corresponding WorkTimeResponseDTOs
     */
    public List<WorkTimeResponseDTO> convertEntitiesToResponseDTOList(
            Collection<WorkTime> workTimes) {
        return workTimes.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
