package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class RegisterEndPointUOI {

    private String system;
    private String url;
    private String type;

    public RegisterEndPointUOI(){
        this.system = system;
        this.url = url;
        this.type = type;
    }
}
