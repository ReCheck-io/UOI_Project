package io.recheck.uoi.dto;

import io.recheck.uoi.entity.LEVEL;
import lombok.Data;

@Data
public class GetChildrenDTO {

    private LEVEL level;
    private String uoi;

    public GetChildrenDTO(String uoi, LEVEL level){
        this.uoi = uoi;
        this.level = level;
    }

}
