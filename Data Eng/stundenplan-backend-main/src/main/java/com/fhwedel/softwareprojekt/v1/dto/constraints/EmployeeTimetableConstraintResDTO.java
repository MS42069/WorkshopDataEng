package com.fhwedel.softwareprojekt.v1.dto.constraints;

import com.fhwedel.softwareprojekt.v1.dto.WorkTimeResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO for representing employee timetable constraints response.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTimetableConstraintResDTO {
    @Schema(description = "List of work time response DTOs")
    private List<WorkTimeResponseDTO> workTimeResponseDTOS;

    @Schema(description = "List of employee timeslot constraint response DTOs")
    private List<EmployeeTimeslotConstraintResDTO> employeeTimeslotConstraintResDTOS;
}
