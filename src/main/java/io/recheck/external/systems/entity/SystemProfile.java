package io.recheck.external.systems.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class SystemProfile {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String systemName;

    public SystemProfile() {
    }

    public SystemProfile(String systemName) {
        this.systemName = systemName;
    }

}
