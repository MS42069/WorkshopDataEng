package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeWorkTimesResDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A converter class for converting between Employee DTOs (Data Transfer Objects) and Employee
 * entities.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeReqDTO to an Employee entity.
     *
     * @param employeeReqDto The EmployeeReqDTO to be converted
     * @return The converted Employee entity
     */
    public Employee convertDtoToEntity(EmployeeReqDTO employeeReqDto) {
        Employee employee = modelMapper.map(employeeReqDto, Employee.class);
        log.debug("Converted employeeDto {} to entity {}", employeeReqDto, employee);
        return employee;
    }

    /**
     * Converts an Employee entity to an EmployeeResDTO (response DTO).
     *
     * @param employee The Employee entity to be converted
     * @return The converted EmployeeResDTO
     */
    public EmployeeResDTO convertEntityToResponseDTO(Employee employee) {
        EmployeeResDTO employeeResDTO = modelMapper.map(employee, EmployeeResDTO.class);
        employeeResDTO.setTimetable(employee.getTimetable().getId());
        return employeeResDTO;
    }

    /**
     * Converts a collection of Employee entities to a list of EmployeeResDTOs.
     *
     * @param employees The collection of Employee entities
     * @return A list of EmployeeResDTOs
     */
    public List<EmployeeResDTO> convertEntitiesToResponseDTOList(Collection<Employee> employees) {
        return employees.stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts an Employee entity to an EmployeeWorkTimesResDTO (work time response DTO).
     *
     * @param employee The Employee entity to be converted
     * @return The converted EmployeeWorkTimesResDTO
     */
    public EmployeeWorkTimesResDTO convertEntityToWorkTimeResponseDTO(
            Employee employee, List<EmployeeTimeslotConstraintResDTO> timeslotsContraints) {
        EmployeeWorkTimesResDTO employeeWorkTimesResDTO =
                modelMapper.map(employee, EmployeeWorkTimesResDTO.class);
        employeeWorkTimesResDTO.setTimetable(employee.getTimetable().getId());
        employeeWorkTimesResDTO.setEmployeeTimeslotConstraints(timeslotsContraints);
        return employeeWorkTimesResDTO;
    }
}
