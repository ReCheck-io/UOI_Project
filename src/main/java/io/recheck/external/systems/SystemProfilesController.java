package io.recheck.external.systems;


import io.recheck.external.systems.dto.CirdaxProfileDTO;
import io.recheck.uoi.dto.RegisterEndPointUOI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class SystemProfilesController {

    private final CirdaxProfileService cirdaxProfileService;

    public SystemProfilesController(CirdaxProfileService cirdaxProfileService) {
        this.cirdaxProfileService = cirdaxProfileService;
    }

    /** TEST JSON

     {
     "requestUoiAccessTokenAddress": "http://dev-services.xl-websolutions.nl/scripts/amwebconnector.dll/~cirdax/getwebdocument/RequestUoiAccessToken?UoiId=NL.9c7c4070-8bf4-4788-8c2c-3490713336c8&UoiRequestorCode=TEST&UoiRequestorName=Test-Name",
     "checkUoiAccessTokenAddress": "http://dev-services.xl-websolutions.nl/scripts/amwebconnector.dll/~cirdax/getwebdocument/CheckUoiAccessToken?UoiId=NL.9c7c4070-8bf4-4788-8c2c-3490713336c8&UoiAccessToken=6944035EEBB2CD4EBE0F6603578BE62DD3AEBDAC978F5B079E1921F713302CDB",
     "uoiQueryDocumentsAddress": "http://dev-services.xl-websolutions.nl/scripts/amwebconnector.dll/~cirdax/getwebdocument/UoiQueryDocuments?UoiId=NL.9c7c4070-8bf4-4788-8c2c-3490713336c8&UoiAccessToken=6944035EEBB2CD4EBE0F6603578BE62DD3AEBDAC978F5B079E1921F713302CDB",
     "getUoiDocumentAddress": "http://dev-services.xl-websolutions.nl/scripts/amwebconnector.dll/~cirdax/getwebdocument/GetUoiDocument?DocumentId=%7b9db88288-df27-4485-a6d2-187c1a3cd2f6%7d&UoiAccessToken=8BDCD097B6FD740EF002F25A0A7D1AD7BCDF6BCEFBD218236EC4F9298C9FF297&UoiId=NL.faa24612-e110-4b4b-a7de-b47d2df37779"
     }

     */
    @PostMapping(path = "/system/profile/cirdax", consumes = "application/json", produces = "application/json")
    public void saveCirdaxProfile(@RequestBody @Valid CirdaxProfileDTO cirdaxProfileDTO) {
        cirdaxProfileService.save(cirdaxProfileDTO);
    }

    @PutMapping(path = "/registerAnEndpoint")
    public void registerEndPoint(@RequestBody RegisterEndPointUOI registerEndPointUOI) {
        cirdaxProfileService.registerEndPoint(registerEndPointUOI);
    }

}
