package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class RequestAccessDTO {

    private String userId;
    private String systemId;
    private String uoi;

    public RequestAccessDTO(String uoi, String systemId, String userId){
        this.uoi = uoi;
        this.systemId = systemId;
        this.userId = userId;
    }
}
