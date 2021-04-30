package io.recheck.external.systems.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CirdaxProfile extends SystemProfile {

    private Map<CirdaxResourcesEnum, String> resources = new HashMap<>();

}
