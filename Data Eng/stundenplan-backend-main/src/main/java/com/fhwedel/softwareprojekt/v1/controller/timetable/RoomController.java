package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.RoomConverter;
import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.RoomResDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.service.RoomService;
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

/** REST-Controller for handling room-related requests. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/rooms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "room", description = "Operations about rooms")
public class RoomController {

    /** Service for managing rooms. */
    private final RoomService roomService;

    /** Converter for converting room entities to DTOs. */
    private final RoomConverter roomConverter;

    /** Service for managing the scheduler. */
    private final SchedulerService schedulerService;

    @Operation(
            summary = "Find all rooms",
            description = "Returns all rooms, or empty list if no room was found")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping
    List<RoomResDTO> getAllRooms(@PathVariable("timetableId") UUID id) {
        List<Room> rooms = roomService.findAllByTimetableId(id);
        log.info("Found {} room(s): {}", rooms.size(), rooms);
        return roomConverter.convertEntitiesToResponseDTOList(rooms);
    }

    @Operation(
            summary = "Find room by ID",
            description = "Returns the room identified by the given ID")
    @Parameter(
            name = "roomId",
            description = "ID of the room to return (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid roomId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Room not found",
                        content = @Content)
            })
    @GetMapping(path = "/{roomId}")
    RoomResDTO getSingleRoom(@PathVariable("roomId") UUID id) {
        Room room = roomService.findByID(id);
        RoomResDTO roomResDTO = roomConverter.convertEntityToResponseDTO(room);
        log.info("Found requested room, returning {}", roomResDTO);
        return roomResDTO;
    }

    @Operation(
            summary = "Create new room",
            description =
                    "Creates a new room based on the given request body; returns the created room with the ID assigned to it")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Room was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid request body was supplied (validation fail)
                    - or if a room property violates a unique constraint (e.g. abbreviation already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable with the given ID does not exist",
                        content = @Content)
            })
    @PostMapping
    RoomResDTO createRoom(
            @Valid @RequestBody RoomReqDTO roomReqDto,
            @PathVariable("timetableId") UUID timetableId) {
        Room room = roomService.save(roomReqDto, timetableId);
        log.info("Created new room successfully: {}", room);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return roomConverter.convertEntityToResponseDTO(room);
    }

    @Operation(
            summary = "Update room",
            description =
                    "Updates the properties of the room identified by the given ID to the properties in the submitted request body;"
                            + " returns the updated room")
    @Parameter(
            name = "roomId",
            description = "ID of the room to update (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updates an existent room (by replacing it)")
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
                     - if an update property violates a unique constraint (e.g. abbreviation already used)
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    				Not Found
                    - Room with the given ID does not exist
                    - Timetable with the given ID does not exist
                    """,
                        content = @Content)
            })
    @PatchMapping(path = "/{roomId}")
    RoomResDTO editRoom(
            @PathVariable("roomId") UUID id,
            @Valid @RequestBody RoomReqDTO roomReqDto,
            @PathVariable("timetableId") UUID timetableId) {
        Room room = roomService.updateRoom(id, roomReqDto, timetableId);
        log.info("Updated room successfully: {} ", room);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        return roomConverter.convertEntityToResponseDTO(room);
    }

    @Operation(
            summary = "Delete room by ID",
            description = "Deletes the room identified by the given ID")
    @Parameter(
            name = "roomId",
            description = "ID of the room to delete (Identifier generated upon creation)")
    @Parameter(
            name = "timetableId",
            description = "ID of the timetable (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Room was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid roomId supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - Room with the given ID does not exist
                    - Timetable with the given Id does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoom(
            @PathVariable("roomId") UUID id, @PathVariable("timetableId") UUID timetableId) {
        log.info("Handling delete request for the room with id {}", id);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        roomService.deleteRoom(id);
    }
}
