package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.FreeDayConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.FreeDayConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.FreeDayConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeFreeDayConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeFreeDayConstraintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/** Service class for managing FreeDayConstraint operations. */
@Service
@RequiredArgsConstructor
public class FreeDayConstraintService {

    /** Repository for EmployeeFreeDayConstraint entities. */
    private final EmployeeFreeDayConstraintRepository employeeFreeDayConstraintRepository;

    /** Converter for FreeDayConstraint entities and DTOs. */
    private final FreeDayConstraintConverter freeDayConstraintConverter;

    /** ModelMapper for mapping between DTOs and entities. */
    private final ModelMapper modelMapper;

    /**
     * Handles the saving of FreeDayConstraint entities.
     *
     * @param dto The DTO containing FreeDayConstraint information.
     */
    public void handleSave(FreeDayConstraintReqDTO dto) {
        if (dto != null && dto.getEmployeeAbbreviation() != null) {
            if (dto.getConstraintValue() != null) {
                saveOrUpdate(dto);
            } else {
                delete(dto);
            }
        }
    }

    /**
     * Saves or updates a FreeDayConstraint entity based on the DTO.
     *
     * @param dto The DTO containing FreeDayConstraint information.
     */
    private void saveOrUpdate(FreeDayConstraintReqDTO dto) {

        EmployeeFreeDayConstraint constraint =
                employeeFreeDayConstraintRepository
                        .findByEmployeeAbbreviation(dto.getEmployeeAbbreviation())
                        .orElseGet(EmployeeFreeDayConstraint::new);

        modelMapper.map(dto, constraint);

        employeeFreeDayConstraintRepository.save(constraint);
    }

    /**
     * Deletes a FreeDayConstraint entity based on the DTO.
     *
     * @param dto The DTO containing FreeDayConstraint information.
     */
    private void delete(FreeDayConstraintReqDTO dto) {
        Optional<EmployeeFreeDayConstraint> constraint =
                employeeFreeDayConstraintRepository.findByEmployeeAbbreviation(
                        dto.getEmployeeAbbreviation());

        constraint.ifPresent(employeeFreeDayConstraintRepository::delete);
    }

    /**
     * Retrieves a FreeDayConstraintResDTO based on the employee's abbreviation.
     *
     * @param abbreviation The abbreviation of the employee.
     * @return A FreeDayConstraintResDTO containing constraint information.
     */
    public FreeDayConstraintResDTO getResDTO(String abbreviation) {
        return employeeFreeDayConstraintRepository
                .findByEmployeeAbbreviation(abbreviation)
                .map(freeDayConstraintConverter::convertToResDTO)
                .orElseGet(FreeDayConstraintResDTO::new);
    }
}
