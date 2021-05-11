package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String uoi;
    private String token;

    public AccessTokenDTO(String uoi, String token){
        this.uoi = uoi;
        this.token = token;
    }
}
