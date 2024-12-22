package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCombinationRepository extends JpaRepository<RoomCombination, UUID> {}
