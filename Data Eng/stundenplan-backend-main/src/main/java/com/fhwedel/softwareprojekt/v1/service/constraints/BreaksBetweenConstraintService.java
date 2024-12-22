package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.BreaksBetweenConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.BreaksBetweenConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.BreaksBetweenConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeBreaksBetweenConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeBreaksBetweenConstraintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/** Service class for managing Breaks Between Constraints related operations. */
@Service
@RequiredArgsConstructor
public class BreaksBetweenConstraintService {

    /** Repository for managing EmployeeBreaksBetweenConstraint entities. */
    private final EmployeeBreaksBetweenConstraintRepository
            employeeBreaksBetweenConstraintRepository;

    /** Converter for mapping between BreaksBetweenConstraint DTOs and entities. */
    private final BreaksBetweenConstraintConverter breaksBetweenConstraintConverter;

    /** ModelMapper for mapping between DTOs and entities. */
    private final ModelMapper modelMapper;

    /**
     * Handles the save operation for a Breaks Between Constraint.
     *
     * @param dto The BreaksBetweenConstraintReqDTO object containing constraint details
     */
    public void handleSave(BreaksBetweenConstraintReqDTO dto) {
        if (dto != null && dto.getEmployeeAbbreviation() != null) {
            if (dto.getConstraintValue() != null) {
                saveOrUpdate(dto);
            } else {
                delete(dto);
            }
        }
    }

    /**
     * Saves or updates a Breaks Between Constraint based on the provided DTO.
     *
     * @param dto The BreaksBetweenConstraintReqDTO object containing constraint details
     */
    private void saveOrUpdate(BreaksBetweenConstraintReqDTO dto) {
        EmployeeBreaksBetweenConstraint constraint =
                employeeBreaksBetweenConstraintRepository
                        .findByEmployeeAbbreviation(dto.getEmployeeAbbreviation())
                        .orElseGet(EmployeeBreaksBetweenConstraint::new);

        modelMapper.map(dto, constraint);

        employeeBreaksBetweenConstraintRepository.save(constraint);
    }

    /**
     * Deletes a Breaks Between Constraint based on the provided DTO.
     *
     * @param dto The BreaksBetweenConstraintReqDTO object containing constraint details
     */
    private void delete(BreaksBetweenConstraintReqDTO dto) {
        Optional<EmployeeBreaksBetweenConstraint> constraint =
                employeeBreaksBetweenConstraintRepository.findByEmployeeAbbreviation(
                        dto.getEmployeeAbbreviation());
        constraint.ifPresent(employeeBreaksBetweenConstraintRepository::delete);
    }

    /**
     * Retrieves a BreaksBetweenConstraintResDTO object by employee abbreviation.
     *
     * @param abbreviation The abbreviation of the employee
     * @return A BreaksBetweenConstraintResDTO object representing the constraint or an empty one if
     *     not found
     */
    public BreaksBetweenConstraintResDTO getResDTO(String abbreviation) {
        return employeeBreaksBetweenConstraintRepository
                .findByEmployeeAbbreviation(abbreviation)
                .map(breaksBetweenConstraintConverter::convertToResDTO)
                .orElseGet(BreaksBetweenConstraintResDTO::new);
    }
}
