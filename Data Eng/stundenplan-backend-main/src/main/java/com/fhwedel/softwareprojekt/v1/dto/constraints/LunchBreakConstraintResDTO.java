package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

@Schema(
        description =
                "Response DTO that provides the data of a Lunch Break Constraint for an employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LunchBreakConstraintResDTO {
    private String employeeAbbreviation;
    private ConstraintValue constraintValue;
    private Set<DayOfWeek> harmedDays;
}
