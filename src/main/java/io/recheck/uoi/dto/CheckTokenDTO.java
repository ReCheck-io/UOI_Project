package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class CheckTokenDTO {
    private String uoi;
    private String token;

    public CheckTokenDTO(String uoi, String token){
        this.uoi = uoi;
        this.token = token;
    }
}
