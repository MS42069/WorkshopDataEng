package com.fhwedel.softwareprojekt.v1.controller.types;

import com.fhwedel.softwareprojekt.v1.converter.types.SchoolTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import com.fhwedel.softwareprojekt.v1.service.types.SchoolTypeService;
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

/** REST-Controller for handling school type-related requests. */
@RestController
@RequestMapping("/v1/schoolTypes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "schoolType", description = "everything SchoolType")
public class SchoolTypeController {

    /** Service for managing school types. */
    private final SchoolTypeService schoolTypeService;

    /** Converter for converting between school type entities and DTOs. */
    private final SchoolTypeConverter schoolTypeConverter;

    @Operation(
            summary = "Find all schoolTypes",
            description = "Returns all schoolTypes, or empty list if no schoolType was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<SchoolTypeResDTO> getAllSchoolTypes() {
        List<SchoolType> schoolTypes = schoolTypeService.findAll();
        log.info("Found {} schoolType(s): {}", schoolTypes.size(), schoolTypes);
        return schoolTypeConverter.convertEntitiesToResponseDTOList(schoolTypes);
    }

    @Operation(
            summary = "Find schoolType by ID",
            description = "Returns the schoolType identified by the given ID")
    @Parameter(
            name = "schoolTypeId",
            description = "ID of the schoolType to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid schoolTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "SchoolType not found",
                        content = @Content)
            })
    @GetMapping(path = "/{schoolTypeId}")
    SchoolTypeResDTO getSingleSchoolType(@PathVariable("schoolTypeId") UUID id) {
        SchoolType schoolType = schoolTypeService.findByID(id);
        SchoolTypeResDTO schoolTypeResDTO =
                schoolTypeConverter.convertEntityToResponseDTO(schoolType);
        log.info("Found requested schoolType, returning {}", schoolTypeResDTO);
        return schoolTypeResDTO;
    }

    @Operation(
            summary = "Update schoolType",
            description =
                    "Updates the properties of the schoolType identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated schoolType")
    @Parameter(
            name = "schoolTypeId",
            description = "ID of the schoolType to update (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent schoolType (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid schoolTypeId was supplied
                     - if an invalid request body was supplied
                     - if an update property violates a unique constraint (name already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - SchoolType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{schoolTypeId}")
    SchoolTypeResDTO editSchoolType(
            @PathVariable("schoolTypeId") UUID id,
            @Valid @RequestBody SchoolTypeReqDTO schoolTypeReqDto) {
        SchoolType schoolType = schoolTypeService.updateSchoolType(id, schoolTypeReqDto);
        log.info("Updated schoolType successfully: {} ", schoolType);
        return schoolTypeConverter.convertEntityToResponseDTO(schoolType);
    }

    @Operation(
            summary = "Delete schoolType by ID",
            description = "Deletes the schoolType identified by the given ID")
    @Parameter(
            name = "schoolTypeId",
            description = "ID of the schoolType to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "SchoolType was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid schoolTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - SchoolType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{schoolTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSchoolType(@PathVariable("schoolTypeId") UUID id) {
        schoolTypeService.deleteSchoolType(id);
    }

    @Operation(
            summary = "Create new schoolType",
            description =
                    "Creates a new schoolType based on the given request body; returns the created schoolType with the ID assigned to it")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "SchoolType was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a schoolType property violates a unique constraint (e.g. name already used)
                    """,
                        content = @Content)
            })
    @PostMapping
    SchoolTypeResDTO createSchoolType(@Valid @RequestBody SchoolTypeReqDTO schoolTypeReqDto) {
        SchoolType schoolType = schoolTypeService.save(schoolTypeReqDto);
        log.info("Created new schoolType successfully: {}", schoolType);
        return schoolTypeConverter.convertEntityToResponseDTO(schoolType);
    }
}
