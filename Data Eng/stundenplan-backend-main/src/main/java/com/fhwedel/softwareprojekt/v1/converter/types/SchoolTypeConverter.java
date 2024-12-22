package com.fhwedel.softwareprojekt.v1.converter.types;

import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A component class for converting between SchoolType entities and SchoolType DTOs. */
@Component
@RequiredArgsConstructor
public class SchoolTypeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a SchoolTypeReqDTO object to a SchoolType entity.
     *
     * @param schoolTypeReqDto the SchoolTypeReqDTO to be converted
     * @return the corresponding SchoolType entity
     */
    public SchoolType convertDtoToEntity(SchoolTypeReqDTO schoolTypeReqDto) {
        return modelMapper.map(schoolTypeReqDto, SchoolType.class);
    }

    /**
     * Converts a SchoolType entity to a SchoolTypeResDTO.
     *
     * @param schoolType the SchoolType entity to be converted
     * @return the corresponding SchoolTypeResDTO
     */
    public SchoolTypeResDTO convertEntityToResponseDTO(SchoolType schoolType) {
        return modelMapper.map(schoolType, SchoolTypeResDTO.class);
    }

    /**
     * Converts a collection of SchoolType entities to a list of SchoolTypeResDTOs.
     *
     * @param schoolTypes the collection of SchoolType entities to be converted
     * @return a list of corresponding SchoolTypeResDTOs
     */
    public List<SchoolTypeResDTO> convertEntitiesToResponseDTOList(
            Collection<SchoolType> schoolTypes) {
        return schoolTypes.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
