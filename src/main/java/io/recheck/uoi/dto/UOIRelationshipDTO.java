package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class UOIRelationshipDTO {
    RELATIONSHIP relationship;
    String parentNode;
    String childNode;
}

