package com.fhwedel.softwareprojekt.v1.service.types;

import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * A service for managing employee types. This service provides CRUD operations for employee types.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeTypeService {

    /** The repository for managing employee types. */
    private final EmployeeTypeRepository employeeTypeRepository;

    /** The converter for converting between DTOs and entities for employee types. */
    private final EmployeeTypeConverter employeeTypeConverter;

    /**
     * Retrieves a list of all employee types.
     *
     * @return A list of employee types.
     */
    public List<EmployeeType> findAll() {
        return employeeTypeRepository.findAll();
    }

    /**
     * Finds an employee type by its ID.
     *
     * @param id The ID of the employee type to find.
     * @return The found employee type.
     * @throws ResponseStatusException If the employee type is not found.
     */
    public EmployeeType findByID(UUID id) {
        log.debug("Searching for employeeType with id {}", id);
        return employeeTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No employeeType entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            EmployeeType.class,
                                            id));
                        });
    }

    /**
     * Saves a new employee type.
     *
     * @param employeeTypeReqDto The DTO containing employee type information.
     * @return The saved employee type.
     */
    public EmployeeType save(EmployeeTypeReqDTO employeeTypeReqDTO) {

        var employeeType = employeeTypeConverter.convertEmployeeTypeDtoToEntity(employeeTypeReqDTO);
        log.debug("Saving {}", employeeType);
        employeeType = employeeTypeRepository.save(employeeType);
        log.info("Saved employeeType, assigned id {}", employeeType.getId());
        return employeeType;
    }

    /**
     * Updates an existing employee type.
     *
     * @param id The ID of the employee type to update.
     * @param employeeTypeReqDto The DTO containing updated employee type information.
     * @return The updated employee type.
     */
    @Transactional
    public EmployeeType updateEmployeeType(UUID id, EmployeeTypeReqDTO employeeTypeReqDTO) {
        EmployeeType employeeType = findByID(id);
        log.debug("Updating {} \n  with value {}", employeeType, employeeTypeReqDTO);
        employeeType.setName(employeeTypeReqDTO.getName());
        return employeeType;
    }

    /**
     * Deletes an employee type by its ID.
     *
     * @param id The ID of the employee type to delete.
     */
    public void deleteEmployeeType(UUID id) {
        EmployeeType employeeType = findByID(id);
        try {
            employeeTypeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", employeeType);
    }
}
