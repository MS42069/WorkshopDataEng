package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static com.fhwedel.softwareprojekt.v1.errorHandling.APIError.VALIDATION_ERROR;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.TimeslotController;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Integration Tests for the {@link TimeslotController} across all layers to test end-to-end
 * behaviour.
 *
 * <p>The use of <code>WebEnvironment.MOCK</code> means that the entire application context is
 * loaded in a mock servlet environment.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@AutoConfigureMockMvc
@WithMockUser(username = "test_account", password = "pwd")
public class TimeslotIntegrationMockEnvTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TimeslotRepository timeslotRepository;
    @Autowired private TimetableRepository timetableRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @AfterEach
    void rollbackDB() {
        timeslotRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    public void givenTimeslot_whenPostTimeslotWithNoUniqueIndex_thenBadRequestResponse()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        timeslotRepository.saveAndFlush(timeslot);

        TimeslotReqDTO newTimeslot =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("10:00"))
                        .endTime(LocalTime.parse("11:00"))
                        .index(1)
                        .build();
        // when
        this.mockMvc
                .perform(post(format("/v1/timetable/%s/timeslots", timetable.getId()), newTimeslot))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void
            givenTimeslot_whenPostAndNewTimeslotOverlapsWithGivenTimeslot_thenBadRequestResponse()
                    throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00"));
        timeslot.setEndTime(LocalTime.parse("09:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        timeslotRepository.saveAndFlush(timeslot);

        TimeslotReqDTO newTimeslot =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:30"))
                        .endTime(LocalTime.parse("11:00"))
                        .index(2)
                        .build();

        // when
        MvcResult result =
                this.mockMvc
                        .perform(
                                post(
                                        format("/v1/timetable/%s/timeslots", timetable.getId()),
                                        newTimeslot))
                        // then
                        .andExpect(status().isBadRequest())
                        .andReturn();

        assertThat(result.getResponse().getErrorMessage()).isNotNull();
        assertThat(result.getResponse().getErrorMessage()).contains("overlap");

        List<Timeslot> list = timeslotRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getTimetable().getId()).isEqualTo(timetable.getId());
    }

    @Test
    public void givenTwoTimeslots_whenPatchAndTimeslotsOverlap_thenBadRequestAndNoDatabaseChanges()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslotOne = new Timeslot();
        timeslotOne.setStartTime(LocalTime.parse("08:00"));
        timeslotOne.setEndTime(LocalTime.parse("09:00"));
        timeslotOne.setIndex(1);
        timeslotOne.setTimetable(timetable);

        Timeslot timeslotTwo = new Timeslot();
        timeslotTwo.setStartTime(LocalTime.parse("10:00"));
        timeslotTwo.setEndTime(LocalTime.parse("11:00"));
        timeslotTwo.setIndex(2);
        timeslotTwo.setTimetable(timetable);

        timeslotRepository.save(timeslotOne);
        timeslotTwo = timeslotRepository.save(timeslotTwo);
        timeslotRepository.flush();

        TimeslotReqDTO updateDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:30"))
                        .endTime(LocalTime.parse("10:30"))
                        .index(2)
                        .build();
        // when
        MvcResult result =
                this.mockMvc
                        .perform(
                                patch(
                                        format(
                                                "/v1/timetable/%s/timeslots/%s",
                                                timetable.getId(), timeslotTwo.getId()),
                                        updateDTO))
                        // then
                        .andExpect(status().isBadRequest())
                        .andReturn();

        assertThat(result.getResponse().getErrorMessage()).isNotNull();
        assertThat(result.getResponse().getErrorMessage()).contains("overlap");

        timeslotRepository.flush();
        List<Timeslot> list = timeslotRepository.findByTimetableId(timetable.getId());
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getTimetable().getId()).isEqualTo(timetable.getId());
        assertThat(list.get(1).getTimetable().getId()).isEqualTo(timetable.getId());
    }

    @Test
    public void whenPostInvalidTimeslotWithEndTimeBeforeStartTime_thenBadRequest()
            throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("09:00"))
                        .endTime(LocalTime.parse("08:00"))
                        .index(1)
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString(VALIDATION_ERROR.getMessage())));
    }

    @Test
    public void whenPostAndValidTimeslot_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse("08:00:00"))
                        .endTime(LocalTime.parse("09:15:00"))
                        .index(0)
                        .build();

        mockMvc.perform(
                        post(
                                format("/v1/timetable/%s/timeslots", timetable.getId()),
                                timeslotReqDTO))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPatchAndValidTimeslot_thenCorrectResponse() throws Exception {
        // changes to patch
        final String modifiedStartTime = "08:15:00";
        final String modifiedEndTime = "09:30:00";

        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        timeslot = timeslotRepository.saveAndFlush(timeslot);

        TimeslotReqDTO timeslotReqDTO =
                TimeslotReqDTO.builder()
                        .startTime(LocalTime.parse(modifiedStartTime))
                        .endTime(LocalTime.parse(modifiedEndTime))
                        .index(1)
                        .build();
        // when
        this.mockMvc
                .perform(
                        patch(
                                format(
                                        "/v1/timetable/%s/timeslots/%s",
                                        timetable.getId(), timeslot.getId()),
                                timeslotReqDTO))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime", is(modifiedStartTime)))
                .andExpect(jsonPath("$.endTime", is(modifiedEndTime)));

        Timeslot timeslotInDb =
                timeslotRepository.findById(timeslot.getId()).orElse(new Timeslot());
        assertThat(timeslotInDb.getStartTime()).isEqualTo(timeslotReqDTO.getStartTime());
        assertThat(timeslotInDb.getEndTime()).isEqualTo(timeslotReqDTO.getEndTime());
    }

    @Test
    public void givenTimeslots_whenGetAllTimeslots_thenReturnListOfTimeslots() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot tsOne = new Timeslot();
        tsOne.setStartTime(LocalTime.parse("08:00:00"));
        tsOne.setEndTime(LocalTime.parse("09:15:00"));
        tsOne.setIndex(1);
        tsOne.setTimetable(timetable);

        Timeslot tsTwo = new Timeslot();
        tsTwo.setStartTime(LocalTime.parse("09:30:00"));
        tsTwo.setEndTime(LocalTime.parse("10:45:00"));
        tsTwo.setIndex(2);
        tsTwo.setTimetable(timetable);

        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(tsOne);
        timeslots.add(tsTwo);

        timeslotRepository.save(tsOne);
        timeslotRepository.save(tsTwo);
        timeslotRepository.flush();

        // when
        this.mockMvc
                .perform(get(format("/v1/timetable/%s/timeslots", timetable.getId())))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(timeslots.size())))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].id", notNullValue()));
    }

    @Test
    public void giveTimeslot_whenGetTimeslotById_thenReturnTimeslot() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);
        timeslot = timeslotRepository.saveAndFlush(timeslot);

        // when, then
        this.mockMvc
                .perform(
                        get(
                                format(
                                        "/v1/timetable/%s/timeslots/%s",
                                        timetable.getId(), timeslot.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime", is("08:00:00")))
                .andExpect(jsonPath("$.endTime", is("09:15:00")));
    }

    @Test
    public void whenDeleteTimeslotThenReturnSuccessful() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setId(UUID.randomUUID());
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        timeslot = timeslotRepository.saveAndFlush(timeslot);

        this.mockMvc
                .perform(
                        delete(
                                format(
                                        "/v1/timetable/%s/timeslots/%s",
                                        timetable.getId(), timeslot.getId())))
                .andExpect(status().isNoContent());

        assertThat(timeslotRepository.existsById(timeslot.getId())).isFalse();
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
