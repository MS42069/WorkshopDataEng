package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterResDTO;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping between different representations of DegreeSemester objects. */
@Component
@RequiredArgsConstructor
public class DegreeSemesterConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a DegreeSemesterReqDTO object to a DegreeSemester entity.
     *
     * @param semesterDTO The DegreeSemesterReqDTO to be converted.
     * @return The converted DegreeSemester entity.
     */
    public DegreeSemester convertDtoToEntity(DegreeSemesterReqDTO semesterDTO) {
        return modelMapper.map(semesterDTO, DegreeSemester.class);
    }

    /**
     * Converts a DegreeSemester entity to a DegreeSemesterReqDTO object.
     *
     * @param semester The DegreeSemester entity to be converted.
     * @return The converted DegreeSemesterReqDTO object.
     */
    public DegreeSemesterReqDTO convertEntityToDto(DegreeSemester semester) {
        return modelMapper.map(semester, DegreeSemesterReqDTO.class);
    }

    /**
     * Converts a DegreeSemester entity to a DegreeSemesterResDTO response object.
     *
     * @param semester The DegreeSemester entity to be converted.
     * @return The converted DegreeSemesterResDTO response object.
     */
    public DegreeSemesterResDTO convertEntityToResponseDTO(DegreeSemester semester) {
        DegreeSemesterResDTO resDTO = modelMapper.map(semester, DegreeSemesterResDTO.class);
        resDTO.setTimetable(semester.getTimetable().getId());
        return resDTO;
    }

    /**
     * Converts a collection of DegreeSemester entities to a list of DegreeSemesterResDTO response
     * objects.
     *
     * @param semesters The collection of DegreeSemester entities to be converted.
     * @return The list of converted DegreeSemesterResDTO response objects.
     */
    public List<DegreeSemesterResDTO> convertEntitiesToResponseDTOList(
            Collection<DegreeSemester> semesters) {
        return semesters.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
