package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create a new lunch break constraint or to modify "
                        + "the properties of an existing constraint")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LunchBreakConstraintReqDTO {
    private String employeeAbbreviation;
    private ConstraintValue constraintValue;
}
