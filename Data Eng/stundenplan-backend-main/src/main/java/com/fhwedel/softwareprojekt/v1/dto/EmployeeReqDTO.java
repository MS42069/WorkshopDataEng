package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for receiving {@link Employee} data from the client. */
@Schema(
        description =
                "DTO that is sent as request to the server to create a new Employee or to modify the properties of an existing employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeReqDTO implements Serializable {
    @Schema(
            description = "Employee abbreviation (= Mitarbeiterkürzel); MUST be unique",
            minLength = 1,
            example = "wol")
    @NotBlank(message = "no abbreviation was given")
    private String abbreviation;

    @Schema(description = "First name", example = "Birger")
    @NotNull
    private String firstname;

    @Schema(description = "Last name", example = "Wolter")
    @NotNull
    private String lastname;

    @Schema(description = "ID of Team resp. group category to which an employee is assigned")
    @NotNull
    private UUID employeeType;

    @Schema(description = "List of working times that an employee can be scheduled for")
    @NotNull
    private List<@NotNull @Valid WorkTimeDTO> workTimes;
}
