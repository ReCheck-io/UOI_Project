package io.recheck.external.systems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CirdaxAccessResponseDTO {

    private String token;
    private String rawResponse;

}
