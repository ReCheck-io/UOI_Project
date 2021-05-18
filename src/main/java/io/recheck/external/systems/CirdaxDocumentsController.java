package io.recheck.external.systems;

import io.recheck.external.systems.dto.CirdaxAccessRequestDTO;
import io.recheck.external.systems.dto.CirdaxResponseWrapperDTO;
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
    public CirdaxResponseWrapperDTO requestAccessAndQueryDocuments(@RequestBody @Valid CirdaxAccessRequestDTO dto) {
        return cirdaxDocumentsService.requestAccessAndQueryDocuments(dto);
    }

}
