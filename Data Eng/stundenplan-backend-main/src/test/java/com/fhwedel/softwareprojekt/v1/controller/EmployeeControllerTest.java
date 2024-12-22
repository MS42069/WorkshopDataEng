package com.fhwedel.softwareprojekt.v1.controller;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.EmployeeController;
import com.fhwedel.softwareprojekt.v1.converter.EmployeeConverter;
import com.fhwedel.softwareprojekt.v1.converter.WorkTimeConverter;
import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeResDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeWorkTimesResDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeResponseDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.service.EmployeeService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.constraints.TimeslotConstraintService;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.delete;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.get;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.put;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class EmployeeControllerTest {
    private final UUID timetableID = UUID.randomUUID();
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeConverter employeeConverter;
    @MockBean
    private EmployeeTypeRepository employeeTypeRepository;
    @MockBean
    private EmployeeTypeConverter employeeTypeConverter;
    @MockBean
    private WorkTimeConverter workTimeConverter;
    @MockBean
    private SchedulerService schedulerService;
    @MockBean
    private TimeslotConstraintService timeslotConstraintService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostAndValidEmployee_thenCorrectResponse() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();
        when(timeslotConstraintService.getEmployeeTimeslotConstraints(any())).thenReturn(Collections.emptyList());
        when(employeeService.save(any(), any())).thenReturn(new Employee());
        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostAndNoAbbreviationGiven() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndAbbreviationIsEmpty() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndNoFirstnameGiven() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndNoLastnameGiven() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndNoEmployeeTypeGiven() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndWorkTmeIsNull() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .build();

        mockMvc.perform(post(format("/v1/timetable/%s/employees", timetableID), employeeReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstname("Max");
        employee1.setLastname("Mustermann");
        employee1.setAbbreviation("mus");
        employee1.setEmployeeType(persistEmployeeTypeAssistant());
        employee1.setWorkTimes(new ArrayList<>());
        employee1.setTimetable(timetable);

        Employee employee2 = new Employee();
        employee2.setId(UUID.randomUUID());
        employee2.setFirstname("Hans");
        employee2.setLastname("Zimmerman");
        employee2.setAbbreviation("zim");
        employee2.setEmployeeType(employee1.getEmployeeType());
        employee2.setWorkTimes(new ArrayList<>());
        employee2.setTimetable(timetable);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        EmployeeResDTO dtoEmployee1 =
                EmployeeResDTO.builder()
                        .id(employee1.getId())
                        .firstname(employee1.getFirstname())
                        .lastname(employee1.getLastname())
                        .abbreviation(employee1.getAbbreviation())
                        .employeeType(
                                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(
                                        employee1.getEmployeeType()))
                        .timetable(timetable.getId())
                        .build();
        EmployeeResDTO employeeResDTO2 =
                EmployeeResDTO.builder()
                        .id(employee2.getId())
                        .firstname(employee2.getFirstname())
                        .lastname(employee2.getLastname())
                        .abbreviation(employee2.getAbbreviation())
                        .employeeType(
                                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(
                                        employee2.getEmployeeType()))
                        .timetable(timetable.getId())
                        .build();

        List<EmployeeResDTO> responseDTOList = List.of(dtoEmployee1, employeeResDTO2);

        when(employeeService.findAll(timetable.getId())).thenReturn(employees);
        when(employeeConverter.convertEntitiesToResponseDTOList(ArgumentMatchers.anyCollection()))
                .thenReturn(responseDTOList);

        this.mockMvc
                .perform(get(format("/v1/timetable/%s/employees", timetable.getId()
                        .toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())))
                .andExpect(jsonPath("$[0].abbreviation", is(employee1.getAbbreviation())))
                .andExpect(jsonPath("$[1].abbreviation", is(employee2.getAbbreviation())));
    }

    @Test
    public void giveEmployee_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstname("Max");
        employee1.setLastname("Mustermann");
        employee1.setAbbreviation("mus");
        employee1.setEmployeeType(persistEmployeeTypeAssistant());
        employee1.setWorkTimes(new ArrayList<>());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);
        timeslot.setId(UUID.randomUUID());

        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());
        workTime.setEmployee(employee1);
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setTimeslot(timeslot);

        employee1.getWorkTimes()
                .add(workTime);

        List<WorkTimeResponseDTO> list = new ArrayList<>();
        list.add(workTimeConverter.convertEntityToResponseDTO(employee1.getWorkTimes()
                .get(0)));

        EmployeeWorkTimesResDTO employeeWorkTimesResDTO = new EmployeeWorkTimesResDTO();
        employeeWorkTimesResDTO.setFirstname(employee1.getFirstname());
        employeeWorkTimesResDTO.setLastname(employee1.getLastname());
        employeeWorkTimesResDTO.setAbbreviation(employee1.getAbbreviation());
        employeeWorkTimesResDTO.setWorkTimes(list);

        when(employeeService.findByID(any(UUID.class))).thenReturn(employee1);
        when(employeeConverter.convertEntityToWorkTimeResponseDTO(any(Employee.class), any()))
                .thenReturn(employeeWorkTimesResDTO);

        this.mockMvc
                .perform(
                        get(
                                format(
                                        "/v1/timetable/%s/employees/%s",
                                        timetableID, UUID.randomUUID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(employee1.getFirstname())))
                .andExpect(jsonPath("$.abbreviation", is(employee1.getAbbreviation())))
                .andExpect(jsonPath("$.workTimes.size()", is(employee1.getWorkTimes()
                        .size())));
    }

    @Test
    public void whenUpdateEmployee_thenReturnModifiedEmployee() throws Exception {
        EmployeeType employeeType = persistEmployeeTypeAssistant();

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstname("Max");
        employee1.setLastname("Mustermann");
        employee1.setAbbreviation("mus");
        employee1.setEmployeeType(employeeType);
        employee1.setWorkTimes(new ArrayList<>());
        employee1.setTimetable(timetable);

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);
        timeslot.setId(UUID.randomUUID());

        WorkTime workTime = new WorkTime();
        workTime.setId(UUID.randomUUID());
        workTime.setEmployee(employee1);
        workTime.setWeekday(DayOfWeek.MONDAY);
        workTime.setTimeslot(timeslot);

        employee1.getWorkTimes()
                .add(workTime);

        Timeslot timeslotUpdated = new Timeslot();
        timeslotUpdated.setStartTime(LocalTime.parse("09:15"));
        timeslotUpdated.setEndTime(LocalTime.parse("10:30"));
        timeslotUpdated.setIndex(2);
        timeslotUpdated.setId(UUID.randomUUID());

        EmployeeReqDTO requestDTO =
                EmployeeReqDTO.builder()
                        .firstname("Hans")
                        .lastname("Zimmerman")
                        .abbreviation("zim")
                        .employeeType(employeeType.getId())
                        .workTimes(
                                List.of(
                                        WorkTimeDTO.builder()
                                                .weekday(DayOfWeek.WEDNESDAY)
                                                .timeslotID(timeslotUpdated.getId())
                                                .build()))
                        .build();

        List<WorkTimeResponseDTO> workTimeResponseDTO = new ArrayList<>();

        TimeslotResDTO timeslotResponseDTO =
                TimeslotResDTO.builder()
                        .startTime(timeslotUpdated.getStartTime())
                        .endTime(timeslotUpdated.getEndTime())
                        .id(timeslotUpdated.getId())
                        .index(timeslotUpdated.getIndex())
                        .build();

        WorkTimeResponseDTO workTimeResponseDTO1 =
                WorkTimeResponseDTO.builder()
                        .timeslot(timeslotResponseDTO)
                        .weekday(DayOfWeek.WEDNESDAY)
                        .timeslot(timeslotResponseDTO)
                        .build();
        workTimeResponseDTO.add(workTimeResponseDTO1);

        EmployeeWorkTimesResDTO responseDTO = new EmployeeWorkTimesResDTO();
        responseDTO.setAbbreviation(employee1.getAbbreviation());
        responseDTO.setFirstname(employee1.getFirstname());
        responseDTO.setLastname(employee1.getLastname());
        responseDTO.setEmployeeType(
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(
                        employee1.getEmployeeType()));
        responseDTO.setId(employee1.getId());
        responseDTO.setWorkTimes(workTimeResponseDTO);
        responseDTO.setTimetable(timetable.getId());
        when(employeeTypeRepository.saveAndFlush(any(EmployeeType.class))).thenReturn(employeeType);
        when(employeeService.updateEmployee(
                any(UUID.class), any(EmployeeReqDTO.class), any(UUID.class)))
                .thenReturn(employee1);
        when(employeeConverter.convertEntityToWorkTimeResponseDTO(any(Employee.class), any()))
                .thenReturn(responseDTO);

        this.mockMvc
                .perform(
                        put(
                                format(
                                        "/v1/timetable/%s/employees/%s",
                                        timetable.getId(), employee1.getId()),
                                requestDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(employee1.getFirstname())))
                .andExpect(jsonPath("$.abbreviation", is(employee1.getAbbreviation())))
                .andExpect(jsonPath("$.workTimes[0].weekday", is(DayOfWeek.WEDNESDAY.toString())))
                .andExpect(jsonPath("$.workTimes.size()", is(employee1.getWorkTimes()
                        .size())));
    }

    @Test
    public void whenDeleteEmployee_thenReturnSuccessful() throws Exception {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(persistEmployeeTypeAssistant());
        employee.setWorkTimes(new ArrayList<>());

        doNothing().when(employeeService)
                .deleteEmployee(any(UUID.class));

        this.mockMvc
                .perform(
                        delete(
                                format(
                                        "/v1/timetable/%s/employees/%s",
                                        timetableID, employee.getId())))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(employee.getId());
    }

    private EmployeeType persistEmployeeTypeAssistant() {
        EmployeeType employeeType = TestDataUtil.createEmployeeTypeAssistent();
        employeeType.setId(UUID.randomUUID());
        return employeeType;
    }
}
