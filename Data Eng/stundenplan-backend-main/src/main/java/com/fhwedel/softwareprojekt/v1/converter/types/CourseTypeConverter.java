package com.fhwedel.softwareprojekt.v1.converter.types;

import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping CourseType objects to DTOs and vice versa. */
@Component
@RequiredArgsConstructor
public class CourseTypeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a CourseTypeReqDTO to a CourseType entity.
     *
     * @param courseTypeReqDto the CourseTypeReqDTO to convert
     * @return the converted CourseType entity
     */
    public CourseType convertDtoToEntity(CourseTypeReqDTO courseTypeReqDto) {
        return modelMapper.map(courseTypeReqDto, CourseType.class);
    }

    /**
     * Converts a CourseType entity to a CourseTypeResDTO.
     *
     * @param courseType the CourseType entity to convert
     * @return the converted CourseTypeResDTO
     */
    public CourseTypeResDTO convertEntityToResponseDTO(CourseType courseType) {
        return modelMapper.map(courseType, CourseTypeResDTO.class);
    }

    /**
     * Converts a collection of CourseType entities to a list of CourseTypeResDTOs.
     *
     * @param courseTypes the collection of CourseType entities to convert
     * @return a list of converted CourseTypeResDTOs
     */
    public List<CourseTypeResDTO> convertEntitiesToResponseDTOList(
            Collection<CourseType> courseTypes) {
        return courseTypes.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
