package io.recheck.external.systems;

import io.recheck.external.systems.dto.CirdaxProfileDTO;
import io.recheck.external.systems.entity.CirdaxProfile;
import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import io.recheck.uoi.dto.RegisterEndPointUOI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CirdaxProfileService {

    private final String SYSTEM_NAME = "Cirdax";

    private final CirdaxProfileRepository cirdaxProfileRepository;

    public CirdaxProfileService(CirdaxProfileRepository cirdaxProfileRepository) {
        this.cirdaxProfileRepository = cirdaxProfileRepository;
    }


    public void save(CirdaxProfileDTO cirdaxProfileDTO) {
        CirdaxProfile cirdaxProfile = findOrCreate();

        cirdaxProfile.getResources().put(CirdaxResourcesEnum.RequestUoiAccessToken, cirdaxProfileDTO.getRequestUoiAccessTokenAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.CheckUoiAccessToken, cirdaxProfileDTO.getCheckUoiAccessTokenAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.UoiQueryDocuments, cirdaxProfileDTO.getUoiQueryDocumentsAddress());
        cirdaxProfile.getResources().put(CirdaxResourcesEnum.GetUoiDocument, cirdaxProfileDTO.getGetUoiDocumentAddress());

        cirdaxProfileRepository.save(cirdaxProfile);
    }

    public Map<CirdaxResourcesEnum, String> getCirdaxResourcesMap() {
        CirdaxProfile cirdaxProfile = findOrCreate();
        return cirdaxProfile.getResources();
    }

    public void registerEndPoint(RegisterEndPointUOI registerEndPointUOI) {
        CirdaxProfile cirdaxProfile = findOrCreate();

        CirdaxResourcesEnum cirdaxResourcesEnumVal = registerEndPointUOI.getType();
        cirdaxProfile.getResources().put(cirdaxResourcesEnumVal, registerEndPointUOI.getUrl());

        cirdaxProfileRepository.save(cirdaxProfile);
    }

    private CirdaxProfile findOrCreate() {
        CirdaxProfile cirdaxProfile = cirdaxProfileRepository.findBySystemName(SYSTEM_NAME);
        if (cirdaxProfile == null) {
            cirdaxProfile = new CirdaxProfile(SYSTEM_NAME);
            cirdaxProfileRepository.save(cirdaxProfile);
        }
        return cirdaxProfile;
    }

}
