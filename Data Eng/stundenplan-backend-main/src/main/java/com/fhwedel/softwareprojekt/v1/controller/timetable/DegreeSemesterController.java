package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.DegreeSemesterConverter;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterResDTO;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.service.DegreeSemesterService;
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

/** Controller class for managing degree semesters, including their associated data. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/semesters")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "semester", description = "Operations about semesters")
public class DegreeSemesterController {

    /** The degree semester service for managing degree semesters. */
    private final DegreeSemesterService semesterService;

    /** The degree semester converter for converting degree semester entities to DTOs. */
    private final DegreeSemesterConverter semesterConverter;

    /** The scheduler service for scheduling degree semesters. */
    private final SchedulerService schedulerService;

    @GetMapping
    @Operation(
            summary = "Find all semesters",
            description = "Returns all semesters, or empty list if no semester was found")
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
    public List<DegreeSemesterResDTO> getAllSemesters(@PathVariable("timetableId") UUID id) {
        List<DegreeSemester> semesters = semesterService.findAll(id);
        log.info("Found {} semester(s)", semesters.size());
        return semesterConverter.convertEntitiesToResponseDTOList(semesters);
    }

    @Operation(
            summary = "Find semester by ID",
            description = "Returns the semester identified by the given ID")
    @Parameter(
            name = "semesterID",
            description = "ID of the semester to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid degreeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Semester not found",
                        content = @Content)
            })
    @GetMapping(path = "/{semesterID}")
    public DegreeSemesterResDTO getSingleSemester(@PathVariable("semesterID") UUID semesterID) {
        DegreeSemester semester = semesterService.findByID(semesterID);
        log.info("Found requested semester {}", semester);
        return semesterConverter.convertEntityToResponseDTO(semester);
    }

    @Operation(
            summary = "Create new semester",
            description =
                    "Creates a new semester based on the given request body; returns the created semester with the ID assigned to it")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Semester was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - if a non existing degree Id was send in degrees list
                    - if a non existing course Id was send in courses list
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PostMapping
    public DegreeSemesterResDTO createSemester(
            @Valid @RequestBody DegreeSemesterReqDTO semesterReqDTO,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Creating new semester {}", semesterReqDTO);
        DegreeSemester semester = semesterService.save(semesterReqDTO, timetableId);
        log.info("Created new semester successfully: {}", semester);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return semesterConverter.convertEntityToResponseDTO(semester);
    }

    @Operation(
            summary = "Update semester",
            description =
                    "Updates the properties of the semester identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated semester")
    @Parameter(
            name = "semesterID",
            description = "ID of the semester to update (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent semester (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid semesterID was supplied
                     - if an invalid request body was supplied
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - Semester with the given ID does not exist
                    - Timetable with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{semesterID}")
    public DegreeSemesterResDTO updateSemester(
            @Valid @RequestBody DegreeSemesterReqDTO semesterReqDTO,
            @PathVariable("semesterID") UUID semesterID,
            @PathVariable("timetableId") UUID timetableId) {
        DegreeSemester semester =
                semesterService.updateSemester(semesterID, semesterReqDTO, timetableId);
        log.info("Updated semester successfully: {}", semester);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return semesterConverter.convertEntityToResponseDTO(semester);
    }

    @Operation(
            summary = "Delete semester by ID",
            description = "Deletes the semester identified by the given ID")
    @Parameter(
            name = "semesterID",
            description = "ID of the semester to delete (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Semester was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid semesterID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - Semester with the given Id does not exist
                    - Timetable with the given Id does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{semesterID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSemester(
            @PathVariable("semesterID") UUID semesterID,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling delete request for the semester with id {}", semesterID);
        semesterService.deleteSemester(semesterID);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
