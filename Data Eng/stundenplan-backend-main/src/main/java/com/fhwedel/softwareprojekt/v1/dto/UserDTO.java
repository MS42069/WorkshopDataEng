package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Data Transfer Object for providing user data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Schema(description = "Display name of the user")
    private String displayName;

    @Schema(description = "Username of the user")
    private String username;
}
