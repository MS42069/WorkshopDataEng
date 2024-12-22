package com.fhwedel.softwareprojekt.v1.controller.types;

import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.service.types.EmployeeTypeService;
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

/** REST-Controller for handling employee type-related requests. */
@RestController
@RequestMapping("/v1/employeeTypes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "employeeType", description = "everything about EmployeeTypes")
public class EmployeeTypeController {
    /** Service for managing employee types. */
    private final EmployeeTypeService employeeTypeService;

    /** Converter for converting between employee type entities and DTOs. */
    private final EmployeeTypeConverter employeeTypeConverter;

    @Operation(
            summary = "Find all employeeTypes",
            description = "Returns all employeeTypes, or empty list if no employeeType was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<EmployeeTypeResDTO> getAllRoomTypes() {
        List<EmployeeType> employeeTypes = employeeTypeService.findAll();
        log.info("Found {} employeeType(s): {}", employeeTypes.size(), employeeTypes);
        return employeeTypeConverter.convertEmployeeTypeEntitiesToResponseDTOList(employeeTypes);
    }

    @Operation(
            summary = "Find employeeType by ID",
            description = "Returns the employeeType identified by the given ID")
    @Parameter(
            name = "employeeTypeId",
            description = "ID of the employeeType to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid employeeTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "EmployeeType not found",
                        content = @Content)
            })
    @GetMapping(path = "/{employeeTypeId}")
    EmployeeTypeResDTO getSingleEmployeeType(@PathVariable("employeeTypeId") UUID id) {
        EmployeeType employeeType = employeeTypeService.findByID(id);
        EmployeeTypeResDTO employeeTypeResDTO =
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType);
        log.info("Found requested employeeType, returning {}", employeeTypeResDTO);
        return employeeTypeResDTO;
    }

    @Operation(
            summary = "Update employeeType",
            description =
                    "Updates the properties of the employeeType identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated employeeType")
    @Parameter(
            name = "employeeTypeId",
            description = "ID of the employeeType to update (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent employeeType (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid employeeTypeId was supplied
                     - if an invalid request body was supplied
                     - if an update property violates a unique constraint (name already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - EmployeeType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{employeeTypeId}")
    EmployeeTypeResDTO editRoomType(
            @PathVariable("employeeTypeId") UUID id,
            @Valid @RequestBody EmployeeTypeReqDTO employeeTypeReqDTO) {
        EmployeeType employeeType = employeeTypeService.updateEmployeeType(id, employeeTypeReqDTO);
        log.info("Updated employeeType successfully: {} ", employeeType);
        return employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType);
    }

    @Operation(
            summary = "Delete employeeType by ID",
            description = "Deletes the employeeType identified by the given ID")
    @Parameter(
            name = "employeeTypeId",
            description = "ID of the employeeType to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "employeeType was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid employeeTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - EmployeeType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{employeeTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployeeType(@PathVariable("employeeTypeId") UUID id) {
        employeeTypeService.deleteEmployeeType(id);
    }

    @Operation(
            summary = "Create new employeeType",
            description =
                    "Creates a new employeeType based on the given request body; returns the created employeeType with the ID assigned to it")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "employeeType was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a employeeType property violates a unique constraint (e.g. name already used)
                    """,
                        content = @Content)
            })
    @PostMapping
    EmployeeTypeResDTO createEmployeeType(
            @Valid @RequestBody EmployeeTypeReqDTO employeeTypeReqDTO) {
        EmployeeType employeeType = employeeTypeService.save(employeeTypeReqDTO);
        log.info("Created new employeeType successfully: {}", employeeType);
        return employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType);
    }
}
