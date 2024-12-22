package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.WeekEventController;
import com.fhwedel.softwareprojekt.v1.converter.WeekEventConverter;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventReqDTO;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(WeekEventController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class WeekEventControllerTest {

    @MockBean private SchedulerService schedulerService;

    @MockBean private WeekEventConverter weekEventConverter;

    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostInvalidWeekEventAndCourseIdIsNull_thenRespondWithBadRequest()
            throws Exception {
        WeekEventReqDTO weekEventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(null)
                        .weekday(DayOfWeek.MONDAY)
                        .blockOfTimeslots(Set.of(UUID.randomUUID()))
                        .takesPlaceInRooms(Set.of())
                        .build();

        String url = String.format("/v1/timetable/%s/week-events/schedule", UUID.randomUUID());

        mockMvc.perform(MockMvcTestUtil.post(url, weekEventReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostInvalidWeekEventAndBlockOfTimeslotsContainsNull_thenRespondWithBadRequest()
            throws Exception {
        Set<UUID> invalidUUIDs = new HashSet<>();
        invalidUUIDs.add(null);
        WeekEventReqDTO weekEventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(UUID.randomUUID())
                        .weekday(DayOfWeek.MONDAY)
                        .blockOfTimeslots(invalidUUIDs)
                        .takesPlaceInRooms(Set.of(UUID.randomUUID()))
                        .build();

        String url = String.format("/v1/timetable/%s/week-events/schedule", UUID.randomUUID());

        mockMvc.perform(MockMvcTestUtil.post(url, weekEventReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostInvalidWeekEventAndBlockOfTimeslotIsEmpty_thenRespondWithBadRequest()
            throws Exception {
        WeekEventReqDTO weekEventReqDTO =
                WeekEventReqDTO.builder()
                        .courseId(UUID.randomUUID())
                        .weekday(DayOfWeek.MONDAY)
                        .blockOfTimeslots(Set.of())
                        .takesPlaceInRooms(Set.of())
                        .build();

        String url = String.format("/v1/timetable/%s/week-events/schedule", UUID.randomUUID());

        mockMvc.perform(MockMvcTestUtil.post(url, weekEventReqDTO))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetOptionsWithMultipleCourseIds_thenRespondWithOk() throws Exception {
        int numOfCourses = 5;
        String[] courseIds = new String[numOfCourses];
        for (int i = 0; i < numOfCourses; i++) {
            courseIds[i] = UUID.randomUUID().toString();
        }

        String url = String.format("/v1/timetable/%s/week-events/options", UUID.randomUUID());

        mockMvc.perform(get(url).param("courseIds", courseIds)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOptionsWithOneCourseId_thenRespondWithOk() throws Exception {
        String courseId = UUID.randomUUID().toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("courseIds", courseId);

        String url = String.format("/v1/timetable/%s/week-events/options", UUID.randomUUID());

        mockMvc.perform(get(url).params(requestParams)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOptionsNoCourseIds_thenRespondWithBadRequest() throws Exception {

        String url = String.format("/v1/timetable/%s/week-events/options", UUID.randomUUID());

        mockMvc.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutRegenerate_thenRespondWithStatusNoContent() throws Exception {
        String url = String.format("/v1/timetable/%s/week-events/regenerate", UUID.randomUUID());
        mockMvc.perform(MockMvcTestUtil.put(url, "")).andExpect(status().isNoContent());
    }

    @Test
    public void whenGetOptionsWithMultipleCourseIdsAndWeekdays_thenRespondWithOk()
            throws Exception {

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("courseIds", UUID.randomUUID().toString());
        requestParams.add("courseIds", UUID.randomUUID().toString());
        requestParams.add("courseIds", UUID.randomUUID().toString());

        requestParams.add("weekdays", DayOfWeek.MONDAY.toString());
        requestParams.add("weekdays", DayOfWeek.TUESDAY.toString());

        String url = String.format("/v1/timetable/%s/week-events/options", UUID.randomUUID());

        mockMvc.perform(get(url).params(requestParams)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOptionsWithOneCourseIdAndNoWeekdaysSpecified_thenRespondWithOk()
            throws Exception {
        String courseId = UUID.randomUUID().toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("courseIds", courseId);

        String url = String.format("/v1/timetable/%s/week-events/options", UUID.randomUUID());

        mockMvc.perform(get(url).params(requestParams)).andExpect(status().isOk());
    }
}
