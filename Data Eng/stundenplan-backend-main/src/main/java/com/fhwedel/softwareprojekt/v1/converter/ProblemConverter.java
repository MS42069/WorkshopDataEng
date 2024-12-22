package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.RoomBasicResDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemsDTO;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.Conflict;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Converter class for mapping conflicts to problem DTOs. */
@Component
@RequiredArgsConstructor
public class ProblemConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a list of conflicts and a week event to a ProblemsDTO.
     *
     * @param event The week event associated with the conflicts
     * @param conflicts The list of conflicts
     * @return A ProblemsDTO containing the week event and the conflicts
     */
    public ProblemsDTO convertConflictsToProblemsDTO(WeekEvent event, List<Conflict> conflicts) {
        ProblemsDTO dto = new ProblemsDTO();
        ProblemsDTO.WeekEventDTO eventDTO = modelMapper.map(event, ProblemsDTO.WeekEventDTO.class);

        // Ensure the week is set in the DTO
        eventDTO.setWeek(event.getWeek());

        // sort timeslots in ascending order by start time
        eventDTO.getTimeslots().sort(Comparator.comparing(TimeslotResDTO::getStartTime));

        List<ProblemDTO> problems =
                conflicts.stream().map(this::convertConflictToProblemDTO).toList();

        dto.setEvent(eventDTO);
        dto.setProblems(problems);

        return dto;
    }

    /**
     * Converts a conflict to a ProblemDTO.
     *
     * @param conflict The conflict to be converted
     * @return A ProblemDTO representing the conflict
     */
    ProblemDTO convertConflictToProblemDTO(Conflict conflict) {
        ProblemDTO dto = new ProblemDTO();
        dto.setViolatedConstraint(conflict.getConstraint());
        dto.setDescription(conflict.getDescription());

        List<ProblemDTO.EmployeeBasicDTO> employeeDTOList =
                conflict.getEmployees().stream().map(this::convertEmployeeToDTO).toList();
        List<ProblemDTO.DegreeSemesterBasicDTO> semesterDTOList =
                conflict.getDegreeSemesters().stream()
                        .map(this::convertDegreeSemesterToDTO)
                        .toList();
        List<RoomBasicResDTO> roomDTOList =
                conflict.getRooms().stream().map(this::convertRoomToDTO).toList();

        dto.setConflictedEmployees(employeeDTOList);
        dto.setConflictedSemesters(semesterDTOList);
        dto.setConflictedRooms(roomDTOList);

        return dto;
    }

    /**
     * Converts an Employee object to an EmployeeBasicDTO.
     *
     * @param employee The Employee object to be converted
     * @return An EmployeeBasicDTO representing the employee
     */
    ProblemDTO.EmployeeBasicDTO convertEmployeeToDTO(Employee employee) {
        return modelMapper.map(employee, ProblemDTO.EmployeeBasicDTO.class);
    }

    /**
     * Converts a DegreeSemester object to a DegreeSemesterBasicDTO.
     *
     * @param semester The DegreeSemester object to be converted
     * @return A DegreeSemesterBasicDTO representing the degree semester
     */
    ProblemDTO.DegreeSemesterBasicDTO convertDegreeSemesterToDTO(DegreeSemester semester) {
        return modelMapper.map(semester, ProblemDTO.DegreeSemesterBasicDTO.class);
    }

    /**
     * Converts a Room object to a RoomBasicResDTO.
     *
     * @param room The Room object to be converted
     * @return A RoomBasicResDTO representing the room
     */
    RoomBasicResDTO convertRoomToDTO(Room room) {
        return modelMapper.map(room, RoomBasicResDTO.class);
    }
}
