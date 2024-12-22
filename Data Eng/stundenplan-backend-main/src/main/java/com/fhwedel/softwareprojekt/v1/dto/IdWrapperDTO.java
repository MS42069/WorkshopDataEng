package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Wraps an ID that identifies an entity from the database")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdWrapperDTO implements Serializable {
    @Schema(description = "Identifier (UUID from the database)")
    @NotNull
    private UUID id;
}
