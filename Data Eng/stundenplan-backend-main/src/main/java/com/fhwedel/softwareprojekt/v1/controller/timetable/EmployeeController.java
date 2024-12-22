package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.converter.EmployeeConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeWorkTimesResDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.service.EmployeeService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.constraints.TimeslotConstraintService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for managing employees and their work times. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/employees")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "employee", description = "Everything about employees and their work times")
public class EmployeeController {

    /** The employee service for managing employees. */
    private final EmployeeService employeeService;

    /** The employee converter for converting employee entities to DTOs. */
    private final EmployeeConverter employeeConverter;

    /** The scheduler service for scheduling employees and work times. */
    private final SchedulerService schedulerService;

    /** to get the constraints that the employees have chosen* */
    private final TimeslotConstraintService timeslotConstraintService;

    @Operation(
            summary = "Find all employees",
            description =
                    "Returns all employees (without their work times), or empty list if no employee was found")
    @Parameter(
            name = "timetableId",
            description =
                    "ID of the timetable to return all associated rooms (Identifier generated upon creation)")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping
    public List<EmployeeResDTO> getAllEmployees(@PathVariable("timetableId") UUID id) {
        List<Employee> employees = employeeService.findAll(id);
        log.info("Found {} employee(s)", employees.size());
        return employeeConverter.convertEntitiesToResponseDTOList(employees);
    }

    @Operation(
            summary = "Find employee by ID",
            description =
                    "Returns the employee identified by the given ID "
                            + "including a list of their associated work times")
    @Parameter(name = "employeeId", description = "ID of the employee to return")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description = "Employee not found",
                        content = @Content)
            })
    @GetMapping(path = "/{employeeId}")
    public EmployeeWorkTimesResDTO getSingleEmployee(@PathVariable("employeeId") UUID id) {
        Employee employee = employeeService.findByID(id);
        log.info("Found requested employee {}", employee);
        List<EmployeeTimeslotConstraintResDTO> timeslotConstraints =
                timeslotConstraintService.getEmployeeTimeslotConstraints(
                        employee.getAbbreviation());
        return employeeConverter.convertEntityToWorkTimeResponseDTO(employee, timeslotConstraints);
    }

    @Operation(
            summary = "Create new employee",
            description =
                    "Creates a new employee based on the given request body; "
                            + "also create the work times given in the list and associates them with the employee.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Employee was created successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid employee was supplied
                    - if a property violates a unique constraint (abbreviation not unique)
                    - if the supplied work times list contains invalid work times
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    Not Found
                    - Timetable with given Id does not exist
                    - Employee with given Id does not exist
                    """,
                        content = @Content),
            })
    @PostMapping
    public EmployeeWorkTimesResDTO createEmployee(
            @Valid @RequestBody EmployeeReqDTO employeeReqDTO,
            @PathVariable("timetableId") UUID timetableId) {
        log.info("Creating new employee {}", employeeReqDTO);
        Employee employee = employeeService.save(employeeReqDTO, timetableId);
        log.info("Created new employee successfully: {}", employee);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        List<EmployeeTimeslotConstraintResDTO> timeslotConstraints =
                timeslotConstraintService.getEmployeeTimeslotConstraints(
                        employee.getAbbreviation());
        return employeeConverter.convertEntityToWorkTimeResponseDTO(employee, timeslotConstraints);
    }

    @Operation(
            summary = "Update employee and their working times",
            description =
                    "Updates the employee identified by the given ID and replaces their list of work times. "
                            + "I.d. work times that no longer exist in the update list are deleted, non-existing working times are created and added to the employee.")
    @Parameter(name = "employeeId", description = "ID of the employee to update")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful update"),
                @ApiResponse(
                        responseCode = "400",
                        description =
                                """
                    Bad Request
                    - if an invalid employeeId was supplied
                    - if an invalid employee request object supplied
                    - if an employee property with a unique constraint is not unique
                    - if the supplied work times list contains invalid work times
                    """,
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                            - if the employee was not found or a given weekday or timeslot in the work time list does not exist
                            - Timetable with the given Id does not exist
                            """,
                        content = @Content)
            })
    @PutMapping(path = "/{employeeId}")
    public EmployeeWorkTimesResDTO editEmployee(
            @PathVariable("employeeId") UUID id,
            @Valid @RequestBody EmployeeReqDTO employeeReqDTO,
            @PathVariable("timetableId") UUID timetableId) {

        Employee employee = employeeService.updateEmployee(id, employeeReqDTO, timetableId);
        log.info("Updated employee successfully: {}", employee);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
        List<EmployeeTimeslotConstraintResDTO> timeslotConstraints =
                timeslotConstraintService.getEmployeeTimeslotConstraints(
                        employee.getAbbreviation());
        return employeeConverter.convertEntityToWorkTimeResponseDTO(employee, timeslotConstraints);
    }

    @Operation(
            summary = "Delete employee by ID",
            description =
                    "Deletes the employee identified by the given ID and all work times associated with them")
    @Parameter(name = "employeeId", description = "ID of the employee to delete")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Employee was deleted successfully"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid ID supplied",
                        content = @Content),
                @ApiResponse(
                        responseCode = "404",
                        description =
                                """
                    - Employee with the given Id does not exist
                    - Timetable with the given Id does not exist
                    """,
                        content = @Content)
            })
    @DeleteMapping(path = "/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(
            @PathVariable("employeeId") UUID id, @PathVariable("timetableId") UUID timetableId) {
        employeeService.deleteEmployee(id);
        log.info("Regenerate scheduler for timetable {}", timetableId);
        schedulerService.regenerateScheduler(timetableId);
    }
}
