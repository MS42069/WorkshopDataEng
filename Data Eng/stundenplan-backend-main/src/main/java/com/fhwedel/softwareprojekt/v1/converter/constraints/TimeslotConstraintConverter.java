package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Converter class for mapping between EmployeeTimeslotConstraint entities and their corresponding
 * DTOs.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TimeslotConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeTimeslotConstraint entity to its corresponding DTO.
     *
     * @param constraint The EmployeeTimeslotConstraint entity to convert
     * @return The corresponding EmployeeTimeslotConstraintResDTO
     */
    public EmployeeTimeslotConstraintResDTO convertToDTO(EmployeeTimeslotConstraint constraint) {
        return modelMapper.map(constraint, EmployeeTimeslotConstraintResDTO.class);
    }

    /**
     * Converts an EmployeeTimeslotConstraintResDTO to its corresponding entity.
     *
     * @param constraint The EmployeeTimeslotConstraintResDTO to convert
     * @return The corresponding EmployeeTimeslotConstraint entity
     */
    public EmployeeTimeslotConstraint convertToEntity(EmployeeTimeslotConstraintResDTO constraint) {
        return modelMapper.map(constraint, EmployeeTimeslotConstraint.class);
    }

    /**
     * Converts an EmployeeTimeslotConstraintReqDTO to its corresponding entity.
     *
     * @param constraint The EmployeeTimeslotConstraintReqDTO to convert
     * @return The corresponding EmployeeTimeslotConstraint entity
     */
    public EmployeeTimeslotConstraint convertToEntity(EmployeeTimeslotConstraintReqDTO constraint) {
        return modelMapper.map(constraint, EmployeeTimeslotConstraint.class);
    }

    /**
     * Converts a list of EmployeeTimeslotConstraint entities to a list of corresponding DTOs.
     *
     * @param constraints The list of EmployeeTimeslotConstraint entities to convert
     * @return A list of corresponding EmployeeTimeslotConstraintResDTOs
     */
    public List<EmployeeTimeslotConstraintResDTO> convertToDTOList(
            List<EmployeeTimeslotConstraint> constraints) {
        return constraints.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
