package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "DTO for providing user credentials")
public class CredentialsDTO {
    @Schema(description = "The username")
    private String username;

    @Schema(description = "The password")
    private String password;
}
