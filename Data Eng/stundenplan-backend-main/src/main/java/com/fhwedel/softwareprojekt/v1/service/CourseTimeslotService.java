package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.dto.course.CourseTimeslotDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.repository.CourseTimeslotRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing course timeslots. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseTimeslotService {

    /** Repository for managing course timeslot data. */
    private final CourseTimeslotRepository courseTimeslotRepository;

    /** Service for managing timeslot-related operations. */
    private final TimeslotService timeslotService;

    /**
     * Retrieve a list of all course timeslots.
     *
     * @return A list of course timeslots.
     */
    public List<CourseTimeslot> findAll() {
        return courseTimeslotRepository.findAll();
    }

    /**
     * Retrieve a course timeslot by its ID.
     *
     * @param id The ID of the course timeslot to retrieve.
     * @return The course timeslot entity.
     * @throws ResponseStatusException If no course timeslot with the specified ID is found.
     */
    public CourseTimeslot findByID(UUID id) {
        return courseTimeslotRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No CourseTimeslot entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            CourseTimeslot.class,
                                            id));
                        });
    }

    /**
     * Create and save a new course timeslot for a given course.
     *
     * @param courseTimeslotDTO The DTO containing course timeslot information.
     * @param course The course for which the timeslot is associated.
     * @return The saved course timeslot entity.
     */
    public CourseTimeslot save(CourseTimeslotDTO courseTimeslotDTO, Course course) {
        log.info("Creating new timeslot for course '{}'", course.getId());
        var courseTimeslot = new CourseTimeslot();
        courseTimeslot.setCourse(course);

        Timeslot timeslot = timeslotService.findByID(courseTimeslotDTO.getTimeslotID());

        courseTimeslot.setTimeslot(timeslot);
        courseTimeslot.setWeekday(courseTimeslotDTO.getWeekday());

        courseTimeslot = courseTimeslotRepository.save(courseTimeslot);
        log.debug("Saved {}", courseTimeslot);
        return courseTimeslot;
    }

    /**
     * Delete a course timeslot by its ID, if it exists. If the course timeslot is associated with
     * any constraints, a constraint violation handling mechanism is invoked.
     *
     * @param id The ID of the course timeslot to delete.
     */
    public void deleteCourseTimeslot(UUID id) {
        CourseTimeslot courseTimeslot = findByID(id);
        try {
            courseTimeslotRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.debug("Deleted {}", courseTimeslot);
    }
}
