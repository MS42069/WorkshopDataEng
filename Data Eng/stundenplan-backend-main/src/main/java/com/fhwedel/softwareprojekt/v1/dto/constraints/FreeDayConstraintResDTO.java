package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a Free Day Constraint for an employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeDayConstraintResDTO {
    private String employeeAbbreviation;
    private ConstraintValue constraintValue;
    private DayOfWeek favoriteDay;
}
