package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class CasIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private WeekEventRepository weekEventRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    @AfterEach
    void rollbackDB() {
        weekEventRepository.deleteAll();
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timeslotRepository.deleteAll();
        courseRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void givenTimetableWithPlannedCoursesWhenExportCasGetCasData() throws Exception {
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2021, 12, 14));
        timetable.setEndDate(LocalDate.of(2021, 12, 21));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);
        timetable = timetableRepository.saveAndFlush(timetable);

        Timeslot ts = new Timeslot();
        ts.setIndex(0);
        ts.setStartTime(LocalTime.of(16, 0));
        ts.setEndTime(LocalTime.of(17, 30));
        ts.setTimetable(timetable);
        ts = timeslotRepository.saveAndFlush(ts);

        RoomType rt = new RoomType();
        rt.setName("test");
        rt.setOnline(false);
        rt = roomTypeRepository.saveAndFlush(rt);

        Room room = new Room();
        room.setTimetable(timetable);
        room.setRoomType(rt);
        room.setIdentifier("E0.15");
        room.setCapacity(100);
        room.setAbbreviation("hs1");
        room.setName("Hörsaal 1");
        room = roomRepository.saveAndFlush(room);

        Course givenCourse = new Course();
        givenCourse.setCasID("WS21SA008V");
        givenCourse.setName("Algebra");
        givenCourse.setAbbreviation("Physics");
        givenCourse.setDescription("description");
        givenCourse.setBlockSize(2);
        givenCourse.setWeeksPerSemester(11);
        givenCourse.setSlotsPerWeek(4);
        givenCourse.setCourseType(null);
        givenCourse.setSuitedRooms(new ArrayList<>());
        givenCourse.setLecturers(new ArrayList<>());
        givenCourse.setTimetable(timetable);
        givenCourse = courseRepository.saveAndFlush(givenCourse);

        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setWeekday(DayOfWeek.TUESDAY);
        weekEvent.setWeek(0);
        weekEvent.setCourse(givenCourse);
        weekEvent.setRooms(List.of(room));
        weekEvent.setTimetable(timetable);
        weekEvent.setTimeslots(List.of(ts));
        weekEventRepository.saveAndFlush(weekEvent);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.get(
                                                format(
                                                        "/v1/timetable/%s/cas/export",
                                                        timetable.getId()))
                                        .contentType(MediaType.APPLICATION_OCTET_STREAM))
                        .andExpect(MockMvcResultMatchers.status()
                                .is(200))
                        .andReturn();
        assertThat(result.getResponse()
                .getStatus()).isEqualTo(200);
        assertThat(result.getResponse()
                .getContentType())
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM.toString());

        File f = new File("src/test/resources/template_Termine_Einzel .xml");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        String expected;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            expected = stringBuilder.toString();
        } finally {
            reader.close();
        }
        assertThat(result.getResponse()
                .getContentAsString()).isEqualTo(expected);
    }
}
