package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeWorkTimesResDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@ExtendWith(MockitoExtension.class)
public class EmployeeConverterTest {

    private final ModelMapper modelMapper = setModelMapper();
    private final EmployeeConverter employeeConverter = new EmployeeConverter(modelMapper);

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Test
    void convertDtoToEntity() {
        WorkTimeDTO workTimeDTO =
                WorkTimeDTO.builder()
                        .weekday(DayOfWeek.MONDAY)
                        .timeslotID(UUID.randomUUID())
                        .build();
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .workTimes(List.of(workTimeDTO))
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(UUID.randomUUID())
                        .build();

        Employee employee = employeeConverter.convertDtoToEntity(employeeReqDTO);

        assertThat(employee).isNotNull();
        assertThat(employee.getAbbreviation()).isEqualTo("mus");
    }

    @Test
    void convertEntityToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        EmployeeResDTO convertDTO = employeeConverter.convertEntityToResponseDTO(employee);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getFirstname()).isEqualTo("Max");
    }

    @Test
    void convertEntitiesToResponseDTOList() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstname("TEST");
        employee1.setLastname("test");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeDozent());
        employee1.setAbbreviation("tes");
        employee1.setWorkTimes(new ArrayList<>());
        employee1.setTimetable(timetable);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee1);

        List<EmployeeResDTO> convertDTO =
                employeeConverter.convertEntitiesToResponseDTOList(employees);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }

    @Test
    void convertEntityToWorkTimeResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);
        timeslot.setId(UUID.randomUUID());

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setTimetable(timetable);

        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());
        workTime.setEmployee(employee);
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setTimeslot(timeslot);

        employee.setWorkTimes(List.of(workTime));

        EmployeeWorkTimesResDTO convertDTO =
                employeeConverter.convertEntityToWorkTimeResponseDTO(employee, null);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getFirstname()).isEqualTo("Max");
        assertThat(convertDTO.getWorkTimes().size()).isEqualTo(1);
        assertThat(convertDTO.getWorkTimes().get(0).getWeekday()).isEqualTo(DayOfWeek.MONDAY);
    }
}
