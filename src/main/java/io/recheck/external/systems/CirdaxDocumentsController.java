package io.recheck.external.systems;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.recheck.external.systems.dto.CirdaxDocumentsRequestAccessDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsResponseDTO;
import io.recheck.uoi.exceptions.GeneralErrorException;
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
    public CirdaxDocumentsResponseDTO requestAccessOrQueryDocuments(@RequestBody @Valid CirdaxDocumentsRequestAccessDTO dto) throws JsonProcessingException, GeneralErrorException {
        return cirdaxDocumentsService.requestAccessOrQueryDocuments(dto);
    }

}
