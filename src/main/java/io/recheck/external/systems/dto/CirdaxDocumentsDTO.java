package io.recheck.external.systems.dto;

import lombok.Data;

@Data
public class CirdaxDocumentsDTO {

    private String documentId;
    private String documentFileName;
    private String documentFileSize;
    private String documentType;
    private String documentTypeName;
    private String deepLinkUrl;

}
