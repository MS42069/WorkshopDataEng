package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.RoomConverter;
import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing room-related operations. */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    /** Service for managing room type-related operations. */
    private final RoomTypeService roomTypeService;

    /** Repository for managing room entities. */
    private final RoomRepository roomRepository;

    /** Service for managing timetable-related operations. */
    private final TimetableService timetableService;

    /** Converter for mapping between Room DTOs and entities. */
    private final RoomConverter roomConverter;

    /**
     * Find all rooms associated with a given timetable ID.
     *
     * @param timetableid The ID of the timetable.
     * @return A list of rooms associated with the timetable.
     */
    public List<Room> findAllByTimetableId(UUID timetableid) {
        log.debug("Finding all rooms for timetable {}", timetableid);
        return roomRepository.findByTimetableId(timetableid);
    }

    /**
     * Find a room by its ID.
     *
     * @param id The ID of the room to find.
     * @return The room entity if found, or throw a ResponseStatusException if not found.
     */
    public Room findByID(UUID id) {
        log.debug("Searching for room with id {}", id);
        return roomRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No room entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Room.class,
                                            id));
                        });
    }

    /**
     * Save a new room entity.
     *
     * @param roomReqDto The DTO containing room data to be saved.
     * @param timetableId The ID of the timetable to associate the room with.
     * @return The saved room entity.
     */
    public Room save(RoomReqDTO roomReqDto, UUID timetableId) {

        var room = roomConverter.convertDtoToEntity(roomReqDto);
        room.setRoomType(roomTypeService.findByID(roomReqDto.getRoomType()));
        log.debug("Setting RoomType for Room with id {}", roomReqDto.getRoomType());

        log.debug("Setting Timetable for Room with id {}", timetableId);
        room.setTimetable(timetableService.findByID(timetableId));
        log.debug("Saving {}", room);
        room = roomRepository.save(room);
        log.info("Saved room, assigned id {}", room.getId());
        return room;
    }

    /**
     * Update an existing room entity.
     *
     * @param id The ID of the room to update.
     * @param roomReqDto The DTO containing updated room data.
     * @param timetableId The ID of the timetable associated with the room.
     * @return The updated room entity.
     */
    @Transactional
    public Room updateRoom(UUID id, RoomReqDTO roomReqDto, UUID timetableId) {
        Room room = findByID(id);
        Timetable timetable = timetableService.findByID(timetableId);
        log.debug("Updating {} \n  with values {}", room, roomReqDto);
        room.setAbbreviation(roomReqDto.getAbbreviation());
        room.setName(roomReqDto.getName());
        room.setCapacity(roomReqDto.getCapacity());
        UUID roomTypeId = roomReqDto.getRoomType();

        room.setRoomType(roomTypeService.findByID(roomTypeId));

        room.setIdentifier(roomReqDto.getIdentifier());
        room.setTimetable(timetable);

        return room;
    }

    /**
     * Delete a room by its ID.
     *
     * @param id The ID of the room to delete.
     */
    public void deleteRoom(UUID id) {
        Room room = findByID(id);
        try {
            roomRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", room);
    }
}
