package com.fhwedel.softwareprojekt.v1.dto.constraints;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeCheckTimeslotConstraintResDTO {
    private UUID id;
    private String abbreviation;
    private String firstname;
    private String lastname;
    private DayOfWeek weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;
}
