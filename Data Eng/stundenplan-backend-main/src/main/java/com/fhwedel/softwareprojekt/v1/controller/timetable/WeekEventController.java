package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.dto.schedule.*;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** REST-Controller for handling week event-related requests. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/week-events")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "week event",
        description =
                "Operations for planning and scheduling week events (wöchentliche Veranstaltungstermine)")
public class WeekEventController {

    /** Service for managing the scheduler. */
    private final SchedulerService schedulerService;

    @Operation(
            summary = "Find all week events",
            description = "Returns all week events that match the specified query.")
    @Parameter(name = "timetableId", description = "The ID of the timetable to search for.")
    @Parameter(
            name = "week",
            description =
                    "The week number to filter the events. If null, events for all weeks are returned.")
    @Parameter(
            name = "weekday",
            description =
                    "The day of the week for which all events that take place on this day are to be returned; "
                            + "if null, then the week events for all weekdays are returned")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful fetch",
                        content = @Content)
            })
    @GetMapping
    public List<WeekEventResDTO> getAllEvents(
            @PathVariable UUID timetableId,
            @RequestParam(value = "week", required = false) Integer week,
            @RequestParam(value = "weekday", required = false) DayOfWeek weekday) {

        List<WeekEventResDTO> events =
                schedulerService.getAllWeekEventsAsDTO(
                        timetableId,
                        Optional.ofNullable(week), // Add week parameter
                        Optional.ofNullable(weekday));
        log.info("Found {} weekly event(s)", events.size());

        return events;
    }

    @Operation(
            summary = "Find options for week events",
            description =
                    """
            Finds for one ore more given courses the options (i.e. the possible week events) on which these courses can be scheduled.
            Theoretically, the 'degree of freedom' (= Freiheitsgrad) can be derived directly from the number of
            options.
            """)
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @Parameter(
            name = "courseIds",
            description = "Set of IDs of the courses for which the options are to be returned")
    @Parameter(
            name = "weekdays",
            description =
                    "Set of weekdays to be considered when searching for options (does not influence the degree of freedom);"
                            + "if not specified all 7 days of the week (Mo-Su) are considered.")
    @Parameter(
            name = "week",
            description =
                    "Set of weeks to be considered when searching for options. If not specified, all weeks are considered.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successful Operation, return for each given course the options"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request:
                     - if an invalid timetableId or courseId was supplied
                     - if no courseIds were supplied
                    """,
                        content = @Content),
            })
    @GetMapping(path = "/options")
    public List<OptionsDTO> getEventOptions(
            @PathVariable UUID timetableId,
            @RequestParam Set<UUID> courseIds,
            @RequestParam(value = "week", required = false) Set<Integer> weeks,
            @RequestParam(required = false, name = "weekdays") Set<DayOfWeek> weekdays) {
        schedulerService.initializeIfNotPresent(timetableId);
        log.info(
                "Handling request to find the options for the courses with courseIds={}",
                courseIds);
        log.debug("Weeks to consider for the options {}", weeks);
        log.debug("Weekdays to consider for the options {}", weekdays);
        return schedulerService.findEventOptions(
                timetableId, courseIds, Optional.ofNullable(weeks), Optional.ofNullable(weekdays));
    }

    @Operation(
            summary = "Find all week events of an employee by ID",
            description =
                    "Returns the events identified by the given ID, "
                            + "especially includes detailed information")
    @Parameter(name = "employeeId", description = "ID of the employee to return event for")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content)
            })
    @GetMapping(path = "/employee/{employeeId}")
    public List<WeekEventResDTO> getWeekEventsForEmployee(@PathVariable UUID employeeId) {
        List<WeekEventResDTO> resDTO = schedulerService.getAllWeekEventsOfEmployee(employeeId);
        log.info("Found requested events, returning {}", resDTO);
        return resDTO;
    }

    @Operation(
            summary = "Find all week events of an degreeSemester by ID",
            description =
                    "Returns the events identified by the given ID, "
                            + "especially includes detailed information")
    @Parameter(
            name = "degreeSemesterId",
            description = "ID of the degreeSemester to return event for")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content)
            })
    @GetMapping(path = "/semester/{degreeSemesterId}")
    public List<WeekEventResDTO> getWeekEventsForDegreeSemester(
            @PathVariable UUID degreeSemesterId) {
        List<WeekEventResDTO> resDTO =
                schedulerService.getAllWeekEventsOfDegreeSemester(degreeSemesterId);
        log.info("Found requested events, returning {}", resDTO);
        return resDTO;
    }

    @Operation(
            summary = "Find all week events of an room by ID",
            description =
                    "Returns the events identified by the given ID, "
                            + "especially includes detailed information")
    @Parameter(name = "roomId", description = "ID of the room to return event for")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content)
            })
    @GetMapping(path = "/room/{roomId}")
    public List<WeekEventResDTO> getWeekEventsForRoom(@PathVariable UUID roomId) {
        List<WeekEventResDTO> resDTO = schedulerService.getAllWeekEventsOfRoom(roomId);
        log.info("Found requested events, returning {}", resDTO);
        return resDTO;
    }

    @Operation(
            summary = "Find week event by ID",
            description =
                    "Returns the event identified by the given ID, "
                            + "especially includes detailed information")
    @Parameter(name = "eventId", description = "ID of the event to return")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Event not found",
                        content = @Content)
            })
    @GetMapping(path = "/{eventId}")
    public WeekEventResDTO getSingleWeekEvent(@PathVariable UUID eventId) {
        WeekEventResDTO resDTO = schedulerService.getWeekEventByIdAsDTO(eventId);
        log.info("Found requested event, returning {}", resDTO);
        return resDTO;
    }

    @Operation(
            summary = "Schedule a course for a specific weekly date.",
            description =
                    """
                    Schedules a course on a specific weekly date (defined by a weekday and a block of time slots)
                    for one or more rooms. Verifies that the course can actually be scheduled on this date
                    without violating constraints that would lead to an inconsistent schedule. Subsequently
                    creates a week event representing this date.\s
                    The constraint check can be disabled by the request parameter `force`.
                    """)
    @Parameter(
            name = "force",
            description =
                    "Whether to force the scheduling of an event, regardless of problems encountered/constraints violated.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "if scheduling of the given event was successful"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid Request Body or Request Parameter was supplied (e.g. illegal null values)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    Not found
                    - if a resource associated with a specified ID (in the path or body) does not exist
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "409",
                        description =
                                """
                    Event could not be scheduled because of a constraint violation/conflicts""",
                        content = @Content(schema = @Schema(implementation = ProblemsDTO.class)))
            })
    @PostMapping("/schedule")
    public SchedulingResultDTO scheduleWeekEvent(
            @PathVariable UUID timetableId,
            @RequestParam(value = "force", defaultValue = "false") boolean force,
            @RequestBody @Valid WeekEventReqDTO weekEventReqDTO) {
        log.info("Handling scheduling request {}", weekEventReqDTO);
        SchedulingResultDTO resultDTO =
                schedulerService.scheduleEvent(timetableId, weekEventReqDTO, force);
        log.info("Scheduled event successfully, returning {}", resultDTO);
        return resultDTO;
    }

    @Operation(
            summary = "Delete week event by ID",
            description = "Deletes the event identified by the given ID")
    @Parameter(name = "eventId", description = "ID of the event to delete")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Event was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable or event not found",
                        content = @Content)
            })
    @DeleteMapping(path = "/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWeekEvent(@PathVariable UUID timetableId, @PathVariable UUID eventId) {
        schedulerService.deleteEvent(timetableId, eventId);
    }

    @Operation(
            summary = "Regenerate options scheduler",
            description =
                    """
            Regenerate the Scheduler.
            Reloads the entities from the database and recalculates the freedom of degrees.
            """)
    @Parameter(name = "timetableId", description = "ID of the timetable")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful Operation, timetable schedule did regenerate"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request:
                     - if an invalid timetableId or courseId was supplied
                     - if no courseIds were supplied
                    """,
                        content = @Content)
            })
    @PutMapping(path = "/regenerate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void regenerate(@PathVariable UUID timetableId) {
        log.info("Handling request to regenerate the scheduler for timetable={}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
