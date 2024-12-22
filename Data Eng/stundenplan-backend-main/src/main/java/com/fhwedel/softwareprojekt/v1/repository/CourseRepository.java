package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByTimetable(Timetable timetable);

    void deleteByTimetableId(UUID timetableId);

    List<Course> findByTimetableId(UUID timetableId);

    Streamable<Course> streamByTimetableId(UUID timetableId);

    List<Course> findByLecturersId(UUID lecturerId);

    List<Course> findBySemestersId(UUID semesterId);

    List<Course> findAllDistinctByLecturersId(UUID id);

    List<Course> findAllDistinctBySemestersId(UUID id);
}
