package io.recheck.external.systems.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CirdaxDocumentsRequestAccessDTO {

    @NotBlank
    private String uoi;
    @NotBlank
    private String requestorCode;
    @NotBlank
    private String requestorName;

}
