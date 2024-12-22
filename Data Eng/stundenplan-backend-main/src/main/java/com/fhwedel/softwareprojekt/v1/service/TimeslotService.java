package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.TimeslotConverter;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing timeslots.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TimeslotService {

    /**
     * Repository for timeslots.
     */
    private final TimeslotRepository timeslotRepository;

    /**
     * Service for managing timetables.
     */
    private final TimetableService timetableService;

    /**
     * Converter for timeslot DTOs.
     */
    private final TimeslotConverter timeslotConverter;

    /**
     * Finds all timeslots for a given timetable.
     *
     * @param uuid The ID that identifies the relevant timetable.
     * @return A list of timeslots for the specified timetable.
     */
    public List<Timeslot> findAll(UUID uuid) {
        log.debug("Finding all timeslots for timetable {}", uuid);
        Timetable timetable = timetableService.findByID(uuid);
        return timeslotRepository.findByTimetableId(timetable.getId());
    }

    /**
     * Finds a timeslot by its ID.
     *
     * @param id The ID of the timeslot to find.
     * @return The found timeslot.
     * @throws ResponseStatusException If no timeslot with the given ID is found.
     */
    public Timeslot findByID(UUID id) {
        log.debug("Searching for timeslot with id {}", id);
        return timeslotRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No timeslot entity with id {} was found", id);
                            return new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Timeslot.class,
                                            id));
                        });
    }

    /**
     * Saves a new timeslot for the specified timetable.
     *
     * @param timetableId    The ID that identifies the relevant timetable.
     * @param timeslotReqDTO The DTO containing timeslot information.
     * @return The saved timeslot.
     */
    public Timeslot save(UUID timetableId, TimeslotReqDTO timeslotReqDTO) {
        var timeslot = timeslotConverter.convertDtoToEntity(timeslotReqDTO);
        log.debug("Setting Timetable for Timeslot with id {}", timetableId);
        timeslot.setTimetable(timetableService.findByID(timetableId));
        checkForOverlapping(timeslot);
        log.debug("Saving {}", timeslot);
        timeslot = timeslotRepository.save(timeslot);
        log.info("Saved timeslot, assigned id {}", timeslot.getId());

        return timeslot;
    }

    /**
     * Updates an existing timeslot.
     *
     * @param id             The ID of the timeslot to update.
     * @param timeslotReqDTO The DTO containing timeslot information.
     * @param timetableId    The ID that identifies the relevant timetable.
     * @return The updated timeslot.
     */
    @Transactional
    public Timeslot updateTimeslot(UUID id, TimeslotReqDTO timeslotReqDTO, UUID timetableId) {
        List<Timeslot> timeslots = findAll(timetableId);
        Timeslot timeslot =
                timeslots.stream()
                        .filter(elem -> elem.getId()
                                .equals(id))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
        Timetable timetable = timetableService.findByID(timetableId);
        log.debug("Updating {} \n  with values {}", timeslot, timeslotReqDTO);
        timeslot.setStartTime(timeslotReqDTO.getStartTime());
        timeslot.setEndTime(timeslotReqDTO.getEndTime());
        timeslot.setTimetable(timetable);
        checkForOverlapping(timeslot);
        timeslots.sort(Comparator.comparing(Timeslot::getStartTime));
        for (int i = 0; i < timeslots.size(); i++) {
            Timeslot updatedTimeslot = timeslots.get(i);
            updatedTimeslot.setIndex(Integer.MAX_VALUE - i);
            timeslotRepository.save(updatedTimeslot);
        }
        for (int i = 0; i < timeslots.size(); i++) {
            Timeslot updatedTimeslot = timeslots.get(i);
            updatedTimeslot.setIndex(i);
            timeslotRepository.saveAndFlush(updatedTimeslot);
        }

        return timeslot;
    }

    /**
     * Deletes a timeslot by its ID.
     *
     * @param id The ID of the timeslot to delete.
     */
    public void deleteTimeslot(UUID id) {
        Timeslot timeslot = findByID(id);
        try {
            timeslotRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.info("Deleted {}", timeslot);
    }

    /**
     * Checks that the given timeslot does not overlap with any timeslots in the database and throws
     * an exception if it does.
     *
     * <p>Note: Timeslots may also not be directly adjacent to each other.
     *
     * @param newTimeslot the timeslot in question
     * @throws ResponseStatusException if newTimeslot overlaps with an existing timeslot in the
     *                                 database.
     */
    private void checkForOverlapping(Timeslot newTimeslot) {
        log.debug(
                "Checking if timeslot intervall ({}-{}) overlaps with existing an one...",
                newTimeslot.getStartTime(),
                newTimeslot.getEndTime());
        if (intersectingTimeslotExists(newTimeslot)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "timeslot intervall " + "overlaps with an existing timeslot");
        }
    }

    /**
     * Queries the database to check if any timeslot already exists with which the given timeslot
     * intersects.
     *
     * @param newTimeslot the new timeslot for which an overlap is to be checked
     * @return true, if an overlap with the given timeslot exists, otherwise false.
     */
    private boolean intersectingTimeslotExists(Timeslot newTimeslot) {
        return timeslotRepository.findByTimetableId(newTimeslot.getTimetable()
                        .getId())
                .stream()
                .anyMatch(
                        oldTimeslot -> {
                            if (!oldTimeslot.getId()
                                    .equals(newTimeslot.getId())) {
                                if (areIntersecting(oldTimeslot, newTimeslot)) {
                                    log.warn(
                                            "newTimeslot=({}-{}) overlaps with oldTimeslot=({}-{})",
                                            newTimeslot.getStartTime(),
                                            newTimeslot.getEndTime(),
                                            oldTimeslot.getStartTime(),
                                            oldTimeslot.getEndTime());
                                    return true;
                                }
                            }
                            return false;
                        });
    }

    /**
     * Checks if timeslot A intersects with timeslot B.
     *
     * @param a timeslot A
     * @param b timeslot B
     * @return true, if the timeslot overlap, otherwise false.
     */
    private boolean areIntersecting(Timeslot a, Timeslot b) {
        // timeslots intersect if the start time of each of the two timeslots is before the end time
        // of
        // the other one
        return !a.getStartTime()
                .isAfter(b.getEndTime())
                && !a.getEndTime()
                .isBefore(b.getStartTime());
    }
}
