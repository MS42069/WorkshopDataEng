package com.fhwedel.softwareprojekt.v1.repository.types;

import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterTypeRepository extends JpaRepository<SemesterType, UUID> {}
