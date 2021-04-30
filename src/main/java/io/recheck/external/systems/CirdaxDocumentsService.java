package io.recheck.external.systems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.external.systems.dto.CirdaxDocumentsDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsRequestAccessDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsResponseDTO;
import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import io.recheck.external.systems.entity.CirdaxResourcesParams;
import io.recheck.uoi.exceptions.GeneralErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CirdaxDocumentsService {

    private final String TOKEN = "FB795A9EA62078B5A62AF0263EE6D8AC1E20E7813F13DEDEFAEADC35C962046A";
//    private final String TOKEN = "BB39CE8D69265BCE0A0629FE4C1C4FC8D83C2230717A25D114AD14219776E618"; // "accessTokenState": "Requested"
    private final String UOI = "NL.b6372870-d4c5-45a6-adc7-b629e0df12a8";

    private final RestClientService restClientService;
    private final Map<CirdaxResourcesEnum, String> cirdaxProfileResourcesMap;

    public CirdaxDocumentsService(RestClientService restClientService, CirdaxProfileService cirdaxProfileService) {
        this.restClientService = restClientService;
        this.cirdaxProfileResourcesMap = cirdaxProfileService.getCirdaxResourcesMap();
    }

    public CirdaxDocumentsResponseDTO requestAccessOrQueryDocuments(CirdaxDocumentsRequestAccessDTO dto) throws GeneralErrorException, JsonProcessingException {
        String addressForQueryDocuments = getAddressForQueryDocuments(UOI, TOKEN);
        ResponseEntity<String> responseEntityDocuments = restClientService.get(addressForQueryDocuments);
        if (responseEntityDocuments.getStatusCode() != HttpStatus.OK) {
            throw new GeneralErrorException(responseEntityDocuments.getBody());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        if (responseEntityDocuments.getBody().contains(CirdaxResourcesParams.AccessTokenState.name())) {
            Map<String, Object> responseEntityDocumentsMap = objectMapper.readValue(responseEntityDocuments.getBody(), new TypeReference<Map<String, Object>>() {});
            return new CirdaxDocumentsResponseDTO(responseEntityDocumentsMap.get(CirdaxResourcesParams.AccessTokenState.name()).toString(), Collections.emptyList());
        }
        else {
            List<CirdaxDocumentsDTO> cirdaxDocumentsDTOS = objectMapper.readValue(responseEntityDocuments.getBody(), new TypeReference<List<CirdaxDocumentsDTO>>() {});
            cirdaxDocumentsDTOS.forEach(c -> c.setDeepLinkUrl(getAddressForDocuments(c.getDeepLinkUrl(), UOI, TOKEN, c.getDocumentId())));
            return new CirdaxDocumentsResponseDTO(null, cirdaxDocumentsDTOS);
        }
    }

    private String requestUoiAccessToken(CirdaxDocumentsRequestAccessDTO dto) throws GeneralErrorException, JsonProcessingException {
        String addressForRequestToken = getAddressForRequestToken(dto.getUoi(), dto.getRequestorCode(), dto.getRequestorName());
        ResponseEntity<String> responseEntityToken = restClientService.get(addressForRequestToken);
        if (responseEntityToken.getStatusCode() != HttpStatus.OK) {
            throw new GeneralErrorException(responseEntityToken.getBody());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> responseEntityTokenMap = objectMapper.readValue(responseEntityToken.getBody(), new TypeReference<Map<String, String>>() {});

        return responseEntityTokenMap.get(CirdaxResourcesParams.UoiAccessTokenHash.name());
    }

    private String getAddressForRequestToken(String uoi, String requestorCode, String requestorName) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(cirdaxProfileResourcesMap.get(CirdaxResourcesEnum.RequestUoiAccessToken));

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiRequestorCode.name(), requestorCode);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiRequestorName.name(), requestorName);

        return builder.toUriString();
    }

    private String getAddressForQueryDocuments(String uoi, String accessToken) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(cirdaxProfileResourcesMap.get(CirdaxResourcesEnum.UoiQueryDocuments));

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiAccessToken.name(), accessToken);

        return builder.toUriString();
    }

    private String getAddressForDocuments(String uoi, String accessToken, String documentId) {
        return getAddressForDocuments(cirdaxProfileResourcesMap.get(CirdaxResourcesEnum.GetUoiDocument), uoi, accessToken, documentId);
    }

    private String getAddressForDocuments(String httpUrl, String uoi, String accessToken, String documentId) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(httpUrl);

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiAccessToken.name(), accessToken);
        builder.replaceQueryParam(CirdaxResourcesParams.DocumentId.name(), documentId);

        return builder.toUriString();
    }
}
