package io.recheck.uoi.dto;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.validation.CountryConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUOIDTO {

    @CountryConstraint
    @NotBlank(message = "Country code (Country's acronym) may not be empty.")
    private String countryCode;

    @NotNull(message = "Level may not be empty.")
    private LEVEL level;

    // the owner will be able to be changed by a higher lvl role in the hierarchy
    private String owner;

    private String uoiClass;

    private String parentUOI;
}
