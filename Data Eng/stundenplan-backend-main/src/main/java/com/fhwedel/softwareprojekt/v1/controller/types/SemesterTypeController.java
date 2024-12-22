package com.fhwedel.softwareprojekt.v1.controller.types;

import com.fhwedel.softwareprojekt.v1.converter.types.SemesterTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
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

/** REST-Controller for handling semester type-related requests. */
@RestController
@RequestMapping("/v1/semesterTypes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "roomType", description = "everything about SemesterType")
public class SemesterTypeController {

    /** Service for managing semester types. */
    private final SemesterTypeService semesterTypeService;

    /** Converter for converting between semester type entities and DTOs. */
    private final SemesterTypeConverter semesterTypeConverter;

    @Operation(
            summary = "Find all semesterTypes",
            description = "Returns all semesterTypes, or empty list if no semesterType was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<SemesterTypeResDTO> getAllSemesterTypes() {
        List<SemesterType> semesterTypes = semesterTypeService.findAll();
        log.info("Found {} semesterType(s): {}", semesterTypes.size(), semesterTypes);
        return semesterTypeConverter.convertSemesterTypeEntitiesToResponseDTOList(semesterTypes);
    }

    @Operation(
            summary = "Find semesterType by ID",
            description = "Returns the semesterType identified by the given ID")
    @Parameter(
            name = "semesterTypeId",
            description = "ID of the semesterType to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid semesterTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "SemesterType not found",
                        content = @Content)
            })
    @GetMapping(path = "/{semesterTypeId}")
    SemesterTypeResDTO getSingleSemesterType(@PathVariable("semesterTypeId") UUID id) {
        SemesterType semesterType = semesterTypeService.findByID(id);
        SemesterTypeResDTO semesterTypeResDTO =
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType);
        log.info("Found requested semesterType, returning {}", semesterTypeResDTO);
        return semesterTypeResDTO;
    }

    @Operation(
            summary = "Update semesterType",
            description =
                    "Updates the properties of the semesterType identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated semesterType")
    @Parameter(
            name = "semesterTypeId",
            description = "ID of the semesterType to update (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent semesterType (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid request body was supplied
                     - if an update property violates a unique constraint (name already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - SemesterType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{semesterTypeId}")
    SemesterTypeResDTO editSemesterType(
            @PathVariable("semesterTypeId") UUID id,
            @Valid @RequestBody SemesterTypeReqDTO semesterTypeReqDTO) {
        SemesterType semesterType = semesterTypeService.updateSemesterType(id, semesterTypeReqDTO);
        log.info("Updated semesterType successfully: {} ", semesterType);
        return semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType);
    }

    @Operation(
            summary = "Delete semesterType by ID",
            description = "Deletes the semesterType identified by the given ID")
    @Parameter(
            name = "semesterTypeId",
            description = "ID of the semesterType to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "semesterType was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid semesterTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - SemesterType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{semesterTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSemesterType(@PathVariable("semesterTypeId") UUID id) {
        semesterTypeService.deleteSemesterType(id);
    }

    @Operation(
            summary = "Create new semesterType",
            description =
                    "Creates a new semesterType based on the given request body; returns the created semesterType with the ID assigned to it")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "SemesterType was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a semesterType property violates a unique constraint (e.g. name already used)
                    """,
                        content = @Content)
            })
    @PostMapping
    SemesterTypeResDTO createSemesterType(
            @Valid @RequestBody SemesterTypeReqDTO semesterTypeReqDto) {
        SemesterType semesterType = semesterTypeService.save(semesterTypeReqDto);
        log.info("Created new semesterType successfully: {}", semesterType);
        return semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType);
    }
}
