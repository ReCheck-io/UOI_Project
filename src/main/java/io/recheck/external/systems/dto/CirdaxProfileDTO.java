package io.recheck.external.systems.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CirdaxProfileDTO {

    @NotBlank
    private String requestUoiAccessTokenAddress;

    private String checkUoiAccessTokenAddress;

    @NotBlank
    private String uoiQueryDocumentsAddress;

    private String getUoiDocumentAddress;

}
