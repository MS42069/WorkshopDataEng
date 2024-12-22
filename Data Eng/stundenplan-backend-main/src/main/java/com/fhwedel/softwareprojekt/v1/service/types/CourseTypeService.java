package com.fhwedel.softwareprojekt.v1.service.types;

import com.fhwedel.softwareprojekt.v1.converter.types.CourseTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import com.fhwedel.softwareprojekt.v1.repository.types.CourseTypeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** A service for managing course types. This service provides CRUD operations for course types. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseTypeService {
    /** The repository for managing course types. */
    private final CourseTypeRepository courseTypeRepository;

    /** The converter for converting between DTOs and entities for course types. */
    private final CourseTypeConverter courseTypeConverter;

    /**
     * Retrieves a list of all course types.
     *
     * @return A list of course types.
     */
    public List<CourseType> findAll() {
        return courseTypeRepository.findAll();
    }

    /**
     * Finds a course type by its ID.
     *
     * @param id The ID of the course type to find.
     * @return The found course type.
     * @throws ResponseStatusException If the course type is not found.
     */
    public CourseType findByID(UUID id) {
        log.debug("Searching for courseType with id {}", id);
        return courseTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No courseType entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            CourseType.class,
                                            id));
                        });
    }

    /**
     * Saves a new course type.
     *
     * @param courseTypeReqDto The DTO containing course type information.
     * @return The saved course type.
     */
    public CourseType save(CourseTypeReqDTO courseTypeReqDto) {
        var courseType = courseTypeConverter.convertDtoToEntity(courseTypeReqDto);
        log.debug("Saving {}", courseType);
        courseType = courseTypeRepository.save(courseType);
        log.info("Saved courseType, assigned id {}", courseType.getId());
        return courseType;
    }

    /**
     * Updates an existing course type.
     *
     * @param id The ID of the course type to update.
     * @param courseTypeReqDto The DTO containing updated course type information.
     * @return The updated course type.
     */
    @Transactional
    public CourseType updateCourseType(UUID id, CourseTypeReqDTO courseTypeReqDto) {
        CourseType courseType = findByID(id);
        log.debug("Updating {} \n  with value {}", courseType, courseTypeReqDto);
        courseType.setName(courseTypeReqDto.getName());
        return courseType;
    }

    /**
     * Deletes a course type by its ID.
     *
     * @param id The ID of the course type to delete.
     */
    public void deleteCourseType(UUID id) {
        CourseType courseType = findByID(id);
        try {
            courseTypeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", courseType);
    }
}
