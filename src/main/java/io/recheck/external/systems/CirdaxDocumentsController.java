package io.recheck.external.systems;

import io.recheck.external.systems.dto.CirdaxDocumentsRequestAccessDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CirdaxDocumentsController {

    private final CirdaxDocumentsService cirdaxDocumentsService;

    public CirdaxDocumentsController(CirdaxDocumentsService cirdaxDocumentsService) {
        this.cirdaxDocumentsService = cirdaxDocumentsService;
    }

    @PostMapping(path = "/documents/cirdax", consumes = "application/json", produces = "application/json")
    public CirdaxDocumentsResponseDTO requestAccessAndQueryDocuments(@RequestBody @Valid CirdaxDocumentsRequestAccessDTO dto) {
        return cirdaxDocumentsService.requestAccessAndQueryDocuments(dto);
    }

}
