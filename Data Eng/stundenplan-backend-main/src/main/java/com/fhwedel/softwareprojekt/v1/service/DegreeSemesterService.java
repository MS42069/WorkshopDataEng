package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.DegreeSemesterConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing degree semesters, including operations like retrieval, creation,
 * updating, and deletion of degree semesters and their associations with degrees and courses.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DegreeSemesterService {

    /** Repository for managing degree semester data. */
    private final DegreeSemesterRepository degreeSemesterRepository;

    /** Repository for managing degree data. */
    private final DegreeRepository degreeRepository;

    /** Repository for managing course data. */
    private final CourseRepository courseRepository;

    /** Converter for degree semester-related operations. */
    private final DegreeSemesterConverter semesterConverter;

    /** Service for managing timetable-related operations. */
    private final TimetableService timetableService;

    /**
     * Finds all degree semesters for a given timetable.
     *
     * @param id The ID of the timetable.
     * @return A list of degree semesters associated with the timetable.
     */
    public List<DegreeSemester> findAll(UUID id) {
        log.debug("Finding all semesters for timetable {}", id);
        Timetable timetable = timetableService.findByID(id);
        return degreeSemesterRepository.findByTimetable(timetable);
    }

    /**
     * Finds a degree semester by its ID.
     *
     * @param id The ID of the degree semester to find.
     * @return The degree semester entity.
     * @throws ResponseStatusException if the degree semester is not found.
     */
    public DegreeSemester findByID(UUID id) {
        log.debug("Searching for semester with id {}", id);
        return degreeSemesterRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No semester entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            DegreeSemester.class,
                                            id));
                        });
    }

    /**
     * Saves a degree semester based on the provided DTO.
     *
     * @param semesterDTO The DTO containing degree semester data.
     * @param timetableId The ID of the associated timetable.
     * @return The saved degree semester entity.
     */
    @Transactional
    public DegreeSemester save(DegreeSemesterReqDTO semesterDTO, UUID timetableId) {
        var semester = semesterConverter.convertDtoToEntity(semesterDTO);
        log.debug("Setting Timetable for semester with id {}", semester.getId());
        semester.setTimetable(timetableService.findByID(timetableId));
        semester = degreeSemesterRepository.save(semester);
        log.debug("Saved semester {}", semester);
        semester.setDegree(getDegreeWhenExistingByUUID(semesterDTO.getDegree(), semester, true));
        semester.setCourses(getCoursesWhenExistingByUUID(semesterDTO.getCourses(), true));
        log.info("Saved semester and added degrees and courses to it {}", semester);
        return semester;
    }

    /**
     * Updates a degree semester based on the provided DTO.
     *
     * @param id The ID of the degree semester to update.
     * @param semesterDTO The DTO containing updated degree semester data.
     * @param timetableId The ID of the associated timetable.
     * @return The updated degree semester entity.
     */
    @Transactional
    public DegreeSemester updateSemester(
            UUID id, DegreeSemesterReqDTO semesterDTO, UUID timetableId) {
        DegreeSemester semester = findByID(id);
        Timetable timetable = timetableService.findByID(timetableId);
        log.debug("Updating {} \n  with values {}", semester, semesterDTO);
        semester.setSemesterNumber(semesterDTO.getSemesterNumber());
        semester.setExtensionName(semesterDTO.getExtensionName());
        semester.setAttendees(semesterDTO.getAttendees());
        semester.setDegree(getDegreeWhenExistingByUUID(semesterDTO.getDegree(), semester, false));
        semester.setCourses(getCoursesWhenExistingByUUID(semesterDTO.getCourses(), true));
        semester.setTimetable(timetable);
        log.info("Updated semester {}", semester);

        return semester;
    }

    /**
     * Deletes a degree semester by its ID.
     *
     * @param id The ID of the degree semester to delete.
     */
    public void deleteSemester(UUID id) {
        DegreeSemester semester = findByID(id);
        try {
            degreeSemesterRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", semester);
    }

    /**
     * Retrieves a list of courses based on the provided UUIDs if they exist.
     *
     * @param uuids A list of UUIDs representing courses.
     * @param create If true, courses are added to the result list; otherwise, only existing courses
     *     are checked.
     * @return A list of courses that match the provided UUIDs.
     * @throws ResponseStatusException if a course is not found.
     */
    private List<Course> getCoursesWhenExistingByUUID(List<UUID> uuids, boolean create) {
        List<Course> courses = new ArrayList<>();
        if (uuids != null) {
            for (UUID uuid : uuids) {
                Course course =
                        courseRepository
                                .findById(uuid)
                                .orElseThrow(
                                        () -> {
                                            log.warn("No course entity with id {} was found", uuid);
                                            throw new ResponseStatusException(
                                                    HttpStatus.NOT_FOUND,
                                                    String.format(
                                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                                            Course.class,
                                                            uuid));
                                        });
                if (course.getSemesters() == null) {
                    course.setSemesters(new ArrayList<>());
                }
                if (create) {
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    /**
     * Retrieves a degree based on the provided UUID if it exists.
     *
     * @param uuid The UUID representing the degree.
     * @param semester The associated degree semester.
     * @param create If true, the degree is associated with the semester; otherwise, only an
     *     existing degree is checked.
     * @return The degree entity that matches the provided UUID.
     * @throws ResponseStatusException if a degree is not found.
     */
    private Degree getDegreeWhenExistingByUUID(UUID uuid, DegreeSemester semester, boolean create) {
        Degree degree = null;
        if (uuid != null) {
            degree =
                    degreeRepository
                            .findById(uuid)
                            .orElseThrow(
                                    () -> {
                                        log.warn("No degree entity with id {} was found", uuid);
                                        throw new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                String.format(
                                                        APIError.ENTITY_NOT_FOUND.getMessage(),
                                                        Degree.class,
                                                        uuid));
                                    });
            if (degree.getSemesters() == null) {
                degree.setSemesters(new ArrayList<>());
            }
            if (create) {
                degree.getSemesters().add(semester);
            }
        }
        return degree;
    }
}
