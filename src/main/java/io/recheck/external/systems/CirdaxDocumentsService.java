package io.recheck.external.systems;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.external.systems.dto.*;
import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import io.recheck.external.systems.entity.CirdaxResourcesParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    public CirdaxResponseWrapperDTO requestAccessAndQueryDocuments(CirdaxAccessRequestDTO dto) {
        CirdaxAccessResponseDTO cirdaxAccessResponseDTO = requestAccess(dto);
        if (StringUtils.hasText(cirdaxAccessResponseDTO.getToken())) {
            return new CirdaxResponseWrapperDTO(queryDocuments(dto.getUoi(), cirdaxAccessResponseDTO.getToken()));
        }
        else {
            return new CirdaxResponseWrapperDTO(cirdaxAccessResponseDTO);
        }
    }

    private CirdaxAccessResponseDTO requestAccess(CirdaxAccessRequestDTO dto) {
        String addressForRequestToken = getAddressForRequestToken(dto.getUoi(), dto.getRequestorCode(), dto.getRequestorName());

        ResponseEntity<String> responseEntityToken = restClientService.get(addressForRequestToken);
        String requestAccessBody = responseEntityToken.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> responseEntityTokenMap = objectMapper.readValue(requestAccessBody, new TypeReference<Map<String, String>>() {});
            if (responseEntityTokenMap.containsKey(CirdaxResourcesParams.UoiAccessTokenHash.name())) {
                String token = responseEntityTokenMap.get(CirdaxResourcesParams.UoiAccessTokenHash.name());
                return new CirdaxAccessResponseDTO(token, "");
            }
            else {
                return new CirdaxAccessResponseDTO("", requestAccessBody);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new CirdaxAccessResponseDTO("", requestAccessBody);
        }
    }

    private CirdaxDocumentsResponseDTO queryDocuments(String uoi, String token) {
        String addressForQueryDocuments = getAddressForQueryDocuments(uoi, token);

        ResponseEntity<String> responseEntityDocuments = restClientService.get(addressForQueryDocuments);
        String documentsBody = responseEntityDocuments.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        try {
            List<CirdaxDocumentsDTO> cirdaxDocumentsDTOS = objectMapper.readValue(documentsBody, new TypeReference<List<CirdaxDocumentsDTO>>() {});
            cirdaxDocumentsDTOS.forEach(c -> c.setDeepLinkUrl(getAddressForDocuments(c.getDeepLinkUrl(), uoi, token, c.getDocumentId())));
            return new CirdaxDocumentsResponseDTO(cirdaxDocumentsDTOS, "");
        } catch (JsonProcessingException e) {
            return new CirdaxDocumentsResponseDTO(Collections.emptyList(), documentsBody);
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

    private String getAddressForDocuments(String httpUrl, String uoi, String accessToken, String documentId) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(httpUrl);

        builder.replaceQueryParam(CirdaxResourcesParams.UoiId.name(), uoi);
        builder.replaceQueryParam(CirdaxResourcesParams.UoiAccessToken.name(), accessToken);
        builder.replaceQueryParam(CirdaxResourcesParams.DocumentId.name(), documentId);

        return builder.toUriString();
    }
}
