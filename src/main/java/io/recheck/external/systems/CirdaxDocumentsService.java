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

    private final RestClientService restClientService;
    private final CirdaxProfileService cirdaxProfileService;

    public CirdaxDocumentsService(RestClientService restClientService, CirdaxProfileService cirdaxProfileService) {
        this.restClientService = restClientService;
        this.cirdaxProfileService = cirdaxProfileService;
    }

    public CirdaxDocumentsResponseDTO requestAccessOrQueryDocuments(CirdaxDocumentsRequestAccessDTO dto) throws GeneralErrorException, JsonProcessingException {
        String token = requestUoiAccessToken(dto);
        String addressForQueryDocuments = getAddressForQueryDocuments(dto.getUoi(), token);
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
            cirdaxDocumentsDTOS.forEach(c -> c.setDeepLinkUrl(getAddressForDocuments(c.getDeepLinkUrl(), dto.getUoi(), token, c.getDocumentId())));
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
        Map<CirdaxResourcesEnum, String> map = cirdaxProfileService.getCirdaxResourcesMap();
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(map.get(CirdaxResourcesEnum.RequestUoiAccessToken));

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiRequestorCode.name(), requestorCode);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiRequestorName.name(), requestorName);

        return builder.toUriString();
    }

    private String getAddressForQueryDocuments(String uoi, String accessToken) {
        Map<CirdaxResourcesEnum, String> map = cirdaxProfileService.getCirdaxResourcesMap();
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(map.get(CirdaxResourcesEnum.UoiQueryDocuments));

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiAccessToken.name(), accessToken);

        return builder.toUriString();
    }

    private String getAddressForDocuments(String uoi, String accessToken, String documentId) {
        Map<CirdaxResourcesEnum, String> map = cirdaxProfileService.getCirdaxResourcesMap();
        return getAddressForDocuments(map.get(CirdaxResourcesEnum.GetUoiDocument), uoi, accessToken, documentId);
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
