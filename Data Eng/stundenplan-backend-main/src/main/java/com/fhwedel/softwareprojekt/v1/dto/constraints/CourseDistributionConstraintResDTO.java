package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a Course Distribution Constraint for an employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDistributionConstraintResDTO {
    private String employeeAbbreviation;
    private ConstraintValue constraintValue;
}
