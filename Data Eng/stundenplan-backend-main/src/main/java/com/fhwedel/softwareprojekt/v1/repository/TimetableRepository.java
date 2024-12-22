package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, UUID> {}
