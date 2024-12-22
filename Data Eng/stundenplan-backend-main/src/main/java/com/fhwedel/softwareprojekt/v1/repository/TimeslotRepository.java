package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, UUID> {
    List<Timeslot> findByTimetableId(UUID timetableId);

    void deleteByTimetableId(UUID timetableId);

    /**
     * Finds all timeslots of the timetable specified by its ID and returns them in a list sorted in
     * ascending order by their start times.
     *
     * @param timetableId ID of the timetable whose timeslot should be found
     * @return sorted list of all timeslots of the specified timetable
     */
    List<Timeslot> findByTimetableIdOrderByStartTimeAsc(UUID timetableId);
}
