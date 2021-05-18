package io.recheck.external.systems.dto;

import lombok.Data;

@Data
public class CirdaxResponseWrapperDTO {

    private CirdaxAccessResponseDTO accessResponse;
    private CirdaxDocumentsResponseDTO documentsResponse;

    public CirdaxResponseWrapperDTO(CirdaxAccessResponseDTO accessResponse) {
        this.accessResponse = accessResponse;
    }

    public CirdaxResponseWrapperDTO(CirdaxDocumentsResponseDTO documentsResponse) {
        this.documentsResponse = documentsResponse;
    }
}
