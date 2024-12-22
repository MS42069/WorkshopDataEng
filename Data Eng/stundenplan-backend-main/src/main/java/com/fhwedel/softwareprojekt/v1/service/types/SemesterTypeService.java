package com.fhwedel.softwareprojekt.v1.service.types;

import com.fhwedel.softwareprojekt.v1.converter.types.SemesterTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * A service for managing semester types. This service provides CRUD operations for semester types.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SemesterTypeService {

    /** The repository for managing semester types. */
    private final SemesterTypeRepository semesterTypeRepository;

    /** The converter for converting between DTOs and entities for semester types. */
    private final SemesterTypeConverter semesterTypeConverter;

    /**
     * Retrieves a list of all semester types.
     *
     * @return A list of semester types.
     */
    public List<SemesterType> findAll() {
        return semesterTypeRepository.findAll();
    }

    /**
     * Finds a semester type by its ID.
     *
     * @param id The ID of the semester type to find.
     * @return The found semester type.
     * @throws ResponseStatusException If the semester type is not found.
     */
    public SemesterType findByID(UUID id) {
        log.debug("Searching for semesterType with id {}", id);
        return semesterTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No semesterType entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            SemesterType.class,
                                            id));
                        });
    }

    /**
     * Saves a new semester type.
     *
     * @param semesterTypeReqDTO The DTO containing semester type information.
     * @return The saved semester type.
     */
    public SemesterType save(SemesterTypeReqDTO semesterTypeReqDTO) {
        var semesterType = semesterTypeConverter.convertSemesterTypeDtoToEntity(semesterTypeReqDTO);
        log.debug("Saving {}", semesterType);
        semesterType = semesterTypeRepository.save(semesterType);
        log.info("Saved roomType, assigned id {}", semesterType.getId());
        return semesterType;
    }

    /**
     * Updates an existing semester type.
     *
     * @param id The ID of the semester type to update.
     * @param semesterTypeReqDTO The DTO containing updated semester type information.
     * @return The updated semester type.
     */
    @Transactional
    public SemesterType updateSemesterType(UUID id, SemesterTypeReqDTO semesterTypeReqDTO) {
        SemesterType semesterType = findByID(id);
        log.debug("Updating {} \n  with value {}", semesterType, semesterTypeReqDTO);
        semesterType.setName(semesterTypeReqDTO.getName());
        return semesterType;
    }

    /**
     * Deletes a semester type by its ID.
     *
     * @param id The ID of the semester type to delete.
     */
    public void deleteSemesterType(UUID id) {
        SemesterType semesterType = findByID(id);
        try {
            semesterTypeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", semesterType);
    }
}
