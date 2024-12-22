package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialEventRepository extends JpaRepository<SpecialEvent, UUID> {

    List<SpecialEvent> findByTimetableId(UUID timetableId);

    void deleteByTimetableId(UUID timetableId);
}
