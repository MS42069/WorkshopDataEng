package com.fhwedel.softwareprojekt.v1.repository.types;

import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {}
