package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRelationReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseRoomComboReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseTimeslotDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomCombinationRepository;
import com.fhwedel.softwareprojekt.v1.service.types.CourseTypeService;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing courses and their related entities such as room combinations, course
 * relations, and more.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    /** Repository for managing Courses. */
    private final CourseRepository courseRepository;

    /** Repository for managing Room Combinations. */
    private final RoomCombinationRepository roomCombinationRepository;

    /** Converter for Course entities. */
    private final CourseConverter courseConverter;

    /** Service for managing Rooms. */
    private final RoomService roomService;

    /** Service for managing Employees. */
    private final EmployeeService employeeService;

    /** Service for managing Course Relations. */
    private final CourseRelationService courseRelationService;

    /** Repository for managing Course Relations. */
    private final CourseRelationRepository courseRelationRepository;

    /** Service for managing Course Timeslots. */
    private final CourseTimeslotService courseTimeslotService;

    /** Service for managing Course Types. */
    private final CourseTypeService courseTypeService;

    /** Service for managing Timetables. */
    private final TimetableService timetableService;

    /**
     * Find all courses associated with a given timetable (Timetable).
     *
     * @param id The ID of the timetable (Timetable).
     * @return A list of courses associated with the timetable.
     */
    public List<Course> findAllCourses(UUID id) {

        log.debug("Finding all courses  by TimetableID {}", id);
        Timetable timetable = timetableService.findByID(id);
        return courseRepository.findByTimetable(timetable);
    }

    /**
     * Find a course by its ID.
     *
     * @param id The ID of the course to find.
     * @return The found course if it exists.
     * @throws ResponseStatusException If no course with the specified ID is found.
     */
    public Course findCourseByID(UUID id) {
        log.debug("Searching for course with id {}", id);
        return courseRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No course entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Course.class,
                                            id));
                        });
    }

    /**
     * Save a new course.
     *
     * @param courseReqDTO The information for the new course.
     * @param timetableId The ID of the timetable (Timetable) to which the course belongs.
     * @return The saved course.
     */
    @Transactional
    public Course saveCourse(CourseReqDTO courseReqDTO, UUID timetableId) {
        log.info("Creating and Saving new course with values\n  {}", courseReqDTO);
        Course course = courseConverter.convertCourseReqDtoToEntity(courseReqDTO);
        course.setTimetable(timetableService.findByID(timetableId));
        if (courseReqDTO.getCourseType() != null) {
            course.setCourseType(courseTypeService.findByID(courseReqDTO.getCourseType()));
            log.debug("Setting CourseType for Course with id {}", courseReqDTO.getCourseType());
        }

        // create room combinations
        for (CourseRoomComboReqDTO roomComboReqDTO : courseReqDTO.getSuitedRooms()) {
            RoomCombination newRoomCombo = createRoomCombo(course, roomComboReqDTO);
            course.getSuitedRooms().add(newRoomCombo);
            log.debug("added room combination to the course to be saved {}", newRoomCombo);
        }

        // add courseTimeslots
        List<CourseTimeslot> courseTimeslots = new ArrayList<>();
        for (CourseTimeslotDTO courseTimeslotDTO : courseReqDTO.getCourseTimeslots()) {
            log.debug("Creating new timeslot for course {}", courseReqDTO);
            courseTimeslots.add(courseTimeslotService.save(courseTimeslotDTO, course));
        }
        course.setCourseTimeslots(courseTimeslots);
        log.info("Save timeslots for course {}", course);

        // retrieve referenced employees and add them to the course
        addLecturers(course, courseReqDTO.getLecturers());

        log.debug("saving {}", course);
        course =
                courseRepository.save(
                        course); // reassignment just for consistency (not necessarily required)
        roomCombinationRepository.saveAll(course.getSuitedRooms());

        // create and associate course relations
        saveCourseRelations(course, courseReqDTO.getCourseRelations());

        log.info("Created and saved new course {}", course);

        return course;
    }

    /**
     * Update an existing course.
     *
     * @param id The ID of the course to update.
     * @param courseReqDTO The updated information for the course.
     * @param timetableId The ID of the timetable (Timetable) to which the course belongs.
     * @return The updated course.
     */
    @Transactional
    public Course updateCourse(UUID id, CourseReqDTO courseReqDTO, UUID timetableId) {
        Course course = this.findCourseByID(id);

        log.debug("Updating course {} \n with values {}", course, courseReqDTO);

        List<CourseTimeslot> courseTimeslots = new ArrayList<>();
        // delete old timeslots
        for (CourseTimeslot courseTimeslot : course.getCourseTimeslots()) {
            log.debug("Delete old courseTimeslot {}", courseTimeslot);
            courseTimeslotService.deleteCourseTimeslot(courseTimeslot.getId());
        }
        // add new courseTimeslots
        for (CourseTimeslotDTO courseTimeslotDTO : courseReqDTO.getCourseTimeslots()) {
            log.debug("Create and add new courseTimeslot {}", courseTimeslotDTO);
            CourseTimeslot courseTimeslot = courseTimeslotService.save(courseTimeslotDTO, course);
            courseTimeslots.add(courseTimeslot);
        }
        course.setCourseTimeslots(courseTimeslots);
        course.setCasID(courseReqDTO.getCasID());
        course.setName(courseReqDTO.getName());
        // necessary due to the specified optionality of the abbreviation
        course.setAbbreviation(courseConverter.convertCourseReqDtoToAbbrev(courseReqDTO));
        course.setDescription(courseReqDTO.getDescription());
        course.setBlockSize(courseReqDTO.getBlockSize());
        course.setWeeksPerSemester(courseReqDTO.getWeeksPerSemester());
        course.setSlotsPerWeek(courseReqDTO.getSlotsPerWeek());
        if (courseReqDTO.getCourseType() != null) {
            course.setCourseType(courseTypeService.findByID(courseReqDTO.getCourseType()));
        }
        if (courseReqDTO.getCourseType() == null) {
            course.setCourseType(null);
        }
        course.setTimetable(timetableService.findByID(timetableId));

        // remove/add room combinations
        replaceSuitedRooms(course, courseReqDTO.getSuitedRooms());

        // update lecturers
        replaceLecturers(course, courseReqDTO.getLecturers());

        // update course relations
        replaceCourseRelations(course, courseReqDTO.getCourseRelations());

        return course;
    }

    /**
     * Deletes a course by its ID, if it exists. If the course is associated with any constraints, a
     * constraint violation handling mechanism is invoked.
     *
     * @param id The ID of the course to delete.
     */
    public void deleteCourse(UUID id) {
        Course course = findCourseByID(id);
        try {
            courseRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }

        log.info("Deleted {}", course);
    }

    /**
     * Retrieves all course relations associated with a given course.
     *
     * @param course The course for which to retrieve course relations.
     * @return A list of course relations involving the given course.
     */
    private List<CourseRelation> getAllCourseRelations(Course course) {
        return courseRelationRepository.findByCourseA_IdOrCourseB_Id(
                course.getId(), course.getId());
    }

    /**
     * Creates for the given Course all course-relationships specified by the passed {@link
     * CourseRelationReqDTO}, and saves them to the database.
     *
     * @param targetCourse the target course
     * @param relationReqDTO DTO that specifies the course relations to be created
     */
    private void saveCourseRelations(Course targetCourse, CourseRelationReqDTO relationReqDTO) {
        log.debug(
                "Creating new course relations for course '{}'\n  from {}",
                targetCourse.getCasID(),
                relationReqDTO);
        List<CourseRelation> courseRelations = new ArrayList<>();

        for (IdWrapperDTO courseIdDTO : relationReqDTO.getMayBeParallelTo()) {
            if (courseIdDTO.getId() != null) {
                Course courseB = findCourseByID(courseIdDTO.getId());
                courseRelations.add(
                        courseRelationService.save(
                                targetCourse, courseB, RelationType.MAY_BE_PARALLEL));
            }
        }

        for (IdWrapperDTO courseIdDTO : relationReqDTO.getMustBeHeldBefore()) {
            if (courseIdDTO.getId() != null) {
                // the target course must be held before the course referenced by courseIdDTO
                Course courseB = findCourseByID(courseIdDTO.getId());
                log.debug(
                        "target course {} must be held before course {}",
                        targetCourse.getCasID(),
                        courseB.getCasID());
                courseRelations.add(
                        courseRelationService.save(
                                targetCourse, courseB, RelationType.A_MUST_BE_BEFORE_B));
            }
        }

        for (IdWrapperDTO courseIdDTO : relationReqDTO.getMustBeHeldAfter()) {
            if (courseIdDTO.getId() != null
                    // Note: targetCourse.getId() != courseIdDTO.getId()
                    // Prevents, if a course is related to itself, that a relation is inserted twice
                    && !targetCourse.getId().equals(courseIdDTO.getId())) {
                // the target course must be held after the course referenced by courseIdDTO
                Course courseA = findCourseByID(courseIdDTO.getId());
                log.debug(
                        "target course {} must be held after course {}",
                        targetCourse.getCasID(),
                        courseA.getCasID());
                courseRelations.add(
                        courseRelationService.save(
                                courseA, targetCourse, RelationType.A_MUST_BE_BEFORE_B));
            }
        }

        log.debug(
                "Created and saved {} new course relations {}",
                courseRelations.size(),
                courseRelations);
    }

    /**
     * Replaces the current course-relationships of the given course with the relationships
     * specified by the passed {@link CourseRelationReqDTO}, which are created and saved
     * accordingly. All old course relations will be deleted.
     *
     * @param course the target course to update
     * @param relationReqDTO DTO that specifies the new course relations
     */
    private void replaceCourseRelations(Course course, CourseRelationReqDTO relationReqDTO) {
        log.debug(
                "Replacing course relations of course '{}' with {}",
                course.getName(),
                relationReqDTO);
        // delete all existing relations of the course
        List<CourseRelation> oldCourseRelations = getAllCourseRelations(course);
        log.debug("Found {} old course relations", oldCourseRelations.size());

        for (CourseRelation oldRelation : oldCourseRelations) {
            courseRelationRepository.deleteById(oldRelation.getId());
            log.info("Deleted old course relation {}", oldRelation);
        }
        course.setCourseRelationsA(new ArrayList<>());
        course.setCourseRelationsB(new ArrayList<>());

        // flush the current memory cache from the EntityManager to the database session,
        // so that deletions become visible before insertions (otherwise we might get a unique
        // constraint violation)
        courseRelationRepository.flush();

        // create and add new course relations
        saveCourseRelations(course, relationReqDTO);
    }

    /**
     * Replaces the list of suited rooms of the course with the given set of room combinations.
     * Deletes and creates the room combinations accordingly.
     *
     * @param course the course
     * @param suitedRoomsDTO new set of room combination to replace the previous set of suited room
     *     combinations
     */
    private void replaceSuitedRooms(Course course, Set<CourseRoomComboReqDTO> suitedRoomsDTO) {
        log.info(
                "Replacing suitedRooms of course '{}' with\n  {}",
                course.getName(),
                suitedRoomsDTO);

        List<RoomCombination> oldSuitedRooms = course.getSuitedRooms();
        for (RoomCombination roomCombo : oldSuitedRooms) {
            log.debug("Delete old room combination {} of the course to update", roomCombo);
            roomCombinationRepository.deleteById(roomCombo.getId());
        }

        course.setSuitedRooms(new ArrayList<>());
        for (CourseRoomComboReqDTO roomComboReqDTO : suitedRoomsDTO) {
            RoomCombination newRoomCombo = createRoomCombo(course, roomComboReqDTO);
            course.getSuitedRooms().add(newRoomCombo);
            log.debug("Added new room combination {} to the course to update", newRoomCombo);
        }

        roomCombinationRepository.saveAll(course.getSuitedRooms());
    }

    /**
     * Replaces the list of employees associated as lecturers with the course with the employees
     * whose ID is referenced in the given set.
     *
     * @param course the course
     * @param lecturerIds set of IDs of the employees to replace the current lecturers
     */
    private void replaceLecturers(Course course, Set<IdWrapperDTO> lecturerIds) {
        log.debug("Replacing lecturers of course '{}' by\n  {}", course, lecturerIds);
        if (!course.getLecturers().isEmpty()) {
            for (Employee employee : course.getLecturers()) {
                // remove course reference in employee
                log.debug("Remove existing employee '{}' from the course", employee);
                employee.getCourses()
                        .removeIf(employeeCourse -> employeeCourse.getId().equals(course.getId()));
            }
            // remove all existing employees from course
            course.getLecturers().clear();
        }
        // fetch employees and add them to the course
        addLecturers(course, lecturerIds);
    }

    /**
     * Fetches employees whose ID is referenced in the given set and adds them to the course as
     * lecturers. If a referenced employee does not exist, a {@link ResponseStatusException} with
     * status NOT_FOUND is thrown.
     *
     * @param course the course
     * @param lecturerIds set of IDs of the employees to be added to the course
     */
    private void addLecturers(Course course, Collection<IdWrapperDTO> lecturerIds) {
        // retrieve employees referenced in the given collection and associate course and employees
        for (IdWrapperDTO employeeIdDTO : lecturerIds) {
            log.debug(
                    "Retrieve employee '{}', and associate them with the course '{}'",
                    employeeIdDTO,
                    course.getName());
            Employee employee = employeeService.findByID(employeeIdDTO.getId());
            log.debug("Adding employee to the course {}", employee);
            course.getLecturers().add(employee);
            employee.getCourses().add(course);
        }
    }

    /**
     * Creates a new room combination from the given dto and associates the room combination with
     * the course, but does not save entity to the database or add them to the course.
     *
     * @param course the course
     * @param roomComboReqDTO dto that specifies the room combination to be created.
     * @return a new instance of {@link RoomCombination}
     */
    private RoomCombination createRoomCombo(Course course, CourseRoomComboReqDTO roomComboReqDTO) {
        log.debug("Creating new RoomCombination for course '{}'", course.getName());
        RoomCombination roomCombo = new RoomCombination();
        roomCombo.setCourse(course);

        List<Room> rooms = new ArrayList<>();
        for (IdWrapperDTO roomIdDTO : roomComboReqDTO.getRooms()) {
            Room room = roomService.findByID(roomIdDTO.getId());
            log.debug("Adding room to the combination {}", room);
            rooms.add(room);
        }
        roomCombo.setRooms(rooms);

        return roomCombo;
    }
}
