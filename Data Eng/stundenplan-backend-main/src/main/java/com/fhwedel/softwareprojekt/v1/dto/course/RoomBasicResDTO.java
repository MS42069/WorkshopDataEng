package com.fhwedel.softwareprojekt.v1.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Provides basic information about a room.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomBasicResDTO implements Serializable {

    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Unique name", example = "Hörsaal 1")
    private String name;

    @Schema(description = "Unique short name (= Raumkürzel)", example = "HS01")
    private String abbreviation;
}
