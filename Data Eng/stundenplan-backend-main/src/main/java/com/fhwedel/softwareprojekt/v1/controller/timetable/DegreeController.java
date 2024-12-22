package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.DegreeConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.service.DegreeService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
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

/** Controller class for managing degrees, including their associated data. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/degrees")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "degree", description = "Operations about degrees")
public class DegreeController {

    /** The degree service for managing degrees. */
    private final DegreeService degreeService;

    /** The degree converter for converting degree entities to DTOs. */
    private final DegreeConverter degreeConverter;

    /** The scheduler service for scheduling degrees. */
    private final SchedulerService schedulerService;

    @GetMapping
    @Operation(
            summary = "Find all degrees",
            description = "Returns all degrees, or empty list if no degree was found")
    @Parameter(
            name = "timetableId",
            description =
                    "ID of the timetable to return all associated rooms (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    public List<DegreeResDTO> getAllDegrees(@PathVariable("timetableId") UUID timetableId) {
        List<Degree> degrees = degreeService.findAll(timetableId);
        log.info("Found {} degree(s)", degrees.size());
        return degreeConverter.convertEntitiesToResponseDTOList(degrees);
    }

    @Operation(
            summary = "Find degree by ID",
            description = "Returns the degree identified by the given ID")
    @Parameter(
            name = "degreeId",
            description = "ID of the degree to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid degreeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Degree not found",
                        content = @Content)
            })
    @GetMapping(path = "/{degreeId}")
    public DegreeResDTO getSingleDegree(@PathVariable("degreeId") UUID degreeId) {
        Degree degree = degreeService.findByID(degreeId);
        log.info("Found requested degree {}", degree);
        return degreeConverter.convertEntityToResponseDTO(degree);
    }

    @Operation(
            summary = "Create new degree",
            description =
                    "Creates a new degree based on the given request body; returns the created degree with the ID assigned to it")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Degree was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - if a non existing degreeSemester Id was send in semesters list
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PostMapping
    public DegreeResDTO createDegree(
            @Valid @RequestBody DegreeReqDTO degreeReqDTO,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Creating new degree {}", degreeReqDTO);
        Degree degree = degreeService.save(degreeReqDTO, timetableId);
        log.info("Created new degree successfully: {}", degree);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return degreeConverter.convertEntityToResponseDTO(degree);
    }

    @Operation(
            summary = "Update degree",
            description =
                    "Updates the properties of the degree identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated degree")
    @Parameter(
            name = "degreeId",
            description = "ID of the degree to update (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent degree (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid degreeId was supplied
                     - if an invalid request body was supplied
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - Degree with the given ID does not exist
                    - Timetable with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{degreeId}")
    public DegreeResDTO editDegree(
            @Valid @RequestBody DegreeReqDTO degreeReqDTO,
            @PathVariable("degreeId") UUID degreeId,
            @PathVariable("timetableId") UUID timetableId) {
        Degree degree = degreeService.updateDegree(degreeId, degreeReqDTO, timetableId);
        log.info("Updated degree successfully: {}", degree);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return degreeConverter.convertEntityToResponseDTO(degree);
    }

    @Operation(
            summary = "Delete degree by ID",
            description = "Deletes the degree identified by the given ID")
    @Parameter(
            name = "degreeId",
            description = "ID of the degree to delete (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Degree was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid degreeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - Degree with the given Id does not exist
                    - Timetable with the given Id does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{degreeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDegree(
            @PathVariable("degreeId") UUID degreeId,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling delete request for the degree with id {}", degreeId);
        degreeService.deleteDegree(degreeId);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
