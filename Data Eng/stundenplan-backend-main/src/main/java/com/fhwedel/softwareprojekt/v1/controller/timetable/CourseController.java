package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseDetailResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseToPlanResDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.service.CourseService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Controller class for managing courses, including their associated data. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/courses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "course", description = "Everything about courses")
public class CourseController {

    /** The course service for managing courses. */
    private final CourseService courseService;

    /** The course converter for converting course entities to DTOs. */
    private final CourseConverter courseConverter;

    /** The scheduler service for scheduling courses. */
    private final SchedulerService schedulerService;

    @Operation(
            summary = "Find all courses",
            description = "Returns all courses including suitable rooms and associated employees.")
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping
    public List<CourseDetailResDTO> getAllCourses(@PathVariable("timetableId") UUID id) {
        List<Course> courses = courseService.findAllCourses(id);
        log.info("Found {} course(s)", courses.size());
        return courseConverter.convertCourseEntitiesToDetailResDtoList(courses);
    }

    @Operation(
            summary = "Find all courses that still have to be scheduled",
            description =
                    "Returns all courses that still need to be scheduled, additionally returns "
                            + "for how many timeslots a course still needs to be scheduled, as well as the Degree of Freedom.")
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @Parameter(
            name = "employeeId",
            description =
                    "Optional employee ID. If specified, only courses held by the "
                            + "given employee are returned.")
    @Parameter(
            name = "degreeSemesterId",
            description =
                    "Optional degree semester ID. If specified, only "
                            + "courses attended by the given semester are returned.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping(path = "/toPlan")
    public List<CourseToPlanResDTO> getAllCoursesToPlan(
            @PathVariable("timetableId") UUID timetableId,
            @RequestParam(name = "employeeId", required = false) UUID employeeId,
            @RequestParam(name = "degreeSemesterId", required = false) UUID semesterId) {
        List<CourseToPlanResDTO> courses =
                schedulerService.findAllCoursesStillToPlan(
                        timetableId,
                        Optional.ofNullable(employeeId),
                        Optional.ofNullable(semesterId));
        log.info("Found {} course(s) that still have to be scheduled ", courses.size());
        return courses;
    }

    @Operation(
            summary = "Find course by ID",
            description =
                    "Returns the course identified by the given ID, "
                            + "includes detailed information and nested resources such as suitable room combinations etc.")
    @Parameter(name = "courseId", description = "ID of the course to return")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Course not found",
                        content = @Content)
            })
    @GetMapping(path = "/{courseId}")
    public CourseDetailResDTO getSingleCourse(@PathVariable("courseId") UUID courseId) {
        Course course = courseService.findCourseByID(courseId);
        CourseDetailResDTO resDTO = courseConverter.convertCourseEntityToDetailResDTO(course);
        log.info("Found requested course, returning {}", resDTO);
        return resDTO;
    }

    @Operation(
            summary = "Create new course",
            description =
                    """
            Creates a new course based on the given request body.
            Creates also specified room combinations and relations to other courses (such as "may be parallel" etc.).
            """)
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Course was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if a invalid request body was supplied (e.g. required attribute missing,
                      invalid suited room list, minimum constraint violated etc.)
                    - if a property violates a unique constraint (e.g. casID already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    Not found
                    - if an employee specified in `lecturers` does not exist
                    - if the timetable does not exist
                    - if a room specified as part of a room combination in `suitedRooms` does not exist
                    - if a course specified in `courseRelations` does not exist
                    """,
                        content = @Content)
            })
    @PostMapping
    public CourseDetailResDTO createCourse(
            @Valid @RequestBody CourseReqDTO courseDTO,
            @PathVariable("timetableId") UUID timetableId) {
        Course course = courseService.saveCourse(courseDTO, timetableId);
        CourseDetailResDTO result = courseConverter.convertCourseEntityToDetailResDTO(course);
        log.info("Created new course successfully, returning {}", result);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return result;
    }

    @Operation(
            summary =
                    "Update course including its suitable rooms, associated employees and relations to other courses",
            description =
                    """
                    Updates the course identified by the given ID.
                    Replaces in particular suitable rooms or room combinations, associated employees and course relationships.
                    For course dependent resources such as room combinations or course relations, resources that no longer exist
                    in the specified DTO are deleted, and resources that do not yet exist are created.
                    For example, specifying an empty list for suitedRooms results in all
                    associated room combinations being removed.
                    (empty list or set is always the assumed default case if a property of this kind is not specified)
                    """)
    @Parameter(name = "courseId", description = "ID of the course to update")
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful update (returning the updated course"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid courseId was supplied
                    - if a invalid request body was supplied (e.g. required attribute missing,
                      invalid suited room list, minimum constraint violated etc.)
                    - if a updated property violates a unique constraint (e.g. casID already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    Not found
                    - if a course identified by the given ID does not exist
                    - if the timetable does not exist
                    - if an employee specified in `lecturers` or a room  does not exist
                    - if a room specified as part of a room combination in `suitedRooms` does not exist
                    - if a course specified in `courseRelations` does not exist
                    """,
                        content = @Content)
            })
    @PutMapping(path = "/{courseId}")
    public CourseDetailResDTO editCourse(
            @PathVariable("courseId") UUID courseId,
            @Valid @RequestBody CourseReqDTO courseDTO,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling update request for course '{}'", courseId);
        Course course = courseService.updateCourse(courseId, courseDTO, timetableId);
        log.info("Updated course successfully: {}", course);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return courseConverter.convertCourseEntityToDetailResDTO(course);
    }

    @Operation(
            summary = "Delete course by ID",
            description =
                    "Deletes the course identified by the given ID, "
                            + "also deletes all exclusively associated resources such as suitable room combinations.")
    @Parameter(name = "courseId", description = "ID of the course to delete")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Course was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "A course with the given ID does not exist")
            })
    @DeleteMapping(path = "/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(
            @PathVariable("courseId") UUID courseId,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling delete request for the course with id {}", courseId);
        courseService.deleteCourse(courseId);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
