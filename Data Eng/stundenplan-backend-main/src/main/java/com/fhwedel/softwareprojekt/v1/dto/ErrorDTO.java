package com.fhwedel.softwareprojekt.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents an api error.")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO implements Serializable {
    @Schema(description = "Describes the type of error")
    private String error;

    @Schema(description = "A more detailed error message")
    private String message;
}
