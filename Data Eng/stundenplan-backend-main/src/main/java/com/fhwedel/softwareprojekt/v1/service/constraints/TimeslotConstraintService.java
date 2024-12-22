package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.TimeslotConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeCheckTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintType;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeTimeslotConstraintRepository;
import com.fhwedel.softwareprojekt.v1.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeslotConstraintService {
    private final EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository;
    private final EmployeeService employeeService;
    private final TimeslotConstraintConverter timeslotConstraintConverter;

    public List<EmployeeTimeslotConstraintResDTO> getEmployeeTimeslotConstraints(
            String abbreviation) {
        return timeslotConstraintConverter.convertToDTOList(
                employeeTimeslotConstraintRepository.findByEmployeeAbbreviation(abbreviation));
    }

    public void handleSave(
            String abbreviation,
            List<EmployeeTimeslotConstraintReqDTO> employeeTimeslotConstraintDTOs) {
        if (abbreviation != null && employeeTimeslotConstraintDTOs != null) {
            employeeTimeslotConstraintDTOs.forEach(elem -> handleSave(abbreviation, elem));
        }
    }

    private void handleSave(
            String abbreviation, EmployeeTimeslotConstraintReqDTO employeeTimeslotConstraintDTO) {
        if (employeeTimeslotConstraintDTO != null
                && employeeTimeslotConstraintDTO.getConstraintType() != null
                && employeeTimeslotConstraintDTO.getConstraintValue() != null) {
            saveOrUpdate(abbreviation, employeeTimeslotConstraintDTO);
        } else {
            delete(abbreviation, employeeTimeslotConstraintDTO);
        }
    }

    private void saveOrUpdate(
            String abbreviation, EmployeeTimeslotConstraintReqDTO employeeTimeslotConstraintDTO) {

        EmployeeTimeslotConstraint employeeTimeslotConstraint =
                employeeTimeslotConstraintRepository
                        .findByEmployeeAbbreviationAndTimeslotIndexAndStartTimeAndEndTimeAndWeekday(
                                abbreviation,
                                employeeTimeslotConstraintDTO.getTimeslotIndex(),
                                employeeTimeslotConstraintDTO.getStartTime(),
                                employeeTimeslotConstraintDTO.getEndTime(),
                                employeeTimeslotConstraintDTO.getWeekday())
                        .orElseGet(EmployeeTimeslotConstraint::new);

        ConstraintType prevType = employeeTimeslotConstraint.getConstraintType();
        ConstraintType newType = employeeTimeslotConstraintDTO.getConstraintType();
        Boolean wasAccepted = employeeTimeslotConstraint.getIsAccepted();

        boolean isAccepted = false;

        if (prevType == ConstraintType.HARD) {
            isAccepted = newType.equals(ConstraintType.HARD) ? wasAccepted : false;
        } else {
            isAccepted = !newType.equals(ConstraintType.HARD);
        }
        employeeTimeslotConstraint.setIsAccepted(isAccepted);

        employeeTimeslotConstraint.setConstraintType(
                employeeTimeslotConstraintDTO.getConstraintType());
        employeeTimeslotConstraint.setConstraintValue(
                employeeTimeslotConstraintDTO.getConstraintValue());
        employeeTimeslotConstraint.setEmployeeAbbreviation(abbreviation);
        employeeTimeslotConstraint.setEndTime(employeeTimeslotConstraintDTO.getEndTime());
        employeeTimeslotConstraint.setStartTime(employeeTimeslotConstraintDTO.getStartTime());
        employeeTimeslotConstraint.setTimeslotIndex(
                employeeTimeslotConstraintDTO.getTimeslotIndex());
        employeeTimeslotConstraint.setReason(employeeTimeslotConstraintDTO.getReason());
        employeeTimeslotConstraint.setWeekday(employeeTimeslotConstraintDTO.getWeekday());
        employeeTimeslotConstraintRepository.save(employeeTimeslotConstraint);
    }

    private void delete(
            String abbreviation, EmployeeTimeslotConstraintReqDTO employeeTimeslotConstraintDTO) {
        if (employeeTimeslotConstraintDTO != null
                && employeeTimeslotConstraintDTO.getTimeslotIndex() != null
                && employeeTimeslotConstraintDTO.getWeekday() != null) {
            employeeTimeslotConstraintRepository
                    .findByEmployeeAbbreviationAndTimeslotIndexAndStartTimeAndEndTimeAndWeekday(
                            abbreviation,
                            employeeTimeslotConstraintDTO.getTimeslotIndex(),
                            employeeTimeslotConstraintDTO.getStartTime(),
                            employeeTimeslotConstraintDTO.getEndTime(),
                            employeeTimeslotConstraintDTO.getWeekday())
                    .ifPresent(employeeTimeslotConstraintRepository::delete);
        }
    }

    public List<EmployeeCheckTimeslotConstraintResDTO> getPendingConstraints() {
        return employeeTimeslotConstraintRepository
                .findByConstraintType(ConstraintType.HARD)
                .stream()
                .filter(elem -> !elem.getIsAccepted())
                .map(
                        elem -> {
                            Optional<Employee> employee =
                                    employeeService.findFirstByAbbreviation(
                                            elem.getEmployeeAbbreviation());
                            EmployeeCheckTimeslotConstraintResDTO
                                    employeeCheckTimeslotConstraintResDTO =
                                            new EmployeeCheckTimeslotConstraintResDTO();
                            employeeCheckTimeslotConstraintResDTO.setId(elem.getId());
                            employeeCheckTimeslotConstraintResDTO.setAbbreviation(
                                    elem.getEmployeeAbbreviation());
                            employee.ifPresent(
                                    emp -> {
                                        employeeCheckTimeslotConstraintResDTO.setFirstname(
                                                emp.getFirstname());
                                        employeeCheckTimeslotConstraintResDTO.setLastname(
                                                emp.getLastname());
                                    });
                            employeeCheckTimeslotConstraintResDTO.setWeekDay(elem.getWeekday());
                            employeeCheckTimeslotConstraintResDTO.setStartTime(elem.getStartTime());
                            employeeCheckTimeslotConstraintResDTO.setEndTime(elem.getEndTime());
                            employeeCheckTimeslotConstraintResDTO.setReason(elem.getReason());
                            return employeeCheckTimeslotConstraintResDTO;
                        })
                .collect(Collectors.toList());
    }

    public void handleAcceptOrDecline(UUID constraintId, boolean accept) {
        employeeTimeslotConstraintRepository
                .findById(constraintId)
                .ifPresent(
                        elem -> {
                            elem.setIsAccepted(true);
                            if (!accept) {
                                elem.setConstraintType(ConstraintType.SOFT);
                            }
                            employeeTimeslotConstraintRepository.save(elem);
                        });
    }
}
