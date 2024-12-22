package com.fhwedel.softwareprojekt.v1.dto.types;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO that is sent as request to the server to create a new roomType or to modify the properties of an existing roomType")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeReqDTO {

    @Schema(description = "RoomType name", example = "PC Pool")
    @NotBlank
    private String name;

    @NotNull private boolean online;
}
