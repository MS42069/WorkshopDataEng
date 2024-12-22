package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.BreaksBetweenConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.BreaksBetweenConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeBreaksBetweenConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A converter class for mapping between different representations of
 * EmployeeBreaksBetweenConstraint objects.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BreaksBetweenConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeBreaksBetweenConstraint entity to a BreaksBetweenConstraintReqDTO.
     *
     * @param constraint The EmployeeBreaksBetweenConstraint entity to convert
     * @return A BreaksBetweenConstraintReqDTO object
     */
    public BreaksBetweenConstraintReqDTO convertToReqDTO(
            EmployeeBreaksBetweenConstraint constraint) {
        return modelMapper.map(constraint, BreaksBetweenConstraintReqDTO.class);
    }

    /**
     * Converts a BreaksBetweenConstraintReqDTO to an EmployeeBreaksBetweenConstraint entity.
     *
     * @param dto The BreaksBetweenConstraintReqDTO to convert
     * @return An EmployeeBreaksBetweenConstraint entity
     */
    public EmployeeBreaksBetweenConstraint convertToEntity(BreaksBetweenConstraintReqDTO dto) {
        return modelMapper.map(dto, EmployeeBreaksBetweenConstraint.class);
    }

    /**
     * Converts an EmployeeBreaksBetweenConstraint entity to a BreaksBetweenConstraintResDTO.
     *
     * @param constraint The EmployeeBreaksBetweenConstraint entity to convert
     * @return A BreaksBetweenConstraintResDTO object
     */
    public BreaksBetweenConstraintResDTO convertToResDTO(
            EmployeeBreaksBetweenConstraint constraint) {
        return modelMapper.map(constraint, BreaksBetweenConstraintResDTO.class);
    }

    /**
     * Converts a BreaksBetweenConstraintResDTO to an EmployeeBreaksBetweenConstraint entity.
     *
     * @param dto The BreaksBetweenConstraintResDTO to convert
     * @return An EmployeeBreaksBetweenConstraint entity
     */
    public EmployeeBreaksBetweenConstraint convertToEntity(BreaksBetweenConstraintResDTO dto) {
        return modelMapper.map(dto, EmployeeBreaksBetweenConstraint.class);
    }
}
