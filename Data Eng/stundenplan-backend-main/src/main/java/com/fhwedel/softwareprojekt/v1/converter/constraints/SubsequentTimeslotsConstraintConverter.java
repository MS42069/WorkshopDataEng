package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.SubsequentTimeslotsConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.SubsequentTimeslotsConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeSubsequentTimeslotsConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Converter class for converting between different representations of SubsequentTimeslotsConstraint
 * objects.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SubsequentTimeslotsConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeSubsequentTimeslotsConstraint entity to a
     * SubsequentTimeslotsConstraintReqDTO.
     *
     * @param constraint The EmployeeSubsequentTimeslotsConstraint entity to be converted
     * @return A SubsequentTimeslotsConstraintReqDTO representing the same data
     */
    public SubsequentTimeslotsConstraintReqDTO convertToReqDTO(
            EmployeeSubsequentTimeslotsConstraint constraint) {
        return modelMapper.map(constraint, SubsequentTimeslotsConstraintReqDTO.class);
    }

    /**
     * Converts a SubsequentTimeslotsConstraintReqDTO to an EmployeeSubsequentTimeslotsConstraint
     * entity.
     *
     * @param dto The SubsequentTimeslotsConstraintReqDTO to be converted
     * @return An EmployeeSubsequentTimeslotsConstraint entity representing the same data
     */
    public EmployeeSubsequentTimeslotsConstraint convertToEntity(
            SubsequentTimeslotsConstraintReqDTO dto) {
        return modelMapper.map(dto, EmployeeSubsequentTimeslotsConstraint.class);
    }

    /**
     * Converts an EmployeeSubsequentTimeslotsConstraint entity to a
     * SubsequentTimeslotsConstraintResDTO.
     *
     * @param constraint The EmployeeSubsequentTimeslotsConstraint entity to be converted
     * @return A SubsequentTimeslotsConstraintResDTO representing the same data
     */
    public SubsequentTimeslotsConstraintResDTO convertToResDTO(
            EmployeeSubsequentTimeslotsConstraint constraint) {
        return modelMapper.map(constraint, SubsequentTimeslotsConstraintResDTO.class);
    }

    /**
     * Converts a SubsequentTimeslotsConstraintResDTO to an EmployeeSubsequentTimeslotsConstraint
     * entity.
     *
     * @param dto The SubsequentTimeslotsConstraintResDTO to be converted
     * @return An EmployeeSubsequentTimeslotsConstraint entity representing the same data
     */
    public EmployeeSubsequentTimeslotsConstraint convertToEntity(
            SubsequentTimeslotsConstraintResDTO dto) {
        return modelMapper.map(dto, EmployeeSubsequentTimeslotsConstraint.class);
    }
}
