package com.fhwedel.softwareprojekt.v1.dto.types;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Response DTO that provides the data of a courseType along with its assigned ID")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTypeResDTO {
    @Schema(description = "Identifier in the database (UUID generated upon creation)")
    private UUID id;

    @Schema(description = "Name of CourseType", example = "Uebung")
    private String name;
}
