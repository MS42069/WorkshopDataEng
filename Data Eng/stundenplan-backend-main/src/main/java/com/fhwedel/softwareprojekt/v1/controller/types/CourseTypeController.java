package com.fhwedel.softwareprojekt.v1.controller.types;

import com.fhwedel.softwareprojekt.v1.converter.types.CourseTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import com.fhwedel.softwareprojekt.v1.service.types.CourseTypeService;
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

/** REST-Controller for handling course type-related requests. */
@RestController
@RequestMapping("/v1/courseTypes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "courseType", description = "everything CourseType")
public class CourseTypeController {

    /** Service for managing course types. */
    private final CourseTypeService courseTypeService;

    /** Converter for converting between course type entities and DTOs. */
    private final CourseTypeConverter courseTypeConverter;

    @Operation(
            summary = "Find all courseTypes",
            description = "Returns all courseTypes, or empty list if no courseType was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<CourseTypeResDTO> getAllCourseTypes() {
        List<CourseType> courseTypes = courseTypeService.findAll();
        log.info("Found {} courseType(s): {}", courseTypes.size(), courseTypes);
        return courseTypeConverter.convertEntitiesToResponseDTOList(courseTypes);
    }

    @Operation(
            summary = "Find courseType by ID",
            description = "Returns the courseType identified by the given ID")
    @Parameter(
            name = "courseTypeId",
            description = "ID of the courseType to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid courseTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "CourseType not found",
                        content = @Content)
            })
    @GetMapping(path = "/{courseTypeId}")
    CourseTypeResDTO getSingleCourseType(@PathVariable("courseTypeId") UUID id) {
        CourseType courseType = courseTypeService.findByID(id);
        CourseTypeResDTO courseTypeResDTO =
                courseTypeConverter.convertEntityToResponseDTO(courseType);
        log.info("Found requested courseType, returning {}", courseTypeResDTO);
        return courseTypeResDTO;
    }

    @Operation(
            summary = "Update courseType",
            description =
                    "Updates the properties of the courseType identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated courseType")
    @Parameter(
            name = "courseTypeId",
            description = "ID of the courseType to update (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent courseType (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid courseTypeId was supplied
                     - if an invalid request body was supplied
                     - if an update property violates a unique constraint (name already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - CourseType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{courseTypeId}")
    CourseTypeResDTO editCourseType(
            @PathVariable("courseTypeId") UUID id,
            @Valid @RequestBody CourseTypeReqDTO courseTypeReqDto) {
        CourseType courseType = courseTypeService.updateCourseType(id, courseTypeReqDto);
        log.info("Updated courseType successfully: {} ", courseType);
        return courseTypeConverter.convertEntityToResponseDTO(courseType);
    }

    @Operation(
            summary = "Delete courseType by ID",
            description = "Deletes the courseType identified by the given ID")
    @Parameter(
            name = "courseTypeId",
            description = "ID of the courseType to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "CourseType was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid courseTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                      - CourseType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{courseTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCourseType(@PathVariable("courseTypeId") UUID id) {
        courseTypeService.deleteCourseType(id);
    }

    @Operation(
            summary = "Create new courseType",
            description =
                    "Creates a new courseType based on the given request body; returns the created courseType with the ID assigned to it")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "CourseType was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a courseType property violates a unique constraint (e.g. name already used)
                    """,
                        content = @Content)
            })
    @PostMapping
    CourseTypeResDTO createCourseType(@Valid @RequestBody CourseTypeReqDTO courseTypeReqDto) {
        CourseType courseType = courseTypeService.save(courseTypeReqDto);
        log.info("Created new courseType successfully: {}", courseType);
        return courseTypeConverter.convertEntityToResponseDTO(courseType);
    }
}
