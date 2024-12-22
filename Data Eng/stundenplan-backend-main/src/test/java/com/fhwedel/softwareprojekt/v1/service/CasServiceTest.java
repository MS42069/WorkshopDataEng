package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Streamable;

@ExtendWith(MockitoExtension.class)
public class CasServiceTest {

    @InjectMocks private CasService casService;

    @Mock private TimetableService timetableService;

    @Mock private CourseService courseService;

    @Mock private WeekEventRepository weekEventRepository;

    @Test
    void testExportWithoutSpecialEvents() throws IOException {

        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setStartDate(LocalDate.of(2021, 12, 14));
        timetable.setEndDate(LocalDate.of(2021, 12, 21));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        Timeslot ts = new Timeslot();
        ts.setId(UUID.randomUUID());
        ts.setIndex(0);
        ts.setStartTime(LocalTime.of(16, 0));
        ts.setEndTime(LocalTime.of(17, 30));
        RoomType rt = new RoomType();
        rt.setName("Seminarraum");
        Room room = new Room();
        room.setTimetable(timetable);
        room.setRoomType(rt);
        room.setIdentifier("E0.15");
        room.setCapacity(100);
        room.setAbbreviation("hs1");
        room.setName("Hörsaal 1");

        Course givenCourse = new Course();
        givenCourse.setId(UUID.randomUUID());
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

        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setWeekday(DayOfWeek.TUESDAY);
        weekEvent.setWeek(null);
        weekEvent.setCourse(givenCourse);
        weekEvent.setId(UUID.randomUUID());
        weekEvent.setRooms(List.of(room));
        weekEvent.setTimetable(timetable);
        weekEvent.setTimeslots(List.of(ts));

        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);
        when(courseService.findAllCourses(any(UUID.class))).thenReturn(List.of(givenCourse));
        when(weekEventRepository.findByCourse(any(Course.class)))
                .thenReturn(Streamable.of(weekEvent));
        Resource output = casService.exportToCasXML(UUID.randomUUID());
        InputStream inputStream = output.getInputStream();
        // Output to CMD only for Checking the Result
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(textBuilder);

        // then
        assertThat(output).isNotNull();
    }

    @Test
    void testExportWithSpecialEvents() throws IOException {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setEndDate(LocalDate.of(2023, 1, 31));
        timetable.setStartDate(LocalDate.of(2023, 1, 1));
        timetable.setName("test");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setNumberOfWeeks(12);

        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setTimetable(timetable);
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(LocalDate.of(2023, 1, 2));
        specialEvent.setEndDate(LocalDate.of(2023, 1, 2));
        specialEvent.setId(UUID.randomUUID());

        SpecialEvent specialEvent2 = new SpecialEvent();
        specialEvent2.setTimetable(timetable);
        specialEvent2.setSpecialEventType(SpecialEventType.MONDAY_PLAN);
        specialEvent2.setStartDate(LocalDate.of(2023, 1, 3));
        specialEvent2.setEndDate(LocalDate.of(2023, 1, 4));
        specialEvent2.setId(UUID.randomUUID());

        timetable.getSpecialEvents().add(specialEvent);
        timetable.getSpecialEvents().add(specialEvent2);

        Timeslot ts = new Timeslot();
        ts.setId(UUID.randomUUID());
        ts.setIndex(0);
        ts.setStartTime(LocalTime.of(16, 0));
        ts.setEndTime(LocalTime.of(17, 30));
        RoomType rt = new RoomType();
        rt.setName("Seminarraum");
        Room room = new Room();
        room.setTimetable(timetable);
        room.setRoomType(rt);
        room.setIdentifier("E0.15");
        room.setCapacity(100);
        room.setAbbreviation("hs1");
        room.setName("Hörsaal 1");

        Course givenCourse = new Course();
        givenCourse.setId(UUID.randomUUID());
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

        WeekEvent weekEvent = new WeekEvent();
        weekEvent.setWeekday(DayOfWeek.MONDAY);
        weekEvent.setWeek(null);
        weekEvent.setCourse(givenCourse);
        weekEvent.setId(UUID.randomUUID());
        weekEvent.setRooms(List.of(room));
        weekEvent.setTimetable(timetable);
        weekEvent.setTimeslots(List.of(ts));

        when(timetableService.findByID(any(UUID.class))).thenReturn(timetable);
        when(courseService.findAllCourses(any(UUID.class))).thenReturn(List.of(givenCourse));
        when(weekEventRepository.findByCourse(any(Course.class)))
                .thenReturn(Streamable.of(weekEvent));

        // when
        Resource output = casService.exportToCasXML(UUID.randomUUID());

        InputStream inputStream = output.getInputStream();
        // Output to CMD only for Checking the Result
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(textBuilder);

        // then
        assertThat(output).isNotNull();
    }
}
