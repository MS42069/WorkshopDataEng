package com.fhwedel.softwareprojekt.v1.repository.types;

import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolTypeRepository extends JpaRepository<SchoolType, UUID> {}
