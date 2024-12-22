package com.fhwedel.softwareprojekt.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeAppAvailabilityDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
