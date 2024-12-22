package com.fhwedel.softwareprojekt.v1.controller.types;

import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
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

/** REST-Controller for handling room type-related requests. */
@RestController
@RequestMapping("/v1/roomTypes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "roomType", description = "everything RoomType")
public class RoomTypeController {

    /** Service for managing room types. */
    private final RoomTypeService roomTypeService;

    /** Converter for converting between room type entities and DTOs. */
    private final RoomTypeConverter roomTypeConverter;

    @Operation(
            summary = "Find all roomTypes",
            description = "Returns all roomTypes, or empty list if no roomType was found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful fetch")})
    @GetMapping
    public List<RoomTypeResDTO> getAllRoomTypes() {
        List<RoomType> roomTypes = roomTypeService.findAll();
        log.info("Found {} roomType(s): {}", roomTypes.size(), roomTypes);
        return roomTypeConverter.convertRoomTypeEntitiesToResponseDTOList(roomTypes);
    }

    @Operation(
            summary = "Find roomType by ID",
            description = "Returns the roomType identified by the given ID")
    @Parameter(
            name = "roomTypeId",
            description = "ID of the roomType to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid roomTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "RoomType not found",
                        content = @Content)
            })
    @GetMapping(path = "/{roomTypeId}")
    RoomTypeResDTO getSingleRoomType(@PathVariable("roomTypeId") UUID id) {
        RoomType roomType = roomTypeService.findByID(id);
        RoomTypeResDTO roomTypeResDTO =
                roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType);
        log.info("Found requested roomType, returning {}", roomTypeResDTO);
        return roomTypeResDTO;
    }

    @Operation(
            summary = "Update roomType",
            description =
                    "Updates the properties of the roomType identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated roomType")
    @Parameter(
            name = "roomTypeId",
            description = "ID of the roomType to update (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent roomType (by replacing it)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                     Bad Request:
                     - if an invalid roomId was supplied
                     - if an invalid request body was supplied
                     - if an update property violates a unique constraint (name already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - RoomType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{roomTypeId}")
    RoomTypeResDTO editRoomType(
            @PathVariable("roomTypeId") UUID id,
            @Valid @RequestBody RoomTypeReqDTO roomTypeReqDto) {
        RoomType roomType = roomTypeService.updateRoomType(id, roomTypeReqDto);
        log.info("Updated roomType successfully: {} ", roomType);
        return roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType);
    }

    @Operation(
            summary = "Delete roomType by ID",
            description = "Deletes the roomType identified by the given ID")
    @Parameter(
            name = "roomTypeId",
            description = "ID of the roomType to delete (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "RoomType was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid roomTypeId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - RoomType with the given ID does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{roomTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoomType(@PathVariable("roomTypeId") UUID id) {
        roomTypeService.deleteRoomType(id);
    }

    @Operation(
            summary = "Create new roomType",
            description =
                    "Creates a new roomType based on the given request body; returns the created roomType with the ID assigned to it")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "RoomType was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a roomType property violates a unique constraint (e.g. abbreviation already used)
                    """,
                        content = @Content)
            })
    @PostMapping
    RoomTypeResDTO createRoomType(@Valid @RequestBody RoomTypeReqDTO roomTypeReqDto) {
        RoomType roomType = roomTypeService.save(roomTypeReqDto);
        log.info("Created new roomType successfully: {}", roomType);
        return roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType);
    }
}
