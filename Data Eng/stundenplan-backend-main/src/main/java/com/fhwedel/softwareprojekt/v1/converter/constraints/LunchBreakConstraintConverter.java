package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.LunchBreakConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.LunchBreakConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeLunchBreakConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A converter class for transforming between LunchBreakConstraintReqDTO,
 * LunchBreakConstraintResDTO, and EmployeeLunchBreakConstraint entities.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LunchBreakConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeLunchBreakConstraint entity to a LunchBreakConstraintReqDTO.
     *
     * @param constraint The EmployeeLunchBreakConstraint entity to convert
     * @return A LunchBreakConstraintReqDTO
     */
    public LunchBreakConstraintReqDTO convertToReqDTO(EmployeeLunchBreakConstraint constraint) {
        return modelMapper.map(constraint, LunchBreakConstraintReqDTO.class);
    }

    /**
     * Converts a LunchBreakConstraintReqDTO to an EmployeeLunchBreakConstraint entity.
     *
     * @param dto The LunchBreakConstraintReqDTO to convert
     * @return An EmployeeLunchBreakConstraint entity
     */
    public EmployeeLunchBreakConstraint convertToEntity(LunchBreakConstraintReqDTO dto) {
        return modelMapper.map(dto, EmployeeLunchBreakConstraint.class);
    }

    /**
     * Converts an EmployeeLunchBreakConstraint entity to a LunchBreakConstraintResDTO.
     *
     * @param constraint The EmployeeLunchBreakConstraint entity to convert
     * @return A LunchBreakConstraintResDTO
     */
    public LunchBreakConstraintResDTO convertToResDTO(EmployeeLunchBreakConstraint constraint) {
        return modelMapper.map(constraint, LunchBreakConstraintResDTO.class);
    }

    /**
     * Converts a LunchBreakConstraintResDTO to an EmployeeLunchBreakConstraint entity.
     *
     * @param dto The LunchBreakConstraintResDTO to convert
     * @return An EmployeeLunchBreakConstraint entity
     */
    public EmployeeLunchBreakConstraint convertToEntity(LunchBreakConstraintResDTO dto) {
        return modelMapper.map(dto, EmployeeLunchBreakConstraint.class);
    }
}
