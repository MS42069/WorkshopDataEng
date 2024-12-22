package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeRepository extends JpaRepository<Degree, UUID> {
    List<Degree> findByTimetable(Timetable timetable);

    List<Degree> findByTimetableId(UUID timetableId);

    void deleteByTimetableId(UUID timetableId);
}
