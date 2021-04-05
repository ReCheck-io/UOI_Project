package io.recheck.uoi.dto;

import lombok.Data;
@Data
public class OnlyUOIDTO {

    private String uoi;

    public OnlyUOIDTO(String uoi){
        this.uoi = uoi;
    }
}
