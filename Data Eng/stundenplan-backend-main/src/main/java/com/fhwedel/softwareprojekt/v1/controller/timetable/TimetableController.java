package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.TimetableConverter;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.service.CopyService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.TimetableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** REST-Controller for handling timetable-related requests. */
@RestController
@RequestMapping("/v1/timetables")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "timetable", description = "Operations about timetables")
public class TimetableController {

    /** Service for managing timetables. */
    private final TimetableService timetableService;

    /** Converter for converting timetable entities to DTOs. */
    private final TimetableConverter timetableConverter;

    /** Service for managing the scheduler. */
    private final SchedulerService schedulerService;

    /** Service for copying data. */
    private final CopyService copyService;

    @Operation(
            summary = "Find all timetables",
            description = "Returns all timetables, or empty list if no timetable was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<TimetableResDTO> getAllTimetables() {
        List<Timetable> timetables = timetableService.findAll();
        log.info("Found {} timetable(s)", timetables.size());
        return timetableConverter.convertEntitiesToResponseDTOList(timetables);
    }

    @Operation(
            summary = "Find timetable by ID",
            description =
                    "Returns the timetable identified by the given ID "
                            + "including a list of the associated special events")
    @Parameter(name = "timetableId", description = "ID of the timetable to return")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping(path = "/{timetableId}")
    public TimetableResDTO getSingleTimetable(@PathVariable("timetableId") UUID id) {
        Timetable timetable = timetableService.findByID(id);
        log.info("Found requested timetable {}", timetable);
        return timetableConverter.convertEntityToResponseDTO(timetable);
    }

    @Operation(
            summary = "Create new timetable",
            description =
                    "Creates a new timetable based on the given request body; "
                            + "also create the special events given in the list and associates them with the timetable.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Timetable was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid timetable was supplied
                    - if a property violates a unique constraint (name not unique)
                    - if the supplied special events list contains invalid special events
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "if a given timeslot does not exist")
            })
    @PostMapping
    public TimetableResDTO createTimetable(@Valid @RequestBody TimetableReqDTO timetableReqDTO) {
        log.info("Creating new timetable {}", timetableReqDTO);
        Timetable timetable = timetableService.save(timetableReqDTO);
        log.info("Created new timetable successfully: {}", timetable);
        return timetableConverter.convertEntityToResponseDTO(timetable);
    }

    @Operation(
            summary = "Update timetable and the special events",
            description =
                    "Updates the timetable identified by the given ID and replaces the list of special events. "
                            + "I.d. special events that no longer exist in the update list are deleted, non-existing special events are created and added to the timetable.")
    @Parameter(name = "timetableId", description = "ID of the timetable to update")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid timetableId was supplied
                    - if an invalid timetable request object supplied
                    - if an timetable property with a unique constraint is not unique
                    - if the supplied special events list contains invalid special events
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                "if the timetable was not found or a given timeslot in the special events list does not exist",
                        content = @Content)
            })
    @PutMapping(path = "/{timetableId}")
    public TimetableResDTO editTimetable(
            @PathVariable("timetableId") UUID timetableId,
            @Valid @RequestBody TimetableReqDTO timetableReqDTO) {
        Timetable timetable = timetableService.updateTimetable(timetableId, timetableReqDTO);
        log.info("Updated timetable successfully: {}", timetable);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return timetableConverter.convertEntityToResponseDTO(timetable);
    }

    @Operation(
            summary = "Delete timetable by ID",
            description =
                    "Deletes the timetable identified by the given ID and all special events associated with them")
    @Parameter(name = "timetableId", description = "ID of the timetable to delete")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Timetable was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @DeleteMapping(path = "/{timetableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimetable(@PathVariable("timetableId") UUID id) {
        timetableService.deleteTimetable(id);

        // Remove the scheduler instance that may have been created for the timetable
        schedulerService.removeScheduler(id);
    }

    @Operation(
            summary = "Copy all Data associated from a Timetable to a different Timetable",
            description =
                    "Deletes the Data associated with a specific timetable and then copy Data from another timetable")
    @Parameter(name = "timetableId", description = "ID of the target timetable to copy to")
    @Parameter(name = "timetableId2", description = "ID of the source timetable to copy from")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Timetable was copied successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PutMapping(path = "/{timetableId}/copyAll/{timetableId2}")
    public TimetableResDTO copyTimetable(
            @PathVariable("timetableId") UUID copyTo, @PathVariable("timetableId2") UUID copyFrom) {
        Timetable ttcopyTo = timetableService.findByID(copyTo);
        Timetable ttcopyFrom = timetableService.findByID(copyFrom);

        copyService.copy(ttcopyTo, ttcopyFrom);
        return getSingleTimetable(copyTo);
    }

    @Operation(
            summary =
                    "Copy selected Data associated from a Timetable to a different Timetable, with Request-Parameters('*?example=1&example2=1')",
            description =
                    "Deletes the Data associated with a specific timetable and then copy Data from another timetable")
    @Parameter(name = "timetableId", description = "ID of the target timetable to copy to")
    @Parameter(name = "timetableId2", description = "ID of the source timetable to copy from")
    @Parameter(name = "employee", description = "optional Request-Parameter to copy employees")
    @Parameter(name = "room", description = "optional Request-Parameter to copy rooms")
    @Parameter(name = "course", description = "optional Request-Parameter to copy courses")
    @Parameter(name = "degree", description = "optional Request-Parameter to copy degrees")
    @Parameter(
            name = "degree-semester",
            description = "optional Request-Parameter to copy degree-semesters")
    @Parameter(name = "timeslot", description = "optional Request-Parameter to copy timeslots")
    @Parameter(name = "week-event", description = "optional Request-Parameter to copy week-events")
    @Parameter(
            name = "special-event",
            description = "optional Request-Parameter to copy special-events")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Timetable was copied successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PutMapping(path = "/{timetableId}/copyfrom/{timetableId2}")
    public TimetableResDTO copyTimetablePartial(
            @PathVariable("timetableId") UUID copyTo,
            @PathVariable("timetableId2") UUID copyFrom,
            @RequestParam(required = false, defaultValue = "false") boolean employee,
            @RequestParam(required = false, defaultValue = "false") boolean room,
            @RequestParam(required = false, defaultValue = "false") boolean course,
            @RequestParam(required = false, defaultValue = "false") boolean degree,
            @RequestParam(required = false, defaultValue = "false") boolean degreesemester,
            @RequestParam(required = false, defaultValue = "false") boolean timeslot,
            @RequestParam(required = false, defaultValue = "false") boolean weekevent,
            @RequestParam(required = false, defaultValue = "false") boolean specialevent) {
        Timetable ttcopyTo = timetableService.findByID(copyTo);
        Timetable ttcopyFrom = timetableService.findByID(copyFrom);

        copyService.copyPartial(
                ttcopyTo,
                ttcopyFrom,
                employee,
                room,
                course,
                degree,
                degreesemester,
                timeslot,
                weekevent,
                specialevent);
        return getSingleTimetable(copyTo);
    }
}
