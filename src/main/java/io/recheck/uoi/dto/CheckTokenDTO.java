package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class CheckTokenDTO {
    private String uoi;
    private String user;
    private String token;

    public CheckTokenDTO(String uoi, String user, String token){
        this.uoi = uoi;
        this.user = user;
        this.token = token;
    }
}
