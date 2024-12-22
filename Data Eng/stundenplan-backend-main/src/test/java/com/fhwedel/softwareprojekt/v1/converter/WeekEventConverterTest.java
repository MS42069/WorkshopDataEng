package com.fhwedel.softwareprojekt.v1.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fhwedel.softwareprojekt.v1.config.AppConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventResDTO;
import com.fhwedel.softwareprojekt.v1.model.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class WeekEventConverterTest {

    /**
     * Converter under test, manual injection of the ModelMapper dependency using the
     * Bean-definition in the AppConfiguration
     */
    private final WeekEventConverter converter =
            new WeekEventConverter(new AppConfiguration().modelMapper());

    @Test
    public void whenConvertEventEntityToResDTO_thenCorrectResult() {
        Course course = createCourseOne();
        Timeslot timeslot = createTimeslot();
        Room room = createRoomOne();
        DayOfWeek weekday = DayOfWeek.WEDNESDAY;

        WeekEvent event = new WeekEvent();
        event.setId(UUID.randomUUID());
        event.setCourse(course);
        event.setWeekday(DayOfWeek.of(weekday.getValue()));
        event.setRooms(List.of(room));
        event.setTimeslots(List.of(timeslot));
        event.setTimetable(createTimetable());

        // when
        WeekEventResDTO resultDTO = converter.convertEntityToResDTO(event);

        assertEquals(event.getId(), resultDTO.getId());
        assertEquals(event.getCourse().getId(), resultDTO.getCourse().getId());
        assertEquals(event.getRooms().get(0).getId(), resultDTO.getRooms().get(0).getId());
        assertEquals(1, resultDTO.getRooms().size());
        assertEquals(event.getTimeslots().get(0).getId(), resultDTO.getTimeslots().get(0).getId());
        assertEquals(1, resultDTO.getTimeslots().size());
        assertEquals(event.getWeekday(), resultDTO.getWeekday());
    }

    private Course createCourseOne() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setCasID("WS22A001");
        course.setName("Approximate Accuracy");
        course.setAbbreviation("Approx. Accuracy");
        course.setBlockSize(1);
        course.setWeeksPerSemester(12);
        course.setSlotsPerWeek(2);
        course.setCourseType(null);
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        return course;
    }

    private Room createRoomOne() {
        Room room = new Room();
        room.setId(UUID.randomUUID());
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");

        return room;
    }

    private Timeslot createTimeslot() {
        Timeslot ts = new Timeslot();
        ts.setId(UUID.randomUUID());
        ts.setStartTime(LocalTime.parse("08:00:00"));
        ts.setEndTime(LocalTime.parse("09:15:00"));

        return ts;
    }

    private Timetable createTimetable() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("Example Timetable");
        return timetable;
    }
}
