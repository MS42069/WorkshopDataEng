package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.SpecialEventRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing special events. */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialEventService {

    /** Repository for special events. */
    private final SpecialEventRepository specialEventRepository;

    /**
     * Retrieves a list of special events for the given timetable.
     *
     * @param timetable The timetable for which to retrieve special events.
     * @return A list of SpecialEvent objects belonging to the specified timetable.
     */
    public List<SpecialEvent> findAll(Timetable timetable) {
        return specialEventRepository.findByTimetableId(timetable.getId());
    }

    /**
     * Finds a special event by its unique ID.
     *
     * @param id The UUID of the special event to retrieve.
     * @return The SpecialEvent object with the specified ID.
     * @throws ResponseStatusException if no special event with the given ID is found.
     */
    public SpecialEvent findByID(UUID id) {
        return specialEventRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No SpecialEvent entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            SpecialEvent.class,
                                            id));
                        });
    }

    /**
     * Saves a new special event for the given timetable.
     *
     * @param specialEventReqDTO The SpecialEventReqDTO containing the special event's details.
     * @param timetable The timetable to which the special event belongs.
     * @return The saved SpecialEvent object.
     */
    public SpecialEvent save(SpecialEventReqDTO specialEventReqDTO, Timetable timetable) {
        log.info("Creating new special event for timetable '{}'", timetable.getId());
        var specialEvent = new SpecialEvent();
        specialEvent.setTimetable(timetable);

        specialEvent.setStartDate(specialEventReqDTO.getStartDate());
        specialEvent.setEndDate(specialEventReqDTO.getEndDate());
        specialEvent.setSpecialEventType(specialEventReqDTO.getSpecialEventType());

        specialEvent = specialEventRepository.save(specialEvent);
        log.debug("Saved {}", specialEvent);
        return specialEvent;
    }

    /**
     * Deletes a special event by its unique ID.
     *
     * @param id The UUID of the special event to delete.
     */
    public void deleteSpecialEvent(UUID id) {
        SpecialEvent specialEvent = findByID(id);
        try {
            specialEventRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }

        log.debug("Deleted {}", specialEvent);
    }
}
