package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.FreeDayConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.FreeDayConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeFreeDayConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A converter class for mapping between different representations of {@link
 * EmployeeFreeDayConstraint}.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FreeDayConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an {@link EmployeeFreeDayConstraint} entity to a {@link FreeDayConstraintReqDTO}.
     *
     * @param constraint The entity to be converted
     * @return The corresponding {@link FreeDayConstraintReqDTO}
     */
    public FreeDayConstraintReqDTO convertToReqDTO(EmployeeFreeDayConstraint constraint) {
        return modelMapper.map(constraint, FreeDayConstraintReqDTO.class);
    }

    /**
     * Converts a {@link FreeDayConstraintReqDTO} to an {@link EmployeeFreeDayConstraint} entity.
     *
     * @param dto The DTO to be converted
     * @return The corresponding {@link EmployeeFreeDayConstraint} entity
     */
    public EmployeeFreeDayConstraint convertToEntity(FreeDayConstraintReqDTO dto) {
        return modelMapper.map(dto, EmployeeFreeDayConstraint.class);
    }

    /**
     * Converts an {@link EmployeeFreeDayConstraint} entity to a {@link FreeDayConstraintResDTO}.
     *
     * @param constraint The entity to be converted
     * @return The corresponding {@link FreeDayConstraintResDTO}
     */
    public FreeDayConstraintResDTO convertToResDTO(EmployeeFreeDayConstraint constraint) {
        return modelMapper.map(constraint, FreeDayConstraintResDTO.class);
    }

    /**
     * Converts a {@link FreeDayConstraintResDTO} to an {@link EmployeeFreeDayConstraint} entity.
     *
     * @param dto The DTO to be converted
     * @return The corresponding {@link EmployeeFreeDayConstraint} entity
     */
    public EmployeeFreeDayConstraint convertToEntity(FreeDayConstraintResDTO dto) {
        return modelMapper.map(dto, EmployeeFreeDayConstraint.class);
    }
}
