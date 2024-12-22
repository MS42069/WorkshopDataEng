package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.EmployeeAppAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeAppAvailabilityRepository extends JpaRepository<EmployeeAppAvailability, UUID> {
    Optional<EmployeeAppAvailability> findFirstBy();
}
