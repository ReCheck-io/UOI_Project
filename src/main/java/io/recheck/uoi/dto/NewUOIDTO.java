package io.recheck.uoi.dto;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.exceptions.ValidationErrorException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Locale;


@Data
@NoArgsConstructor
public class NewUOIDTO {

    @NotBlank(message = "Country code (Country's acronym) may not be empty.")
    private String countryCode;

    @NotBlank(message = "Level may not be empty.")
    private LEVEL level;
    // the owner will be able to be changed by a higher lvl role in the hierarchy
    private String owner;
    private String uoiClass;
    private String parentUOI;

    public NewUOIDTO(String countryCode, LEVEL level, String owner, String uoiClass, String parentUOI) throws ValidationErrorException {
        this.countryCode = countryCodeValidation(countryCode);
        this.countryCode = countryCode;
//        this.level = setLevel(level);
        this.owner = owner;
        System.out.println("DTO owner: " + owner);
        this.level = level;
        this.uoiClass = uoiClass;
        this.parentUOI = parentUOI;
    }

    public NewUOIDTO(String countryCode, LEVEL level, String owner, String uoiClass) throws ValidationErrorException {
        this.countryCode = countryCodeValidation(countryCode);
        this.countryCode = countryCode;
        //        this.level = setLevel(level);
        this.level = level;
        this.uoiClass = uoiClass;
    }

    public NewUOIDTO(String countryCode, LEVEL level, String owner) throws ValidationErrorException {
        this.countryCode = countryCodeValidation(countryCode);
        this.countryCode = countryCode;
        //        this.level = setLevel(level);
        this.level = level;
    }

    public NewUOIDTO(String countryCode, LEVEL level) throws ValidationErrorException {
        this.countryCode = countryCodeValidation(countryCode);
        this.countryCode = countryCode;
        //        this.level = setLevel(level);
        this.level = level;
    }

    public String countryCodeValidation(String countryCode) throws ValidationErrorException {
        countryCode = countryCode.toUpperCase(Locale.ROOT);
        if (countryCode.trim().length() != 2) {
            throw new ValidationErrorException("The country acronym has to be 2 letters");
        } else {
            String[] contries = Locale.getISOCountries();
            boolean res = false;
            for (String contry : contries) {
                if (contry.equals(countryCode)) {
                    res = true;
                    break;
                }
            }
            if (!res) {
                throw new ValidationErrorException("The country acronym you provided " + countryCode + "  is not recognized");
            }
        }
        return countryCode;
    }

    public LEVEL levelValidation(String level) throws ValidationErrorException {
        level = level.toUpperCase(Locale.ROOT);
        LEVEL tempLevel = null;
        LEVEL[] levels = LEVEL.values();
        System.out.println(levels.toString());
        System.out.println(level);
        boolean valid = false;
        for (LEVEL value : levels) {
            if (value.toString().equals(level)) {
                valid = true;
                tempLevel = value;
                break;
            }
        }
        if (!valid) {

            throw new ValidationErrorException("The specified LEVEL is not found in the db.");

        }
        System.out.println(levels);
        return tempLevel;
    }
}
