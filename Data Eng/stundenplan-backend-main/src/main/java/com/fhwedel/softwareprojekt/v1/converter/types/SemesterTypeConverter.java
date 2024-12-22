package com.fhwedel.softwareprojekt.v1.converter.types;

import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Converter class for mapping between {@link SemesterType} entities and DTOs. */
@Component
@RequiredArgsConstructor
public class SemesterTypeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a {@link SemesterTypeReqDTO} object to a {@link SemesterType} entity.
     *
     * @param semesterTypeReqDTO The DTO to be converted.
     * @return The corresponding entity.
     */
    public SemesterType convertSemesterTypeDtoToEntity(SemesterTypeReqDTO semesterTypeReqDTO) {
        return modelMapper.map(semesterTypeReqDTO, SemesterType.class);
    }

    /**
     * Converts a {@link SemesterType} entity to a {@link SemesterTypeResDTO} response DTO.
     *
     * @param semesterType The entity to be converted.
     * @return The corresponding response DTO.
     */
    public SemesterTypeResDTO convertSemesterTypeEntityToResponseDTO(SemesterType semesterType) {
        return modelMapper.map(semesterType, SemesterTypeResDTO.class);
    }

    /**
     * Converts a collection of {@link SemesterType} entities to a list of {@link
     * SemesterTypeResDTO} response DTOs.
     *
     * @param semesterTypes The collection of entities to be converted.
     * @return The list of corresponding response DTOs.
     */
    public List<SemesterTypeResDTO> convertSemesterTypeEntitiesToResponseDTOList(
            Collection<SemesterType> semesterTypes) {
        return semesterTypes.stream()
                .map(this::convertSemesterTypeEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
