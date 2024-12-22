package com.fhwedel.softwareprojekt.v1.service.types;

import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** A service for managing room types. This service provides CRUD operations for room types. */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomTypeService {
    /** The repository for managing room types. */
    private final RoomTypeRepository roomTypeRepository;

    /** The converter for converting between DTOs and entities for room types. */
    private final RoomTypeConverter roomTypeConverter;

    /**
     * Retrieves a list of all room types.
     *
     * @return A list of room types.
     */
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    /**
     * Finds a room type by its ID.
     *
     * @param id The ID of the room type to find.
     * @return The found room type.
     * @throws ResponseStatusException If the room type is not found.
     */
    public RoomType findByID(UUID id) {
        log.debug("Searching for roomType with id {}", id);
        return roomTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No roomType entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            RoomType.class,
                                            id));
                        });
    }

    /**
     * Saves a new room type.
     *
     * @param roomTypeReqDto The DTO containing room type information.
     * @return The saved room type.
     */
    public RoomType save(RoomTypeReqDTO roomTypeReqDto) {
        var roomType = roomTypeConverter.convertRoomTypeDtoToEntity(roomTypeReqDto);
        log.debug("Saving {}", roomType);
        roomType = roomTypeRepository.save(roomType);
        log.info("Saved roomType, assigned id {}", roomType.getId());
        return roomType;
    }

    /**
     * Updates an existing room type.
     *
     * @param id The ID of the room type to update.
     * @param roomTypeReqDto The DTO containing updated room type information.
     * @return The updated room type.
     */
    @Transactional
    public RoomType updateRoomType(UUID id, RoomTypeReqDTO roomTypeReqDto) {
        RoomType roomType = findByID(id);
        log.debug("Updating {} \n  with value {}", roomType, roomTypeReqDto);
        roomType.setName(roomTypeReqDto.getName());
        return roomType;
    }

    /**
     * Deletes a room type by its ID.
     *
     * @param id The ID of the room type to delete.
     */
    public void deleteRoomType(UUID id) {
        RoomType roomType = findByID(id);
        try {
            roomTypeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", roomType);
    }
}
