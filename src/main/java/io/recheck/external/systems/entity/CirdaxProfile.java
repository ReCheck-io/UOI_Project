package io.recheck.external.systems.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class CirdaxProfile extends SystemProfile {

    @ElementCollection
    @CollectionTable(name = "cirdax_profile_resources_map", joinColumns = { @JoinColumn(name = "cirdax_profile_id") })
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "resource_type")
    @Column(name = "resource_address", length = 500)
    private Map<CirdaxResourcesEnum, String> resources = new HashMap<>();

    public CirdaxProfile() {
    }

    public CirdaxProfile(String systemName) {
        super(systemName);
    }

}
