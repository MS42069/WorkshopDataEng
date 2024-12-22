package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTimeslotRepository extends JpaRepository<CourseTimeslot, UUID> {}
