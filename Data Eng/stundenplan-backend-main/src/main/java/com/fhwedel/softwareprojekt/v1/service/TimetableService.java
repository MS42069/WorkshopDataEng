package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.TimetableConverter;
import com.fhwedel.softwareprojekt.v1.dto.SpecialEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimetableReqDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.*;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing timetables. */
@Service
@RequiredArgsConstructor
@Slf4j
public class TimetableService {

    /** Repository for timetables. */
    private final TimetableRepository timetableRepository;

    /** Service for managing semester types. */
    private final SemesterTypeService semesterTypeService;

    /** Converter for timetables. */
    private final TimetableConverter timetableConverter;

    /** Service for managing special events. */
    private final SpecialEventService specialEventService;

    /** Repository for timeslots. */
    private final TimeslotRepository timeslotRepository;

    /** Repository for courses. */
    private final CourseRepository courseRepository;

    /** Repository for degrees. */
    private final DegreeRepository degreeRepository;

    /** Repository for employees. */
    private final EmployeeRepository employeeRepository;

    /** Repository for rooms. */
    private final RoomRepository roomRepository;

    /** Repository for week events. */
    private final WeekEventRepository weekEventRepository;

    /**
     * Finds all timetables.
     *
     * @return A list of all timetables.
     */
    public List<Timetable> findAll() {
        return timetableRepository.findAll();
    }

    /**
     * Finds a timetable by its ID.
     *
     * @param id The ID of the timetable to find.
     * @return The found timetable.
     * @throws ResponseStatusException If no timetable with the given ID is found.
     */
    public Timetable findByID(UUID id) {
        log.debug("Searching for timetable with id {}", id);
        return timetableRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No timetable entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Timetable.class,
                                            id));
                        });
    }

    /**
     * Saves a new timetable.
     *
     * @param timetableReqDTO The DTO containing timetable information.
     * @return The saved timetable.
     */
    @Transactional
    public Timetable save(TimetableReqDTO timetableReqDTO) {
        var timetable = timetableConverter.convertDtoToEntity(timetableReqDTO);

        UUID semesterTypeId = timetableReqDTO.getSemesterType();
        timetable.setSemesterType(semesterTypeService.findByID(semesterTypeId));

        timetable = timetableRepository.save(timetable);
        log.debug("Saved timetable {}", timetable);

        List<SpecialEvent> specialEvents = new ArrayList<>();
        for (SpecialEventReqDTO specialEventReqDTO : timetableReqDTO.getSpecialEvents()) {
            log.debug("Creating new special event with {}", specialEventReqDTO);
            specialEvents.add(specialEventService.save(specialEventReqDTO, timetable));
        }
        timetable.setSpecialEvents(specialEvents);
        log.info("Saved timetable and their special events {}", timetable);

        log.debug("Creating Timeslots from 08:00 to 18:15 for timetable {}", timetable);
        Timeslot oldTimeslot = new Timeslot();
        oldTimeslot.setTimetable(timetable);
        oldTimeslot.setStartTime(LocalTime.of(8, 0));
        oldTimeslot.setEndTime(LocalTime.of(9, 15));
        oldTimeslot.setIndex(0);
        timeslotRepository.saveAndFlush(oldTimeslot);
        Timeslot newTimeslot;
        for (int i = 1; i < 7; i++) {
            newTimeslot = new Timeslot();
            newTimeslot.setTimetable(oldTimeslot.getTimetable());
            newTimeslot.setStartTime(oldTimeslot.getEndTime().plusMinutes(15));
            newTimeslot.setEndTime(newTimeslot.getStartTime().plusMinutes(75));
            newTimeslot.setIndex(i);
            timeslotRepository.saveAndFlush(newTimeslot);
            oldTimeslot = newTimeslot;
        }
        log.info("Created Timeslots from 08:00 to 18:15 for timetable {}", timetable);
        return timetable;
    }

    /**
     * Updates an existing timetable.
     *
     * @param id The ID of the timetable to update.
     * @param timetableReqDTO The DTO containing timetable information.
     * @return The updated timetable.
     */
    @Transactional
    public Timetable updateTimetable(UUID id, TimetableReqDTO timetableReqDTO) {
        Timetable timetable = findByID(id);
        log.debug("Updating {} \n  with values {}", timetable, timetableReqDTO);
        List<SpecialEvent> specialEvents = new ArrayList<>();

        // delete old special events
        for (SpecialEvent specialEvent : timetable.getSpecialEvents()) {
            log.debug("Delete old specialEvents of the timetable to update");
            specialEventService.deleteSpecialEvent(specialEvent.getId());
        }

        // create + add new special events
        for (SpecialEventReqDTO specialEventReqDTO : timetableReqDTO.getSpecialEvents()) {
            log.debug("Create and add new specialEvents");
            SpecialEvent specialEvent = specialEventService.save(specialEventReqDTO, timetable);
            specialEvents.add(specialEvent);
        }
        timetable.setSpecialEvents(specialEvents);
        timetable.setName(timetableReqDTO.getName());
        timetable.setStartDate(timetableReqDTO.getStartDate());
        timetable.setEndDate(timetableReqDTO.getEndDate());
        timetable.setNumberOfWeeks(timetableReqDTO.getNumberOfWeeks());
        UUID semesterTypeId = timetableReqDTO.getSemesterType();
        timetable.setSemesterType(semesterTypeService.findByID(semesterTypeId));

        log.info("Updated timetable {}", timetable);

        return timetable;
    }

    /**
     * Deletes a timetable by its ID.
     *
     * @param id The ID of the timetable to delete.
     */
    @Transactional
    public void deleteTimetable(UUID id) {
        Timetable timetable = findByID(id);
        try {
            weekEventRepository.deleteByTimetableId(timetable.getId());
            degreeRepository.deleteByTimetableId(timetable.getId());
            courseRepository.deleteByTimetableId(timetable.getId());
            employeeRepository.deleteByTimetableId(timetable.getId());
            roomRepository.deleteByTimetableId(timetable.getId());
            timeslotRepository.deleteByTimetableId(timetable.getId());
            timetableRepository.deleteById(id);
            log.info("Deleted {}", timetable);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
    }
}
