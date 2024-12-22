package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping between different representations of Degree objects. */
@Component
@RequiredArgsConstructor
public class DegreeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a DegreeReqDTO object to a Degree entity.
     *
     * @param degreeDTO The DegreeReqDTO to be converted.
     * @return A Degree entity.
     */
    public Degree convertDtoToEntity(DegreeReqDTO degreeDTO) {
        return modelMapper.map(degreeDTO, Degree.class);
    }

    /**
     * Converts a Degree entity to a DegreeReqDTO object.
     *
     * @param degree The Degree entity to be converted.
     * @return A DegreeReqDTO object.
     */
    public DegreeReqDTO convertEntityToDto(Degree degree) {
        return modelMapper.map(degree, DegreeReqDTO.class);
    }

    /**
     * Converts a Degree entity to a DegreeResDTO object.
     *
     * @param degree The Degree entity to be converted.
     * @return A DegreeResDTO object.
     */
    public DegreeResDTO convertEntityToResponseDTO(Degree degree) {
        DegreeResDTO resDTO = modelMapper.map(degree, DegreeResDTO.class);
        resDTO.setTimetable(degree.getTimetable().getId());
        return resDTO;
    }

    /**
     * Converts a collection of Degree entities to a list of DegreeResDTO objects.
     *
     * @param degrees A collection of Degree entities.
     * @return A list of DegreeResDTO objects.
     */
    public List<DegreeResDTO> convertEntitiesToResponseDTOList(Collection<Degree> degrees) {
        return degrees.stream().map(this::convertEntityToResponseDTO).collect(Collectors.toList());
    }
}
