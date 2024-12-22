package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO that is used to provide the client with the data of an {@link Employee} entity. */
@Schema(
        description =
                "Response DTO that provides the data of an employee along with their assigned ID "
                        + "but without their working times")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResDTO implements Serializable {
    @Schema(description = "Employee identifier (UUID from the database)")
    private UUID id;

    @Schema(description = "Unique employee abbreviation (= Mitarbeiterkürzel)", example = "wol")
    private String abbreviation;

    @Schema(description = "First name", example = "Birger")
    private String firstname;

    @Schema(description = "Last name", example = "Wolter")
    private String lastname;

    @Schema(description = "Team resp. group category to which an employee is assigned")
    private EmployeeTypeResDTO employeeType;

    @Schema(
            description =
                    "Identifier of a timetable in the database (UUID generated upon creation)")
    private UUID timetable;
}
