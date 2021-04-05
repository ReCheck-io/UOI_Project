package io.recheck.uoi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank
public class UOIRelationshipDTO {
    private RELATIONSHIP relationship;
    private String parentNode;
    private String childNode;
}

