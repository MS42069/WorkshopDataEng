package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.TimeslotConverter;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.TimeslotService;
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

/** REST-Controller for handling timeslot-related requests. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/timeslots")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "timeslot", description = "Operations about timeslots")
public class TimeslotController {

    /** Service for managing timeslots. */
    private final TimeslotService timeslotService;

    /** Converter for converting timeslot entities to DTOs. */
    private final TimeslotConverter timeslotConverter;

    /** Service for managing the scheduler. */
    private final SchedulerService schedulerService;

    @Operation(
            summary = "Find all timeslots",
            description = "Returns a list of all timeslots, or empty list if no timeslot was found")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping
    public List<TimeslotResDTO> getAllTimeslots(@PathVariable("timetableId") UUID id) {
        List<Timeslot> timeslots = timeslotService.findAll(id);
        log.info("Found {} timeslot(s): {}", timeslots.size(), timeslots);
        return timeslotConverter.convertEntitiesToResponseDTOList(timeslots);
    }

    @Operation(
            summary = "Find timeslot by ID",
            description = "Returns the timeslot identified by the given ID")
    @Parameter(
            name = "timeslotId",
            description = "ID of the timeslot to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid timeslotId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timeslot not found",
                        content = @Content)
            })
    @GetMapping(path = "/{timeslotId}")
    public TimeslotResDTO getSingleTimeslot(@PathVariable("timeslotId") UUID uuid) {
        Timeslot timeslot = timeslotService.findByID(uuid);

        TimeslotResDTO responseDTO = timeslotConverter.convertEntityToResponseDTO(timeslot);
        log.info("Found requested timeslot, returning {}", responseDTO);
        return responseDTO;
    }

    @Operation(
            summary = "Create new timeslot",
            description =
                    """
                    Creates a new timeslot with the given time interval; returns the created timeslot with the ID
                    assigned to it. The given time interval must neither overlap with an already existing timeslot
                    nor be directly adjacent to it.""")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Timeslot was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (e.g. invalid time format or end before start time)
                    - if a timeslot property violates a unique constraint (e.g. index already used)
                    - if the given timeslot overlaps with an already existing timeslot
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PostMapping
    TimeslotResDTO createTimeslot(
            @Valid @RequestBody TimeslotReqDTO timeslotReqDTO,
            @PathVariable("timetableId") UUID timetableId) {

        Timeslot timeslot = timeslotService.save(timetableId, timeslotReqDTO);

        log.info("Created new timeslot successfully: {}", timeslot);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return timeslotConverter.convertEntityToResponseDTO(timeslot);
    }

    @Operation(
            summary = "Update timeslot",
            description =
                    """
                    Updates the properties of the timeslot identified by the given ID to the properties in the
                    submitted request body; returns the updated timeslot. As with creation, the updated timeslot
                    must not overlap with an existing timeslot (including interval boundaries)""")
    @Parameter(
            name = "timeslotId",
            description = "ID of the timeslot to update (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid timeslotId was supplied
                     - if an invalid request body was supplied (e.g. invalid time format, end before start time)
                     - if an update property violates a unique constraint (e.g. index already used)
                     - if the updated timeslot overlaps with another timeslot
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                     Not Found
                    - Timeslot with the given ID does not exist
                    - Timetable with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{timeslotId}")
    TimeslotResDTO editTimeslot(
            @PathVariable("timeslotId") UUID id,
            @Valid @RequestBody TimeslotReqDTO timeslotReqDTO,
            @PathVariable("timetableId") UUID timetableId) {
        Timeslot timeslot = timeslotService.updateTimeslot(id, timeslotReqDTO, timetableId);
        log.info("Updated timeslot successfully: {} ", timeslot);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return timeslotConverter.convertEntityToResponseDTO(timeslot);
    }

    @Operation(
            summary = "Delete timeslot by ID",
            description = "Deletes the timeslot identified by the given ID")
    @Parameter(
            name = "timeslotId",
            description = "ID of the timeslot to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Timeslot was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid timeslotId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - Timeslot with the given Id does not exist
                    - Timetable with the given Id does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{timeslotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTimeslot(
            @PathVariable("timeslotId") UUID id, @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling delete request for the timeslot with id {}", id);
        timeslotService.deleteTimeslot(id);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
