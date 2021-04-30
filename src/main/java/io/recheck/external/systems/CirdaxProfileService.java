package io.recheck.external.systems;

import io.recheck.external.systems.dto.CirdaxProfileDTO;
import io.recheck.external.systems.entity.CirdaxProfile;
import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CirdaxProfileService {

    private CirdaxProfile cirdaxProfile = new CirdaxProfile();

    public void save(CirdaxProfileDTO cirdaxProfileDTO) {
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.RequestUoiAccessToken, cirdaxProfileDTO.getRequestUoiAccessTokenAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.CheckUoiAccessToken, cirdaxProfileDTO.getCheckUoiAccessTokenAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.UoiQueryDocuments, cirdaxProfileDTO.getUoiQueryDocumentsAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.GetUoiDocument, cirdaxProfileDTO.getGetUoiDocumentAddress());

        log.debug("insert into db: {}", cirdaxProfile);
    }

    public Map<CirdaxResourcesEnum, String> getCirdaxResourcesMap() {
        log.debug("select from db: {}", cirdaxProfile);
        return cirdaxProfile.getResources();
    }

}
