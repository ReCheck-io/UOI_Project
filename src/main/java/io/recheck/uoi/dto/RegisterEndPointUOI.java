package io.recheck.uoi.dto;

import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import lombok.Data;

@Data
public class RegisterEndPointUOI {

    private String system;
    private String url;
    private CirdaxResourcesEnum type;

    public RegisterEndPointUOI(){
        this.system = system;
        this.url = url;
        this.type = type;
    }
}
