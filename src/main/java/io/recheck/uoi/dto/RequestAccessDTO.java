package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class RequestAccessDTO {

    private String userId;
    private String systemId;
    private String uoi;

}
