package io.recheck.uoi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank
public class UOISearchByPropertiesDTO {
    private boolean metaData;
    private String key;
    private String value;

    public UOISearchByPropertiesDTO(String key, String value, boolean metaData){
        this.key = key;
        this.value = value;
        this.metaData = metaData;
    }
    
}
