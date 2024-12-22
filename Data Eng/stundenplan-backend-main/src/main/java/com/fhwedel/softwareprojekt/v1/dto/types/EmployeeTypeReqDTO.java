package com.fhwedel.softwareprojekt.v1.dto.types;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create a new employeeType or to modify the properties of an existing employeeType")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTypeReqDTO {
    @Schema(description = "EmployeeType name", example = "Dozent")
    @NotBlank
    private String name;
}
