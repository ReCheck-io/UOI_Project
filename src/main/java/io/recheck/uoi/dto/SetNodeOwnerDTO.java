package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class SetNodeOwnerDTO {

    private String uoi;
    private String owner;

    public SetNodeOwnerDTO (String uoi, String owner){
        this.uoi = uoi;
        this.owner = owner;
    }
}
