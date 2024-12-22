package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeSemesterRepository extends JpaRepository<DegreeSemester, UUID> {
    List<DegreeSemester> findByTimetable(Timetable timetable);

    void deleteByTimetable(Timetable timetableId);
}
