package com.fhwedel.softwareprojekt.v1.controller.employee;

import com.fhwedel.softwareprojekt.v1.converter.WorkTimeConverter;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeResponseDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimetableConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.service.EmployeeService;
import com.fhwedel.softwareprojekt.v1.service.WorkTimeService;
import com.fhwedel.softwareprojekt.v1.service.constraints.TimeslotConstraintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/timetable/{employeeAbbreviation}")
@RequiredArgsConstructor
@Slf4j
public class EmployeeTimetableController {
    private final WorkTimeService workTimeService;
    private final EmployeeService employeeService;

    private final TimeslotConstraintService timeslotConstraintService;

    private final WorkTimeConverter workTimeConverter;

    /**
     * Retrieves the timetable constraints for the specified employee.
     *
     * @param abbreviation The employee's abbreviation.
     * @return An instance of {@link EmployeeTimetableConstraintResDTO} containing the most recent work times
     * and employee timeslot constraints for the employee.
     */

    @GetMapping
    public EmployeeTimetableConstraintResDTO getEmployeeTimetable(
            @PathVariable("employeeAbbreviation") String abbreviation) {
        List<WorkTimeResponseDTO> mostRecentWorkTimesForEmployee =
                workTimeService.getMostRecentWorkTimesForEmployee(
                        employeeService.findByAbbreviation(abbreviation));
        List<EmployeeTimeslotConstraintResDTO> employeeTimeslotConstraints =
                timeslotConstraintService.getEmployeeTimeslotConstraints(abbreviation);
        return new EmployeeTimetableConstraintResDTO(
                mostRecentWorkTimesForEmployee, employeeTimeslotConstraints);
    }


    /**
     * Saves the employee's timeslot constraints.
     *
     * @param abbreviation                The employee's abbreviation.
     * @param employeeTimeslotConstraints A list of employee timeslot constraints to be saved.
     */

    @PostMapping
    public void saveEmployeeTimetable(
            @PathVariable("employeeAbbreviation") String abbreviation,
            @RequestBody List<EmployeeTimeslotConstraintReqDTO> employeeTimeslotConstraints) {
        timeslotConstraintService.handleSave(abbreviation, employeeTimeslotConstraints);
    }
}
