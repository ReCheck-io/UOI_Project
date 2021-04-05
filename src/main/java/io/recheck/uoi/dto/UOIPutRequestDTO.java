package io.recheck.uoi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank
public class UOIPutRequestDTO {

    private String uoi;
    private String key;
    private String value;

}
