package com.fhwedel.softwareprojekt.v1.service.types;

import com.fhwedel.softwareprojekt.v1.converter.types.SchoolTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import com.fhwedel.softwareprojekt.v1.repository.types.SchoolTypeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** A service for managing school types. This service provides CRUD operations for school types. */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolTypeService {

    /** The repository for managing school types. */
    private final SchoolTypeRepository schoolTypeRepository;

    /** The converter for converting between DTOs and entities for school types. */
    private final SchoolTypeConverter schoolTypeConverter;

    /**
     * Retrieves a list of all school types.
     *
     * @return A list of school types.
     */
    public List<SchoolType> findAll() {
        return schoolTypeRepository.findAll();
    }

    /**
     * Finds a school type by its ID.
     *
     * @param id The ID of the school type to find.
     * @return The found school type.
     * @throws ResponseStatusException If the school type is not found.
     */
    public SchoolType findByID(UUID id) {
        log.debug("Searching for schoolType with id {}", id);
        return schoolTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No schoolType entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            SchoolType.class,
                                            id));
                        });
    }

    /**
     * Saves a new school type.
     *
     * @param schoolTypeReqDto The DTO containing school type information.
     * @return The saved school type.
     */
    public SchoolType save(SchoolTypeReqDTO schoolTypeReqDto) {
        var schoolType = schoolTypeConverter.convertDtoToEntity(schoolTypeReqDto);
        log.debug("Saving {}", schoolType);
        schoolType = schoolTypeRepository.save(schoolType);
        log.info("Saved schoolType, assigned id {}", schoolType.getId());
        return schoolType;
    }

    /**
     * Updates an existing school type.
     *
     * @param id The ID of the school type to update.
     * @param schoolTypeReqDto The DTO containing updated school type information.
     * @return The updated school type.
     */
    @Transactional
    public SchoolType updateSchoolType(UUID id, SchoolTypeReqDTO schoolTypeReqDto) {
        SchoolType schoolType = findByID(id);
        log.debug("Updating {} \n  with value {}", schoolType, schoolTypeReqDto);
        schoolType.setName(schoolTypeReqDto.getName());
        return schoolType;
    }

    /**
     * Deletes a school type by its ID.
     *
     * @param id The ID of the school type to delete.
     */
    public void deleteSchoolType(UUID id) {
        SchoolType schoolType = findByID(id);
        try {
            schoolTypeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", schoolType);
    }
}
