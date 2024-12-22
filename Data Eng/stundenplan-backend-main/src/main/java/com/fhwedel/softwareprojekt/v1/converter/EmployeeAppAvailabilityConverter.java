package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.EmployeeAppAvailabilityDTO;
import com.fhwedel.softwareprojekt.v1.model.EmployeeAppAvailability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeAppAvailabilityConverter {
    private final ModelMapper modelMapper;

    public EmployeeAppAvailabilityDTO convertEntityToResponseDTO(EmployeeAppAvailability employeeAppAvailability) {
        return modelMapper.map(employeeAppAvailability, EmployeeAppAvailabilityDTO.class);
    }
}
