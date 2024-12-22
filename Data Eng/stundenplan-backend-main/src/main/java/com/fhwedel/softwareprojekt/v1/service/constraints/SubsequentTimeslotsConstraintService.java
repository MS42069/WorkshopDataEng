package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.SubsequentTimeslotsConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.SubsequentTimeslotsConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.SubsequentTimeslotsConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeSubsequentTimeslotsConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeSubsequentTimeslotsConstraintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/** Service class for managing SubsequentTimeslotsConstraint operations. */
@Service
@RequiredArgsConstructor
public class SubsequentTimeslotsConstraintService {

    /** Repository for EmployeeSubsequentTimeslotsConstraint entities. */
    private final EmployeeSubsequentTimeslotsConstraintRepository
            employeeSubsequentTimeslotsConstraintRepository;

    /** Converter for SubsequentTimeslotsConstraint entities and DTOs. */
    private final SubsequentTimeslotsConstraintConverter subsequentTimeslotsConstraintConverter;

    /** ModelMapper for mapping between DTOs and entities. */
    private final ModelMapper modelMapper;

    /**
     * Handles the saving of SubsequentTimeslotsConstraint entities.
     *
     * @param dto The DTO containing SubsequentTimeslotsConstraint information.
     */
    public void handleSave(SubsequentTimeslotsConstraintReqDTO dto) {
        if (dto != null && dto.getEmployeeAbbreviation() != null) {
            if (dto.getConstraintValue() != null) {
                saveOrUpdate(dto);
            } else {
                delete(dto);
            }
        }
    }

    /**
     * Saves or updates a SubsequentTimeslotsConstraint entity based on the DTO.
     *
     * @param dto The DTO containing SubsequentTimeslotsConstraint information.
     */
    private void saveOrUpdate(SubsequentTimeslotsConstraintReqDTO dto) {
        EmployeeSubsequentTimeslotsConstraint constraint =
                employeeSubsequentTimeslotsConstraintRepository
                        .findByEmployeeAbbreviation(dto.getEmployeeAbbreviation())
                        .orElseGet(EmployeeSubsequentTimeslotsConstraint::new);

        modelMapper.map(dto, constraint);

        employeeSubsequentTimeslotsConstraintRepository.save(constraint);
    }

    /**
     * Deletes a SubsequentTimeslotsConstraint entity based on the DTO.
     *
     * @param dto The DTO containing SubsequentTimeslotsConstraint information.
     */
    private void delete(SubsequentTimeslotsConstraintReqDTO dto) {
        Optional<EmployeeSubsequentTimeslotsConstraint> constraint =
                employeeSubsequentTimeslotsConstraintRepository.findByEmployeeAbbreviation(
                        dto.getEmployeeAbbreviation());

        constraint.ifPresent(employeeSubsequentTimeslotsConstraintRepository::delete);
    }

    /**
     * Retrieves a SubsequentTimeslotsConstraintResDTO based on the employee's abbreviation.
     *
     * @param abbreviation The abbreviation of the employee.
     * @return A SubsequentTimeslotsConstraintResDTO containing constraint information.
     */
    public SubsequentTimeslotsConstraintResDTO getResDTO(String abbreviation) {
        return employeeSubsequentTimeslotsConstraintRepository
                .findByEmployeeAbbreviation(abbreviation)
                .map(subsequentTimeslotsConstraintConverter::convertToResDTO)
                .orElseGet(SubsequentTimeslotsConstraintResDTO::new);
    }
}
