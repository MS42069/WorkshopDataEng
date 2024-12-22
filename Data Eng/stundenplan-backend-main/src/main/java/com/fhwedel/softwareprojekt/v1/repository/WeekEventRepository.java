package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface WeekEventRepository extends JpaRepository<WeekEvent, UUID> {

    /**
     * Finds all week events associated with the given course.
     *
     * @param course the given course
     * @return all scheduled events of the given course
     */
    Streamable<WeekEvent> findByCourse(Course course);

    /**
     * Finds all events of the timetable specified by its ID.
     *
     * @param timetableId timetable ID
     * @return all scheduled events of the given timetable
     */
    List<WeekEvent> findByTimetableId(UUID timetableId);

    /**
     * Finds all events of the specified timetable by its ID and week.
     *
     * @param timetableId The ID of the timetable to search for.
     * @param week The week number to filter the events.
     * @return A list of scheduled events for the given timetable and week.
     */
    List<WeekEvent> findByTimetableIdAndWeek(UUID timetableId, Integer week);

    /**
     * Finds all events with specified timetable ID and weekday.
     *
     * @param timetable_id timetable ID
     * @param weekday weekday
     * @return all scheduled events of the given timetable that take place on the specified weekday.
     */
    List<WeekEvent> findByTimetableIdAndWeekday(UUID timetable_id, DayOfWeek weekday);

    /**
     * Finds all events of the specified timetable by its ID, week, and weekday.
     *
     * @param timetableId The ID of the timetable to search for.
     * @param week The week number to filter the events.
     * @param weekday The day of the week to filter the events.
     * @return A list of scheduled events for the given timetable, week, and weekday.
     */
    List<WeekEvent> findByTimetableIdAndWeekAndWeekday(
            UUID timetableId, Integer week, DayOfWeek weekday);

    /**
     * Finds all events for the specified courses.
     *
     * @param courses courses
     * @return all scheduled events for the given courses.
     */
    List<WeekEvent> findByCourseIn(List<Course> courses);

    /**
     * Finds all events for the specified room.
     *
     * @param roomId roomId
     * @return all scheduled events for the given courses.
     */
    List<WeekEvent> findByRoomsId(UUID roomId);

    @EntityGraph(
            value = "scheduler.graph.weekevent.with.course",
            type = EntityGraph.EntityGraphType.LOAD)
    WeekEvent getWeekEventGraphCourseById(UUID id);

    @EntityGraph(
            value = "scheduler.graph.weekevent.with.rooms",
            type = EntityGraph.EntityGraphType.LOAD)
    WeekEvent getWeekEventGraphRoomsById(UUID id);

    @EntityGraph(
            value = "scheduler.graph.weekevent.with.timeslots",
            type = EntityGraph.EntityGraphType.LOAD)
    WeekEvent getWeekEventGraphTimeslotById(UUID id);

    void deleteByTimetableId(UUID timetableId);
}
