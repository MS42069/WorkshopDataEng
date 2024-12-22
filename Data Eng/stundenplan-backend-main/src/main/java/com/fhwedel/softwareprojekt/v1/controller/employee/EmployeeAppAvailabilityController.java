package com.fhwedel.softwareprojekt.v1.controller.employee;

import com.fhwedel.softwareprojekt.v1.dto.EmployeeAppAvailabilityDTO;
import com.fhwedel.softwareprojekt.v1.service.EmployeeAppAvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/wishes-app-activation")
@RequiredArgsConstructor
@Slf4j
public class EmployeeAppAvailabilityController {
    private final EmployeeAppAvailabilityService employeeAppAvailabilityService;

    @GetMapping
    public EmployeeAppAvailabilityDTO getEmployeeAppAvailabilityTimes() {
        return employeeAppAvailabilityService.getEmployeeAppAvailabilityTime();
    }

    @PostMapping
    public void setEmployeeAppAvailabilityTimes(@RequestBody EmployeeAppAvailabilityDTO employeeAppAvailabilityDTO) {
        employeeAppAvailabilityService.changeEmployeeAppAvailabilityTime(employeeAppAvailabilityDTO);
    }

}
