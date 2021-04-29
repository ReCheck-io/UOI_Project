package io.recheck.uoi.dto;

import lombok.Data;

@Data
public class GetDocumentDTO {
    private String uoi;
    private String documentId;
    private String token;

    public GetDocumentDTO(String uoi, String token, String documentId){
        this.uoi = uoi;
        this.token = token;
        this.documentId = documentId;
    }
}
