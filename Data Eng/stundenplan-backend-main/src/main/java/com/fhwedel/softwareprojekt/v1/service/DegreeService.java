package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.DegreeConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.service.types.SchoolTypeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing degree-related operations. */
@Service
@RequiredArgsConstructor
@Slf4j
public class DegreeService {

    /** Repository for managing degree entities. */
    private final DegreeRepository degreeRepository;

    /** Service for managing timetable-related operations. */
    private final TimetableService timetableService;

    /** Converter for mapping between Degree DTOs and entities. */
    private final DegreeConverter degreeConverter;

    /** Service for managing school type-related operations. */
    private final SchoolTypeService schoolTypeService;

    /** Repository for managing degree semester entities. */
    private final DegreeSemesterRepository degreeSemesterRepository;

    /**
     * Retrieves all degrees associated with the specified timetable.
     *
     * @param uuid The UUID of the timetable.
     * @return A list of degrees associated with the timetable.
     */
    public List<Degree> findAll(UUID uuid) {
        log.debug("Finding all degrees for timetable {}", uuid);
        Timetable timetable = timetableService.findByID(uuid);
        return degreeRepository.findByTimetable(timetable);
    }

    /**
     * Retrieves a degree by its UUID.
     *
     * @param id The UUID of the degree to retrieve.
     * @return The degree entity if found; otherwise, throws an exception.
     * @throws ResponseStatusException if a degree with the given UUID is not found.
     */
    public Degree findByID(UUID id) {
        log.debug("Searching for degree with id {}", id);
        return degreeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No degree entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Degree.class,
                                            id));
                        });
    }

    /**
     * Saves a new degree entity with the provided data and associates it with a timetable.
     *
     * @param degreeDTO The DTO containing degree information.
     * @param timetableId The UUID of the associated timetable.
     * @return The saved degree entity.
     */
    @Transactional
    public Degree save(DegreeReqDTO degreeDTO, UUID timetableId) {
        var degree = degreeConverter.convertDtoToEntity(degreeDTO);
        log.debug("Setting Timetable for Degree with id {}", degree.getId());
        degree.setTimetable(timetableService.findByID(timetableId));

        if (degreeDTO.getSchoolType() != null) {
            degree.setSchoolType(schoolTypeService.findByID(degreeDTO.getSchoolType()));
            log.debug("Setting SchoolType for Degree with id {}", degreeDTO.getSchoolType());
        }
        degree = degreeRepository.save(degree);
        log.debug("Saved degree {}", degree);
        degree.setSemesters(getDegreeSemestersWhenExistingByUUID(degreeDTO.getSemesters(), degree));
        log.info("Saved degree and added their semesters {}", degree);
        return degree;
    }

    /**
     * Updates an existing degree entity with the provided data.
     *
     * @param id The UUID of the degree to update.
     * @param degreeDTO The DTO containing updated degree information.
     * @param timetableId The UUID of the associated timetable.
     * @return The updated degree entity.
     */
    @Transactional
    public Degree updateDegree(UUID id, DegreeReqDTO degreeDTO, UUID timetableId) {
        Degree degree = findByID(id);
        Timetable timetable = timetableService.findByID(timetableId);
        log.debug("Updating {} \n  with values {}", degree, degreeDTO);
        degree.setSemesters(getDegreeSemestersWhenExistingByUUID(degreeDTO.getSemesters(), degree));
        degree.setName(degreeDTO.getName());
        degree.setShortName(degreeDTO.getShortName());

        if (degreeDTO.getSchoolType() != null) {
            degree.setSchoolType(schoolTypeService.findByID(degreeDTO.getSchoolType()));
        }
        if (degreeDTO.getSchoolType() == null) {
            degree.setSchoolType(null);
        }
        degree.setSemesterAmount(degreeDTO.getSemesterAmount());
        degree.setStudyRegulation(degreeDTO.getStudyRegulation());
        degree.setTimetable(timetable);

        log.info("Updated degree {}", degree);

        return degree;
    }

    /**
     * Deletes a degree entity by its UUID.
     *
     * @param id The UUID of the degree to delete.
     */
    public void deleteDegree(UUID id) {
        Degree degree = findByID(id);
        try {
            degreeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", degree);
    }

    /**
     * Retrieves a list of degree semesters based on the provided UUIDs if they exist.
     *
     * @param ids A list of UUIDs representing degree semesters.
     * @param degree The associated degree.
     * @return A list of degree semesters that match the provided UUIDs.
     * @throws ResponseStatusException if a degree semester is not found.
     */
    private List<DegreeSemester> getDegreeSemestersWhenExistingByUUID(
            List<UUID> ids, Degree degree) {
        List<DegreeSemester> semesters = new ArrayList<>();
        Optional<DegreeSemester> optSemester;
        DegreeSemester semester;
        if (ids != null) {
            for (UUID id : ids) {
                log.debug("Searching if semester with id {} exists", id);
                optSemester = degreeSemesterRepository.findById(id);
                if (optSemester.isPresent()) {
                    semester = optSemester.get();
                    semester.setDegree(degree);
                    semesters.add(semester);
                } else {
                    log.warn("No semester entity with id {} was found", id);
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format(
                                    APIError.ENTITY_NOT_FOUND.getMessage(),
                                    DegreeSemester.class,
                                    id));
                }
            }
        }
        return semesters;
    }
}
