package io.recheck.external.systems;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.external.systems.dto.CirdaxDocumentsDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsRequestAccessDTO;
import io.recheck.external.systems.dto.CirdaxDocumentsResponseDTO;
import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import io.recheck.external.systems.entity.CirdaxResourcesParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CirdaxDocumentsService {

    private final RestClientService restClientService;
    private final CirdaxProfileService cirdaxProfileService;

    public CirdaxDocumentsService(RestClientService restClientService, CirdaxProfileService cirdaxProfileService) {
        this.restClientService = restClientService;
        this.cirdaxProfileService = cirdaxProfileService;
    }

    public CirdaxDocumentsResponseDTO requestAccessAndQueryDocuments(CirdaxDocumentsRequestAccessDTO dto) {

        String addressForRequestToken = getAddressForRequestToken(dto.getUoi(), dto.getRequestorCode(), dto.getRequestorName());

        log.info("Send GET {}", addressForRequestToken);
        ResponseEntity<String> responseEntityToken = restClientService.get(addressForRequestToken);
        log.info("Receive {}", responseEntityToken);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> responseEntityTokenMap = objectMapper.readValue(responseEntityToken.getBody(), new TypeReference<Map<String, String>>() {});
            if (responseEntityTokenMap.containsKey(CirdaxResourcesParams.UoiAccessTokenHash.name())) {
                String token = responseEntityTokenMap.get(CirdaxResourcesParams.UoiAccessTokenHash.name());
                return queryDocuments(dto.getUoi(), token);
            }
            else {
                return new CirdaxDocumentsResponseDTO("", Collections.emptyList(), responseEntityToken.getBody());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new CirdaxDocumentsResponseDTO("", Collections.emptyList(), responseEntityToken.getBody());
        }

    }

    private CirdaxDocumentsResponseDTO queryDocuments(String uoi, String token) {
        String addressForQueryDocuments = getAddressForQueryDocuments(uoi, token);

        log.info("Send GET {}", addressForQueryDocuments);
        ResponseEntity<String> responseEntityDocuments = restClientService.get(addressForQueryDocuments);
        log.info("Receive {}", responseEntityDocuments);
        String documentsBody = responseEntityDocuments.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        try {
            List<CirdaxDocumentsDTO> cirdaxDocumentsDTOS = objectMapper.readValue(documentsBody, new TypeReference<List<CirdaxDocumentsDTO>>() {});
            cirdaxDocumentsDTOS.forEach(c -> c.setDeepLinkUrl(getAddressForDocuments(c.getDeepLinkUrl(), uoi, token, c.getDocumentId())));
            return new CirdaxDocumentsResponseDTO(null, cirdaxDocumentsDTOS, "");
        } catch (JsonParseException e) {
            return new CirdaxDocumentsResponseDTO("", Collections.emptyList(), documentsBody);
        } catch (JsonProcessingException e) {

            try {
                Map<String, Object> responseEntityDocumentsMap = objectMapper.readValue(documentsBody, new TypeReference<Map<String, Object>>() {});
                return new CirdaxDocumentsResponseDTO(responseEntityDocumentsMap.get(CirdaxResourcesParams.AccessTokenState.name()).toString(), Collections.emptyList(), "");
            } catch (JsonProcessingException ex) {
                return new CirdaxDocumentsResponseDTO("", Collections.emptyList(), documentsBody);
            }
        }
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
