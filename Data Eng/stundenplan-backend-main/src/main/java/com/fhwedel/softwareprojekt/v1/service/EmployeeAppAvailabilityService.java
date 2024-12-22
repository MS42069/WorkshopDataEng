package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.EmployeeAppAvailabilityConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeAppAvailabilityDTO;
import com.fhwedel.softwareprojekt.v1.model.EmployeeAppAvailability;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeAppAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeAppAvailabilityService {
    private final EmployeeAppAvailabilityRepository employeeAppAvailabilityRepository;

    private final EmployeeAppAvailabilityConverter employeeAppAvailabilityConverter;

    public EmployeeAppAvailabilityDTO getEmployeeAppAvailabilityTime() {
        return employeeAppAvailabilityConverter.convertEntityToResponseDTO(employeeAppAvailabilityRepository.findFirstBy()
                .orElseGet(EmployeeAppAvailability::new));
    }

    public void changeEmployeeAppAvailabilityTime(
            EmployeeAppAvailabilityDTO employeeAppAvailabilityDTO) {
        EmployeeAppAvailability employeeAppAvailability = employeeAppAvailabilityRepository.findFirstBy()
                .orElseGet(EmployeeAppAvailability::new);
        employeeAppAvailability.setStartDate(employeeAppAvailabilityDTO.getStartDate());
        employeeAppAvailability.setEndDate(employeeAppAvailabilityDTO.getEndDate());
        employeeAppAvailabilityRepository.save(employeeAppAvailability);
    }
}
