package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.LunchBreakConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.LunchBreakConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.LunchBreakConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeLunchBreakConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeLunchBreakConstraintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/** Service class for managing LunchBreakConstraint operations. */
@Service
@RequiredArgsConstructor
public class LunchBreakConstraintService {
    /** Repository for EmployeeLunchBreakConstraint entities. */
    private final EmployeeLunchBreakConstraintRepository employeeLunchBreakConstraintRepository;

    /** Converter for LunchBreakConstraint entities and DTOs. */
    private final LunchBreakConstraintConverter lunchBreakConstraintConverter;

    /** ModelMapper for mapping between DTOs and entities. */
    private final ModelMapper modelMapper;

    /**
     * Handles the saving of LunchBreakConstraint entities.
     *
     * @param dto The DTO containing LunchBreakConstraint information.
     */
    public void handleSave(LunchBreakConstraintReqDTO dto) {
        if (dto != null && dto.getEmployeeAbbreviation() != null) {
            if (dto.getConstraintValue() != null) {
                saveOrUpdate(dto);
            } else {
                delete(dto);
            }
        }
    }

    /**
     * Saves or updates a LunchBreakConstraint entity based on the DTO.
     *
     * @param dto The DTO containing LunchBreakConstraint information.
     */
    private void saveOrUpdate(LunchBreakConstraintReqDTO dto) {

        EmployeeLunchBreakConstraint constraint =
                employeeLunchBreakConstraintRepository
                        .findByEmployeeAbbreviation(dto.getEmployeeAbbreviation())
                        .orElseGet(EmployeeLunchBreakConstraint::new);

        modelMapper.map(dto, constraint);

        employeeLunchBreakConstraintRepository.save(constraint);
    }

    /**
     * Deletes a LunchBreakConstraint entity based on the DTO.
     *
     * @param dto The DTO containing LunchBreakConstraint information.
     */
    private void delete(LunchBreakConstraintReqDTO dto) {
        Optional<EmployeeLunchBreakConstraint> constraintOptional =
                employeeLunchBreakConstraintRepository.findByEmployeeAbbreviation(
                        dto.getEmployeeAbbreviation());
        constraintOptional.ifPresent(employeeLunchBreakConstraintRepository::delete);
    }

    /**
     * Retrieves a LunchBreakConstraintResDTO based on the employee's abbreviation.
     *
     * @param abbreviation The abbreviation of the employee.
     * @return A LunchBreakConstraintResDTO containing constraint information.
     */
    public LunchBreakConstraintResDTO getResDTO(String abbreviation) {
        return employeeLunchBreakConstraintRepository
                .findByEmployeeAbbreviation(abbreviation)
                .map(lunchBreakConstraintConverter::convertToResDTO)
                .orElseGet(LunchBreakConstraintResDTO::new);
    }
}
