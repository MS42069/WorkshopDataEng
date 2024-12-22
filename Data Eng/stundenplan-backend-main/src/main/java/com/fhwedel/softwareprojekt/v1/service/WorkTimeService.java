package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.WorkTimeConverter;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeResponseDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Service class for managing work times. */
@Service
@RequiredArgsConstructor
@Slf4j
public class WorkTimeService {

    /** The repository for accessing work time data. */
    private final WorkTimeRepository workTimeRepository;

    /** Converter for work time DTOs. */
    private final WorkTimeConverter workTimeConverter;

    /** Service for managing timeslots. */
    private final TimeslotService timeslotService;

    /**
     * Find all work times.
     *
     * @return A list of all work times.
     */
    public List<WorkTime> findAll() {
        return workTimeRepository.findAll();
    }

    /**
     * Find a work time by its ID.
     *
     * @param id The ID of the work time to find.
     * @return The work time object.
     * @throws ResponseStatusException If no work time with the given ID is found.
     */
    public WorkTime findByID(UUID id) {
        return workTimeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No WorkTime entity with id {} was found", id);
                            return new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            WorkTime.class,
                                            id));
                        });
    }

    /**
     * Save a new work time for an employee.
     *
     * @param workTimeDTO The DTO containing work time data.
     * @param employee The employee for whom the work time is being saved.
     * @return The saved work time object.
     */
    public WorkTime save(WorkTimeDTO workTimeDTO, Employee employee) {
        log.info("Creating new work time for employee '{}'", employee.getId());
        var workTime = new WorkTime();
        workTime.setEmployee(employee);

        Timeslot timeslot = timeslotService.findByID(workTimeDTO.getTimeslotID());

        workTime.setTimeslot(timeslot);
        workTime.setWeekday(workTimeDTO.getWeekday());

        workTime = workTimeRepository.save(workTime);
        log.debug("Saved {}", workTime);
        return workTime;
    }

    /**
     * Delete a work time by its ID.
     *
     * @param id The ID of the work time to delete.
     */
    public void deleteWorkTime(UUID id) {
        WorkTime workTime = findByID(id);
        try {
            workTimeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }
        log.debug("Deleted {}", workTime);
    }

    /**
     * Get the most recent work times for a list of employees.
     *
     * @param employees The list of employees.
     * @return A list of the most recent work times for the employees.
     */
    public List<WorkTimeResponseDTO> getMostRecentWorkTimesForEmployee(List<Employee> employees) {

        List<WorkTime> workTimes =
                findAll().stream()
                        .filter(workTime -> employees.contains(workTime.getEmployee()))
                        .toList();

        Optional<LocalDate> highestStartDate =
                workTimes.stream()
                        .map(workTime -> workTime.getTimeslot().getTimetable().getStartDate())
                        .max(Comparator.naturalOrder());

        if (highestStartDate.isPresent()) {
            LocalDate maxStartDate = highestStartDate.get();

            List<WorkTime> workTimesForEmployee =
                    workTimes.stream()
                            .distinct()
                            .filter(
                                    workTime ->
                                            workTime.getTimeslot()
                                                    .getTimetable()
                                                    .getStartDate()
                                                    .equals(maxStartDate))
                            .toList();

            return workTimeConverter.convertEntitiesToResponseDTOList(workTimesForEmployee);
        }
        return Collections.emptyList();
    }
}
