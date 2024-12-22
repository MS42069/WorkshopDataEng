package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO that is used to provide the client with the data of an {@link Employee} entity along with
 * their associated {@link WorkTime work times}.
 */
@Schema(
        description =
                "Response DTO that provides the data of an employee including a complete "
                        + "list of their working times")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeWorkTimesResDTO extends EmployeeResDTO {
    @Schema(description = "List of working times that an employee can be scheduled for")
    private List<WorkTimeResponseDTO> workTimes = new ArrayList<>();

    @Schema(description = "List of constraints for one employee")
    List<EmployeeTimeslotConstraintResDTO> employeeTimeslotConstraints = new ArrayList<>();
}
