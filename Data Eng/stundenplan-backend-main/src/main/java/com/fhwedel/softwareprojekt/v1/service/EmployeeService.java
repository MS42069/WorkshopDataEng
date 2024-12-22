package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.EmployeeConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIErrorUtils;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.EmployeeTypeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeTypeService employeeTypeService;
    private final EmployeeConverter employeeConverter;

    private final WorkTimeService workTimeService;

    private final TimetableService timetableService;

    public List<Employee> findAll(UUID id) {
        log.debug("Finding all employees for timetable {}", id);
        Timetable timetable = timetableService.findByID(id);
        return employeeRepository.findByTimetable(timetable);
    }

    public Employee findByID(UUID id) {
        log.debug("Searching for employee with id {}", id);
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No employee entity with id {} was found", id);
                            return new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            Employee.class,
                                            id));
                        });
    }

    public List<Employee> findByAbbreviation(String abbreviation) {
        log.debug("Searching for employee with abbreviation {}", abbreviation);
        return employeeRepository.findByAbbreviation(abbreviation);
    }

    public Optional<Employee> findFirstByAbbreviation(String abbreviation) {
        log.debug("Searching for employee with abbreviation {}", abbreviation);
        return employeeRepository.findFirstByAbbreviation(abbreviation);
    }

    @Transactional
    public Employee save(EmployeeReqDTO employeeReqDto, UUID timetableId) {
        var employee = employeeConverter.convertDtoToEntity(employeeReqDto);

        UUID employeeTypeId = employeeReqDto.getEmployeeType();
        employee.setEmployeeType(employeeTypeService.findByID(employeeTypeId));

        log.debug("Setting Timetable for Employee with id {}", timetableId);
        employee.setTimetable(timetableService.findByID(timetableId));

        employee = employeeRepository.save(employee);
        log.debug("Saved employee {}", employee);

        List<WorkTime> workTimes = new ArrayList<>();
        for (WorkTimeDTO workTimeDTO : employeeReqDto.getWorkTimes()) {
            log.debug("Creating new work time with {}", workTimeDTO);
            workTimes.add(workTimeService.save(workTimeDTO, employee));
        }
        employee.setWorkTimes(workTimes);
        log.info("Saved employee and their work times {}", employee);

        return employee;
    }

    @Transactional
    public Employee updateEmployee(UUID id, EmployeeReqDTO employeeReqDto, UUID timetableId) {
        Employee employee = findByID(id);
        Timetable timetable = timetableService.findByID(timetableId);
        log.debug("Updating {} \n  with values {}", employee, employeeReqDto);

        UUID employeeTypeId = employeeReqDto.getEmployeeType();
        employee.setEmployeeType(employeeTypeService.findByID(employeeTypeId));

        List<WorkTime> workTimes = new ArrayList<>();

        // delete old worktimes
        for (WorkTime workTime : employee.getWorkTimes()) {
            log.debug("Delete old WorkTime of the employee to update");
            workTimeService.deleteWorkTime(workTime.getId());
        }

        // add new worktimes
        for (WorkTimeDTO workTimeDTO : employeeReqDto.getWorkTimes()) {
            log.debug("Create and add new WorkTime");
            WorkTime workTime = workTimeService.save(workTimeDTO, employee);
            workTimes.add(workTime);
        }
        employee.setWorkTimes(workTimes);
        employee.setAbbreviation(employeeReqDto.getAbbreviation());
        employee.setFirstname(employeeReqDto.getFirstname());
        employee.setLastname(employeeReqDto.getLastname());
        employee.setTimetable(timetable);

        log.info("Updated employee {}", employee);

        return employee;
    }

    public void deleteEmployee(UUID id) {
        Employee employee = findByID(id);
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            APIErrorUtils.handleConstraintViolation(id.toString());
        }

        log.info("Deleted {}", employee);
    }
}
