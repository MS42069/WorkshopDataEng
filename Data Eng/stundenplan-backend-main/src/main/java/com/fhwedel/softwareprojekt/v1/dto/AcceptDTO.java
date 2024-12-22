package com.fhwedel.softwareprojekt.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptDTO {
    private Boolean accept;
    private String reason;
}
