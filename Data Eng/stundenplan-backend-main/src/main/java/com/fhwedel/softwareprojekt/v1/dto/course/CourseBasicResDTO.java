package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Provides basic data about a course (= Veranstaltung)")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseBasicResDTO implements Serializable {

    @Schema(description = "Course identifier (UUID from the database)")
    private UUID id;

    @Schema(description = "Course name", example = "Anwendungsentwicklung in ERP-Systemen")
    private String name;

    @Schema(
            description = "Unique alphanumeric Identifier used in CAS system",
            example = "WS22SB037")
    private String casID;

    @Schema(description = "Teaching method", example = "Vorlesung")
    private CourseTypeResDTO courseType;
}
