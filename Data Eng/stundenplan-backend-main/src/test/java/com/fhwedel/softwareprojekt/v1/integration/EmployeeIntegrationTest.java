package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.EmployeeWorkTimesResDTO;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.WorkTimeDTO;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WorkTimeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration Tests for the Employee Controller across all layers to test end-to-end behaviour.
 *
 * <p>The `@SpringBootTest` annotation tells Spring Boot to load the entire application context. The
 * use of <code>WebEnvironment.RANDOM_PORT</code> starts a server with a random port.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class EmployeeIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private EmployeeRepository employeeRepository;

    @Autowired private TimeslotRepository timeslotRepository;
    @Autowired private WorkTimeRepository workTimeRepository;
    @Autowired private TimetableRepository timetableRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Autowired private EmployeeTypeRepository employeeTypeRepository;

    @AfterEach
    void rollback() {
        // not the deletion order is important: e.g. employees have to be deleted before timeslots
        workTimeRepository.deleteAll();
        employeeRepository.deleteAll();
        employeeTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void whenPostEmployee_thenCorrectResponse() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(createAndPersistEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        // when
        EmployeeWorkTimesResDTO response =
                mockMvcTestUtil.post(
                        format("/v1/timetable/%s/employees", timetable.getId()),
                        EmployeeWorkTimesResDTO.class,
                        employeeReqDTO);

        assertEquals("Max", response.getFirstname());
        assertEquals("Mustermann", response.getLastname());
        assertNotNull(response.getId());
    }

    @Test
    void
            whenPostEmployeeWithInvalidWorkTime_NonExistingTimeslotAndWeekday_thenNotFoundAndNoPersist()
                    throws Exception {
        Timetable timetable = createAndPersistTimetable();

        Timeslot timeslot = createAndPersistTimeSlotNineToFive(timetable);
        // assert setup
        assertTrue(timeslotRepository.findById(timeslot.getId()).isPresent());

        // given
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(createAndPersistEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        WorkTimeDTO validWorkTimeDTO = new WorkTimeDTO(DayOfWeek.MONDAY, timeslot.getId());

        // invalid work time with non-existing weekday and timeslot
        WorkTimeDTO invalidWorkTimeDTO = new WorkTimeDTO(DayOfWeek.MONDAY, UUID.randomUUID());

        employeeReqDTO.getWorkTimes().add(validWorkTimeDTO);
        employeeReqDTO.getWorkTimes().add(invalidWorkTimeDTO);

        // when
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/employees", timetable.getId()),
                                employeeReqDTO))
                .andExpect(status().isNotFound());

        // then
        assertEquals(
                0, employeeRepository.findAll().size(), "No employee should have been persisted");
        assertEquals(
                0, workTimeRepository.findAll().size(), "No WorkTime should have been persisted");
    }

    @Test
    void whenPostEmployeeWithNonUniqueAbbrev_thenBadRequestAndNoPersistOfWorkTimes()
            throws Exception {
        // given
        EmployeeType employeeType = createAndPersistEmployeeType();
        Timetable timetable = createAndPersistTimetable();

        Timeslot timeslot = createAndPersistTimeSlotNineToFive(timetable);

        Employee existingEmployee = createDefaultEmployee(employeeType);
        existingEmployee.setAbbreviation("mus");
        existingEmployee.setTimetable(timetable);
        existingEmployee = employeeRepository.saveAndFlush(existingEmployee);

        // assert setup
        assertTrue(timeslotRepository.findById(timeslot.getId()).isPresent());

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        WorkTimeDTO validWorkTimeDTO = new WorkTimeDTO(DayOfWeek.MONDAY, timeslot.getId());
        employeeReqDTO.getWorkTimes().add(validWorkTimeDTO);

        // when
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/employees", timetable.getId()),
                                employeeReqDTO))
                .andExpect(status().isBadRequest());

        assertTrue(employeeRepository.findById(existingEmployee.getId()).isPresent());
        assertEquals(
                1,
                employeeRepository.findAll().size(),
                "No second employee should have been persisted");
        assertEquals(
                0, workTimeRepository.findAll().size(), "No WorkTime should have been persisted");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnListOfEmployees() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        EmployeeType employeeType = createAndPersistEmployeeType();
        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstname("Max");
        employee1.setLastname("Mustermann");
        employee1.setAbbreviation("mus");
        employee1.setEmployeeType(employeeType);
        employee1.setWorkTimes(new ArrayList<>());
        employee1.setTimetable(timetable);

        Employee employee2 = new Employee();
        employee2.setId(UUID.randomUUID());
        employee2.setFirstname("Max2");
        employee2.setLastname("Mustermann2");
        employee2.setAbbreviation("mus2");
        employee2.setEmployeeType(employeeType);
        employee2.setWorkTimes(new ArrayList<>());
        employee2.setTimetable(timetable);

        employee1 = employeeRepository.saveAndFlush(employee1);
        employee2 = employeeRepository.saveAndFlush(employee2);

        // when
        EmployeeWorkTimesResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/employees", timetable.getId()),
                        EmployeeWorkTimesResDTO[].class);

        assertEquals(employee1.getId(), response[0].getId());
        assertEquals(employee2.getId(), response[1].getId());
    }

    @Test
    public void givenEmployee_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employee = employeeRepository.saveAndFlush(employee);

        // when
        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());
        EmployeeWorkTimesResDTO response = mockMvcTestUtil.get(url, EmployeeWorkTimesResDTO.class);

        assertEquals(employee.getId(), response.getId());
    }

    @Test
    public void givenEmployee_whenPutEmployeeName_thenResponseOk() throws Exception {
        // given
        EmployeeType employeeType = createAndPersistEmployeeType();
        Timetable timetable = createAndPersistTimetable();
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(employeeType);
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employee = employeeRepository.saveAndFlush(employee);

        List<WorkTimeDTO> workTimeDTOS = new ArrayList<>();
        for (WorkTime wt : employee.getWorkTimes()) {
            workTimeDTOS.add(
                    WorkTimeDTO.builder()
                            .weekday(wt.getWeekday())
                            .timeslotID(wt.getTimeslot().getId())
                            .build());
        }
        EmployeeReqDTO requestDTO =
                EmployeeReqDTO.builder()
                        .lastname(employee.getLastname())
                        .firstname(employee.getFirstname())
                        .abbreviation(employee.getAbbreviation())
                        .employeeType(employeeType.getId())
                        .workTimes(workTimeDTOS)
                        .build();
        requestDTO.setEmployeeType(employeeType.getId());
        requestDTO.setFirstname("TEST");

        // when
        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());
        mockMvcTestUtil.put(url, EmployeeWorkTimesResDTO.class, requestDTO);

        // then
        Employee savedEmployee =
                employeeRepository.findById(employee.getId()).orElse(new Employee());
        assertThat(savedEmployee.getFirstname()).isEqualTo("TEST");
    }

    @Test
    public void givenEmployee_whenDeleteEmployee_thenNoContentResponse() throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employee = employeeRepository.saveAndFlush(employee);

        // when
        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        assertThat(employeeRepository.existsById(employee.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingEmployee_thenNotFoundResponse() throws Exception {
        String url = format("/v1/timetable/%s/employees/%s", UUID.randomUUID(), UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingEmployee_thenNotFoundResponse() throws Exception {
        String url = format("/v1/timetable/%s/employees/%s", UUID.randomUUID(), UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPutNotExistingEmployee_thenNotFoundResponse() throws Exception {
        createAndPersistTimetable();
        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(createAndPersistEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        String url = format("/v1/timetable/%s/employees/%s", UUID.randomUUID(), UUID.randomUUID());
        mockMvc.perform(put(url, employeeReqDTO)).andExpect(status().isNotFound());
    }

    @Test
    void givenEmployee_whenPostREmployeeWithNonUniqueAbbreviation_thenBadRequestResponse()
            throws Exception {
        // given
        Timetable timetable = createAndPersistTimetable();
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstname("Max");
        employee.setLastname("Mustermann");
        employee.setAbbreviation("mus");
        employee.setEmployeeType(createAndPersistEmployeeType());
        employee.setWorkTimes(new ArrayList<>());
        employee.setTimetable(timetable);

        employeeRepository.saveAndFlush(employee);

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max2")
                        .lastname("Mustermann2")
                        .abbreviation("mus")
                        .employeeType(employee.getEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        mockMvc.perform(post("/v1/employees", employeeReqDTO))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenEmployeePutChangeWorkTimesDeletingWorktimesCorrectly() throws Exception {
        Timetable timetable = createAndPersistTimetable();

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00"))
                        .endTime(LocalTime.parse("09:00"))
                        .index(1)
                        .build();

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("max")
                        .employeeType(createAndPersistEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        EmployeeReqDTO employeeReqDTO2 =
                EmployeeReqDTO.builder()
                        .firstname("Max2")
                        .lastname("Mustermann2")
                        .abbreviation("max2")
                        .employeeType(employeeReqDTO.getEmployeeType())
                        .workTimes(new ArrayList<>())
                        .build();

        // Timeslot speichern
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO))
                .andExpect(status().isOk());

        // Employee speichern
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/employees", timetable.getId()),
                                employeeReqDTO))
                .andExpect(status().isOk());

        Employee employee = employeeRepository.findAll().get(0);
        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());

        EmployeeWorkTimesResDTO response =
                mockMvcTestUtil.put(url, EmployeeWorkTimesResDTO.class, employeeReqDTO2);

        assertEquals(employeeReqDTO2.getFirstname(), response.getFirstname());
        assertEquals(employeeReqDTO2.getWorkTimes().size(), response.getWorkTimes().size());
    }

    @Test
    void givenEmployeePutChangeWorkTimesSavingWorktimesCorrectly() throws Exception {
        Timetable timetable = createAndPersistTimetable();
        EmployeeType employeeType = createAndPersistEmployeeType();

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("10:00"))
                        .endTime(LocalTime.parse("11:00"))
                        .index(2)
                        .build();

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        EmployeeReqDTO employeeReqDTO2 =
                EmployeeReqDTO.builder()
                        .firstname("Max2")
                        .lastname("Mustermann2")
                        .abbreviation("mus2")
                        .employeeType(employeeType.getId())
                        .workTimes(new ArrayList<>())
                        .build();

        // Timeslot speichern

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO))
                .andExpect(status().isOk());

        // Employee speichern
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/employees", timetable.getId()),
                                employeeReqDTO))
                .andExpect(status().isOk());

        Employee employee = employeeRepository.findAll().get(0);

        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());
        EmployeeWorkTimesResDTO response =
                mockMvcTestUtil.put(url, EmployeeWorkTimesResDTO.class, employeeReqDTO2);

        assertEquals(employeeReqDTO2.getFirstname(), response.getFirstname());
        assertEquals(employeeReqDTO2.getWorkTimes().size(), response.getWorkTimes().size());
    }

    @Test
    void givenEmployeePutChangeWorkTimesSavingAndDeletingWorktimesCorrectly() throws Exception {
        Timetable timetable = createAndPersistTimetable();

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("10:00"))
                        .endTime(LocalTime.parse("11:00"))
                        .index(2)
                        .build();

        TimeslotReqDTO timeslotReqDTO2 =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("14:00"))
                        .endTime(LocalTime.parse("15:00"))
                        .index(3)
                        .build();

        EmployeeReqDTO employeeReqDTO =
                EmployeeReqDTO.builder()
                        .firstname("Max")
                        .lastname("Mustermann")
                        .abbreviation("mus")
                        .employeeType(createAndPersistEmployeeType().getId())
                        .workTimes(new ArrayList<>())
                        .build();

        EmployeeReqDTO employeeReqDTO2 =
                EmployeeReqDTO.builder()
                        .firstname("Max2")
                        .lastname("Mustermann2")
                        .abbreviation("mus2")
                        .employeeType(employeeReqDTO.getEmployeeType())
                        .workTimes(new ArrayList<>())
                        .build();

        // Timeslot speichern
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO))
                .andExpect(status().isOk());
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO2))
                .andExpect(status().isOk());

        WorkTimeDTO workTimeDTO =
                WorkTimeDTO.builder()
                        .timeslotID(timeslotRepository.findAll().get(0).getId())
                        .weekday(DayOfWeek.SATURDAY)
                        .build();

        WorkTimeDTO workTimeDTO2 =
                WorkTimeDTO.builder()
                        .timeslotID(timeslotRepository.findAll().get(0).getId())
                        .weekday(DayOfWeek.TUESDAY)
                        .build();

        WorkTimeDTO workTimeDTO3 =
                WorkTimeDTO.builder()
                        .timeslotID(timeslotRepository.findAll().get(1).getId())
                        .weekday(DayOfWeek.FRIDAY)
                        .build();

        // employeeDTO eine WorkTime geben
        employeeReqDTO.setWorkTimes(List.of(workTimeDTO));

        // employeeDTO2 eine WorkTime geben
        employeeReqDTO2.setWorkTimes(List.of(workTimeDTO2, workTimeDTO3));

        // Employee speichern
        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/employees", timetable.getId()),
                                employeeReqDTO))
                .andExpect(status().isOk());

        Employee employee = employeeRepository.findAll().get(0);

        String url = format("/v1/timetable/%s/employees/%s", timetable.getId(), employee.getId());
        EmployeeWorkTimesResDTO response =
                mockMvcTestUtil.put(url, EmployeeWorkTimesResDTO.class, employeeReqDTO2);

        assertEquals(employeeReqDTO2.getFirstname(), response.getFirstname());
        assertEquals(employeeReqDTO2.getWorkTimes().size(), response.getWorkTimes().size());
        assertEquals(
                employeeReqDTO2.getWorkTimes().get(0).getWeekday(),
                response.getWorkTimes().get(0).getWeekday());
        assertEquals(
                employeeReqDTO2.getWorkTimes().get(1).getWeekday(),
                response.getWorkTimes().get(1).getWeekday());
    }

    private Timeslot createAndPersistTimeSlotNineToFive(Timetable timetable) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.of(8, 0));
        timeslot.setEndTime(LocalTime.of(9, 0));
        timeslot.setIndex(0);
        timeslot.setTimetable(timetable);

        timeslot = timeslotRepository.saveAndFlush(timeslot);
        assertThat(timeslot).isNotNull();

        return timeslot;
    }

    private Employee createDefaultEmployee(EmployeeType employeeType) {
        Employee employee = new Employee();
        employee.setFirstname("Brian W.");
        employee.setLastname("Kernighan");
        employee.setAbbreviation("bwk");
        employee.setEmployeeType(employeeType);
        employee.setWorkTimes(new ArrayList<>());

        return employee;
    }

    private Timetable createAndPersistTimetable() {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);
        timetable = timetableRepository.saveAndFlush(timetable);
        assertThat(timetable).isNotNull();
        return timetable;
    }

    private EmployeeType createAndPersistEmployeeType() {
        EmployeeType employeeType = TestDataUtil.createEmployeeTypeDozent();
        return employeeTypeRepository.saveAndFlush(employeeType);
    }
}
