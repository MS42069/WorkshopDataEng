package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.EmployeeConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.service.types.EmployeeTypeService;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private final UUID timetableId = UUID.randomUUID();
    private final ModelMapper modelMapper = setModelMapper();
    private final EmployeeConverter employeeConverter = new EmployeeConverter(modelMapper);
    @Mock private EmployeeRepository employeeRepository;
    @InjectMocks private EmployeeService underTest;
    @Mock private WorkTimeService workTimeService;
    @Mock private TimetableService timetableService;
    @Mock private EmployeeTypeService employeeTypeService;

    public ModelMapper setModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @BeforeEach
    void setUp() {
        underTest =
                new EmployeeService(
                        employeeRepository,
                        employeeTypeService,
                        employeeConverter,
                        workTimeService,
                        timetableService);
    }

    @Test
    void findAll() {
        // when
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);

        underTest.findAll(timetable.getId());
        // then
        verify(employeeRepository).findByTimetable(any(Timetable.class));
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<Employee> emptyOptional = Optional.empty();
        // when
        when(employeeRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Employee' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        Optional<Employee> optionalEmployee = Optional.of(employee);
        // when
        when(employeeRepository.findById(any(UUID.class))).thenReturn(optionalEmployee);

        UUID id = UUID.randomUUID();
        // then
        Employee actualEmployee = underTest.findByID(id);

        assertThat(actualEmployee).isEqualTo(employee);
    }

    @Test
    void saveEmployeeWithoutWorkTimes() {
        // given
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .workTimes(new ArrayList<>())
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(UUID.randomUUID())
                        .build();

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        // when
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee actualEmployee = underTest.save(employeeReqDTO, timetableId);

        // then
        assertThat(actualEmployee).isNotNull();
        assertThat(actualEmployee.getFirstname()).isEqualTo("Max");
    }

    @Test
    void saveEmployeeWithWorkTimes() {
        // given
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .workTimes(new ArrayList<>())
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(UUID.randomUUID())
                        .build();
        WorkTimeDTO workTimeDTO =
                WorkTimeDTO.builder()
                        .weekday(DayOfWeek.MONDAY)
                        .timeslotID(UUID.randomUUID())
                        .build();
        employeeReqDTO.getWorkTimes().add(workTimeDTO);

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        // Timeslot and Weekday of a WorkTime only concern the WorkTimeService thus are negligible
        // in this test
        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());

        // when
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(workTimeService.save(workTimeDTO, employee)).thenReturn(workTime);

        Employee actualEmployee = underTest.save(employeeReqDTO, timetableId);

        // then
        assertThat(actualEmployee).isNotNull();
        assertThat(actualEmployee.getFirstname()).isEqualTo("Max");
        // verify that a work time was added
        verify(workTimeService, times(1)).save(workTimeDTO, employee);
        assertThat(actualEmployee.getWorkTimes().size()).isEqualTo(1);
        assertThat(actualEmployee.getWorkTimes().get(0)).isEqualTo(workTime);
    }

    @Test
    void updateEmployee() {
        // given
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .workTimes(new ArrayList<>())
                        .firstname("Max22")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(UUID.randomUUID())
                        .build();

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        UUID uuid = UUID.randomUUID();

        // when
        when(employeeRepository.findById(any(UUID.class))).thenReturn(Optional.of(employee));

        Employee actualEmployee = underTest.updateEmployee(uuid, employeeReqDTO, timetableId);

        // then
        assertThat(actualEmployee).isNotNull();
        assertThat(actualEmployee.getFirstname()).isEqualTo("Max22");
    }

    @Test
    void updateEmployeeWithWorkTime() {
        // given
        WorkTimeDTO workTimeDTO =
                WorkTimeDTO.builder()
                        .weekday(DayOfWeek.MONDAY)
                        .timeslotID(UUID.randomUUID())
                        .build();

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .workTimes(new ArrayList<>(List.of(workTimeDTO)))
                        .firstname("Richard")
                        .lastname("Feynman")
                        .abbreviation("rpf")
                        .employeeType(UUID.randomUUID())
                        .build();

        WorkTime oldWorkTime = new WorkTime();
        oldWorkTime.setId(UUID.randomUUID());

        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(UUID.randomUUID());
        employeeToUpdate.setFirstname("Max");
        employeeToUpdate.setLastname("Mustermann");
        employeeToUpdate.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employeeToUpdate.setAbbreviation("muster");
        employeeToUpdate.setWorkTimes(new ArrayList<>(List.of(oldWorkTime)));

        // when
        when(employeeRepository.findById(employeeToUpdate.getId()))
                .thenReturn(Optional.of(employeeToUpdate));
        WorkTime newWorkTime = new WorkTime();
        newWorkTime.setId(UUID.randomUUID());
        when(workTimeService.save(any(WorkTimeDTO.class), any(Employee.class)))
                .thenReturn(newWorkTime);

        Employee actualEmployee =
                underTest.updateEmployee(employeeToUpdate.getId(), employeeReqDTO, timetableId);

        // then
        assertThat(actualEmployee).isNotNull();
        assertThat(actualEmployee.getFirstname()).isEqualTo("Richard");
        assertThat(actualEmployee.getLastname()).isEqualTo("Feynman");
        assertThat(actualEmployee.getAbbreviation()).isEqualTo("rpf");

        List<WorkTime> actualWorkTimes = actualEmployee.getWorkTimes();
        assertThat(actualWorkTimes.size()).isEqualTo(1);
        assertThat(actualWorkTimes.get(0).getId()).isEqualTo(newWorkTime.getId());
    }

    @Test
    void deleteEmployee() {
        // given
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setEmployeeType(TestDataUtil.createEmployeeTypeAssistent());
        employee.setAbbreviation("mus");
        employee.setWorkTimes(new ArrayList<>());

        UUID uuid = UUID.randomUUID();

        // when
        when(employeeRepository.findById(any(UUID.class))).thenReturn(Optional.of(employee));

        doNothing().when(employeeRepository).deleteById(any(UUID.class));

        underTest.deleteEmployee(uuid);

        verify(employeeRepository, times(1)).deleteById(uuid);
    }
}
